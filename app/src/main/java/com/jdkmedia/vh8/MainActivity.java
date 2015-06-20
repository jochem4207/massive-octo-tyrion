package com.jdkmedia.vh8;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.jdkmedia.vh8.auth.LoginActivity;
import com.jdkmedia.vh8.domain.Player;
import com.jdkmedia.vh8.domain.PlayerExtended;
import com.jdkmedia.vh8.domain.PlayerTank;
import com.jdkmedia.vh8.fragment.MainActivityFragment;
import com.jdkmedia.vh8.fragment.NavigationDrawerFragment;
import com.jdkmedia.vh8.fragment.PlayerDetailFragment;
import com.jdkmedia.vh8.fragment.PlayerDetailInnerFragment;
import com.jdkmedia.vh8.fragment.PlayerSearchMainFragment;
import com.jdkmedia.vh8.fragment.TankListFragment;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.List;
import java.util.Map;


public class MainActivity extends Activity implements MainActivityFragment.OnLoadDetailFragment, NavigationDrawerFragment.NavigationDrawerCallbacks, PlayerSearchMainFragment.OnPlayerSelectedListener, TankListFragment.onTankListInteractionListener {
    //Logging
    public final String TAG = getClass().getName() + " ";
    public static final String APP = "World of tanks ";

    //API
    private static final String APPLICATION_ID = "?application_id=74da03a344137eb2756c49c9e9069092";
    private static final String API_URL = "http://api.worldoftanks.eu/wot/";
    private static final String API_CALL = "account/info/";
    private static final String API_OPTION = "&account_id=";
    private static final String API_OPTION_AUTH = "&access_token=";


    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;
    private CharSequence mTitle;

    //Auth player
    private String accessToken;
    private boolean isLoggedIn;
    private PlayerExtended loggedInPlayer = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Get bundle
        Bundle b = getIntent().getExtras();

        //Get access token
        if (b != null && !b.getBoolean("error")) {

            //Get properties from bundle
            accessToken = b.getString("accessToken");
            String nickName = b.getString("nickName");
            int accountId = b.getInt("accountId");

            //Set logged in flag (for menu and checks)
            isLoggedIn = true;

            //Create new player for detailed information
            loggedInPlayer = new PlayerExtended(nickName, accountId, accessToken);

            Toast.makeText(this, getString(R.string.welcome_logged_in_user) + " " + nickName, Toast.LENGTH_LONG).show();

            //Log
            Log.d(APP + " Class: " + TAG, "Access token" + accessToken);
            Log.d(APP + " Class: " + TAG, "Player created" + loggedInPlayer.toString());

        } else {
            isLoggedIn = false;
            Log.d(APP + " Class: " + TAG, "Access token is null");
        }

        //Set view
        setContentView(R.layout.activity_main);

        //Get the nav drawer.
        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        //Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
    }


    public void onNavigationDrawerItemSelected(int position, boolean fromSavedInstanceState) {

        // update the main content by replacing fragments

        //if for example the orientation changed the fromSavedInstance will be true.
        //When the item in the nav drawer menu is clicked it will be false
        if (!fromSavedInstanceState) {

            //Declare fragment
            Fragment fragment;
            switch (position) {
                case 0:
                    //Give the logged in player to the mainActivity fragment so it can load details for the logged in player
                    fragment = MainActivityFragment.newInstance(loggedInPlayer);
                    Log.d(APP + " Class: " + TAG, "Main activity fragment selected");
                    break;
                case 1:
                    fragment = PlayerSearchMainFragment.newInstance();
                    Log.d(APP + " Class: " + TAG, "Player search  fragment selected");
                    break;
                case 2:
                    fragment = new TankListFragment();
                    Log.d(APP + " Class: " + TAG, "Tank list fragment selected");
                    break;
                default:
                    //Give the logged in player to the mainActivity fragment so it can load details for the logged in player
                    fragment = MainActivityFragment.newInstance(loggedInPlayer);
                    Log.d(APP + " Class: " + TAG, " default fragment selected");
                    break;
            }

            if (fragment != null) {
                Log.d(APP + " Class: " + TAG, "Fragment is not null - transaction");
                //Get the manager
                FragmentManager fragmentManager = getFragmentManager();
                //Replace the fragment
                //Add it to the backstack
                fragmentManager.beginTransaction()
                        .replace(R.id.container, fragment, fragment.getClass().getName()).addToBackStack(fragment.getClass().getName()).commit();
            }
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {

            //if user is logged in show appropiate menu
            if (isLoggedIn) {
                Log.d(APP + " Class: " + TAG, "User is logged in, logged in menu is shown");
                getMenuInflater().inflate(R.menu.main_logged_in, menu);

            } else {
                Log.d(APP + " Class: " + TAG, "User is not logged in, default menu is shown");
                getMenuInflater().inflate(R.menu.main, menu);
            }

            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            Log.d(APP + " Class: " + TAG, "Settings activity started (user clicked on settings in actionbar");
            //Open settings activity
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }
        if (item.getItemId() == R.id.action_login) {
            Log.d(APP + " Class: " + TAG, "Login activity started (user clicked on login in actionbar");
            //Open login activity
            startActivity(new Intent(this, LoginActivity.class));
            return true;
        }

        if (item.getItemId() == R.id.action_logout) {
            Log.d(APP + " Class: " + TAG, "Main activity is restarted (user clicked on logout in actionbar");
            //User logged out, change properties
            isLoggedIn = false;
            accessToken = null;

            //Restart the mainactivity
            startActivity(new Intent(this, MainActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * FRAGMENTS *
     */

    //Player search fragment
    @Override
    public void onPlayerSelected(Player player) {
        //Call api to get a complete user
        MyTaskParams taskParams;

        //Check if user is logged in
        //If so use accestoken to get extra details
        if (accessToken == null && !isLoggedIn) {
            Log.d(APP + " Class: " + TAG, "OnPlayerSelected user is not logged in");
            taskParams = new MyTaskParams(API_URL + API_CALL + APPLICATION_ID + API_OPTION + player.getAccountId(), false, player.getAccountId());
        } else {
            Log.d(APP + " Class: " + TAG, "OnPlayerSelected user is logged in");
            taskParams = new MyTaskParams(API_URL + API_CALL + APPLICATION_ID + API_OPTION_AUTH + accessToken + API_OPTION + player.getAccountId(), false, player.getAccountId());
        }

        //Execute the call
        new CallAPI().execute(taskParams);
    }


    @Override
    public void onLoadDetailFragment(PlayerExtended playerExtended) {
        Log.d(APP + " Class: " + TAG, "OnPlayerSelected user is logged in");
        MyTaskParams taskParams = new MyTaskParams(API_URL + API_CALL + APPLICATION_ID + API_OPTION_AUTH + accessToken + API_OPTION + playerExtended.getAccountId(), true, playerExtended.getAccountId());
        new CallAPI().execute(taskParams);
    }


    /**
     * Class to give multiple params to the asynctask
     * url: The url to get the json from
     * innerfragment: home screen or detail screen (true or false)
     * accountId: the account id from the player to get details from
     */
    private static class MyTaskParams {
        String url;
        boolean innerFragment;
        int accountId;

        MyTaskParams(String url, boolean innerFragment, int accountId) {
            this.url = url;
            this.accountId = accountId;
            this.innerFragment = innerFragment;
        }
    }

    /*
        API to get information from players to show detailed view
     */
    public class CallAPI extends AsyncTask<MyTaskParams, Void, Boolean> {
        private PlayerExtended playerExtended;
        private boolean innerFragment = false;

        @Override
        protected Boolean doInBackground(MyTaskParams... params) {

            //Get properties from params
            String urlString = params[0].url;
            innerFragment = params[0].innerFragment;

            Log.d(APP + " Class: " + TAG, "Async task: Get player details: Url:" + urlString);

            // Get output from api
            try {
                //Create url
                URL url = new URL(urlString);
                //open stream
                InputStream input = url.openStream();
                //Open reader
                Reader reader = new InputStreamReader(input);

                //Parse the result
                JsonElement root = new JsonParser().parse(reader);
                //Get the status
                String status = root.getAsJsonObject().get("status").getAsString();
                //if status is ok
                if (status.equals("ok")) {
                    //Get JsonObject data
                    JsonObject obj = root.getAsJsonObject().get("data").getAsJsonObject();

                    //For every entry set create a playerExtended
                    for (Map.Entry<String, JsonElement> entry : obj.entrySet()) {
                        //Can be multiple players (api allows, app does not!)
                        playerExtended = new Gson().fromJson(entry.getValue(), PlayerExtended.class);
                        Log.d(APP + " Class: " + TAG, playerExtended.getNickname());
                    }

                } else if (status.equals("error")) {
                    //api error
                    Log.d(APP + " Class: " + TAG, "API EXCEPTION");
                }

            }catch (IOException e) {

                Log.e(APP + " Class: " + TAG, "IOException in getting player details :" + e);
                return false;
            }

            try{

                //Get players tanks info
                URL urlGetPlayerTanks = new URL("https://api.worldoftanks.eu/wot/account/tanks/?application_id=" + APPLICATION_ID +"&account_id=" + params[0].accountId);
                //open stream
                InputStream inputPlayerTanks = urlGetPlayerTanks.openStream();
                //Open reader
                Reader readerPlayerTanks = new InputStreamReader(inputPlayerTanks);

                //Parse the result
                JsonElement rootPlayerTanks = new JsonParser().parse(readerPlayerTanks);
                //Get the status
                String statusPlayerTanks = rootPlayerTanks.getAsJsonObject().get("status").getAsString();
                if (statusPlayerTanks.equals("ok")) {

                    JsonObject obj1 = rootPlayerTanks.getAsJsonObject().get("data").getAsJsonObject();
                    for (Map.Entry<String, JsonElement> entry : obj1.entrySet()) {
                        Type listType = new TypeToken<List<PlayerTank>>() {}.getType();
                        List<PlayerTank> playerTanks = (List<PlayerTank>) new Gson().fromJson(entry.getValue(), listType);
                        playerExtended.setPlayerTankList(playerTanks);
                    }
                }
            } catch (IOException e) {
                Log.e(APP + " Class: " + TAG, "IOException in getting player tanks:" + e);
                return false;
            }


            return true;
        }

        protected void onPostExecute(Boolean result) {
            Log.d(APP + " Class: " + TAG, "Result onpost execute:" + result);
            if (result) {

                //Only get details for logged in player if its the inner fragment
                if (loggedInPlayer != null && innerFragment) {
                    playerExtended.setAccessToken(accessToken);
                }

                FragmentManager fragmentManager = getFragmentManager();

                if (innerFragment) {
                    PlayerDetailInnerFragment playerDetailInnerFragment = new PlayerDetailInnerFragment(playerExtended);
                    fragmentManager.beginTransaction()
                            .replace(R.id.child_fragment, playerDetailInnerFragment, playerDetailInnerFragment.getClass().getName()).addToBackStack(playerDetailInnerFragment.getClass().getName()).commit();
                } else {

                    PlayerDetailFragment playerDetailFragment = new PlayerDetailFragment(playerExtended);
                    fragmentManager.beginTransaction()
                            .replace(R.id.container, playerDetailFragment, playerDetailFragment.getClass().getName()).addToBackStack(playerDetailFragment.getClass().getName()).commit();
                }
                Log.d(APP + " Class: " + TAG, "Transaction committed");
            }
        }

    }

    /**
     * Called when the user touches the button on the home screen
     */
    public void loadPlayers(View view) {
        PlayerSearchMainFragment playerSearchMainFragment = new PlayerSearchMainFragment();
        replaceFragment(playerSearchMainFragment);
    }

    /**
     * Called when the user touches the button on the home screen
     */
    public void loadTanks(View view) {
        TankListFragment tankListFragment = new TankListFragment();
        replaceFragment(tankListFragment);
    }


    public void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, fragment, fragment.getClass().getName()).addToBackStack(fragment.getClass().getName()).commit();
    }


    @Override
    public void onTankListFragmentInteraction(Uri uri) {

    }

}
