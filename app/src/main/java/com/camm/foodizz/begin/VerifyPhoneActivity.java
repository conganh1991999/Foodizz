package com.camm.foodizz.begin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.camm.foodizz.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class VerifyPhoneActivity extends AppCompatActivity {

    private String verificationId;
    private String code;
    private PhoneAuthCredential credential;
    private FirebaseAuth mAuth;

    private EditText edtVerifyCode;
    private ProgressBar progressVerifyCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_phone);

        mAuth = FirebaseAuth.getInstance();
        edtVerifyCode = findViewById(R.id.edtVerifyCode);
        progressVerifyCode = findViewById(R.id.progressVerifyCode);

        String phone = getIntent().getStringExtra("phone");
        sendVerificationCode(phone); // send verify code to user

        findViewById(R.id.btnVerify).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                code = edtVerifyCode.getText().toString().trim();
                if (code.isEmpty() || code.length() < 6) {
                    edtVerifyCode.setError("Enter code...");
                    edtVerifyCode.requestFocus();
                    return;
                }
                verifyCode(code);
            }
        });

    }

    private void verifyCode(String code) {
        credential = PhoneAuthProvider.getCredential(verificationId, code);
        signInWithCredential();
    }

    private void signInWithCredential() {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(VerifyPhoneActivity.this, "Verify code successfully", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent();
                            intent.putExtra("checker", true);
                            setResult(RESULT_OK, intent);
                            finish();
                        } else {
                            if(task.getException() != null){
                                Toast.makeText(VerifyPhoneActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                            Intent intent = new Intent();
                            intent.putExtra("checker", false);
                            setResult(RESULT_OK, intent);
                        }
                    }
                });
    }

    private void sendVerificationCode(String number) {
        progressVerifyCode.setVisibility(View.VISIBLE);
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                number,
                60,
                TimeUnit.SECONDS,
                this,
                mCallBack
        );
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks
            mCallBack = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            // This callback will be invoked in two situations:
            // 1 - Instant verification: In some cases the phone number can be instantly
            // verified without needing to send or enter a verification code.
            // 2 - Auto-retrieval. On some devices Google Play services can automatically
            // detect the incoming verification SMS and perform verification without
            // user action.
            credential = phoneAuthCredential;
            signInWithCredential();
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            // This callback is invoked in an invalid request for verification is made,
            // for instance if the the phone number format is not valid.
            Toast.makeText(VerifyPhoneActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            // The SMS verification code has been sent to the provided phone number, we
            // now need to ask the user to enter the code and then construct a credential
            // by combining the code with a verification ID.
            super.onCodeSent(s, forceResendingToken);
            verificationId = s;
            // resendToken = forceResendingToken;
        }

    };
}
