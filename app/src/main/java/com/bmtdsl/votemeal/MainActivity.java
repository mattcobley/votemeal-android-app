package com.bmtdsl.votemeal;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements VoteFragment.OnVoteSelectedListener, VotedFragment.OnSignOutListener, ResponseNetworkConnectionCompleteListener, VoteNetworkConnectionCompleteListener {

    private TextView mTextMessage;
    private int userId;
    private String name;
    private FragmentManager fragmentManager;
    private boolean voteComplete = false;
    private Party votedFor;
    ProgressDialog mProgressDialog;
    String resultsUrl = "http://votemeal.spess-whale.co.uk/results";
    String predictionsUrl = "http://votemeal.spess-whale.co.uk/predictions";
    String voteUrl = "http://votemeal.spess-whale.co.uk/vote";

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {
                case R.id.navigation_vote:
                    switchVoteFragment();
                    return true;
                case R.id.navigation_results:
                    getResults();
                    return true;
                case R.id.navigation_predictions:
                    getPredictions();
                    return true;
            }
            return false;
        }

    };


    private void getResults(){
        attemptGetConnection(resultsUrl, "results");
    }

    private void getPredictions(){
        attemptGetConnection(predictionsUrl, "predictions");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Bundle extras = getIntent().getExtras();
        userId = (int)extras.get("userId");
        name = (String)extras.get("name");

        addInitialFragment();

        mTextMessage = (TextView) findViewById(R.id.message);
        mTextMessage.setText(getString(R.string.welcome, name));
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    private void addInitialFragment(){
        fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        VoteFragment fragment = new VoteFragment();
        fragmentTransaction.add(R.id.mainactivitycontent, fragment);
        fragmentTransaction.commit();
    }

    private void switchVoteFragment(){
        fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if(!voteComplete) {
            VoteFragment fragment = new VoteFragment();
            fragmentTransaction.replace(R.id.mainactivitycontent, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
        else{
            VotedFragment fragment = new VotedFragment();
            fragment.setVotedFor(votedFor);
            fragmentTransaction.replace(R.id.mainactivitycontent, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
    }

    private void attemptGetConnection(String url, String responseFor){
        if (isNetworkConnected()) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage("Please wait...");
            mProgressDialog.setCancelable(false);
            mProgressDialog.show();

            startGetConnection(url, responseFor);
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

    private void attemptPostConnection(String url, Party party){
        if (isNetworkConnected()) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage("Please wait...");
            mProgressDialog.setCancelable(false);
            mProgressDialog.show();

            startPostConnection(url, party);
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

    private void startGetConnection(String url, String responseFor) {
        new NetworkGetTask(this).execute(url, responseFor, Integer.toString(userId));
    }

    private void startPostConnection(String url, Party party){
        new NetworkVotePostTask(this).execute(url, party.name().toLowerCase(), Integer.toString(userId));
    }

    private void switchToResultsFragment(Results results){
        fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        ResultsFragment fragment = new ResultsFragment();
        fragment.setResults(results);
        fragmentTransaction.replace(R.id.mainactivitycontent, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void switchToPredictionsFragment(Predictions predictions){
        fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        PredictionsFragment fragment = new PredictionsFragment();
        if(voteComplete) {
            fragment.setVotedFor(votedFor);
        }
        fragment.setPredictions(predictions);
        fragmentTransaction.replace(R.id.mainactivitycontent, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void onVoteSelected(Party votedFor){
        attemptPostConnection(voteUrl, votedFor);
        this.votedFor = votedFor;
    }

    @Override
    public void onSignOut(){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    @Override
    public void downloadComplete(Response response) {
        if (mProgressDialog != null) {
            mProgressDialog.hide();
        }

        if(response.responseFor == "results") {
            switchToResultsFragment(response.results);
        }
        if(response.responseFor == "predictions"){
            switchToPredictionsFragment(response.predictions);
        }
    }

    @Override
    public void downloadComplete(boolean success){
        if (mProgressDialog != null) {
            mProgressDialog.hide();
        }
        if(success){
            voteComplete = true;
            switchVoteFragment();
        }
    }
}
