import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import Coords.MyCoords;
import Geom.Point3D;

public class TestMyCoords {

	static MyCoords m;
	static double approximity;
	static double pointApprox;
	
	@BeforeClass
    public static void oneTimeSetUp() {
        m = new MyCoords(); 
        approximity = 0.1;
        pointApprox = 0.001;
    }

	
	@Test
	public void testAdd() {
		
	}
	
	@Test
	public void testDistance() {
		System.out.println("-------- Distance3D tests ----------");
		
		Point3D b9 = new Point3D(32.103315 ,35.209039,670);
		Point3D humus = new Point3D(32.106352,35.205225,650);

		double dist = m.distance3d(b9, humus);
		double distByVector = m.vectorNormal(m.vector3D(b9, humus));
		double distByVectorBoaz = m.vectorNormal2D(m.vector3D(b9, humus));
		
		assertTrue("distance should be around 493.6",dist > (493.6-approximity) && dist < (493.6+approximity));
		System.out.println("Distance3D: "+ dist + " | distByVector: " + distByVector + " | distByVectorBoaz: " + distByVectorBoaz);
	
	}
	
	@Test
	public void testVector3D() {
		System.out.println("-------- Vector tests ----------");
		
		Point3D b9 = new Point3D(32.103315 ,35.209039,670);
		Point3D humus = new Point3D(32.106352,35.205225,650);
		
		Point3D v3d = m.vector3D(b9, humus);
		System.out.println("vector3D Boaz: "+v3d);
		b9 = m.add(b9,v3d);
		System.out.println("humus: "+humus);
		System.out.println("should be humus: "+b9);
		
		assertTrue("Should be same as Humus point",( humus.x() > (b9.x()-pointApprox) && humus.x() < (b9.x()+pointApprox) ) &&
				( humus.y() > (b9.y()-pointApprox) && humus.y() < (b9.y()+pointApprox) ) &&
				( humus.z() > (b9.z()-pointApprox) && humus.x() < (b9.z()+pointApprox) ));
		
	}
	
	@Test
	public void testAzimuth() {
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

}
