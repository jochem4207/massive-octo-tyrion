package com.jdkmedia.vh8;

import android.app.Activity;
import android.app.ActionBar;
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
import com.jdkmedia.vh8.domain.Player;
import com.jdkmedia.vh8.domain.PlayerExtended;
import com.jdkmedia.vh8.auth.LoginActivity;
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
import java.net.URL;
import java.util.Map;


public class MainActivity extends Activity implements MainActivityFragment.OnLoadDetailFragment, NavigationDrawerFragment.NavigationDrawerCallbacks, PlayerSearchMainFragment.OnPlayerSelectedListener, TankListFragment.onTankListInteractionListener {
    public final String TAG = getClass().getName() + " ";
    public static final String APP = "JdkMedia ";

    //API
    private static final String APPLICATION_ID = "?application_id=74da03a344137eb2756c49c9e9069092";
    private static final String API_URL = "http://api.worldoftanks.eu/wot/";
    private static final String API_CALL = "account/info/";
    private static final String API_OPTION = "&account_id=";
    private static final String API_OPTION_AUTH = "&access_token=";

    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

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
            Log.i(APP + " Class: " + TAG, "Access token" + accessToken);
            Log.i(APP + " Class: " + TAG, "Player created" + loggedInPlayer.toString());

        } else {
            isLoggedIn = false;
            Log.e(APP + " Class: " + TAG, "Access token is null");
        }

        //Set player search view as default
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

    private void getFullPlayerDetails() {

    }

    public void onNavigationDrawerItemSelected(int position, boolean fromSavedInstanceState) {
        // update the main content by replacing fragments
        if (!fromSavedInstanceState) {
            Fragment fragment;
            switch (position) {
                case 0:
                    fragment = MainActivityFragment.newInstance(loggedInPlayer);
                    Log.i(APP + " Class: " + TAG, "Main activity fragment selected");
                    break;
                case 1:
                    fragment = PlayerSearchMainFragment.newInstance();
                    Log.i(APP + " Class: " + TAG, "Player search  fragment selected");
                    break;
                case 2:
                    fragment = new TankListFragment();
                    Log.i(APP + " Class: " + TAG, "Tank list fragment selected");
                    break;
                default:
                    fragment = MainActivityFragment.newInstance(loggedInPlayer);
                    Log.i(APP + " Class: " + TAG, " default fragment selected");
                    break;
            }

            if (fragment != null) {
                Log.i(APP + " Class: " + TAG, "Fragment is not null - transaction");
                FragmentManager fragmentManager = getFragmentManager();
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
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.

            if (isLoggedIn) {
                Log.i(APP + " Class: " + TAG, "User is logged in, logged in menu is shown");
                getMenuInflater().inflate(R.menu.main_logged_in, menu);

            } else {
                Log.i(APP + " Class: " + TAG, "User is not logged in, default menu is shown");
                getMenuInflater().inflate(R.menu.main, menu);
            }

            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            Log.i(APP + " Class: " + TAG, "Settings activity started (user clicked on settings in actionbar");
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }
        if (item.getItemId() == R.id.action_login) {
            Log.i(APP + " Class: " + TAG, "Login activity started (user clicked on login in actionbar");
            startActivity(new Intent(this, LoginActivity.class));
            return true;
        }

        if (item.getItemId() == R.id.action_logout) {
            Log.i(APP + " Class: " + TAG, "Main activity is restarted (user clicked on logout in actionbar");
            isLoggedIn = false;
            accessToken = null;
            startActivity(new Intent(this, MainActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * FRAGMENTS *
     */

    //Communication with player related fragments
    /* player fragment */
    @Override
    public void onPlayerSelected(Player player) {
        //Call api to get a complete user
        MyTaskParams taskParams;
        if (accessToken == null && !isLoggedIn) {
            Log.i(APP + " Class: " + TAG, "OnPlayerSelected user is not logged in");
             taskParams = new MyTaskParams(API_URL + API_CALL + APPLICATION_ID + API_OPTION + player.getAccountId(), false);
        } else {
            Log.i(APP + " Class: " + TAG, "OnPlayerSelected user is logged in");
            taskParams = new MyTaskParams(API_URL + API_CALL + APPLICATION_ID + API_OPTION_AUTH + accessToken + API_OPTION + player.getAccountId(), false);
        }
        new CallAPI().execute(taskParams);
    }

    @Override
    public void onTankListFragmentInteraction(Uri uri) {

    }

    @Override
    public void onLoadDetailFragment(PlayerExtended player) {
        Log.i(APP + " Class: " + TAG, "OnPlayerSelected user is logged in");
        MyTaskParams taskParams = new MyTaskParams(API_URL + API_CALL + APPLICATION_ID + API_OPTION_AUTH + accessToken + API_OPTION + player.getAccountId(), true);
        new CallAPI().execute(taskParams);
    }


    private static class MyTaskParams {
        String url;
        boolean innerFragment;

        MyTaskParams(String url, boolean innerFragment) {
            this.url = url;
            this.innerFragment = innerFragment;
        }
    }


    //API + NEW FRAGMENT
    public class CallAPI extends AsyncTask<MyTaskParams, Void, Boolean> {
        private PlayerExtended playerExtended;
        private boolean innerFragment = false;

        @Override
        protected Boolean doInBackground(MyTaskParams... params) {
            //Get url from params
            String urlString = params[0].url;
            innerFragment = params[0].innerFragment;

            Log.i(APP + " Class: " + TAG, "Async task: Get player details: Url:" + urlString);

            // Get output from api
            try {
                //Create url
                URL url = new URL(urlString);
                //open stream
                InputStream input = url.openStream();
                //Open reader
                Reader reader = new InputStreamReader(input);
                //Json to class

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
                        Log.e(APP + " Class: " + TAG, playerExtended.getNickname());
                    }

                    //Achievements
                    //
                } else if (status.equals("error")) {
                    //api error
                    Log.e(APP + " Class: " + TAG, "API EXCEPTION");
                }

            } catch (IOException e) {
                Log.i(APP + " Class: " + TAG, "IOException:" + e);
                return false;
            }

            return true;
        }

        protected void onPostExecute(Boolean result) {
            Log.i(APP + " Class: " + TAG, "Result on post execute:" + result);
            if (result) {

                //Only get details for logged in player if its the inner fragment
                if (loggedInPlayer != null && innerFragment) {
                    playerExtended.setAccessToken(accessToken);
                }

                //Detail fragment


                //Manage fragment
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
                Log.i(APP + " Class: " + TAG, "Transaction committed");
            }
        }

    }

    /**
     * Called when the user touches the button
     */
    public void loadPlayers(View view) {
        PlayerSearchMainFragment playerSearchMainFragment = new PlayerSearchMainFragment();

        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, playerSearchMainFragment, playerSearchMainFragment.getClass().getName()).addToBackStack(playerSearchMainFragment.getClass().getName()).commit();

    }

    /**
     * Called when the user touches the button
     */
    public void loadTanks(View view) {
        TankListFragment tankListFragment = new TankListFragment();

        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, tankListFragment, tankListFragment.getClass().getName()).addToBackStack(tankListFragment.getClass().getName()).commit();

    }
}
