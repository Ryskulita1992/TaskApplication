package kg.geektech.taskapprestored.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.session.MediaSession;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.auth.Token;

import java.util.concurrent.TimeUnit;

import kg.geektech.taskapprestored.MainActivity;
import kg.geektech.taskapprestored.R;
import kg.geektech.taskapprestored.ui.onboard.BoardFragment;

public class PhoneActivity extends AppCompatActivity {

    private String verificationID;

    private EditText editPhone, editCode;
    private Button submit, submitCode;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks callbacks;
    private TextView countryCode;
    private LottieAnimationView anim;
    private FirebaseAuth mAuth;
    PhoneAuthProvider.ForceResendingToken mResendToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone);
        mAuth = FirebaseAuth.getInstance();
        countryCode = findViewById(R.id.country_code);
        anim = findViewById(R.id.anim);
        editPhone = findViewById(R.id.phoneNum);
        submit = findViewById(R.id.submit);
        editCode = findViewById(R.id.edit_code_num);
        submitCode = findViewById(R.id.submit_code);
        callbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                Log.e("ololo", "onVerificationCompleted");
                String code = phoneAuthCredential.getSmsCode();
                if (code != null) {
                    verifyCode(code);
                }
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                Log.e("ololo", "" + e.getMessage());
            }

            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                Log.e("ololo", "onCodeSent");
                verificationID = s;
                mResendToken = forceResendingToken;
            }
        };
    }

    private void verifyCode(String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationID, code);
        Log.e("ololo", ""+ verificationID.toString());
        signIn(credential);
    }

    private void signIn(PhoneAuthCredential phoneAuthCredential) {
        FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                        } else {
                            Log.e("ololo", "" + task.getException().getMessage());
                            Toast.makeText(PhoneActivity.this, "", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    public void submit(View view) {
        String phone = countryCode.getText().toString().trim() + editPhone.getText().toString().trim();
        PhoneAuthProvider.getInstance().verifyPhoneNumber
                (phone, 60, TimeUnit.SECONDS, this, callbacks);
        Log.e("ololo", "PhoneNumber = " + phone);
        editPhone.setVisibility(View.GONE);
        submit.setVisibility(View.GONE);
        countryCode.setVisibility(View.GONE);
        editCode.setVisibility(View.VISIBLE);
        submitCode.setVisibility(View.VISIBLE);
    }

    public void submitCode(View view) {
        String code = editCode.getText().toString().trim();
        if (code.isEmpty()|| code.length()<6){
            Toast.makeText(this, "failed", Toast.LENGTH_SHORT).show();
            editCode.requestFocus();
            return;
        }
            verifyCode(code);
            startActivity(new Intent(PhoneActivity.this, MainActivity.class));
         finish();
        }
    }

