package com.camm.foodizz.ui.entry;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.camm.foodizz.R;
import com.camm.foodizz.ui.home_menu.HomeMenuActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private EditText edtEmailLogin, edtPasswordLogin;
    private CheckBox cbRememberUser;

    private SharedPreferences sharedPreferences;

    private static final int REQUEST_CODE_REGISTER = 201;
    private static final String LOGIN_DATA_FILE = "loginData";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sharedPreferences = getSharedPreferences(LOGIN_DATA_FILE, MODE_PRIVATE);

        Mapping();

        autoFillLoginData();

    }

    private void Mapping(){
        edtEmailLogin = findViewById(R.id.edtEmailLogin);
        edtPasswordLogin = findViewById(R.id.edtPasswordLogin);
        cbRememberUser = findViewById(R.id.cbRememberUser);
    }

    private void autoFillLoginData(){
        edtEmailLogin.setText(sharedPreferences.getString("email", ""));
        edtPasswordLogin.setText(sharedPreferences.getString("password", ""));
        cbRememberUser.setChecked(sharedPreferences.getBoolean("isSaveLoginData", false));
    }

    public void performSignUp(View view) {
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivityForResult(intent, REQUEST_CODE_REGISTER);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == REQUEST_CODE_REGISTER && resultCode == RESULT_OK && data != null){
            edtEmailLogin.setText(data.getStringExtra("email"));
            edtPasswordLogin.setText(data.getStringExtra("password"));
            cbRememberUser.setChecked(false);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void performSignIn(View view) {
        final String email = edtEmailLogin.getText().toString().trim();
        final String password = edtPasswordLogin.getText().toString().trim();
        final boolean isChecked = cbRememberUser.isChecked();

        if(email.equals("") || password.equals("")){
            Toast.makeText(LoginActivity.this, "Fields can't be empty", Toast.LENGTH_SHORT).show();
        }
        else {
            FirebaseAuth mAuth = FirebaseAuth.getInstance();
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                saveLoginData(email, password, isChecked);
                                Intent intent = new Intent(LoginActivity.this, HomeMenuActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            } else {
                                Toast.makeText(LoginActivity.this, "Sign in failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

    private void saveLoginData(String email, String password, boolean isChecked){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if(isChecked){
            editor.putString("email", email);
            editor.putString("password", password);
            editor.putBoolean("isSaveLoginData", true);
            editor.apply();
        }
        else {
            editor.remove("email");
            editor.remove("password");
            editor.remove("isSaveLoginData");
            editor.apply();
        }
    }

}
