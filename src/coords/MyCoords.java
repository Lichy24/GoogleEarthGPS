package Coords;

import Geom.Point3D;

public class MyCoords implements coords_converter{

	private final static double earth_radius = 6378137;//ESTIMATE: 6371000;
	private final static double earth_lon_norm = 0.84709;


	@Override
	public Point3D add(Point3D gps, Point3D local_vector_in_meter) {

		Point3D p3d = test(gps);
		p3d.add(local_vector_in_meter);
		return test2(p3d);
	}

	@Override
	public double distance3d(Point3D gps0, Point3D gps1) { //WORKS
		//
		//		double d = earth_radius*Math.sin(Point3D.d2r(gps1.x()-gps0.x()));
		//		double d2 = earth_radius*earth_lon_norm*Math.sin(Point3D.d2r(gps1.y()-gps0.y()));
		//		double norm = Math.sqrt((d*d+d2*d2));

		double deltaPhi,distance,deltaLamda,phi1,phi2,alphaRad,alphaDeg;
		deltaLamda = Math.toRadians(gps1.y()) - Math.toRadians(gps0.y());
		phi1 = Math.toRadians(gps0.x());
		phi2 = Math.toRadians(gps1.x());
		deltaPhi = phi2 - phi1;
		double a = Math.pow(Math.sin(deltaPhi/2),2)+Math.cos(phi1)*Math.cos(phi2)*Math.pow(Math.sin(deltaLamda/2),2);
		double c = 2*Math.atan2(Math.sqrt(a),Math.sqrt(1-a));
		distance =  earth_radius * c;



		return distance;
	}

	

	public double vectorNormal(Point3D vector) {
		return Math.sqrt(vector.x()*vector.x()+vector.y()*vector.y()+vector.z()*vector.z());
	}
	
	public double vectorNormal2D(Point3D vector) {
		return Math.sqrt(vector.x()*vector.x()+vector.y()*vector.y());
	}


	@Override
	public Point3D vector3D(Point3D gps0, Point3D gps1) {	
		double x0 = earth_radius*Math.sin(Point3D.d2r(gps1.x()-gps0.x()));
		double y0 = earth_radius*earth_lon_norm*Math.sin(Point3D.d2r(gps1.y()-gps0.y()));
		double z0 = gps1.z()-gps0.z();
		
		return new Point3D(x0,y0,z0);
	}



	

	@Override
	public double[] azimuth_elevation_dist(Point3D gps0, Point3D gps1){
		double[] arr = new double[3];

		/*
		 * deltaLamba =  longitude(gps1) - longitude(gps0) in radians
		 * phi1 = latitude(gps0) in radians
		 * phi2 = latitude(gps1) in radians
		 * alphaRad = the Azimuth angle in radians
		 * alphaDeg = the Azimuth angle in degrees
		 */
		double deltaLamda,phi1,phi2,alphaRad,alphaDeg;
		deltaLamda = Math.toRadians(gps1.y()) - Math.toRadians(gps0.y());
		phi1 = Math.toRadians(gps0.x());
		phi2 = Math.toRadians(gps1.x());

		double x = Math.sin(deltaLamda)*Math.cos(phi2);//Split for checking
		double y = (Math.cos(phi1)*Math.sin(phi2))-(Math.sin(phi1)*Math.cos(phi2)*Math.cos(deltaLamda));//split for checking
		alphaRad = Math.atan2(x,y);
		alphaDeg = Math.toDegrees(alphaRad);
		if (alphaDeg<0)//just so it will give positive degree
			alphaDeg = 360+alphaDeg;

		arr[0] = alphaDeg;
		double A = (gps1.z()-gps0.z());
		double C = distance3d(gps0,gps1); //vectorNormal2D(vector3D(gps0, gps1));//
		double B = Math.sqrt(C*C-A*A);
		arr[1] = Math.toDegrees(Math.atan(A/B));
		arr[2] = distance3d(gps0, gps1);
		System.out.println("Tan-1: "+ Math.toDegrees(Math.atan(A/B))+" | Sin-1: " + Math.toDegrees(Math.asin(A/C)));
		System.out.println("angle:" +Math.toDegrees(test(gps0).angleZ(test(gps1))));




		return arr;
	}

	@Override
	public boolean isValid_GPS_Point(Point3D p) { // works (?)

		// if lat is out of range of (-180, 180) than it's an invalid GPS point
		if(p.x() < -180 && p.x() > 180)
			return false;

		// if lon is out of range of (-90, 90) than it's an invalid GPS point
		if(p.y() < -90 && p.y() > 90)
			return false;

		// if alt is out of range of (-450, +inf) than it's an invalid GPS point
		if(p.z() < -450)
			return false;

		return true;
	}


	
	public Point3D test(Point3D gps0) {
	
		double x = earth_radius*Math.sin(Point3D.d2r(gps0.x()));
		double y = earth_radius*earth_lon_norm*Math.sin(Point3D.d2r(gps0.y()));
		
		return new Point3D(x,y,gps0.z());
	}
	
	public Point3D test2(Point3D v3d) {
		double x = Math.toDegrees(Math.asin(v3d.x()/earth_radius));
		double y = Math.toDegrees(Math.asin(v3d.y()/(earth_radius*earth_lon_norm)));
		
		return new Point3D(x,y,v3d.z());
	}

}
