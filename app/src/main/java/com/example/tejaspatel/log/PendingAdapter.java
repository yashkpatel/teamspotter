package com.example.tejaspatel.log;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import static com.example.tejaspatel.log.R.drawable.btnshape;
import static com.example.tejaspatel.log.R.drawable.btnshape_red;
import static com.example.tejaspatel.log.R.drawable.btnshape_yellow;

/**
 * Created by Tejas Patel on 26-Jul-17.
 */

public class PendingAdapter extends ArrayAdapter<PendingInfo> {
    Context context;

    String userid;

    public PendingAdapter(Context context, List<PendingInfo> list) {

        super(context, 0,list);
        this.context = context;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view=convertView;
        if(view == null){
            view= LayoutInflater.from(getContext()).inflate(R.layout.pendinglayout,parent,false);
        }

        final PendingInfo postInfo=getItem(position);


        userid = ""+postInfo.getUserid();
        TextView username=(TextView)view.findViewById(R.id.txt_user);
        username.setText(postInfo.getUsername().toString());

        TextView detail=(TextView)view.findViewById(R.id.txt_game);
        detail.setText(postInfo.getSportname().toString()+"\n"+postInfo.getLocationname().toString());

        TextView need=(TextView)view.findViewById(R.id.need);
        need.setText(""+postInfo.getNeed());

        TextView date= (TextView) view.findViewById(R.id.time);
        date.setText(postInfo.getDate());

        Button accept=(Button) view.findViewById(R.id.accept);
        if(postInfo.getStatus().equals("2")){
            accept.setText("ACCEPTED");
            accept.setBackground(getContext().getResources().getDrawable(R.drawable.btnshape));
        }
        else if(postInfo.getStatus().equals("1")){
            accept.setText("QUEUED");
            accept.setTextColor(Color.parseColor("#FFC107"));
            accept.setBackground(getContext().getResources().getDrawable(R.drawable.btnshape_yellow));
        }
        else if(postInfo.getStatus().equals("3")){
            accept.setText("CANCELLED");
            accept.setTextColor(Color.parseColor("#F44336"));
            accept.setBackground(getContext().getResources().getDrawable(R.drawable.btnshape_red));
        }

        username = (TextView)view.findViewById(R.id.txt_user);
        username.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,userProfile.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("userId",userid);
                context.startActivity(intent);

            }
        });

        username = (TextView)view.findViewById(R.id.txt_user);
        username.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,userProfile.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("userId",postInfo.getUserid());
                context.startActivity(intent);

            }
        });

        return view;
    }
}
