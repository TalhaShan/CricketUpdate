package com.example.cricketapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class MatchDetailActivity extends AppCompatActivity {

    TextView mTeam1tv,mTeam2tv,mMatchStatusTv,mScoreTv,mDescriptionTv,mDateTv;
    private  String Url ="https://cricapi.com/api/cricketScore?apikey=szRAaR2GOsOFrcs4A3p4Ha0ITW62&unique_id=";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_detail);
        //action bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Match Details");
        //enable Back butto
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        String id = intent.getStringExtra("match_id");
        String date = intent.getStringExtra("date");
        Url = Url+id;

        mTeam1tv = findViewById(R.id.team1TV);
        mTeam2tv = findViewById(R.id.team2TV);
        mMatchStatusTv = findViewById(R.id.matchstautsTVm);
        mScoreTv = findViewById(R.id.matchscoreTVm);
        mDescriptionTv = findViewById(R.id.matchdescriptionTVm);
        mDateTv = findViewById(R.id.dateTV);

        mDateTv.setText(date);

        loadData();
    }

    private void loadData() {
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Loading...");
        pd.show();

        StringRequest detailsrequest = new StringRequest(Request.Method.GET, Url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                pd.dismiss();
                try {

                    JSONObject jsonObject = new JSONObject(response);
                    String team1 = jsonObject.getString("team-1");
                    String team2 = jsonObject.getString("team-2");
                    String matchStatus = jsonObject.getString("matchStarted");
                        if(matchStatus.equals("true")){
                            matchStatus ="Match Started";

                        }else {
                            matchStatus ="Match yet to Start..";
                        }

                        mTeam1tv.setText(team1);
                        mTeam2tv.setText(team2);
                        mMatchStatusTv.setText(matchStatus);

                        //These values will only be received if the match has been starte so thatswhy keeping int itsperate block
                        try {

                            String score = jsonObject.getString("score");
                            String description = jsonObject.getString("description");

                            mScoreTv.setText(score);
                            mDescriptionTv.setText(description);

                        }catch (Exception e){
                            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                        }

                }catch (Exception e){
                    Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"Error: "+ error.toString(),Toast.LENGTH_LONG).show();
                    error.printStackTrace();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(detailsrequest);
    }

    @Override
        public boolean onSupportNavigateUp() {
        onBackPressed();//goto previous activity
        return super.onSupportNavigateUp();
    }
}
