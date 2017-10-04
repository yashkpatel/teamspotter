package com.example.tejaspatel.log;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.tejaspatel.log.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by Dell on 02-10-2017.
 */

public class userProfile extends AppCompatActivity {

    String userid;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_profile);

        Bundle bun= getIntent().getExtras();
        userid=""+bun.getInt("ID");

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
                        Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();

                        //getting the user from the response
                        JSONArray arrayTEAM= obj.getJSONArray("request");

                        for(int i=0;arrayTEAM.length()>i;i++){
                            JSONObject team= arrayTEAM.getJSONObject(i);
                            int id=team.getInt("id");
                            String username=team.getString("username");
                            String name=team.getString("name");
                            String sportname=team.getString("sportname");
                            String gender=team.getString("gender");
                            int avgRating=team.getInt("avgRating");

                        }



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
                return requestHandler.sendPostRequest(URLs.URL_USER_PROFILE, params);
            }
        }

        UserLogin ul = new UserLogin();
        ul.execute();
    }
}
