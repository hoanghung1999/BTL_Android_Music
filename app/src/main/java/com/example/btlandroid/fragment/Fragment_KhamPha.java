package com.example.btlandroid.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.btlandroid.Adapter.DanhSachNgheSiAdapter;
import com.example.btlandroid.Adapter.PhoBienAdapter;
import com.example.btlandroid.R;
import com.example.btlandroid.Service.APIService;
import com.example.btlandroid.Service.Dataservice;
import com.example.btlandroid.modelUI.DichVu;
import com.example.btlandroid.modelUI.NgheSi;
import com.example.btlandroid.modelUI.PhoBien;
import com.example.btlandroid.modelUI.QuangCao;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Fragment_KhamPha extends Fragment {
    private RecyclerView recyclerNgheSi, recyclerPhoBien;
    private DanhSachNgheSiAdapter adapterNS;
    private PhoBienAdapter adapterPB;
    private ImageSlider imageSlider;
    View v;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment__kham_pha, container, false);
        init();
        getDataQuangCao();
        getDataNgheSi();
        getDataPhoBien();

        return v;

    }


    public void init() {
        imageSlider=v.findViewById(R.id.slider);
        recyclerNgheSi = v.findViewById(R.id.recycleDSNgheSi);
        recyclerPhoBien = v.findViewById(R.id.recyclePhoBien);
    }
    private void getDataQuangCao() {
        Dataservice dataservice= APIService.getService();
        Call<List<QuangCao>> callback=dataservice.getQuangCao();
        callback.enqueue(new Callback<List<QuangCao>>() {
            @Override
            public void onResponse(Call<List<QuangCao>> call, Response<List<QuangCao>> response) {
                List<QuangCao> quangCaoList=(List<QuangCao>) response.body();
                List<SlideModel> slideModels=new ArrayList<>();
                for(QuangCao i:quangCaoList){
                    slideModels.add(new SlideModel(i.getImg(), ScaleTypes.CENTER_CROP));
                }
                imageSlider.setImageList(slideModels,ScaleTypes.CENTER_CROP);
            }

            @Override
            public void onFailure(Call<List<QuangCao>> call, Throwable t) {
            }
        });
    }

    public void getDataPhoBien(){
        Dataservice dataservice= APIService.getService();
        Call<List<PhoBien>> callback=dataservice.getPlayListPhoBien();
        callback.enqueue(new Callback<List<PhoBien>>() {
            @Override
            public void onResponse(Call<List<PhoBien>> call, Response<List<PhoBien>> response) {
                List<PhoBien> dichVuList =(List<PhoBien>) response.body();
                LinearLayoutManager layoutManagerPB=new LinearLayoutManager(getContext());
                layoutManagerPB.setOrientation(LinearLayoutManager.HORIZONTAL);
                recyclerPhoBien.setLayoutManager(layoutManagerPB);
                adapterPB=new PhoBienAdapter(getActivity(), dichVuList);
                recyclerPhoBien.setAdapter(adapterPB);
            }
            @Override
            public void onFailure(Call<List<PhoBien>> call, Throwable t) {
            }
        });
    }
    public void getDataNgheSi(){
        Dataservice dataservice=APIService.getService();
        Call<List<NgheSi>> callback=dataservice.getPlayNghiSi();
        callback.enqueue(new Callback<List<NgheSi>>() {
            @Override
            public void onResponse(Call<List<NgheSi>> call, Response<List<NgheSi>> response) {
                List<NgheSi> ngheSiList=response.body();
                LinearLayoutManager layoutManagerNS=new LinearLayoutManager(getContext());
                layoutManagerNS.setOrientation(RecyclerView.HORIZONTAL);
                recyclerNgheSi.setLayoutManager(layoutManagerNS);
                adapterNS=new DanhSachNgheSiAdapter(getActivity(),ngheSiList);
                recyclerNgheSi.setAdapter(adapterNS);
            }
            @Override
            public void onFailure(Call<List<NgheSi>> call, Throwable t) {
            }
        });
    }

}