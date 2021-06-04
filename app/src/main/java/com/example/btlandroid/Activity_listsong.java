package com.example.btlandroid;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.btlandroid.Adapter.SongLocalAdapter;
import com.example.btlandroid.model.Song;
import com.example.btlandroid.sqlite.SQLiteBaiHatHelper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Activity_listsong extends AppCompatActivity implements View.OnClickListener {
    private RecyclerView recyclerView;
    private List<Song> listSongLocal;
    private ImageButton btnSpeach;
    private SearchView searchView;
    private ImageButton btnBack;

    // Bai Hat Duoc noi bang google
    private SongLocalAdapter adapter;
    private SQLiteBaiHatHelper sqLiteBaiHatHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listsong);
        init();

        //load Bài Hát
        LoadListSongLocal();
        adapter = new SongLocalAdapter(getApplicationContext(), listSongLocal);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(adapter);

        btnSpeach.setOnClickListener(this);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                sqLiteBaiHatHelper=new SQLiteBaiHatHelper(getApplicationContext());
                    List<Song> listSearch=sqLiteBaiHatHelper.getSongByName(newText);
                    adapter.setMlist(listSearch);
                    recyclerView.setAdapter(adapter);

                return false;
            }
        });

        btnBack.setOnClickListener(v -> {finish();});

    }

    public void init() {
        recyclerView = findViewById(R.id.listSongs);
        btnSpeach = findViewById(R.id.voice);
        searchView=findViewById(R.id.search_view);
        btnBack=findViewById(R.id.btnBack);
    }

    public void LoadListSongLocal() {
    sqLiteBaiHatHelper = new SQLiteBaiHatHelper(getApplicationContext());
        listSongLocal = sqLiteBaiHatHelper.getAllSong();

    }

    @Override
    public void onBackPressed() {
//        Intent intent = new Intent(this, MainActivity.class);
//        startActivity(intent);
        finish();
    }
    private static final int RECOGNIZER_RESULT=1;
    @Override
    public void onClick(View v) {
        if (v == btnSpeach) {
        Intent speechIntent=new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        speechIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        speechIntent.putExtra(RecognizerIntent.EXTRA_PROMPT,"Nói Tên Bài Hát");
        startActivityForResult(speechIntent,RECOGNIZER_RESULT);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == RECOGNIZER_RESULT && resultCode== RESULT_OK ){
            ArrayList<String> matches=data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            searchView.setQuery(matches.get(0).toString(),false);
            searchView.setIconified(false);
            searchView.clearFocus();
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}