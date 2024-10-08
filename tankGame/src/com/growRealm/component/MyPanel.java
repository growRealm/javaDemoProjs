package com.growRealm.component;

import com.growRealm.pojo.*;
import com.growRealm.service.PlayAudio;
import com.growRealm.service.Recorder;
import com.growRealm.service.Shot;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.util.Vector;

/**
 * 坦克大战绘图区域
 * 为了让panel 不停的重绘子弹，需要将MyPanel 重绘方法中的内容放入到单独的线程中
 */
public class MyPanel extends JPanel implements KeyListener, Runnable {
    //定义我的坦克
    Hero hero = null;
    //定义敌人坦克
    Vector<EnemyTank> enemyTanks = new Vector<>();
    //用于存放炸弹
    //当子弹击中坦克时，就加入一个bomb 对象
    Vector<Bomb> bombs = new Vector<>();
    //定义三种图片，用于显示炸弹
    Image img1 = null;
    Image img2 = null;
    Image img3 = null;
    
    //定义一个存放Node 对象的Vector，用于恢复敌人坦克的坐标和放向
    Vector<Node> nodes = null;
    //敌人坦克数量
    int enemyTankSize = 3;
    public MyPanel(String type) {
        //判断记录文件是否存在
        //如果存在就正常执行，如果文件不存在，提示只能开启新的游戏
        File file = new File(Recorder.getRecordFile());
        if (!file.exists()){
            type = "1";
            System.out.println("文件不存在只能开启新游戏");
        }else {
            nodes = Recorder.getNodesAndTanks();
        }
        
        //初始化我的坦克
        hero = new Hero(150, 150);
        //设置坦克速度
        hero.setSpeed(5);
        switch (type) {
            case "1":
                //初始化敌人坦克
                for (int i = 0; i < enemyTankSize; i++) {
                    EnemyTank enemyTank = new EnemyTank(100 * (i + 1), 0);
                    enemyTank.setDirection(2);
                    //启动敌人坦克线程
                    new Thread(enemyTank).start();
                    //给该enemyTank 加入一颗子弹
                    Shot shot = new Shot(enemyTank.getX() + 20, enemyTank.getY() + 60, enemyTank.getDirection());
                    //加入到
                    enemyTank.getShots().add(shot);
                    //为每个坦克，放入所有的敌人对象
                    enemyTank.setEnemyTanks(enemyTanks);
                    //启动shot
                    new Thread(shot).start();
                    enemyTanks.add(enemyTank);
                }
                break;
            case "2": //继续上局游戏
                //初始化敌人坦克
                for (int i = 0;i < nodes.size();i++){
                    Node node = nodes.get(i);
                    EnemyTank enemyTank = new EnemyTank(node.getX(),node.getY());
                    enemyTank.setDirection(node.getDirection());
                    //启动敌人坦克线程
                    new Thread(enemyTank).start();
                    //给该enemyTank 加入一颗子弹
                    Shot shot = new Shot(enemyTank.getX() + 20, enemyTank.getY() + 60, enemyTank.getDirection());
                    //加入到
                    enemyTank.getShots().add(shot);
                    //为每个坦克，放入所有的敌人对象
                    enemyTank.setEnemyTanks(enemyTanks);
                    //启动shot
                    new Thread(shot).start();
                    enemyTanks.add(enemyTank);
                }
                break;
            default:
                System.out.println("你的输入有误");
        }
        //获取项目路径
        //1.获取图片资源
        String parent = System.getProperty("user.dir");
        parent += "\\";
        //2.初始化图片
        img1 = Toolkit.getDefaultToolkit().getImage(parent + "res\\bomb_1.gif");
        img2 = Toolkit.getDefaultToolkit().getImage(parent + "res\\bomb_2.gif");
        img3 = Toolkit.getDefaultToolkit().getImage(parent + "res\\bomb_3.gif");
        //存放敌人坦克信息
        Recorder.setEnemyTanks(enemyTanks);
        //播放指定的音乐
        new PlayAudio("res\\bg.wav").start();
    }
    
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        //填充矩形，默认黑色
        g.fillRect(0, 0, 1000, 750);
        //画出游戏信息
        showInfo(g);
        //画自己坦克
        if (hero != null && hero.isLive()) {
            drawTank(hero.getX(), hero.getY(), g, hero.getDirection(), 1);
        }
        //画出自己射击的子弹
        //不为空，且处于发射状态
        if (hero.getShot() != null && hero.getShot().isLive()) {
            g.draw3DRect(hero.getShot().getX(), hero.getShot().getY(), 2, 2, false);
        }
        if (hero.getShots().size() > 0) {
            for (int i = 0; i < hero.getShots().size(); i++) {
                Shot shot = hero.getShots().get(i);
                if (shot != null && shot.isLive()) {
                    g.draw3DRect(shot.getX(), shot.getY(), 2, 2, false);
                } else {
                    //如果shot对象已经无效，就从子弹集合中移除
                    hero.getShots().remove(shot);
                }
            }
        }
        
        //画敌人坦克
        for (int i = 0; i < enemyTanks.size(); i++) {
            EnemyTank enemyTank = enemyTanks.get(i);
            //判断当前坦克是否还存活，只有存活的时候才去画
            if (enemyTank.isLive()) {
                drawTank(enemyTank.getX(), enemyTank.getY(), g, enemyTank.getDirection(), 0);
                //绘制敌人坦克子弹
                Vector<Shot> shots = enemyTank.getShots();
                for (int j = 0; j < shots.size(); j++) {
                    Shot shot = shots.get(j);
                    //子弹存活的时候才绘制
                    if (shot.isLive()) {
                        g.draw3DRect(shot.getX(), shot.getY(), 1, 1, false);
                    } else {
                        //子弹出边界了，移除子弹
                        enemyTank.getShots().remove(shot);
                    }
                }
            }
        }
        //如果bombs 集合中有对象，就画出
        for (int i = 0; i < bombs.size(); i++) {
            Bomb bomb = bombs.get(i);
            //根据当前炸弹的life值画出对应的图片
            if (bomb.getLife() > 6) {
                g.drawImage(img1, bomb.getX(), bomb.getY(), 60, 60, this);
            } else if (bomb.getLife() > 3) {
                g.drawImage(img2, bomb.getX(), bomb.getY(), 60, 60, this);
            } else {
                g.drawImage(img3, bomb.getX(), bomb.getY(), 60, 60, this);
            }
            //让这个炸弹的生命值减少
            bomb.lifeDown();
            //如果bomb 生命值为0，就从集合中删除
            if (bomb.getLife() == 0) {
                bombs.remove(bomb);
            }
        }
    }
    
    /**
     * 显示 击毁敌方的坦克信息
     *
     * @param g 画图
     */
    public void showInfo(Graphics g) {
        g.setColor(Color.BLACK);
        Font font = new Font("宋体", Font.BOLD, 25);
        g.setFont(font);
        g.drawString("您累计击毁敌方坦克", 1020, 30);
        drawTank(1020, 60, g, 0, 0);
        g.setColor(Color.BLACK);
        g.drawString(Recorder.getAllEnemyTankTotal() + "", 1080, 100);
    }
    
    /**
     * 绘制坦克
     *
     * @param x         坦克的左上角x坐标
     * @param y         坦克的左上角y坐标
     * @param g         画笔
     * @param direction 坦克方向（上下左右）
     * @param type      坦克类型
     */
    public void drawTank(int x, int y, Graphics g, int direction, int type) {
        //根据不同类型的坦克设置不同颜色
        switch (type) {
            case 0://0 敌人的坦克
                g.setColor(Color.cyan);
                break;
            //
            case 1://1 我们的坦克
                g.setColor(Color.yellow);
                break;
        }
        //根据坦克的方向来绘制坦克
        //direction 0-向上，1-向右，2-向下，3-向左
        switch (direction) {
            case 0:
                //左右两边轮子
                g.fill3DRect(x, y, 10, 60, false);
                g.fill3DRect(x + 30, y, 10, 60, false);
                //画矩形
                g.fill3DRect(x + 10, y + 10, 20, 40, false);
                //画圆
                g.fillOval(x + 10, y + 20, 20, 20);
                //画炮杆--起点终点
                g.drawLine(x + 20, y + 30, x + 20, y);
                break;
            case 1:
                g.fill3DRect(x, y, 60, 10, false);//上边轮子
                g.fill3DRect(x, y + 30, 60, 10, false);//下边轮子
                //画矩形
                g.fill3DRect(x + 10, y + 10, 40, 20, false);
                //画圆
                g.fillOval(x + 20, y + 10, 20, 20);
                //画炮杆--起点终点
                g.drawLine(x + 30, y + 20, x + 60, y + 20);
                break;
            case 2:
                //两边轮子
                g.fill3DRect(x, y, 10, 60, false);
                g.fill3DRect(x + 30, y, 10, 60, false);
                //画矩形
                g.fill3DRect(x + 10, y + 10, 20, 40, false);
                //画圆
                g.fillOval(x + 10, y + 20, 20, 20);
                //画炮杆--起点终点
                g.drawLine(x + 20, y + 30, x + 20, y + 60);
                break;
            case 3:
                g.fill3DRect(x, y, 60, 10, false);//上边轮子
                g.fill3DRect(x, y + 30, 60, 10, false);//下边轮子
                //画矩形
                g.fill3DRect(x + 10, y + 10, 40, 20, false);
                //画圆
                g.fillOval(x + 20, y + 10, 20, 20);
                //画炮杆--起点终点
                g.drawLine(x + 30, y + 20, x, y + 20);
                break;
            default:
                System.out.println("暂时没有处理");
        }
    }
    
    @Override
    public void keyTyped(KeyEvent e) {
    
    }
    
    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        //判断用户按下的按键
        if (KeyEvent.VK_W == code) { //向上
            //移动时判断不能重叠
            hero.setDirection(0);
            hero.moveUp();
        } else if (KeyEvent.VK_D == code) { //向右
            hero.setDirection(1);
            hero.moveRight();
        } else if (KeyEvent.VK_S == code) { //向下
            hero.setDirection(2);
            hero.moveDown();
        } else if (KeyEvent.VK_A == code) { //向左
            hero.setDirection(3);
            hero.moveLeft();
        }
        //判断是否是射击
        if (KeyEvent.VK_J == code) {
            //如果为空，说明是第一次发射
            //发送一颗子弹
//            if (hero.getShot() == null || !(hero.getShot().isLive())){
//                hero.shotEnemyTank();
//            }
            
            //发射多颗子弹
            hero.shotEnemyTank();
        }
        
        //重绘
        this.repaint();
    }
    
    
    //如果我们的坦克可以发射多发子弹
    //在判断我方子弹是否击中敌人坦克时，就需要把我们的子弹集合所有的子弹取出，都取出和敌人的所有坦克进行判断
    //编写方法，判断我方的子弹是否击中敌人坦克
    
    /**
     * 判断子弹和坦克
     *
     * @param shots 子弹集合
     * @param tank  坦克
     */
    public void hitTank(Vector<Shot> shots, Tank tank) {
        for (int i = 0; i < shots.size(); i++) {
            hitTank(shots.get(i), tank);
        }
    }
    
    /**
     * 判断子弹和坦克
     *
     * @param s    子弹
     * @param tank 坦克
     */
    public void hitTank(Shot s, Tank tank) {
        switch (tank.getDirection()) {
            case 0://坦克向上
            case 2://坦克向下
                if (s.getX() > tank.getX() && s.getX() < tank.getX() + 40 &&
                            s.getY() > tank.getY() && s.getY() < tank.getY() + 60) {
                    s.setLive(false);
                    tank.setLive(false);
                    //当我的子弹击中敌人坦克后，将enemyTank 坦克从集合中去掉
                    enemyTanks.remove(tank);
                    //当我方击毁一辆敌方坦克时，就对一个值++(前提不是我放坦克)
                    if (!(tank instanceof Hero)) {
                        Recorder.addlEnemyTankTotal();
                    }
                    //创建bomb对象，加入到bombs集合
                    Bomb bomb = new Bomb(tank.getX(), tank.getY());
                    bombs.add(bomb);
                }
                break;
            case 1://坦克向右
            case 3://坦克向左
                if (s.getX() > tank.getX() && s.getX() < tank.getX() + 60 &&
                            s.getY() > tank.getY() && s.getY() < tank.getY() + 40) {
                    s.setLive(false);
                    tank.setLive(false);
                    //打我的子弹击中敌人坦克后，将enemyTank 坦克从集合中去掉
                    enemyTanks.remove(tank);
                    //当我方击毁一辆敌方坦克时，就对一个值++(前提不是我放坦克)
                    if (!(tank instanceof Hero)) {
                        Recorder.addlEnemyTankTotal();
                    }
                    //创建bomb对象，加入到bombs集合
                    Bomb bomb = new Bomb(tank.getX(), tank.getY());
                    bombs.add(bomb);
                }
                break;
        }
    }
    
    @Override
    public void keyReleased(KeyEvent e) {
    
    }
    
    @Override
    public void run() {
        //每隔100毫秒，重绘区域
        while (true) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //判断是否击中敌人坦克
            hitEnemy();
            //判断敌人坦克是否击中我们
            hitHero();
            this.repaint();
        }
    }
    
    /**
     * 判断是否击中敌人坦克
     */
    public void hitEnemy() {
        if (hero.getShot() != null && hero.getShot().isLive()) {
            //遍历敌人所有的坦克
            for (int i = 0; i < enemyTanks.size(); i++) {
                EnemyTank enemyTank = enemyTanks.get(i);
                //发射单个子弹
//                    hitTank(hero.getShot(),enemyTank);
                //发射多颗子弹
                hitTank(hero.getShots(), enemyTank);
            }
        }
    }
    
    /**
     * 判断敌人坦克是否击中
     */
    public void hitHero() {
        if (enemyTanks.size() > 0) {
            //拿到所有敌人的坦克-下的子弹，判断是否重合如果重合则显示爆炸
            for (int i = 0; i < enemyTanks.size(); i++) {
                EnemyTank enemyTank = enemyTanks.get(i);
                //判断是否击中
                //敌人的子弹
                Vector<Shot> shots = enemyTank.getShots();
                if (shots.size() > 0) {
                    for (int j = 0; j < shots.size(); j++) {
                        Shot shot = shots.get(j);
                        if (hero.isLive() && shot.isLive()) {
                            //判断子弹和坦克是否重合
                            hitTank(shots, hero);
                        }
                    }
                }
            }
        }
    }
}

