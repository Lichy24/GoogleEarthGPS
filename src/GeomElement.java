import Geom.Geom_element;
import Geom.Point3D;

public class GeomElement implements Geom_element {
    private Point3D point;
    public GeomElement(){
        point = new Point3D(0,0,0);
    }
    public GeomElement(Point3D point){
        this.point =  new Point3D(point.x(),point.y(),point.z());
    }
    @Override
    public double distance3D(Point3D p) {
        return point.distance3D(p);
    }

    @Override
    public double distance2D(Point3D p) {
        return Math.sqrt(Math.pow(point.x()+p.x(),2)+Math.pow(point.y()+p.y(),2));
    }
}
