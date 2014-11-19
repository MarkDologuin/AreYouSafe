package application.areyousafe;

/**
 * Created by Mark on 11/13/2014.
 */
public class Incident {


    public String date;
    public String time;
    public String latitude;
    public String longitude;
    public int total_injured;
    public int total_killed;
    public String contrib_factor_1;
    public String contrib_factor_2;


    //One and only constructor
    public Incident(
    String _date,
    String _time,
    String _latitude,
    String _longitude,
    String _total_injured,
    String _total_killed,
    String _contrib_factor_1,
    String _contrib_factor_2)
    {


        date = _date;
        time = _time;
        latitude = _latitude;
        longitude = _longitude;
        total_injured = Integer.parseInt(_total_injured);
        total_killed = Integer.parseInt(_total_killed);
        contrib_factor_1 = _contrib_factor_1;
        contrib_factor_2 = _contrib_factor_2;

    }


}
