package com.pikally;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseUser;

public class welcome extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        Thread thread = new Thread(){
            @Override
            public  void  run(){
                try{
                    sleep(10000);
                } catch (Exception e){
                    e.printStackTrace();

                }

                finally {
                    Intent welcomeIntent = new Intent(welcome.this,LoginActivity.class);
                    startActivity(welcomeIntent);
                }
            }

        };
        thread.start();
    }

   /* @Override
    protected void onPause() {
        super.onPause();
        finish();
    }*/



}
