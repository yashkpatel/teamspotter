package com.example.tejaspatel.log;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Tejas Patel on 25-Jul-17.
 */


public class Pending extends Fragment {

    public ListView publishView;
    ArrayList<PendingInfo> pends;
    PendingAdapter postAdapter;
    String userid;
    TextView username;
    Activity context;           //yash
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.pending, container, false);

        final User user = SharedPrefManager.getInstance(getContext()).getUser();
        userid=""+user.getId();
        publishView=(ListView)rootView.findViewById(R.id.pendView);

        pends = new ArrayList<PendingInfo>();

        username = (TextView)rootView.findViewById(R.id.txt_user);          //yash

        /*username.setOnClickListener(new View.OnClickListener() {            //yash
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context.getApplicationContext(),userProfile.class);
                startActivity(i);
            }
        });*/


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
                        Toast.makeText(rootView.getContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();

                        //getting the user from the response
                        JSONArray arrayTEAM= obj.getJSONArray("request");

                        for(int i=0;arrayTEAM.length()>i;i++){
                            JSONObject team= arrayTEAM.getJSONObject(i);
                            int id=team.getInt("id");
                            String username=team.getString("username");
                            String sportname=team.getString("sportname");
                            String locationname=team.getString("locationname");
                            String status=team.getString("status");
                            int need=team.getInt("need");
                            String monthName= team.getString("monthname");
                            int day= team.getInt("day");
                            String date= ""+day+" "+monthName;

                            pends.add(new PendingInfo(id,username, sportname, locationname, status, need, date));


                        }
                        postAdapter=new PendingAdapter(rootView.getContext(),pends);
                        publishView.setAdapter(postAdapter);


                    } else {
                        // Toast.makeText(rootView.getContext(), "Invalid username or password", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            protected String doInBackground(Void... voids) {
                //creating request handler object
                RequestHandler requestHandler = new RequestHandler();

                //creating request parameters
                HashMap<String, String> params = new HashMap<>();
                params.put("fromuserid", userid);


                //returing the response
                return requestHandler.sendPostRequest(URLs.URL_LIST_WAITING, params);
            }
        }

        UserLogin ul = new UserLogin();
        ul.execute();

        if(pends.isEmpty()){
            Log.i("my bad3:","help me");
        }else{
            Log.i("my bad3:","got it");
        }


        return rootView;
    }
}
