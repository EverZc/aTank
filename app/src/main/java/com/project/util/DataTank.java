package com.project.util;

import android.graphics.RectF;

public class DataTank {
 int  cateName;// 物块名称
 int allLife;// 总生命
 int hurtLife;//生命 
 int index;// 位置
 RectF rect;// 矩形块
int directState;//方向
int animIndex;//动画序列 
long bulletTime;//产生子弹的 时间   
int player;//玩家


public long getBulletTime() {
	return bulletTime;
}

public void setBulletTime(long bulletTime) {
	this.bulletTime = bulletTime;
}

public int getPlayer() {
	return player;
}

public void setPlayer(int player) {
	this.player = player;
}

public int getCateName() {
	return cateName;
}

public void setCateName(int cateName) {
	this.cateName = cateName;
}

public int getAllLife() {
	return allLife;
}

public void setAllLife(int allLife) {
	this.allLife = allLife;
}

public int getHurtLife() {
	return hurtLife;
}

public void setHurtLife(int hurtLife) {
	this.hurtLife = hurtLife;
}

public int getIndex() {
	return index;
}

public void setIndex(int index) {
	this.index = index;
}

public RectF getRect() {
	return rect;
}

public void setRect(RectF rect) {
	this.rect = rect;
}

public int getDirectState() {
	return directState;
}

public void setDirectState(int directState) {
	this.directState = directState;
}

public int getAnimIndex() {
	return animIndex;
}

public void setAnimIndex(int animIndex) {
	this.animIndex = animIndex;
}
 
 
 
}
