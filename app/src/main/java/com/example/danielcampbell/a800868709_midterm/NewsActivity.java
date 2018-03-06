package com.example.danielcampbell.a800868709_midterm;

import android.app.ProgressDialog;
import android.content.Intent;
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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by danielcampbell on 10/23/17.
 * Daniel Campbell
 * Group21InClass06.zip
 */


public class NewsActivity extends AppCompatActivity {

    ArrayList<Article> articleList = new ArrayList<Article>();
    String apiKEY = "12cac4305e0d4c3faff4befadc1f7a72";
    String articleAPI = "https://newsapi.org/v1/articles";
    private int currentArticleID = 0;


    private ProgressDialog pdia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        Intent i = getIntent();
        Source source = (Source)getIntent().getExtras().getSerializable("source");
        String sourceName = source.getName();
        String sourceID_n = source.getId();
        final int sourceID = (int) i.getSerializableExtra("sourceID");

        //set title
        System.out.println(source.getName().toString());

        try {
            articleAPI = articleAPI + "?" + "source=" + URLEncoder.encode(sourceID_n, "UTF-8") + "&"
                    + "apiKey=" + URLEncoder.encode(apiKEY, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        //System.out.println(articleAPI);

        try {
            articleList = new GetArticles().execute(articleAPI).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        if (articleList.size() != 0) {

            //populate articles in view
           populateListView(articleList);
            registerClickCallBack();










        } else {

            System.out.println("Not Populated!");
        }
    }

    private void registerClickCallBack() {
        ListView list = (ListView)findViewById(R.id.newsListView);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Article clickedArticle = NewsActivity.this.articleList.get(position);
                currentArticleID = position;
                //String message = "You Clicked on " + clickedArticle.getTitle();
                //System.out.println(message);
                String url = clickedArticle.getUrl();
                System.out.println(url);
                Intent x = new Intent(getApplicationContext(), WebViewActivity.class);
                x.putExtra("url", url);
                startActivity(x);

            }
        });


    }


    private void populateListView(ArrayList<Article> a ){

        ArrayAdapter<Article> adapter = new MyListAdapter();
        ListView list = (ListView)findViewById(R.id.newsListView);
        list.setAdapter(adapter);
    }






    private class MyListAdapter extends ArrayAdapter<Article> {
        public MyListAdapter() {
            super(NewsActivity.this, R.layout.newsview, articleList);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View itemView = convertView;
            if (itemView == null) {
                itemView = getLayoutInflater().inflate(R.layout.newsview, parent, false);
            }

            Article currentArticle = articleList.get(position);
            //fill the information in the view
            //set contact first name
            TextView articleTitle = (TextView) itemView.findViewById(R.id.articleTitle);
            articleTitle.setText("" + currentArticle.getTitle());

            TextView articleAuthor = (TextView) itemView.findViewById(R.id.author);
            articleAuthor.setText("" + currentArticle.getAuthor());

            TextView articleDate = (TextView) itemView.findViewById(R.id.publishedDate);
            articleDate.setText("" + currentArticle.getPublishedAt());

            ImageView articleImage = (ImageView)itemView.findViewById(R.id.urlToImage);
            String urlToImage = currentArticle.getUrlToImage();
            Picasso.with(NewsActivity.this).load(urlToImage).into(articleImage);

            String url = currentArticle.getUrl();

            return itemView;

        }

    }




}




