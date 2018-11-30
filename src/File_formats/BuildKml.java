package File_formats;

import GIS.*;
import Geom.Point3D;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.Iterator;

/**
 * a static class used to generate / create / build a kml file from a given GIS object (GIS_project | GIS_layer).
 * @author Adi Lichy.
 */
public class BuildKml {
    private static DocumentBuilderFactory factory;
    private static DocumentBuilder builder;
    private static Document document;
    private static Element folder;
    private static Iterator<GIS_element> iteratorLayer;
    private static Point3D beforePoint;
    private static boolean isProject=false;
    
	/**
	 * should be kept private because BuildKml should be a complete static class. <br>
	 * there should be no instances of this class.
	 */
    private BuildKml() {}

    /**
     * create GISProject to kml
     * @param project GISProject data which to contain in file
     * @param fileName String String name of file to create and contain layer data
     */
    public static void create(GIS_project project, String fileName){
        try {
            isProject = true;
            factory = DocumentBuilderFactory.newInstance();
            builder = factory.newDocumentBuilder();
            document = builder.newDocument();
            setupKml();//setup
            Iterator<GIS_layer> iteratorProject = project.iterator();
            GISLayer layer;//current layer
            Iterator<GIS_element> iteratorLayer;//iterator of current layer
            while (iteratorProject.hasNext()) {
                layer = (GISLayer) iteratorProject.next();
                create(layer,fileName);
            }
            createFile(fileName);//create file
        }catch (Exception e){
            e.printStackTrace();
        } finally {
            isProject = false;
        }
    }

    /**
     * create GISLayer to kml
     * @param layer GISLayer data which to contain in file
     * @param fileName String name of file to create and contain layer data
     */
    public static void create(GIS_layer layer, String fileName){
        try {
            if(!isProject) {//if build a project to kml don't run this code
                factory = DocumentBuilderFactory.newInstance();
                builder = factory.newDocumentBuilder();
                document = builder.newDocument();
                setupKml();//setup
            }
                iteratorLayer = layer.iterator();//elements from layer
                while (iteratorLayer.hasNext()) {
                    addGIS((GISElement) iteratorLayer.next());//casting and add
                }

            if (!isProject)//if build a project to kml don't run this code
                createFile(fileName);//create file
            }
            catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * addGIS gets GISElement and create tags to contain its data.
     * @param gisElement GISElement current element being added to kml
     */
    private static void addGIS(GISElement gisElement){//add gis element to kml.
        MetaData data = (MetaData)gisElement.getData();
        Point3D geo = (Point3D) gisElement.getGeom();

        Element placemark = document.createElement("Placemark");//placemark tag
        folder.appendChild(placemark);

        Element name = document.createElement("name");//name tag
        name.appendChild(document.createCDATASection(data.getSsid()));
        placemark.appendChild(name);

        Element description = document.createElement("description");//description tag
        description.appendChild(document.createCDATASection("BSSID: <b>"+data.getMac()+"</b><br/>Capabilities: <b>"+data.getAuthmode()+"</b><br/>Frequency: <b>"+data.getFrequency()+"</b><br/>Timestamp: <b>"+data.getUTC()+"</b><br/>Date: <b>"+data.getFirstseen()+"</b>"));
        placemark.appendChild(description);

        Element timestamp = document.createElement("TimeStamp");//timestamp tag
        placemark.appendChild(timestamp);
        Element when = document.createElement("when");
        when.appendChild(document.createTextNode(data.getFirstseen().replace(" ","T")+"Z"));
        timestamp.appendChild(when);

        Element styleUrl = document.createElement("styleUrl");//styleurl tag
        styleUrl.appendChild(document.createTextNode(setColorByHeight(geo)));
        placemark.appendChild(styleUrl);

        //point
        Element point = document.createElement("Point");//point tag
        placemark.appendChild(point);

        Element coordinate = document.createElement("coordinates");//coordinates tag
        point.appendChild(coordinate);
        coordinate.appendChild(document.createTextNode(geo.y()+","+geo.x()));

    }

    /**
     * setupKml writes in the setup lines of turn xml to kml adding styles etc.
     */
    private static void setupKml() {//kml starter
        Element root = document.createElement("kml");
        root.setAttribute("xmlns","http://www.opengis.net/kml/2.2");
        document.appendChild(root);

        Element doc = document.createElement("Document");
        root.appendChild(doc);

        setStyle("red","http://maps.google.com/mapfiles/ms/icons/red-dot.png",doc);//red icon
        setStyle("yellow","http://maps.google.com/mapfiles/ms/icons/yellow-dot.png",doc);//yellow icon
        setStyle("green","http://maps.google.com/mapfiles/ms/icons/green-dot.png",doc);//green icon
        setStyle("blue","http://maps.google.com/mapfiles/ms/icons/blue-dot.png",doc);//blue icon

        folder = document.createElement("Folder");
        doc.appendChild(folder);

        Element name = document.createElement("name");
        name.appendChild(document.createTextNode("Wifi Networks"));
        folder.appendChild(name);
    }

    /**
     *  setStyle build style tags in kml
     * @param name String tag name
     * @param iconURL String url of icon image
     * @param root Element which tag will contain this element.
     */
    private static void setStyle(String name,String iconURL,Element root){

        Element style = document.createElement("Style");
        style.setAttribute("id",name);
        root.appendChild(style);

        Element iconstyle = document.createElement("IconStyle");
        style.appendChild(iconstyle);

        Element icon = document.createElement("Icon");
        iconstyle.appendChild(icon);

        Element href = document.createElement("href");
        href.appendChild(document.createTextNode(iconURL));
        icon.appendChild(href);
    }

    /**
     * createFile creates file from giving data.
     * @param fileName String name of file may contain path.
     * @throws TransformerException in case of failing to create kml file throw exception.
     */
    private static void createFile(String fileName) throws TransformerException {//setting up kml file to put text into.
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource domSource = new DOMSource(document);

        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        StreamResult streamResult = new StreamResult(new File(fileName));

        transformer.transform(domSource,streamResult);
    }

    /**
     * setColorByHeight uses difference between current coord and the one before to color icon
     * @param currentPoint Point3D coords of current gis element
     * @return return blue if first point/not much difference in height, return red if current point is lower then the one before in quiet a bit
     *         return green if current point is higher then the one before.
     */
    private  static String setColorByHeight(Point3D currentPoint){
        if (beforePoint ==null){
            beforePoint = currentPoint;
            return "#blue";
        }
        //System.out.println("before: "+beforePoint.z()+" current: "+currentPoint.z());
        double diff = beforePoint.z()-currentPoint.z();
        //System.out.println("diff: "+ diff);
        beforePoint = currentPoint;
        if (diff>0.05){
            return "#red";
        }
        if (diff<-0.05){
            return "#green";
        }
        return "#blue";
    }
}
