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

public class MatchSummmaryActivity extends AppCompatActivity {

    String url="https://cricapi.com/api/fantasySummary?apikey=szRAaR2GOsOFrcs4A3p4Ha0ITW62&unique_id=";

    TextView fieldT1titletv,fieldT2titletv,fieldT1detailtv,fieldT2detailtv;
    TextView bowlT1titletv,bowlT2titletv,bowlT1detailtv,bowlT2detailtv;
    TextView batT1titletv,batT2titletv,batT1detailtv,batT2detailtv,otherResultstv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_summmary);

        //action bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Match Summary");
        //enable Back butto
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        String uniqueid= intent.getStringExtra("match_id");
        url = url+uniqueid;


        fieldT1detailtv = findViewById(R.id.fieldT1Detailtv);
        fieldT2detailtv = findViewById(R.id.fieldT2Detailtv);
        fieldT1titletv  = findViewById(R.id.fieldT1titletv);
        fieldT2titletv  = findViewById(R.id.fieldT2titletv);

        bowlT1detailtv = findViewById(R.id.bowlT1Detailtv);
        bowlT2detailtv = findViewById(R.id.bowlT2Detailtv);
        bowlT1titletv  = findViewById(R.id.bowlT1titletv);
        bowlT2titletv  = findViewById(R.id.bowlT2titletv);

        batT1detailtv = findViewById(R.id.batT1Detailtv);
        batT2detailtv = findViewById(R.id.batT2Detailtv);
        batT1titletv  = findViewById(R.id.batT1titletv);
        batT2titletv  = findViewById(R.id.batT2titletv);

        otherResultstv = findViewById(R.id.otherResulttv);
        loadData();
    }

    private void loadData() {

        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Loading...");
        pd.show();

        StringRequest fieldingrequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                pd.dismiss();
                try {

                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject dataObject= jsonObject.getJSONObject("data");

                    JSONArray fieldArray = dataObject.getJSONArray("fielding");
                    JSONArray bowlArray = dataObject.getJSONArray("bowling");

                    JSONArray batArray = dataObject.getJSONArray("batting");


                    JSONObject field0=fieldArray.getJSONObject(0);
                    JSONObject field1= fieldArray.getJSONObject(1);

                    String field1title = field0.getString("title");
                    String field2title = field1.getString("title");

                    JSONArray field1ScoreArray = field0.getJSONArray("scores");
                    JSONArray field2ScoreArray = field1.getJSONArray("scores");

                    //geeting fielding summart

                    fieldT1titletv.setText(field1title);

                    for(int i=0; i<field1ScoreArray.length(); i++){

                        String name    = field1ScoreArray.getJSONObject(i).getString("name");
                        String bowled  = field1ScoreArray.getJSONObject(i).getString("bowled");
                        String catchh  = field1ScoreArray.getJSONObject(i).getString("catch");
                        String lbw     = field1ScoreArray.getJSONObject(i).getString("lbw");
                        String runout  = field1ScoreArray.getJSONObject(i).getString("runout");
                        String stumped = field1ScoreArray.getJSONObject(i).getString("stumped");

                        //set Data

                        fieldT1detailtv.append("Name: "+name
                                +"\nBowled: "  + bowled
                                +"\nCatch: "   + catchh
                                +"\nLbw: "     + lbw
                                +"\nRunout: "  + runout
                                +"\nStumped: " + stumped +"\n\n"
                        );

                    }

                    fieldT2titletv.setText(field2title);

                    for(int i=0; i<field2ScoreArray.length(); i++){

                        String name    = field2ScoreArray.getJSONObject(i).getString("name");
                        String bowled  = field2ScoreArray.getJSONObject(i).getString("bowled");
                        String catchh  = field2ScoreArray.getJSONObject(i).getString("catch");
                        String lbw     = field2ScoreArray.getJSONObject(i).getString("lbw");
                        String runout  = field2ScoreArray.getJSONObject(i).getString("runout");
                        String stumped = field2ScoreArray.getJSONObject(i).getString("stumped");

                        //set Data

                        fieldT2detailtv.append("Name: "+name
                                +"\nBowled: "  + bowled
                                +"\nCatch: "   + catchh
                                +"\nLbw: "     + lbw
                                +"\nRunout: "  + runout
                                +"\nStumped: " + stumped +"\n\n"
                        );

                    }

                    JSONObject bowl0 = bowlArray.getJSONObject(0);
                    JSONObject bowl1 = bowlArray.getJSONObject(1);

                    String bowl1title = bowl0.getString("title");
                    String bowl2title = bowl1.getString("title");

                    JSONArray bowl1ScoreArray = bowl0.getJSONArray("scores");
                    JSONArray bowl2ScoreArray = bowl1.getJSONArray("scores");

                    bowlT1titletv.setText(bowl1title);

                    for(int i = 0; i<bowl1ScoreArray.length(); i++){

                        String bowlername = bowl1ScoreArray.getJSONObject(i).getString("bowler");
                        String overs = bowl1ScoreArray.getJSONObject(i).getString("O");
                        String wicket = bowl1ScoreArray.getJSONObject(i).getString("W");
                        String  runs = bowl1ScoreArray.getJSONObject(i).getString("R");
                        String zeroes = bowl1ScoreArray.getJSONObject(i).getString("0s");
                        String fours = bowl1ScoreArray.getJSONObject(i).getString("4s");
                        String sixes = bowl1ScoreArray.getJSONObject(i).getString("6s");
                        String m = bowl1ScoreArray.getJSONObject(i).getString("M");
                        String econ = bowl1ScoreArray.getJSONObject(i).getString("Econ");


                        bowlT1detailtv.append("Name: "+bowlername
                                        +"\nOvers: "+overs
                                        +"\nWickets: "+wicket
                                        +"\nRuns: "+runs
                                        +"\nZeroes: "+zeroes
                                        +"\nFours: "+fours
                                        +"\nSixes: "+sixes
                                        +"\nMaiden: "+m
                                        +"\nEconomy: "+econ
                                        +"\n\n"

                                );

                    }

                    bowlT2titletv.setText(bowl2title);

                    for(int i = 0; i<bowl2ScoreArray.length(); i++){

                        String bowlername = bowl2ScoreArray.getJSONObject(i).getString("bowler");
                        String overs = bowl2ScoreArray.getJSONObject(i).getString("O");
                        String wicket = bowl2ScoreArray.getJSONObject(i).getString("W");
                        String  runs = bowl2ScoreArray.getJSONObject(i).getString("R");
                        String zeroes = bowl2ScoreArray.getJSONObject(i).getString("0s");
                        String fours = bowl2ScoreArray.getJSONObject(i).getString("4s");
                        String sixes = bowl2ScoreArray.getJSONObject(i).getString("6s");
                        String m = bowl2ScoreArray.getJSONObject(i).getString("M");
                        String econ = bowl2ScoreArray.getJSONObject(i).getString("Econ");


                        bowlT2detailtv.append("Name: "+bowlername
                                +"\nOvers: "+overs
                                +"\nWickets: "+wicket
                                +"\nRuns: "+runs
                                +"\nZeroes: "+zeroes
                                +"\nFours: "+fours
                                +"\nSixes: "+sixes
                                +"\nMaiden: "+m
                                +"\nEconomy: "+econ
                                +"\n\n"

                        );
                    }

                    JSONObject bat0= batArray.getJSONObject(0);
                    JSONObject bat1= batArray.getJSONObject(1);

                    String bat1title = bat0.getString("title");
                    String bat2title = bat1.getString("title");

                    JSONArray bat1ScoreArray = bat0.getJSONArray("scores");
                    JSONArray bat2ScoreArray = bat1.getJSONArray("scores");

                    for(int i =0 ; i< bat1ScoreArray.length(); i++){

                        String batsman = bat1ScoreArray.getJSONObject(i).getString("batsman");
                        String ballb = bat1ScoreArray.getJSONObject(i).getString("B");
                        String runsb = bat1ScoreArray.getJSONObject(i).getString("R");
                        String foursb = bat1ScoreArray.getJSONObject(i).getString("4s");
                        String sixesb = bat1ScoreArray.getJSONObject(i).getString("6s");
                        String strikeRate = bat1ScoreArray.getJSONObject(i).getString("SR");
                        String dismisalInfo = bat1ScoreArray.getJSONObject(i).getString("dismissal-info");
                        String dismissal="" ,dismissedby="";
                        try {
                          dismissal = bat1ScoreArray.getJSONObject(i).getString("dismissal");
                          dismissedby =bat1ScoreArray.getJSONObject(i).getJSONObject("dismissal-by").getString("name");

                        }catch (Exception e){

                            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();

                        }
                        batT1titletv.setText(bat1title);
                        batT1detailtv.append("Batsman: "+batsman
                                        +"\nBalls: "+ ballb
                                        +"\nRuns: "+ runsb
                                        +"\nFours: "+ foursb
                                        +"\nSixes: "+ sixesb
                                        +"\nStrikeRate: "+ strikeRate
                                        +"\nDismissal: "+ dismissal
                                        +"\nDismissal Info: "+ dismisalInfo
                                        +"\nDismissed By: "+ dismissedby
                                        +"\n\n"
                        );


                    }
                        //batting2 summary
                    for(int i =0 ; i< bat2ScoreArray.length(); i++){

                        String batsman = bat2ScoreArray.getJSONObject(i).getString("batsman");
                        String ballb = bat2ScoreArray.getJSONObject(i).getString("B");
                        String runsb = bat2ScoreArray.getJSONObject(i).getString("R");
                        String foursb = bat2ScoreArray.getJSONObject(i).getString("4s");
                        String sixesb = bat2ScoreArray.getJSONObject(i).getString("6s");
                        String strikeRate = bat2ScoreArray.getJSONObject(i).getString("SR");
                        String dismisalInfo = bat2ScoreArray.getJSONObject(i).getString("dismissal-info");
                        String dismissal="" ,dismissedby="";
                        try {
                            dismissal = bat2ScoreArray.getJSONObject(i).getString("dismissal");
                            dismissedby =bat2ScoreArray.getJSONObject(i).getJSONObject("dismissal-by").getString("name");

                        }catch (Exception e){

                            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();

                        }
                        batT2titletv.setText(bat2title);
                        batT2detailtv.append("Batsman: "+batsman
                                +"\nBalls: "+ ballb
                                +"\nRuns: "+ runsb
                                +"\nFours: "+ foursb
                                +"\nSixes: "+ sixesb
                                +"\nStrikeRate: "+ strikeRate
                                +"\nDismissal: "+ dismissal
                                +"\nDismissal Info: "+ dismisalInfo
                                +"\nDismissed By: "+ dismissedby
                                +"\n\n"
                        );

                    }

                     String manOfthematch = "", tossWinner="",winnerTeam="";

                    try {

                        manOfthematch = dataObject.getJSONObject("man-of-the-match").getString("name");


                    }catch (Exception e){
                        Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();

                    }

                    try {

                        tossWinner = dataObject.getString("toss_winner_team");


                    }

                    catch (Exception e){
                        Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();

                    }

                    try {

                        winnerTeam = dataObject.getString("winner_team");


                    }catch (Exception e){
                        Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();

                    }

                                   otherResultstv.setText("Toss Winner: "+tossWinner
                                            +"\nWinner: "+ winnerTeam
                                            +"\nMan of the match: "+ manOfthematch);




                    }catch (Exception e){

                    Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();

                }




            }
        },
                new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"Error: "+ error.toString(),Toast.LENGTH_LONG).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(fieldingrequest);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();//goto previous activity
        return super.onSupportNavigateUp();
    }
}
