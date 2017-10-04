package com.example.tejaspatel.log;

/**
 * Created by Tejas Patel on 25-Jul-17.
 */

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

public class ViewGame extends Fragment{

    public ListView listView;
    public ArrayList<ListInfo> listInfo;
    ListInfoAdapter listInfoAdapter;
    String userid;
    TextView username;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.view1, container, false);

        listView=(ListView)rootView.findViewById(R.id.list_view);
        listInfo=new ArrayList<ListInfo>();
        final User user = SharedPrefManager.getInstance(getContext()).getUser();
        userid=""+user.getId();

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
                        JSONArray arrayTEAM= obj.getJSONArray("user");

                        for(int i=0;arrayTEAM.length()>i;i++){
                            JSONObject team= arrayTEAM.getJSONObject(i);
                            int id=team.getInt("id");
                            String sp=team.getString("sportname");
                            int userid=team.getInt("userid");
                            String uname=team.getString("username");
                            String lname=team.getString("locationname");
                            int pneed= team.getInt("playerneed ");
                            String monthName= team.getString("monthname");
                            int day= team.getInt("day");
                            String date= ""+day+" "+monthName;
                            Log.i("json :",""+id+" "+sp+" "+uname+" "+lname);
                            listInfo.add(new ListInfo(id, userid, uname,sp,lname,pneed, date));
                            Log.i("json :",""+id+" "+sp+" "+uname+" "+lname);

                        }
                        listInfoAdapter=new ListInfoAdapter(rootView.getContext(),listInfo);

                        listView.setAdapter(listInfoAdapter);


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
                params.put("userid", userid);


                //returing the response
                return requestHandler.sendPostRequest(URLs.URL_GET_TEAM, params);
            }
        }

        UserLogin ul = new UserLogin();
        ul.execute();

        if(listInfo.isEmpty()){
            Log.i("my bad3:","help me");
        }else{
            Log.i("my bad3:","got it");
        }




        return rootView;
    }

}
