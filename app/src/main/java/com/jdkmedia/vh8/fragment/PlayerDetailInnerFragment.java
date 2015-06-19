package com.jdkmedia.vh8.fragment;

import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jdkmedia.vh8.R;
import com.jdkmedia.vh8.domain.PlayerExtended;

public class PlayerDetailInnerFragment extends Fragment {

    //Debugging
    public final String TAG = getClass().getName() + " ";
    public static final String APP = "JdkMedia ";

    //The Adapter for cards
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    //Player detail
    private PlayerExtended player;

    public PlayerDetailInnerFragment() {
        // Required empty public constructor
    }

    public PlayerDetailInnerFragment(PlayerExtended loggedInPlayer) {
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
        if(player == null) {
            //Load error view
            view = inflater.inflate(R.layout.fragment_player_detail_not_logged_in, container, false);

            //Get textview and settext
            TextView playerDetailNotLoggedInTextView = (TextView) view.findViewById(R.id.playerDetailNotLoggedIn);
            playerDetailNotLoggedInTextView.setText(R.string.login_message_home_screen);

        }else{
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
