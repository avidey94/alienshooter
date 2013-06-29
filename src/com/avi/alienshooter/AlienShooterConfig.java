/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avi.alienshooter;

import com.avi.alienshooter.util.Util;
import java.awt.Rectangle;

/**
 *
 * @author Avi
 */
public class AlienShooterConfig {
    private int gunSpeed = 100;
    private int alienSpeed = 100;
    private int bulletSpeed = 1000;
    //timer interval in milliseconds
    private int timerInterval = 30;
    
    public int getGunSpeed() {
        
        Rectangle world = Util.getWorldCoordinate();
        int pixels = (int)world.getWidth() / gunSpeed;
        return pixels;
        
    }
    
    public int getWorldGunSpeed() {
        return gunSpeed;
    }

    public void setGunSpeed(int gunSpeed) {
        this.gunSpeed = gunSpeed;
    }

    public int getAlienSpeed() {
        Rectangle world = Util.getWorldCoordinate();
        int pixels = (int)world.getWidth() / alienSpeed;
        return pixels;
    }
    
    public int getWorldAlienSpped() {
        return alienSpeed;
    }

    public void setAlienSpeed(int alienSpeed) {
        this.alienSpeed = alienSpeed;
    }

    public int getBulletSpeed() {
        Rectangle world = Util.getWorldCoordinate();
        int pixels = (int)world.getWidth() / bulletSpeed;
        return pixels;
    }
    
    public int getWorldBulletSpeed () {
        return bulletSpeed;
    }

    public void setBulletSpeed(int bulletSpeed) {
        this.bulletSpeed = bulletSpeed;
    }

    public int getTimerInterval() {
        return timerInterval;
    }

    public void setTimerInterval(int timerInterval) {
        this.timerInterval = timerInterval;
    }
    
    
}
