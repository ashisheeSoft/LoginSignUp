package com.example.ashish.loginsignup;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private Button b1;
    private EditText e1,e2,e3,e4;
    boolean flag =true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        b1 = (Button) findViewById(R.id.senOtp);
        e1=(EditText)findViewById(R.id.uName);
        e2=(EditText)findViewById(R.id.uPhone);
        e3=(EditText)findViewById(R.id.uPassword);
        e4=(EditText)findViewById(R.id.uCPassword);
        String test= "+91"+e2.getText().toString();
        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("Users");
        databaseReference.child(test);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(dataSnapshot.getValue()==null){
                    flag=false;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(MainActivity.this,"Error in database",Toast.LENGTH_LONG).show();

            }
        });

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phone=e2.getText().toString().trim();
                String username=e1.getText().toString().trim();
                String password=e3.getText().toString().trim();
                String cpassword=e4.getText().toString().trim();

                if(TextUtils.isEmpty(username)){
                    e1.setError("Field required");
                    return;
                }


                if(TextUtils.isEmpty(phone) || phone.length()<10){
                    e2.setError("too short");
                    return;
                }
                else{
                    phone="+91"+phone;
                }


                if(TextUtils.isEmpty(password)){
                    e1.setError("Field required");
                    return;
                }
                if(TextUtils.isEmpty(cpassword)){
                    e1.setError("Field required");
                    return;
                }

              if(flag==false) {
                  Bundle bundle = new Bundle();
                  bundle.putString("mPhone", phone);

                  bottom_upfragment bottomUpfragment = new bottom_upfragment();
                  bottomUpfragment.setArguments(bundle);


                  bottomUpfragment.show(getSupportFragmentManager(), "example");
              }
              else
              {
                  Toast.makeText(MainActivity.this,"No. already exists",Toast.LENGTH_LONG).show();
              }


//                Otp_new otp_new=new Otp_new();
//                otp_new.setArguments(bundle);

//                FragmentManager fragmentManager = getFragmentManager();
//                final FragmentTransaction t = fragmentManager.beginTransaction();
//
//                t.replace(android.R.id.content,otp_new);
//                t.commit();

            }
        });


    }

}
