package com.example.btlandroid.Service;

import com.example.btlandroid.modelUI.DichVu;
import com.example.btlandroid.modelUI.NgheSi;
import com.example.btlandroid.modelUI.PhoBien;
import com.example.btlandroid.modelUI.QuangCao;
import com.example.btlandroid.modelUI.SongOnline;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Dataservice {
    //Danh Sách các Top chủ để phổ biến cung cấp trong Dịch Vụ Id là 2
    @GET("chude/dichvu/2")
    Call<List<PhoBien>> getPlayListPhoBien();

    //Danh Sách Nghệ sĩ được cung cấp trong dịch vụ có id là 3
    @GET("chude/dichvu/3")
    Call<List<NgheSi>> getPlayNghiSi();

    @GET("chude/dichvu/4")
    Call<List<QuangCao>> getQuangCao();

    // ZingChart Cung cấp dịch vụ backend là id 5
    @GET("baihat/chude/5")
    Call<List<SongOnline>> getSongZingChart();

    //Hien thi bai hat theo Chu de tuong ứng
    @GET("baihat/chude/{id}")
    Call<List<SongOnline>> getSongByChuDe(@Path("id") int id);


    //Get Bai Hat Bang Ten
    @GET("baihat")
    Call<List<SongOnline>> getSongByName(@Query("name") String name);
}
