package com.example.atank;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;


public class MainActivity extends Activity {
   
	 private ImageView mImg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
  		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
              Window window = getWindow();
               window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS );
                    }else {
              	 getWindow().addFlags( WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
              			     
              			 );
  			             }
        setContentView(R.layout.activity_main);
        mImg=(ImageView) findViewById(R.id.man_img);
        
        mImg.setImageResource(R.drawable.boms);
        AnimationDrawable  animationDrawable = (AnimationDrawable) mImg.getDrawable();
        animationDrawable.start();
   		 new Handler().postDelayed(new Runnable() {
   			
   			@Override
   			public void run() {
   				 Intent in=new Intent(MainActivity.this,MenuActivity.class);
   				 
   				 startActivity(in);
   				 
   				 finish();
   			}
   		}, 1500);

    }
}
