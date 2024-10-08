package com.growRealm.service;

import com.growRealm.pojo.EnemyTank;
import com.growRealm.pojo.EnemyTankInfo;

import java.io.*;
import java.util.Vector;

// 用于记录
public class Recorder {
    // 定义变量，记录我方击毁敌人坦克数量
    private static int allEnemyTankTotal = 0;
    // 定义IO 对象,准备写数据到文件中
    private static BufferedWriter bufferedWriter = null;
    private static BufferedReader bufferedReader = null;
    // 要将游戏数据保存到哪个目录下
    private static String recordFile;
    // 存储敌人坦克
    private static Vector<EnemyTank> enemyTanks = null;
    // 定义一个EnemyTankInfo 类型的Vector ，用于保存敌人的信息
    private static Vector<EnemyTankInfo> enemyTankInfos = new Vector<>();
    
    // 在类加载到内存时执行的一段静态初始化代码块，且只执行一次，不论创建多少个类的实例
    static {
        String parentPath = System.getProperty("user.dir");
        // 提高跨平台兼容性，使用 File.separator 代替硬编码的反斜杠 \\
        recordFile = parentPath + File.separator + "record.txt";
    }
    
    /**
     * 该方法在继续上局时调用；用于读取record.txt 文件，恢复相关信息
     *
     * @return 敌人坦克信息
     */
    public static Vector<EnemyTankInfo> getEnemyTankInfos() {
        try {
            bufferedReader = new BufferedReader(new FileReader(recordFile));
            // 击毁数量
            allEnemyTankTotal = Integer.parseInt(bufferedReader.readLine());
            // 获取敌人坦克信息
            String dataLine = "";
            while ((dataLine = bufferedReader.readLine()) != null) {
                String[] split = dataLine.split(" ");
                if (split.length == 3) {
                    EnemyTankInfo enemyTankInfo = new EnemyTankInfo(Integer.parseInt(split[0]), Integer.parseInt(split[1]), Integer.parseInt(split[2]));
                    enemyTankInfos.add(enemyTankInfo);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return enemyTankInfos;
    }
    
    public static void setEnemyTanks(Vector<EnemyTank> enemyTanks) {
        Recorder.enemyTanks = enemyTanks;
    }
    
    public static int getAllEnemyTankTotal() {
        return allEnemyTankTotal;
    }
    
    public static void setAllEnemyTankTotal(int allEnemyTankTotal) {
        Recorder.allEnemyTankTotal = allEnemyTankTotal;
    }
    
    // 当我方坦克击毁一辆敌人坦克，就应当对这个值++
    public static void addToEnemyTankTotal() {
        allEnemyTankTotal++;
    }
    
    /**
     * 当游戏退出时，我们将 allEnemyTankTotal 保存到 record.txt 文件中
     */
    public static void keepRecord() {
        try {
            bufferedWriter = new BufferedWriter(new FileWriter(recordFile));
            bufferedWriter.write(allEnemyTankTotal + "\r\n");
            // 变量敌人坦克集合，然后根据情况保存
            // OOP 编程思想，定义一个属性，通过SET 方法得到敌人的坦克集合
            if (!enemyTanks.isEmpty()) {
                for (EnemyTank enemyTank : enemyTanks) {
                    // 为了保险，判断一下是否存活
                    if (enemyTank.isLive()) {
                        // 保存enemy坦克信息
                        String record = enemyTank.getX() + " " + enemyTank.getY() + " " + enemyTank.getDirection();
                        bufferedWriter.write(record);
                        bufferedWriter.newLine();
                    }
                }
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bufferedWriter != null) {
                try {
                    bufferedWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    public static String getRecordFile() {
        return recordFile;
    }
    
    public static void setRecordFile(String recordFile) {
        Recorder.recordFile = recordFile;
    }
}

