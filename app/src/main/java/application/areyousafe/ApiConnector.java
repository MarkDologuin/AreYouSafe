package application.areyousafe;
import android.content.Context;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.internal.io;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.google.android.gms.internal.io.*;

/**
 * Created by Mark on 11/13/2014.
 * Majority of code from Taseen Amin Tutorial
 * https://www.youtube.com/watch?v=4Soj22OMc98
 */
public class ApiConnector {

    //TECHNICALY DO NOT NEED
        public static JSONArray getIncidents(String x, String y, TextView status)
        {


            String message ="";
            //y=latitude x=longitude
            // URL for getting all customers
            String url = "http://mgltr.root.sx/query.php?";
            url += "x=";
            url += x;
            url += "&y=";
            url += y;

            // Get HttpResponse Object from url.
            // Get HttpEntity from Http Response Object

            HttpEntity httpEntity = null;

            status.setText("Accessing:" + url);

            DefaultHttpClient httpClient = new DefaultHttpClient();  // Default HttpClient
            HttpGet httpGet = new HttpGet(url);
            HttpResponse httpResponse = null;

            //Execute http query
            try {
                httpResponse = httpClient.execute(httpGet);
            } catch (IOException e) {
                e.printStackTrace();
            }
            status.setText("HTTP Executed");


            if (httpResponse != null) {
                HttpEntity entity = httpResponse.getEntity();
            }



            //message = getStringFromInputStream(httpResponse.getEntity().getContent());


        //Log.e("BUFFERED READER", message );
           status.setText(message);

            // Convert HttpEntity into JSON Array
            JSONArray jsonArray = null;
            try {
                jsonArray = new JSONArray(message);
            }
            catch (JSONException e){
                e.printStackTrace();
            }
            /*
            if (httpEntity != null) {
                try {
                    String entityResponse = EntityUtils.toString(httpEntity);

                    Log.e("Entity Response  : ", entityResponse);
                //HANDLE RESPONSES as they come

                    jsonArray = new JSONArray(entityResponse);

                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            */
            return jsonArray;


        }


    public static String getStringFromInputStream(InputStream is) {

        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();

        String line;
        try {

            br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return sb.toString();

    }
 }


