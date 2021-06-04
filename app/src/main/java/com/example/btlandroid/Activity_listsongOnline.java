package com.example.btlandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.btlandroid.Adapter.ZingChartAdapter;
import com.example.btlandroid.Service.APIService;
import com.example.btlandroid.Service.Dataservice;
import com.example.btlandroid.modelUI.DichVu;
import com.example.btlandroid.modelUI.SongOnline;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Activity_listsongOnline extends AppCompatActivity {
    private ImageButton btnBack,btnMenu;
    private ImageView imageChuDe;
    private TextView nameChude;
    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listsong_online);
        init();
        setDataChuDe();
    }

    public void init(){
        btnBack=findViewById(R.id.btnBack);
        btnMenu=findViewById(R.id.btnmenuBar);
        imageChuDe=findViewById(R.id.imageChuDe);
        nameChude=findViewById(R.id.ChuDe);
        recyclerView=findViewById(R.id.recyclerChuDe);
    }
    public void setDataChuDe(){
        // lay idchude dich vu gui qua
        Intent intent=getIntent();
        DichVu dichVu= (DichVu) intent.getSerializableExtra("dichvu");
        System.out.println("dich vu id:"+dichVu.getId());
        // update giap dien
        Picasso.get().load(dichVu.getImg()).into(imageChuDe);
        nameChude.setText(dichVu.getName());


        Dataservice dataservice= APIService.getService();
        Call<List<SongOnline>> callBack=dataservice.getSongByChuDe(dichVu.getId());
        callBack.enqueue(new Callback<List<SongOnline>>() {
            @Override
            public void onResponse(Call<List<SongOnline>> call, Response<List<SongOnline>> response) {
                List<SongOnline> songOnlines=response.body();
                LinearLayoutManager linearLayout=new LinearLayoutManager(getApplicationContext());
                recyclerView.setLayoutManager(linearLayout);
                ZingChartAdapter zingChartAdapter=new ZingChartAdapter(getApplicationContext(),songOnlines);
                recyclerView.setAdapter(zingChartAdapter);
            }

            @Override
            public void onFailure(Call<List<SongOnline>> call, Throwable t) {

            }
        });

    }
}