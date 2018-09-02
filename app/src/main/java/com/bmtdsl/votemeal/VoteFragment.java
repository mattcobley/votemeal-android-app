package com.bmtdsl.votemeal;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import static com.bmtdsl.votemeal.R.id.conservatives_button;


public class VoteFragment extends Fragment{

    private OnVoteSelectedListener mListener;

    public VoteFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_vote, container, false);

        Button conservatives_button = view.findViewById(R.id.conservatives_button);
        conservatives_button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                selectParty(view);
            }
        });
        Button labour_button = view.findViewById(R.id.labour_button);
        labour_button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                selectParty(view);
            }
        });
        Button libdems_button = view.findViewById(R.id.libdems_button);
        libdems_button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                selectParty(view);
            }
        });
        Button greenparty_button = view.findViewById(R.id.greenparty_button);
        greenparty_button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                selectParty(view);
            }
        });
        Button ukip_button = view.findViewById(R.id.ukip_button);
        ukip_button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                selectParty(view);
            }
        });
        return view;
    }

    public void selectParty(View view){
        Party votedFor;
        switch(view.getId()) {
            case conservatives_button:
                votedFor = Party.CONSERVATIVES;
                break;
            case R.id.labour_button:
                votedFor = Party.LABOUR;
                break;
            case R.id.libdems_button:
                votedFor = Party.LIBDEMS;
                break;
            case R.id.greenparty_button:
                votedFor = Party.GREEN;
                break;
            case R.id.ukip_button:
                votedFor = Party.UKIP;
                break;
            default:
                return;
        }

        if (mListener != null) {
            mListener.onVoteSelected(votedFor);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnVoteSelectedListener) {
            mListener = (OnVoteSelectedListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnVoteSelectedListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    //Allows this fragment to interact with other fragments via the parent activity.
    public interface OnVoteSelectedListener {
        void onVoteSelected(Party votedFor);
    }
}
