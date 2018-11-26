import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Csv2kml {
    private String csv,line,dateFormat;
    private int lat,lon,firstSeen,SSID,mac,authMode,RSSI;
    private int count =0;
    private StringBuilder kmlTxt;

    /**
     * constructor given a file name of csv and creates a kml of given csv
     * @param csv String file name
     */
    Csv2kml(String csv){
        setCsv(csv);
        try(BufferedReader br = new BufferedReader(new FileReader("src/Data/"+csv))){
            kmlTxt = new StringBuilder();
            DateFormat format;
            Date date;
            kmlSetup();
            while ((line=br.readLine())!= null){
                count++;
                String[] lineinfo = line.split(",");
                if (count==2){
                    dynamicPostion(lineinfo);
                }
                /*
                for (String info:lineinfo) {
                    System.out.print(info+" , ");
                }
                System.out.println();
                */
                if (count>2){//first 2 lines are headers
                    System.out.println(firstSeen);
                    dateFormat = lineinfo[firstSeen].replaceAll("[APM]","");
                    format = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");
                    date = format.parse(dateFormat);
                    kmlTxt.append("<Placemark>\n");
                    kmlTxt.append("<name><![CDATA["+lineinfo[SSID]+"]]></name>\n");
                    kmlTxt.append("<description><![CDATA[BSSID: <b>"+lineinfo[mac]+"</b><br/>Capabilities: <b>"+lineinfo[authMode]+"</b><br/>Frequency: <b>"+lineinfo[RSSI]+"</b><br/>Timestamp: <b>"+date.getTime()+"</b><br/>Date: <b>"+lineinfo[firstSeen]+"</b>]]></description><styleUrl>#red</styleUrl>\n");
                    kmlTxt.append("<Point>\n<coordinates>"+lineinfo[lon]+","+lineinfo[lat]+"</coordinates></Point>\n");
                    kmlTxt.append("</Placemark>\n");
                }
            }
            kmlSealed();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * start writing a kml
     */
    private void kmlSetup(){
        kmlTxt.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?> \r\n");
        kmlTxt.append("<kml xmlns=\"http://www.opengis.net/kml/2.2\">");
        kmlTxt.append("<Document><Style id=\"red\"><IconStyle><Icon><href>http://maps.google.com/mapfiles/ms/icons/red-dot.png</href></Icon></IconStyle></Style><Style id=\"yellow\"><IconStyle><Icon><href>http://maps.google.com/mapfiles/ms/icons/yellow-dot.png</href></Icon></IconStyle></Style><Style id=\"green\"><IconStyle><Icon><href>http://maps.google.com/mapfiles/ms/icons/green-dot.png</href></Icon></IconStyle></Style><Folder><name>Wifi Networks</name>\n");

    }

    /**
     * finish kml data and create kml file to hold data.
     * @throws FileNotFoundException if file create couldn't be found.
     */
    private void kmlSealed() throws FileNotFoundException {
        String kml = csv.replace(".csv",".kml");//take same name just change format
        PrintWriter pw = new PrintWriter(new File("src/Data/"+kml));//create file and ready printwriter to file.
        kmlTxt.append("</Folder>\n</Document></kml>");//last line in kml
        pw.write(kmlTxt.toString());//write kml txt to file.
        pw.close();//end
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
    private void setCsv(String csv) {
        this.csv = csv;
    }
}
