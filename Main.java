import Coords.MyCoords;
import Geom.Point3D;

public class Main {

	public static void main(String[] args)
	{	
		MyCoords m = new MyCoords();
		Point3D b9 = new Point3D(32.103315 ,35.209039,670);
		
		Point3D humus = new Point3D(32.106352,35.205225,650);
		double dist = m.distance3d(b9, humus);
		System.out.println(dist);
		
		Point3D v3d = m.vector3D(b9, humus);
		System.out.println(Math.sqrt(v3d.x()*v3d.x()+v3d.y()*v3d.y() + v3d.z()*v3d.z()));
		System.out.println("Vector3D: "+v3d);
		System.out.println("Vector3D converted: "+m.ecef2lla(v3d));
		System.out.println("check add, should be same as humus: "+m.add(b9, v3d));
		System.out.println();
		double b9x = Math.toRadians(humus.x()-b9.x());
		double b9y = Math.toRadians(humus.y()-b9.y());
		System.out.println("x: "+b9x+" y:"+b9y+"| "+Math.sin(b9x)*6378137 + " | "+ Math.sin(b9y)* 0.84709*6378137);
		Point3D vector  = new Point3D(Math.sin(b9x)*6378137, Math.sin(b9y)* 0.84709*6378137,humus.z()-b9.z());
		System.out.println("Vector by excel:" + vector);
		System.out.println("check add, should be same as humus: "+m.add(b9, vector));
		System.out.println(Math.sqrt(vector.x()*vector.x()+vector.y()*vector.y() + vector.z()*vector.z()));

		//		Point3D a = new Point3D(32.17218268, 34.81446402, 13.65040889);
//		Point3D b = m.geo2ecef(a);
//		System.out.println(a);
//		System.out.println(b);
//		System.out.println(m.ecef2lla(b));
//		System.out.println(m.computeGeodetic(b));
//		System.out.println();
//		Point3D az1 = new Point3D(32,34,600);
//		Point3D az2 = new Point3D(33,33,700);
		System.out.println(m.azimuth_elevation_dist(b9, humus)[0]);
		

	}

}
