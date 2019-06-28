package com.project.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

 
  
public class MyConstant {

 
	 
	 
 
   public static final long THREAD_SLEEP = 50;//50毫秒 
	  
 	
 	public static final int NAME_NONE=-10, NAME_TANK_US=10,NAME_TANK_ANIM=20,NAME_BLOCK_NORMAL=30,NAME_BLOCK_HARD=40,
 			NAME_BLOCK_WATER=50,NAME_BULLET=60,NAME_BLOCK_TREE=70,NAME_BIRD=80;//子弹 墙体
 	
 	
 	public static final int DIRECT_UP=1,DIREC_LEFT=0,DIRECT_RIGHT=2,DIRECT_DOWN=3;
 	public static final long BULLET_TIME=1000;//产生子弹时间的 
 	
 	public static final int GAME_MODE_SINGLE=0 ;//游戏模式 
 	public static final int GAME_MISSION_ONE=0,GAME_MISSION_TWO=1,GAME_MISSION_THREEE=2;
	public static final long ADD_TANK_TIME = 3000;//生产塔克时间
	public static final int TANK_COUNT = 20;
 	
 
 
	public static final int SUCESS = 0X5013;
 	 
 	
 	
 	
 	
 	
 	
 	
 	
 	
		
		 public static long stringToDate(String strTime )
		            throws ParseException {
		        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm");
		        Date date = null;
		        date = formatter.parse(strTime);
		        return date.getTime();
		    }
		 
		  public static String TimeSearchFormat(long time) {
				//  获取特定时间格式
				  String s="null";
		          SimpleDateFormat formate=new SimpleDateFormat("yyyy年MM月dd日");
		          s=formate.format(new Date(time));
		          
				  return s;
			}
		 
	  public static String TimeDateFormat(long time) {
		//  获取特定时间格式
		  String s="null";
          SimpleDateFormat formate=new SimpleDateFormat("yyyy年MM月dd日");
          s=formate.format(new Date(time));
          
		  return s;
	}
	  public static String TimeYearFormat(long time) {
			//  获取特定时间格式
			  String s="null";
	          SimpleDateFormat formate=new SimpleDateFormat("yyyy年");
	          s=formate.format(new Date(time));
	          
			  return s;
		}
	  public static String TimeMouthFormat(long time) {
			//  获取特定时间格式 获取到月
			  String s="null";
	          SimpleDateFormat formate=new SimpleDateFormat("yyyy年MM月");
	          s=formate.format(new Date(time));
	          
			  return s;
		}
	  
	  public static String TimeDayFormat(long time) {
			//  获取特定时间格式 获取到日
			  String s="null";
	          SimpleDateFormat formate=new SimpleDateFormat("MM月dd日");
	          s=formate.format(new Date(time));
	          
			  return s;
		}
	  public static String TimeHourFormat(long time) {
			//  获取特定时间格式
			  String s="null";
	          SimpleDateFormat formate=new SimpleDateFormat("yyyy/MM/dd HH:mm");
	          s=formate.format(new Date(time));
	          
			  return s;
		}
	  
	  
	  public static String TimeToHourFormat(long time) {
			//  获取特定时间格式 获取到日
			  String s="null";
	          SimpleDateFormat formate=new SimpleDateFormat("MM月dd日 HH:mm");
	          s=formate.format(new Date(time));
	          
			  return s;
		}
	  public static String timeShow(long time) {
			//  获取特定时间格式
			  String s="";
			   
	           if (time<1000) {
				   s="00:00";
			   }
	           if (time>=1000&&time<1000*60) {
				    int a=(int) (time/1000);
				    if (a<10) {
						s="00:0"+a;
					}else {
						s="00:"+a;
					}
					
			  }
	           if (time>=1000*60&&time<1000*60*60) {
	        	   String last =":00";
	        	    long mils=time%(1000*60);
	        	     if (mils<1000) {
						 last=":00";
					   }else
	        	     if (mils>=1000&&mils<1000*60) {
	 				    int a=(int) (mils/1000);
	 				    if (a<10) {
	 				    	last=":0"+a;
	 					}else {
	 						last=":"+a;
	 					   }
 	 			        }
	        	    
	        	    int min=(int) (time/(1000*60));
	        	     if (min<10) {
						s="0"+min+last;
					  }else {
						s=min+last;
					}
	        	    
	        	  
			  }
	          
	          
			  return s;
		}
	  
	  
	  
	  
	  public static String dirutionFormat(long time) {
			//  获取特定时间格式
			  String s="null";
	           if (time<1000) {
				 s=time+"毫秒";
			  }
	           if (time>=1000&&time<1000*60) {
				   s=time/1000+"秒"+dirution(time%1000);
			}
	           if (time>=1000*60&&time<1000*60*60) {
				  s=time/(1000*60)+"分"+dirution(time%(1000*60));
			}
	           if (time>=1000*60*60) {
				 s=time/(1000*60*60)+"时"+dirution(time%(1000*60*60));
			}
	          
			  return s;
		}
	  
	  private static String dirution(long time) {
			//  获取特定时间格式
			  String s=null;
	           if (time<1000) {
				 s=time+"毫秒";
			      }
	           if (time>=1000&&time<1000*60) {
				   s=time/1000+"秒";
			}
	           if (time>=1000*60&&time<1000*60*60) {
				  s=time/(1000*60)+"分";
			    }
	           
	          
			  return s;
		}
}
