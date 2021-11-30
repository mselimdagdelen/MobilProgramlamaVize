package com.example.suygulama;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class StarterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.starter);
        Thread timerThread = new Thread(){
            public void run(){
                try{
                    sleep(2000);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }finally{
                    Intent intent = new Intent(StarterActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        };
        timerThread.start();
    }
}