package File_formats;

import GIS.*;
import Geom.Point3D;
import Geom.Point3D;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.StringWriter;
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
    private static boolean isProject=false;
    
	/**
	 * should be kept private because BuildKml should be a complete static class. <br>
	 * there should be no instances of this class.
	 */
    private BuildKml() {}
    
    public static void create(GISProject project, String fileName){
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
    public static void create(GISLayer layer, String fileName){
        try {
            if(!isProject) {
                factory = DocumentBuilderFactory.newInstance();
                builder = factory.newDocumentBuilder();
                document = builder.newDocument();
                setupKml();//setup
            }
                iteratorLayer = layer.iterator();//elements from layer
                while (iteratorLayer.hasNext()) {
                    addGIS((GISElement) iteratorLayer.next());//casting and add
                }

            if (!isProject)
                createFile(fileName);//create file
            }
            catch (Exception e){
            e.printStackTrace();
        }
    }
    private static void addGIS(GISElement gisElement){//add gis element to kml.
        MetaData data = (MetaData)gisElement.getData();
        Point3D geo = (Point3D) gisElement.getGeom();

        Element placemark = document.createElement("Placemark");
        folder.appendChild(placemark);

        Element name = document.createElement("name");
        name.appendChild(document.createCDATASection(data.getSsid()));
        placemark.appendChild(name);

        Element description = document.createElement("description");
        description.appendChild(document.createCDATASection("BSSID: <b>"+data.getMac()+"</b><br/>Capabilities: <b>"+data.getAuthmode()+"</b><br/>Frequency: <b>"+data.getRssi()+"</b><br/>Timestamp: <b>"+data.getUTC()+"</b><br/>Date: <b>"+data.getFirstseen()+"</b>"));
        placemark.appendChild(description);

        Element styleUrl = document.createElement("styleUrl");
        styleUrl.appendChild(document.createTextNode("#red"));
        placemark.appendChild(styleUrl);

        //point
        Element point = document.createElement("Point");
        placemark.appendChild(point);

        Element coordinate = document.createElement("coordinates");
        point.appendChild(coordinate);
        coordinate.appendChild(document.createTextNode(geo.y()+","+geo.x()));

    }
    private static void setupKml() throws ParserConfigurationException {//kml starter
        Element root = document.createElement("kml");
        root.setAttribute("xmlns","http://www.opengis.net/kml/2.2");
        document.appendChild(root);

        Element doc = document.createElement("Document");
        root.appendChild(doc);

        setStyle("red","http://maps.google.com/mapfiles/ms/icons/red-dot.png",doc);//red icon
        setStyle("yellow","http://maps.google.com/mapfiles/ms/icons/yellow-dot.png",doc);//yellow icon
        setStyle("green","http://maps.google.com/mapfiles/ms/icons/green-dot.png",doc);//green icon

        folder = document.createElement("Folder");
        doc.appendChild(folder);

        Element name = document.createElement("name");
        name.appendChild(document.createTextNode("Wifi Networks"));
        folder.appendChild(name);
    }
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
    private static void createFile(String fileName) throws TransformerException {//setting up kml file to put text into.
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource domSource = new DOMSource(document);

        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        StreamResult streamResult = new StreamResult(new File(fileName));

        transformer.transform(domSource,streamResult);
    }
}
