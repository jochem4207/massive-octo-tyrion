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

    /**
     * Constructor for the fragment
     *
     * @param loggedInPlayer the player that is logged in or null
     */
    public TankDetailInnerFragment(PlayerExtended loggedInPlayer) {
        Log.d(APP + " Class: " + TAG, "Creating new player detail innerfragment");
        this.player = loggedInPlayer;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Get the args
        View view;
        if (player == null) {
            Log.d(APP + " Class: " + TAG, "Player is not logged in, load error view");

            //Load error view
            view = inflater.inflate(R.layout.fragment_player_detail_not_logged_in, container, false);

            //Get textview and settext
            TextView playerDetailNotLoggedInTextView = (TextView) view.findViewById(R.id.playerDetailNotLoggedIn);
            playerDetailNotLoggedInTextView.setText(R.string.login_message_home_screen);

        } else {
            Log.d(APP + " Class: " + TAG, "Player is logged in, load details view");

            view = inflater.inflate(R.layout.fragment_player_detail_inner, container, false);

            //Show some player details
            TextView playerName = (TextView) view.findViewById(R.id.playerName);
            TextView rating = (TextView) view.findViewById(R.id.rating);
            playerName.setText(player.getNickname());
            rating.setText(Integer.toString(player.getGlobalRating()));
        }

        return view;
    }
}
