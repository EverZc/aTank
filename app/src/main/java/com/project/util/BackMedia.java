package com.project.util;

import java.io.IOException;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
 
 
public class BackMedia{
    
   
   
     private MediaPlayer mmediaPlayer;
     private Context mContext;
   public  BackMedia(Context context){
	   if (mmediaPlayer==null) {
		// “Ù¿÷≤•∑≈ ≥ı ºªØ
	
	   mmediaPlayer=new MediaPlayer();
	 
	   }
	     mContext=context;
		AssetManager assetManager=context.getAssets();
	
    	try {
			AssetFileDescriptor filedescriptor=assetManager.openFd("bgm.wav");
		
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
			
			
			mmediaPlayer.start();
            mmediaPlayer.setOnCompletionListener(new OnCompletionListener() {
				
				@Override
				public void onCompletion(MediaPlayer mp) {
					 
					// Reset(mContext);
					 mmediaPlayer.start();
				}
			});
		}
		
  }

	public void Reset(Context context) {
		if (mmediaPlayer!=null) {
			mmediaPlayer.stop();

		}
	
		if (mmediaPlayer!=null) {
			 
			   mmediaPlayer.reset();
			   }
			
				AssetManager assetManager=context.getAssets(); 
			
		    	try {
					AssetFileDescriptor filedescriptor1=assetManager.openFd("bgm.wav");
				 
					mmediaPlayer.setDataSource(filedescriptor1.getFileDescriptor(), 
							filedescriptor1.getStartOffset(),
							filedescriptor1.getLength());
					
					mmediaPlayer.prepare();
					
		    	} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		
	}
  public void Pause() {
	  //if (mmediaPlayer.isPlaying()==true) {
		
	             
	            try {
					mmediaPlayer.pause();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	              
	 // }
}
  public void release() {
    mmediaPlayer.release();
    
      }
  
}
