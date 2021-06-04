package com.example.btlandroid.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.example.btlandroid.model.Song;
import com.example.btlandroid.model.User;

import java.util.ArrayList;
import java.util.List;

public class SQLiteYeuThichHelper extends  SQLiteMusicDBHelper{

    public SQLiteYeuThichHelper(@Nullable Context context) {
        super(context);
    }
    public List<Song> getAllBaiHatYeuThich(){
        List<Song> list=new ArrayList<>();
        String sql="SELECT baihat.*  FROM (select * from user where online=1) as online " +
                "JOIN yeuthich ON online.id=yeuthich.iduser " +
                "JOIN baihat on yeuthich.idbaihat=baihat.id";
        SQLiteDatabase sqLiteDatabase=getReadableDatabase();
        Cursor cursor=sqLiteDatabase.rawQuery(sql,null);
        while (cursor!=null && cursor.moveToNext()){
        list.add(new Song(cursor.getInt(0),cursor.getString(1),
                cursor.getString(2),cursor.getString(3),
                cursor.getString(4)
                ));
        }
        return list;
    }
    public long addYeuThich(int idUser,int idbaiHat) {
        ContentValues v = new ContentValues();
        v.put("iduser", idUser);
        v.put("idbaihat",idbaiHat);
        SQLiteDatabase st = getWritableDatabase();
        return st.insert("yeuthich", null, v);
    }
    public int deleteBaiHatYeuThich(int iduser,int idbaihat){
        String whereClause= " iduser = ? and idbaihat= ?";
        String[] whereArgs = {Integer.toString(iduser),Integer.toString(idbaihat)};
        SQLiteDatabase sqLiteDatabase=getWritableDatabase();
        return sqLiteDatabase.delete("yeuthich",whereClause,whereArgs);
    }
    public int checkYeuthich(int iduser,int idbaihat){
        String whereClause= " iduser = ? and idbaihat= ?";
        String[] whereArgs = {Integer.toString(iduser),Integer.toString(idbaihat)};
        SQLiteDatabase sqLiteDatabase=getReadableDatabase();
        Cursor cursor=sqLiteDatabase.query("yeuthich",null,whereClause,whereArgs,null,null,null);
        if(cursor!=null && cursor.moveToNext()){
            return 1;
        }
        return 0;
    }

}
