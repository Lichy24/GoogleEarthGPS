import Geom.Point3D;

public class Azimuth {
    public double[] azimuth_elevation_dist(Point3D gps0, Point3D gps1){
        double[] arr = new double[3];
        double R = 6371;
        double deltaLamda,deltaPhi,phi1,phi2,distance,alphaRad,alphaDeg ;
        deltaLamda = Math.toRadians(gps1.y()) - Math.toRadians(gps0.y());
        phi1 = Math.toRadians(gps0.x());
        phi2 = Math.toRadians(gps1.x());
        deltaPhi = phi2 - phi1;
        double a = Math.pow(Math.sin(deltaPhi/2),2)+Math.cos(phi1)*Math.cos(phi2)*Math.pow(Math.sin(deltaLamda/2),2);
        double c = 2*Math.atan2(Math.sqrt(a),Math.sqrt(1-a));
        distance =  R * c;
        System.out.println(distance);
        arr[1] = distance;
        double x = Math.sin(deltaLamda)*Math.cos(phi2);//spilt for checking
        double y = (Math.cos(phi1)*Math.sin(phi2))-(Math.sin(phi1)*Math.cos(phi2)*Math.cos(deltaLamda));//split for checking
        alphaRad = Math.atan2(x,y);
        alphaDeg = Math.toDegrees(alphaRad);
        if (alphaDeg<0)//just so it will give positive degree
            alphaDeg = 360+alphaDeg;
        System.out.println(alphaDeg); //check if you want from here.
        arr[0] = alphaDeg;
        return arr;
    }
}
