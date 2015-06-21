package com.jdkmedia.vh8.fragment;

import android.app.Activity;
import android.app.ListFragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jdkmedia.vh8.R;
import com.jdkmedia.vh8.adapters.PlayerListAdapter;
import com.jdkmedia.vh8.api.JsonResultPlayerQuery;
import com.jdkmedia.vh8.domain.Player;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class PlayerSearchMainFragment extends ListFragment implements AbsListView.OnItemClickListener {

    //API
    private static final String APPLICATION_ID = "?application_id=74da03a344137eb2756c49c9e9069092";
    private static final String API_URL = "http://api.worldoftanks.eu/wot/";
    private static final String API_CALL = "account/list/";
    private static final String API_OPTION = "&search=";

    //Logging
    public final String TAG = getClass().getName() + " ";
    public static final String APP = "World of tanks ";

    /**
     * The fragment's ListView/GridView.
     */
    private ListView mListView;
    private PlayerListAdapter mAdapter;
    private OnPlayerSelectedListener mListener;

    //Result api
    private JsonResultPlayerQuery JsonResult;

    /*
      * Use this factory method to create a new instance of
      * this fragment using the provided parameters.

      * @return A new instance of fragment PlayerSearchMainFragment.
      */
    public static PlayerSearchMainFragment newInstance() {
        return new PlayerSearchMainFragment();
    }

    public PlayerSearchMainFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Retain instance state to save input
        setRetainInstance(true);

        //SetHasoptions menu to create a different options menu than the activity has
        setHasOptionsMenu(true);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        //Inflate the specific menu
        inflater.inflate(R.menu.search_menu, menu);

        //Get the search item
        MenuItem item = menu.findItem(R.id.search_menu);

        //Bind the search
        if (getActivity().getActionBar() != null) {

            SearchView sv = new SearchView((getActivity()).getActionBar().getThemedContext());

            MenuItemCompat.setShowAsAction(item, MenuItemCompat.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW | MenuItemCompat.SHOW_AS_ACTION_IF_ROOM);
            MenuItemCompat.setActionView(item, sv);

            //Set a listener
            sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {

                    Log.d(APP + " Class: " + TAG, "User submitted query" + query);

                    //Query has to be atleast 4 chars for api requirements
                    if (query.length() > 3) {
                        Log.d(APP + " Class: " + TAG, "query is valid" + query);
                        getPlayers(query);
                        return false;
                    }

                    Toast.makeText(getActivity(), getString(R.string.error_fill_field), Toast.LENGTH_LONG).show();
                    return true;
                }

                @Override
                public boolean onQueryTextChange(String query) {

                    //Query has to be atleast 4 chars for api requirements
                    if (query.length() > 3) {
                        Log.d(APP + " Class: " + TAG, "query is valid" + query);
                        getPlayers(query);
                        return false;
                    }
                    return true;
                }
            });
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(APP + " Class: " + TAG, "Load fragment player search view");
        //Inflate fragment layout
        View view = inflater.inflate(R.layout.fragment_player_search_view, container, false);

        //Get the listview
        mListView = (ListView) view.findViewById(android.R.id.list);

        Log.d(APP + " Class: " + TAG, "Set adapter");

        //Initate adapter
        mAdapter = new PlayerListAdapter(getActivity(), new ArrayList<Player>());

        //Set adapter
        mListView.setAdapter(mAdapter);

        // Set OnItemClickListener so we can be notified on item clicks
        mListView.setOnItemClickListener(this);

        if (savedInstanceState != null && JsonResult != null) {
            JsonResult = (JsonResultPlayerQuery) savedInstanceState.getSerializable("search_result");
            updateFragment(JsonResult);
        }
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnPlayerSelectedListener) activity;
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

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putSerializable("search_result", JsonResult);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    //To update the fragment with data
    private void updateFragment(JsonResultPlayerQuery data) {
        Log.d(APP + " Class: " + TAG, "Get new data");
        Log.d(APP + " Class: " + TAG, data.toString());

        // get new modified  data
        List<Player> objects = data.getPlayers();

        Log.d(APP + " Class: " + TAG, "Clear adapter and add new data");
        // update data in our adapter
        mAdapter.getData().clear();
        mAdapter.getData().addAll(objects);
        // Notify adapter that it is changed
        mAdapter.notifyDataSetChanged();
    }


    //SEARCH PLAYERS

    public void getPlayers(String query) {
        if (getView() != null) {


            //Log the input
            Log.d(APP + " Class: " + TAG, "Search:" + query);

            //Call api to get result
            new CallAPI().execute(API_URL + API_CALL + APPLICATION_ID + API_OPTION + query);

        } else {
            //Give a toast error
            Toast.makeText(getActivity(), getString(R.string.error_fill_field), Toast.LENGTH_LONG).show();

        }
    }

    private class CallAPI extends AsyncTask<String, Void, Boolean> {
        @Override
        protected Boolean doInBackground(String... params) {
            Log.d(APP + " Class: " + TAG, "JsonAsyncTask URL:" + params[0]);

            //Get url from params
            String urlString = params[0];

            // Get output from api
            try {
                //Create url
                URL url = new URL(urlString);
                //open stream
                InputStream input = url.openStream();
                //Open reader
                Reader reader = new InputStreamReader(input);
                //Json to class use GSON
                JsonResultPlayerQuery result = new Gson().fromJson(reader, JsonResultPlayerQuery.class);
                if (result != null) {
                    JsonResult = result;
                } else {
                    Log.d(APP + " Class: " + TAG, "Unhandled Error in Call API");
                }

            } catch (IOException e) {
                Log.e(APP + " Class: " + TAG, "IOEXception" + e.getMessage());
                e.printStackTrace();
                return false;
            }
            return true;
        }

        protected void onPostExecute(Boolean result) {
            Log.d(APP + " Class: " + TAG, "OnPostExecute result" + result);
            if (!result) {
                Toast.makeText(getActivity(), getString(R.string.could_not_get_data), Toast.LENGTH_LONG).show();
            } else {

                updateFragment(JsonResult);

                Log.d(APP + " Class: " + TAG, "OnPostExecute result player" + JsonResult.toString());
            }
        }
    }

    //SHOW PLAYERS
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        // do something with the data
        //get player from postion
        //give it to the main activity
        mListener.onPlayerSelected((Player) mAdapter.getItem(position));
        Log.d(APP + " Class: " + TAG, "PlayerListFragment Position clicked" + Integer.toString(position));
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (null != mListener) {
            // Notify the active callbacks interface (the activity, if the
            // fragment is attached to one) that an item has been selected.
            //  mListener.onFragmentInteraction(DummyContent.ITEMS.get(position).id);
        }
    }


    public interface OnPlayerSelectedListener {
        /**
         * If a player is selected in the list send the player to the main activity
         * The main activity will get details and show a detail view
         *
         * @param player selected player
         */
        public void onPlayerSelected(Player player);
    }
}

