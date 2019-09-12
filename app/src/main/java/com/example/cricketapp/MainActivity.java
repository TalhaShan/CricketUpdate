package com.example.cricketapp;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerview;
    public  String Url = "https://cricapi.com/api/matches?apikey=szRAaR2GOsOFrcs4A3p4Ha0ITW62";

    private  RecyclerView.Adapter mAdapter;
    private List<Model> modelList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerview = findViewById(R.id.recyclerview);
        mRecyclerview.setHasFixedSize(true);
        mRecyclerview.setLayoutManager(new LinearLayoutManager(this));

        modelList = new ArrayList<>();

        //function to call data
        loadURLdata();

    }

    private void loadURLdata() {
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Loading...");
        pd.show();

        //requesting data so requesting using Get methid
        StringRequest request = new StringRequest(Request.Method.GET, Url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                pd.dismiss();
                //json feteching
                try {
                    JSONArray jsonArray = new JSONObject(response).getJSONArray("matches");
                    for(int i =0; i<jsonArray.length(); i++){
                        try{

                            String unique_id= jsonArray.getJSONObject(i).getString("unique_id");
                            String team1= jsonArray.getJSONObject(i).getString("team-1");
                            String team2= jsonArray.getJSONObject(i).getString("team-2");
                            String matchtype= jsonArray.getJSONObject(i).getString("type");
                            String matchstatus= jsonArray.getJSONObject(i).getString("matchStarted");

                            if(matchstatus.equals("true")){

                                matchstatus="Match Started";

                            } else {
                                matchstatus="Yet to start...";
                            }

                            String datetimeGmt=  jsonArray.getJSONObject(i).getString("dateTimeGMT");

                            SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                            format1.setTimeZone(TimeZone.getTimeZone(datetimeGmt));
                            Date date = format1.parse(datetimeGmt);

                            //conver to dd//mm/yyyy HH:MM

                            SimpleDateFormat format2 = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                            format2.setTimeZone(TimeZone.getTimeZone("GMT"));
                            String datetime = format2.format(date);

                            //set Data
                            Model model = new Model(unique_id,team1,team2,matchtype,matchstatus,datetime);
                            modelList.add(model);

                        }catch (Exception e){

                            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                        }
                    }

                    mAdapter = new MyAdapter(modelList,getApplicationContext());
                    mRecyclerview.setAdapter(mAdapter);
                }catch (Exception e){

                    Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"Error: "+ error.toString(),Toast.LENGTH_LONG).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);

    }
}
