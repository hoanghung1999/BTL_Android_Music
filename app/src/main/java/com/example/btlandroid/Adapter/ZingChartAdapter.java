package com.example.btlandroid.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.btlandroid.PlaySong;
import com.example.btlandroid.PlaySongOnline;
import com.example.btlandroid.R;
import com.example.btlandroid.Service.APIService;
import com.example.btlandroid.Service.Dataservice;
import com.example.btlandroid.model.Song;
import com.example.btlandroid.modelUI.SongOnline;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.List;
import java.util.Random;

public class ZingChartAdapter extends RecyclerView.Adapter<ZingChartAdapter.ZingChartViewHolder> {
    private Context context;
    private List<SongOnline> mlist;
    View v;

    public ZingChartAdapter(Context context, List<SongOnline> mlist) {
        this.context = context;
        this.mlist = mlist;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ZingChartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.zc_top_song, parent, false);
        return new ZingChartViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ZingChartViewHolder holder, int position) {
        SongOnline songOnline = mlist.get(position);
        int xh = position + 1;
        holder.stt.setText("" + xh);
        setColorForSTT(holder,xh);
        Picasso.get().load(songOnline.getImage()).into(holder.image);
        holder.songName.setText(songOnline.getName());
        holder.singerName.setText(songOnline.getNghesi().getName());
        holder.itemView.setOnClickListener(v1 -> {
            Toast.makeText(context,""+position,Toast.LENGTH_SHORT).show();
            Intent intentSong=new Intent(context, PlaySongOnline.class);
            intentSong.putExtra("pos",position);
            intentSong.putExtra("listSongOnline", (Serializable) mlist);
            v.getContext().startActivity(intentSong);
        });
    }

    @Override
    public int getItemCount() {
        if(mlist!=null) return  mlist.size();
        return 0;
    }

    public class ZingChartViewHolder extends RecyclerView.ViewHolder {
        private TextView stt;
        private ImageView image;
        private TextView singerName;
        private TextView songName;

        public ZingChartViewHolder(@NonNull View v) {
            super(v);
            stt = v.findViewById(R.id.stt);
            image = v.findViewById(R.id.imageSongTop);
            songName = v.findViewById(R.id.songNameTop);
            singerName = v.findViewById(R.id.singerSongTop);
        }
    }
    public void setColorForSTT(ZingChartViewHolder holder,int positon){
        if(positon==1) holder.stt.setTextColor(Color.parseColor("#10F362"));
        else if(positon==2) holder.stt.setTextColor(Color.parseColor("#0B3DF1"));
        else if(positon==3) holder.stt.setTextColor(Color.parseColor("#F81010"));
        else{
            Random rnd = new Random();
            int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
            holder.stt.setTextColor(color);
        }
    }
}
