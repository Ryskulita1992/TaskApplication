package kg.geektech.taskapprestored.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.NotProvisionedException;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.OAuthCredential;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

import kg.geektech.taskapprestored.MainActivity;
import kg.geektech.taskapprestored.R;

public class PhoneActivity extends AppCompatActivity {
    EditText editPhoneNum, editCodeNum;
    TextView textCountryCode;
    private String verificationID;
    Button submit, submitCode;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks callbacks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone);
       // setContentView(R.layout.phone_second_activity);
        editPhoneNum=findViewById(R.id.phoneNum);
        textCountryCode=findViewById(R.id.country_code);
        submit=findViewById(R.id.submit);
        submitCode=findViewById(R.id.submit_code);
        editCodeNum=findViewById(R.id.edit_code_num);
        //final boolean isReceived;
        callbacks=new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                   String code=phoneAuthCredential.getSmsCode();
                   if (code!=null){
                    verifyCode(code);
                    return;

                }
                signIn(phoneAuthCredential);
                Log.e("ololo", "Verification completed ");


            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                Toast.makeText(getParent(), "Verification is failed",
                        Toast.LENGTH_LONG).show();
                Log.e("ololo", ""+ e.getMessage());

            }

            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.
                    ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                Log.e("ololo", "sending code if user enter the " +
                        "app after long time, On code will not apply the method if " +
                        "user entered the app few minutes ago");
                verificationID=s;
                editCodeNum.setVisibility(View.VISIBLE);
                submitCode.setVisibility(View.VISIBLE);

            }
        };
    }
    private void verifyCode(String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationID, code);
        signIn(credential);
    }

    private void signIn(PhoneAuthCredential phoneAuthCredential) {
            FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
            Log.e("ololo", "signInWithCredential works after onVerification ");
            if (task.isSuccessful()){
               // startActivity(new Intent(PhoneActivity.this, MainActivity.class));
            }else{
                Toast.makeText(getParent(), "Sign in was failed", Toast.LENGTH_SHORT).show();
            } }
        });

    }

    public void countryCode(View view) {

    }

    public void submit(View view) {
        String phone=textCountryCode.getText().toString().trim()+ editPhoneNum.getText().toString().trim();
        PhoneAuthProvider.getInstance().verifyPhoneNumber(phone, 60, TimeUnit.SECONDS, this, callbacks);
        editPhoneNum.setVisibility(View.GONE);
        submitCode.setVisibility(View.GONE);
        textCountryCode.setVisibility(View.GONE);
    }

    public void submitCode(View view) {
       String code=editCodeNum.getText().toString().trim();
        if (code.isEmpty()){
         return;
        }
        verifyCode(code);
        finish();
    }




}
