package com.example.btlandroid.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.btlandroid.Adapter.ZingChartAdapter;
import com.example.btlandroid.R;
import com.example.btlandroid.Service.APIService;
import com.example.btlandroid.Service.Dataservice;
import com.example.btlandroid.modelUI.SongOnline;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Fragment_ZingChart extends Fragment {
    private RecyclerView recyclerView;
    private View v;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v=inflater.inflate(R.layout.fragment__zing_chart, container, false);
        init();
        setDataZingChart();
        return v;
    }
    public void init(){
        recyclerView=v.findViewById(R.id.recycleViewZ);
    }


    public void setDataZingChart(){
        Dataservice dataservice= APIService.getService();
        Call<List<SongOnline>> callBack=dataservice.getSongZingChart();
        callBack.enqueue(new Callback<List<SongOnline>>() {
            @Override
            public void onResponse(Call<List<SongOnline>> call, Response<List<SongOnline>> response) {
                List<SongOnline> songOnlines=response.body();
//                System.out.println("SIZE"+songOnlines.size());
                LinearLayoutManager linearLayout=new LinearLayoutManager(getContext());
                recyclerView.setLayoutManager(linearLayout);
                ZingChartAdapter zingChartAdapter=new ZingChartAdapter(getContext(),songOnlines);
                recyclerView.setAdapter(zingChartAdapter);
            }
            @Override
            public void onFailure(Call<List<SongOnline>> call, Throwable t) {

            }
        });
    }
}