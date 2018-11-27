package File_formats;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;

import GIS.*;

final public class CsvReader {
	private static int mac,SSID,firstSeen,lat,lon,alt,RSSI,channel,authMode,accuracy,Type;
	private static GISLayer gis_layer;
	public static GIS_layer read(String csv) {
		int count = 0;
		String line;
		try (BufferedReader br = new BufferedReader(new FileReader("src/Data/" + csv))) {
			gis_layer = new GISLayer();
			while ((line = br.readLine()) != null) {
				count++;
				String[] lineinfo = line.split(",");

				if (count == 2) {
					dynamicPostion(lineinfo);
				} else if (count > 2)
					gis_layer.add(new GISElement(lineinfo[lat],lineinfo[lon],lineinfo[alt],lineinfo[mac],lineinfo[SSID],lineinfo[authMode],lineinfo[firstSeen],lineinfo[channel],lineinfo[RSSI],lineinfo[accuracy],lineinfo[Type]));


			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return gis_layer;
	}

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
