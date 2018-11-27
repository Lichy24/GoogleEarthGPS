package File_formats;
import Algorithms.MultiCSV;
import GIS.GIS_layer;
import GIS.GIS_project;

/**
 * a static class used for Converting CVS files to a GIS objects (GIS_project | GIS_layer | GIS_element) and for converting GIS objects to KML files. <br>
 * Note: using "CsvReader", "MultiCSV" and "BuildKML classes.
 * @author Ofek Bader & Adi Lichy
 */
public class Csv2kml {

	/**
	 * should be kept private because Csv2Kml should be a complete static class. <br>
	 * there should be no instances of this class.
	 */
	private Csv2kml() {}
	
	/**
	 * Reading a CSV file from a given file's relative path, converts it into a GIS_layer <br>
	 * Note: using relative path name. <br>
	 * Note: in case of invalid or wrong path file name, the function would print an error stack trace and return NULL. <Br>
	 * Note: can read a single file, for multiple files use "readCSVDirectory(String)". <br>
	 * Note: read more in "CsvReader.read(String)" function. <br>
	 * @param CSVPathName the file path to read, Example: "Wiggle20171354.csv" or "src/Data/junk/Wiggle20171354.csv".
	 * @return GIS_layer object converted from the CSV file.
	 */
	public static GIS_layer readCSVFile(String CSVPathName) {
		return CsvReader.read(CSVPathName);
	}
	
	/**
	 * Reading multiple CSV files from a given directory path, scanning the given directory path and its sub-directories for CSV files 
	 * converting them into a CSV_layers using "CsvReader.read(String)", the function would stack them up in a GISProject set and eventually return it. <br>
	 * Note: the function is making use of "MultiCSV.readDirectory(String)" function.
	 * Note: using relative path.
	 * @param DirPathName the directory path to scan for CSV files, Example: "src/Data/junk/"
	 * @return GIS_layer object converted from the CSV file.
	 */
	public static GIS_project readCSVDirectory(String DirPathName) {
		return MultiCSV.readDirectory(DirPathName);
	}
	
	public void createKMLfile(GIS_project project, String pathname) {
		//return BuildKml.create(layer, fileName);
	}
}
