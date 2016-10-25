package com.apporio.dailysparkle;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

public class SplashActivity extends Activity {
    SharedPreferences prefs2;
    SharedPreferences.Editor edit2;
    boolean previouslyStarted;

    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        prefs2 = PreferenceManager.getDefaultSharedPreferences(SplashActivity.this);
        edit2 = prefs2.edit();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        previouslyStarted = prefs.getBoolean("pref_previously_started", false);

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            try {

                String permission = "android.permission.WRITE_EXTERNAL_STORAGE";

                int permissionCheck = ContextCompat.checkSelfPermission(SplashActivity.this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE);

                if (ContextCompat.checkSelfPermission(SplashActivity.this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {

                    // Should we show an explanation?
                    if (ActivityCompat.shouldShowRequestPermissionRationale(SplashActivity.this,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                        Toast.makeText(getApplicationContext(), "Please allow phone permission to receive push notifications !!!", Toast.LENGTH_LONG).show();
                        // Show an expanation to the user *asynchronously* -- don't block
                        // this thread waiting for the user's response! After the user
                        // sees the explanation, try again to request the permission.


                    } else {

                        // No explanation needed, we can request the permission.

                        ActivityCompat.requestPermissions(SplashActivity.this,
                                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                MY_PERMISSIONS_REQUEST_READ_CONTACTS);

                        Handler handler = new Handler();

                        handler.postDelayed(new Runnable() {

                            @Override
                            public void run()

                            {

                                    Intent i=new Intent(SplashActivity.this,LoginActivity.class);
                                    startActivity(i);
                                    finish();


                            }
                            //startThread();
                        }
                                , 3000);


                    }
                }
                else {

                    Handler handler = new Handler();

                    handler.postDelayed(new Runnable() {

                        @Override
                        public void run()

                        {

                                Intent i=new Intent(SplashActivity.this,LoginActivity.class);
                                startActivity(i);
                                finish();


                        }
                        //startThread();
                    }
                            , 3000);
                }
            } catch (Exception e) {
                Log.e("ghghghhg", "" + e);
            }
        }
        else {

            Handler handler = new Handler();

            handler.postDelayed(new Runnable() {

                @Override
                public void run()

                {

                        Intent i=new Intent(SplashActivity.this,LoginActivity.class);
                        startActivity(i);
                        finish();


                }
                //startThread();
            }
                    , 3000);
        }

        // parsingfornotification.parsing(MainActivity.this,regId,dd);

    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_CONTACTS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {



                    Handler handler = new Handler();

                    handler.postDelayed(new Runnable() {

                        @Override
                        public void run()

                        {

                                Intent i=new Intent(SplashActivity.this,LoginActivity.class);
                                startActivity(i);
                                finish();

                        }
                        //startThread();
                    }
                            , 3000);
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }






    }
}
