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

import org.json.JSONArray;
import org.json.JSONObject;

public class PlayersActivity extends AppCompatActivity {

    private  String url ="https://cricapi.com/api/fantasySquad?apikey=szRAaR2GOsOFrcs4A3p4Ha0ITW62&unique_id=";

    TextView team1nametv,team2nametv,team1playertv,team2playertv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_players);

        //action bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Players");
        //enable Back butto
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        String uniqueid= intent.getStringExtra("match_id");
        url = url+uniqueid;

        team1nametv = findViewById(R.id.team1Nametv);
        team2nametv = findViewById(R.id.team2Nametv);
        team1playertv = findViewById(R.id.team1Playerstv);
        team2playertv = findViewById(R.id.team2Playerstv);

        //LoadData()
        loadData();

    }

    private void loadData() {

        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Loading...");
        pd.show();

        StringRequest playerrequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
            pd.dismiss();

            try {

                JSONArray squadarray = new JSONObject(response).getJSONArray("squad");

                JSONObject  json0 = squadarray.getJSONObject(0);
                JSONObject  json1 = squadarray.getJSONObject(1);

                //get name of team1 and team2
                String team1Name = json0.getString("name");
                String team2Name = json1.getString("name");

                JSONArray team1Array = json0.getJSONArray("players");
                JSONArray team2Array = json1.getJSONArray("players");

                team1nametv.setText(team1Name);
                team2nametv.setText(team2Name);

                for(int i=0; i<team1Array.length(); i++){

                    String team1 = team1Array.getJSONObject(i).getString("name");
                    team1playertv.append((i+1) + ") " + team1 + "\n");
                }

                for (int i=0; i<team2Array.length(); i++){

                    String team2 = team2Array.getJSONObject(i).getString("name");
                    team2playertv.append((i+1) + ") " +  team2 + "\n");

                }




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
        requestQueue.add(playerrequest);

    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();//goto previous activity
        return super.onSupportNavigateUp();
    }
}
