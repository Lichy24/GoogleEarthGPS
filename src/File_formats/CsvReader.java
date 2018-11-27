package File_formats;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;

import GIS.*;

/**
 * reads a single CSV file and converts it into a GIS_Layer obj.
 * Note: using relative paths.
 * @author Adi Lichy
 *
 */
final public class CsvReader {
	// fields of CSV file
	private static int mac,SSID,firstSeen,lat,lon,alt,RSSI,channel,authMode,accuracy,Type;
	private static GISLayer gis_layer;
	
	/**
	 * should be kept private because CsvReader should be a complete static class. <br>
	 * there should be no instances of this class.
	 */
	private CsvReader() {}
	
	/**
	 * Reading a CSV file from a given file's relative path, converts it into a GIS_layer
	 * @param csv string pathname relative to current program run.
	 * @return GIS_layer object.
	 */
	public static GIS_layer read(String csv) {
		int count = 0;
		String line;
		try (BufferedReader br = new BufferedReader(new FileReader(csv))) { //read file, each line is converted to GIS_element
			gis_layer = new GISLayer(); // initialize GISLayer as GISLayer is a set of GIS_Element
			while ((line = br.readLine()) != null) {
				count++;
				String[] lineinfo = line.split(","); // CSV is a file which its values separated by comma.
				
				// the first line in the file is metadata, the second line contains header types
				if (count == 2) { 
					dynamicPostion(lineinfo);
				} else if (count > 2) // convert each line to GISElement and add it to the layer
					gis_layer.add(new GISElement(lineinfo[lat],lineinfo[lon],lineinfo[alt],lineinfo[mac],lineinfo[SSID],lineinfo[authMode],lineinfo[firstSeen],lineinfo[channel],lineinfo[RSSI],lineinfo[accuracy],lineinfo[Type]));


			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return gis_layer;
	}

	/**
	 * the header types in CSV files are not always in the same order, this functions handles different orders of data columns and arranges them accordingly. 
	 * @param strs a single line from CSV file
	 */
	private static void dynamicPostion(String[] strs){
		for (int i = 0;i<strs.length;i++) {
			switch (strs[i]) {
			case "MAC":
				mac = i;
				break;
			case "SSID":
				SSID = i;
				break;
			case "AuthMode":
				authMode = i;
				break;
			case "FirstSeen":
				firstSeen = i;
				break;
			case "RSSI":
				RSSI = i;
				break;
			case "CurrentLatitude":
				lat = i;
				break;
			case "CurrentLongitude":
				lon = i;
				break;
			case "AltitudeMeters":
				alt = i;
				break;
			case "Channel":
				channel = i;
				break;
			case "AccuracyMeters":
				accuracy = i;
				break;
			case "Type":
				Type = i;
				break;
			}
		}
	}
}
