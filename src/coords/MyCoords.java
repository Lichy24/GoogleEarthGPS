package Coords;

import Geom.Point3D;

public class MyCoords implements coords_converter{

	private final static double earth_radius = 6378137;//ESTIMATE: 6371000;
	private final static double earth_lon_norm = 0.84709;
	private final double a = 6378137; // radius
	private final double e = 8.1819190842622e-2;  // eccentricity

	private final double asq = Math.pow(a,2);
	private final double esq = Math.pow(e,2);
	
	@Override
	public Point3D add(Point3D gps, Point3D local_vector_in_meter) {
		
		Point3D p3d = geo2ecef(gps);//new Point3D(gps);
		p3d.add(local_vector_in_meter);
		return ecef2lla(p3d);
	}

	@Override
	public double distance3d(Point3D gps0, Point3D gps1) { //WORKS

		double d = earth_radius*Math.sin(Point3D.d2r(gps1.x()-gps0.x()));
		double d2 = earth_radius*earth_lon_norm*Math.sin(Point3D.d2r(gps1.y()-gps0.y()));
		double norm = Math.sqrt((d*d+d2*d2));
		
		return norm;
	}
	
	/**
	 * Converts from LLA (Geodetic) coordinates to ECEF (XYZ) coordinates.
	 * @param gps0 Point3D object in Geodetic system.
	 * @return Point3D object in ECEF system.
	 */
	public Point3D geo2ecef(Point3D gps0) {
		double x0 = earth_radius * Math.cos(Point3D.d2r(gps0.x())) * Math.cos(Point3D.d2r(gps0.y()));
		double y0 = earth_radius * Math.cos(Point3D.d2r(gps0.x())) * Math.sin(Point3D.d2r(gps0.y()));
		double z0 = earth_radius * Math.sin(Point3D.d2r(gps0.x()));
		return new Point3D(x0,y0,z0);
	}
	
	/**
	 * Converts from ECEF (XYZ) coordinates to LLA (Geodetic) coordinates.
	 * @param gps0 Point3D object in ECEF system.
	 * @return Point3D object in Geodetic system.
	 */
	

	 public Point3D computeGeodetic(Point3D gps) { 
		  double X = gps.x();
		  double Y = gps.y();
		  double Z = gps.z();
		 
		  //this.geod = new SimpleMatrix(3, 1); 
		 
		  // Radius computation 
		  double r = Math.sqrt(Math.pow(X, 2) + Math.pow(Y, 2) + Math.pow(Z, 2)); 
		 
		  // Geocentric longitude 
		  double lamGeoc = Math.atan2(Y, X); 
		 
		  // Geocentric latitude 
		  double phiGeoc = Math.atan(Z / Math.sqrt(Math.pow(X, 2) + Math.pow(Y, 2))); 
		 
		  // Computation of geodetic coordinates 
		  double psi = Math.atan(Math.tan(phiGeoc) / Math.sqrt(1 - Math.pow(e, 2))); 
		  double phiGeod = Math.atan((r * Math.sin(phiGeoc) + Math.pow(e, 2) * a 
		    / Math.sqrt(1 - Math.pow(e, 2)) * Math.pow(Math.sin(psi), 3)) 
		    / (r * Math.cos(phiGeoc) - Math.pow(e, 2) * a * Math.pow(Math.cos(psi), 3))); 
		  double lamGeod = lamGeoc; 
		  double N = a / Math.sqrt(1 - Math.pow(e, 2) * Math.pow(Math.sin(phiGeod), 2)); 
		  double h = r * Math.cos(phiGeoc) / Math.cos(phiGeod) - N; 
		 
		  return new Point3D( Math.toDegrees(phiGeod),Math.toDegrees(lamGeod),h );
		 } 
	
	public Point3D ecef2lla(Point3D ecef){
		  double x = ecef.x();
		  double y = ecef.y();
		  double z = ecef.z();

		  double b = Math.sqrt( asq * (1-esq) );
		  double bsq = Math.pow(b,2);
		  double ep = Math.sqrt( (asq - bsq)/bsq);
		  double p = Math.sqrt( Math.pow(x,2) + Math.pow(y,2) );
		  double th = Math.atan2(a*z, b*p);

		  double lon = Math.atan2(y,x);
		  double lat = Math.atan2( (z + Math.pow(ep,2)*b*Math.pow(Math.sin(th),3) ), (p - esq*a*Math.pow(Math.cos(th),3)) );
		  double N = a/( Math.sqrt(1-esq*Math.pow(Math.sin(lat),2)) );
		  double alt = p / Math.cos(lat) - N;

		  // mod lat to 0-2pi
		  
		  //
		  lat = Point3D.r2d(lat);
		  lon = Point3D.r2d(lon);
		  alt = Point3D.r2d(alt);

		  // correction for altitude near poles left out.

		  return new Point3D(lat, lon, alt);
		}
	
	
	@Override
	public Point3D vector3D(Point3D gps0, Point3D gps1) {
		
		//System.out.printf("%f, %f ,%f\n", x0, y0, z0);
		
		
		Point3D p0 = geo2ecef(gps0);
		Point3D p1 = geo2ecef(gps1);
		
//		//System.out.println(p0 +" \n" +p1);
//		System.out.println("Original: "+ gps0);
//		System.out.println("ECEF:" +p0);
//		//ecef2geo(p0);
//		System.out.println("LLA/GEODETIC:" + ecef2lla(p0));
//		//System.out.println(p0.distance3D(p1)); // CORRECT DISTANCE
		
		
		
		return new Point3D(p1.x() - p0.x(), p1.y() - p0.y(), p1.z() - p0.z());
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
	        double deltaLamda,phi1,phi2,alphaRad,alphaDeg ;
	        deltaLamda = Math.toRadians(gps1.y()) - Math.toRadians(gps0.y());
	        phi1 = Math.toRadians(gps0.x());
	        phi2 = Math.toRadians(gps1.x());

	        double x = Math.sin(deltaLamda)*Math.cos(phi2);//spilt for checking
	        double y = (Math.cos(phi1)*Math.sin(phi2))-(Math.sin(phi1)*Math.cos(phi2)*Math.cos(deltaLamda));//split for checking
	        alphaRad = Math.atan2(x,y);
	        alphaDeg = Math.toDegrees(alphaRad);
	        if (alphaDeg<0)//just so it will give positive degree
	            alphaDeg = 360+alphaDeg;
	        System.out.println(alphaDeg); //check if you want from here.
	        arr[0] = alphaDeg;
	        arr[1] = distance3d(gps0, gps1);
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

}
