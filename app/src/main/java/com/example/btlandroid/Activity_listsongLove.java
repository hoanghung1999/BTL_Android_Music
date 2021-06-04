package com.example.btlandroid;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SearchView;

import com.example.btlandroid.Adapter.SongLocalAdapter;
import com.example.btlandroid.model.Song;
import com.example.btlandroid.sqlite.SQLiteBaiHatHelper;
import com.example.btlandroid.sqlite.SQLiteYeuThichHelper;

import java.util.ArrayList;
import java.util.List;

public class Activity_listsongLove extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<Song> listSongLocal;
    private ImageButton btnSpeach;
    private SearchView searchView;
    private ImageButton btnBack;

    // Bai Hat Duoc noi bang google
    private SongLocalAdapter adapter;
    private SQLiteYeuThichHelper sqLiteHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listsong_love);
        init();

        //load Bài Hát
        LoadListSongLocal();
        adapter = new SongLocalAdapter(getApplicationContext(), listSongLocal);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(adapter);

    }

    public void init() {
        recyclerView = findViewById(R.id.listSongs);

        btnBack=findViewById(R.id.btnBack);
    }

    public void LoadListSongLocal() {
        sqLiteHelper = new SQLiteYeuThichHelper(getApplicationContext());
        listSongLocal = sqLiteHelper.getAllBaiHatYeuThich();

    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        LoadListSongLocal();
        adapter = new SongLocalAdapter(getApplicationContext(), listSongLocal);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(adapter);
    }
}