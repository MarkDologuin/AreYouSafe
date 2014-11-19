package application.areyousafe;


import android.app.Dialog;

import android.content.Context;
import android.hardware.Camera;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
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
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.Key;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;


public class MainActivity extends FragmentActivity implements LocationListener{
    private GoogleMap map;
    public LocationManager lm;
    Button GetMyLocationButton;
    public List<Incident> incidentList;
    public Vector<Incident> incidentVector;
    public Location currentLocation;


    public TextView responseTextView;
    public Button mainButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mainButton = (Button) findViewById(R.id.MainButton);
        this.responseTextView = (TextView) this.findViewById(R.id.textView);

        //If google play is available
        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getBaseContext());

        //Else Google Play Services are not available
        if(status != ConnectionResult.SUCCESS)
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
                //map.animateCamera(CameraUpdateFactory.zoomTo(18));
                new GetIncidentsTask().execute(new ApiConnector());
                responseTextView.setText("Accessing database");
                mainButton.setEnabled(false);

            }
        });



    }//End of MAIN







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

        currentLocation = location;
        map.moveCamera(CameraUpdateFactory.newLatLng(lastLng));
        map.animateCamera(CameraUpdateFactory.zoomTo(18));


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

    private class GetIncidentsTask extends AsyncTask<ApiConnector,Long, JSONArray>
    {
        @Override
        protected JSONArray doInBackground(ApiConnector... params) {
            //executed on Background thread
            String longitude = String.valueOf( currentLocation.getLongitude());
            String latitude = String.valueOf( currentLocation.getLatitude());
            //JSONArray data = null;

            return params[0].getIncidents(longitude, latitude, responseTextView);
        }

        @Override
        protected  void onPostExecute (JSONArray jsonArray){
            Context context = getApplicationContext();
            CharSequence text = "Parsing Results!";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            //ParseResults(jsonArray);
        }

    }


    public void ParseResults(JSONArray results){


        //makes a new array list

        for(int i=0; i < results.length(); i++){
            JSONObject json = null;
            Incident temp = null;
            try {
                json = results.getJSONObject(i);
                temp =  new Incident(json.getString("date"),
                        json.getString("time"),
                        json.getString("latitude"),
                        json.getString("longitude"),
                        json.getString("total_injured"),
                        json.getString("total_killed"),
                        json.getString("contrib_factor_1"),
                        json.getString("contrib_factor_2"));
                incidentVector.add(temp);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        responseTextView.setText(incidentVector.size());

    }
}


