package com.bmtdsl.votemeal;

import android.os.AsyncTask;

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

public class NetworkVotePostTask extends AsyncTask<String, Void, String> {

    String crlf = "\r\n";
    String twoHyphens = "--";
    String boundary =  "*****";
    VoteNetworkConnectionCompleteListener mVoteNetworkConnectionCompleteListener;
    String vote;
    String charset = "UTF-8";
    String userId;

    public NetworkVotePostTask(VoteNetworkConnectionCompleteListener voteNetworkConnectionCompleteListener) {
        this.mVoteNetworkConnectionCompleteListener = voteNetworkConnectionCompleteListener;
    }

    // 1
    @Override
    protected String doInBackground(String... params) {
        try {
            userId = params[2];
            vote = params[1];
            return downloadData(params[0]);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    // 2
    @Override
    protected void onPostExecute(String result) {
        //TODO some check here to see if vote stored successfully.
        mVoteNetworkConnectionCompleteListener.downloadComplete(true);
    }

    private String downloadData(String urlString) throws IOException {
        InputStream is = null;
        try {
            String query = String.format("party=%s;id=%s",
                    URLEncoder.encode(vote, charset), URLEncoder.encode(userId, charset));
            URL url = new URL(urlString + "?" + query);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.connect();
            is = conn.getInputStream();
            return convertToString(is);

        } catch(Exception ex) {
            String test = "";
            return "";
        }finally {
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
