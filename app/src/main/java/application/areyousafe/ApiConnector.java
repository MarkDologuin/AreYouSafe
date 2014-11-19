package application.areyousafe;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
/**
 * Created by Mark on 11/13/2014.
 * Majority of code from Taseen Amin Tutorial
 * https://www.youtube.com/watch?v=4Soj22OMc98
 */
public class ApiConnector {

        public JSONArray getIncidents(String x, String y)
        {
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

            try
            {

                DefaultHttpClient httpClient = new DefaultHttpClient();  // Default HttpClient
                HttpGet httpGet = new HttpGet(url);

                HttpResponse httpResponse = httpClient.execute(httpGet);

                httpEntity = httpResponse.getEntity();



            } catch (ClientProtocolException e) {

                // Signals error in http protocol
                e.printStackTrace();

                //Log Errors Here



            } catch (IOException e) {
                e.printStackTrace();
            }


            // Convert HttpEntity into JSON Array
            JSONArray jsonArray = null;

            if (httpEntity != null) {
                try {
                    String entityResponse = EntityUtils.toString(httpEntity);

                    Log.e("Entity Response  : ", entityResponse);

                    jsonArray = new JSONArray(entityResponse);

                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            return jsonArray;


        }


    }


