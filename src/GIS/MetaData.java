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
    private String mac,ssid,authmode,firstseen,channel,rssi, accuracyMeters,type,frequency;

    /**
     *  Creates a new MetaData object for GISElement.
     * @param mac String of machine id
     * @param ssid String display name of device.
     * @param authmode String type of encryption.
     * @param firstseen String exact date of device been seen.
     * @param channel String channel which used for transmission.
     * @param rssi String Received Signal Strength Indicator show from 0 to 255 the strength of signal the closer to 0 the better.
     * @param accuracyMeters String the accuracy of device location.
     * @param type String type of transmission to device.
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
        setFrequency();
    }

    /**
     * get UTC by current gis element date
     * @return long UTC in milliseconds
     */
    @Override
    public long getUTC() {
        try {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = dateFormat.parse(firstseen);
            Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
            calendar.setTime(date);
            return calendar.getTimeInMillis();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return -1;//time cant be negative soo... but should not reach.
    }

    /**
     * check by type and channel to know what kinda of frequency.
     */
    private void setFrequency(){
        if (type=="GSM")
            this.frequency = "0";
        else{
            this.frequency = String.valueOf(setByChannel());
        }
    }

    /**
     * SetByChannel check by channel of transmission to know what is the center frequency is.
     * !!! Note this Method only knows channels which serve israel , will not contain channels which not serve this country. !!!
     * @return int center of frequency.
     */
    private int setByChannel(){//get channel set frequency
        int channelInt = Integer.parseInt(channel);
        if (channelInt<14&&channelInt>0){
            return 2407+5*channelInt;
        }
        switch (channel){
            case "34":
                return 5170;
            case "36":
                return 5180;
            case "38":
                return  5190;
            case "40":
                return  5200;
            case "44":
                return  5220;
            case "46":
                return  5230;
            case "48":
                return 5240;
            case "54":
                return  5270;
            case "62":
                return  5310;
            case "56":
                return 5280;
            case "60":
                return  5300;
            case "64":
                return 5320;
            case "52":
                return  5260;
        }
        return 0;
    }

    /**
     * get_Orientation not implemented yet.
     * @return null
     */
    @Override//TODO need cooords_converter method azimuth_elevation_dist to set orientation.
    public Point3D get_Orientation() {
        return null;
    }

    /**
     * toString prints all object meta data contains.
     * @return String of objects in class
     */
    @Override
    public String toString() {//simple print.
        return "[ MAC: "+mac+" , SSID: "+ssid+" , AuthMode: "+authmode+" , Firstseen: "+firstseen+" , Channel: "+channel+", Frequency:"+ frequency+ " , RSSI: "+rssi+" , AccuracyMeters: "+accuracyMeters+" , Type: "+type+" ]";
    }

    /**
     * returns this object's frequency
     * @return String frequency
     */
    public String getFrequency() {
        return frequency;
    }

    /**
     * returns this object's mac
     * @return String mac address
     */
    public String getMac() {
        return mac;
    }

    /**
     * returns this object's SSID
     * @return String SSID address
     */
    public String getSsid() {
        return ssid;
    }

    /**
     * returns this Authentication Mode
     * @return String Auth Mode
     */
    public String getAuthmode() {
        return authmode;
    }
    /**
     * Returns this object's first time seen
     * @return String firstseen
     */
    public String getFirstseen() {
        return firstseen;
    }

    /**
     * returns this object's channel used
     * @return String channel
     */
    public String getChannel() {
        return channel;
    }

    /**
     * returns this object's Received Signal Strength Indicator
     * @return String RSSI
     */
    public String getRssi() {
        return rssi;
    }

    /**
     * returns this object's accuracy in meters
     * @return String accuracyMeters
     */
    public String getAccuracyMeters() {
        return accuracyMeters;
    }

    /**
     * returns this object's type
     * @return String type
     */
    public String getType() {
        return type;
    }
}
