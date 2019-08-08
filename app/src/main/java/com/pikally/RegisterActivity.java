package com.pikally;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {
    private Button RegBtn;
    private EditText Name,Email,Password,surname;
    private TextView loginMein;
    private FirebaseAuth mAuth;
    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        RegBtn = (Button) findViewById(R.id.RegBtn);
        Name =(EditText) findViewById(R.id.RegNameTxt);
        Email =(EditText)findViewById(R.id.RegEmailTxt);
        Password=(EditText)findViewById(R.id.RegPw);
        loginMein =(TextView) findViewById(R.id.logmeIntxt);
        surname =(EditText) findViewById(R.id.RegsurNameTxt);

        mAuth = FirebaseAuth.getInstance();

        loadingBar = new ProgressDialog(this);

        loginMein.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent loginIntent = new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(loginIntent);
            }
        });


        RegBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               final String email = Email.getText().toString();
                String password = Password.getText().toString();
               final String name = Name.getText().toString();
              final   String Surname = surname.getText().toString();


                RegisterClient(email,password);

            }
        });
    }

    private void RegisterClient(final String email, String password) {

        if(TextUtils.isEmpty(email)){
            Toast.makeText(RegisterActivity.this,"Enter Email...",Toast.LENGTH_SHORT).show();
        }

        if(TextUtils.isEmpty(password)){
            Toast.makeText(RegisterActivity.this,"Enter Password...",Toast.LENGTH_SHORT).show();
        } else{

            loadingBar.setTitle("Customer Registration");
            loadingBar.setMessage("Please wait while we register your Data");
            loadingBar.show();
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(RegisterActivity.this,"Registration Sucessful!",Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();
                                Intent loggedinIntent = new Intent(RegisterActivity.this,drawerActivity.class);
                                startActivity(loggedinIntent);

                                User user = new User(

                                  email,surname,Name

                                );
                                FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            Toast.makeText(RegisterActivity.this,"Registration Sucessful!",Toast.LENGTH_SHORT).show();
                                        }else{

                                            Toast.makeText(RegisterActivity.this,"Registration Not Sucessful Please Try Again!",Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });



                            }
                            else{
                                Toast.makeText(RegisterActivity.this,"Registration Not Sucessful Please Try Again!",Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();
                            }

                        }
                    });

        }
    }
}
