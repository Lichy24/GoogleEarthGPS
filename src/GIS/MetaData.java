package GIS;

import GIS.Meta_data;
import Geom.Point3D;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class MetaData implements Meta_data {
    private String mac,ssid,authmode,firstseen,channel,rssi, accuracyMeters,type;

    public MetaData(String mac, String ssid, String authmode, String firstseen, String channel, String rssi, String accuracyMeters, String type) {
        this.mac = mac;
        this.ssid = ssid;
        this.authmode = authmode;
        this.firstseen = firstseen;
        this.channel = channel;
        this.rssi = rssi;
        this.accuracyMeters = accuracyMeters;
        this.type = type;
    }

    @Override
    public long getUTC() {
        try {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = dateFormat.parse(firstseen);
            Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
            calendar.setTime(date);
            return calendar.getTimeInMillis();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return -1;//time cant be negative soo... but should not reach.
    }

    @Override//TODO need cooords_converter method azimuth_elevation_dist to set orientation.
    public Point3D get_Orientation() {
        return null;
    }

    @Override
    public String toString() {//simple print.
        return "[ MAC: "+mac+" , SSID: "+ssid+" , AuthMode: "+authmode+" , Firstseen: "+firstseen+" , Channel: "+channel+" , RSSI: "+rssi+" , AccuracyMeters: "+accuracyMeters+" , Type: "+type+" ]";
    }
}
