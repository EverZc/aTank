package com.project.util;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.zip.Inflater;

import com.example.atank.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class SAdapter extends BaseAdapter {
	private LayoutInflater minflater;
	private Context context;
	private List<ScoreBean>itemlist;
 
	// ≈≈––∞Ò  ≈‰∆˜
    public SAdapter(Context context,List<ScoreBean> itemlist ) {
		this.context=context;
		this.itemlist=itemlist;
		minflater=LayoutInflater.from(context);
       
		
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		
//		if (itemlist.size()>9) {
//			return 10;
//		}else{
			return itemlist.size();
//		}
		
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return itemlist.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int arg0, View convertView, ViewGroup parent) {
		viewHolder viewholder=null;
		 if (viewholder==null) {
			viewholder=new viewHolder();
			
			convertView=minflater.inflate(R.layout.listadpx,parent, false);
	 	   viewholder.num=(TextView) convertView.findViewById(R.id.adp_num);
 		    viewholder.score=(TextView) convertView.findViewById(R.id.adp_score);
   		    viewholder.time=(TextView) convertView.findViewById(R.id.adp_time);

			convertView.setTag(viewholder);
		}else {
			viewholder=(viewHolder) convertView.getTag();
		}
			
		 
		// viewholder.contentImg.setImageResource(R.drawable.bgmemo); 
          if (itemlist!=null&&itemlist.size()>0){
         	 viewholder.score.setText(itemlist.get(arg0).score+"");
        	 viewholder.num.setText(arg0+1+"");
          	 viewholder.time.setText(itemlist.get(arg0).time);
           }
          
			return convertView;
	}
 class viewHolder{
	 TextView score;
	 TextView num;
 	 TextView time;
 }
}
