package com.clash.clash;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.View;

import android.widget.*;

import java.io.FileInputStream;
import java.util.ArrayList;

public class CardsActivity extends AppCompatActivity {
    private ArrayList<TextView> alTextView;
    private ArrayList<Card> alCardUnlocks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cards_activity);

        this.alTextView = new ArrayList<TextView>();
        this.alCardUnlocks = new ArrayList<Card>();

        this.alCardUnlocks = getIntent().getParcelableArrayListExtra("CardUnlocks");
        LinearLayout layout = (LinearLayout) findViewById(R.id.LinearLayout1);
        int i = 0;
        for(Card c : alCardUnlocks){
            TextView tvCard = new TextView(this);
            tvCard.setText(c.getName());
            tvCard.setTextSize(TypedValue.COMPLEX_UNIT_SP,25);
            tvCard.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            tvCard.setPadding(5,5,5,5);
            if( i++%2 == 0 )
                tvCard.setBackgroundColor(Color.parseColor("#408EC9"));

            layout.addView(tvCard);
            alTextView.add(tvCard);
        }
    }

    public Bitmap loadImageBitmap(Context context, String imageName) {
        Bitmap bitmap = null;
        FileInputStream fiStream;
        try {
            fiStream = context.openFileInput(imageName);
            bitmap = BitmapFactory.decodeStream(fiStream);
            fiStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }



}
