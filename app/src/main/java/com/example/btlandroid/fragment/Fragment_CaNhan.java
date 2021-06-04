package com.example.btlandroid.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.btlandroid.Adapter.TenDichVuAdapter;
import com.example.btlandroid.R;
import com.example.btlandroid.modelUI.TenDichVu;

import java.util.ArrayList;
import java.util.List;

public class Fragment_CaNhan extends Fragment {
    private RecyclerView recyclerDichVu,recyclerNgheSi,recyclerPhoBien;
    private List<TenDichVu> listDichVu;
    private TenDichVuAdapter adapterDV;

    View v;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment__ca_nhan, container, false);
        init();
        //data
        addDataDichVu();

        //DichVu
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        recyclerDichVu.setLayoutManager(gridLayoutManager);
        //truyen vao list dichvu
        adapterDV = new TenDichVuAdapter(getActivity(),listDichVu);
        //Bat su kien khi click vao item trong recycleview
        recyclerDichVu.setAdapter(adapterDV);

        return v;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
    public void init(){
        recyclerDichVu = v.findViewById(R.id.recycleDichVu);
        recyclerNgheSi=v.findViewById(R.id.recycleDSNgheSi);
        recyclerPhoBien=v.findViewById(R.id.recyclePhoBien);
    }

    public void addDataDichVu() {
        listDichVu = new ArrayList<>();
        listDichVu.add(new TenDichVu(R.drawable.list_music, "Bài Hát"));
        listDichVu.add(new TenDichVu(R.drawable.favorite, "Yêu Thích"));
        listDichVu.add(new TenDichVu(R.drawable.album_music, "Album"));
        listDichVu.add(new TenDichVu(R.drawable.mv_player, "MV"));
    }

}