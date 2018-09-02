package com.bmtdsl.votemeal;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LoginActivity extends AppCompatActivity {

    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int REQUEST_TAKE_PHOTO = 1;
    String mCurrentPhotoPath;
    Uri currentPhotoUri;
    private TextView mTextMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if(!deviceHasCamera()){
            setContentView(R.layout.activity_no_camera);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mTextMessage = (TextView) findViewById(R.id.signin_message);
        Bundle extras = getIntent().getExtras();
        if(getIntent().hasExtra("login_failed") && extras.getString("login_failed").equals("true")){
            mTextMessage.setText(getString(R.string.failed_login_message));
            mTextMessage.setTextColor(getColor(android.R.color.holo_red_dark));
        }
        else{
            mTextMessage.setText(getString(R.string.login_message));
            mTextMessage.setTextColor(getColor(android.R.color.black));
        }

        getPermissions();
        //TODO something in here where it checks if they're signed in or not and goes to the main activity if they are.
    }

    private boolean deviceHasCamera(){
        PackageManager packageManager = this.getPackageManager();
        return packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA);
    }

    public void signIn(View view){
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                String test = ex.toString();
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                try {
                    Uri photoURI = FileProvider.getUriForFile(this,
                            "com.bmtdsl.votemeal.fileprovider",
                            photoFile);
                    currentPhotoUri = photoURI;

                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                    startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
                }
                catch(Exception ex){
                    //Do something
                    String test = ex.toString();
                }
            }
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK)
        {
            try {
                Intent photoIntent = new Intent(this, PhotoConfirmationActivity.class);
                photoIntent.putExtra(MediaStore.EXTRA_OUTPUT, currentPhotoUri);
                startActivity(photoIntent);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private boolean shouldAskPermission(){
        return(Build.VERSION.SDK_INT> Build.VERSION_CODES.LOLLIPOP_MR1);
    }

    private void getPermissions(){
        if(shouldAskPermission()){
            String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
            int permsRequestCode = 200;
            requestPermissions(perms, permsRequestCode);
        }
    }

    @Override
    public void onRequestPermissionsResult(int permsRequestCode, String[] permissions, int[] grantResults){
        switch(permsRequestCode){
            case 200:
                boolean writeAccepted = grantResults.length > 0
                        && grantResults[0]==PackageManager.PERMISSION_GRANTED;
                if(!writeAccepted){
                    setContentView(R.layout.activity_no_camera);
                }
                break;
        }
    }
}
