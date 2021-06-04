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

import com.example.btlandroid.Activity_listsongOnline;
import com.example.btlandroid.PlaySongOnline;
import com.example.btlandroid.R;
import com.example.btlandroid.modelUI.DichVu;
import com.example.btlandroid.modelUI.PhoBien;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PhoBienAdapter extends RecyclerView.Adapter<PhoBienAdapter.PhoBienHolder> {
    private Context context;
    private List<PhoBien> mlist;
    View v;

    public PhoBienAdapter(Context context, List<PhoBien> list) {
        this.context = context;
        this.mlist = list;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public PhoBienHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.re_phobien, parent, false);
        return new PhoBienHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PhoBienHolder holder, int position) {
        DichVu c = mlist.get(position);
//        holder.imageView.setImageResource(c.getImg());
        Picasso.get().load(c.getImg()).into(holder.imageView);
        holder.textView.setText(c.getName());
        v.setOnClickListener(v1 -> {
            Toast.makeText(context,""+position,Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(context, Activity_listsongOnline.class);
            DichVu dichVu=mlist.get(position);
            intent.putExtra("dichvu",dichVu);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        if(mlist==null) return 0;
        return mlist.size();
    }

    public class PhoBienHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;

        public PhoBienHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imagePB);
            textView = itemView.findViewById(R.id.namePB);
        }
    }
}
