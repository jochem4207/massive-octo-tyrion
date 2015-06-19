package com.jdkmedia.vh8;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import java.util.Locale;
import android.content.Intent;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.Log;

import java.util.List;

public class SettingsActivity extends PreferenceActivity implements SharedPreferences.OnSharedPreferenceChangeListener {
    public final String TAG = getClass().getName() + " ";
    public static final String APP = "JdkMedia ";

    SharedPreferences.OnSharedPreferenceChangeListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected boolean isValidFragment (String fragmentName) {
        return true;
        //TODO: FIX
    }

    @Override
    public void onBuildHeaders(List<Header> target) {
        loadHeadersFromResource(R.xml.pref_headers, target);
    }

    @Override
    protected void onResume() {
        super.onResume();
        PreferenceManager.getDefaultSharedPreferences(this).registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        PreferenceManager.getDefaultSharedPreferences(this).unregisterOnSharedPreferenceChangeListener(this);

    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        Log.i(APP + " Class: " + TAG, "Key selected" + key);
        if(key.equals("sync_frequency")){

            //NA is return value if the value doesnt exist
            String lang = sharedPreferences.getString("sync_frequency", "NA");
            Log.i(APP + " Class: " + TAG, "Lang selected" + lang);

            if(!lang.contains("na")){
                Resources res = this.getResources();
                // Change locale settings in the app.
                DisplayMetrics dm = res.getDisplayMetrics();
                android.content.res.Configuration conf = res.getConfiguration();
                conf.locale = new Locale(lang.toLowerCase());
                res.updateConfiguration(conf, dm);
                Intent refresh = new Intent(this, MainActivity.class);
                startActivity(refresh);
            }
        }
    }

    public static class GeneralPreferenceFragment extends PreferenceFragment
    {
        @Override
        public void onCreate(final Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.pref_general);
        }


    }

    public static class NotificationPreferenceFragment extends PreferenceFragment
    {
        @Override
        public void onCreate(final Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.pref_notification);
        }

    }

    public static class DataSyncPreferenceFragment extends PreferenceFragment {
        @Override
        public void onCreate(final Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.pref_sync);
        }
    }
}

/*
    How to get settings
    SharedPreferences SP = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
    String strUserName = SP.getString("username", "NA");
    boolean bAppUpdates = SP.getBoolean("applicationUpdates",false);
    String downloadType = SP.getString("downloadType","1");
 */

/*
    Tutorial from
    http://alvinalexander.com/android/android-tutorial-preferencescreen-preferenceactivity-preferencefragment
 */