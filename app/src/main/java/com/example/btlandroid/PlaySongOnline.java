package com.example.btlandroid;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.btlandroid.fragment.Fragment_ZingChart;
import com.example.btlandroid.model.Song;
import com.example.btlandroid.modelUI.SongOnline;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

public class PlaySongOnline extends AppCompatActivity implements View.OnClickListener {

    private ImageButton btnPlay, btnNext, btnPrevious, btnRandom, btnLoop, btnBack, btnMenuBar;
    private TextView txtTimeStart, txtTimeEnd, txtSingerName, txtSongName;
    private SeekBar songBar;
    private ImageView imageSong;
    private MediaPlayer mediaPlayer;
    private ObjectAnimator animator;

    private SongOnline songPlay;
    private int pos;
    private List<SongOnline> listSongOnline;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_song_online);
        init();
        loadData();
        loadSongPlay(songPlay);
        upDateTime();
        //Bat su kien khi click vao cac imageButton
        btnPlay.setOnClickListener(this);
        btnBack.setOnClickListener(this);
        btnNext.setOnClickListener(this);
        btnPrevious.setOnClickListener(this);
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
        this.pos = intent.getIntExtra("pos", 0);
        this.listSongOnline = (List<SongOnline>) intent.getSerializableExtra("listSongOnline");
        this.songPlay = this.listSongOnline.get(pos);
    }

    public void loadSongPlay(SongOnline song) {
        //TO DO
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            mediaPlayer.setDataSource(song.getFile());
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mediaPlayer.start();
        //load thong tin
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
        txtTimeEnd.setText(simpleDateFormat.format(mediaPlayer.getDuration()));
        songBar.setMax(mediaPlayer.getDuration());
        btnPlay.setImageResource(R.drawable.play);
        txtSongName.setText(song.getName());
        txtSingerName.setText(song.getNghesi().getName());
        //anh
        Picasso.Builder builder = new Picasso.Builder(this);
        builder.listener(new Picasso.Listener()
        {
            @Override
            public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                imageSong.setImageResource(R.drawable.diskmusic);
                System.out.println("LOAD ANH LOI NE");
            }
        });
        builder.build().load(song.getImage()).into(imageSong);
        //set animation cho anh
        animator = ObjectAnimator.ofFloat(imageSong, "rotation", 0, 360);
        animator.setDuration(10000);
        animator.setInterpolator(new LinearInterpolator());
        animator.setRepeatCount(ObjectAnimator.INFINITE);
        animator.setRepeatMode(ObjectAnimator.RESTART);
        animator.start();
    }

    public void upDateTime() {
        final Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
                    txtTimeStart.setText(simpleDateFormat.format(mediaPlayer.getCurrentPosition()));
                    songBar.setProgress(mediaPlayer.getCurrentPosition());
                    handler.postDelayed(this, 500);
                    mediaPlayer.setOnCompletionListener(mp -> {
                        pos++;
                        if (pos == listSongOnline.size()) pos = 0;
                        loadSongPlay(listSongOnline.get(pos));
                    });
                }
            }
        };
        handler.postDelayed(runnable, 100);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mediaPlayer != null) {
            mediaPlayer.pause();
            if (isFinishing()) {
                mediaPlayer.stop();
                mediaPlayer.reset();
            }
        }
    }
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
                finish();
                break;
            case R.id.btnNext:
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.stop();
                    animator.pause();
                }
                pos++;
                if (pos == this.listSongOnline.size()) pos = 0;
                this.songPlay = listSongOnline.get(pos);
                System.out.println("NEXT: " + pos);
                loadSongPlay(this.songPlay);
                break;
            case R.id.btnPrevious:
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.stop();
                    animator.pause();
                }
                pos--;
                if (pos < 0) pos = this.listSongOnline.size() - 1;
                this.songPlay = listSongOnline.get(pos);
                loadSongPlay(this.songPlay);
                break;
        }
    }
}