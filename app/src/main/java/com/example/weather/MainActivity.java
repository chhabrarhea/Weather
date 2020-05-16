package com.example.weather;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;

public class MainActivity extends AppCompatActivity {
    public void getWeather(View view)
    {
        EditText e=(EditText) findViewById(R.id.e);
        String l= null;
        try {
            l = URLEncoder.encode(e.getText().toString(),"UTF-8");
        } catch (UnsupportedEncodingException ex) {
            ex.printStackTrace();
        }
        weather w=new weather();
        w.execute("http://openweathermap.org/data/2.5/weather?q="+l+"&appid=439d4b804bc8187953eb36d2a8c26a02");
        InputMethodManager in=(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        in.hideSoftInputFromWindow(e.getWindowToken(),0);
    }

    public class weather extends AsyncTask<String,Void,String>
    {
        TextView t1=(TextView) findViewById(R.id.main);
        TextView t2=(TextView) findViewById(R.id.desc);
        @Override
        protected String doInBackground(String... strings) {
            URL url;
            HttpURLConnection h=null;
            try {
                url=new URL(strings[0]);
                h=(HttpURLConnection) url.openConnection();
                InputStream i=h.getInputStream();
                InputStreamReader r=new InputStreamReader(i);
                int data=r.read();
                String result="";
                while(data!=-1)
                {
                    char c=(char)data;
                    result+=c;
                    data=r.read();
                }


            } catch (Exception e) {
                e.printStackTrace();

                Toast.makeText(getApplicationContext(), "Invalid City", Toast.LENGTH_SHORT).show();
                return null;
            }


        }

        @Override
        protected void onPostExecute(String s) {

            super.onPostExecute(s);
            try {
                JSONObject j=new JSONObject(s);
                String info=j.getString("weather");
                JSONArray arr=new JSONArray(info);
                for(int i=0;i<arr.length();i++)
                {
                    JSONObject part=arr.getJSONObject(i);
                    t1.setText(part.getString("main"));
                    t2.setText(part.getString("description"));

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ConstraintLayout ll=(ConstraintLayout) findViewById(R.id.ll);
        ll.setBackgroundResource(R.drawable.yoyo);

    }
}
