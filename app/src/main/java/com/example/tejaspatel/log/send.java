package com.example.tejaspatel.log;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import static android.R.attr.startYear;
//import static com.example.tejaspatel.log.R.id.datepicker;
import static com.example.tejaspatel.log.R.id.editText;
//import static com.example.tejaspatel.log.R.id.user;
import static java.security.AccessController.getContext;


public class send extends AppCompatActivity {

    EditText need, teamname, date, mob;
    ImageButton datebtn;
    String pneed, tname, tdate, mobile, locationame, sportname, userid, getTdate;
    Spinner location, sportl;
    Button post, clear;
    ArrayList<String> locationArray, sportArray;
    private DatePickerDialog datePicker;
    private SimpleDateFormat dateFormatter;
    private int year, month, day;

    String dateSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send);
        Bundle bun = getIntent().getExtras();
        userid = "" + bun.getInt("ID");
        Log.i("UserId:", userid);
        post = (Button) findViewById(R.id.post);
        clear = (Button) findViewById(R.id.clear);
        location = (Spinner) findViewById(R.id.location);
        sportl = (Spinner) findViewById(R.id.sport);
        locationArray = new ArrayList<>();
        sportArray = new ArrayList<>();
        teamname = (EditText) findViewById(R.id.teamname);
        need = (EditText) findViewById(R.id.need);

        //  dateFormatter = new SimpleDateFormat("dd-MM-yyyy");


        //datePicker = new DatePickerDialog(getContext(),send.this,year,)
        //datePicker = (DatePickerDialog) findViewById(R.id.datepicker);             //yash
        date = (EditText) findViewById(editText);
        datebtn = (ImageButton) findViewById(R.id.datebtn);

        datebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                year = c.get(Calendar.YEAR);
                month = c.get(Calendar.MONTH);
                day = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePicker = new DatePickerDialog(send.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year1,
                                                  int monthOfYear, int dayOfMonth) {


                                date.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year1);

                                dateSelected = year1+"-"+monthOfYear+"-"+dayOfMonth;
                            }
                        }, year, month, day);


                datePicker.show();
            }
        });




        /*datePicker.init(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth(), new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker datePicker, int i, int i1, int i2) {
                String day = Integer.toString(i);
                String month = Integer.toString(i1);
                String year = Integer.toString(i2);

                date.setText(day+" / "+month+" / "+year);
            }
        });*/


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

                        JSONArray area = obj.getJSONArray("area");
                        JSONArray sport = obj.getJSONArray("sport");

                        for (int i = 0; area.length() > i; i++) {
                            JSONObject team = area.getJSONObject(i);

                            String name = team.getString("name");
                            locationArray.add(name);
                            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(send.this, android.R.layout.simple_spinner_item, locationArray); //selected item will look like a spinner set from XML
                            //spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
                            location.setAdapter(spinnerArrayAdapter);
                        }
                        for (int i = 0; sport.length() > i; i++) {
                            JSONObject team = sport.getJSONObject(i);

                            String name = team.getString("name");
                            sportArray.add(name);
                            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(send.this, android.R.layout.simple_list_item_1, sportArray); //selected item will look like a spinner set from XML
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


        Log.i("listtttttttttAbove:", tname + userid + locationame + sportname + pneed);
        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tname = teamname.getText().toString();
                pneed = need.getText().toString();
                locationame = (String) location.getSelectedItem();
                sportname = (String) sportl.getSelectedItem();
                class UserLogin1 extends AsyncTask<Void, Void, String> {

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
                                String msg = obj.getString("message");
                                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();

                            } else {

                                Toast.makeText(getApplicationContext(), "error posting team", Toast.LENGTH_SHORT).show();
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
                        params.put("teamname", tname);
                        params.put("locationname", locationame);
                        params.put("sportname", sportname);
                        params.put("playerneed", pneed);
                        params.put("date",dateSelected);
                        Log.i("listttttttttt:", tname + userid + locationame + sportname + pneed);

                        //returing the response
                        return requestHandler.sendPostRequest(URLs.URL_POST_TEAM, params);
                    }
                }

                UserLogin1 ul = new UserLogin1();
                ul.execute();
            }
        });
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                teamname.setText("");
                need.setText("");
            }
        });


    }
}

