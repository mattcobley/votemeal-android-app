package com.bmtdsl.votemeal;

import android.graphics.Bitmap;
import android.os.AsyncTask;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * Created by matthew on 13/06/17.
 */

public class NetworkLoginPostTask extends AsyncTask<String, Void, String> {

    String attachmentName = "image";
    String attachmentFileName = "image.bmp";
    String crlf = "\r\n";
    String twoHyphens = "--";
    String boundary =  "*****";
    LoginNetworkConnectionCompleteListener mLoginNetworkConnectionCompleteListener;
    Bitmap imageBitmap;

    public NetworkLoginPostTask(LoginNetworkConnectionCompleteListener loginNetworkConnectionCompleteListener, Bitmap image) {
        this.mLoginNetworkConnectionCompleteListener = loginNetworkConnectionCompleteListener;
        this.imageBitmap = image;
    }

    @Override
    protected String doInBackground(String... params) {
        try {
            return downloadData(params[0]);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(String result) {
        try {
            mLoginNetworkConnectionCompleteListener.downloadComplete(Util.getLoginResultFromNetworkResponse(result));
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private String downloadData(String urlString) throws IOException {
        InputStream is = null;
        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setUseCaches(false);
            conn.setDoOutput(true);

            conn.setRequestMethod("POST");
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("Cache-Control", "no-cache");
            conn.setRequestProperty(
                    "Content-Type", "multipart/form-data;boundary=" + this.boundary);

            DataOutputStream request = new DataOutputStream(
                    conn.getOutputStream());

            request.writeBytes(this.twoHyphens + this.boundary + this.crlf);
            request.writeBytes("Content-Disposition: form-data; name=\"" +
                    this.attachmentName + "\";filename=\"" +
                    this.attachmentFileName + "\""
                     + this.crlf);
            request.writeBytes(this.crlf);

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] pixels = stream.toByteArray();

            request.write(pixels);
            request.writeBytes(this.crlf);
            request.writeBytes(this.twoHyphens + this.boundary +
                    this.twoHyphens + this.crlf);
            request.flush();
            request.close();

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
