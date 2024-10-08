package com.growRealm.service;

public class Shot implements Runnable{
    private int x;//子弹x坐标
    private int y;//子弹x坐标
    private int direction = 0;//子弹方向
    private int speed = 2;//子弹速度
    private boolean isLive = true; //子弹是否还存活
    
    @Override
    public void run() {//射击行为
        while (true){
            //线程休眠 50 毫秒
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //根据方向来改变x ，y坐标
            switch (direction){
                case 0: //向上
                    y -= speed;
                    break;
                case 1: //向右
                    x += speed;
                    break;
                case 2://向下
                    y += speed;
                    break;
                case 3://向左
                    x -= speed;
                    break;
            }
            System.out.println("x==>"+x+"   y===>"+y);
            //当子弹碰到敌人坦克时，也应该结束线程
            //当子弹移动到面板的边界时，销毁子弹
            if (!(x >= 0 && x <= 1000 && y >= 0 && y <= 750 && isLive)){
                //设置子弹是否存活状态
                isLive = false;
                System.out.println("子弹线程退出");
                break;
            }
            
        }
    }
    
    //构造子弹
    public Shot(int x, int y, int direction) {
        this.x = x;
        this.y = y;
        this.direction = direction;
    }
    
    public boolean isLive() {
        return isLive;
    }
    public void setLive(boolean live) {
        isLive = live;
    }
    public int getDirection() {
        return direction;
    }
    public void setDirection(int direction) {
        this.direction = direction;
    }
    public int getSpeed() {
        return speed;
    }
    public void setSpeed(int speed) {
        this.speed = speed;
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
    
    @Override
    public String toString() {
        return "Shot{" +
                       "x=" + x +
                       ", y=" + y +
                       ", direction=" + direction +
                       ", speed=" + speed +
                       ", isLive=" + isLive +
                       '}';
    }
}

