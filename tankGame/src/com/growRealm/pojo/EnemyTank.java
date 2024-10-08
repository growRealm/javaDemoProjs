package com.growRealm.pojo;

import com.growRealm.service.Shot;

import java.util.Vector;

@SuppressWarnings("All")
public class EnemyTank extends Tank implements Runnable {
    //在敌人坦克类，保存多个shot
    private Vector<Shot> shots = new Vector<>();
    //敌人坦克 可以得到敌人坦克的Vector
    //1.EnemyTank 在mypanel里面
    private Vector<EnemyTank> enemyTanks = new Vector<>();
    
    public EnemyTank(int x, int y) {
        super(x, y);
    }
    
    public Vector<Shot> getShots() {
        return shots;
    }
    
    public void setShots(Vector<Shot> shots) {
        this.shots = shots;
    }
    
    public Vector<EnemyTank> getEnemyTanks() {
        return enemyTanks;
    }
    
    /**
     * 提供一个方法，可以将mypanel 的成员 Vector<EnemyTank> enemyTanks = new Vector<>();
     * 设置到  private Vector<EnemyTank> enemyTanks = new Vector<>();
     * @param enemyTanks 敌人坦克集合
     */
    public void setEnemyTanks(Vector<EnemyTank> enemyTanks) {
        this.enemyTanks = enemyTanks;
    }
    
    //编写方法，判断当前的敌人坦克，是否和 enemyTanks 中2的 其他坦克发生了重叠 或者是碰撞
    public boolean isTouchEnemyTank(){
        //判断当前敌人坦克(this) 方向
        switch (this.getDirection()){
            case 0: //上
                //让当前的this 敌人坦克 和 其他所有的敌人坦克比较
                for (int i = 0;i< enemyTanks.size();i++){
                    //从vector中取出一辆敌人的坦克
                    EnemyTank enemyTank = enemyTanks.get(i);
                    //不和自己比较
                    if (this != enemyTank){
                        /*
                        1.如果敌人坦克是上/下方向   x的范围是什么【enemyTank.getX() ,enemyTank.getX() + 40】
                                                y的范围是什么【enemyTank.getY() ,enemyTank.getY() + 60】
                         */
                        if (enemyTank.getDirection() == 0 || enemyTank.getDirection() == 2){
                            //2.当前坦克的左上角坐标【this.getX(),this.getY()】
                            if (this.getX() >= enemyTank.getX() && this.getX() <= enemyTank.getX() + 40 &&
                                        this.getY() >= enemyTank.getY() && this.getY() <= enemyTank.getY() + 60){
                                return true;
                            }
                            //3.当前坦克的右上角坐标【this.getX() + 40,this.getY()】
                            if (this.getX() + 40 >= enemyTank.getX() && this.getX()  + 40 <= enemyTank.getX() + 60 &&
                                        this.getY() >= enemyTank.getY() && this.getY() <= enemyTank.getY() + 40){
                                return true;
                            }
                        }
                        /*
                        1.如果敌人坦克是左/右方向   x的范围是什么【enemyTank.getX() ,enemyTank.getX() + 60】
                                                y的范围是什么【enemyTank.getY() ,enemyTank.getY() + 40】
                         */
                        //如果敌人坦克是左/右方向
                        if (enemyTank.getDirection() == 1 || enemyTank.getDirection() == 3){
                            //2.当前坦克的左上角坐标【this.getX(),this.getY()】
                            if (this.getX() >= enemyTank.getX() && this.getX() <= enemyTank.getX() + 60 &&
                                        this.getY() >= enemyTank.getY() && this.getY() <= enemyTank.getY() + 40){
                                return true;
                            }
                            //3.当前坦克的右上角坐标【this.getX() + 40,this.getY()】
                            if (this.getX() + 40 >= enemyTank.getX() && this.getX()  + 40 <= enemyTank.getX() + 60 &&
                                        this.getY() >= enemyTank.getY() && this.getY() <= enemyTank.getY() + 40){
                                return true;
                            }
                        }
                    }
                }
                break;
            case 1: //右
                //让当前的this 敌人坦克 和 其他所有的敌人坦克比较
                for (int i = 0;i< enemyTanks.size();i++){
                    //从vector中取出一辆敌人的坦克
                    EnemyTank enemyTank = enemyTanks.get(i);
                    //不和自己比较
                    if (this != enemyTank){
                        /*
                        1.如果敌人坦克是上/下方向   x的范围是什么【enemyTank.getX() ,enemyTank.getX() + 40】
                                                y的范围是什么【enemyTank.getY(),enemyTank.getY()  + 40】
                         */
                        if (enemyTank.getDirection() == 0 || enemyTank.getDirection() == 2){
                            //2.当前坦克的右上角坐标【this.getX() + 60,this.getY()】
                            if (this.getX() + 60 >= enemyTank.getX() && this.getX() + 60 <= enemyTank.getX() + 40 &&
                                        this.getY() >= enemyTank.getY() && this.getY() <= enemyTank.getY() + 60){
                                return true;
                            }
                            //3.当前坦克的右下角坐标【this.getX() + 60,this.getY() + 40】
                            if (this.getX() + 60 >= enemyTank.getX() && this.getX()  + 60 <= enemyTank.getX() + 40 &&
                                        this.getY() + 40 >= enemyTank.getY() && this.getY()  + 40 <= enemyTank.getY() + 60){
                                return true;
                            }
                        }
                        /*
                        1.如果敌人坦克是左/右方向   x的范围是什么【enemyTank.getX(),enemyTank.getX() + 60】
                                                y的范围是什么【enemyTank.getY(),enemyTank.getX() + 40】
                         */
                        //如果敌人坦克是左/右方向
                        if (enemyTank.getDirection() == 1 || enemyTank.getDirection() == 3){
                            //2.当前坦克的右上角坐标【this.getX() + 60,this.getY()】
                            if (this.getX() + 60 >= enemyTank.getX() && this.getX() + 60 <= enemyTank.getX() + 60 &&
                                        this.getY() >= enemyTank.getY() && this.getY() <= enemyTank.getY() + 40){
                                return true;
                            }
                            //3.当前坦克的右下角坐标【this.getX() + 60,this.getY()  + 40】
                            if (this.getX() + 60 >= enemyTank.getX() && this.getX() + 60 <= enemyTank.getX() + 60 &&
                                        this.getY() + 40 >= enemyTank.getY() && this.getY() + 40 <= enemyTank.getY() + 40){
                                return true;
                            }
                        }
                    }
                }
                break;
            case 2: //下
                //让当前的this 敌人坦克 和 其他所有的敌人坦克比较
                for (int i = 0;i< enemyTanks.size();i++){
                    //从vector中取出一辆敌人的坦克
                    EnemyTank enemyTank = enemyTanks.get(i);
                    //不和自己比较
                    if (this != enemyTank){
                        /*
                        1.如果敌人坦克是上/下方向   x的范围是什么【enemyTank.getX() ,enemyTank.getX() + 40】
                                                y的范围是什么【enemyTank.getY(),enemyTank.getY()  + 60】
                         */
                        if (enemyTank.getDirection() == 0 || enemyTank.getDirection() == 2){
                            //2.当前坦克的左下角坐标【this.getX(),this.getY() + 60】
                            if (this.getX() >= enemyTank.getX() && this.getX() <= enemyTank.getX() + 40 &&
                                        this.getY() + 60 >= enemyTank.getY() && this.getY() + 60 <= enemyTank.getY() + 60){
                                return true;
                            }
                            //3.当前坦克的右下角坐标【this.getX() + 40,this.getY() + 60】
                            if (this.getX() + 40 >= enemyTank.getX() && this.getX()  + 40 <= enemyTank.getX() + 40 &&
                                        this.getY() + 60 >= enemyTank.getY() && this.getY()  + 60 <= enemyTank.getY() + 60){
                                return true;
                            }
                        }
                        /*
                        1.如果敌人坦克是左/右方向   x的范围是什么【enemyTank.getX(),enemyTank.getX() + 60】
                                                y的范围是什么【enemyTank.getY(),enemyTank.getX() + 40】
                         */
                        //如果敌人坦克是左/右方向
                        if (enemyTank.getDirection() == 1 || enemyTank.getDirection() == 3){
                            //2.当前坦克的左下角坐标【this.getX(),this.getY() + 60】
                            if (this.getX() >= enemyTank.getX() && this.getX() <= enemyTank.getX() + 60 &&
                                        this.getY() + 60 >= enemyTank.getY() && this.getY() + 60 <= enemyTank.getY() + 40){
                                return true;
                            }
                            //3.当前坦克的右下角坐标【this.getX() + 40,this.getY() + 60】
                            if (this.getX() + 40 >= enemyTank.getX() && this.getX()  + 40 <= enemyTank.getX() + 60 &&
                                        this.getY() + 60 >= enemyTank.getY() && this.getY()  + 60 <= enemyTank.getY() + 40){
                                return true;
                            }
                        }
                    }
                }
                break;
            case 3: //左
                //让当前的this 敌人坦克 和 其他所有的敌人坦克比较
                for (int i = 0;i< enemyTanks.size();i++){
                    //从vector中取出一辆敌人的坦克
                    EnemyTank enemyTank = enemyTanks.get(i);
                    //不和自己比较
                    if (this != enemyTank){
                        /*
                        1.如果敌人坦克是上/下方向   x的范围是什么【enemyTank.getX() ,enemyTank.getX() + 40】
                                                y的范围是什么【enemyTank.getY(),enemyTank.getY()  + 60】
                         */
                        if (enemyTank.getDirection() == 0 || enemyTank.getDirection() == 2){
                            //2.当前坦克的左上角坐标【this.getX(),this.getY()】
                            if (this.getX() >= enemyTank.getX() && this.getX() <= enemyTank.getX() + 40 &&
                                        this.getY() >= enemyTank.getY() && this.getY() <= enemyTank.getY() + 60){
                                return true;
                            }
                            //3.当前坦克的左下角坐标【this.getX(),this.getY() + 40】
                            if (this.getX() >= enemyTank.getX() && this.getX() <= enemyTank.getX() + 40 &&
                                        this.getY() + 40 >= enemyTank.getY() && this.getY()  + 40 <= enemyTank.getY() + 60){
                                return true;
                            }
                        }
                        /*
                        1.如果敌人坦克是左/右方向   x的范围是什么【enemyTank.getX(),enemyTank.getX() + 60】
                                                y的范围是什么【enemyTank.getY(),enemyTank.getX() + 40】
                         */
                        //如果敌人坦克是左/右方向
                        if (enemyTank.getDirection() == 1 || enemyTank.getDirection() == 3){
                            //2.当前坦克的左上角坐标【this.getX(),this.getY()】
                            if (this.getX() >= enemyTank.getX() && this.getX() <= enemyTank.getX() + 60 &&
                                        this.getY() >= enemyTank.getY() && this.getY() <= enemyTank.getY() + 40){
                                return true;
                            }
                            //3.当前坦克的左下角坐标【this.getX(),this.getY() + 40】
                            if (this.getX() >= enemyTank.getX() && this.getX() <= enemyTank.getX() + 60 &&
                                        this.getY() + 40 >= enemyTank.getY() && this.getY()  + 40 <= enemyTank.getY() + 40){
                                return true;
                            }
                        }
                    }
                }
                break;
        }
        return false;
    }
    
    @Override
    public void run() {
        while (true) {
            //这里我们判断如果shots size() == 0，创建一颗子弹，放入到shots集合，并启动
            if (isLive() && shots.size() < 3) {
                Shot s = null;
                //判断坦克的方向，创建对应的子弹
                switch (getDirection()) {
                    case 0:
                        s = new Shot(getX() + 20, getY(), 0);
                        break;
                    case 1:
                        s = new Shot(getX() + 60, getY() + 20, 1);
                        break;
                    case 2:
                        s = new Shot(getX() + 20, getY() + 60, 2);
                        break;
                    case 3:
                        s = new Shot(getX(), getY() + 20, 3);
                        break;
                }
                //添加到容器
                shots.add(s);
                //启动线程
                new Thread(s).start();
            }
            //当前坦克的方向来继续移动
            for (int i = 0; i < 30; i++) {
                switch (getDirection()) {
                    case 0://向上
                        if (getY() > 0 && !isTouchEnemyTank()){
                            moveUp();
                        }
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        break;
                    case 1://向右
                        if (getX() + 60 < 1000  && !isTouchEnemyTank()){
                            moveRight();
                        }
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        break;
                    case 2://向下
                        if (getY() + 60 < 722  && !isTouchEnemyTank()){
                            moveDown();
                        }
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        break;
                    case 3://向左
                        if (getX() > 0  && !isTouchEnemyTank()){
                            moveLeft();
                        }
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        break;
                }
            }
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            
            //然后随机的改变方向
            int direction = (int) (Math.random() * 4);
            setDirection(direction);
            
            //一旦写并发程序，一定要考虑清楚该线程什么时候结束
            //被子弹打中了，结束线程
            if (!isLive()) {
                break;
            }
        }
    }
}

