package info.devexchanges.restapi;

import android.net.http.HttpResponseCache;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;

import javax.net.ssl.HttpsURLConnection;

public class HttpPostActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new GetDataTask().execute();
    }

    private class GetDataTask extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... voids) {
            URL httpbinEndpoint;
            try {
                httpbinEndpoint = new URL("https://httpbin.org/post");
                HttpsURLConnection myConnection = (HttpsURLConnection) httpbinEndpoint.openConnection();
                myConnection.setRequestMethod("POST");

                //Create the data
                String myData = "message=Hello";
                myConnection.setDoOutput(true);
                myConnection.getOutputStream().write(myData.getBytes());

                //get response
                if (myConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    Log.i("Main", "getresponse code 200: ");
                    String result;
                    // Get InputStream
                    InputStream is = myConnection.getInputStream();
                    // Convert the InputStream into a string
                    String charset = "UTF-8";
                    BufferedReader r = new BufferedReader(new InputStreamReader(is, charset));
                    StringBuilder total = new StringBuilder();
                    String line;

                    while ((line = r.readLine()) != null) {
                        total.append(line);
                    }

                    byte[] bytes = total.toString().getBytes();
                    result = new String(bytes, Charset.forName(charset));
                    Log.d("Main", result);

                } else {
                    // Server returned HTTP error code.
                    Log.d("Main", "server error");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return "";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.i("Main", "result: " + s);
        }
    }
}
