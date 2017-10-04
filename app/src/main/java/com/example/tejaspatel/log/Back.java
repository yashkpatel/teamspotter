package com.example.tejaspatel.log;

import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.webkit.HttpAuthHandler;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.Buffer;

/**
 * Created by Tejas Patel on 14-Sep-17.
 */

public class Back extends AsyncTask<String,Void,String> {

    private Context context;
    AlertDialog alertDialog;
    public Back(Context context) {
        this.context = context;
    }

    @Override
    protected String doInBackground(String... params) {
        String type=params[0];
        String username=params[1];
        String password=params[2];
        String login_URL="http://tp010297.000webhostapp.com/login.php";

        if(type.equals("login")){
            try {
                URL url=new URL(login_URL);
                HttpURLConnection httpURLConnection=(HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                httpURLConnection.setDoOutput(true);
                OutputStream outputStream=httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));

                String post_data= URLEncoder.encode("username","UTF-8")+"="+URLEncoder.encode(username,"UTF-8")+"&"
                        + URLEncoder.encode("password","UTF-8")+"="+URLEncoder.encode(password,"UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream=httpURLConnection.getInputStream();
                BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                String result="";
                String line="";
                while ( (line=bufferedReader.readLine()) != null){
                    result+= line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                Log.i("conn",url.toString());
                String name = null;
                int age=0;
                String city=null;
                try {
                    JSONObject root = new JSONObject(result);
                    name=root.getString("name");
                   age=root.getInt("age");
                    city=root.getString("city");
                    Log.i("Name:",name);
                    Log.i("Age:",""+age);
                    Log.i("City:",city);
                } catch (JSONException e) {

                    e.printStackTrace();
                }

                return result;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        alertDialog= new AlertDialog.Builder(context).create();
        alertDialog.setTitle("Login Status:");
    }

    @Override
    protected void onPostExecute(String result) {
        alertDialog.setMessage(result);
        alertDialog.show();


    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}
