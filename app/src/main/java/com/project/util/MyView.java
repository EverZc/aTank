package com.project.util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

import com.example.atank.R;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;

@SuppressLint({ "ClickableViewAccessibility", "ResourceAsColor" })
public class MyView extends SurfaceView implements Callback, Runnable {

	private Paint mPaint;
	private Canvas mCanvas;

 
	private int mHeight;
	private int mWidth;
	private SurfaceHolder holder;
	private Context context;
	private Thread t;
	private BomMedia media;
	private TankSql mSql;

	public MyView(Context context) {
		super(context);
		if (holder == null) {
			holder = this.getHolder();
			holder.addCallback(this);
			initView(context);
			initPaint();
		}

	}

	public MyView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		holder = this.getHolder();

		holder.addCallback(this);
		initView(context);
		initPaint();

	}

	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		t = new Thread(this);
		t.start();
		isRun = true;

	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		t = new Thread(this);
		t.start();
		isRun = true;

		backThread();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		isRun = false;
		try {

		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	public void setSql(TankSql sql) {
		this.mSql = sql;
	}

	// 播放 击中爆破音效
	private boolean isSound = true;

	public void setMedia(BomMedia media, boolean isSound) {
		this.media = media;
		this.isSound = isSound;
	}

	private void tounchEvent(Context context) {
		this.context = context;

		this.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {

				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:

					performClick();

					float x = event.getX();
					float y = event.getY();

					invalidate();
					break;
				case MotionEvent.ACTION_MOVE:
					performClick();
					float x1 = event.getX();
					float y1 = event.getY();

					invalidate();

					break;
				case MotionEvent.ACTION_UP:

					float x2 = event.getX();
					float y2 = event.getY();

					invalidate();
					break;
				}

				return true;
			}
		});

	}

	Handler handler;

	public void setHandler(Handler handler) {
		this.handler = handler;
	}

	private boolean isFailed = true;
	private int Score = 0;
	private boolean isSuccess = false;
	private int Misson = 1;
	public boolean isRun = true, isGetAnimy = true, isGetUsBullet = true;
	private RectF mStarRect;

	@SuppressLint("NewApi")
	private void remindEvent() {
		// 提示 窗口
		AlertDialog.Builder bulid = new Builder(context);
		bulid.setTitle("下一关！");

		Misson = Misson + 1;

		if (Misson > 3) {
			isSuccess = true;
			mSql.addScoreEvent("", Score, Misson, getSysTimeRemind());
			bulid.setNegativeButton("恭喜已通关!",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							isFailed = false;
							isSuccess = true;

							Misson = 1;
						}
					});

			bulid.setOnDismissListener(new DialogInterface.OnDismissListener() {

				@Override
				public void onDismiss(DialogInterface dialog) {

					resetEvent();

				}
			});
		} else {

			bulid.setOnDismissListener(new DialogInterface.OnDismissListener() {

				@Override
				public void onDismiss(DialogInterface dialog) {

					isFailed = false;
					isSuccess = false;

				}
			});
			bulid.setNegativeButton("下一关",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {

						}

					});
		}
		bulid.show();
	}

	protected void resetEvent() {
	 
		mBossBit = mSBossbit;
		isFailed = false;
		isSuccess = false;
		addedScore = false;
 		Score = 0;
		mAttackCount = 0;
		// Misson=1;

		isPause = true;
		mAnimyCount = 0;
		mBagList.clear();
		try {

			mAnimyList.clear();
			mUsBulletList.clear();
			mAnimyBulletList.clear();

			bomList.clear();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		initRect();

		setMissonBlock(Misson, MyConstant.GAME_MODE_SINGLE);
	}

	// 暂停
	public boolean isPause = true;;
 	private boolean addedScore = true;

	public void draw() {
		synchronized (this) {

			try {
				mCanvas = new Canvas(mBitmap);
				mCanvas = holder.lockCanvas();
				mCanvas.drawColor(Color.BLACK);
 
				if (isSuccess) {
					mCanvas.drawText("恭喜您，通关", 2 * mWidth / 5, mHeight / 2,
							textPaint);
					if (!addedScore) {
						handler.sendEmptyMessage(MyConstant.SUCESS);
						addedScore = true;

						mSql.addScoreEvent(name, Score, Misson,
								getSysTimeRemind());

					}
				}

				if (!isFailed) {

					try {
						for (int i = 0; i < bomList.size(); i++) {
							mCanvas.drawBitmap(bomBit, null, bomList.get(i),
									mPaint);

						}

					} catch (Exception e) {
						// TODO: handle exception
					}

					try {
						if (mStarRect != null) {
							mCanvas.drawBitmap(mBackBit, null, mStarRect,
									mPaint);

						}

					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					try {
						// 绘制子弹

						drawBullet(mAnimyBulletList, mCanvas, 0);
						drawBullet(mUsBulletList, mCanvas, 1);
						drawStar(mCanvas);

					} catch (Exception e) {
						// TODO: handle exception
					}

					try {
						// 绘制坦克
						drawTank(mUsAList, mCanvas);

						drawTank(mAnimyList, mCanvas);

					} catch (Exception e) {
						// TODO: handle exception
					}

					try {
						for (int i = 0; i < mBlockList.size(); i++) {
							switch (mBlockList.get(i).getCateName()) {
							case MyConstant.NAME_BLOCK_NORMAL:
								mCanvas.drawBitmap(mBlockNorBit, null,
										mBaseList.get(mBlockList.get(i).index),
										mPaint);
								break;
							case MyConstant.NAME_BLOCK_TREE:
								mCanvas.drawBitmap(mBlockTreeBit, null,
										mBaseList.get(mBlockList.get(i).index),
										mPaint);
								break;
							case MyConstant.NAME_BLOCK_WATER:
								mCanvas.drawBitmap(mBlockWaterBit, null,
										mBaseList.get(mBlockList.get(i).index),
										mPaint);
								break;
							case MyConstant.NAME_BLOCK_HARD:
								mCanvas.drawBitmap(mBlockHarBit, null,
										mBaseList.get(mBlockList.get(i).index),
										mPaint);
								break;

							case MyConstant.NAME_BIRD:
								mCanvas.drawBitmap(mBirdBit, null,
										mBaseList.get(mBlockList.get(i).index),
										mPaint);
								break;

							}

						}
						mCanvas.drawBitmap(mRTbit, null, mRectLeft, mPaint);
						mCanvas.drawBitmap(mRTbit, null, mRectRight, mPaint);

					} catch (Exception e) {
						// TODO: handle exception
					}

					try {

						mCanvas.drawText(
								"剩余生命："
										+ (mUsAList.get(0).getAllLife() - mUsAList
												.get(0).getHurtLife()), 10,
								160, mPaint);

					} catch (Exception e) {
						// TODO: handle exception
					}

					mCanvas.drawText("分       数：" + (Score), 10, 280, mPaint);
					mCanvas.drawText("当前关卡：" + (Misson), mWidth / 2
							+ (mRectWidth * 7), 280, mPaint);
					mCanvas.drawText("剩余坦克："
							+ (MyConstant.TANK_COUNT - mAttackCount), mWidth
							/ 2 + (mRectWidth * 7), 160, mPaint);

				} else {
					mCanvas.drawText("GAME OVER", 2 * mWidth / 5, mHeight / 2,
							textPaint);
					if (!addedScore) {
						addedScore = true;
						Log.e("添加分数", addedScore + "");
						mSql.addScoreEvent(name, Score, Misson,
								getSysTimeRemind());

					}
				}

				holder.unlockCanvasAndPost(mCanvas);

			} catch (Exception e) {
				// TODO Auto-generated catch block

				try {
					holder.unlockCanvasAndPost(mCanvas);
					mCanvas = null;
				} catch (Exception e1) {

					e1.printStackTrace();
				}

			}
		}
	}

	String name = "";

	public void setName(String name) {
		this.name = name;
	}

	private void drawStar(Canvas canvas) {
		// 画奖励

		for (int i = 0; i < mBagList.size(); i++) {
			canvas.drawBitmap(mBackBit, null,
					mBaseList.get(mBagList.get(i).getIndex()), mPaint);
		}

	}

	private void drawBullet(ArrayList<DataTank> mList, Canvas canvas, int whi) {
		// 绘制子弹
		for (int i = 0; i < mList.size(); i++) {
			switch (mList.get(i).getDirectState()) {
			case MyConstant.DIREC_LEFT:
				if (isBagTime && whi == 1) {
					mCanvas.drawBitmap(usBulletLevBitR, null, mList.get(i)
							.getRect(), mPaint);
				} else {
					mCanvas.drawBitmap(uBulletL, null, mList.get(i)
							.getRect(), mPaint);

				}
				break;
			case MyConstant.DIRECT_RIGHT:
				if (isBagTime && whi == 1) {
					mCanvas.drawBitmap(usBulletLevBitL, null, mList.get(i)
							.getRect(), mPaint);
				} else {
					mCanvas.drawBitmap(uBulletR, null, mList.get(i)
							.getRect(), mPaint);
				}
				break;
			case MyConstant.DIRECT_UP:
				if (isBagTime && whi == 1) {
					mCanvas.drawBitmap(usBulletLevBitU, null, mList.get(i)
							.getRect(), mPaint);
				} else {
					mCanvas.drawBitmap(mUsBulletBit, null, mList.get(i)
							.getRect(), mPaint);
				}
				break;
			case MyConstant.DIRECT_DOWN:
				if (isBagTime && whi == 1) {
					mCanvas.drawBitmap(usBulletLevBitD, null, mList.get(i)
							.getRect(), mPaint);
				} else {
					mCanvas.drawBitmap(uBulletD, null, mList.get(i)
							.getRect(), mPaint);
				}
				break;

			}

		}

	}

	private void drawTank(ArrayList<DataTank> mList, Canvas canvas) {
		// 绘制

		for (int i = 0; i < mList.size(); i++) {
			switch (mList.get(i).getDirectState()) {
			case MyConstant.DIREC_LEFT:
				if (mList.get(i).getCateName() == MyConstant.NAME_TANK_ANIM) {
					canvas.drawBitmap(atankL, null,
							mBaseList.get(mList.get(i).index), mPaint);
				} else {
					canvas.drawBitmap(mtankL, null,
							mBaseList.get(mList.get(i).index), mPaint);
				}

				break;

			case MyConstant.DIRECT_RIGHT:
				if (mList.get(i).getCateName() == MyConstant.NAME_TANK_ANIM) {
					canvas.drawBitmap(atankR, null,
							mBaseList.get(mList.get(i).index), mPaint);
				} else {
					canvas.drawBitmap(mtankR, null,
							mBaseList.get(mList.get(i).index), mPaint);
				}

				break;
			case MyConstant.DIRECT_UP:
				if (mList.get(i).getCateName() == MyConstant.NAME_TANK_ANIM) {
					canvas.drawBitmap(atankU, null,
							mBaseList.get(mList.get(i).index), mPaint);
				} else {
					canvas.drawBitmap(mtankU, null,
							mBaseList.get(mList.get(i).index), mPaint);
				}

				break;
			case MyConstant.DIRECT_DOWN:
				if (mList.get(i).getCateName() == MyConstant.NAME_TANK_ANIM) {
					canvas.drawBitmap(atankD, null,
							mBaseList.get(mList.get(i).index), mPaint);
				} else {
					canvas.drawBitmap(mtankD, null,
							mBaseList.get(mList.get(i).index), mPaint);
				}

				break;
			}
		}
	}

	@Override
	public void run() {
		tounchEvent(context);

	}

	private Bitmap mtankU, mtankD, mtankL, mtankR;

	private Bitmap atankU, atankD, atankL, atankR;

	private Bitmap uBulletR, uBulletL, uBulletD;
	private Bitmap usBulletLevBitR, usBulletLevBitL, usBulletLevBitD,
			usBulletLevBitU;
	private Bitmap mRTbit;

	 
	private Bitmap mBossBit,  mSBossbit, mBitmap, mBackBit,
			mUsBulletBit;
	private Paint   textPaint;

	private Bitmap bomBit;
	private ArrayList<RectF> bomList = new ArrayList<RectF>();

	private void initPaint() {
		mPaint = new Paint();
		 
		textPaint = new Paint();
		if (mPaint != null) {
			mPaint.reset();

		}

		mPaint.setAntiAlias(true);
		mPaint.setDither(true);

		mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
		mPaint.setStrokeJoin(Paint.Join.ROUND);
		mPaint.setStrokeCap(Paint.Cap.ROUND);
		mPaint.setStrokeWidth(5);
		mPaint.setShadowLayer(1, 3, 3, Color.argb(50, 0, 0, 0));
		mPaint.setTextSize(40);
		mPaint.setColor(Color.GRAY);

		textPaint.setColor(Color.GREEN);
		textPaint.setStyle(Paint.Style.FILL_AND_STROKE);
		textPaint.setTextSize(80);
		textPaint.setStrokeWidth(3);
		textPaint.setShadowLayer(5, 1, 1, Color.argb(100, 0, 0, 0));

		mBitmap = Bitmap.createBitmap((mWidth), (mHeight),
				Bitmap.Config.ARGB_8888);
		mtankD = BitmapFactory.decodeResource(getResources(),
				R.drawable.ldown);
		mtankU = BitmapFactory
				.decodeResource(getResources(), R.drawable.lup);
		mtankL = BitmapFactory.decodeResource(getResources(),
				R.drawable.lleft);
		mtankR = BitmapFactory.decodeResource(getResources(),
				R.drawable.lright);

		atankD = BitmapFactory.decodeResource(getResources(),
				R.drawable.rdown);
		atankU = BitmapFactory.decodeResource(getResources(),
				R.drawable.rup);
		atankR = BitmapFactory.decodeResource(getResources(),
				R.drawable.rright);
		atankL = BitmapFactory.decodeResource(getResources(),
				R.drawable.rleft);

		mBackBit = BitmapFactory.decodeResource(getResources(),
				R.drawable.remine);

		mUsBulletBit = BitmapFactory.decodeResource(getResources(),
				R.drawable.zdbg1);
		uBulletD = BitmapFactory.decodeResource(getResources(),
				R.drawable.zdbg1d);
		uBulletR = BitmapFactory.decodeResource(getResources(),
				R.drawable.zdbg1r);
		uBulletL = BitmapFactory.decodeResource(getResources(),
				R.drawable.zdbg1l);

		usBulletLevBitU = BitmapFactory.decodeResource(getResources(),
				R.drawable.zdbg1a);
		usBulletLevBitD = BitmapFactory.decodeResource(getResources(),
				R.drawable.zdbg1da);
		usBulletLevBitL = BitmapFactory.decodeResource(getResources(),
				R.drawable.zdbg1ra);
		usBulletLevBitR = BitmapFactory.decodeResource(getResources(),
				R.drawable.zdbg1la);

		 

		mSBossbit = BitmapFactory.decodeResource(getResources(),
				R.drawable.tankboss);
		 

		bomBit = BitmapFactory.decodeResource(getResources(), R.drawable.bomf);
		 

		mBlockHarBit = BitmapFactory.decodeResource(getResources(),
				R.drawable.blockh);
		mBlockNorBit = BitmapFactory.decodeResource(getResources(),
				R.drawable.qiang);
		mBlockWaterBit = BitmapFactory.decodeResource(getResources(),
				R.drawable.blockwater);
		mBlockTreeBit = BitmapFactory.decodeResource(getResources(),
				R.drawable.blokctree);
		mBirdBit = BitmapFactory.decodeResource(getResources(),
				R.drawable.bird_2);

		mRTbit = BitmapFactory
				.decodeResource(getResources(), R.drawable.remine);
	}

	private void initView(Context context) {
		// 初始化 矩形和参数
		DisplayMetrics ds = new DisplayMetrics();
		WindowManager wm = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		wm.getDefaultDisplay().getMetrics(ds);

		mHeight = (int) ((ds.heightPixels));
		mWidth = (int) (ds.widthPixels);//
		int r = mWidth / 20;
		int l = mWidth / 2;

		 

		mUsBullV = mHeight / 13;
		mRectWidth = mHeight / 13;
		mStartX = (mWidth - (mHeight)) / 2;
		initRect();
		setMissonBlock(0, MyConstant.GAME_MODE_SINGLE);
	}

	float mUsBullV;
	private RectF mRectLeft, mRectRight;
	private Bitmap mBlockNorBit, mBlockHarBit, mBlockWaterBit, mBlockTreeBit,
			mBirdBit;
	private float mStartX, mEndX;
	private float mRectWidth;
	private ArrayList<DataTank> mUsBulletList = new ArrayList<DataTank>();
	private ArrayList<DataTank> mAnimyBulletList = new ArrayList<DataTank>(); 

	private ArrayList<DataTank> mUsAList = new ArrayList<DataTank>(); 

	private ArrayList<DataTank> mAnimyList = new ArrayList<DataTank>(); 
	private ArrayList<DataTank> mBlockList = new ArrayList<DataTank>(); 
	private ArrayList<RectF> mBaseList = new ArrayList<RectF>(); 

	private ArrayList<DataTank> mBagList = new ArrayList<DataTank>(); 

	private void initRect() {
		// 初始化基础方块位置

		mBaseList.clear();
		for (int j = 0; j < 13; j++) {
			for (int i = 0; i < 13; i++) {
				RectF re = new RectF(mStartX + (i * mRectWidth),
						j * mRectWidth,
						mStartX + (i * mRectWidth + mRectWidth), j * mRectWidth
								+ mRectWidth);
				mBaseList.add(re);
			}
		}

		mRectLeft = new RectF(0, 0, mStartX, mHeight);
		mRectRight = new RectF(mStartX + (13 * mRectWidth), 0, mWidth, mHeight);

		mUsAList.clear();

		// 设置我方
		DataTank hol = new DataTank();
		hol.setCateName(MyConstant.NAME_TANK_US);
		hol.setAllLife(5);
		hol.setHurtLife(0);
		hol.setIndex(160);
		hol.setDirectState(MyConstant.DIRECT_UP);
		mUsAList.add(hol);

	}

	private int mMode = MyConstant.GAME_MODE_SINGLE;

	public void setmMode(int mMode) {
		this.mMode = mMode; 
	}

	public void setMisson(int misson) {
		this.Misson = misson;
	}

	private void setMissonBlock(int mission, int mode) {
		 
		mBlockList.clear();

		switch (mode) {
		case MyConstant.GAME_MODE_SINGLE:
			switch (mission) {
			case MyConstant.GAME_MISSION_ONE:

				break;
			case MyConstant.GAME_MISSION_TWO:

				break;
			case MyConstant.GAME_MISSION_THREEE:

				break;

			}
			break;

		}

		setBlock(mission);
	}

	Random mRandom = new Random();

	private void setBlock(int mission) {
	 
		mBlockList.clear();

		DataTank hol = new DataTank();
		hol.setCateName(MyConstant.NAME_BIRD);
		hol.setAllLife(3);
		hol.setHurtLife(0);
		hol.setIndex(162);
		hol.setDirectState(MyConstant.DIREC_LEFT);
		mBlockList.add(hol);

		DataTank hola = new DataTank();
		hola.setCateName(MyConstant.NAME_BLOCK_NORMAL);
		hola.setAllLife(2);
		hola.setHurtLife(0);
		hola.setIndex(161);
		hola.setDirectState(MyConstant.DIREC_LEFT);
		mBlockList.add(hola);

		DataTank holb = new DataTank();
		holb.setCateName(MyConstant.NAME_BLOCK_NORMAL);
		holb.setAllLife(2);
		holb.setHurtLife(0);
		holb.setIndex(163);
		holb.setDirectState(MyConstant.DIREC_LEFT);
		mBlockList.add(holb);

		DataTank holu = new DataTank();
		holu.setCateName(MyConstant.NAME_BLOCK_NORMAL);
		holu.setAllLife(2);
		holu.setHurtLife(0);
		holu.setIndex(162 - 13);
		holu.setDirectState(MyConstant.DIREC_LEFT);
		mBlockList.add(holu);

		DataTank holau = new DataTank();
		holau.setCateName(MyConstant.NAME_BLOCK_NORMAL);
		holau.setAllLife(2);
		holau.setHurtLife(0);
		holau.setIndex(161 - 13);
		holau.setDirectState(MyConstant.DIREC_LEFT);
		mBlockList.add(holau);

		DataTank holbu = new DataTank();
		holbu.setCateName(MyConstant.NAME_BLOCK_NORMAL);
		holbu.setAllLife(2);
		holbu.setHurtLife(0);
		holbu.setIndex(163 - 13);
		holbu.setDirectState(MyConstant.DIREC_LEFT);
		mBlockList.add(holbu);// 鸟的位置

		// 敌方坦克产生的位置 1 和 11
		for (int i = 0; i < 40 + (mission * 3); i++) {
			addNormBlock();
		}
		for (int i = 0; i < 10 + (mission * 2); i++) {
			addOtherBlock();
		}
		for (int i = 0; i < 5 + (mission * 2); i++) {
			addHardBlock();
		}
	}

	private void addNormBlock() {
		// 添加物件
		int ran = mRandom.nextInt(13 * 13);
		boolean has = false;
		if (ran != 1 && ran != 11 && ran != 160 && ran != 164) {
			for (int i = 0; i < mBlockList.size(); i++) {
				if (ran == mBlockList.get(i).getIndex()) {
					has = true;
				}
			}
			if (!has) {

				DataTank holania = new DataTank();
				holania.setCateName(MyConstant.NAME_BLOCK_NORMAL);
				holania.setAllLife(2);
				holania.setHurtLife(0);
				holania.setIndex(ran);
				holania.setDirectState(MyConstant.DIREC_LEFT);
				mBlockList.add(holania);
			}
		}
	}

	private void addHardBlock() {
		// 添加物件
		int ran = mRandom.nextInt(13 * 13);
		boolean has = false;
		if (ran != 1 && ran != 11 && ran != 160 && ran != 164) {

			for (int i = 0; i < mBlockList.size(); i++) {
				if (ran == mBlockList.get(i).getIndex()) {
					has = true;

				}
			}
			if (!has) {
				DataTank holania = new DataTank();
				holania.setCateName(MyConstant.NAME_BLOCK_HARD);
				holania.setAllLife(100000);
				holania.setHurtLife(0);
				holania.setIndex(ran);
				holania.setDirectState(MyConstant.DIREC_LEFT);
				mBlockList.add(holania);
			}
		}

	}

	private void addOtherBlock() {
		// 添加物件
		int ran = mRandom.nextInt(13 * 13);
		int oth = mRandom.nextInt(2);
		boolean has = false;
		if (ran != 1 && ran != 11 && ran != 160 && ran != 164) {
			for (int i = 0; i < mBlockList.size(); i++) {
				if (ran == mBlockList.get(i).getIndex()) {
					has = true;

				}
			}

			if (!has) {
				DataTank holania = new DataTank();
				if (oth == 0) {
					holania.setCateName(MyConstant.NAME_BLOCK_TREE);
				} else {
					holania.setCateName(MyConstant.NAME_BLOCK_WATER);
				}

				holania.setAllLife(10000);
				holania.setHurtLife(0);
				holania.setIndex(ran);
				holania.setDirectState(MyConstant.DIREC_LEFT);
				mBlockList.add(holania);
			}

		}
	}

	public void setDrec(int direction) {

		for (int i = 0; i < mUsAList.size(); i++) {
			DataTank hol = mUsAList.get(i);
			if (direction == hol.getDirectState()) {
				int ind = hol.getIndex();
				switch (direction) {
				case MyConstant.DIRECT_UP:

					ind = ind - 13;

					if (ind > 0 && canMove(ind)) {
						hol.setIndex(ind);
						mUsAList.set(i, hol);
					}

					break;
				case MyConstant.DIRECT_DOWN:
					ind = ind + 13;

					if (ind < 169 && canMove(ind)) {
						hol.setIndex(ind);
						mUsAList.set(i, hol);
					}

					break;
				case MyConstant.DIREC_LEFT:
					ind = ind - 1;
					if (ind / 13 == (ind + 1) / 13 && canMove(ind)) {
						hol.setIndex(ind);
						mUsAList.set(i, hol);
					}

					break;
				case MyConstant.DIRECT_RIGHT:
					ind = ind + 1;
					if (ind / 13 == (ind - 1) / 13 && canMove(ind)) {
						hol.setIndex(ind);
						mUsAList.set(i, hol);
					}
					break;

				}
			} else {
				hol.setDirectState(direction);
				mUsAList.set(i, hol);
			}

		}

		for (int i = 0; i < mBagList.size(); i++) {
			for (int j = 0; j < mUsAList.size(); j++) {
				if (mBagList.get(i).getIndex() == mUsAList.get(j).getIndex()) {
					mBagList.remove(i);
					isBagTime = true;

					break;
				}
			}
		}

	}

	private boolean canMove(int ind) {
		// 是否可以移动
		boolean canMove = true;

		for (int i = 0; i < mBlockList.size(); i++) {
			if (mBlockList.get(i).getCateName() == MyConstant.NAME_BLOCK_HARD
					|| mBlockList.get(i).getCateName() == MyConstant.NAME_BLOCK_NORMAL) {
				if (mBlockList.get(i).getIndex() == ind) {
					canMove = false;
				}

			}
		}
		return canMove;

	}

	public void usFires() {

		doFire(mUsAList);

	}

	private void doFire(ArrayList<DataTank> mList) {
		// 开火
		for (int i = 0; i < mList.size(); i++) {
			DataTank hol = new DataTank();
			hol.setIndex(mList.get(i).getIndex());
			hol.setDirectState(mList.get(i).getDirectState());
			RectF re = mBaseList.get(mList.get(i).getIndex());
			switch (mList.get(i).getDirectState()) {
			case MyConstant.DIREC_LEFT:
				RectF lRe = new RectF(re.left, re.top
						+ ((re.bottom - re.top) / 2) - (mRectWidth / 12),
						re.left + (mRectWidth / 3), re.top
								+ ((re.bottom - re.top) / 2)
								+ (mRectWidth / 12));
				hol.setRect(lRe);

				break;
			case MyConstant.DIRECT_RIGHT:
				RectF rRe = new RectF(re.left, re.top
						+ ((re.bottom - re.top) / 2) - (mRectWidth / 12),
						re.left + (mRectWidth / 3), re.top
								+ ((re.bottom - re.top) / 2)
								+ (mRectWidth / 12));
				hol.setRect(rRe);
				break;
			case MyConstant.DIRECT_UP:
				RectF uRe = new RectF(re.left + ((re.right - re.left) / 2)
						- (mRectWidth / 12), re.top, re.left
						+ ((re.right - re.left) / 2) + (mRectWidth / 12),
						re.top + (mRectWidth / 3));
				hol.setRect(uRe);
				break;
			case MyConstant.DIRECT_DOWN:
				RectF dRe = new RectF(re.left + ((re.right - re.left) / 2)
						- (mRectWidth / 12), re.bottom - (mRectWidth / 3),
						re.left + ((re.right - re.left) / 2)
								+ (mRectWidth / 12), re.bottom);
				hol.setRect(dRe);
				break;

			}
			hol.setIndex(hol.getIndex());
			hol.setDirectState(hol.getDirectState());
			hol.setCateName(MyConstant.NAME_NONE);
			mUsBulletList.add(hol);

		}

	}

	private void doAnimFire(DataTank hol) {
		// 敌人开火

		RectF re = mBaseList.get(hol.getIndex());
		switch (hol.getDirectState()) {
		case MyConstant.DIREC_LEFT:
			RectF lRe = new RectF(re.left, re.top + ((re.bottom - re.top) / 2)
					- (mRectWidth / 12), re.left + (mRectWidth / 3), re.top
					+ ((re.bottom - re.top) / 2) + (mRectWidth / 12));
			hol.setRect(lRe);

			break;
		case MyConstant.DIRECT_RIGHT:
			RectF rRe = new RectF(re.right, re.top + ((re.bottom - re.top) / 2)
					- (mRectWidth / 12), re.right + (mRectWidth / 3), re.top
					+ ((re.bottom - re.top) / 2) + (mRectWidth / 12));
			hol.setRect(rRe);
			break;
		case MyConstant.DIRECT_UP:
			RectF uRe = new RectF(re.left + ((re.right - re.left) / 2)
					- (mRectWidth / 12), re.top, re.left
					+ ((re.right - re.left) / 2) + (mRectWidth / 12), re.top
					+ (mRectWidth / 3));
			hol.setRect(uRe);
			break;
		case MyConstant.DIRECT_DOWN:
			RectF dRe = new RectF(re.left + ((re.right - re.left) / 2)
					- (mRectWidth / 12), re.bottom - (mRectWidth / 3), re.left
					+ ((re.right - re.left) / 2) + (mRectWidth / 12), re.bottom);
			hol.setRect(dRe);
			break;

		}

		mAnimyBulletList.add(hol);

	}

	private void setBulletPosi(ArrayList<DataTank> mBulletList, final int whis) {
		// 设置我方子弹的位置
		for (int i = 0; i < mBulletList.size(); i++) {
			DataTank hol = mBulletList.get(i);
			RectF re = hol.getRect();
			switch (mBulletList.get(i).getDirectState()) {
			case MyConstant.DIREC_LEFT:
				re.set(re.left - mUsBullV, re.top, re.right - mUsBullV,
						re.bottom);
				if (re.right <= 0) {
					mBulletList.remove(i);
				} else {
					hol.setRect(re);
					mBulletList.set(i, hol);
					if (whis == 0) {

						setUsBullResult(hol, i);
					} else {
						setAnimBullResult(hol, i);
					}

				}

				break;
			case MyConstant.DIRECT_RIGHT:
				re.set(re.left + mUsBullV, re.top, re.right + mUsBullV,
						re.bottom);
				if (re.left >= mStartX + (13 * mRectWidth)) {// 超出右边屏幕
					mBulletList.remove(i);
				} else {
					hol.setRect(re);
					mBulletList.set(i, hol);
					if (whis == 0) {
						setUsBullResult(hol, i);
					} else {
						setAnimBullResult(hol, i);
					}
				}
				break;
			case MyConstant.DIRECT_UP:
				re.set(re.left, re.top - mUsBullV, re.right, re.bottom
						- mUsBullV);
				if (re.bottom <= 0) {// 超出屏幕
					mBulletList.remove(i);
				} else {
					hol.setRect(re);
					mBulletList.set(i, hol);
					Log.e("打敌人+上", "" + whis);
					if (whis == 0) {
						Log.e("打敌人+上", i + "");
						setUsBullResult(hol, i);
					} else {
						setAnimBullResult(hol, i);
					}
				}
				break;
			case MyConstant.DIRECT_DOWN:
				re.set(re.left, re.top + mUsBullV, re.right, re.bottom
						+ mUsBullV);
				if (re.top >= mHeight) {// 超出屏幕
					mBulletList.remove(i);
				} else {
					hol.setRect(re);
					mBulletList.set(i, hol);
					if (whis == 0) {
						setUsBullResult(hol, i);
					} else {
						setAnimBullResult(hol, i);
					}
				}
				break;

			}

		}

	}

	private int mAttackCount = 0;// 击毁坦克数目

	private void setUsBullResult(DataTank hol, int in) {
		// 子弹击中效果
		boolean istouch = false;
		for (int i = 0; i < mAnimyList.size(); i++) {
			if (isTouch(hol.getRect(),
					mBaseList.get(mAnimyList.get(i).getIndex()))) {
				Log.e("击中敌人", i + "");
				istouch = true;
				mUsBulletList.remove(in);
				DataTank hom = mAnimyList.get(i);
				hom.setHurtLife(hom.getHurtLife() + 1);
				if (hom.getHurtLife() >= hom.getAllLife()) {

					RectF mre = mBaseList.get(mAnimyList.get(i).getIndex());
					float mx = (mre.left + mre.right) / 2;
					float my = (mre.top + mre.bottom) / 2;

					RectF r = new RectF(mx - (mRectWidth / 10), my
							- (mRectWidth / 10), mx + (mRectWidth / 10), my
							+ (mRectWidth / 10));
					bomList.add(r);

					mAnimyList.remove(i);

					Score = Score + 100;
					mAttackCount = mAttackCount + 1;
					if (isSound) {
						media.Play(true);
					}
				}

				break;
			}
		}
		// if (!istouch) {
		for (int j = 0; j < mBlockList.size(); j++) {
			// 击中 墙壁
			if (mBlockList.get(j).getCateName() != MyConstant.NAME_BLOCK_TREE
					&& mBlockList.get(j).getCateName() != MyConstant.NAME_BLOCK_WATER) {
				if (isTouch(hol.getRect(),
						mBaseList.get(mBlockList.get(j).getIndex()))) {
					istouch = true;
					mUsBulletList.remove(in);
					DataTank hom = mBlockList.get(j);
					hom.setHurtLife(hom.getHurtLife() + 1);
					if (hom.getHurtLife() >= hom.getAllLife()) {
						mBlockList.remove(j);

					}
					break;

				}
			}
			// }
		}

	}

	private void setAnimBullResult(DataTank hol, int in) {
		// 子弹击中效果
		boolean istouch = false;
		for (int i = 0; i < mUsAList.size(); i++) {
			if (isTouch(hol.getRect(),
					mBaseList.get(mUsAList.get(i).getIndex()))) {
				istouch = true;
				mAnimyBulletList.remove(in);
				DataTank hom = mUsAList.get(i);
				hom.setHurtLife(hom.getHurtLife() + 1);
				if (hom.getHurtLife() >= hom.getAllLife()) {
					mUsAList.remove(i);
				}

				break;
			}
		}

		if (!istouch) {
			for (int j = 0; j < mBlockList.size(); j++) {
				// 击中 墙壁
				if (mBlockList.get(j).getCateName() != MyConstant.NAME_BLOCK_TREE
						&& mBlockList.get(j).getCateName() != MyConstant.NAME_BLOCK_WATER) {

					if (isTouch(hol.getRect(),
							mBaseList.get(mBlockList.get(j).getIndex()))) {
						istouch = true;
						mAnimyBulletList.remove(in);
						DataTank hom = mBlockList.get(j);
						hom.setHurtLife(hom.getHurtLife() + 1);
						if (hom.getHurtLife() >= hom.getAllLife()) {
							mBlockList.remove(j);

						}
						break;

					}
				}
			}
		}

	}

	private boolean isTouch(RectF A, RectF B) {
		// 矩形是否有重叠
		float x = A.left + ((A.right - A.left) / 2);
		float y = A.top + ((A.bottom - A.top) / 2);

		if (A.left >= B.left && A.left < B.right && A.top > B.top
				&& A.top < B.bottom) {
			return true;
		} else if (A.right >= B.left && A.right < B.right && A.top > B.top
				&& A.top < B.bottom) {
			return true;
		} else if (A.left >= B.left && A.right <= B.right && A.top > B.top
				&& A.top < B.bottom) {
			return true;
		} else if (A.top < B.bottom && A.top > B.top && A.left > B.left
				&& A.left < B.right) {
			return true;
		} else if (A.bottom < B.bottom && A.bottom > B.top && A.left > B.left
				&& A.left < B.right) {
			return true;
		} else {
			return false;
		}
	}

	int mAnimyCount = 0;// 产生的坦克数量

	private void addAnimy() {
		// 产生敌方坦克
		if (mAnimyCount < MyConstant.TANK_COUNT) {

			int ran = mRandom.nextInt(2);
			DataTank hola = new DataTank();
			hola.setCateName(MyConstant.NAME_TANK_ANIM);
			hola.setAllLife(2);
			hola.setHurtLife(0);

			if (ran == 0) {
				hola.setIndex(1);
			} else {
				hola.setIndex(11);
			}
			mAnimyList.add(hola);
			mAnimyCount = mAnimyCount + 1;
		}
	}

	private void addStar() {
		// 奖励
		boolean has = false;
		int in = mRandom.nextInt(13 * 13);
		for (int i = 0; i < mBlockList.size(); i++) {
			if (mBlockList.get(i).getIndex() == in) {
				has = true;
			}
		}
		if (!has) {
			DataTank hol = new DataTank();
			hol.setIndex(in);
			mBagList.add(hol);
			if (mBagList.size() > 2) {
				mBagList.remove(0);
			}
		}

	}

	private void bomb() {
	 
		try {
			for (int i = 0; i < bomList.size(); i++) {
				float mx = (bomList.get(i).left + bomList.get(i).right) / 2;
				float my = (bomList.get(i).top + bomList.get(i).bottom) / 2;
				float len = (bomList.get(i).right - bomList.get(i).left) / 2.0f;

				RectF r = new RectF(bomList.get(i).left - len,
						bomList.get(i).top - len, bomList.get(i).right + len,
						bomList.get(i).bottom + len);
				bomList.set(i, r);

				if (len > mRectWidth) {
					bomList.remove(i);
				}

			}
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	long aiGoTime = 0;

	private void AIGo() {
		// 敌人智能行动

		for (int i = 0; i < mAnimyList.size(); i++) {
			DataTank hol = mAnimyList.get(i);
			boolean hasDown = false;
			if (hol.getIndex() / 13 == 12) {
				// 最后 一行
				if (hol.getIndex() % 13 > 6) {
					boolean is = isHasBlock(hol.getIndex() - 1);
					if (!is) {
						hol.setIndex(hol.getIndex() - 1);

					}
					hol.setDirectState(MyConstant.DIREC_LEFT);
					mAnimyList.set(i, hol);
					/*
					 * 此处开火
					 */
					doAnimFire(hol);
				} else {
					boolean is = isHasBlock(hol.getIndex() + 1);
					if (!is) {
						hol.setIndex(hol.getIndex() + 1);

					}
					hol.setDirectState(MyConstant.DIRECT_RIGHT);
					mAnimyList.set(i, hol);
					/*
					 * 此处
					 */
					doAnimFire(hol);
				}
			} else {
				// 前边的行数
				for (int j = 0; j < mBlockList.size(); j++) {

					if (mBlockList.get(j).getIndex() == hol.getIndex() + 13) {
						hasDown = true;// 下方有 墙体
					}
				}

				if (!isHasBlock(hol.getIndex() + 13)) {
					// 下方没墙体
					Log.e("向下移动", hol.getIndex() + "");
					hol.setIndex(hol.getIndex() + 13);
					hol.setDirectState(MyConstant.DIRECT_DOWN);
					mAnimyList.set(i, hol);

				} else {

					if (hol.getIndex() % 13 > 6) {
						// 坦克在右部分

						// if (hol.getIndex()-1/13==hol.getIndex()/13) {
						boolean is = isHasBlock(hol.getIndex() - 1);
						Log.e("左右移动", hol.getIndex() % 13 + "列可以移动" + is);
						if (!is) {
							Log.e("向左移动", hol.getIndex() + "");
							hol.setIndex(hol.getIndex() - 1);
							hol.setDirectState(MyConstant.DIREC_LEFT);
							mAnimyList.set(i, hol);
						} else if (hol.getIndex() + 1 / 13 == hol.getIndex() / 13
								&& !isHasBlock(hol.getIndex() + 1)) {
							// 左边不能移动
							Log.e("向左不能移动", hol.getIndex() + "");
							hol.setIndex(hol.getIndex() + 1);
							hol.setDirectState(MyConstant.DIRECT_RIGHT);
							mAnimyList.set(i, hol);
						} else {
							// 开火
							Log.e("不能移动开火", hol.getIndex() + "");

							doAnimFire(hol);
						}
						// }

					} else if (hol.getIndex() % 13 < 6) {
						// 坦克在左部分

						// if (hol.getIndex()-1/13==hol.getIndex()/13) {

						boolean is = isHasBlock(hol.getIndex() + 1);
						if (!is) {
							Log.e("向右移动", hol.getIndex() + "");
							hol.setIndex(hol.getIndex() + 1);
							hol.setDirectState(MyConstant.DIRECT_RIGHT);
							mAnimyList.set(i, hol);
						} else if (hol.getIndex() - 1 / 13 == hol.getIndex() / 13
								&& !isHasBlock(hol.getIndex() - 1)) {
							// 左边不能移动
							Log.e("向右不能移动", hol.getIndex() + "");

							hol.setIndex(hol.getIndex() - 1);
							hol.setDirectState(MyConstant.DIREC_LEFT);
							mAnimyList.set(i, hol);

						} else {
							// 开火
							Log.e("不能移动开火", hol.getIndex() + "");
							doAnimFire(hol);

						}
					} else {
						hol.setDirectState(MyConstant.DIRECT_DOWN);
						mAnimyList.set(i, hol);
						doAnimFire(hol);
					}
					// }
				}

			}
		}

	}

	private boolean isHasBlock(int ind) {
		// 是否有墙体
		boolean has = false;
		for (int j = 0; j < mBlockList.size(); j++) {
			if (mBlockList.get(j).getIndex() == ind) {
				if (mBlockList.get(j).getCateName() == MyConstant.NAME_BLOCK_TREE
						|| mBlockList.get(j).getCateName() == MyConstant.NAME_BLOCK_WATER) {
				} else {
					has = true;// 有墙体
				}

			}
		}
		return has;
	}

	private void AIFire() {
		// 智能开火

		for (int i = 0; i < mAnimyList.size(); i++) {
			DataTank hol = mAnimyList.get(i);
			for (int j = 0; j < mUsAList.size(); j++) {
				int h = mAnimyList.get(i).getIndex() / 13;
				int ha = mUsAList.get(j).getIndex() / 13;

				int l = mAnimyList.get(i).getIndex() % 13;
				int la = mUsAList.get(j).getIndex() % 13;
				boolean go = false;
				for (int k = 0; k < mBlockList.size(); k++) {
					if (mBlockList.get(k).getIndex() == mUsAList.get(j)
							.getIndex()) {
						if (mBlockList.get(k).getCateName() == MyConstant.NAME_BLOCK_TREE
								|| mBlockList.get(k).getCateName() == MyConstant.NAME_BLOCK_WATER) {
							go = true;
						}
					}
				}

				if (h == ha && l > la && !go) {

					hol.setDirectState(MyConstant.DIREC_LEFT);

					mAnimyList.set(i, hol);
					doAnimFire(hol);
				} else if (h == ha && l < la && !go) {
					hol.setDirectState(MyConstant.DIRECT_RIGHT);
					mAnimyList.set(i, hol);
					doAnimFire(hol);
				} else if (h > ha && l == la && !go) {
					hol.setDirectState(MyConstant.DIRECT_UP);
					mAnimyList.set(i, hol);
					doAnimFire(hol);
				} else if (h < ha && l == la && !go) {
					hol.setDirectState(MyConstant.DIRECT_DOWN);
					mAnimyList.set(i, hol);
					doAnimFire(hol);
				}

			}

		}

	}

	private void isFailed() {
		// 是否成功
		boolean has = false;
		for (int i = 0; i < mBlockList.size(); i++) {
			if (mBlockList.get(i).getCateName() == MyConstant.NAME_BIRD) {
				has = true;
			}
		}
		if (!has) {
			isFailed = true;
		}

		if (mMode == MyConstant.GAME_MODE_SINGLE) {
			if (mUsAList.size() <= 0) {
				isFailed = true;
			}
		}
	}

	private void isSucce() {
		// 是否成功
		if (mAttackCount >= MyConstant.TANK_COUNT) {
			isSuccess = true;
		}

	}

	long mADDTime = 0;
	long mBagTime = 0;
	boolean isBagTime = false;

	long bagTime = 0;

	private void backThread() {
		// 后台线程

		new Thread(new Runnable() {

			@Override
			public void run() {
				while (isRun) {
					draw();

					if (!isStop) {

					 
						 
						setBulletPosi(mUsBulletList, 0);

						setBulletPosi(mAnimyBulletList, 1);

						bomb();
					 

						mBagTime = mBagTime + MyConstant.THREAD_SLEEP;
						if (mBagTime > 5000) {
							mBagTime = 0;
							 
							addStar();
							 

						}

						if (isBagTime) {
							bagTime = bagTime + MyConstant.THREAD_SLEEP;
							if (bagTime > 3000) {
								isBagTime = false;
								bagTime = 0;
							}

						}

						mADDTime = mADDTime + MyConstant.THREAD_SLEEP;
						if (mADDTime >= MyConstant.ADD_TANK_TIME) {
							mADDTime = 0;
							addAnimy();
						}

						aiGoTime = aiGoTime + MyConstant.THREAD_SLEEP;
						;
						if (aiGoTime > 400) {
							aiGoTime = 0;
							AIGo();
							try {
								AIFire();
							} catch (Exception e) {

								e.printStackTrace();
							}

						}

						isFailed();
						isSucce();
					}

					try {
						Thread.sleep(MyConstant.THREAD_SLEEP);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			}
		}).start();
	}

 

	boolean isStop = true;

	public void setStop(boolean isStop) {
		this.isStop = isStop;
	}

	public void gameStart(boolean isPauses) {
		// 游戏开始

		resetEvent();

		isFailed = false;

	}

	Calendar c;

	public String getSysTimeRemind() {// 获得时间的格式
		c = Calendar.getInstance();
		String name = "";
		name = c.get(Calendar.YEAR) + "年" + (c.get(Calendar.MONTH) + 1) + "月"
				+ c.get(Calendar.DAY_OF_MONTH) + "日"
				+ c.get(Calendar.HOUR_OF_DAY) + "时" + c.get(Calendar.MINUTE)
				+ "分";

		return name;
	}
}
