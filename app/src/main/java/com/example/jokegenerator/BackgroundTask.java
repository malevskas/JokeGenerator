package com.example.jokegenerator;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

public class BackgroundTask extends AsyncTask<Void, Void, String> {

    public Context context;
    BackgroundTask(Context context) { this.context = context; }

    @Override
    protected String doInBackground(Void... voids) {
        return ConnectToAPI.Connect();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        try {
            JSONObject object = new JSONObject(s);
            String joke = "";
            if(object.getString("type").equals("single")) {
                joke = object.getString("joke");
            }
            if(object.getString("type").equals("twopart")) {
                joke = object.getString("setup") + "\n" + object.getString("delivery");
            }
            new CreateNotification(context).createNotificationChannel(joke);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
