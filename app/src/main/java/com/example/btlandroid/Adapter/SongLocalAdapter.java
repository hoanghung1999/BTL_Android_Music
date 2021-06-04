package com.example.btlandroid.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.btlandroid.PlaySong;
import com.example.btlandroid.PlaySongOnline;
import com.example.btlandroid.R;
import com.example.btlandroid.model.Song;
import com.example.btlandroid.modelUI.SongOnline;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.List;

public class SongLocalAdapter extends RecyclerView.Adapter<SongLocalAdapter.SongViewHolder> {
    private Context context;
    private List<Song> mlist;
    View v;

    public SongLocalAdapter(Context context, List<Song> mlist) {
        this.context = context;
        this.mlist = mlist;
    }

    public List<Song> getMlist() {
        return mlist;
    }

    public void setMlist(List<Song> mlist) {
        this.mlist = mlist;
    }

    @NonNull
    @Override
    public SongViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.song_local, parent, false);
        return new SongViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull SongViewHolder holder, int position) {
        Song song = mlist.get(position);
        int xh = position + 1;
        Picasso.get().load(song.getImage()).into(holder.image);
        holder.songName.setText(song.getName());
        holder.singerName.setText(song.getSinger());
        holder.itemView.setOnClickListener(v1 -> {
            Toast.makeText(context,""+position,Toast.LENGTH_SHORT).show();
            Intent intentSong=new Intent(context, PlaySong.class);
            intentSong.putExtra("pos",position);
            intentSong.putExtra("listSongLocal", (Serializable) mlist);
            v.getContext().startActivity(intentSong);
        });
    }

    @Override
    public int getItemCount() {
        if(mlist!=null) return  mlist.size();
        return 0;
    }

    public class SongViewHolder extends RecyclerView.ViewHolder {
        private ImageView image;
        private TextView singerName;
        private TextView songName;

        public SongViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.imageSongTop);
            songName = itemView.findViewById(R.id.songNameTop);
            singerName = itemView.findViewById(R.id.singerSongTop);
        }
    }
}
