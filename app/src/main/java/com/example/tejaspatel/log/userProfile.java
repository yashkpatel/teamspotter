package com.example.tejaspatel.log;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tejaspatel.log.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.HashMap;

/**
 * Created by Dell on 02-10-2017.
 */

public class userProfile extends AppCompatActivity {

    String userid;
    TextView uname,name,gender,location,sport,avgRating;
    RatingBar rate;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_profile);

        Bundle bun= getIntent().getExtras();
        userid=""+bun.getInt("userId");

        name=(TextView)findViewById(R.id.userprofile_textView_name);
        uname=(TextView)findViewById(R.id.userprofile_id);
        gender=(TextView)findViewById(R.id.userprofile_textView_gender);
        location=(TextView)findViewById(R.id.userprofile_textView_location);
        sport=(TextView)findViewById(R.id.userprofile_textView_sport);
        avgRating=(TextView)findViewById(R.id.userprofile_textView_avg);
        rate=(RatingBar)findViewById(R.id.ratingBar);

       rate.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
           @Override
           public void onRatingChanged(RatingBar ratingBar, final float rating, boolean fromUser) {

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

                               JSONObject team= obj.getJSONObject("user");
                               double avgRating1=team.getDouble("avgRating");
                               avgRating.setText(""+avgRating1);
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
                       params.put("val", ""+rating);

                       //returing the response
                       return requestHandler.sendPostRequest(URLs.URL_RATE_USER, params);
                   }
               }

               UserLogin ul = new UserLogin();
               ul.execute();
           }
       });

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

                            JSONObject team= obj.getJSONObject("user");
                            int id=team.getInt("id");
                            String username=team.getString("username");
                            String name1=team.getString("name");
                            String email=team.getString("email");
                            String location1=team.getString("locationname");
                            String sportname1=team.getString("sportname");
                            String gender1=team.getString("gender");
                            int avgRating1=team.getInt("avgRating");

                        Log.i("Opppppsy........",username+name1+location1+sportname1);
                        uname.setText(username);
                        name.setText(name1);
                        gender.setText(gender1);
                        location.setText(location1);
                        sport.setText(sportname1);
                        avgRating.setText(""+avgRating1);



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

                Log.i("Oppppsy userid",userid);

                //returing the response
                return requestHandler.sendPostRequest(URLs.URL_USER_PROFILE, params);
            }
        }

        UserLogin ul = new UserLogin();
        ul.execute();
    }
}
