package com.elvis.shopping.list;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by elvis on 15/09/14.
 */
public class SplashActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        Thread logoTimer = new Thread() {
            public void run(){
                try{
                    int logoTimer = 0;
                    while(logoTimer < 3000){
                        sleep(100);
                        logoTimer = logoTimer + 100;
                    }
                    startActivity(new Intent("com.elvis.shopping.list.CLEARSCREEN"));
                }

                catch (InterruptedException e) {
                    //TODO Auto-generated catch block
                    e.printStackTrace();
                }

                finally{
                    finish();
                }
            }
        };
        logoTimer.start();
    }
}
