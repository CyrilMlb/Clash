package com.clash.clash;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.v7.widget.RecyclerView;
import android.util.*;
import android.view.*;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.util.ArrayList;

public class ArenaAdapter extends RecyclerView.Adapter<ArenaAdapter.ArenaViewHolder> {
    private Context context;
    private ArrayList<Arena> arenas;

    public ArenaAdapter(Context context, ArrayList<Arena> arenas){
        this.context = context;
        this.arenas = arenas;
    }

    @Override
    public ArenaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.arena_item, parent, false);
        return new ArenaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ArenaViewHolder holder, int position) {
        holder.display(arenas.get(position));
    }

    @Override
    public int getItemCount(){
        if(arenas == null)
            return 0;
        return arenas.size();
    }

    public class ArenaViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView arenaName;
        private ImageView arenaImage;

        public ArenaViewHolder(View itemView){
            super(itemView);

            arenaName = (TextView)itemView.findViewById(R.id.arena_name);
            arenaImage = (ImageView)itemView.findViewById(R.id.arena_image);
            arenaImage.setOnClickListener(this);
        }

        public void display(Arena arena){
            arenaName.setText(arena.getName());
            arenaImage.setImageBitmap(loadImageBitmap(arenaImage.getContext(), arena.getIdName()));
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

        @Override
        public void onClick(View v)
        {
            int position = getAdapterPosition();
            Arena arena = arenas.get(position);

            Toast toast = Toast.makeText(context, ""+arena.getCardUnlocks().size() + " cartes débloquées!", Toast.LENGTH_SHORT);
            toast.show();

            Intent intent = new Intent(context, CardsActivity.class);
            intent.putParcelableArrayListExtra("CardUnlocks", arena.getCardUnlocks());
            context.startActivity(intent);
        }
    }
}
