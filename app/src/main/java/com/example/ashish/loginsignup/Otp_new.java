package com.example.ashish.loginsignup;

import android.app.Fragment;
import android.os.Bundle;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;


public class Otp_new extends Fragment {

    FirebaseAuth fAuth;
    private  String verID;
    private String rcd;
    private EditText e1;
    private Button b1;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fAuth=FirebaseAuth.getInstance();
        ViewGroup root=(ViewGroup) inflater.inflate(R.layout.fragment_otp_new,null);
        e1=(EditText) root.findViewById(R.id.otp);
        b1=(Button) root.findViewById(R.id.button2);
        if(getArguments()!=null){
            rcd=getArguments().getString("mPhone");
            sendVerificationCode(rcd);
        }
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verifyCode(e1.getText().toString().trim());
            }
        });
        return root;
    }
    private  void verifyCode(String code){

        PhoneAuthCredential credential= PhoneAuthProvider.getCredential(verID,code);

       if(credential!=null) {
           signInwithCredentials(credential);
       }
       else {
           e1.setText("No credential");
       }
    }

    private void signInwithCredentials(PhoneAuthCredential credential) {
        fAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(getActivity(), "verified", Toast.LENGTH_SHORT).show();
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
          //  String code= phoneAuthCredential.getSmsCode();




        }

        @Override
        public void onVerificationFailed(FirebaseException e) {


        }
    };





}
