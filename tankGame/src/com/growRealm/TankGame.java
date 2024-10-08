package com.growRealm;

import com.growRealm.component.MyPanel;
import com.growRealm.service.Recorder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Scanner;


public class TankGame extends JFrame {
    
    private MyPanel panel;
    
    public static void main(String[] args) {
        
        new TankGame();
    }
    
    public TankGame(){
        System.out.println("请输入选择： 1 表示新游戏，2是继续上局");
        Scanner scanner = new Scanner(System.in);
        //将panel放入到thread中
        this.panel = new MyPanel(scanner.next());
        scanner.close();
        
        new Thread(panel).start();
        //设置绑定监听器
        this.addKeyListener(panel);
        this.add(panel);
        //设置窗体关闭
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("坦克大战");
        //获取当前屏幕分辨率
        int width = Toolkit.getDefaultToolkit().getScreenSize().width;
        int height = Toolkit.getDefaultToolkit().getScreenSize().height;
        //设置窗体大小不可变
        this.setResizable(false);
        //设置窗体位置
        width = (width - 1300) / 2;
        height = (height - 750) / 2;
        this.setBounds(width,height,1300,750);
        this.setBackground(Color.black);
        //设置窗体显示
        this.setVisible(true);
        
        //在JFrame 中增加相关关闭窗口的处理
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Recorder.keepRecord();
                System.exit(0);
            }
        });
        
    }
}

