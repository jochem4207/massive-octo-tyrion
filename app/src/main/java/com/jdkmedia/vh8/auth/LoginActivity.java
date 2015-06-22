package com.jdkmedia.vh8.auth;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.jdkmedia.vh8.MainActivity;
import com.jdkmedia.vh8.R;

public class LoginActivity extends Activity {
    public final String TAG = getClass().getName();
    public static final String APP = "JdkMedia ";
    public static final String APP_ID = "74da03a344137eb2756c49c9e9069092";
    public Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activity = this;
        //Set content login
        setContentView(R.layout.activity_login);

        //Get the webview
        WebView webView = (WebView) findViewById(R.id.WebView);

        //Specify url
        String url = "https://api.worldoftanks.eu/wot/auth/login/?application_id="+ APP_ID + "&redirect_uri=https://eu.wargaming.net/developers/api_explorer/wot/auth/login/complete/";

        //Load the login url
        webView.loadUrl(url);

        Log.i(APP + " Class: " + TAG, "Url being loaded" + url);

        //Get every url thats being loaded
        webView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url){


                 //if the url contains a access token, user is logged in and redirected to main activity
                if (url.contains("access_token"))
                {
                    //Parse url
                    Uri uri = Uri.parse(url);

                    //Get status
                    String status = uri.getQueryParameter("status");

                    if(status.equals("ok")){
                        String accessToken = uri.getQueryParameter("access_token");
                        String nickName = uri.getQueryParameter("nickname");
                        String accountId = uri.getQueryParameter("account_id");

                        //Create bundle
                        Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                        Bundle b = new Bundle();

                        boolean error = false;
                        if(accessToken.isEmpty() || accessToken.equals(""))
                        {
                            error = true;
                            b.putBoolean("error", true);
                            b.putString("errorDescription", "accessToken empty");
                            intent.putExtras(b);
                            Log.i(APP + " Class: " + TAG, "Url empty access token");

                        }else if(nickName.isEmpty() || nickName.equals("") || accountId.isEmpty() || accountId.equals(""))
                        {
                            error = true;
                            b.putBoolean("error", true);
                            b.putString("errorDescription", "account details empty");
                            Log.i(APP + " Class: " + TAG, "Url empty account details");
                        }

                        if(!error){
                            b.putBoolean("error", false);
                            b.putString("accessToken", accessToken);
                            b.putString("nickName", nickName);
                            b.putInt("accountId", Integer.parseInt(accountId));

                            intent.putExtras(b);
                            Log.i(APP + " Class: " + TAG, "Retrieving details from url success");
                        }

                        new AlertDialog.Builder(activity)
                                .setTitle(getString(R.string.dialogTitle))
                                .setMessage(getString(R.string.dialogMessage))
                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        //TODO
                                    }
                                })
                                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        //TODO
                                    }
                                })
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();


                        //Start the activity with the values from the bundle
                        startActivity(intent);
                    }
                    return true;
                }
                else
                {
                    Log.i(APP + " Class: " + TAG, "Url being loaded doesn't contain a access token" + url);
                    // view.loadUrl(url);
                   // return false;
                }
                return false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
