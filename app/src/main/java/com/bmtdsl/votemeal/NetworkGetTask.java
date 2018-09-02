package com.bmtdsl.votemeal;

import android.os.AsyncTask;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;


/**
 * Created by matthew on 13/06/17.
 */

public class NetworkGetTask extends AsyncTask<String, Void, String> {

    String responseFor;
    String userId;
    String charset = "UTF-8";
    ResponseNetworkConnectionCompleteListener mResponseNetworkConnectionCompleteListener;

    public NetworkGetTask(ResponseNetworkConnectionCompleteListener responseNetworkConnectionCompleteListener) {
        this.mResponseNetworkConnectionCompleteListener = responseNetworkConnectionCompleteListener;
    }

    // 1
    @Override
    protected String doInBackground(String... params) {
        try {
            userId = params[2];
            responseFor = params[1];
            return downloadData(params[0]);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    // 2
    @Override
    protected void onPostExecute(String result) {
        try {
            mResponseNetworkConnectionCompleteListener.downloadComplete(Util.getResponseResultFromNetworkResponse(result, responseFor));
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private String downloadData(String urlString) throws IOException {
        InputStream is = null;
        try {
            if(responseFor == "predictions") {
                String query = String.format("id=%s",
                        URLEncoder.encode(userId, charset));
                URL url = new URL(urlString + "?" + query);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.connect();
                is = conn.getInputStream();
                return convertToString(is);
            }
            else{
                URL url = new URL(urlString);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.connect();
                is = conn.getInputStream();
                return convertToString(is);
            }
        } finally{
            if (is != null) {
                is.close();
            }
        }
    }

    private String convertToString(InputStream is) throws IOException {
        BufferedReader r = new BufferedReader(new InputStreamReader(is));
        StringBuilder total = new StringBuilder();
        String line;
        while ((line = r.readLine()) != null) {
            total.append(line);
        }
        return new String(total);
    }
}
