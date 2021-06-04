package com.example.btlandroid.login;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.btlandroid.R;
import com.example.btlandroid.model.User;
import com.example.btlandroid.sqlite.SQLiteUserHelper;

public class Activity_signup extends AppCompatActivity implements View.OnClickListener {

    private EditText email,nameUser, password,repassword;
    private Button btnDK, btnReturn;

    private SQLiteUserHelper sqLiteUserHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        init();
        btnReturn.setOnClickListener(this);
        btnDK.setOnClickListener(this);

    }
    public void init() {
        btnReturn = findViewById(R.id.btnReturn);
        btnDK = findViewById(R.id.btnDK);
        email = findViewById(R.id.email);
        nameUser=findViewById(R.id.nameUser);
        password = findViewById(R.id.password);
        repassword=findViewById(R.id.repassword);
    }

    @Override
    public void onClick(View v) {
        if(v==btnDK){
            if(validateData()) {
                sqLiteUserHelper = new SQLiteUserHelper(this);
                User user = new User();
                user.setEmail(email.getText().toString().trim());
                user.setName(nameUser.getText().toString().trim());
                user.setPassword(password.getText().toString());
                if(sqLiteUserHelper.addUser(user)!=-1){
                    Toast.makeText(this,"Đăng kí thành công",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(this,"email đã tồn tại",Toast.LENGTH_SHORT).show();
                }
            }
        }
        else if(v==btnReturn){
            finish();
        }
    }
    public boolean validateData(){
        if(email.getText().toString().trim().length()==0){
            Toast.makeText(this,"Tài khoản không được để trống",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(nameUser.getText().toString().trim().length()==0){
            Toast.makeText(this,"Họ tên không được để trống",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(password.getText().toString().trim().length()==0){
            Toast.makeText(this,"mật khẩu không được để trống",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(!password.getText().toString().equals(repassword.getText().toString())){
            Toast.makeText(this,"nhập lại mật khẩu không khớp",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

}