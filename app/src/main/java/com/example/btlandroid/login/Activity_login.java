package com.example.btlandroid.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.btlandroid.MainActivity;
import com.example.btlandroid.R;
import com.example.btlandroid.model.User;
import com.example.btlandroid.sqlite.SQLiteUserHelper;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.Arrays;

public class Activity_login extends AppCompatActivity implements View.OnClickListener {

    CallbackManager callbackManager;
    private ImageButton btnFb;
    private SQLiteUserHelper sqLiteUserHelper;

    private EditText email, password;
    private Button btnDN, btnDK;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();

        checkUserLogined();
        btnDK.setOnClickListener(this);
        btnDN.setOnClickListener(this);

        btnFb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginManager.getInstance().logOut();
                LoginManager.getInstance().logInWithReadPermissions(Activity_login.this, Arrays.asList("public_profile", "email"));
                xuLyLoginFB();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void getFbInfo() {
        if (AccessToken.getCurrentAccessToken() != null) {
            GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(),
                    new GraphRequest.GraphJSONObjectCallback() {
                        @Override
                        public void onCompleted(final JSONObject me, GraphResponse response) {
                            if (me != null) {
                                sqLiteUserHelper = new SQLiteUserHelper(getApplicationContext());
                                User user = new User();
                                System.out.println(me.toString() + " data");
                                user.setIdFacebook(me.optString("id"));
                                user.setName(me.optString("name"));
                                user.setEmail(me.optString("email"));
                                user.setAvatar("https://graph.facebook.com/" + me.optString("id") + "/picture?type=large");
                                user.setOnline(1);
                                // Tim xem da co trong DB chua
                                User userDB = sqLiteUserHelper.getUserByIdFb(user.getIdFacebook());
                                //Neu khong co trong DB
                                if (userDB == null) {
                                    Toast.makeText(getApplicationContext(), "Da vao Day", Toast.LENGTH_SHORT).show();
                                    long id = sqLiteUserHelper.addUser(user);
                                    sqLiteUserHelper.updateUserLogin(id);
                                }
                                //neu da co trong DB
                                else {
                                    System.out.println(userDB.getName() + " " + userDB.getIdFacebook());
                                    sqLiteUserHelper.updateUserLogin(userDB.getId());
                                }
                            }
                        }
                    });
            Bundle parameters = new Bundle();
            parameters.putString("fields", "id,name,email");
            request.setParameters(parameters);
            request.executeAsync();
        }
    }


    public void init() {
        btnDN = findViewById(R.id.btnDN);
        btnFb = findViewById(R.id.btnFB);
        btnDK = findViewById(R.id.btnDK);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
    }


    public void xuLyLoginFB() {
        FacebookSdk.sdkInitialize(this.getApplicationContext());
        callbackManager = CallbackManager.Factory.create();

        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {

            @Override
            public void onSuccess(LoginResult loginResult) {
                // lay thong tin nguoi dung
                getFbInfo();
                Toast.makeText(getApplicationContext(), "Login Facebook success.", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();


            }

            @Override
            public void onCancel() {
                Toast.makeText(getApplicationContext(), "Login Facebook cancelled.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(getApplicationContext(), "Login Facebook error.", Toast.LENGTH_SHORT).show();
            }
        });


    }

    public void checkUserLogined() {
        sqLiteUserHelper = new SQLiteUserHelper(this);
        if (sqLiteUserHelper.checkUserLogin() != null) {
            Intent intent = new Intent(Activity_login.this, MainActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onClick(View v) {
        if (v == btnDK) {
            Intent intent=new Intent(this,Activity_signup.class);
            startActivity(intent);
        }
        else if(v==btnDN) {
            sqLiteUserHelper=new SQLiteUserHelper(this);
            User user=new User();
            user.setEmail(email.getText().toString().trim());
            user.setPassword(password.getText().toString());
            User userLogin=sqLiteUserHelper.getUserByAccount(user);
            if(userLogin!=null){
                sqLiteUserHelper.updateUserLogin(userLogin.getId());
                Intent intent=new Intent(this,MainActivity.class);
                startActivity(intent);
                System.exit(0);
            }else {
                Toast.makeText(this,"Tài khoản hoặc mật khâu sai",Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();

    }
}