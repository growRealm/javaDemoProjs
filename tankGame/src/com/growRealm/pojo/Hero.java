package com.growRealm.pojo;

import com.growRealm.service.Shot;

import java.util.Vector;

/**
 * 自己的坦克
 */
public class Hero extends Tank{
    //定义一个shot对象,表示射击行为
    private Shot shot = null;
    //可以发射多颗子弹
    private Vector<Shot> shots = new Vector<>();
    
    public Hero(int x, int y) {
        super(x, y);
    }
    
    //射击
    public void shotEnemyTank(){
        
        //控制面板上子弹数量
        if (shots.size() >= 5){
            return;
        }
        
        //创建short对象，根据当前hero对象的位置方向来创建shot对象
        switch (getDirection()){
            case 0:
                shot = new Shot(getX() + 20,getY(),0);
                break;
            case 1:
                shot = new Shot(getX() + 60,getY() +20,1);
                break;
            case 2:
                shot = new Shot(getX() + 20,getY() + 60,2);
                break;
            case 3:
                shot = new Shot(getX(),getY() + 20,3);
                break;
        }
        //把进创建的short放到集合中
        shots.add(shot);
        //启动shot线程
        new Thread(shot).start();
    }
    
    public Vector<Shot> getShots() {
        return shots;
    }
    
    public void setShots(Vector<Shot> shots) {
        this.shots = shots;
    }
    
    public Shot getShot() {
        return shot;
    }
    
    public void setShot(Shot shot) {
        this.shot = shot;
    }
}

