package com.jdkmedia.vh8.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jdkmedia.vh8.R;
import com.jdkmedia.vh8.adapters.PlayerDetailCardAdapter;
import com.jdkmedia.vh8.domain.PlayerDetailCard;
import com.jdkmedia.vh8.domain.PlayerExtended;

import java.util.ArrayList;

public class PlayerDetailFragment extends Fragment {


    //Logging
    public final String TAG = getClass().getName() + " ";
    public static final String APP = "World of tanks ";

    //The Adapter for cards
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter<PlayerDetailCardAdapter.ViewHolder> mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private PlayerExtended playerExtended;

    public PlayerDetailFragment(){
        //required empty constructor
    }

    public static PlayerDetailFragment newInstance(PlayerExtended playerExtended) {

        PlayerDetailFragment fragment = new PlayerDetailFragment();
        Bundle args = new Bundle();

        args.putSerializable("selectedPlayer", playerExtended);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        if (getArguments() != null) {
            //Get the logged in player
            playerExtended = (PlayerExtended) getArguments().getSerializable("selectedPlayer");
        }

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Get view and set text
        View view = inflater.inflate(R.layout.fragment_player_detail, container, false);
        TextView playerName = (TextView) view.findViewById(R.id.playerName);
        playerName.setText(playerExtended.getNickname() + " (" + playerExtended.getGlobalRating() + ")");

        Log.d(APP + " Class: " + TAG, "Setting the adapter");

        //Set the adapters data
        mAdapter = new PlayerDetailCardAdapter(getDataSet(playerExtended)); //<-- get the data from getDataSet
        mRecyclerView = (RecyclerView) view.findViewById(R.id.tank_list);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        // Set click listener to this fragment
        return view;
    }


    //Create some demo stuff
    private ArrayList<PlayerDetailCard> getDataSet(PlayerExtended playerExtended) {
        Log.d(APP + " Class: " + TAG, "Creating new arrayList with player details");

        ArrayList<PlayerDetailCard> results = new ArrayList<PlayerDetailCard>();

        //Tank count
        PlayerDetailCard tankCountCard = new PlayerDetailCard(getString(R.string.textTankCount), Integer.toString(playerExtended.getTankCount()), R.mipmap.ic_tank_count);
        PlayerDetailCard mastersOfExcellenceOneCard = new PlayerDetailCard(getString(R.string.textMarksOfExcellenceMastery), Integer.toString(playerExtended.getMarksOfExcellenceCount(1)), R.mipmap.ic_mastery);
        PlayerDetailCard mastersOfExcellenceTwoCard = new PlayerDetailCard(getString(R.string.textMarksOfExcellenceOne), Integer.toString(playerExtended.getMarksOfExcellenceCount(2)), R.mipmap.ic_mastery_1);
        PlayerDetailCard mastersOfExcellenceThreeCard = new PlayerDetailCard(getString(R.string.textMarksOfExcellenceTwo), Integer.toString(playerExtended.getMarksOfExcellenceCount(3)), R.mipmap.ic_mastery_2);
        PlayerDetailCard mastersOfExcellenceFourCard = new PlayerDetailCard(getString(R.string.textMarksOfExcellenceThree), Integer.toString(playerExtended.getMarksOfExcellenceCount(4)), R.mipmap.ic_mastery_3);

        results.add(tankCountCard);
        results.add(mastersOfExcellenceOneCard);
        results.add(mastersOfExcellenceTwoCard);
        results.add(mastersOfExcellenceThreeCard);
        results.add(mastersOfExcellenceFourCard);

        return results;
    }

    @Override
    public void onResume() {
        super.onResume();
        ((PlayerDetailCardAdapter) mAdapter).setOnItemClickListener(new PlayerDetailCardAdapter.TankItemClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                Log.d(APP + " Class: " + TAG, "Clicked on item" + position);
            }
        });
    }
}