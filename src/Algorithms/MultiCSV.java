package Algorithms;
import java.io.File;
import java.io.FilenameFilter;

import File_formats.BuildKml;
import File_formats.CsvReader;
import GIS.GISProject;
import GIS.GIS_layer;
import GIS.GIS_project;

/**
 * a class that can read Multiple CSV files from a directory and its sub-directories and convert them into a GIS_Project obj.
 * Note: using a relative path
 * @author Ofek Bader
 *
 */
public final class MultiCSV {

	/**
	 * project from type GIS_project which is a Set of GIS_layer, Set<GIS_layer>. <br>
	 * GIS_layer can be added to the project's set by "project.add( myGIS_Layer)".
	 */
	private static GIS_project project;

	/**
	 * should be kept private because MultiCSV should be a complete static class. <br>
	 * there should be no instances of this class.
	 */
	private MultiCSV() {}

	/**
	 * read all CSV files in given folder and its subdirectories and converts them into GIS_project object instance. <br>
	 * using the "CsvReader" static class to read single CSV files, this function uses recursion of CsvReader to read directories and folders. <br>
	 * [Note: using a relative path!]
	 * @param folder - to read CSV files from
	 * @return GIS_project object which is a set contains GIS_layers, each GIS_layer is equivalent to a single CSV file.
	 */
	public static GIS_project readDirectory(String folder) {
		project = new GISProject();
		visit(folder); 
		return project;
	}

	/**
	 * creating GIS_Layers from CSV files found in a specific folder and adding them into a Set -> GIS_project in this static class. <br>
	 * by using recursion it goes through all the subdirectories found in the current folder.
	 * @param folder to scan and read for CSV files
	 */
	private static void visit(String folder) {
		// folder to scan
		File dir = new File(folder);

		// filter - > scan for directories only
		FilenameFilter filterDir = new FilenameFilter() {
			public boolean accept (File dir, String name) {
				return new File(dir.getPath()+"\\"+name).isDirectory();
			}
		};
		// get directories
		String[] children = dir.list(filterDir);

		// for each sub-directory scan for CSV files again.
		if(children != null)
			for (String child:children) {
				// scan in the sub-directory
				visit(folder+"/"+child);
			}

		// filter -> scan for .csv filename extension files.
		FilenameFilter filter = new FilenameFilter() {
			public boolean accept (File dir, String name) {
				return name.endsWith(".csv");
			}
		};

		// get .csv files
		children = dir.list(filter);

		// for each .csv file found, read it using "CsvReader.read" static method of the CsvReader static class.
		// by reading the CsvReader returns a GIS_layer object reference, adding it to the Set GIS_project.
		if(children != null)
			for (String child:children) {
				project.add(CsvReader.read(folder+"/"+child));
			}
	}
	
	/**
	 * Exporting GIS_project type object to a single KML file that can be read by Google Earth.
	 * @param project the GIS_project object to get the data from.
	 * @param fileName relative path to save the KML file: EX: "Src/Data/myKML.kml"
	 */
	public static void createKML(GIS_project project, String fileName) {
		BuildKml.create(project, fileName);
	}
	
	/**
	 * Exporting GIS_layer type object to a single KML file that can be read by Google Earth.
	 * @param layer the GIS_layer object to get the data from.
	 * @param fileName relative path to save the KML file: EX: "Src/Data/myKML.kml"
	 */
	public static void createKML(GIS_layer layer, String fileName) {
		BuildKml.create(layer, fileName);
	}
}