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

    public GISElement(String lat,String lon,String alt,String mac, String ssid, String authmode, String firstseen, String channel, String rssi, String accuracyMeters, String type){
        setGeom(lat,lon,alt);
        setData(mac, ssid, authmode, firstseen, channel, rssi, accuracyMeters, type);
    }
    private void setGeom(String lat,String lon,String alt){
        double x = Double.parseDouble(lat);
        double y = Double.parseDouble(lon);
        double z = Double.parseDouble(alt);
        geomElement = new Point3D(x,y,z);
    }
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
        Point3D point = new MyCoords().add(geomElement,vec);//add vector to point;
        geomElement = point;//set new point.
    }
}
