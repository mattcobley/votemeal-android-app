package com.bmtdsl.votemeal;

import android.app.Fragment;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;


public class PredictionsFragment extends Fragment{

    public void setPredictions(Predictions predictions) {
        this.predictions = predictions;
    }

    private Predictions predictions;

    public void setVotedFor(Party votedFor) {
        this.votedFor = votedFor;
    }

    Party votedFor;


    public PredictionsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_predictions, container, false);

        TextView genderPredictionView = view.findViewById(R.id.gender_prediction_view);
        genderPredictionView.setText(getString(R.string.gender_prediction, predictions.getGender()));

        TextView agePredictionView = view.findViewById(R.id.age_prediction_view);
        agePredictionView.setText(getString(R.string.age_prediction, predictions.getAge()));

        if(votedFor != null) {
            boolean partyPredictionCorrect = false;
            if(votedFor == predictions.getPredictedParty()){
                partyPredictionCorrect = true;
            }
            String partyPredictionCorrectText;
            Drawable partyPredictionCorrectImage;
            int textColour;
            if(partyPredictionCorrect){
                partyPredictionCorrectText = getString(R.string.prediction_correct);
                partyPredictionCorrectImage = ContextCompat.getDrawable(getContext(), R.mipmap.green_tick);
                textColour = android.R.color.holo_green_dark;
            }
            else{
                partyPredictionCorrectText = getString(R.string.prediction_incorrect);
                partyPredictionCorrectImage = ContextCompat.getDrawable(getContext(), android.R.drawable.ic_delete);
                textColour = android.R.color.holo_red_dark;
            }

            TextView partyPredictionView = view.findViewById(R.id.party_prediction_view);
            partyPredictionView.setText(getString(R.string.party_prediction, predictions.getPredictedParty().getName()));

            TextView partyPredictionCorrectView = view.findViewById(R.id.prediction_correct_view);
            partyPredictionCorrectView.setText(partyPredictionCorrectText);
            partyPredictionCorrectView.setTextColor(getResources().getColor(textColour));

            ImageView partyPredictionCorrectImageView = view.findViewById(R.id.prediction_correct_image);
            partyPredictionCorrectImageView.setImageDrawable(partyPredictionCorrectImage);

            ProgressBar conservativesProgressBar=view.findViewById(R.id.conservativesPredictionBar);
            conservativesProgressBar.setMax(100);
            conservativesProgressBar.setProgress(predictions.getConservativesPercentage());
            conservativesProgressBar.getProgressDrawable().setColorFilter(
                    Color.rgb(1, 148, 225), android.graphics.PorterDuff.Mode.SRC_IN);
            ProgressBar labourProgressBar=view.findViewById(R.id.labourPredictionBar);
            labourProgressBar.setMax(100);
            labourProgressBar.setProgress(predictions.getLabourPercentage());
            labourProgressBar.getProgressDrawable().setColorFilter(
                    Color.rgb(218, 21, 0), android.graphics.PorterDuff.Mode.SRC_IN);
            ProgressBar libdemsProgressBar=view.findViewById(R.id.libdemsPredictionBar);
            libdemsProgressBar.setMax(100);
            libdemsProgressBar.setProgress(predictions.getLibdemsPercentage());
            libdemsProgressBar.getProgressDrawable().setColorFilter(
                    Color.rgb(253, 187, 41), android.graphics.PorterDuff.Mode.SRC_IN);
            ProgressBar greensProgressBar=view.findViewById(R.id.greensPredictionBar);
            greensProgressBar.setMax(100);
            greensProgressBar.setProgress(predictions.getGreensPercentage());
            greensProgressBar.getProgressDrawable().setColorFilter(
                    Color.rgb(106, 175, 33), android.graphics.PorterDuff.Mode.SRC_IN);
            ProgressBar ukipProgressBar=view.findViewById(R.id.ukipPredictionBar);
            ukipProgressBar.setMax(100);
            ukipProgressBar.setProgress(predictions.getUkipPercentage());
            ukipProgressBar.getProgressDrawable().setColorFilter(
                    Color.rgb(141, 50, 130), android.graphics.PorterDuff.Mode.SRC_IN);


            TextView conservativesNameView = view.findViewById(R.id.conservativesPredictionView);
            conservativesNameView.setText(getString(R.string.party_name_and_percentage_conservatives, predictions.getConservativesPercentage()));
            TextView labourNameView = view.findViewById(R.id.labourPredictionView);
            labourNameView.setText(getString(R.string.party_name_and_percentage_labour, predictions.getLabourPercentage()));
            TextView libdemsNameView = view.findViewById(R.id.libdemsPredictionView);
            libdemsNameView.setText(getString(R.string.party_name_and_percentage_libdems, predictions.getLibdemsPercentage()));
            TextView greensNameView = view.findViewById(R.id.greensPredictionView);
            greensNameView.setText(getString(R.string.party_name_and_percentage_greens, predictions.getGreensPercentage()));
            TextView ukipNameView = view.findViewById(R.id.ukipPredictionView);
            ukipNameView.setText(getString(R.string.party_name_and_percentage_ukip, predictions.getUkipPercentage()));
        }

        return view;
    }
}
