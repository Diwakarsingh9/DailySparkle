package com.apporio.dailysparkle;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.apporio.dailysparkle.Parsing.parsingforLogin;
import com.cunoraz.gifview.library.GifView;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Logger;

public class LoginActivity extends Activity {
    TextView login,help;
    LinearLayout llforinfo;
    EditText username,userid;
   public static GifView pb;
    SharedPreferences prefs;
    public static LoginActivity loginact;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_acitivity);
        login = (TextView)findViewById(R.id.login);
        help = (TextView)findViewById(R.id.help);
        llforinfo = (LinearLayout)findViewById(R.id.llforinfo);
        username = (EditText)findViewById(R.id.usernaem);
        userid = (EditText)findViewById(R.id.userid);
        pb = (GifView)findViewById(R.id.gif1);
        loginact = LoginActivity.this;
        llforinfo.setVisibility(View.INVISIBLE);
        prefs = PreferenceManager.getDefaultSharedPreferences(LoginActivity.this);

        userid.setText(prefs.getString("userid", ""));
        username.setText(prefs.getString("username", ""));
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                parsingforLogin.parsingEmail(LoginActivity.this,
                        userid.getText().toString().trim(), username.getText().toString().trim());
            }
        });

        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llforinfo.setVisibility(View.VISIBLE);
            }
        });

    }



}
