package kg.geektech.taskapprestored.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthSettings;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.hbb20.CountryCodePicker;

import java.util.concurrent.TimeUnit;

import kg.geektech.taskapprestored.MainActivity;
import kg.geektech.taskapprestored.R;

public class PhoneActivity extends AppCompatActivity {
    private EditText editText, edit_otp;
    Button submit, submit_otp;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks callbacks;
    CountryCodePicker countryCodePicker;
    private boolean isCodeSent ;
    PhoneAuthCredential phoneAuthCredential;
    private String verification;


//    String phoneNum = "+16505554567";
//    String testVerificationCode = "123456";
//    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
//    FirebaseAuthSettings firebaseAuthSettings = firebaseAuth.getFirebaseAuthSettings();
//    firebaseAuthSettings.


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone);
        editText = findViewById(R.id.edit_auth);

        edit_otp = findViewById(R.id.edit_otp);
        edit_otp.setVisibility(View.GONE);
        submit_otp = findViewById(R.id.submit_otp);
        submit_otp.setVisibility(View.GONE);
        submit = findViewById(R.id.submit);

        countryCodePicker = findViewById(R.id.ccp);
        callbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                phoneAuthCredential.getSmsCode();
                signIn(phoneAuthCredential);
//                if (isCodeSent) {
//                    //submit
//
//
//                } else {
                    signIn(phoneAuthCredential);
                //}

                Log.e("ololo", "OnVerification");

            }


            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                Log.e("ololo", "OnVerification failed" + e.getMessage());
            }

            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                verification = s;
                isCodeSent=true;
                    Log.e("ololo","OnCode sent");

            }

        };
    }


    private void signIn(PhoneAuthCredential phoneAuthCredential) {
        FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    startActivity(new Intent(PhoneActivity.this, MainActivity.class));
                    finish();
                } else {
                    Toast.makeText(PhoneActivity.this, "Sign in was failed" , Toast.LENGTH_SHORT).show();
                    Log.e("ololo", "Incorrect Verification Code , Toast.LENGTH_LONG).show();"+ task.getException().getMessage());
                }
            }
        });
    }


    public void submit(View view) {
        phoneAuthCredential.getSmsCode();
        //signIn(phoneAuthCredential);
        String phone= editText.getText().toString().trim();

        if(phone.isEmpty()){
            editText.setError("Phone number is required");
            editText.requestFocus();
            return;
        }else if(phone.length() < 9 ){
            editText.setError("Please enter a valid phone");
            editText.requestFocus();
            return;
        }else if (isCodeSent=true){
                    editText.setVisibility(View.GONE);
                    submit.setVisibility(View.GONE);
                    countryCodePicker.setVisibility(View.GONE);
                    edit_otp.setVisibility(View.VISIBLE);
                    submit_otp.setVisibility(View.VISIBLE);

        }

        Toast.makeText(this, "Code was sent, don`t share with anyone!", Toast.LENGTH_SHORT).show();
        PhoneAuthProvider.getInstance().verifyPhoneNumber(phone, 60, TimeUnit.SECONDS, this, callbacks);

    }

    public void submit_otp(View view) {
        String code = edit_otp.getText().toString();
        if (code.isEmpty()){
            edit_otp.setError("You should enter the  SMS code  ");
            edit_otp.requestFocus();

        }
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verification, code);
        signIn(credential);

    }
}
