package application.areyousafe;

import android.app.Activity;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;


import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.LatLng;


public class MainActivity extends FragmentActivity {
    private GoogleMap map;
    private LocationManager lm;
    private Location currentLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MapView view = (MapView) findViewById(R.id.mapFragment);

        map = view.getMap();

        lm = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        LocationListener listener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                currentLocation = location;
                map.animateCamera(CameraUpdateFactory.newLatLng(new LatLng(location.getLatitude(), location.getLongitude())));

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
        };

        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER,0, 0, listener);
    
    }

    /**
    private void setMap() {
        map = ( (MapFragment) getFragmentManager().findFragmentById(R.id.mapFragment)).getMap();
        map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        LatLng currLoc = getCurrentLocation();
        //map.animateCamera(CameraUpdateFactory.newLatLng(currLoc, 15));
    }
    **/
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

    public void onClickButton(){
        //DOES NOTHING FOR NOW
    }


}


