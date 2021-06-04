package com.example.btlandroid.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.btlandroid.model.Song;

import java.util.ArrayList;
import java.util.List;

public class SQLiteBaiHatHelper extends SQLiteMusicDBHelper{

    public SQLiteBaiHatHelper(@Nullable Context context) {
        super(context);
    }

    public long addSong(Song song) {
        ContentValues v = new ContentValues();
        v.put("image",song.getImage());
        v.put("name",song.getName());
        v.put("singer",song.getSinger());
        v.put("file",song.getFile());
        SQLiteDatabase st = getWritableDatabase();
        return st.insert("baihat", null, v);
    }

    public List<Song> getAllSong(){
        List<Song> listSong=new ArrayList<>();
        SQLiteDatabase st=getReadableDatabase();
        Cursor cursor=st.query("baihat",null,null,
                null,null,null,null);
        while (cursor!=null &&cursor.moveToNext()){
            Song song=new Song(cursor.getInt(0),cursor.getString(1),cursor.getString(2),
                    cursor.getString(3),cursor.getString(4));
            listSong.add(song);
        }
        return listSong;
    }

    public List<Song> getSongByName(String name){
        List<Song> listSong=new ArrayList<>();

        String whereClause="name like ?";
        String whereargs[]={"%"+name+"%"};
        SQLiteDatabase st=getReadableDatabase();
        Cursor cursor=st.query("baihat",null,whereClause,whereargs,null,null,null);
        while (cursor!=null && cursor.moveToNext()){
            Song song=new Song(cursor.getInt(0),cursor.getString(1),cursor.getString(2),
                    cursor.getString(3),cursor.getString(4));
            listSong.add(song);
        }
        return listSong;
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
