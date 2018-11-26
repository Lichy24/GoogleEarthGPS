import Geom.Point3D;

public class Test {
    public static void main(String[] args) {
        //new Csv2kml("WigleWifi_20171201110209.csv");
        //new MultiCSV("src/Data");
        new Azimuth().azimuth_elevation_dist(new Point3D(40,35,200),new Point3D(32,20,100));
        new MetaData().getUTC();
    }
}
