package com.bmtdsl.votemeal;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


public class VotedFragment extends Fragment{

    private Party votedFor;
    private OnSignOutListener mListener;
    private TextView votedMessage;

    public void setVotedFor(Party votedFor){
        this.votedFor = votedFor;
    }

    public VotedFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_voted, container, false);

        Button signout_button = view.findViewById(R.id.signout_button);
        signout_button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                signOut(view);
            }
        });

        votedMessage = view.findViewById(R.id.party_result_view);
        votedMessage.setText(votedFor.getName());
;
        return view;
    }

    public void signOut(View view){
        if (mListener != null) {
            mListener.onSignOut();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnSignOutListener) {
            mListener = (OnSignOutListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnSignOutListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    //Allows this fragment to interact with other fragments via the parent activity.
    public interface OnSignOutListener {
        void onSignOut();
    }
}
