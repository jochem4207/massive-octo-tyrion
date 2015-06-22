package com.jdkmedia.vh8.fragment;

import android.app.Activity;
import android.app.ListFragment;
import android.content.res.Configuration;
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
import android.widget.GridView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.jdkmedia.vh8.R;
import com.jdkmedia.vh8.adapters.TankGridAdapter;
import com.jdkmedia.vh8.adapters.TankListAdapter;
import com.jdkmedia.vh8.domain.Tank;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;


public class TankSearchMainFragment extends ListFragment implements AbsListView.OnItemClickListener {

    //API
    private static final String APPLICATION_ID = "?application_id=74da03a344137eb2756c49c9e9069092";
    private static final String API_URL = "http://api.worldoftanks.eu/wot/";
    private static final String API_CALL = "encyclopedia/tanks/";

    //Logging
    public final String TAG = getClass().getName() + " ";
    public static final String APP = "World of tanks ";

    /**
     * The fragment's ListView/GridView.
     */
    private ListView mListView;
    private GridView mGridView;
    private TankListAdapter mListAdapter;
    private TankGridAdapter mGridAdapter;
    private onTankSelectedListener mListener;

    //Result api
    private ArrayList<Tank> tankList = new ArrayList<Tank>();


    /*
      * Use this factory method to create a new instance of
      * this fragment using the provided parameters.

      * @return A new instance of fragment PlayerSearchMainFragment.
      */
    public static TankSearchMainFragment newInstance() {
        return new TankSearchMainFragment();
    }

    public TankSearchMainFragment() {
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
                        getTanks(query);
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
                        getTanks(query);
                        return false;
                    }
                    return true;
                }
            });
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        boolean isTablet = this.getResources().getConfiguration().isLayoutSizeAtLeast(Configuration.SCREENLAYOUT_SIZE_LARGE);
        Log.d(APP + " Class: " + TAG, "Load fragment player search view");
        //Inflate fragment layout
        View view = inflater.inflate(R.layout.fragment_tank_list, container, false);

        if(isTablet){
            mGridView = (GridView) view.findViewById(R.id.someGridList);
//            Log.d(APP + " Class: " + TAG, "Set grid adapter");
//            mGridAdapter = new TankGridAdapter(getActivity(), new ArrayList<Tank>());
            mListAdapter = new TankListAdapter(getActivity(), new ArrayList<Tank>());

            //Set adapter
            mGridView.setAdapter(mListAdapter);

            // Set OnItemClickListener so we can be notified on item clicks
            mListView.setOnItemClickListener(this);

        }else{
            //Get the listview
            mListView = (ListView) view.findViewById(android.R.id.list);

            Log.d(APP + " Class: " + TAG, "Set list adapter");

            //Initate adapter
            mListAdapter = new TankListAdapter(getActivity(), new ArrayList<Tank>());

            //Set adapter
            mListView.setAdapter(mListAdapter);

            // Set OnItemClickListener so we can be notified on item clicks
            mListView.setOnItemClickListener(this);


        }

        if (savedInstanceState != null && tankList != null) {
            tankList = (ArrayList<Tank>) savedInstanceState.getSerializable("search_result");
            updateFragment(tankList);
        }

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        getTanks();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (onTankSelectedListener) activity;
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
        outState.putSerializable("search_result", tankList);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    //To update the fragment with data
    private void updateFragment(ArrayList<Tank> data) {
        Log.d(APP + " Class: " + TAG, "Get new data");
        Log.d(APP + " Class: " + TAG, data.toString());


        Log.d(APP + " Class: " + TAG, "Clear adapter and add new data");
        // update data in our adapter
        mListAdapter.getData().clear();
        mListAdapter.getData().addAll(data);
        // Notify adapter that it is changed
        mListAdapter.notifyDataSetChanged();
    }


    //SEARCH PLAYERS

    public void getTanks() {
        if (getView() != null) {


            //Call api to get result
            new CallAPI().execute(API_URL + API_CALL + APPLICATION_ID);

        } else {
            //Give a toast error
            Toast.makeText(getActivity(), getString(R.string.error_fill_field), Toast.LENGTH_LONG).show();

        }
    }

    public void getTanks(String query){
        //throw notImplementedException();
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
                JsonElement root = new JsonParser().parse(reader);

                String status = root.getAsJsonObject().get("status").getAsString();

                //if status is ok
                if (status.equals("ok")) {
                    //Get JsonObject data
                    JsonObject obj = root.getAsJsonObject().get("data").getAsJsonObject();


                    //For every entry set create a playerExtended
                    for (Map.Entry<String, JsonElement> entry : obj.entrySet()) {

                        //Can be multiple players (api allows, app does not!)
                        Tank tank = new Gson().fromJson(entry.getValue(), Tank.class);
                        tankList.add(tank);
                    }

                } else if (status.equals("error")) {
                    //api error
                    Log.e(APP + " Class: " + TAG, "API EXCEPTION  in getting player details" + status);
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
                updateFragment(tankList);
            }
        }
    }

    //SHOW PLAYERS
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        // do something with the data
        //get player from postion
        //give it to the main activity
        mListener.onTankSelectedListener((Tank) mListAdapter.getItem(position));
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


    public interface onTankSelectedListener {
        /**
         * If a tank is selected in the list send the tank to the main activity
         * The main activity will get details and show a detail view
         *
//         * @param Tank selected tank
         */
        public void onTankSelectedListener(Tank tank);
    }
}

