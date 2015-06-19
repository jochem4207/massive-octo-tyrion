package com.jdkmedia.vh8.fragment;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jdkmedia.vh8.R;
import com.jdkmedia.vh8.domain.PlayerExtended;

public class MainActivityFragment extends Fragment {
    private PlayerExtended loggedInPlayer;
    private OnLoadDetailFragment mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment MainActivityFragment.
     */
    public static MainActivityFragment newInstance(PlayerExtended playerExtended) {
        MainActivityFragment fragment = new MainActivityFragment();
        Bundle args = new Bundle();
        args.putSerializable("loggedInPlayer", playerExtended);
        fragment.setArguments(args);
        return fragment;
    }

    public MainActivityFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            loggedInPlayer = (PlayerExtended) getArguments().getSerializable("loggedInPlayer");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main_activity, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        if(loggedInPlayer != null){
            mListener.onLoadDetailFragment(loggedInPlayer);
        }else{
            //Don't LOAD THE USERS DETAILS because the user is not logged in

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
        if (getActivity() != null) {
            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (getActivity() != null) {
            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
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
        public void onLoadDetailFragment(PlayerExtended player);
    }
}
