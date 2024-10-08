package com.growRealm.pojo;

/**
 * 一个 EnemyTankInfo 对象，表示一个敌人坦克的信息
 */
public class EnemyTankInfo {
    private int x;
    private int y;
    private int direction;
    
    public EnemyTankInfo(int x, int y, int direction) {
        this.x = x;
        this.y = y;
        this.direction = direction;
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
}

