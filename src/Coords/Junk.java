package Coords;

import Geom.Point3D;

public class Junk {
	
	
	private final static double earth_radius = 6378137;//ESTIMATE: 6371000;
	private final static double earth_lon_norm = 0.84709;
	private final double a = 6378137; // radius
	private final double e = 8.1819190842622e-2;  // eccentricity

	private final double asq = Math.pow(a,2);
	private final double esq = Math.pow(e,2);
	
	public Point3D danaVector3D(Point3D gps0, Point3D gps1) {
		double R = earth_radius;
		double b = R + gps1.x(); //destination.altitude;
		double c = R + gps0.x(); //source altitude;

		double b2       = b*b;
		double c2       = c*c;
		double bc2      = 2*b*c;

		// Longitudinal calculations
		double alpha    = gps1.y() - gps0.y();
		// Conversion to radian
		alpha = alpha * Math.PI / 180;
		// Small-angle approximation
		double cos      = 1 - alpha*alpha/2; //Math.cos(alpha);
		// Use the law of cosines / Al Kashi theorem
		double x        = Math.sqrt(b2 + c2 - bc2*cos);

		// Repeat for latitudinal calculations
		alpha = gps1.x() - gps0.x();
		alpha = alpha * Math.PI / 180;
		cos = 1 - alpha*alpha/2; //Math.cos(alpha);
		double y   = Math.sqrt(b2 + c2 - bc2*cos);

		// Obtain vertical difference, too
		double z   = gps1.z() - gps0.z();
		Point3D P=new Point3D(x, y, z);//returns the new point
		return P;
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
	
	public Point3D ecef2geo(Point3D ecef) {
		double a= earth_radius,
				f= 1/298.257;

		/*  Input
	    	      x: coordinate X meters
	    	      y: coordinate y meters
	    	      z: coordinate z meters
	    	    Output
	    	      lat: latitude rad
	    	      lon: longitude rad
	    	      h: height meters
		 */
		double x = ecef.x();
		double y = ecef.y();
		double z = ecef.z();
		double b = a - f*a;
		double e = Math.sqrt(Math.pow(a,2.0)-Math.pow(b,2.0))/a;
		double clambda = Math.atan2(y,x);
		double p = Math.sqrt(Math.pow(x,2.0)+Math.pow(y,2));
		double h_old = 0.0;
		// first guess with h=0 meters
		double theta = Math.atan2(z,p*(1.0-Math.pow(e,2.0)));
		double cs = Math.cos(theta);
		double sn = Math.sin(theta);
		double N = Math.pow(a,2.0)/Math.sqrt(Math.pow(a*cs,2.0)+Math.pow(b*sn,2.0));
		double h = p/cs - N;
		while( Math.abs(h-h_old) > 1.0e-6) {
			h_old = h;
			theta = Math.atan2(z,p*(1.0-Math.pow(e,2.0)*N/(N+h)));
			cs = Math.cos(theta);
			sn = Math.sin(theta);
			N = Math.pow(a,2.0)/Math.sqrt(Math.pow(a*cs,2.0)+Math.pow(b*sn,2.0));
			h = p/cs - N;
		}
		return new Point3D(Math.toDegrees(clambda), Math.toDegrees(theta), h);
	}
	
}
