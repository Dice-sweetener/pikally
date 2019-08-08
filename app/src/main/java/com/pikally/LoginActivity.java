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
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    Button Login;
    EditText LEmail,LPassword;
    TextView forgotPw,CreateAcc;
    private ProgressDialog loadingBar;
    private FirebaseAuth mAuth;
    FirebaseUser currentUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Login = (Button) findViewById(R.id.RegBtn);
        LEmail =(EditText)findViewById(R.id.EmaillLogIn);
        LPassword =(EditText)findViewById(R.id.RegPw);
        forgotPw =(TextView) findViewById(R.id.logmeIntxt);
        CreateAcc =(TextView) findViewById(R.id.createAccTxt);

        mAuth = FirebaseAuth.getInstance();
        currentUser=mAuth.getCurrentUser();

        loadingBar = new ProgressDialog(this);

        //go to register page
        CreateAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent accountIntent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(accountIntent);
            }
        });

        forgotPw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent forgotIntent = new Intent(LoginActivity.this,forgetPwActivity.class);
                startActivity(forgotIntent);
            }
        });



        //log in to account

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = LEmail.getText().toString();
                String password = LPassword.getText().toString();
                
                LoginCustom(password,email);
            }
        });
    }


    //login method code
    private void LoginCustom(String password, String email) {
        if(TextUtils.isEmpty(email)){
            Toast.makeText(LoginActivity.this,"Enter Email...",Toast.LENGTH_SHORT).show();
        }

        if(TextUtils.isEmpty(password)){
            Toast.makeText(LoginActivity.this,"Enter Password...",Toast.LENGTH_SHORT).show();
        } else{

            loadingBar.setTitle("Customer LogIn");
            loadingBar.setMessage("Please wait while we Log you In...");
            loadingBar.show();
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Intent mainIntent = new Intent(LoginActivity.this,drawerActivity.class);
                                startActivity(mainIntent);
                                //Toast.makeText(LoginActivity.this,"Registration Sucessful!",Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();

                            }
                            else{
                                Toast.makeText(LoginActivity.this,"LogIn Not Sucessful, Please Try Again!",Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();
                            }

                        }
                    });

        }
    }


    //UserAuthentication if user is already logged in or not
    @Override
    protected void onStart() {
        super.onStart();

        if(currentUser != null){
            sendUsertoMainActivity();
        }
    }

    //send user to main page if already logged in method

    private void sendUsertoMainActivity() {
        Intent welcomeIntent = new Intent(LoginActivity.this,drawerActivity.class);
        startActivity(welcomeIntent);
    }




}
