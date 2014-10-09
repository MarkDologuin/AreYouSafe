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


import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.LatLng;


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

            }
        });

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
        map.animateCamera(CameraUpdateFactory.zoomTo(14));


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


