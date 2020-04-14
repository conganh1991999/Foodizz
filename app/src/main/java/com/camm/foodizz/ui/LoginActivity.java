package com.camm.foodizz.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.camm.foodizz.R;
import com.camm.foodizz.models.SQLiteHandler;
import com.camm.foodizz.ui.home_menu.HomeMenuActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private Button btnSignIn, btnSignUpLogin;
    private EditText edtEmailLogin, edtPasswordLogin;
    private CheckBox cbRememberUser;

    private SQLiteHandler database = new SQLiteHandler(this, "users.sqlite", null, 1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Mapping();
        autoFillEmailPassword();

        btnSignUpLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = edtEmailLogin.getText().toString().trim();
                final String password = edtPasswordLogin.getText().toString().trim();

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
                                        // Sign in success, update UI with the signed-in user's information
                                        if(cbRememberUser.isChecked()){
                                            database.updateLoginInfo(email, password);
                                        }
                                        Intent intent = new Intent(LoginActivity.this, HomeMenuActivity.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Toast.makeText(LoginActivity.this, "Sign in failed", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });

    }

    private void Mapping(){
        btnSignIn = findViewById(R.id.btnSignIn);
        btnSignUpLogin = findViewById(R.id.btnSignUpLogin);
        edtEmailLogin = findViewById(R.id.edtEmailLogin);
        edtPasswordLogin = findViewById(R.id.edtPasswordLogin);
        cbRememberUser = findViewById(R.id.cbRememberUser);
    }

    private void autoFillEmailPassword(){
        Cursor cursor = database.getLoginData();
        if(cursor.moveToFirst()){
            edtEmailLogin.setText(cursor.getString(1));
            edtPasswordLogin.setText(cursor.getString(2));
        }
    }
}
