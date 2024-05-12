package com.example.monitoringmenudapur;

import static androidx.constraintlayout.motion.widget.TransitionBuilder.validate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.Nullable;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.os.Bundle;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;
    private Button btnLogin;
    private EditText Username, Password;

    SharedPreference sharedPreference;

    Activity context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Username = (EditText) findViewById(R.id.txt_username);
        Password = (EditText) findViewById(R.id.txt_password);
        btnLogin = (Button) findViewById(R.id.btn_login);

        sharedPreference = new SharedPreference();

        String name = sharedPreference.getValue(context);

        if (name != null) {
            Intent intent = new Intent(context, MainActivity.class);
            startActivity(intent);
            finish();
        }

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { login() ;}
        });
    }

    private void login() {
        Log.d(TAG, "Login");

        if (!validate()){
            onLoginFailed();
            return;
        }

        btnLogin.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();

        String isiUser = Username.getText().toString();
        String isiPass = Password.getText().toString();

        InputMethodManager imm =
                (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(Username.getWindowToken(), 0);

        sharedPreference.save(this, isiUser);
        Toast.makeText(context, "Berhasil login", Toast.LENGTH_SHORT).show();

        new android.os.Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                onLoginSucces();
                progressDialog.dismiss();
            }
        },3000);
    }

    private void onLoginSucces() {
        Intent intent = new Intent(context, MainActivity.class);
        startActivity(intent);
        btnLogin.setEnabled(true);
        finish();
    }

    private void onLoginFailed() {
        Toast.makeText(this, "Login gagal", Toast.LENGTH_SHORT).show();
        btnLogin.setEnabled(true);
    }

    private boolean validate() {
        boolean valid = true;

        String user = Username.getText().toString();
        String pass = Password.getText().toString();

        if (user.isEmpty()){
            Username.setError("Username tidak boleh kosong");
            valid = false;
        }else{
            Username.setError(null);
        }

        if (pass.isEmpty()){
            Password.setError("Password tidak boleh kosong");
            valid = false;
        }else{
            Password.setError(null);
        }
        return valid;
    }
}