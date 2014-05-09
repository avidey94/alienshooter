/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avi.alienshooter;

import com.avi.alienshooter.resources.ImageManager;
import com.avi.alienshooter.resources.SoundManager;
import com.avi.alienshooter.view.AlienWorldView;

/**
 *
 * @author Avi
 */
public class AlienShooter {

    private static AlienShooter theInstance = null;
    //Linked List of aliens
    private AlienWorldView frame = null;
    private AlienShooterConfig config = null;
    private ImageManager imageManager = null;
    private SoundManager soundManager = null;
    
    public static void main(String[] args) {
        theInstance = new AlienShooter();
        theInstance.run();
    }

    public SoundManager getSoundManager() {
        return soundManager;
    }

    public static AlienShooter getInstance() {
        return theInstance;
    }

    private AlienShooter() {
        config = new AlienShooterConfig();
    }

    private void run() {
        try {
            this.imageManager = new ImageManager();
            this.soundManager = new SoundManager();
            this.imageManager.loadImages();
            this.soundManager.loadMusic();
            //this.soundManager.playBackgroundSound();
            //this.soundManager.playExplosion();
            frame = new AlienWorldView();
            frame.setVisible(true);
            frame.getBody().startTimer();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public AlienWorldView getFrame() {
        return frame;
    }

    public AlienShooterConfig getConfig() {
        return config;
    }

    public static AlienShooter getTheInstance() {
        return theInstance;
    }

    public ImageManager getImageManager() {
        return imageManager;
    }
}
