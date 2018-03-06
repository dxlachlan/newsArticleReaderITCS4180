package com.example.danielcampbell.a800868709_midterm;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by danielcampbell on 10/23/17.
 * Daniel Campbell
 * Group21InClass06.zip
 */


public class MainActivity extends AppCompatActivity {

    String sourceAPI = "https://newsapi.org/v1/sources";
    private ArrayList<Source> sourcesList = new ArrayList<Source>();
    private ProgressDialog pdia;
    private int currentSourceID = 0;
    static String USER_KEY = "USER";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (isConnected()) {

            Toast.makeText(MainActivity.this, "Internet Available", Toast.LENGTH_SHORT).show();

            //get sources
            new GetDataAsync().execute(sourceAPI);


        } else {

            Toast.makeText(MainActivity.this, "No Internet", Toast.LENGTH_SHORT).show();
        }


    }


    private boolean isConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo == null || !networkInfo.isConnected() ||
                (networkInfo.getType() != ConnectivityManager.TYPE_WIFI
                        && networkInfo.getType() != ConnectivityManager.TYPE_MOBILE)) {
            return false;
        }
        return true;
    }


    private class GetDataAsync extends AsyncTask<String, Void, ArrayList<Source>> {
        @Override
        protected ArrayList<Source> doInBackground(String... params) {
            HttpURLConnection connection = null;
            ArrayList<Source> result = new ArrayList<>();
            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    String json = IOUtils.toString(connection.getInputStream(), "UTF-8");
                    JSONObject root = new JSONObject(json);
                    JSONArray sources = root.getJSONArray("sources");

                    for (int i = 0; i < sources.length(); i++) {
                        JSONObject sourceJSON = sources.getJSONObject(i);
                        Source source = new Source();
                        source.id = sourceJSON.getString("id");
                        source.name = sourceJSON.getString("name");
                        source.toString();
                        sourcesList.add(source);
                    }
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }
            return result;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pdia = new ProgressDialog(MainActivity.this);
            pdia.setMessage("Loading...");
            pdia.show();
        }

        @Override
        protected void onPostExecute(ArrayList<Source> sources) {
            super.onPostExecute(sources);
            pdia.dismiss();
            if (sourcesList.size() != 0) {

                populateListView(sources);
                registerClickCallBack();

            } else {
                Log.d("demo", "null result");
            }

        }

        private void populateListView(ArrayList<Source> s) {


            ArrayAdapter<Source> adapter = new MyListAdapter();
            ListView list = (ListView)findViewById(R.id.sourceListView);
            list.setAdapter(adapter);

        }

        private void registerClickCallBack(){

            ListView list = (ListView)findViewById(R.id.sourceListView);
            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    Source clickedSource = MainActivity.this.sourcesList.get(position);
                    currentSourceID = position;
                    //feedback for testing
                    String message = "You Clicked on " + clickedSource.getName();
                    Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(getApplicationContext(), NewsActivity.class);
                    i.putExtra("source", clickedSource);
                    i.putExtra("sourceID", currentSourceID);
                    startActivity(i);
                }
            });
        }

        private class MyListAdapter extends ArrayAdapter<Source> {
            public MyListAdapter() {
                super(MainActivity.this, R.layout.sourceview, sourcesList);
            }

            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View itemView = convertView;
                if (itemView == null) {
                    itemView = getLayoutInflater().inflate(R.layout.sourceview, parent, false);
                }

                Source currentSource = sourcesList.get(position);
                //fill the information in the view
                //set contact first name
                TextView sourceID = (TextView) itemView.findViewById(R.id.sourceID);
                sourceID.setText("" + currentSource.getId());
                TextView sourceName = (TextView) itemView.findViewById(R.id.sourceNAME);
                sourceName.setText("" + currentSource.getName());

                return itemView;

            }

        }












    }

}




