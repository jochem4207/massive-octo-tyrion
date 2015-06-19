package com.jdkmedia.vh8.fragment;

import android.os.Bundle;
import android.app.Fragment;
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

    //Debugging
    public final String TAG = getClass().getName() + " ";
    public static final String APP = "JdkMedia ";

    //The Adapter for cards
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private PlayerExtended playerExtended;

    public PlayerDetailFragment(PlayerExtended player) {
        this.playerExtended = player;
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Get the args

        //Get view and set text
        View view = inflater.inflate(R.layout.fragment_player_detail, container, false);
        TextView playerName = (TextView) view.findViewById(R.id.playerName);
        playerName.setText(playerExtended.getNickname() + " (" + playerExtended.getGlobalRating() + ")");

        //Create data for cards here

        //Load adapter
        mAdapter = new PlayerDetailCardAdapter(getDataSet(playerExtended));
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
        ArrayList results = new ArrayList<PlayerDetailCard>();

        //Achievements

        //Tank count
        PlayerDetailCard tankCountCard = new PlayerDetailCard("Tanks", Integer.toString(playerExtended.getTankCount()) ,R.mipmap.ic_mastery_1);
        //TODO FIX IMAGES FIRST ONE
        PlayerDetailCard mastersOfExcellenceOneCard = new PlayerDetailCard("Marks Of Excellence ", Integer.toString(playerExtended.getMarksOfExcellenceCount(1)),R.mipmap.ic_mastery_1);
        PlayerDetailCard mastersOfExcellenceTwoCard = new PlayerDetailCard("Marks Of Excellence 1", Integer.toString(playerExtended.getMarksOfExcellenceCount(2)),R.mipmap.ic_mastery_1);
        PlayerDetailCard mastersOfExcellenceThreeCard = new PlayerDetailCard("Marks Of Excellence 2", Integer.toString(playerExtended.getMarksOfExcellenceCount(3)),R.mipmap.ic_mastery_2);
        PlayerDetailCard mastersOfExcellenceFourCard = new PlayerDetailCard("Marks Of Excellence 3", Integer.toString(playerExtended.getMarksOfExcellenceCount(4)),R.mipmap.ic_mastery_3);

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
                Log.i(TAG, " Clicked on Item " + position);
            }
        });
    }

    //UPDATE FRAG //
    public void updatePlayerView(PlayerExtended player){

    }


}