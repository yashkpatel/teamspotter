package com.example.tejaspatel.log;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Tejas Patel on 25-Jul-17.
 */

public class Published extends Fragment {

    public ListView publishView;
    ArrayList<PostInfo> posts;
    PostAdapter postAdapter;
    String userid;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.published, container, false);

        publishView=(ListView)rootView.findViewById(R.id.postView);

        final User user = SharedPrefManager.getInstance(getContext()).getUser();
        userid=""+user.getId();
        posts = new ArrayList<PostInfo>();

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
                            int teamid=team.getInt("teamid");
                            int touserid=team.getInt("touserid");
                            int fromuserid=team.getInt("fromuserid");
                            String status=team.getString("status");
                            String username=team.getString("username");


                            posts.add(new PostInfo(username,id,teamid,touserid,fromuserid,status));
                            Log.i("json :",""+status+" "+teamid+" "+touserid+" "+username);

                        }
                        postAdapter=new PostAdapter(rootView.getContext(),posts);
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
                params.put("touserid", userid);


                //returing the response
                return requestHandler.sendPostRequest(URLs.URL_LIST_REQUEST, params);
            }
        }

        UserLogin ul = new UserLogin();
        ul.execute();

        if(posts.isEmpty()){
            Log.i("my bad3:","help me");
        }else{
            Log.i("my bad3:","got it");
        }

        return rootView;
    }
}
