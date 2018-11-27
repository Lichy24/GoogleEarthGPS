import java.io.File;
import java.io.FilenameFilter;

import Algorithms.MultiCSV;
import Coords.MyCoords;
import File_formats.CsvReader;
import GIS.GISLayer;
import GIS.GISProject;
import Geom.Point3D;

public class Main {

	static 	MyCoords m = new MyCoords();
	public static void main(String[] args)
	{	
		testDistance3D();
		testVector();
		testAzimuth();
		testCsv2Kml();



	}
	
	public static void testDistance3D() {
		System.out.println("-------- Distance3D tests ----------");
		
		Point3D b9 = new Point3D(32.103315 ,35.209039,670);
		Point3D humus = new Point3D(32.106352,35.205225,650);

		double dist = m.distance3d(b9, humus);
		double distByVector = m.vectorNormal(m.vector3D(b9, humus));
		double distByVectorBoaz = m.vectorNormal2D(m.vector3D(b9, humus));
		System.out.println("Distance3D: "+ dist + " | distByVector: " + distByVector + " | distByVectorBoaz: " + distByVectorBoaz);
	}
	
	public static void testVector() {
		System.out.println("-------- Vector tests ----------");
		
		Point3D b9 = new Point3D(32.103315 ,35.209039,670);
		Point3D humus = new Point3D(32.106352,35.205225,650);
		
		Point3D v3d = m.vector3D(b9, humus);
		System.out.println("vector3D Boaz: "+v3d);
		b9 = m.add(b9,v3d);
		System.out.println("humus: "+humus);
		System.out.println("should be humus: "+b9);
		
		b9 = new Point3D(32.103315 ,35.209039,670);
		v3d = m.vector3D(b9, humus);
		Point3D bv3d = m.vector3D(b9, humus);
		System.out.println("Vector3D Boaz: "+bv3d + " | Normal: "+m.vectorNormal2D(bv3d));
		System.out.println("Vector3D: "+v3d+"| Normal: "+m.vectorNormal(v3d));
	}
	

	public static void testAzimuth() {
		System.out.println("-------- Azimuth tests ----------");
		
		Point3D b9 = new Point3D(32.103315 ,35.209039,670);
		Point3D humus = new Point3D(32.106352,35.205225,650);
		Point3D az1 = new Point3D(44,34,600);
		Point3D az2 = new Point3D(44,33.9,633);
		
		double[] azi = m.azimuth_elevation_dist(az1, az2);
		System.out.println(azi[0]+" | " +azi[1] + " | "+azi[2]);
		azi = m.azimuth_elevation_dist(b9, humus);
		System.out.println(azi[0]+" | " +azi[1] + " | "+azi[2]);
		

	}
	
	public static void testCsv2Kml() {
		GISLayer layer =  (GISLayer) CsvReader.read("src/Data/WigleWifi_20171201110209.csv");
		System.out.println(layer);
		
		GISProject project = (GISProject) MultiCSV.readDirectory("src/Data/");
	}


}
