package com.example.btlandroid.Adapter;

import android.app.Activity;
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

import com.example.btlandroid.Activity_listsong;
import com.example.btlandroid.Activity_listsongLove;
import com.example.btlandroid.R;
import com.example.btlandroid.modelUI.TenDichVu;

import java.util.List;

public class TenDichVuAdapter extends RecyclerView.Adapter<TenDichVuAdapter.DichVuViewHolder> {
    Context context;
    private List<TenDichVu> mlist;
    View v;
    public  TenDichVuAdapter(Context context,List<TenDichVu> list){
        this.mlist=list;
        this.context=context;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public DichVuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.re_dichvu, parent, false);
        return new DichVuViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull DichVuViewHolder holder, int position) {
        TenDichVu tenDichVu = mlist.get(position);
        holder.textView.setText(tenDichVu.getTen());
        holder.imageView.setImageResource(tenDichVu.getImg());
        v.setOnClickListener(v1 -> {
            switch (position){
                case 0:
                    Intent intent=new Intent(context, Activity_listsong.class);
                    context.startActivity(intent);
                    break;
                case 1:
                    Intent intent1=new Intent(context, Activity_listsongLove.class);
                    context.startActivity(intent1);
                    break;
            }
        });
    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }

    public class DichVuViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;

        public DichVuViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.img1);
            textView = itemView.findViewById(R.id.txt1);
        }
    }


}
