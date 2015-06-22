package com.jdkmedia.vh8.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jdkmedia.vh8.R;
import com.jdkmedia.vh8.domain.PlayerExtended;

public class TankDetailInnerFragment extends Fragment {

    //Logging
    public final String TAG = getClass().getName() + " ";
    public static final String APP = "World of tanks ";

    //Player detail
    private PlayerExtended player;

    public TankDetailInnerFragment() {
        // Required empty public constructor
    }


    public static TankDetailInnerFragment newInstance(PlayerExtended playerExtended) {

        TankDetailInnerFragment fragment = new TankDetailInnerFragment();
        Bundle args = new Bundle();

        args.putSerializable("selectedPlayer", playerExtended);
        fragment.setArguments(args);

        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            //Get the logged in player
            player = (PlayerExtended) getArguments().getSerializable("selectedPlayer");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Get the args
        View view;
        view = inflater.inflate(R.layout.fragment_tank_detail_inner, container, false);

        //if user isn't logged in player = null
        if(player != null) {
            Log.d(APP + " Class: " + TAG, "Player is logged in, load details view");
            player.getPlayerTankList().size();

            //Show some player details
            TextView playerName = (TextView) view.findViewById(R.id.playerName);
            TextView rating = (TextView) view.findViewById(R.id.rating);
            
            playerName.setText(getString(R.string.fragment_tank_detail_inner_tank_count));
            rating.setText(Integer.toString(player.getTankCount()));
        }
        return view;
    }
}
