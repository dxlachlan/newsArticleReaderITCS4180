package com.example.danielcampbell.a800868709_midterm;

import android.app.ProgressDialog;
import android.os.AsyncTask;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by danielcampbell on 10/23/17.
 * Daniel Campbell
 * Group21InClass06.zip
 */


public class GetArticles extends AsyncTask<String, Void, ArrayList<Article>>{

    private ProgressDialog pdia;
    private ArrayList<Article> articles = new ArrayList<>();



    @Override
    protected ArrayList<Article> doInBackground(String... params) {

        BufferedReader reader = null;
        ArrayList<String> articleList = new ArrayList<>();
        HttpURLConnection connection = null;
        try {
            URL url = new URL(params[0]);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                String json = IOUtils.toString(connection.getInputStream(), "UTF-8");
                JSONObject root = new JSONObject(json);
                //System.out.println(root);
                JSONArray urls = root.getJSONArray("articles");
                //System.out.println(urls);

                if (urls != null){
                    for (int i = 0; i<urls.length();i++){
                        articleList.add(urls.getString(i));
                        JSONObject articleJSON = urls.getJSONObject(i);
                        Article source = new Article();
                        source.author = articleJSON.getString("author");
                        source.title = articleJSON.getString("title");
                        source.url = articleJSON.getString("url");
                        source.urlToImage = articleJSON.getString("urlToImage");
                        source.publishedAt = articleJSON.getString("publishedAt");
                        articles.add(source);
                    }







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
        return articles;

    }


    @Override
    protected void onPreExecute() {



    }

    @Override
    protected void onPostExecute(ArrayList<Article> strings) {
        super.onPostExecute(strings);




    }



}
