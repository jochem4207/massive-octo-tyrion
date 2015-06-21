package com.jdkmedia.vh8.fragment;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jdkmedia.vh8.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class TankCompareTopFragment extends Fragment {


    public TankCompareTopFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Get view and set text
        View view = inflater.inflate(R.layout.fragment_tank_compare, container, false);

        // Set click listener to this fragment
        return view;
    }

}
