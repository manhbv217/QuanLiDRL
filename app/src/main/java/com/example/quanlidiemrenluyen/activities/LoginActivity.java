package com.example.quanlidiemrenluyen.activities;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.quanlidiemrenluyen.R;
import com.example.quanlidiemrenluyen.helper.SessionManager;

public class LoginActivity extends AppCompatActivity {
    EditText edtUsername, edtPassword;
    SessionManager sessionManager;
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtPassword = findViewById(R.id.edtPassword);
        edtUsername = findViewById(R.id.edtUserName);
        btnLogin = findViewById(R.id.btnLogin);
        sessionManager = new SessionManager(getApplication());

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = edtUsername.getText().toString();
                String password = edtPassword.getText().toString();
                Log.d("aa", "onClick: " + username + "-" + password);
                if (username.equals("link") && password.equals("123")) {
                    sessionManager.SetLogin(true);
                    CheckLogin();
                    Toast.makeText(LoginActivity.this, "Dang nhap thanh cong", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(LoginActivity.this, "Dang nhap that bai", Toast.LENGTH_SHORT).show();
                }
            }
        });
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        CheckLogin();
    }


    private void CheckLogin() {
        if (sessionManager.CheckLogin() == true) {
            Intent intent = new Intent(getApplication(), MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

}
