package com.project.util;

import java.io.IOException;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
 
 
public class BomMedia{
    
   
   
     private MediaPlayer mmediaPlayer;
     private Context mContext;
   public  BomMedia(Context context){
	   if (mmediaPlayer==null) {
		// “Ù¿÷≤•∑≈ ≥ı ºªØ
	
	   mmediaPlayer=new MediaPlayer();
	 
	   }
	     mContext=context;
		AssetManager assetManager=context.getAssets();
	
    	try {
			AssetFileDescriptor filedescriptor=assetManager.openFd("bom.wav");
		
			mmediaPlayer.setDataSource(filedescriptor.getFileDescriptor(), 
					filedescriptor.getStartOffset(),
					filedescriptor.getLength());
			mmediaPlayer.setOnPreparedListener(new OnPreparedListener() {
				
				@Override
				public void onPrepared(MediaPlayer mp) {
					 
					
				}
			});
			mmediaPlayer.prepare();
	
    	} catch (IOException e) {
		 
			e.printStackTrace();
		}
    
   }
   public void Play(boolean isStarting) {
	 
		   
	  
		if (isStarting==true&&mmediaPlayer!=null&&mmediaPlayer.isPlaying()==false) {
			
			
			
            mmediaPlayer.setOnCompletionListener(new OnCompletionListener() {
				
				@Override
				public void onCompletion(MediaPlayer mp) {
					 
					// Reset(mContext);
 				}
			});
		}
		mmediaPlayer.start();
  }

 
  public void release() {
    mmediaPlayer.release();
    
      }
  
}
