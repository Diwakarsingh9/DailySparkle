package com.apporio.dailysparkle.Parsing;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.apporio.dailysparkle.Api_s.Apis_url;
import com.apporio.dailysparkle.LoginActivity;
import com.apporio.dailysparkle.MainActivity;
import com.apporio.dailysparkle.singleton.VolleySingleton;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by apporio6 on 07-09-2016.
 */
public class parsingforLogin {

    public static StringRequest sr;
    public static SharedPreferences prefs2;
    static SharedPreferences.Editor edit2;
    public static RequestQueue queue;




    public static void parsingEmail(final Context c, final String Userid, final String Username) {

        prefs2 = PreferenceManager.getDefaultSharedPreferences(c);
        queue = VolleySingleton.getInstance(c).getRequestQueue();
        //   Toast.makeText(getActivity(),"id"+CategoryId,Toast.LENGTH_SHORT).show();
        String urlforRest_food = Apis_url.Login_email;
        urlforRest_food = urlforRest_food.replace(" ", "%20");
        Log.e("bahjd", "" + urlforRest_food);
        sr = new StringRequest(Request.Method.POST, urlforRest_food, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("Sucess", "" + response);

                LoginActivity.pb.setVisibility(View.GONE);
                try {




//

                JSONArray jsonarray = null;
                    String checker="";

                    jsonarray = new JSONArray(response);

                for (int i = 0; i < jsonarray.length(); i++) {
                    JSONObject jsonobject = jsonarray.getJSONObject(i);
                     checker =jsonobject.getString("idRef");

                }



                if (!checker.equals("invalid")) {

                    JSONArray jsonarray2 = null;
                  String userid="",masterhead="",username="",user_image="";

                    jsonarray2 = new JSONArray(response);

                    for (int i = 0; i < jsonarray2.length(); i++) {
                        JSONObject jsonobject2 = jsonarray2.getJSONObject(i);
                        userid =jsonobject2.getString("idRef");
                        masterhead =jsonobject2.getString("mastheadName");
                        username =jsonobject2.getString("email1");
                        user_image =jsonobject2.getString("graphics");
                    }

//                    Toast.makeText(c, "Welcome " + received3.getEmail1().toString(), Toast.LENGTH_SHORT).show();


                    Log.e("dfd",""+userid);
                    edit2 = prefs2.edit();
                    edit2.putBoolean("pref_previously_started", Boolean.TRUE);
                    edit2.putString("userid", "" + userid);
                    edit2.putString("username",""+username);
                    edit2.putString("userimage",""+user_image);
                    edit2.putString("masterhead",""+masterhead);


                    edit2.commit();
                    Intent in = new Intent(c, MainActivity.class);
                    c.startActivity(in);
                    LoginActivity.loginact.finish();

                } else {

                    Toast.makeText(c, "Login Failed ", Toast.LENGTH_SHORT).show();
                }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //pDialog.dismiss();
                LoginActivity.pb.setVisibility(View.GONE);
                if(error instanceof NoConnectionError) {
                    Toast.makeText(c, "No internet connectivity..", Toast.LENGTH_SHORT).show();
                }
                if(error instanceof TimeoutError) {
                    Toast.makeText(c, "Poor Connection..", Toast.LENGTH_SHORT).show();
                }
                Log.e("Sucess", "" + error.toString());
                // Toast.makeText(getActivity(), "Please enter the email and password", Toast.LENGTH_SHORT).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("user_id", ""+Userid);
                params.put("user_email", ""+Username);
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
//                params.put("Content-Type", "application/x-www-form-urlencoded");
                return params;
            }
        };
        sr.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        queue.add(sr);
//        pd.show();
        LoginActivity.pb.setVisibility(View.VISIBLE);

    }

}
