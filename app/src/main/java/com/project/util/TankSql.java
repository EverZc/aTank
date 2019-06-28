package com.project.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
 
import android.database.sqlite.SQLiteOpenHelper;

public class TankSql extends SQLiteOpenHelper{
	private static final String DATABASE_NAME="tank.db"; 
	private static final int SCHEMA_VERSION=1; 
	public TankSql(Context context 
	 ) {
		super(context, DATABASE_NAME, null, SCHEMA_VERSION);
		
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
  // 创建数据库表
 	    
 	   db.execSQL("CREATE TABLE  IF NOT EXISTS hisScore (_id INTEGER PRIMARY KEY AUTOINCREMENT,name TEXT,score int,level int,time TEXT);");
 	   db.execSQL("CREATE TABLE  IF NOT EXISTS users (_id INTEGER PRIMARY KEY AUTOINCREMENT,name TEXT,password TEXT);");//用户信息
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
	}
			  
			   public void addScoreEvent(String name,int score,int level,String time) {
				 
			    	ContentValues cv=new ContentValues();
					SQLiteDatabase db = this.getWritableDatabase(); 	
					
					 cv.put("name", name);
				   
					 cv.put("score", score);
					 cv.put("level", level);
					 cv.put("time", time);
					 db.insert("hisScore",null,cv);
				} 	   
			   
			   public Cursor getscores(String where, String orderBy) { 
			 
					StringBuilder buf=new StringBuilder("SELECT * FROM hisScore");
					SQLiteDatabase db = this.getWritableDatabase(); 
					if (where!=null) {
						buf.append(" WHERE ");
						buf.append(where);
					}
					
					if (orderBy!=null) {
						buf.append(" ORDER BY ");
						buf.append(orderBy);
					}
					
					return(db.rawQuery(buf.toString(), null));
				}
			   public void addUserEvent(String name, String password) {
					 
			    	ContentValues cv=new ContentValues();
					SQLiteDatabase db = this.getWritableDatabase(); 	
					
					 cv.put("name", name);
					 cv.put("password", password);
					 
					 db.insert("users",null,cv);
					 db.close();
				} 
			   
			    
			    
			   
			  public void cancelUserEvent(String name) {
					SQLiteDatabase db = this.getWritableDatabase(); 
			             
			    	db.execSQL("delete from users where name="+"'"+name+"'");
			    	
			    	db.close();
				}
				public Cursor getUserApp(String where, String orderBy) { 
					//查询 用户信息
					StringBuilder buf=new StringBuilder("SELECT * FROM users");
					SQLiteDatabase db = this.getWritableDatabase(); 
					if (where!=null) {
						buf.append(" WHERE ");
						buf.append(where);
					}
					
					if (orderBy!=null) {
						buf.append(" ORDER BY ");
						buf.append(orderBy);
					}
					
					return(db.rawQuery(buf.toString(), null));
				}
				
				
				  
				   
				  public void updateUserPassword(String newPassword,String name) {
				    	SQLiteDatabase db = this.getWritableDatabase(); 
				    	
				        db.execSQL("UPDATE users SET password = "+"'"+newPassword+"'"+" WHERE name = "+"'"+name+"'");
				    	db.close();
					    }
				   public void deleteAllUser() {
				    	SQLiteDatabase db = this.getWritableDatabase(); 
				    	 db.execSQL("delete from users");
				    	db.close();
					    }
				     
			 
    public void releaseEvent() {
    	//释放资源
		SQLiteDatabase db = this.getWritableDatabase(); 
		db.close();
	}  
     
  
}
