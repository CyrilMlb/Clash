package com.clash.clash;

import android.app.IntentService;
import android.content.*;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import java.io.*;
import java.net.*;
import java.util.*;

import org.json.*;

public class InitApp extends IntentService {
    public InitApp() {
        super("InitApp");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        ArrayList<Card> cards = new ArrayList<Card>();
        ArrayList<Arena> arenas = new ArrayList<Arena>();

        String myurl= "http://www.clashapi.xyz/";

        try {
            String strCards = "cards";
            URL urlCards = new URL(myurl + "api/cards");
            HttpURLConnection coCard = (HttpURLConnection) urlCards.openConnection();
            coCard.connect();
            InputStream isCards = coCard.getInputStream();
            String isStrCard = InputStreamOperations.InputStreamToString(isCards);
            JSONArray arrayCards = new JSONArray(isStrCard);
            for (int i = 0; i < arrayCards.length(); i++) {
                JSONObject obj = new JSONObject(arrayCards.getString(i));
                Card card = new Card(obj.getString("_id"), obj.getString("idName"), obj.getString("rarity"), obj.getString("type"),
                                        obj.getString("name"), obj.getString("description"), obj.getInt("elixirCost"));
                cards.add(card);
            }

            URL urlArenas = new URL(myurl + "api/arenas");
            HttpURLConnection coArena = (HttpURLConnection) urlArenas.openConnection();
            coArena.connect();
            InputStream isArenas = coArena.getInputStream();
            String strArenas = InputStreamOperations.InputStreamToString(isArenas);
            JSONArray arrayArenas = new JSONArray(strArenas);
            for (int i = 0; i < arrayArenas.length(); i++) {
                JSONObject obj = new JSONObject(arrayArenas.getString(i));
                JSONArray arrayCardUnlocks = obj.getJSONArray("cardUnlocks");
                ArrayList<Card> linkedCard = new ArrayList<Card>();
                for(int j = 0; j  < arrayCardUnlocks.length(); j++){
                    String str = arrayCardUnlocks.opt(j).toString();
                    for(Card c : cards)
                        if(str.equals(c.getId()))
                            linkedCard.add(c);
                }
                Arena arena = new Arena(obj.getString("idName"), obj.getString("name"),
                        obj.getInt("victoryGold"), obj.getInt("minTrophies"), linkedCard);
                arenas.add(arena);
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        Intent broadCast = new Intent();
        broadCast.setAction("Receive");
        broadCast.putParcelableArrayListExtra("Arenas", arenas);
        sendBroadcast(broadCast);
    }
}