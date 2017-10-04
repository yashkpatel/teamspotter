package com.example.tejaspatel.log;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import static com.example.tejaspatel.log.R.id.editText;
import static java.security.AccessController.getContext;

public class EditProfile extends AppCompatActivity {

    EditText editTextName,editTextUsername, editTextEmail, editTextMobileno;
    Button save;
    Spinner location,sportl;
    ArrayList<String> locationArray,sportArray;
    RadioGroup radioGroupGender;
    String userid, name, username, email, mobileno, gender;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        Bundle bun= getIntent().getExtras();
        location=(Spinner) findViewById(R.id.location);
        sportl=(Spinner) findViewById(R.id.sport);
        editTextName=(EditText) findViewById(R.id.name);
        editTextUsername=(EditText) findViewById(R.id.Username);
        editTextEmail=(EditText) findViewById(R.id.email);
        editTextMobileno=(EditText) findViewById(R.id.mobileno);
        save=(Button)findViewById(R.id.save);
        userid=""+bun.getInt("ID");
        editTextName.setText(bun.getString("name"));
        editTextUsername.setText(bun.getString("username"));
        editTextEmail.setText(bun.getString("email"));
        editTextMobileno.setText(bun.getString("mobileno"));

        locationArray=new ArrayList<>();
        sportArray=new ArrayList<>();
        class UserLogin extends AsyncTask<Void, Void, String> {

            // ProgressBar progressBar;

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

                        JSONArray area= obj.getJSONArray("area");
                        JSONArray sport= obj.getJSONArray("sport");

                        for(int i=0;area.length()>i;i++){
                            JSONObject team= area.getJSONObject(i);

                            String name=team.getString("name");
                            locationArray.add(name);
                            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(EditProfile.this, android.R.layout.simple_spinner_item, locationArray); //selected item will look like a spinner set from XML
                            //spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
                            location.setAdapter(spinnerArrayAdapter);
                        }
                        for(int i=0;sport.length()>i;i++){
                            JSONObject team= sport.getJSONObject(i);

                            String name=team.getString("name");
                            sportArray.add(name);
                            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(EditProfile.this, android.R.layout.simple_list_item_1, sportArray); //selected item will look like a spinner set from XML
                            //spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
                            sportl.setAdapter(spinnerArrayAdapter);
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



                //returing the response
                return requestHandler.sendPostRequest(URLs.URL_LOCATION, params);
            }
        }

        UserLogin ul = new UserLogin();
        ul.execute();

        radioGroupGender = (RadioGroup) findViewById(R.id.radioGender);
         gender = ((RadioButton) findViewById(radioGroupGender.getCheckedRadioButtonId())).getText().toString();
        if((bun.getString("gender")).equals("male")){
            ( (RadioButton) findViewById(R.id.radioButtonMale)).setSelected(true);
        }else {
                ( (RadioButton) findViewById(R.id.radioButtonFemale)).setSelected(true);
        }
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name=editTextName.getText().toString();
                username=editTextUsername.getText().toString();
                email=editTextEmail.getText().toString();
                mobileno=editTextMobileno.getText().toString();
                gender = ((RadioButton) findViewById(radioGroupGender.getCheckedRadioButtonId())).getText().toString();
                final String interestedlocation=(String)location.getSelectedItem();
                final String interestedsport=(String)sportl.getSelectedItem();
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
                            } else {
                                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
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
                        params.put("id",userid);
                        params.put("username",username);
                        params.put("name",name);
                        params.put("email",email);
                        params.put("mobileno",mobileno);
                        params.put("gender",gender);
                        params.put("interestedlocation",interestedlocation);
                        params.put("interestedsport",interestedsport);

                        //returing the response
                        return requestHandler.sendPostRequest(URLs.URL_EDIT_USER, params);
                    }
                }

                UserLogin ul = new UserLogin();
                ul.execute();
            }
        });

    }
}
