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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.Volley;
import com.apporio.dailysparkle.Database.DBManager;
import com.apporio.dailysparkle.Database.PdfTable;
import com.cunoraz.gifview.library.GifView;
import com.squareup.picasso.Picasso;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.logging.Logger;

import io.realm.Realm;

public class MainActivity extends Activity {
    TextView firstday, secondday, thirdday, fourthday, fifthday, sixthday, seventhday;
    TextView masterhead;
    ImageView img;
    public Realm myRealm;
    SharedPreferences prefs;
    int parsing_count = 0;
    int parsing_count2 = 0;
    int count =1;
    int weekcount =0;
    ArrayList<String> dates = new ArrayList<>();
    public static DBManager dbm;
    File cacheFile ;
    String filenames="";
    int count2 ;
    LinearLayout llforinfo;
    ArrayList<String> weeks = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myRealm = Realm.getInstance(MainActivity.this);
        firstday = (TextView) findViewById(R.id.first);
        secondday = (TextView) findViewById(R.id.second);
        thirdday = (TextView) findViewById(R.id.third);
        fourthday = (TextView) findViewById(R.id.fourth);
        fifthday = (TextView) findViewById(R.id.fifth);
        sixthday = (TextView) findViewById(R.id.sixth);
        seventhday = (TextView) findViewById(R.id.seven);
        masterhead = (TextView) findViewById(R.id.mh);
        img = (ImageView) findViewById(R.id.img);
        llforinfo = (LinearLayout)findViewById(R.id.gif1);;
        parsing_count = 0;
        llforinfo.setVisibility(View.GONE);
        dbm = new DBManager(MainActivity.this);

        prefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy_MM_dd");
        for (int i = 0; i < 7; i++) {
            Log.e("dateTag", sdf.format(cal.getTime()));
            weeks.add("" + sdf.format(cal.getTime()));
            dates.add(""+sdf2.format(cal.getTime()));
            cal.add(Calendar.DAY_OF_WEEK, 1);
        }
        firstday.setText("" + weeks.get(0));
        secondday.setText("" + weeks.get(1));
        thirdday.setText("" + weeks.get(2));
        fourthday.setText("" + weeks.get(3));
        fifthday.setText("" + weeks.get(4));
        sixthday.setText("" + weeks.get(5));
        seventhday.setText("" + weeks.get(6));
        getDataFolder(MainActivity.this);
        get_All_Pdf(weeks);
        masterhead.setText("" + prefs.getString("masterhead", "Lindsayfield Lodge"));
        Picasso.with(MainActivity.this)
                .load(prefs.getString("userimage", ""))
                .placeholder(R.drawable.tree) // optional
                .error(R.drawable.tree)         // optional
                .into(img);
        firstday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, PhotoInnerActivity.class);
                i.putExtra("first_page", "Daily_Sparkle" + dates.get(0) + "_" + 1);
                i.putExtra("second_page", "Daily_Sparkle" + dates.get(0) + "_" + 2);
                i.putExtra("third_page", "Daily_Sparkle" + dates.get(0) + "_" + 3);
                i.putExtra("fourth_page", "Daily_Sparkle" + dates.get(0) + "_" + 4);
                i.putExtra("music", "Daily_Sparkle" + dates.get(0) + ".mp3");
                startActivity(i);

            }
        });
        secondday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, PhotoInnerActivity.class);
                i.putExtra("first_page","Daily_Sparkle"+dates.get(1)+"_"+1);
                i.putExtra("second_page","Daily_Sparkle"+dates.get(1)+"_"+2);
                i.putExtra("third_page","Daily_Sparkle"+dates.get(1)+"_"+3);
                i.putExtra("fourth_page","Daily_Sparkle"+dates.get(1)+"_"+4);
                i.putExtra("music","Daily_Sparkle"+dates.get(1)+".mp3");
                startActivity(i);

            }
        });
        thirdday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, PhotoInnerActivity.class);
                i.putExtra("first_page","Daily_Sparkle"+dates.get(2)+"_"+1);
                i.putExtra("second_page","Daily_Sparkle"+dates.get(2)+"_"+2);
                i.putExtra("third_page","Daily_Sparkle"+dates.get(2)+"_"+3);
                i.putExtra("fourth_page","Daily_Sparkle"+dates.get(2)+"_"+4);
                i.putExtra("music","Daily_Sparkle"+dates.get(2)+".mp3");
                startActivity(i);

            }
        });
        fourthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, PhotoInnerActivity.class);
                i.putExtra("first_page","Daily_Sparkle"+dates.get(3)+"_"+1);
                i.putExtra("second_page","Daily_Sparkle"+dates.get(3)+"_"+2);
                i.putExtra("third_page","Daily_Sparkle"+dates.get(3)+"_"+3);
                i.putExtra("fourth_page","Daily_Sparkle"+dates.get(3)+"_"+4);
                i.putExtra("music","Daily_Sparkle"+dates.get(3)+".mp3");
                startActivity(i);

            }
        });
        fifthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, PhotoInnerActivity.class);
                i.putExtra("first_page","Daily_Sparkle"+dates.get(4)+"_"+1);
                i.putExtra("second_page","Daily_Sparkle"+dates.get(4)+"_"+2);
                i.putExtra("third_page","Daily_Sparkle"+dates.get(4)+"_"+3);
                i.putExtra("fourth_page","Daily_Sparkle"+dates.get(4)+"_"+4);
                i.putExtra("music","Daily_Sparkle"+dates.get(4)+".mp3");
                startActivity(i);

            }
        });
        sixthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, PhotoInnerActivity.class);
                i.putExtra("first_page","Daily_Sparkle"+dates.get(5)+"_"+1);
                i.putExtra("second_page","Daily_Sparkle"+dates.get(5)+"_"+2);
                i.putExtra("third_page","Daily_Sparkle"+dates.get(5)+"_"+3);
                i.putExtra("fourth_page","Daily_Sparkle"+dates.get(5)+"_"+4);
                i.putExtra("music","Daily_Sparkle"+dates.get(5)+".mp3");
                startActivity(i);

            }
        });
        seventhday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, PhotoInnerActivity.class);
                i.putExtra("first_page","Daily_Sparkle"+dates.get(6)+"_"+1);
                i.putExtra("second_page","Daily_Sparkle"+dates.get(6)+"_"+2);
                i.putExtra("third_page","Daily_Sparkle"+dates.get(6)+"_"+3);
                i.putExtra("fourth_page","Daily_Sparkle"+dates.get(6)+"_"+4);
                i.putExtra("music","Daily_Sparkle"+dates.get(6)+".mp3");
                startActivity(i);

            }
        });



    }

    public void get_All_Pdf(ArrayList<String> weeks) {
        parsing_count=0;
        for (int i = 0; i < dates.size(); i++) {
//            Daily_Sparkle"+dates.get(weekcount)+"_"+count
            for(int j=1;j<5;j++) {
                if (checkpdfExsistance("Daily_Sparkle"+dates.get(i)+"_"+count) == false) {
                    parsing_count = 1;
                    dbm.clearCartTable();
                }
            }
        }

        if (parsing_count == 1) {
            llforinfo.setVisibility(View.GONE);
            Do_parsing(weeks);
        }
        else {
            for (int i=0;i<dates.size();i++){
                if(checkpdfExsistancesong( "Daily_Sparkle"+dates.get(i)+".mp3")==false){
                    parsing_count2 =1;
                }
            }
            if(parsing_count2==1){
                String url ="http://www.sparkle-hq.com/pdf_editions/DS_";
                String url2=dates.get(0);
                String filename = "Daily_Sparkle"+dates.get(0);
                parsingVolleyMp3(url,url2,filename,0);
            }
        }

    }

    public void Do_parsing(ArrayList<String> weeks) {
//        for (int i = 0; i < weeks.size(); i++) {
//            for (int j = 0; j < 4; j++) {
//                new DownloadFileFromURL().execute(file_url);
//            }
//        }
        count=1;
        String url ="http://www.sparkle-hq.com/pdf_editions/DS_";
        String url2 = dates.get(weekcount)+"."+count;
        String filename = "Daily_Sparkle"+dates.get(weekcount)+"_"+count;
        parsingPdfAndSave(url, url2, filename, count);

    }

    private void parsingPdfAndSave(String url,String url2, String filename, int i) {
//        new DownloadFileFromURL(filename,i).execute(url);
        parsingVolley(url, url2, filename, i);
    }

    public void parsingVolley(String url,String url2, String filename, int i) {

       this. filenames =filename;
        this.count2 = i;
        String mUrl= url+url2+".pdf";
        Log.e("jdfhjd",""+mUrl+" "+url2+"    " +filenames+" "+count2);
        InputStreamVolleyRequest request = new InputStreamVolleyRequest(Request.Method.GET, mUrl,
                new Response.Listener<byte[]>() {
                    @Override
                    public void onResponse(byte[] response) {
                        // TODO handle the response
                        try {

                            if (response!=null) {
                                llforinfo.setVisibility(View.GONE);
                                int count=0;
                                getDataFolder(MainActivity.this);
                                FileOutputStream output = new FileOutputStream(new File(getDataFolder(MainActivity.this),
                                        filenames+".pdf"));
                                     output.write(response);
                                output.flush();
                                output.close();
                                savaBookLocaly(filenames, getDataFolder(MainActivity.this)+"/" + filenames + ".pdf");
//                                Toast.makeText(MainActivity.this, "Download complete.", Toast.LENGTH_LONG).show();
                            }
                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                            Log.e("KEY_ERROR", "UNABLE TO DOWNLOAD FILE  "+e);
                            llforinfo.setVisibility(View.GONE);
                            e.printStackTrace();
                        }
                    }
                } ,new Response.ErrorListener() {



            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO handle the error
                error.printStackTrace();
                llforinfo.setVisibility(View.VISIBLE);
                if(error instanceof TimeoutError){
                    Toast.makeText(MainActivity.this, "Network Problem", Toast.LENGTH_SHORT).show();
                    finish();
                    Intent in = new Intent(MainActivity.this,SplashActivity.class);
                    startActivity(in);
                }
                if(error instanceof NoConnectionError){
                    Toast.makeText(MainActivity.this, "Network Problem", Toast.LENGTH_SHORT).show();
                    finish();
                    Intent in = new Intent(MainActivity.this,SplashActivity.class);
                    startActivity(in);
                }
                Log.e("dsds",""+error);
            }
        }, null);
        RequestQueue mRequestQueue = Volley.newRequestQueue(getApplicationContext(), new HurlStack());
        mRequestQueue.add(request);
        llforinfo.setVisibility(View.VISIBLE);
    }


    @Override
    protected void onResume() {
        super.onResume();
//        get_All_Pdf(weeks);
    }

            private void savaBookLocaly(String pdfname,String pdflocation) {
                dbm.addtocart(pdfname, pdflocation);
                count++;
                if(count>4){
                    count=1;
                    if(weekcount>6){

                        weekcount = 0;

                        Log.e("weekcount","endddddddd");
                    }
                    else {
                        weekcount++;
                        if(weekcount==7){
                            String url ="http://www.sparkle-hq.com/pdf_editions/DS_";
                            String url2=dates.get(0);
                            String filename = "Daily_Sparkle"+dates.get(0);
                            parsingVolleyMp3(url,url2,filename,0);
                        }
                        Log.e("weekcount_ccc",""+weekcount+" "+count);
                        String url ="http://www.sparkle-hq.com/pdf_editions/DS_";
                        String url2=dates.get(weekcount)+"."+count;
                        String filename = "Daily_Sparkle"+dates.get(weekcount)+"_"+count;
                        parsingPdfAndSave(url,url2, filename,count);
                    }

                }
                else {
                    Log.e("weekcount",""+weekcount+" count"+count);
                    String url ="http://www.sparkle-hq.com/pdf_editions/DS_";
                    String url2=dates.get(weekcount)+"."+count;
                    String filename = "Daily_Sparkle"+dates.get(weekcount)+"_"+count;
                    parsingPdfAndSave(url,url2, filename,count);
                }



            }
    private void savaBookLocalymp3(String pdfname,String pdflocation,int countsss) {
        dbm.addtocart(pdfname, pdflocation);
        String url ="http://www.sparkle-hq.com/pdf_editions/DS_";
        try {
            if (countsss < 7) {
                int counts22 = countsss+1;
                String url2 = dates.get(counts22);
                String filename = "Daily_Sparkle" + dates.get(counts22);
                parsingVolleyMp3(url, url2, filename, counts22);
            }
            else {
                llforinfo.setVisibility(View.GONE);
            }
        }
        catch (Exception e){
            Log.e("error",""+e);
        }



        }


            public File getDataFolder(Context context) {
                File dataDir = null;
                if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                    dataDir = new File(Environment.getExternalStorageDirectory(), "Daily_Sparkle");
                    if (!dataDir.isDirectory()) {
                        dataDir.mkdirs();
                    }
                }
                if (!dataDir.isDirectory()) {
                    dataDir = context.getFilesDir();
                    dataDir = new File(Environment.getExternalStorageDirectory(), "Daily_Sparkle");
                }
                return dataDir;
            }
    public boolean checkpdfExsistance(String pdfname) {

        boolean value = false;


        if (myRealm.where(PdfTable.class)
                .equalTo("Pdfname", "" + pdfname)
                .count() == 0) {
            value = false;
        } else {
            value = true;


        }
        Log.e("valuesscheck", "" + value);
        return value;

    }
    public boolean checkpdfExsistancesong(String pdfname) {

        boolean value = false;


        if (myRealm.where(PdfTable.class)
                .equalTo("Pdfname", "" + pdfname)
                .count() == 0) {
            value = false;
        } else {
            value = true;

        }
        Log.e("valuesscheck", "" + value);
        return value;

    }

    public void parsingVolleyMp3(String url, String url2, final String filename,  int i) {

        this. filenames =filename;
        this.count2=i;
        String mUrl= url+url2+".mp3";
        Log.e("jdfhjd",""+mUrl+" "+url2+"    " +filenames+" "+count2);
        InputStreamVolleyRequest request = new InputStreamVolleyRequest(Request.Method.GET, mUrl,
                new Response.Listener<byte[]>() {
                    @Override
                    public void onResponse(byte[] response) {
                        // TODO handle the response
                        try {

                            if (response!=null) {
                                llforinfo.setVisibility(View.GONE);
                                int count=0;
                                getDataFolder(MainActivity.this);
                                FileOutputStream output = new FileOutputStream(new File(getDataFolder(MainActivity.this),
                                        filenames+".mp3")
                                        );
                                output.write(response);
                                output.flush();
                                output.close();
                                savaBookLocalymp3(filenames +
                                        ".mp3", getDataFolder(MainActivity.this)+"/"+
                                        filenames + ".mp3",count2);
//                                Toast.makeText(MainActivity.this, "Download complete.", Toast.LENGTH_LONG).show();
                            }
                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                            Log.e("KEY_ERROR", "UNABLE TO DOWNLOAD FILE  "+e);
                            llforinfo.setVisibility(View.GONE);
                            e.printStackTrace();
                        }
                    }
                } ,new Response.ErrorListener() {



            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO handle the error
                error.printStackTrace();
                llforinfo.setVisibility(View.VISIBLE);
                if(error instanceof TimeoutError){
                    Toast.makeText(MainActivity.this, "Network Problem", Toast.LENGTH_SHORT).show();
                    finish();
                    Intent in = new Intent(MainActivity.this,SplashActivity.class);
                    startActivity(in);
                }
                if(error instanceof NoConnectionError){
                    Toast.makeText(MainActivity.this, "Network Problem", Toast.LENGTH_SHORT).show();
                    finish();
                    Intent in = new Intent(MainActivity.this,SplashActivity.class);
                    startActivity(in);
                }
                Log.e("dsds",""+error);
            }
        }, null);
        RequestQueue mRequestQueue = Volley.newRequestQueue(getApplicationContext(), new HurlStack());
        mRequestQueue.add(request);
        llforinfo.setVisibility(View.VISIBLE);
    }

}


////////////////////////////////////////



//        class DownloadFileFromURL extends AsyncTask<String, String, String> {
//            String filenames="";
//            int count ;
//            public DownloadFileFromURL(String filename, int i) {
//                this.filenames = filename;
//                this.count =i;
//            }
//
//            @Override
//            protected void onPreExecute() {
//                super.onPreExecute();
//
//            }
//
//            @Override
//            protected String doInBackground(String... f_url) {
//                int count;
//                try {
//                    Log.e("urlsssssssssss",""+f_url);
//                    URL url = new URL(f_url[0]);
//                    URLConnection conection = url.openConnection();
//                    conection.connect();
//                    int lenghtOfFile = conection.getContentLength();
//
//                    // download the file
//                    InputStream input = new BufferedInputStream(url.openStream(), 8192);
//                    File cacheDir = getDataFolder(MainActivity.this);
//                    String newbookname = filenames.replace(" ", "_");
//                    cacheFile = new File(cacheDir, newbookname + ".pdf");
//                    Log.e("file path ","" + newbookname);
//                    FileOutputStream output = new FileOutputStream(cacheFile);
//                    byte data[] = new byte[1024];
//                    long total = 0;
//                    while ((count = input.read(data)) != -1) {
//                        total += count;
//                        // publishing the progress....
//                        // After this onProgressUpdate will be called
//                        publishProgress("" + (int) ((total * 100) / lenghtOfFile));
//                        // writing data to file
//                        output.write(data, 0, count);
//                    }
//                    // flushing output
//                    output.flush();
//                    // closing streams
//                    output.close();
//                    input.close();
//                } catch (Exception e) {
//                    Log.e("Error: ", e.getMessage());
//                }
//                return null;
//            }
//
//
//            protected void onProgressUpdate(String... progress) {
//                // setting progress percentage
//            }
//
//
//            @Override
//            protected void onPostExecute(String file_url) {
////            FileaName.FilePath = ""+getDataFolder(ConfirmOrder.this)+"/"+BookNAME.replace(" " , "_")+".epub";
////            FileaName.FileNAME = ""+BookNAME.replace(" " , "_"+".epub");
////            startActivity(new Intent(ConfirmOrder.this, MainActivityEPUBSamir.class));
//                savaBookLocaly(filenames,cacheFile+"");
//
//            }
//
//        }
