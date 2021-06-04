package com.example.btlandroid.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.btlandroid.R;
import com.example.btlandroid.login.Activity_login;
import com.example.btlandroid.model.User;
import com.example.btlandroid.sqlite.SQLiteUserHelper;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;


public class Fragment_User extends Fragment implements View.OnClickListener {
    private View v;
    private CircleImageView imageView;
    private TextView nameUser;
    private SQLiteUserHelper sqLiteUserHelper;

    private User userlogin;

    private Button btnDangXuat, btnCodeVip, btnMuaVip;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment__user, container, false);
        init();
        setData();
        btnDangXuat.setOnClickListener(this);
        return v;
    }

    public void init() {
        imageView = v.findViewById(R.id.imageUser);
        nameUser = v.findViewById(R.id.nameUser);
        btnDangXuat = v.findViewById(R.id.btnDangXuat);
        btnMuaVip = v.findViewById(R.id.btnMuaVip);
        btnCodeVip = v.findViewById(R.id.btnCodeVip);
    }

    public void setData() {
        sqLiteUserHelper = new SQLiteUserHelper(v.getContext());
        userlogin = sqLiteUserHelper.checkUserLogin();

        // Load anh tu Host ve Truong hop anh loi se de anh mac dinh
        Picasso.Builder builder = new Picasso.Builder(v.getContext());
        builder.listener(new Picasso.Listener()
        {
            @Override
            public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                imageView.setImageResource(R.drawable.user_login);
                System.out.println("LOAD ANH LOI NE");
            }
        });
        try {
            builder.build().load(userlogin.getAvatar()+"").into(imageView);
            nameUser.setText(userlogin.getName());
        }catch (Exception e){
            imageView.setImageResource(R.drawable.user_login);
        }



    }

    @Override
    public void onClick(View v) {
        if (v == btnDangXuat) {
            Intent intent = new Intent(v.getContext(), Activity_login.class);
            sqLiteUserHelper.updateUserLoginOut(userlogin.getId());
            v.getContext().startActivity(intent);
        }
    }
}