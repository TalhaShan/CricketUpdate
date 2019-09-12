package com.example.cricketapp;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private List<Model> modelList;
    private Context context;

    public MyAdapter(List<Model> modelList, Context context) {
        this.modelList = modelList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
       //this method is called when our view holder is created

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row,viewGroup,false);

        return new ViewHolder(view);

    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //this methood will bind the view holder

        final Model model = modelList.get(position);
        holder.team1tv.setText(model.getTeam1());
        holder.team2tv.setText(model.getTeam2());
        holder.matchtypetv.setText(model.getMatchType());
        holder.matchstatustv.setText(model.getMatchStatus());
        holder.datetv.setText(model.getDate());
            //handle item clicks
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String matchId = model.getId();
                final String date = model.getDate();

                //options to display in dialog
                String[] options ={"Match detail","Player List","Match Summary"};

                AlertDialog.Builder builder = new AlertDialog.Builder(v.getRootView().getContext());
                builder.setTitle("Choose Options");
                //set Options to dialog
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if(which==0){
                            //Match Details is clicked

                            Intent intent = new Intent(context,MatchDetailActivity.class);
                            intent.putExtra("match_id",matchId);
                            intent.putExtra("date",date);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(intent);


                        }if(which==1){
                            //Player List is clicked

                            Intent intent = new Intent(context,PlayersActivity.class);
                            intent.putExtra("match_id",matchId);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(intent);
                        }
                        if(which==2){
                            //Match Summary is clicked

                            Intent intent = new Intent(context,MatchSummmaryActivity.class);
                            intent.putExtra("match_id",matchId);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(intent);
                        }

                    }
                });
                builder.create().show();
                Intent intent = new Intent(context,MatchDetailActivity.class);
                intent.putExtra("match_id",matchId);
                intent.putExtra("date",date);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView team1tv,team2tv,matchtypetv,matchstatustv,datetv;
        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //iitalize view object
            team1tv=itemView.findViewById(R.id.team1TV);
            team2tv=itemView.findViewById(R.id.team2TV);
            matchstatustv=itemView.findViewById(R.id.matchstatusTV);
            matchtypetv=itemView.findViewById(R.id.matchTypeTV);
            datetv= itemView.findViewById(R.id.dateTV);
            cardView = itemView.findViewById(R.id.cardview);
            //itemView.findViewById(R.id.recyclerview);




        }
    }
}
