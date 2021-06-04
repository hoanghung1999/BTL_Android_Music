package com.example.btlandroid;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.btlandroid.Adapter.ZingChartAdapter;
import com.example.btlandroid.Service.APIService;
import com.example.btlandroid.Service.Dataservice;
import com.example.btlandroid.modelUI.PhoBien;
import com.example.btlandroid.modelUI.SongOnline;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivitySearch extends AppCompatActivity implements View.OnClickListener{
    private SearchView searchView;
    private ImageButton btnSpeach,btnBack;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        init();
        searchView.setFocusable(true);
        searchView.setIconified(false);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(newText.length()>0){
                setDataSearch(newText);
                }
                return true;
            }
        });

        btnSpeach.setOnClickListener(this);
    }
    public void init(){
        searchView=findViewById(R.id.search_view);
        btnSpeach=findViewById(R.id.btnSpeach);
        btnBack=findViewById(R.id.btn_Back);
        recyclerView=findViewById(R.id.SongsBE);
    }

    public void setDataSearch(String name){
        Dataservice dataservice= APIService.getService();
        Call<List<SongOnline>> callback=dataservice.getSongByName(name);
        callback.enqueue(new Callback<List<SongOnline>>() {
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
                Toast.makeText(getApplicationContext(),"Backend không hoạt đông",Toast.LENGTH_SHORT).show();
            }
        });
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
            String result=matches.get(0).toString().toLowerCase();
            String pattern = "^bật bài hát\\s(.+)+";
            Pattern r = Pattern.compile(pattern);
            Matcher m = r.matcher(result);
            if(m.find()){
            PlayNhacBySpeach(m.group(1));
            }
            else {
                searchView.setQuery(matches.get(0).toString(), false);
                searchView.setIconified(false);
                searchView.clearFocus();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void PlayNhacBySpeach(String name){
        Dataservice dataservice= APIService.getService();
        Call<List<SongOnline>> callback=dataservice.getSongByName(name);
        callback.enqueue(new Callback<List<SongOnline>>() {
            @Override
            public void onResponse(Call<List<SongOnline>> call, Response<List<SongOnline>> response) {
                List<SongOnline> songOnlines=response.body();
                if(songOnlines.size()>0){
                    Intent intentSong=new Intent(ActivitySearch.this, PlaySongOnline.class);
                    intentSong.putExtra("pos",0);
                    intentSong.putExtra("listSongOnline", (Serializable) songOnlines);
                    startActivity(intentSong);
                }else {
                    Toast.makeText(getApplicationContext(),"Không tìm thấy bài hát",Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<List<SongOnline>> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Backend không hoạt đông",Toast.LENGTH_SHORT).show();
            }
        });
    }
}