package GIS;

import Coords.MyCoords;
import GIS.GIS_element;
import GIS.Meta_data;

import Geom.Geom_element;
import Geom.Point3D;

/**
 * GISELement implements GIS_element as the basic geometric element of Point3D along with metadata converted from CSV file.
 * @author Ofek Bader
 */
public class GISElement implements GIS_element{
	private Point3D geomElement;
    private MetaData metaData;

    /**
     * Creates a new GISElements from given parameters.
     * @param lat
     * @param lon
     * @param alt
     * @param mac
     * @param ssid
     * @param authmode
     * @param firstseen
     * @param channel
     * @param rssi
     * @param accuracyMeters
     * @param type
     */
    public GISElement(String lat,String lon,String alt,String mac, String ssid, String authmode, String firstseen, String channel, String rssi, String accuracyMeters, String type){
        setGeom(lat,lon,alt);
        setData(mac, ssid, authmode, firstseen, channel, rssi, accuracyMeters, type);
    }
    
    /**
     * set the Geometric element (can be Point3D)
     * @param lat
     * @param lon
     * @param alt
     */
    private void setGeom(String lat,String lon,String alt){
        double x = Double.parseDouble(lat);
        double y = Double.parseDouble(lon);
        double z = Double.parseDouble(alt);
        geomElement = new Point3D(x,y,z);
    }
    
    /**
     * set the Geometric element (can be Point3D)
     * @param Point3D geom
     */
    private void setGeom(Point3D geom){
        geomElement = geom;
    }
    
    /**
     * Set only Meta Data.
     * @param mac
     * @param ssid
     * @param authmode
     * @param firstseen
     * @param channel
     * @param rssi
     * @param accuracyMeters
     * @param type
     */
    private void setData(String mac, String ssid, String authmode, String firstseen, String channel, String rssi, String accuracyMeters, String type){
        metaData = new MetaData(mac, ssid, authmode, firstseen, channel, rssi, accuracyMeters, type);
    }
    @Override
    public Geom_element getGeom() {
        return geomElement;
    }

    @Override
    public Meta_data getData() {
        return metaData;
    }

    @Override
    public void translate(Point3D vec) {
    	 geomElement = new MyCoords().add(geomElement,vec);//add vector to point;
    }
}
