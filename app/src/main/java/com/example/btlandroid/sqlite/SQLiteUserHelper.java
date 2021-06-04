package com.example.btlandroid.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.btlandroid.model.Song;
import com.example.btlandroid.model.User;

import java.util.ArrayList;
import java.util.List;

public class SQLiteUserHelper extends SQLiteMusicDBHelper {

    public SQLiteUserHelper(@Nullable Context context) {
        super(context);
    }

    public User getUserByIdFb(String idfacebook){

        String whereClause="idfacebook=?";
        String whereargs[]={idfacebook};
        SQLiteDatabase st=getReadableDatabase();
        Cursor cursor=st.query("user",null,whereClause,whereargs,null,null,null);
        if (cursor!=null && cursor.moveToNext()){
            User user=new User(cursor.getInt(0),cursor.getString(1),cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),cursor.getString(5)
                    ,cursor.getString(6),cursor.getInt(7));
            return user;
        }
        return null;
    }
    public User getUserByAccount(User user){

        String whereClause="email=? and password=?";
        String whereargs[]={user.getEmail(),user.getPassword()};
        SQLiteDatabase st=getReadableDatabase();
        Cursor cursor=st.query("user",null,whereClause,whereargs,null,null,null);
        if (cursor!=null && cursor.moveToNext()){
            User userLogin =new User(cursor.getInt(0),cursor.getString(1),cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),cursor.getString(5)
                    ,cursor.getString(6),cursor.getInt(7));
            return userLogin;
        }
        return null;
    }

    public User checkUserLogin(){
        String whereClause="online=?";
        String whereargs[]={String.valueOf(1)};
        SQLiteDatabase st=getReadableDatabase();
        Cursor cursor=st.query("user",null,whereClause,whereargs,null,null,null);
        if (cursor!=null && cursor.moveToNext()){
            User user=new User(cursor.getInt(0),cursor.getString(1),cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),cursor.getString(5)
                    ,cursor.getString(6),cursor.getInt(7));
            return user;
        }
        return null;
    }

    public long addUser(User user) {
        ContentValues v = new ContentValues();
        v.put("name", user.getName());
        v.put("email", user.getEmail());
        v.put("password", user.getPassword());
        v.put("avatar", user.getAvatar());
        v.put("idfacebook",user.getIdFacebook());
        v.put("idgoogle",user.getIdGoogle());
        v.put("online",user.getOnline());
        SQLiteDatabase st = getWritableDatabase();
        return st.insert("user", null, v);
    }
    public int updateUserNotLogin(long id) {
        ContentValues v = new ContentValues();
        v.put("online",0);
        String whereClause = "id!=?";
        String[] whereArgs = {String.valueOf(id)};
        SQLiteDatabase st = getWritableDatabase();
        return st.update("user", v, whereClause, whereArgs);
    }
    public int updateUserLoginOut(long id) {
        ContentValues v = new ContentValues();
        v.put("online",0);
        String whereClause = "id=?";
        String[] whereArgs = {String.valueOf(id)};
        SQLiteDatabase st = getWritableDatabase();
        return st.update("user", v, whereClause, whereArgs);
    }
    public int updateUserLogin(long id) {
        ContentValues v = new ContentValues();
        v.put("online",1);
        String whereClause = "id=?";
        String[] whereArgs = {String.valueOf(id)};
        SQLiteDatabase st = getWritableDatabase();
        return st.update("user", v, whereClause, whereArgs);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {


    }
}
