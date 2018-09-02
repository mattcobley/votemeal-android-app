package com.bmtdsl.votemeal;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;


public class ResultsFragment extends Fragment{

    public void setResults(Results results) {
        this.results = results;
    }

    private Results results;

    public ResultsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_results, container, false);

        int conservativeVotes = results.getConservatives();
        int labourVotes = results.getLabour();
        int libdemVotes = results.getLibdems();
        int greenVotes = results.getGreens();
        int ukipVotes = results.getUkip();
        int totalVotes = results.getTotal();
        int maxVotes = results.getMaxValue();


        ProgressBar conservativesProgressBar=view.findViewById(R.id.conservativesProgressBar);
        conservativesProgressBar.setMax(maxVotes);
        conservativesProgressBar.setProgress(conservativeVotes);
        conservativesProgressBar.getProgressDrawable().setColorFilter(
                Color.rgb(1, 148, 225), android.graphics.PorterDuff.Mode.SRC_IN);
        ProgressBar labourProgressBar=view.findViewById(R.id.labourProgressBar);
        labourProgressBar.setMax(maxVotes);
        labourProgressBar.setProgress(labourVotes);
        labourProgressBar.getProgressDrawable().setColorFilter(
                Color.rgb(218, 21, 0), android.graphics.PorterDuff.Mode.SRC_IN);
        ProgressBar libdemsProgressBar=view.findViewById(R.id.libdemsProgressBar);
        libdemsProgressBar.setMax(maxVotes);
        libdemsProgressBar.setProgress(libdemVotes);
        libdemsProgressBar.getProgressDrawable().setColorFilter(
                Color.rgb(253, 187, 41), android.graphics.PorterDuff.Mode.SRC_IN);
        ProgressBar greensProgressBar=view.findViewById(R.id.greenpartyProgressBar);
        greensProgressBar.setMax(maxVotes);
        greensProgressBar.setProgress(greenVotes);
        greensProgressBar.getProgressDrawable().setColorFilter(
                Color.rgb(106, 175, 33), android.graphics.PorterDuff.Mode.SRC_IN);
        ProgressBar ukipProgressBar=view.findViewById(R.id.ukipProgressBar);
        ukipProgressBar.setMax(maxVotes);
        ukipProgressBar.setProgress(ukipVotes);
        ukipProgressBar.getProgressDrawable().setColorFilter(
                Color.rgb(141, 50, 130), android.graphics.PorterDuff.Mode.SRC_IN);

        TextView totalVotesView = view.findViewById(R.id.total_votes_view);
        totalVotesView.setText(getString(R.string.total_votes, totalVotes));
        TextView conservativesNameView = view.findViewById(R.id.conservatives_result_text);
        conservativesNameView.setText(getString(R.string.party_name_and_votes_conservatives, conservativeVotes));
        TextView labourNameView = view.findViewById(R.id.labour_result_text);
        labourNameView.setText(getString(R.string.party_name_and_votes_labour, labourVotes));
        TextView libdemsNameView = view.findViewById(R.id.libdems_result_text);
        libdemsNameView.setText(getString(R.string.party_name_and_votes_libdems, libdemVotes));
        TextView greensNameView = view.findViewById(R.id.greens_result_text);
        greensNameView.setText(getString(R.string.party_name_and_votes_greens, greenVotes));
        TextView ukipNameView = view.findViewById(R.id.ukip_result_text);
        ukipNameView.setText(getString(R.string.party_name_and_votes_ukip, ukipVotes));

        return view;
    }

}
