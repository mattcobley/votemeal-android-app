package com.bmtdsl.votemeal;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import org.json.JSONException;

import java.io.IOException;

public class PhotoConfirmationActivity extends AppCompatActivity implements LoginNetworkConnectionCompleteListener {

    Bitmap imageBitmap;
    ProgressDialog mProgressDialog;
    Uri imageUri;
    String loginUrl = "http://votemeal.spess-whale.co.uk/login"; //"http://52.233.195.249:3000/upload";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_confirmation);

        Bundle extras = getIntent().getExtras();
        imageUri = (Uri)extras.get("output");
        try {
            imageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
            ImageView photoConfirmationImage = (ImageView) findViewById(R.id.photoConfirmationImage);
            photoConfirmationImage.setImageBitmap(imageBitmap);
        }
        catch(IOException ex){
            ex.printStackTrace();
        }
    }

    public void onConfirmationClick(View view) throws JSONException{

        if (isNetworkConnected()) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage("Please wait...");
            mProgressDialog.setCancelable(false);
            mProgressDialog.show();

            startConnection();
        } else {
            new AlertDialog.Builder(this)
                .setTitle("No Internet Connection")
                .setMessage("It looks like your internet connection is off. Please turn it " +
                        "on and try again")
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).setIcon(android.R.drawable.ic_dialog_alert).show();
        }

    }

    private boolean isNetworkConnected() {
        ConnectivityManager connMgr = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    private void startConnection() {
        new NetworkLoginPostTask(this, imageBitmap).execute(loginUrl);
    }

    public void onRejectClick(View view){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    @Override
    public void downloadComplete(UserDetails userDetails) {
        if (mProgressDialog != null) {
            mProgressDialog.hide();
        }

        if(userDetails.isSuccessfulMatch()) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("userId", userDetails.getUserId());
            intent.putExtra("name", userDetails.getName());
            startActivity(intent);
        }
        else{
            Intent intent = new Intent(this, LoginActivity.class);
            intent.putExtra("login_failed", "true");
            startActivity(intent);
        }
    }
}
