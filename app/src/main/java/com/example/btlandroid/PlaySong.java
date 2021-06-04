package com.example.btlandroid;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.btlandroid.model.Song;
import com.example.btlandroid.sqlite.SQLiteUserHelper;
import com.example.btlandroid.sqlite.SQLiteYeuThichHelper;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.List;

public class PlaySong extends AppCompatActivity implements View.OnClickListener {
    private ImageButton btnPlay, btnNext, btnPrevious, btnRandom, btnLoop, btnBack, btnMenuBar,btnFavorite;
    private TextView txtTimeStart, txtTimeEnd, txtSingerName, txtSongName;
    private SeekBar songBar;
    private ImageView imageSong;
    private MediaPlayer mediaPlayer;
    private ObjectAnimator animator;

    private Song songPlay;
    private int love=0;
    private int pos;
    private List<Song> listSong;

    private SQLiteYeuThichHelper sqLiteYeuThichHelper;
    private SQLiteUserHelper sqLiteUserHelper;
    private int iduser;
    private int idbaihat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_song);
        init();
        loadData();
        loadSongPlay(songPlay);
        upDateTime();
        //Bat su kien khi click vao cac imageButton
        btnPlay.setOnClickListener(this);
        btnBack.setOnClickListener(this);
        btnNext.setOnClickListener(this);
        btnPrevious.setOnClickListener(this);
        btnFavorite.setOnClickListener(this);
        songBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.seekTo(songBar.getProgress());
            }
        });
    }


    public void init() {
        btnFavorite=findViewById(R.id.btnFavorite);
        btnBack = findViewById(R.id.btnBack);
        btnMenuBar = findViewById(R.id.menuBar);
        btnPlay = findViewById(R.id.play);
        btnNext = findViewById(R.id.btnNext);
        btnPrevious = findViewById(R.id.btnPrevious);
        btnRandom = findViewById(R.id.random);
        btnLoop = findViewById(R.id.loop);
        txtTimeStart = findViewById(R.id.timeStart);
        txtTimeEnd = findViewById(R.id.timeEnd);
        txtSingerName = findViewById(R.id.singerName);
        txtSongName = findViewById(R.id.songName);
        songBar = findViewById(R.id.seekBarSong);
        imageSong = findViewById(R.id.imageSong);
    }

    public void loadData() {

        Intent intent = getIntent();
        this.pos=intent.getIntExtra("pos",0);
        this.listSong=(List<Song>) intent.getSerializableExtra("listSongLocal");
        this.songPlay=this.listSong.get(pos);

    }

    public void loadSongPlay(Song song) {
        System.out.println(song.getName()+ " ............................");
        int locationSong=this.getResources().getIdentifier(song.getFile(),"raw",this.getPackageName());
        mediaPlayer = MediaPlayer.create(this,locationSong);
        mediaPlayer.start();
        //load thong tin
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
        txtTimeEnd.setText(simpleDateFormat.format(mediaPlayer.getDuration()));
        songBar.setMax(mediaPlayer.getDuration());
        btnPlay.setImageResource(R.drawable.play);
        txtSongName.setText(song.getName());
        txtSingerName.setText(song.getSinger());
        //load YeuThich
        sqLiteYeuThichHelper=new SQLiteYeuThichHelper(getApplicationContext());
        sqLiteUserHelper=new SQLiteUserHelper(getApplicationContext());

        iduser=sqLiteUserHelper.checkUserLogin().getId();
        idbaihat=song.getId();
        if(sqLiteYeuThichHelper.checkYeuthich(iduser,idbaihat)==1){
            btnFavorite.setImageResource(R.drawable.favorite_color_24);
            love=1;
        }else {
            btnFavorite.setImageResource(R.drawable.favorite_border_24);
            love=0;
        }

        Picasso.get().load(song.getImage()).into(imageSong);

        //set animation cho anh
        animator=ObjectAnimator.ofFloat(imageSong,"rotation",0,360);
        animator.setDuration(10000);
        animator.setInterpolator(new LinearInterpolator());
        animator.setRepeatCount(ObjectAnimator.INFINITE);
        animator.setRepeatMode(ObjectAnimator.RESTART);
        animator.start();
    }
    public void upDateTime() {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
                txtTimeStart.setText(simpleDateFormat.format(mediaPlayer.getCurrentPosition()));
                songBar.setProgress(mediaPlayer.getCurrentPosition());
                handler.postDelayed(this, 500);
                mediaPlayer.setOnCompletionListener(mp -> {
                    pos++;
                    if(pos==listSong.size()) pos=0;
                    loadSongPlay(listSong.get(pos));
                });
            }
        }, 100);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mediaPlayer != null) {
            mediaPlayer.pause();
            if (isFinishing()) {
                mediaPlayer.stop();
//                mediaPlayer.release();
            }
        }

    }
//    @Override
//    public void onBackPressed() {
//        Intent setIntent = new Intent(this,Activity_listsong.class);
//        startActivity(setIntent);
//    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.play:
                if (mediaPlayer.isPlaying()) {
                    btnPlay.setImageResource(R.drawable.pause);
                    mediaPlayer.pause();
                    animator.pause();
                } else {
                    btnPlay.setImageResource(R.drawable.play);
                    mediaPlayer.start();
                    animator.start();
                }
                break;
            case R.id.btnBack:
//                Intent intent=new Intent(this,Activity_listsong.class);
//                startActivity(intent);
                finish();
                break;


            case  R.id.btnNext:
                System.out.println("Do t nÈ");
                if(mediaPlayer.isPlaying()){
                    mediaPlayer.stop();
                    animator.pause();
                }
                pos++;
                if(pos==this.listSong.size()) pos=0;
                this.songPlay=listSong.get(pos);
                System.out.println("NEXT: "+pos);
                loadSongPlay(this.songPlay);
                break;
            case  R.id.btnPrevious:
                if(mediaPlayer.isPlaying()) {
                    mediaPlayer.stop();
                    animator.pause();
                }
                pos--;
                if(pos<0) pos=this.listSong.size()-1;
                this.songPlay=listSong.get(pos);
                loadSongPlay(this.songPlay);
                break;

            case R.id.btnFavorite:
                if(love==1){
                    sqLiteYeuThichHelper.deleteBaiHatYeuThich(iduser,idbaihat);
                    btnFavorite.setImageResource(R.drawable.favorite_border_24);
                    love=0;
                }else {
                    sqLiteYeuThichHelper.addYeuThich(iduser,idbaihat);
                    btnFavorite.setImageResource(R.drawable.favorite_color_24);
                    love=1;

                }
                break;
        }
    }

}