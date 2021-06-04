package com.example.btlandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.btlandroid.Adapter.FragmentBottomAdpater;
import com.example.btlandroid.model.Song;
import com.example.btlandroid.model.User;
import com.example.btlandroid.sqlite.SQLiteBaiHatHelper;
import com.example.btlandroid.sqlite.SQLiteUserHelper;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    //    thanh phan co ban ban dau
    public ViewPager viewPager;
    public BottomNavigationView navigation;
    public FragmentBottomAdpater adpater;

    //header
    private Button btnSearch;
    private ImageButton btnSpeach;
    private ImageView avatarUser;

    //Phan Nhac
    private LinearLayout linearLayout;
    private ImageView imageSong;
    private TextView nameSong;
    private TextView singerSong;
    private ImageButton btnPlay, btnNext, btnFavorite;
    private List<Song> mlist;

    //SQLite DB
    private SQLiteBaiHatHelper sqLiteBaiHatHelper;
    private SQLiteUserHelper sqLiteUserHelper;
    //Ho tro de play nhac
    private MediaPlayer mediaPlayer;
    private boolean play = true;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        sqLiteBaiHatHelper = new SQLiteBaiHatHelper(this);
        adpater = new FragmentBottomAdpater(getSupportFragmentManager(), FragmentBottomAdpater.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(adpater);

        setData();
        ChonIconToPage();
        ChuyenPage();

        btnPlay.setOnClickListener(this);
        btnSpeach.setOnClickListener(this);
        btnSearch.setOnClickListener(this);
    }

    // Chon Icon Tren navgation
    public void ChonIconToPage() {
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.canhanNAV:
                        viewPager.setCurrentItem(0);
                        break;
                    case R.id.khamphaNAV:
                        viewPager.setCurrentItem(1);
                        break;
                    case R.id.zingchartNAV:
                        viewPager.setCurrentItem(2);
                        break;

                    case R.id.userNAV:
                        viewPager.setCurrentItem(3);
                        break;
                }
                return true;
            }
        });
    }

    //setData
    public void setData() {
        sqLiteBaiHatHelper = new SQLiteBaiHatHelper(getApplicationContext());
        mlist = sqLiteBaiHatHelper.getAllSong();
        if (mlist.size() != 0) {
            Random random = new Random();
            int pos = random.nextInt(mlist.size());
            Song song = mlist.get(pos);
            Picasso.get().load(song.getImage()).into(imageSong);
        } else {
            backupDataLocal();
        }
        sqLiteUserHelper = new SQLiteUserHelper(this);
        User user = sqLiteUserHelper.checkUserLogin();
        // Xu Ly Trong truong hop load anh dai dien loi
        if (user != null) {
            Picasso.Builder builder = new Picasso.Builder(this);
            builder.listener(new Picasso.Listener() {
                @Override
                public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                    avatarUser.setImageResource(R.drawable.user_login);
                }
            });
            builder.build().load(user.getAvatar() + "").into(avatarUser);
        }
    }


    //        chuyen cac tap thi select cung chuyen theo
    public void ChuyenPage() {
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        navigation.getMenu().findItem(R.id.canhanNAV).setChecked(true);
                        break;
                    case 1:
                        navigation.getMenu().findItem(R.id.khamphaNAV).setChecked(true);
                        break;
                    case 2:
                        navigation.getMenu().findItem(R.id.zingchartNAV).setChecked(true);
                        break;

                    case 3:
                        navigation.getMenu().findItem(R.id.userNAV).setChecked(true);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public void init() {
        // Thanh Phan Co Ban
        viewPager = findViewById(R.id.viewPager);
        navigation = findViewById(R.id.navigationBottom);
        //header
        avatarUser = findViewById(R.id.avatarUser);
        btnSearch = findViewById(R.id.btnSearch);
        btnSpeach = findViewById(R.id.btnSpeach);

        //Phan Play Song
        imageSong = findViewById(R.id.imageSongM);
        nameSong = findViewById(R.id.nameSongM);
        singerSong = findViewById(R.id.singerSongM);
        btnFavorite = findViewById(R.id.btnFavorite);
        btnPlay = findViewById(R.id.btnPlay);
        btnNext = findViewById(R.id.btnNext);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnPlay:
                if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                    btnPlay.setImageResource(R.drawable.pause_song);
                    mediaPlayer.pause();
                } else {
                    if (play) {
                        mediaPlayer = MediaPlayer.create(this, R.raw.anhchuadutucach);
                        play = false;
                    }
                    btnPlay.setImageResource(R.drawable.play_small);
                    mediaPlayer.start();
                }
                break;
            case R.id.btnSearch:
                Intent intent= new Intent(this,ActivitySearch.class);
                startActivity(intent);
                break;
        }
    }

    public void backupDataLocal() {
        SQLiteBaiHatHelper sqLiteBaiHatHelper = new SQLiteBaiHatHelper(this);
        Song song1 = new Song(1, "https://photo-resize-zmp3.zadn.vn/w165_r1x1_jpeg/cover/b/7/b/5/b7b5b99e4aa374702ce8ee64858a9bbb.jpg", "Chỉ Là Không Cùng Nhau", "Tăng Phúc, Trương Thảo Nhi", "chi_la_khong_cung_nhau");
        Song song2 = new Song(2, "https://photo-resize-zmp3.zadn.vn/w94_r1x1_jpeg/cover/c/0/4/7/c047c4e29dbeda34deacbe2d8dbb71dc.jpg?fs=MTYyMDmUsICxMjM2NDE0NHx3ZWJWNHw", "Chiều Thu Hòa Bóng Nàng", "DatKaa", "chieu_thu_hoa_bong_nang");
        Song song3 = new Song(3, "https://photo-resize-zmp3.zadn.vn/w240_r1x1_jpeg/avatars/5/7/7/6/57760259b083b58f89befbc1a6a53951.jpg",
                "Cô Độc Vương", "Thiên Tú", "co_doc_vuong");
        Song song4 = new Song(4, "https://photo-resize-zmp3.zadn.vn/w94_r1x1_jpeg/cover/2/0/c/8/20c8208e735601981e8d3b85b3d4cacd.jpg?fs=MTYyMDmUsICxMjM2NDE0NHx3ZWJWNH", "Họ Yêu Ai Mất Rồi", "Doãn Hiếu, B.", "ho_yeu_ai_mat_roi");
        Song song5 = new Song(5, "https://photo-resize-zmp3.zadn.vn/w240_r1x1_jpeg/cover/5/a/8/c/5a8c15e6afcb3bb7352b69546c626205.jpg", "Anh Chưa Đủ Tư Cách",
                "Lý Tuấn Kiệt", "anhchuadutucach");
        sqLiteBaiHatHelper.addSong(song1);
        sqLiteBaiHatHelper.addSong(song2);
        sqLiteBaiHatHelper.addSong(song3);
        sqLiteBaiHatHelper.addSong(song4);
        sqLiteBaiHatHelper.addSong(song5);
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (mediaPlayer != null) {
            mediaPlayer.pause();
            btnPlay.setImageResource(R.drawable.pause_song);
            if (isFinishing()) {
                mediaPlayer.stop();
                mediaPlayer.release();
            }
        }
    }


    private Boolean exit = false;
    @Override
    public void onBackPressed() {
        if (exit) {
            this.finishAffinity();
        } else {
            Toast.makeText(this, "Press Back again to Exit.",
                    Toast.LENGTH_SHORT).show();
            exit = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    exit = false;
                }
            }, 3 * 1000);

        }

    }
}