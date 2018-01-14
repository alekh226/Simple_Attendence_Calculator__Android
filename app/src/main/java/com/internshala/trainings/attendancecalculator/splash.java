package com.internshala.trainings.attendancecalculator;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;

public class splash extends AppCompatActivity {
    MyDBHandler dbHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash);


        new CountDownTimer(3000,1000){
            @Override
            public void onTick(long millisUntilFinished){}

            @Override
            public void onFinish(){
                //set the new Content of your activity
                 dbHandler =new MyDBHandler(splash.this,null,null,1);
                int flag=dbHandler.checkMember();
                if(flag==1){
                Intent i =new Intent(splash.this,HomeActivity.class);
                splash.this.startActivity(i);}
                else {
                    Intent i =new Intent(splash.this,Loading_Page.class);
                    splash.this.startActivity(i);
                }
            }
        }.start();
    }
}
