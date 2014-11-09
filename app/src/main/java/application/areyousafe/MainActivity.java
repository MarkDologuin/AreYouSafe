package application.areyousafe;


import android.app.Dialog;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.LatLng;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class MainActivity extends FragmentActivity implements LocationListener{
    private GoogleMap map;
    public LocationManager lm;
    Button GetMyLocationButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getBaseContext());//If google play is available

        if(status != ConnectionResult.SUCCESS) // Google Play Services are not available
        {
            int requestCode = 10;
            Dialog dialog = GooglePlayServicesUtil.getErrorDialog(status, this, requestCode);
            dialog.show();
        }
        else
        {
            map = ((MapFragment) getFragmentManager().findFragmentById(R.id.mapFragment)).getMap();
            map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            map.setMyLocationEnabled(true);
            lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

            if (lm.isProviderEnabled(LocationManager.GPS_PROVIDER)){
                Location lastLocation = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                if (lastLocation != null){
                    onLocationChanged(lastLocation);
                }
                lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
            }
        }



        //set onClick Listener
        GetMyLocationButton = (Button) findViewById(R.id.MainButton);

        //BUTTON FUNCTION

        GetMyLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                map.animateCamera(CameraUpdateFactory.zoomTo(18));

            }
        });

        //Create HTTP
        createHTTP();


    }//End of MAIN

    public void createHTTP(){
        Log.e("ENTERING", "createHTTP");
        JSONArray jArray = null;
        /*
        String result = null;
        StringBuilder sb = null;
        InputStream is = null;


        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

        //http post
        try{
            HttpClient httpclient = new DefaultHttpClient();

            HttpPost httppost = new HttpPost("http://mgltr.root.sx/query.php");
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();
            is = entity.getContent();
            Log.e ("log_tag_test", "hello" + entity.toString());
        }catch(Exception e){
            Log.e("log_tag", "Error in http connection"+e.toString());
        }

       //convert response to string
        try{
            BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8);
            sb = new StringBuilder();
            sb.append(reader.readLine() + "\n");

            String line="0";
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            is.close();
            result=sb.toString();
        }catch(Exception e){
            Log.e("log_tag", "Error converting result "+e.toString());
        }

        String name;
        try{
            jArray = new JSONArray(result);
            JSONObject json_data = null;
            for(int i=0;i<jArray.length();i++){
                json_data = jArray.getJSONObject(i);
                String ct_name = json_data.getString("NAME");//here "Name" is the column name in database
            }
        }
        catch(JSONException e1){
            Toast.makeText(getBaseContext(), "No Data Found", Toast.LENGTH_LONG).show();
        } catch (ParseException e1) {
            e1.printStackTrace();
        }
        */
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onLocationChanged(Location location) {
        LatLng lastLng = new LatLng(location.getLatitude(),location.getLongitude());

        map.moveCamera(CameraUpdateFactory.newLatLng(lastLng));
        map.animateCamera(CameraUpdateFactory.zoomTo(16));


    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}


