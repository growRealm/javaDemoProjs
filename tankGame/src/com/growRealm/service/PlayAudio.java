package com.growRealm.service;

import javax.sound.sampled.*;
import java.io.*;

/**
 * 播放音乐
 */
public class PlayAudio extends Thread {
    
    private String filename;
    
    public PlayAudio(String wavfile) {
        String path = System.getProperty("user.dir");
        filename = path+"\\"+wavfile;
    }
    
    @Override
    public void run() {
        // 从项目资源目录下加载背景音乐
        AudioInputStream audioInputStream = null;
        try {
            audioInputStream = AudioSystem.getAudioInputStream(new BufferedInputStream(new FileInputStream(filename)));
        } catch (Exception e1) {
            e1.printStackTrace();
            return;
        }
        
        AudioFormat format = audioInputStream.getFormat();
        SourceDataLine auline = null;
        DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
        
        try {
            auline = (SourceDataLine) AudioSystem.getLine(info);
            auline.open(format);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        
        auline.start();
        int nBytesRead = 0;
        //缓存
        byte[] abData = new byte[512];
        
        try {
            while (nBytesRead != -1) {
                nBytesRead = audioInputStream.read(abData, 0, abData.length);
                if (nBytesRead >= 0)
                    auline.write(abData, 0, nBytesRead);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return;
        } finally {
            auline.drain();
            auline.close();
        }
    }
}

