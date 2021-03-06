package com.jdkmedia.vh8.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jdkmedia.vh8.R;
import com.jdkmedia.vh8.adapters.PlayerDetailCardAdapter;
import com.jdkmedia.vh8.adapters.TankDetailCardAdapter;
import com.jdkmedia.vh8.domain.PlayerDetailCard;
import com.jdkmedia.vh8.domain.PlayerExtended;
import com.jdkmedia.vh8.domain.TankDetailCard;
import com.jdkmedia.vh8.domain.TankExtended;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class TankDetailFragment extends Fragment {

    //Logging
    public final String TAG = getClass().getName() + " ";
    public static final String APP = "World of tanks ";

    //The Adapter for cards
    private RecyclerView mRecyclerView;
    private TankDetailCardAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private TankExtended tankExtended;

    public TankDetailFragment() {
        //req empty constr
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        if (getArguments() != null) {
            //Get the logged in player
            tankExtended = (TankExtended) getArguments().getSerializable("tankExtended");
        }
    }


    public static TankDetailFragment newInstance(TankExtended tank) {

        TankDetailFragment fragment = new TankDetailFragment();
        Bundle args = new Bundle();

        args.putSerializable("tankExtended", tank);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Get orientation
        int orientation = getResources().getConfiguration().orientation;

        //Get view and set text
        View view = inflater.inflate(R.layout.fragment_tank_detail, container, false);

        TextView tankName = (TextView) view.findViewById(R.id.tankName);
        ImageView tankCountourImage = (ImageView) view.findViewById(R.id.imageView);
        Picasso.with(getActivity().getApplicationContext()).load(tankExtended.getImage()).into(tankCountourImage);

        //http://developer.android.com/reference/android/content/res/Configuration.html#ORIENTATION_LANDSCAPE
        if(orientation == 2){
            //Landscape
            //Load extra stuff
            tankName.setText(tankExtended.getLongName());

        }else{
            tankName.setText(tankExtended.getName());
        }


        Log.d(APP + " Class: " + TAG, "Setting the adapter");

        //Set the adapters data
        mAdapter = new TankDetailCardAdapter(getDataSet(tankExtended)); //<-- get the data from getDataSet
        mRecyclerView = (RecyclerView) view.findViewById(R.id.tank_list);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        // Set click listener to this fragment
        return view;
    }


    //Create some demo stuff
    private ArrayList<TankDetailCard> getDataSet(TankExtended tankExtended) {
        Log.d(APP + " Class: " + TAG, "Creating new arrayList with player details");

        ArrayList<TankDetailCard> results = new ArrayList<TankDetailCard>();

        //Tank count
        TankDetailCard tankCountCard = new TankDetailCard(getString(R.string.textTankCount), Integer.toString(tankExtended.getEnginePower()), R.mipmap.ic_tank_count);
        TankDetailCard mastersOfExcellenceOneCard = new TankDetailCard(getString(R.string.textMarksOfExcellenceMastery), Integer.toString(tankExtended.getGunDamageMin()), R.mipmap.ic_mastery);

        results.add(tankCountCard);
        results.add(mastersOfExcellenceOneCard);

        return results;
    }

    @Override
    public void onResume() {
        super.onResume();
        ((TankDetailCardAdapter) mAdapter).setOnItemClickListener(new TankDetailCardAdapter.TankItemClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                Log.d(APP + " Class: " + TAG, "Clicked on item" + position);
            }
        });
    }
}