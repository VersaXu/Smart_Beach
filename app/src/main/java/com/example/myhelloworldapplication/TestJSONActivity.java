package com.example.myhelloworldapplication;

import android.os.AsyncTask;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
public class TestJSONActivity extends AppCompatActivity {
    private static final String TAG = "UsingThingspeakAPI";
    private static final String THINGSPEAK_CHANNEL_ID = "84681";
    private static final String THINGSPEAK_API_KEY = "THINGSPEAK READ API KEY"; //GARBAGE KEY
    private static final String THINGSPEAK_API_KEY_STRING = "THINGSPEAK READ API KEY";
    /* Be sure to use the correct fields for your own app*/
    private static final String THINGSPEAK_FIELD1 = "field1";
    private static final String THINGSPEAK_FIELD2 = "field2";
    private static final String THINGSPEAK_UPDATE_URL = "https://api.thingspeak.com/update?";
    private static final String THINGSPEAK_CHANNEL_URL = "https://api.thingspeak.com/channels/";
    private static final String THINGSPEAK_FEEDS_LAST = "/field";
    TextView t1,t2;
    Button b1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_jsonactivity);
        t1=findViewById(R.id.textView2);
        t2=findViewById(R.id.textView3);
        b1=findViewById(R.id.button);
        t2.setText("");
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    new FetchThingspeakTask().execute();
                }
                catch(Exception e){
                    Log.e("ERROR", e.getMessage(), e);
                }
            }
        });

    }
    class FetchThingspeakTask extends AsyncTask<Void, Void, String> {
        protected void onPreExecute() {
            t2.setText("Fetching Data from Server.Please Wait...");
        }
        protected String doInBackground(Void... urls) {
            try {
//                URL url = new URL(THINGSPEAK_CHANNEL_URL + THINGSPEAK_CHANNEL_ID +
//                        THINGSPEAK_FEEDS_LAST + THINGSPEAK_API_KEY_STRING + "=" +
//                        THINGSPEAK_API_KEY + "");
                URL url = new URL("https://thingspeak.com/channels/295910/field/2.json");
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                try {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder stringBuilder = new StringBuilder();
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        stringBuilder.append(line).append("\n");
                    }
                    bufferedReader.close();
                    return stringBuilder.toString();
                }
                finally{
                    urlConnection.disconnect();
                }
            }
            catch(Exception e) {
                Log.e("ERROR", e.getMessage(), e);
                return null;
            }
        }
        protected void onPostExecute(String response) {
            if(response == null) {
                Toast.makeText(TestJSONActivity.this, "There was an error", Toast.LENGTH_SHORT).show();
                return;
            }
            try {
                JSONObject channel = (JSONObject) new JSONTokener(response).nextValue();
                double v1 = channel.getDouble(THINGSPEAK_FIELD1);
                if(v1>=90)
                    t1.setText("HI ALL  ");
                else
                    t1.setText("NO VALUES");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}