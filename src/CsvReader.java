import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;

public class CsvReader {
    private  int mac,SSID,firstSeen,lat,lon,alt,RSSI,authMode,Type;
    public CsvReader(String csv) {
        int count = 0;
        String line;
        try (BufferedReader br = new BufferedReader(new FileReader("src/Data/" + csv))) {
            DateFormat format;
            Date date;
            while ((line = br.readLine()) != null) {
                count++;
                String[] lineinfo = line.split(",");
                if (count == 2) {
                    dynamicPostion(lineinfo);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void dynamicPostion(String[] strs){
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
            }
        }
    }
}
