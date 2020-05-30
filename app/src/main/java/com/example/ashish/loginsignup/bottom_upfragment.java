package com.example.ashish.loginsignup;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.TimeUnit;

/**
 * Created by ashish on 5/16/2020.
 */
public class bottom_upfragment extends BottomSheetDialogFragment {
    FirebaseAuth fAuth;
    private  String verID;
    private  String rcd;

    private Button B1;
    private EditText mOtp;
    private TextView textView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View v=inflater.inflate(R.layout.bottom_layout,container,false);
       fAuth=FirebaseAuth.getInstance();
       B1=(Button)v.findViewById(R.id.btnDialog);
       mOtp=(EditText)v.findViewById(R.id.otp1);
       textView=(TextView)v.findViewById(R.id.status);
        if(getArguments()!=null){
            rcd=getArguments().getString("mPhone");
            sendVerificationCode(rcd);
        }
        B1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verifyCode(mOtp.getText().toString().trim());
            }
        });

       return v;
    }
    private  void verifyCode(String code){

        PhoneAuthCredential credential= PhoneAuthProvider.getCredential(verID,code);


            signInwithCredentials(credential);
    }

    private void signInwithCredentials(PhoneAuthCredential credential) {
        fAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(getActivity(), "verified", Toast.LENGTH_SHORT).show();
                            textView.setText("Verified");
                            DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("Users");

                        }

                    }
                });

    }
    private void sendVerificationCode(String s)
    {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                s,
                60,
                TimeUnit.SECONDS,
                TaskExecutors.MAIN_THREAD,
                mCallback
        );


    }
    private  PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallback=new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            verID=s;



        }

        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
              String code= phoneAuthCredential.getSmsCode();
              if(code!=null){
                  verifyCode(code);
                  mOtp.setText(code);
              }



        }

        @Override
        public void onVerificationFailed(FirebaseException e) {


        }
    };

}
