package com.apporio.dailysparkle;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.media.AudioManager;

import android.media.MediaPlayer;
import android.os.Build;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.apporio.dailysparkle.pdfviewer.PDFView;

import com.apporio.dailysparkle.pdfviewer.listener.OnDrawListener;
import com.apporio.dailysparkle.pdfviewer.listener.OnErrorListener;
import com.apporio.dailysparkle.pdfviewer.listener.OnLoadCompleteListener;
import com.apporio.dailysparkle.pdfviewer.listener.OnPageChangeListener;
import com.apporio.dailysparkle.pdfviewer.listener.OnPageScrollListener;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Image;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import com.squareup.picasso.Picasso;


import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Arrays;

public class PhotoInnerActivity extends Activity implements OnErrorListener,
        OnDrawListener,OnLoadCompleteListener,OnPageChangeListener,OnPageScrollListener {
    ImageView home,leftarrow,rightarrow,music;
     ImageView tree;
    PDFView pdfview1;
    int count =0;
    int musiccount=0;
    TextView heading;
    public static int pageno=0;
    public  static LinearLayout llforhead;
    SharedPreferences prefs;
    public  static PhotoInnerActivity pdp;
    byte[] musics;
    private MediaPlayer mediaPlayer;
    InputStream inStream;
    FileInputStream fis;

    ArrayList<String> directories = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_inner);
        home = (ImageView) findViewById(R.id.home);
        leftarrow = (ImageView) findViewById(R.id.left);
        rightarrow = (ImageView) findViewById(R.id.right);
        tree = (ImageView) findViewById(R.id.imageView3);
        heading = (TextView) findViewById(R.id.heading);
        llforhead = (LinearLayout) findViewById(R.id.llforheading);
        music = (ImageView) findViewById(R.id.music);
        pdfview1 = (PDFView) findViewById(R.id.pdfView1);
        prefs = PreferenceManager.getDefaultSharedPreferences(PhotoInnerActivity.this);
        pdp = PhotoInnerActivity.this;
        heading.setText("" + prefs.getString("masterhead", "Lindsayfield Lodge"));
        Picasso.with(PhotoInnerActivity.this)
                .load(prefs.getString("userimage", ""))
                .placeholder(R.drawable.tree) // optional
                .error(R.drawable.tree)         // optional
                .into(tree);
        llforhead.setVisibility(View.VISIBLE);
        pageno=0;
        try {
            mediaPlayer = new MediaPlayer();
            count=0;
//            Document document = new Document();
//        File f=new File(getDataFolder(PhotoInnerActivity.this)+"/" + getIntent().getStringExtra("first_page")+".pdf");
//            try {
//                PdfWriter.getInstance(document,new FileOutputStream(f));
//
//                document.open();
//                document.add(new Paragraph("Simple Image"));
//
//                Image image =Image.getInstance("sdcard/img.jpg");
//                document.add(image);
//                document.close();
//
//
//            } catch (Exception e) {
//                e.printStackTrace();
//                Log.e("errorddddddddd",""+e);
//            }
            load_pdf(getDataFolder(PhotoInnerActivity.this)+"/" + getIntent().getStringExtra("first_page") + ".pdf");
            directories.add(getDataFolder(PhotoInnerActivity.this)+"/"  + getIntent().getStringExtra("first_page") + ".pdf");
            directories.add(getDataFolder(PhotoInnerActivity.this)+"/"  + getIntent().getStringExtra("second_page") + ".pdf");
            directories.add(getDataFolder(PhotoInnerActivity.this)+"/"  + getIntent().getStringExtra("third_page") + ".pdf");
            directories.add(getDataFolder(PhotoInnerActivity.this)+"/"  + getIntent().getStringExtra("fourth_page") + ".pdf");
        } catch (Exception e) {
            e.printStackTrace();
        }

        Log.e("musicccccccc",""+getIntent()
                .getStringExtra("music"));

//        pdfview1.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                if(pdfview1.getZoom()>12){
//                    tree.setVisibility(View.GONE);
//                }
//                else {
//                    tree.setVisibility(View.VISIBLE);
//
//                }
//                return true;
//            }
//        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });
        music.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
               if(musiccount==0){
                   playMp3(getIntent()
                           .getStringExtra("music"));
                   musiccount=1;
                   music.setBackground(getResources().getDrawable(R.drawable.pressed));
               }
                else {
                   mediaPlayer.stop();
                   musiccount=0;
                   music.setBackground(getResources().getDrawable(R.drawable.unpressed));
               }

            }
        });
        rightarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            int value = count+1;
                count=value;
                pageno =1;
                llforhead.setVisibility(View.GONE);
                if(value>=directories.size()){

                }
                else {
                    Log.e("right",""+value+" direct "+directories.get(value));
                    String directory = directories.get(value);
                    load_pdf(directory);
                }

            }
        });
        leftarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(count==0){

                    //
                }
                else {
                    int value = count - 1;
                    count = value;
                    if(count==0){
                        pageno=0;
                        llforhead.setVisibility(View.VISIBLE);
                    }
                    else {
                        llforhead.setVisibility(View.GONE);
                    }
                    Log.e("right", "" + value + " direct " + directories.get(value));

                    String directory = directories.get(value);
                    load_pdf(directory);
                }
            }
        });

    }

    private void load_pdf(String directory) {
        pdfview1.useBestQuality(true);
        pdfview1.fromFile(new File(directory))
                .enableSwipe(true)
                .swipeHorizontal(false)
                .enableDoubletap(true)
                .defaultPage(0)
                .onDraw(this)
                .onLoad(this)
                .onPageChange(this)
                .onPageScroll(this)
                .onError(this)
                .enableAnnotationRendering(false)
                .password(null)
                .scrollHandle(null)
                .load();
//        pdfview1.setMaxZoom(1000);
//        pdfview1.setMinZoom(10);


    }

    @Override
    public void onLayerDrawn(Canvas canvas, float pageWidth, float pageHeight, int displayedPage) {

    }

    @Override
    public void loadComplete(int nbPages) {

    }

    @Override
    public void onPageChanged(int page, int pageCount) {

    }

    @Override
    public void onPageScrolled(int page, float positionOffset) {

    }

    @Override
    public void onError(Throwable t) {

    }


    private void playMp3(String mpss) {
        try {
            Log.e("playmusic",""+mpss);

        mediaPlayer = new MediaPlayer();
            try {
                mediaPlayer.setDataSource("/sdcard/Daily_Sparkle/"+mpss);
                mediaPlayer.prepare();
                mediaPlayer.start();

            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }

    private byte[] copyOfRange(byte[] mp3SoundByteArray, int i, int i1) {

        return Arrays.copyOfRange(mp3SoundByteArray, i, i1);

    }

    @Override
    protected void onStop() {
        super.onStop();
        try {
            mediaPlayer.stop();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            mediaPlayer.stop();
        } catch (IllegalStateException e) {
            e.printStackTrace();
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


}
