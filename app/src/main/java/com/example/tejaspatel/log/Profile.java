package com.example.tejaspatel.log;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

//import static com.example.tejaspatel.log.R.id.user;

/**
 * Created by Tejas Patel on 25-Jul-17.
 */

public class Profile extends Fragment {

    TextView textViewCreated,textViewUsername,textViewJoined,textViewGender;
    Button editprofile;

    private  String username;
    private int userid;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.profile, container, false);

        editprofile= (Button) rootView.findViewById(R.id.editprofile) ;
        final User user = SharedPrefManager.getInstance(getContext()).getUser();

        editprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent edit=new Intent(getContext(),EditProfile.class);
                edit.putExtra("ID",user.getId());
                edit.putExtra("name",user.getName());
                edit.putExtra("username",user.getUsername());
                edit.putExtra("email",user.getEmail());
                edit.putExtra("mobileno",user.getMobileno());
                edit.putExtra("gender",user.getGender());
                startActivity(edit);
            }
        });

        //setting the values to the textviews
        textViewUsername=(TextView)rootView.findViewById(R.id.username1);
        textViewCreated=(TextView)rootView.findViewById(R.id.created);
        textViewJoined=(TextView)rootView.findViewById(R.id.joined);
       // textViewId.setText(String.valueOf(user.getId()));
        textViewUsername.setText(""+user.getId());
        username=user.getUsername().toString();
        userid=user.getId();
//        textViewEmail.setText(user.getEmail());
//        textViewGender.setText(user.getGender());
        class UserLogin extends AsyncTask<Void, Void, String> {

            ProgressBar progressBar;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
//                progressBar = (ProgressBar) findViewById(R.id.progressBar);
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

                        JSONArray user=obj.getJSONArray("user");

                        JSONObject myuser=user.getJSONObject(0);
                        int accept=myuser.getInt("accept");
                        int send=myuser.getInt("send");
                        textViewCreated.setText(""+accept);
                        textViewJoined.setText(""+send);


                    } else {
                        Toast.makeText(getContext(), " ohho", Toast.LENGTH_SHORT).show();
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
                params.put("userid", ""+userid);


                //returing the response
                return requestHandler.sendPostRequest(URLs.URL_PROFILE, params);
            }
        }

        UserLogin ul = new UserLogin();
        ul.execute();
        return rootView;
    }
}
