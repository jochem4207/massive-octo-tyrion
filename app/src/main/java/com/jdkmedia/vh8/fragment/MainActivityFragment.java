package com.jdkmedia.vh8.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jdkmedia.vh8.R;
import com.jdkmedia.vh8.domain.PlayerExtended;

public class MainActivityFragment extends Fragment {
    private PlayerExtended loggedInPlayer;
    private OnLoadDetailFragment mListener;

    //Logging
    public final String TAG = getClass().getName() + " ";
    public static final String APP = "World of tanks ";

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment MainActivityFragment.
     */
    public static MainActivityFragment newInstance(PlayerExtended playerExtended) {

        //Create a instance with the logged in player in the bundle
        MainActivityFragment fragment = new MainActivityFragment();
        Bundle args = new Bundle();

        args.putSerializable("loggedInPlayer", playerExtended);
        fragment.setArguments(args);

        //Logging
        if (playerExtended != null) {
            Log.d(APP + " Class: ", "New instance created with player" + playerExtended.toString());
        } else {
            Log.d(APP + " Class: ", "New instance created without player");
        }
        return fragment;
    }

    public MainActivityFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            //Get the logged in player
            loggedInPlayer = (PlayerExtended) getArguments().getSerializable("loggedInPlayer");


            //Logging
            if (loggedInPlayer != null) {
                Log.d(APP + " Class: ", "On create retrevied loggedInPlayer" + loggedInPlayer);
            } else {
                Log.d(APP + " Class: ", "On create  retrevied no player");
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.d(APP + " Class: ", "Oncreate view loading fragment main activity");
        return inflater.inflate(R.layout.fragment_main_activity, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (loggedInPlayer != null) {
            //Load the detailed user info for the fragment on the home screen
            Log.d(APP + " Class: ", "Player is logged in loading detail inner fragment");
            mListener.onLoadDetailFragment(loggedInPlayer);
        } else {
            //Don't load anything, use rnot logged in
            Log.d(APP + " Class: ", "Player is not logged in loading detail inner fragment empty!");

            //Detail fragment
            PlayerDetailInnerFragment playerDetailInnerFragment = new PlayerDetailInnerFragment(null);
            //Manage fragment
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.child_fragment, playerDetailInnerFragment, playerDetailInnerFragment.getClass().getName()).addToBackStack(playerDetailInnerFragment.getClass().getName()).commit();
        }
    }


    @Override
    public void onResume() {
        super.onResume();

        //To lock orientation change
        if (getActivity() != null) {
            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }

    @Override
    public void onPause() {
        super.onPause();

        //To lock orientation change
        if (getActivity() != null) {
            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        //Delete the inner fragment to make sure everything gets deleted
        PlayerDetailInnerFragment f = (PlayerDetailInnerFragment) getFragmentManager()
                .findFragmentById(R.id.player_detail_fragment);
        if (f != null)
            getFragmentManager().beginTransaction().remove(f).commit();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnLoadDetailFragment) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnLoadDetailFragment {
        /**
         * If the player is logged in, send it to the main activity to get extended details
         *
         * @param playerExtended the logged in player
         */
        public void onLoadDetailFragment(PlayerExtended playerExtended);
    }
}
