package com.growRealm.pojo;

/**
 * 坦克实体
 */
public class Tank {
    private int x;
    private int y;
    private int direction;//坦克方向0-上 1-右 2-下 3-左
    private int speed = 1;//坦克速度
    private boolean isLive = true;
    
    public Tank(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    public int getX() {
        return x;
    }
    
    public void setX(int x) {
        this.x = x;
    }
    
    public int getY() {
        return y;
    }
    
    public void setY(int y) {
        this.y = y;
    }
    
    public int getDirection() {
        return direction;
    }
    
    public void setDirection(int direction) {
        this.direction = direction;
    }
    
    //上下左右移动方法
    public void moveUp(){
        //坦克不能出边界
        if (y > 0){
            y-=speed;
        }
    }
    public void moveRight(){
        if (x + 60 < 1000){
            x+=speed;
        }
    }
    public void moveDown(){
        if (y + 60 < 722){
            y+=speed;
        }
    }
    public void moveLeft(){
        if (x > 0){
            x-=speed;
        }
    }
    
    public boolean isLive() {
        return isLive;
    }
    
    public void setLive(boolean live) {
        isLive = live;
    }
    
    public int getSpeed() {
        return speed;
    }
    
    public void setSpeed(int speed) {
        this.speed = speed;
    }
}

