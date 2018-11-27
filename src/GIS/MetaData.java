package GIS;

import GIS.Meta_data;
import Geom.Point3D;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Meta data object represents all the information about the object itself, mostly about the GISElement object.
 * Contains data as mac address, ssid address and more.
 * @author Adi Lichy
 *
 */
public class MetaData implements Meta_data {
    private String mac,ssid,authmode,firstseen,channel,rssi, accuracyMeters,type;

    /**
     * Creates a new MetaData object.
     * @param mac
     * @param ssid
     * @param authmode
     * @param firstseen
     * @param channel
     * @param rssi
     * @param accuracyMeters
     * @param type
     */
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
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        firstseen = firstseen.replace('-', '/');
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

    /**
     * returns this object's mac
     * @return mac address
     */
    public String getMac() {
        return mac;
    }

    /**
     * returns this object's SSID
     * @return SSID address
     */
    public String getSsid() {
        return ssid;
    }

    /**
     * returns this Authentication Mode
     * @return Auth Mode
     */
    public String getAuthmode() {
        return authmode;
    }

    /**
     * Returns the "firstseen" attribute
     * @return fristseen
     */
    public String getFirstseen() {
        return firstseen;
    }

    /**
     * returns the radio channel used
     * @return channel
     */
    public String getChannel() {
        return channel;
    }

    /**
     * returns RSSI
     * @return RSSI
     */
    public String getRssi() {
        return rssi;
    }

    /**
     * returns the accuracy in meters
     * @return accuracy
     */
    public String getAccuracyMeters() {
        return accuracyMeters;
    }

    /**
     * returns this object's type
     * @return type
     */
    public String getType() {
        return type;
    }
}
