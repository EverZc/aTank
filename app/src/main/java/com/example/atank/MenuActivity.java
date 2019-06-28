package com.example.atank;

import java.util.ArrayList;
import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.DialogInterface;
import android.content.IntentFilter;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.project.util.BomMedia;
import com.project.util.MyConstant;
import com.project.util.MyView;
import com.project.util.ScoreBean;
import com.project.util.TankSql;
import com.project.util.BackMedia;
import com.project.util.SAdapter;



public class MenuActivity extends Activity{
    
     private LinearLayout  controlLayout;
     private Button startBtn, scoreBtn,exitBtn,mAdminBtn;
     private MyView mSurface;
     private BackMedia mMedia;
     private Button setBtn;
     private CheckBox mCheckSoud,mCheckMusic ;
      private TankSql sql;
     private ListView mListview;
     private BomMedia bomMedia;
     
     
     Button mLeftBtn,mRightBtn,mUpBtn,mDownBtn,mFireBtn;
     
     
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
             Window window = getWindow();
             window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS|WindowManager.LayoutParams.FLAG_FULLSCREEN);
          }else {
        	  Window window = getWindow();
              window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
		}
        
        setContentView(R.layout.activity_menu);
        
        getWH(this);
        initView();
        clickEvent();
        frontThread();
    }
    
    
    
	private Toast mToast;
      private void initView() {
		//  初始化控件
    	 
    	  mAdminBtn=(Button) findViewById(R.id.admin_btn);
    	   
    	  mLeftBtn=(Button) findViewById(R.id.con_left_btn);
    	  mRightBtn=(Button) findViewById(R.id.con_right_btn);
    	  mUpBtn=(Button) findViewById(R.id.con_up_btn);
    	  mDownBtn=(Button) findViewById(R.id.con_down_btn);
    	  mFireBtn=(Button) findViewById(R.id.con_fire_btn);
    	  
    	  
    	  
    	  bomMedia=new BomMedia(this);
    	  sql=new TankSql(this);
    	  exitBtn=(Button) findViewById(R.id.eixt_btn);
          startBtn=(Button) findViewById(R.id.start_btn);
           
          scoreBtn=(Button) findViewById(R.id.score_btn);
          controlLayout=(LinearLayout) findViewById(R.id.control_layout);
          mSurface=(MyView) findViewById(R.id.myserfaceview);
          
          mSurface.setSql(sql);
          mSurface.setMedia(bomMedia, isAllowSound);
        
          setBtn=(Button) findViewById(R.id.set_btn);

     		View mRView = LayoutInflater.from(this).inflate(R.layout.choice_view, null);  
     		mRemindTv=(TextView)mRView.findViewById(R.id.remind_tv);	        
         	View mPopView = LayoutInflater.from(this).inflate(R.layout.setx, null);  
      	  mCheckMusic=(CheckBox) mPopView.findViewById(R.id.musci_check);
      	mCheckSoud=(CheckBox) mPopView.findViewById(R.id.sound_check);
      	// 设置界面的初始化
      
    	
      	View mScoreView = LayoutInflater.from(this).inflate(R.layout.scorelistx, null);  
     	 mListview=(ListView) mScoreView.findViewById(R.id.score_list);
     	 // 设置分数界面
        
          mMedia=new BackMedia(this);
          
          IntentFilter intentFilter = new IntentFilter();
          intentFilter.addAction(BluetoothDevice.ACTION_FOUND);
          intentFilter.addAction(BluetoothDevice.ACTION_BOND_STATE_CHANGED);
          
       
          mLoginBtn=(Button) findViewById(R.id.login_btn);
 	      mRegistBtn=(Button) findViewById(R.id.regist_btn);
 	      mNewuserBtn=(Button) findViewById(R.id.login_newuser_btn);
 	      
 	      mLoginUserEdit=(EditText) findViewById(R.id.login_username_edit);
 	      mLoginPasswordEdit=(EditText) findViewById(R.id.login_password_edit);
 	     
 	      mRegistPasswordEdit=(EditText) findViewById(R.id.regist_password_edit);
 	      mRegistPasswrodmorEdit=(EditText) findViewById(R.id.regist_passwrodonece_edit);
 	      mRegistUserEdit=(EditText) findViewById(R.id.regist_username_edit);
 	      
 	      mToast = Toast.makeText(this, "", Toast.LENGTH_SHORT);	
 	      
 	      mLoginLayout=(LinearLayout) findViewById(R.id.login_layout);
 	      mRegistLayout=(LinearLayout) findViewById(R.id.regist_layout);
 	      
 	      mLoginCloBtn=(Button) findViewById(R.id.login_close_btn);
 	      mRegistCloBtn=(Button) findViewById(R.id.regist_close_btn);
 	     mSql=new TankSql(getApplicationContext());
	}
  
      /**
 	     * 登录注册相关按钮控件
 	     */
 		private Button mLoginBtn;
 		private Button mRegistBtn;
 		private EditText mLoginUserEdit;
 		private Button mNewuserBtn;
 		private EditText mLoginPasswordEdit;
 		private EditText mRegistPasswordEdit;
 		private EditText mRegistPasswrodmorEdit;
 		private EditText mRegistUserEdit;
 		private LinearLayout mLoginLayout;
 		private LinearLayout mRegistLayout;
 		private Button mLoginCloBtn;
 		private Button mRegistCloBtn;
      
 		 private void adminClick() {
 			//  登录注册 事件
 	    	mLoginCloBtn.setOnClickListener(new OnClickListener() {
 	    		
 	    		@Override
 	    		public void onClick(View v) {
 	    			 
 	    			mLoginLayout.setVisibility(View.GONE);
 	    		}
 	    	});
 	        mRegistCloBtn.setOnClickListener(new OnClickListener() {
 	    		
 	    		@Override
 	    		public void onClick(View v) {
 	    			 mRegistLayout.setVisibility(View.GONE);
 	    			 mLoginLayout.setVisibility(View.VISIBLE);
 	    			 
 	    		}
 	    	});
 	    	
 	    	mNewuserBtn.setOnClickListener(new OnClickListener() {
 	    		
 	    		@Override
 	    		public void onClick(View v) {
 	    			 mRegistLayout.setVisibility(View.VISIBLE);
 	    			  mLoginLayout.setVisibility(View.GONE);
 	    		}
 	    	});
 	    	
 	    	mLoginBtn.setOnClickListener(new OnClickListener() {
 	    		
 	    		@Override
 	    		public void onClick(View v) {
 	    			// TODO Auto-generated method stub
 	    			
 	        	 loginEvent();
 	     		
 	    		}
 	    	});
 	    mRegistBtn.setOnClickListener(new OnClickListener() {
 	    		
 	    		@Override
 	    		public void onClick(View v) {
 	    		 registEvent();
 	    		}
 	    	});
 	    	
 		}
 	    private boolean isLogined=false;
 	    private String userName="a@";//用户名
		private TankSql mSql;
 	    
 	    private void loginEvent() {
 			// 登录
 	        if (!isLogined) {
 				 String name=mLoginUserEdit.getText().toString();
 				 String password=mLoginPasswordEdit.getText().toString();
 				 if (name!=null&&password!=null&&name.length()>0&&password.length()>0) {
 					  Cursor c=mSql.getUserApp(null, null);
 					   while (c.moveToNext()) {
 						   if (c.getString(c.getColumnIndex("name")).equals(name)) {
 							  if (c.getString(c.getColumnIndex("password")).equals(password)) {
 								  isLogined=true;
 								  mLoginLayout.setVisibility(View.GONE);
 								  userName=name;
 								 
 								   
 								  break;
 							}
 						}
 						
 					}
 					   c.close();
 					   if (isLogined) {
 						   showTip("登录成功");
 						  mAdminBtn.setText("注销  "+userName);
 						   mSurface.setName(userName);
 					}else {
 						showTip("用户名或密码错误！");
 					}
 					 
 				}else {
 					 showTip("请填写完整用户名和密码!");
 				}
 	        	
 			 }
 	    	
 		   }
 	   
 	    private void registEvent() {
 			// 注册事件
 	    	  String name=mRegistUserEdit.getText().toString();
 	    	  String password=mRegistPasswordEdit.getText().toString();
 	    	  String passwordone=mRegistPasswrodmorEdit.getText().toString();
 	    	 
 	    	  
 	    	 
 	    	  if (name!=null&&password!=null&&passwordone!=null
 	    			  &&!name.equals("")&&!password.equals("")&&!passwordone.equals("")) {
 				      if (password.length()>5&&passwordone.length()>5) {
 						 if (password.equals(passwordone)) {
 							  boolean ishas=false;
 							 Cursor c=mSql.getUserApp(null, null);
 							 while ( c.moveToNext()) {
 								  if (c.getString(c.getColumnIndex("name")).equals(name)) {
 									 ishas=true;
 								}
 								
 							}
 							 c.close();
 							 if (ishas) {
 								 showTip("用户已存在");
 							}else {
 								mSql.addUserEvent(name, password);
 								mRegistLayout.setVisibility(View.GONE);
 								mLoginLayout.setVisibility(View.VISIBLE);
 								showTip("注册成功，请登陆！");
 							}
 						}else {
 							showTip("两次密码不一致");
 						}
 				    	  
 					}else {
 						showTip("密码长度不能少于6位");
 					}
 			}else {
 				showTip("请填写完整信息");
 			}
 		}
 	    private void showTip(final String str)
 		{
 			runOnUiThread(new Runnable() {
 				@Override
 				public void run() {
 					mToast.setText(str);
 					mToast.show();
 				}
 			});
 		}
 		
      
      
      
      
	        private void clickEvent() {
	        	
	        	mAdminBtn.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						     if (isLogined) {
								 isLogined=false;
								 mAdminBtn.setText("登录");
							}
						    mLoginLayout.setVisibility(View.VISIBLE);
					}
				});
	        	 mFireBtn.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View v) {
							//  设置
						   mSurface.usFires( );
						}
					});   	 
          mLeftBtn.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						//  设置
					   mSurface.setDrec(MyConstant.DIREC_LEFT   );
					}
				});
          
        	 
          mRightBtn.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						//  设置
					   mSurface.setDrec(MyConstant.DIRECT_RIGHT );
					}
				});
          
          
        	 
          mDownBtn.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						//  设置
					   mSurface.setDrec(MyConstant.DIRECT_DOWN );
					}
				});
        	 
          mUpBtn.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						//  设置
					   mSurface.setDrec(MyConstant.DIRECT_UP );
					}
				});
	        	
	        	
	        	
	        	
	        	setBtn.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						//  设置
						SetWindowEvent();
					}
				});
				 startBtn.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						//  开始游戏
						 controlLayout.setVisibility(View.GONE);
					 	 isControl=false;
						 mSurface.isPause=false;
     					 mSurface.gameStart(false);
     					 mSurface.setStop(false);
     					 mSurface.setmMode(MyConstant.GAME_MODE_SINGLE);
				 }    
				 });
 
				 scoreBtn.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// 显示分数
					   ScoreWindowEvent();
					}
				});
				 exitBtn.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// 退出游戏
						finish();
						
					}
				});
			}
	        
	        
	     
	    
	          private boolean isAllowSound=true,isAllowMusic=true;
	          //允许 播放音乐和音效
	        public void SetWindowEvent() {
	        	//音效设置 界面
	       		View mPopView = LayoutInflater.from(this).inflate(R.layout.setx, null);  
	       		final PopupWindow mPopWindow = new PopupWindow(mPopView,4*mWidth/4,  
	       				2*mHeight/2, true);  
	       		mPopWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));  
	       			mPopView.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);  
	       		int popupWidth = mPopView.getMeasuredWidth();  
	       		int popupHeight = mPopView.getMeasuredHeight();  
	       		mPopWindow.showAtLocation(setBtn, Gravity.CENTER, 0, 0);
	       		mPopWindow.update(); 
	       		mPopWindow.setOutsideTouchable(false);
	       	  mCheckMusic=(CheckBox) mPopView.findViewById(R.id.musci_check);
	        	mCheckSoud=(CheckBox) mPopView.findViewById(R.id.sound_check);
	        	 if (isAllowMusic) {
					 mCheckMusic.setChecked(false);
				}else {
					mCheckMusic.setChecked(true);
				}
	        	 
	        	 if (isAllowSound) {
					 mCheckSoud.setChecked(false);
				}else {
					mCheckSoud.setChecked(true);
				}
	              mPopView.findViewById(R.id.set_sure_btn).setOnClickListener(new OnClickListener() {
	      			
	      			@Override
	      			public void onClick(View v) {
	      				 if (mPopWindow!=null) {
	      					  if (mCheckMusic.isChecked()) {
								        isAllowMusic=false;
								        mMedia.Pause();
 							            }else {
 							            	  mMedia.Play(true);
  							        	
 							        	 isAllowMusic=true;
									}
	      					  if (mCheckSoud.isChecked()) {
								 isAllowSound=false;
							}else {
								isAllowSound=true;
							}
	      					  mSurface.setMedia(bomMedia, isAllowSound);
	      					  mPopWindow.dismiss();
	      				 }
	      			}
	      		});
	        }
	        
	        
	         private int whichPlane=0;
	         private final static int PLANEA=0,PLANEB=1;
	      
	        
	        
	         private ArrayList<ScoreBean>scoreList=new ArrayList<ScoreBean>();
	        public void ScoreWindowEvent() {
	        	//显示 分数排行
	       		View mPopView = LayoutInflater.from(this).inflate(R.layout.scorelistx, null);  
	       		final PopupWindow mPopWindow = new PopupWindow(mPopView,4*mWidth/4,  
	       				3*mHeight/3, true);  
	       		mPopWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));  
	       			mPopView.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);  
	       		int popupWidth = mPopView.getMeasuredWidth();  
	       		int popupHeight = mPopView.getMeasuredHeight();  
	       		mPopWindow.showAtLocation(setBtn, Gravity.CENTER, 0, 0);
	       		mPopWindow.update(); 
	       		mPopWindow.setOutsideTouchable(false);
	       	  mListview=(ListView) mPopView.findViewById(R.id.score_list);
	            if (scoreList!=null) {
					scoreList.clear();
				}
	       	    Cursor d=sql.getscores(null, "score DESC");// 数据库中获取数据的方式
	       	    while (d.moveToNext()) {
				    ScoreBean bean=new ScoreBean();
				    bean.score=d.getInt(d.getColumnIndex("score"));
				    bean.time=d.getString(d.getColumnIndex("time"));
				    scoreList.add(bean);
					
				}
	       	    d.close();
	       	    mListview.setAdapter(new SAdapter(this,scoreList));
	       	    
	              
	        }
	        
	        TextView mRemindTv;
	        PopupWindow mRemindWindow;
	        public void remindEvent() {
	        	// 提醒 
	       		View mPopView = LayoutInflater.from(this).inflate(R.layout.choice_view, null);  
	       		final PopupWindow mPopWindow = new PopupWindow(mPopView,4*mWidth/4,  
	       				3*mHeight/3, true);  
	       		mPopWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));  
	       			mPopView.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);  
	       		int popupWidth = mPopView.getMeasuredWidth();  
	       		int popupHeight = mPopView.getMeasuredHeight();  
	       		mPopWindow.showAtLocation(setBtn, Gravity.CENTER, 0, 0);
	       		mPopWindow.update(); 
	       		mPopWindow.setOutsideTouchable(false);
	       		
	       		mRemindWindow=mPopWindow;
	       	 
	       		mRemindTv=(TextView) mPopView.findViewById(R.id.remind_tv);	        
               }
	        
	        ListView mPlayerListV;
	        TextView mCurrenTV;
	        
	     
	      
	        PopupWindow mMenuWindow;
	     
	        private int mWidth,mHeight;
	        private void getWH(Context context) {
	        	//获得屏幕高宽
	        	DisplayMetrics ds=new DisplayMetrics();
	        	WindowManager wm = (WindowManager) context
	        	 	.getSystemService(Context.WINDOW_SERVICE);
	            wm.getDefaultDisplay().getMetrics(ds);
	             
	        	mHeight=(int) ((ds.heightPixels));
	        	mWidth=(int)( ds.widthPixels);
	        }
	@Override
    protected void onPause() {
     
		 
		 try {
			mMedia.Pause();
			 mSurface.isPause=true;
		} catch (Exception e) {
			 
			e.printStackTrace();
		}
		 super.onPause();
    }
    @Override
    protected void onResume() {
         mSurface.isPause=false;
    	super.onResume();
    }

  @Override
protected void onDestroy() {
	//  销毁 绘图线程
	 
	  mSurface.isRun=false;
	  mMedia.Pause();
	  try {
		mMedia.release();
		
		 
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	super.onDestroy();
}
 
	  private boolean isRun=true;
      private boolean isEntered=true;
      private  Handler han;
      private void frontThread() {
			//  刷新 线程
     	 
     	han=new Handler(){
     		 @Override
     		public void handleMessage(Message msg) {
     			  if (msg.what==0x00) {
						try {
						 
							adminClick();
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						 
					}
     			  
     			 
     			 
     			if (msg.what==MyConstant.SUCESS) {
				     remindDia();
			    }
     			super.handleMessage(msg);
     		}
     	 };
       mSurface.setHandler(han);
        new Thread(
     		   new Runnable() {
					
					@Override
					public void run() {
						while (isRun) {
							 if (isEntered) {
								 isEntered=false;
								 han.sendEmptyMessageDelayed(0x00, 1500);
 								 
							   }
							 
							
							try {
								Thread.sleep(1000);
							} catch (Exception e) {
								 
							}
						} 
						
					}
				}
     		   ).start();
		}
       private void setReminText(final String txt) {
		//  显示提醒信息
    	   
    	 runOnUiThread(new Runnable() {
			
			@Override
			public void run() {
				 mRemindTv.setText(txt+"");
				
			}
		})  ;

	}
      
      
      
     
      
     
      
       private boolean isControl=true;
      @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
    	// 按键事件
    	  if (keyCode==KeyEvent.KEYCODE_BACK&&event.getRepeatCount()==0) {
			    if (!isControl) {
					isControl=true;
				    controlLayout.setVisibility(View.VISIBLE);
				  
				   mMedia.Pause();
				   mSurface.isPause=true;
 				  }else{
					try {
						finish();
						mMedia.release();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			  return true;  
		}
    	  
    	return super.onKeyDown(keyCode, event);
    }
	 
      int mission=1;
      private void remindDia() {
	      AlertDialog.Builder dialog = new AlertDialog.Builder(this);
	 
		 dialog.setIcon(R.drawable.tankboss);
		
	     dialog.setTitle("恭喜您");
	     dialog.setMessage("成功");
	
 
		 	dialog.setNegativeButton("主页", new DialogInterface.OnClickListener()
				{
					@Override
					public void onClick(DialogInterface dialog, int which)
					{
						  isControl=true;
						   controlLayout.setVisibility(View.VISIBLE);
						    
						 
		 				   
						   mMedia.Pause();
						   mSurface.isPause=true;
					}
				});
				
			 
			   dialog.setPositiveButton("下一关", new DialogInterface.OnClickListener()
				{
					@Override
					public void onClick(DialogInterface dialog, int which)
					{
						    mission=mission+1;
						    mSurface.setMisson(mission);
						    mSurface.gameStart(false);
						 
 
					}
				});
			 dialog.create();
			 dialog.show();
		  
      }
          
      
}
