package info.devexchanges.restapi;

import android.os.AsyncTask;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class HttpGetActivity extends AppCompatActivity {
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.text);

        //execute asynctask
        new GetDataTask().execute();
    }

    private class GetDataTask extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... voids) {
            try {
                // Create URL
                URL githubEndpoint = new URL("https://api.github.com/");
                // Create connection
                HttpsURLConnection myConnection = (HttpsURLConnection) githubEndpoint.openConnection();
                myConnection.setRequestProperty("User-Agent", "my-rest-app-v0.1");
                myConnection.setRequestProperty("Accept", "application/vnd.github.v3+json");
                myConnection.setRequestProperty("Contact-Me", "wandered.vip.007@example.com");

                if (myConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    InputStream responseBody = myConnection.getInputStream();
                    InputStreamReader responseBodyReader = new InputStreamReader(responseBody, "UTF-8");
                    BufferedReader streamReader = new BufferedReader(responseBodyReader);
                    StringBuilder responseStrBuilder = new StringBuilder();

                    //get JSON String
                    String inputStr;
                    while ((inputStr = streamReader.readLine()) != null)
                        responseStrBuilder.append(inputStr);

                    myConnection.disconnect();
                    return responseStrBuilder.toString();
                } else {
                    Log.d("Main", "error in connection");
                    return "";
                }

            } catch (IOException e) {
                e.printStackTrace();
                return "";
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.i("Main", "result: " + s);
            textView.setText(s);
        }
    }
}
