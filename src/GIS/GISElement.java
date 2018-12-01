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
     * @param lat String of a geographic coordinate that specifies the north–south position of a point on the Earth's surface.(for more information go to https://en.wikipedia.org/wiki/Latitude)
     * @param lon String of a geographic coordinate that specifies the east–west position of a point on the Earth's surface.(for more information go to https://en.wikipedia.org/wiki/Longitude)
     * @param alt String of altitude is commonly used to mean the height above sea level of a location.(for more information go to https://en.wikipedia.org/wiki/Altitude)
     * @param mac String of machine id.
     * @param ssid String display name of device.
     * @param authmode String type of encryption.
     * @param firstseen String exact date of device been seen.
     * @param channel String channel which used for transmission.
     * @param rssi String Received Signal Strength Indicator show from 0 to 255 the strength of signal the closer to 0 the better.
     * @param accuracyMeters String the accuracy of device location.
     * @param type String type of transmission to device.
     */
    public GISElement(String lat,String lon,String alt,String mac, String ssid, String authmode, String firstseen, String channel, String rssi, String accuracyMeters, String type){
        setGeom(lat,lon,alt);
        setData(mac, ssid, authmode, firstseen, channel, rssi, accuracyMeters, type);
    }
    
    /**
     * set the Geometric element (can be Point3D)
     * @param lat String of a geographic coordinate that specifies the north–south position of a point on the Earth's surface.(for more information go to https://en.wikipedia.org/wiki/Latitude)
     * @param lon String of a geographic coordinate that specifies the east–west position of a point on the Earth's surface.(for more information go to https://en.wikipedia.org/wiki/Longitude)
     * @param alt String of altitude is commonly used to mean the height above sea level of a location.(for more information go to https://en.wikipedia.org/wiki/Altitude)
     */
    private void setGeom(String lat,String lon,String alt){
        double x = Double.parseDouble(lat);
        double y = Double.parseDouble(lon);
        double z = Double.parseDouble(alt);
        geomElement = new Point3D(x,y,z);
    }
    
    /**
     * set the Geometric element (can be Point3D)
     * @param geom Point3D point of location.
     */
    private void setGeom(Point3D geom){
        geomElement = geom;
    }
    
    /**
     * Set only Meta Data.
     * @param mac String of machine id.
     * @param ssid String display name of device.
     * @param authmode String type of encryption.
     * @param firstseen String exact date of device been seen.
     * @param channel String channel which used for transmission.
     * @param rssi String Received Signal Strength Indicator show from 0 to 255 the strength of signal the closer to 0 the better.
     * @param accuracyMeters String the accuracy of device location.
     * @param type String type of transmission to device.
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

    /**
     * translate add vector to the geom
     * @param vec Cartesian coordinate system in meters.(for more information go to https://en.wikipedia.org/wiki/Cartesian_coordinate_system)
     */
    @Override
    public void translate(Point3D vec) {
    	 geomElement = new MyCoords().add(geomElement,vec);//add vector to point;
    }

    /**
     *  toString create a string of all contained object in GISElement.
     * @return String of GIS Element
     */
    @Override
    public String toString() {
        Point3D point = (Point3D) getGeom();
        String str = "GISElement:[Geom: [lat: "+point.x()+" , lon: "+point.y()+" , alt: "+point.z()+"] , MetaData:"+getData().toString()+"]";
        return str;
    }
}
