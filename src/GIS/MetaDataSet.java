package GIS;

import java.util.Date;

import Geom.Point3D;

public class MetaDataSet implements Meta_data{

	private long UTC;
	
	public MetaDataSet() {
		Date date = new Date();
		UTC = date.getTime();
	}
	
	@Override
	public long getUTC() {
		return UTC;
	}

	@Override
	public Point3D get_Orientation() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "Info: " + getUTC();
	}

}
