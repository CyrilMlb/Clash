package com.clash.clash;
import android.app.ProgressDialog;
import android.graphics.*;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.SystemClock;
import android.provider.Settings;
import android.support.v7.app.*;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.*;
import java.net.URL;
import java.util.*;
import android.content.*;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

public class Clash extends AppCompatActivity {
    private RecyclerView myArenaRecyclerView;
    private ArenaAdapter myArenaAdapter;

    private ArrayList<Arena> arenas;

    private ProgressDialog progress;

    private BroadcastReceiver myBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            arenas = intent.getParcelableArrayListExtra("Arenas");

            for(int i = 0; i < arenas.size(); i++) {
                DownloadImage dImg = new DownloadImage();
                dImg.execute("http://www.clashapi.xyz/images/arenas/" + arenas.get(i).getIdName() + ".png", arenas.get(i).getIdName());
            }

            SystemClock.sleep(1000);


            //Log.d("test",""+new File("/data/user/0/com.clash.clash/files/" + arenas.get(0).getIdName()).exists());

            /*boolean test = false;
            while(!test){
                test = true;
                for(int i = 0; i < arenas.size(); i++){
                    Log.d("test",""+new File("/data/user/0/com.clash.clash/files/" + arenas.get(i).getIdName()).exists());
                    if(!new File("/data/user/0/com.clash.clash/files/" + arenas.get(i).getIdName()).exists())
                        test = false;
                }
            }*/

            progress.dismiss();
            initView();
        }
    };

    private DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which){
                case DialogInterface.BUTTON_POSITIVE:
                    startActivityForResult(new Intent(Settings.ACTION_WIFI_SETTINGS), 0);
                    onResume();
                    break;
                case DialogInterface.BUTTON_NEGATIVE:
                    System.exit(0);
                    break;
            }
        }
    };

    private class DownloadImage extends AsyncTask<String, Void, Bitmap> {
        private String name = "";

        private Bitmap downloadImageBitmap(String sUrl) {
            Bitmap bitmap = null;
            try {
                InputStream inputStream = new URL(sUrl).openStream();
                bitmap = BitmapFactory.decodeStream(inputStream);
                inputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            this.name = params[1];
            return downloadImageBitmap(params[0]);
        }

        protected void onPostExecute(Bitmap result) {
            FileOutputStream foStream;
            try {
                foStream = getApplicationContext().openFileOutput(this.name, Context.MODE_PRIVATE);
                result.compress(Bitmap.CompressFormat.PNG, 100, foStream);
                foStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clash);

        progress = new ProgressDialog(this);
        progress.setTitle("Loading");
        progress.setMessage("Wait while loading...");
        progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
        progress.show();

        launchInit();
    }

    @Override
    public void onResume() {
        super.onResume();
        this.launchInit();
    }

    @Override
    public void onPause() {
        super.onPause();
        this.unregisterReceiver(myBroadcastReceiver);
    }

    public void initView(){
        this.myArenaRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        this.myArenaRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        this.myArenaAdapter = new ArenaAdapter(getApplicationContext(), arenas);
        this.myArenaRecyclerView.setAdapter(myArenaAdapter);
    }

    public void launchInit(){
        if(!InternetConnexion.isConnectedInternet(this)){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("L'application a besoin d'Internet pour fonctionne.");
            builder.setMessage("Do you want to active Internet ?").setPositiveButton("Yes", dialogClickListener)
                    .setNegativeButton("No", dialogClickListener).show();
        }

        IntentFilter filter = new IntentFilter();
        filter.addAction("Receive");
        this.registerReceiver(this.myBroadcastReceiver, filter);
        //Intent Service
        if(this.arenas == null) {
            Intent intent = new Intent(this, InitApp.class);
            startService(intent);
    }
}
        }