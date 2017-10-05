package com.example.tejaspatel.log;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Tejas Patel on 26-Jul-17.
 */

public class PostAdapter extends ArrayAdapter<PostInfo> {

    Context context;
    public PostAdapter( Context context, List<PostInfo> list) {
        super(context, 0,list);
        this.context = context;
    }

    TextView username;
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view=convertView;
        if(view == null){
            view= LayoutInflater.from(getContext()).inflate(R.layout.view_layout,parent,false);
        }

        final PostInfo postInfo=getItem(position);


        TextView add=(TextView)view.findViewById(R.id.txt_user);
        add.setText(postInfo.getUname());

        Button accept=(Button) view.findViewById(R.id.accept);
        Button cancel=(Button) view.findViewById(R.id.cancel);

        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                class UserLogin extends AsyncTask<Void, Void, String> {

                    ProgressBar progressBar;

                    @Override
                    protected void onPreExecute() {
                        super.onPreExecute();
//                progressBar = (ProgressBar) rootView.findViewById(R.id.progressBar);
//                progressBar.setVisibility(View.VISIBLE);
                    }

                    @Override
                    protected void onPostExecute(String s) {
                        super.onPostExecute(s);
//                progressBar.setVisibility(View.GONE);


                        try {
                            //converting response to json object
                            JSONObject obj = new JSONObject(s);

                            //if no error in response
                            if (!obj.getBoolean("error")) {
                                Toast.makeText(getContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getContext(), "Fail", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    protected String doInBackground(Void... voids) {
                        //creating request handler object
                        RequestHandler requestHandler = new RequestHandler();



                        HashMap<String, String> params = new HashMap<>();
                        params.put("id", ""+postInfo.getId());
                        params.put("status", ""+2);

                        //returing the response
                        return requestHandler.sendPostRequest(URLs.URL_ACCEPT_REQUEST, params);
                    }
                }

                UserLogin ul = new UserLogin();
                ul.execute();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                class UserLogin extends AsyncTask<Void, Void, String> {

                    ProgressBar progressBar;

                    @Override
                    protected void onPreExecute() {
                        super.onPreExecute();
//                progressBar = (ProgressBar) rootView.findViewById(R.id.progressBar);
//                progressBar.setVisibility(View.VISIBLE);
                    }

                    @Override
                    protected void onPostExecute(String s) {
                        super.onPostExecute(s);
//                progressBar.setVisibility(View.GONE);


                        try {
                            //converting response to json object
                            JSONObject obj = new JSONObject(s);

                            //if no error in response
                            if (!obj.getBoolean("error")) {
                                Toast.makeText(getContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getContext(), "Fail", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    protected String doInBackground(Void... voids) {
                        //creating request handler object
                        RequestHandler requestHandler = new RequestHandler();



                        HashMap<String, String> params = new HashMap<>();
                        params.put("id", ""+postInfo.getId());
                        params.put("status", ""+3);

                        //returing the response
                        return requestHandler.sendPostRequest(URLs.URL_ACCEPT_REQUEST, params);
                    }
                }

                UserLogin ul = new UserLogin();
                ul.execute();
            }
        });

        /*TextView username = (TextView)view.findViewById(R.id.txt_user);         //yash
        username.setOnClickListener(new View.OnClickListener() {                //yash
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext().getApplicationContext(),userProfile.class);
            }
        });*/

        username = (TextView)view.findViewById(R.id.txt_user);
        username.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,userProfile.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("userId",postInfo.getId());
                context.startActivity(intent);

            }
        });




        return view;
    }
}
