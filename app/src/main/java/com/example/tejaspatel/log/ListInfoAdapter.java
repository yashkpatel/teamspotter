package com.example.tejaspatel.log;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

import static android.support.v4.content.ContextCompat.startActivity;

/**
 * Created by Tejas Patel on 24-Jul-17.
 */

public class ListInfoAdapter extends ArrayAdapter<ListInfo> {
    Context context;
    public ListInfoAdapter(Context context,List<ListInfo> listInfoList){
        super(context,0,listInfoList);
        this.context = context;
    }

    TextView username;
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view=convertView;

        if(view == null){
            view= LayoutInflater.from(getContext()).inflate(R.layout.layout_listview,parent,false);
        }

        final ListInfo listInfo=getItem(position);

        TextView userName=(TextView)view.findViewById(R.id.txt_user);
        userName.setText(listInfo.getUserName());

        LinearLayout need=(LinearLayout)view.findViewById(R.id.content);
        TextView need1=(TextView)need.findViewById(R.id.need);
        need1.setText(listInfo.getNeed());

        TextView gameName=(TextView)need.findViewById(R.id.txt_game);
        gameName.setText(listInfo.getGameName()+"\n"+listInfo.getLocationname());

        TextView date= (TextView) view.findViewById(R.id.time);
        date.setText(listInfo.getDate());

        final Button send=(Button) view.findViewById(R.id.send);
        send.setOnClickListener(new View.OnClickListener() {
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
                                 Toast.makeText(getContext(), " Request Sent Fail in Android..", Toast.LENGTH_SHORT).show();
                                send.setEnabled(false);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    protected String doInBackground(Void... voids) {
                        //creating request handler object
                        RequestHandler requestHandler = new RequestHandler();

                        User user = SharedPrefManager.getInstance(getContext()).getUser();
                        String fromuserid=""+user.getId();
                        String touserid=""+listInfo.getUserid();
                        String teamid=""+listInfo.getId();
                        //creating request parameters
                        HashMap<String, String> params = new HashMap<>();
                        params.put("touserid", ""+touserid);
                        params.put("fromuserid", ""+fromuserid);
                        params.put("teamid", ""+teamid);
                        //returing the response
                        return requestHandler.sendPostRequest(URLs.URL_SEND_REQUEST, params);
                    }
                }

                UserLogin ul = new UserLogin();
                ul.execute();
            }
        });

        username = (TextView)view.findViewById(R.id.txt_user);
        username.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,userProfile.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("userId",listInfo.getUserid());
                context.startActivity(intent);

            }
        });
        return view;
    }
}
