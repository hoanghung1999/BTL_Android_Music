package com.example.btlandroid.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.btlandroid.Activity_listsongOnline;
import com.example.btlandroid.R;
import com.example.btlandroid.modelUI.DichVu;
import com.example.btlandroid.modelUI.NgheSi;
import com.squareup.picasso.Picasso;

import java.util.List;

public class DanhSachNgheSiAdapter extends RecyclerView.Adapter<DanhSachNgheSiAdapter.DSNgheSiViewHolder>{
    private List<NgheSi> mlist;
    private Context context;
    View v;
    public DanhSachNgheSiAdapter(Context context,List<NgheSi> list){
        this.mlist=list;
        this.context=context;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public DSNgheSiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.re_nghesi, parent, false);
        return new DSNgheSiViewHolder(v);
    }
    @Override
    public void onBindViewHolder(@NonNull DSNgheSiViewHolder holder, int position) {
        NgheSi ngheSi=mlist.get(position);
        holder.textView.setText(ngheSi.getName());
        Picasso.get().load(ngheSi.getImg()).into(holder.imageView);
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
        if(mlist!=null)
        return mlist.size();
        return 0;
    }

    public class DSNgheSiViewHolder extends RecyclerView.ViewHolder {
        de.hdodenhof.circleimageview.CircleImageView imageView;
        TextView textView;
        public DSNgheSiViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageNS);
            textView = itemView.findViewById(R.id.tenNS);
        }
    }

}
