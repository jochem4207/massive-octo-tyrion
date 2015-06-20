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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jdkmedia.vh8.MainActivity;
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

    public final String TAG = getClass().getName() + " ";
    public static final String APP = "JdkMedia ";

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
        setRetainInstance(true);
        setHasOptionsMenu(true);

    }


    @Override
    public void onCreateOptionsMenu (Menu menu, MenuInflater inflater) {
//        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.search_menu, menu);
        MenuItem item = menu.findItem(R.id.search_menu);
        if(getActivity() != null){
            SearchView sv = new SearchView((getActivity()).getActionBar().getThemedContext());
            MenuItemCompat.setShowAsAction(item, MenuItemCompat.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW | MenuItemCompat.SHOW_AS_ACTION_IF_ROOM);
            MenuItemCompat.setActionView(item, sv);
            sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    System.out.println("search query submit");

                    if(query.length() > 3 ){
                        getPlayers(query);
                        return false;
                    }

                    Toast.makeText(getActivity(), getString(R.string.error_fill_field), Toast.LENGTH_LONG).show();
                    return true;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    System.out.println("tap");
                    return false;
                }
            });
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i(APP + " Class: " + TAG, "Loading fragment player");
        //Inflate fragment layout
        View view = inflater.inflate(R.layout.fragment_player_search_view, container, false);

        //Get the listview
        mListView = (ListView) view.findViewById(android.R.id.list);

        Log.i(APP + " Class: " + TAG, "Setting adapter");
        //Initate adapter
        mAdapter = new PlayerListAdapter(getActivity(), new ArrayList<Player>());

        //Set adapter
        mListView.setAdapter(mAdapter);

        // Set OnItemClickListener so we can be notified on item clicks
        mListView.setOnItemClickListener(this);

        Log.i(APP + " Class: " + TAG, "Returning view 1");

        if(savedInstanceState!=null && JsonResult != null)
        {
            JsonResult = (JsonResultPlayerQuery) savedInstanceState.getSerializable("search_result");
        }
        return view;
    }

    @Override
    public void onStart(){
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
    public void onSaveInstanceState (Bundle outState)
    {
        outState.putSerializable("search_result", JsonResult);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e(APP + " Class: " + TAG, "FRAGMENT DEAD");
    }


    //SHOW PLAYERS
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        // do something with the data
        //get player from postion
        //give it to the main activity
        mListener.onPlayerSelected((Player)mAdapter.getItem(position));
        Log.i(APP + " Class: " + TAG, "PlayerListFragment Position clicked" + Integer.toString(position));
    }

    //To update the fragment with data
    private void updateFragment(JsonResultPlayerQuery data){
        Log.i(APP + " Class: " + TAG, "Get new data");
        Log.i(APP + " Class: " + TAG, data.toString());

        // get new modified  data
        List<Player> objects = data.getPlayers();

        Log.i(APP + " Class: " + TAG, "Clear adapter and add new data");
        // update data in our adapter
        mAdapter.getData().clear();
        mAdapter.getData().addAll(objects);
        // Notify adapter that it is changed
        mAdapter.notifyDataSetChanged();
    }

    public interface OnPlayerSelectedListener {
        /**
         * If a player is selected in the list send the player to the main activity
         * The main activity will get details and show a detail view
         * @param player selected player
         */
        public void onPlayerSelected(Player player);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (null != mListener) {
            // Notify the active callbacks interface (the activity, if the
            // fragment is attached to one) that an item has been selected.
            //  mListener.onFragmentInteraction(DummyContent.ITEMS.get(position).id);
        }
    }

    //SEARCH PLAYERS

    public void getPlayers(String query) {
        if(getView() != null){


            //Log the input
            Log.i(APP + " Class: " + TAG, "Search:"+ query);

            //Call api to get result
            new CallAPI().execute(API_URL + API_CALL + APPLICATION_ID + API_OPTION + query);

        }else{
            //Give a toast error
            Toast.makeText(getActivity(), getString(R.string.error_fill_field), Toast.LENGTH_LONG).show();

        }
    }

    private class CallAPI extends AsyncTask<String, Void, Boolean> {
        @Override
        protected Boolean doInBackground(String... params) {
            Log.i(APP + " Class: " + TAG, "JsonAsyncTask URL:"+ params[0]);

            //Get url from params
            String urlString=params[0];

            // Get output from api
            try {
                //Create url
                URL url = new URL(urlString);
                //open stream
                InputStream input = url.openStream();
                //Open reader
                Reader reader = new InputStreamReader(input);
                //Json to class
                JsonResultPlayerQuery result  = new Gson().fromJson(reader, JsonResultPlayerQuery.class);
                if(result != null){
                    JsonResult = result;
                }else{
                    Log.i(APP + " Class: " + TAG, "Unhandled Error in Call API");
                }

            } catch (IOException e) {
                Log.e(APP + " Class: " + TAG, "IOEXception" + e.getMessage());
                e.printStackTrace();
                return false;
            }
            return true;
        }

        protected void onPostExecute(Boolean result) {
            Log.e(APP + " Class: " + TAG, "OnPostExecute result" + result);
            if (!result) {
                Toast.makeText(getActivity(), getString(R.string.could_not_get_data), Toast.LENGTH_LONG).show();
            }else{

                updateFragment(JsonResult);

                Log.e(APP + " Class: " + TAG, "OnPostExecute result player" + JsonResult.toString());
            }
        }
    }
}

//ondestroy -> save your data