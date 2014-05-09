/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avi.alienshooter.resources;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.InputStream;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

/**
 *
 * @author Avi
 */
public class SoundManager {

    private AudioInputStream backgroundSoundInput;
    private AudioInputStream explodeSoundInput;
    private AudioInputStream hitGroundInput;
    private Clip backgroundClip;
    private Clip explodeClip;
    private Clip hitGroundClip;

    public void loadMusic() throws Exception {
    }

    public void playBackgroundSound() throws Exception {

        InputStream stream = this.getClass().getResourceAsStream("AlienTheme.wav"); //InputStream
        InputStream bufferedIn = new BufferedInputStream(stream);
        backgroundSoundInput = AudioSystem.getAudioInputStream(bufferedIn);
        backgroundClip = AudioSystem.getClip();
        backgroundClip.open(backgroundSoundInput);
        backgroundClip.start();
    }

    public void stopBackgroundSound() {
        backgroundClip.stop();
    }

    public void playExplosion() throws Exception {
        InputStream stream2 = this.getClass().getResourceAsStream("alienShoot.wav");
        InputStream bufferedIn2 = new BufferedInputStream(stream2);
        explodeSoundInput = AudioSystem.getAudioInputStream(bufferedIn2);

        explodeClip = AudioSystem.getClip();
        explodeClip.open(explodeSoundInput);
        explodeClip.start();
    }

    public void playHitGround() throws Exception {
        InputStream stream3 = this.getClass().getResourceAsStream("Randomize42.wav");
        InputStream bufferedIn3 = new BufferedInputStream(stream3);
        hitGroundInput = AudioSystem.getAudioInputStream(bufferedIn3);

        hitGroundClip = AudioSystem.getClip();
        hitGroundClip.open(hitGroundInput);
        hitGroundClip.start();

    }
}
