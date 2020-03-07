package com.camm.foodizz.begin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.camm.foodizz.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;
import java.util.regex.Pattern;

import de.hdodenhof.circleimageview.CircleImageView;

public class RegisterActivity extends AppCompatActivity {

    private CircleImageView profileImage;
    private EditText edtNameRegister, edtEmailRegister, edtPasswordRegister, edtPhoneNumber;
    private Button btnSignup;
    ProgressBar progressRegister;

    private String username;
    private String email;
    private String password;
    private String phone;

    private static final int REQUEST_CODE_FOLDER = 101;
    private static final int REQUEST_CODE_PHONE_VERIFICATION = 171;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Mapping();

        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(
                        RegisterActivity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_CODE_FOLDER
                );
            }
        });

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                username = edtNameRegister.getText().toString();
                email = edtEmailRegister.getText().toString();
                password = edtPasswordRegister.getText().toString();
                phone = edtPhoneNumber.getText().toString();

                if(validateAccount()){

                    Intent intent = new Intent(RegisterActivity.this, VerifyPhoneActivity.class);
                    intent.putExtra("phone", phone);
                    startActivityForResult(intent, REQUEST_CODE_PHONE_VERIFICATION);

                }
            }
        });
    }

    private void Mapping(){
        profileImage = findViewById(R.id.profileImage);
        edtNameRegister = findViewById(R.id.edtNameRegister);
        edtEmailRegister = findViewById(R.id.edtEmailRegister);
        edtPasswordRegister = findViewById(R.id.edtPasswordRegister);
        edtPhoneNumber = findViewById(R.id.edtPhoneNumber);
        btnSignup = findViewById(R.id.btnSignup);
        progressRegister = findViewById(R.id.progressRegister);
    }

    private boolean validateAccount(){
        String usernameRegex = "\\w*";

        if(email.equals("") || password.equals("") || username.equals("") || phone.equals("")){
            Toast.makeText(RegisterActivity.this, "Fields can't be empty",
                    Toast.LENGTH_SHORT).show();
            return false;
        }

        if(!Pattern.matches(usernameRegex, username)){
            Toast.makeText(RegisterActivity.this, "Username can only contain letters, digits and underscores",
                    Toast.LENGTH_SHORT).show();
            return false;
        }

        if(username.length() > 30){
            Toast.makeText(RegisterActivity.this, "Username too long",
                    Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private void saveUserImage(final FirebaseUser user){

        Calendar calendar = Calendar.getInstance();

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference("images").child("userImages");
        final StorageReference imageRef = storageRef.child("image" + calendar.getTimeInMillis() + ".png");

        // Get the data from an ImageView as bytes
        profileImage.setDrawingCacheEnabled(true);
        profileImage.buildDrawingCache();
        Bitmap bitmap = ((BitmapDrawable) profileImage.getDrawable()).getBitmap();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
        byte[] data = outputStream.toByteArray();

        // Create a storage reference from our app
        UploadTask uploadTask = imageRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Toast.makeText(RegisterActivity.this, exception.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        saveUserToDatabase(user, uri.toString());
                    }
                });
            }
        });
    }

    private void saveUserToDatabase(final FirebaseUser newUser, String userImage){

        String userId;
        assert newUser != null;
        userId = newUser.getUid();

        FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
        DatabaseReference mRef = mDatabase.getReference("users").child(userId);
        mRef.child("userName").setValue(username);
        mRef.child("userImage").setValue(userImage);
        mRef.child("phoneNumber").setValue(phone);

        gotoLoginActivity();
    }

    private void gotoLoginActivity(){
        Toast.makeText(RegisterActivity.this, "Sign up successfully", Toast.LENGTH_SHORT).show();
        progressRegister.setVisibility(View.GONE);
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        intent.putExtra("email", email);
        intent.putExtra("password", password);
        startActivity(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if(requestCode == REQUEST_CODE_FOLDER && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            startActivityForResult(intent, REQUEST_CODE_FOLDER);
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == REQUEST_CODE_FOLDER && resultCode == RESULT_OK && data != null){
            Uri uri = data.getData();
            assert uri != null;
            Picasso.get().load(uri).into(profileImage);
        }
        if(requestCode == REQUEST_CODE_PHONE_VERIFICATION && resultCode == RESULT_OK && data!=null){
            if(data.getBooleanExtra("checker", false)){
                progressRegister.setVisibility(View.VISIBLE);
                final FirebaseAuth mAuth = FirebaseAuth.getInstance();
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    saveUserImage(mAuth.getCurrentUser());
                                } else {
                                    if(task.getException() != null)
                                        Toast.makeText(RegisterActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

}
