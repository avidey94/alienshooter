/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avi.alienshooter.view;

import com.avi.alienshooter.AlienShooter;
import com.avi.alienshooter.AlienShooterConfig;
import com.avi.alienshooter.resources.ImageManager;
import com.avi.alienshooter.resources.SoundManager;
import com.avi.alienshooter.util.Util;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferStrategy;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;
import java.util.Scanner;
import javax.swing.Timer;

/**
 *
 * @author Avi
 */
public class AlienShooterComponent extends Canvas implements KeyListener, ComponentListener {

    private Scenery scenery = null;
    //LinkedList of aliens
    LinkedList<Alien> alienList = new LinkedList<>();
    //LinkedList of bullets
    LinkedList<Bullet> bulletList = new LinkedList<>();
    LinkedList<Explosion> explosionList = new LinkedList<>();
    //current time is always System.currentTime(); - no need for an instance variable
    //time the last alien was born
    private long lastAlienBornTime = 0;
    private int nextTimeToWaitToCreateAlien = 1 + (int) (Math.random() * 1);
    private long currentTime = 0;
    private Gun gun = null;
    private GameTimer timer = null;
    private Random random = null;
    private SoundManager soundManager = null;
    public int score = 0;
    private int failCount = 0;
    private boolean initialized = false;
    private boolean scoreChangedLastTime = false;
    private int highScore = 0;
    private boolean newHighScoreMade = false;

    private void createBullet() {
        Gun gun = this.getGun();
        if (gun.isFiring() == false) {
            return;
        }
        Bullet bullet = new Bullet();
        bullet.setX(gun.getX());
        bullet.setY(gun.getY());
        //number of bullets
        if (bulletList.size() < 5) {
            bulletList.addLast(bullet);
        }
        //System.out.prin  tln("Bullet created");    
    }

    public void startTimer() throws Exception {
        this.timer.startTimer();
        AlienShooter.getTheInstance().getSoundManager().playBackgroundSound();
    }

    public void stopTimer() {
        this.timer.stopTimer();
        AlienShooter.getTheInstance().getSoundManager().stopBackgroundSound();
    }

    private void checkCollisions(Graphics2D g2) {
        Iterator<Alien> alienIter = alienList.iterator();
        while (alienIter.hasNext()) {
            Alien alien = alienIter.next();
            Shape alienShape = alien.getShape(g2, this);
            Iterator<Bullet> bulletIter = bulletList.iterator();
            AffineTransform at = g2.getTransform();
            while (bulletIter.hasNext()) {
                Bullet bullet = bulletIter.next();
                Point bulletPoint = new Point((int) bullet.getX(), (int) bullet.getY());
                //moves to screen coordinate system
                at.transform(bulletPoint, bulletPoint);
                if (alienShape.contains(bulletPoint)) {
                    this.score++;
                    if (score > highScore) {
                        this.newHighScoreMade = true;
                        this.highScore = score;
                        System.out.println("new high score" + highScore);
                    }
                    //System.out.println(bulletPoint.getX() + " " + bulletPoint.getY());
                    //System.out.println("Alien: " + alienShape.getBounds().toString());
                    //this.boom();
                    Explosion boom = new Explosion();
                    try {
                        AlienShooter.getInstance().getSoundManager().playExplosion();
                        //System.out.println("boom");
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    boom.setX(bullet.getX());
                    boom.setY(bullet.getY());
                    this.explosionList.addFirst(boom);
                    alienIter.remove();
                    bulletIter.remove();
                    break;
                }
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        //System.out.println("key pressed = " + e.getKeyCode());

        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            //System.out.println("right pressed");
            this.gun.moveRight();
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            //System.out.println("left pressed");
            this.gun.moveLeft();
        }
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            this.gun.setFiring(true);
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        //System.out.println("key released = " + e.getKeyCode());
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            //System.out.println("right released");
            this.gun.stop();
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            //System.out.println("left released");
            this.gun.stop();
        }
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            this.gun.setFiring(false);
        }
    }

    public AlienShooterComponent() throws Exception {
        this.obtainHighScore();
        random = new Random();
        scenery = new Scenery();

        gun = new Gun(scenery);
        timer = new GameTimer();
        //this.addKeyListener(this);
        this.addComponentListener(this);
    }

    private void obtainHighScore() {
        String fileName = "/Users/Avi/Desktop/scores.txt";
        Scanner scanner = null;

        try {
            File file = new File(fileName);
            scanner = new Scanner(file);
            while (scanner.hasNext()) {
                int number = scanner.nextInt();
                this.highScore = number;
                System.out.println(number);
            }
        } catch (FileNotFoundException ex) {
            this.highScore = 0;
            System.out.println(highScore);
        }
    }

    /**
     * for every config.timeinterval check ms:
     */
    public void onTimeTick() {
        //this.soundManager.playExplosion();
        currentTime = System.currentTimeMillis();
        //System.out.println("tick");
        //this.changeBackgroundColor();
        //System.out.println(score);
        if (score % 5 == 1) {
            scoreChangedLastTime = false;
        }
        if (score % 5 == 0 && score != 0 && this.scoreChangedLastTime == false) {
            System.out.println("speed changed");
            AlienShooter instance = AlienShooter.getInstance();
            AlienShooterConfig config = instance.getConfig();
            config.setAlienSpeed(config.getAlienSpeed() + 100);
            scoreChangedLastTime = true;
        }
        this.createAlien();
        this.moveAliens();

        this.moveGun();
        //move the bullets - moveBullets();
        try {
            this.createBullet();
        } catch (Exception ex) {
        }
        this.moveBullets();
        this.explode();

        //this.checkCollisions();

        //check if a new alien needs to be born - createAlien(); 
        //move the gun
        refreshDrawing();

        //move the aliens - moveAliens();
        //check if any of the aliens have been hit

        //this.repaint();
    }

    
    //window resize event handler   
    //put the event handler in the world view - call refreshDrawing within that method
    public void refreshDrawing() {
        if (this.isVisible()) {
            if (!this.initialized) {
                this.initialized = true;
                this.createBufferStrategy(2);

            }
            BufferStrategy bs = this.getBufferStrategy();
            if (bs != null) {
                Graphics g = bs.getDrawGraphics();
                this.render(g);
                g.dispose();
                bs.show();
            }
        }
    }

    private void moveGun() {
        if (this.gun.isMoving() == false) {
            return;
        }
        Gun myGun = this.getGun();
        double gunOldX = myGun.getX();
        AlienShooter instance = AlienShooter.getInstance();
        AlienShooterConfig config = instance.getConfig();
        int gunSpeed = config.getGunSpeed();
        int dx = gunSpeed;
        double rightBound = scenery.getGroundRectangle(this).width;
        boolean direction = gun.isMovingRight();
        if (direction == false) {
            dx = -dx;
        }
        double gunNewX = myGun.getX() + dx;
        if (gunNewX < 0) {
            //too much to the left- go right
            gunNewX = rightBound;
        }
        if (gunNewX > rightBound) {
            gunNewX = 0;
        }

        myGun.setX(gunNewX);
    }

    private void moveBullets() {
        AlienShooter instance = AlienShooter.getInstance();
        AlienShooterConfig config = instance.getConfig();
        int bulletSpeed = config.getBulletSpeed();
        Iterator<Bullet> iter = bulletList.iterator();
        while (iter.hasNext()) {
            Bullet bullet = iter.next();
            double bulletNewY = bullet.getY() - bulletSpeed;
            bullet.setY(bulletNewY);
            if (bullet.getY() < 0) {
                iter.remove();
            }
            //System.out.println("moved");
        }
    }

    private void explode() {
        Iterator<Explosion> iter = explosionList.iterator();
        while (iter.hasNext()) {
            Explosion explosion = iter.next();
            explosion.explodeOrCollapse();
            if (explosion.isDone()) {

                iter.remove();

            }
        }
    }

    private void moveAliens() {
        Rectangle groundRectangle = scenery.getGroundRectangle(this);
        double groundLevel = groundRectangle.getY();
        Iterator<Alien> iter = alienList.iterator();
        AlienShooter instance = AlienShooter.getInstance();
        AlienShooterConfig config = instance.getConfig();




        while (iter.hasNext()) {
            Alien alien = iter.next();
            int alienSpeed = alien.getSpeed();
            double alienNewY = alien.getY() + alienSpeed;
            int red = random.nextInt(255);
            int blue = random.nextInt(255);
            int green = random.nextInt(255);
            Color newColor = new Color(red, blue, green);
            alien.setAlienColor(newColor);
            alien.setY(alienNewY);
            if (alienNewY + 80 > groundLevel) {
                iter.remove();
                try {
                    AlienShooter.getInstance().getSoundManager().playHitGround();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                System.out.println("fail");
                this.failCount++;
            }
        }
    }

    private void changeBackgroundColor() {
        int red = random.nextInt(255);
        int blue = random.nextInt(255);
        int green = random.nextInt(255);
        Color newColor = new Color(red, blue, green);
        scenery.setBackgroundColor(newColor);
    }

    private void createAlien() {
        if (this.getWidth() <= 0) {
            return;
        }
        if (currentTime > lastAlienBornTime + nextTimeToWaitToCreateAlien * 1000) {
            Rectangle worldRectangle = Util.getWorldCoordinate();
            int alienXCoordinate = random.nextInt((int) worldRectangle.getWidth());
            //alienXCoordinate = 500;
            ImageManager im = AlienShooter.getInstance().getImageManager();

            LinkedList<Image> ll = im.getAlienSet();
            int alienListIndex = 0 + (int) (Math.random() * ll.size());
            //alienListIndex = 0;
            Image alienImage = ll.get(alienListIndex);
            AlienShooter instance = AlienShooter.getInstance();
            AlienShooterConfig config = instance.getConfig();
            Alien newAlien = new Alien(alienXCoordinate, alienImage, config.getAlienSpeed());
            lastAlienBornTime = currentTime;
            alienList.addLast(newAlien);
            //System.out.println("alien created " + lastAlienBornTime);
            nextTimeToWaitToCreateAlien = 1 + (int) (Math.random() * 5);
        }
    }

    public double getXScale() {
        Dimension dim = this.getSize();
        Rectangle worldCoordinateSystem = Util.getWorldCoordinate();
        //calculate scales
        double xScale = dim.getWidth() / worldCoordinateSystem.getWidth();
        return xScale;

    }

    public double getYScale() {
        Dimension dim = this.getSize();
        Rectangle worldCoordinateSystem = Util.getWorldCoordinate();
        //calculate scales
        double yScale = dim.getHeight() / worldCoordinateSystem.getHeight();
        return yScale;
    }

    public LinkedList<Alien> getAlienList() {
        return alienList;
    }

    public void setAlienList(LinkedList<Alien> alienList) {
        this.alienList = alienList;
    }

    public LinkedList<Bullet> getBulletList() {
        return bulletList;
    }

    public void setBulletList(LinkedList<Bullet> bulletList) {
        this.bulletList = bulletList;
    }

    public LinkedList<Explosion> getExplosionList() {
        return explosionList;
    }

    public void setExplosionList(LinkedList<Explosion> explosionList) {
        this.explosionList = explosionList;
    }

    public long getLastAlienBornTime() {
        return lastAlienBornTime;
    }

    public void setLastAlienBornTime(long lastAlienBornTime) {
        this.lastAlienBornTime = lastAlienBornTime;
    }

    public int getNextTimeToWaitToCreateAlien() {
        return nextTimeToWaitToCreateAlien;
    }

    public void setNextTimeToWaitToCreateAlien(int nextTimeToWaitToCreateAlien) {
        this.nextTimeToWaitToCreateAlien = nextTimeToWaitToCreateAlien;
    }

    public GameTimer getTimer() {
        return timer;
    }

    public void setTimer(GameTimer timer) {
        this.timer = timer;
    }

    public Random getRandom() {
        return random;
    }

    public void setRandom(Random random) {
        this.random = random;
    }

    public SoundManager getSoundManager() {
        return soundManager;
    }

    public void setSoundManager(SoundManager soundManager) {
        this.soundManager = soundManager;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getFailCount() {
        return failCount;
    }

    public void setFailCount(int failCount) {
        this.failCount = failCount;
    }

    public boolean isInitialized() {
        return initialized;
    }

    public void setInitialized(boolean initialized) {
        this.initialized = initialized;
    }

    //@Override
    public void render(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        //save the old transform
        AffineTransform savedAT = g2.getTransform();
        //set up the new transform
        AffineTransform aT = new AffineTransform();
        Dimension dim = this.getSize();
        Rectangle worldCoordinateSystem = Util.getWorldCoordinate();
        //calculate scales
        double xScale = dim.getWidth() / worldCoordinateSystem.getWidth();
        double yScale = dim.getHeight() / worldCoordinateSystem.getHeight();
        //actually set the right ratios for transformation
        aT.scale(xScale, yScale);
        g2.setTransform(aT);
        this.checkCollisions(g2);
        scenery.paint(g2, this);

        gun.paint(g2, this);
        for (Alien alien : alienList) {
            //make an alien paint method
            alien.paint(g2, this);
        }
        for (Bullet bullet : bulletList) {
            bullet.paint(g2, this);
        }

        for (Explosion explosion : explosionList) {
            explosion.paint(g2, this);
        }

        g2.setColor(Color.red);
        if (failCount == 0) {
            Rectangle boundsRect = this.getBounds();

        }
        Font f = new Font("serif", Font.PLAIN, 50);
        g2.setFont(f);
        g2.drawString(String.valueOf(score), 50, 50);

        /**
         * g2.setColor(Color.green); Font f2 = new Font("serif", Font.PLAIN,
         * 100); g2.setFont(f2); g2.drawString(String.valueOf(failCount), 100,
         * 50);
         */
        g2.setTransform(savedAT);
    }
    
    @Override
    public void paint(Graphics g) {
        //this.render(g);
        this.refreshDrawing();
        System.out.println("paint called");
    }

    public Scenery getScenery() {
        return scenery;
    }

    public Gun getGun() {
        return gun;
    }

    public long getCurrentTime() {
        return currentTime;
    }

    public boolean isScoreChangedLastTime() {
        return scoreChangedLastTime;
    }

    public void setScoreChangedLastTime(boolean scoreChangedLastTime) {
        this.scoreChangedLastTime = scoreChangedLastTime;
    }

    public int getHighScore() {
        return highScore;
    }

    public void setHighScore(int highScore) {
        this.highScore = highScore;

    }

    public void overwriteHighScore() throws Exception {
        if (this.newHighScoreMade == true) {
            File file = new File("/Users/Avi/Desktop/scores.txt");
            PrintStream ps = new PrintStream(file.getAbsoluteFile());
            ps.println(score);
            ps.close();
            this.highScore = score;
            System.out.println("new high score" + this.highScore);
        }
    }

    @Override
    public void componentResized(ComponentEvent e) {
        this.refreshDrawing();
        System.out.println("resized");
    }

    @Override
    public void componentMoved(ComponentEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void componentShown(ComponentEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void componentHidden(ComponentEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private class GameTimer implements ActionListener {

        private Timer timer = null;

        public GameTimer() {
            AlienShooterConfig config = AlienShooter.getInstance().getConfig();
            timer = new Timer(config.getTimerInterval(), this);
            timer.setInitialDelay(config.getTimerInterval());
            //timer.start();
        }

        public void startTimer() {
            timer.start();

        }

        public void stopTimer() {
            timer.stop();
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            AlienShooterComponent.this.onTimeTick();
        }
    }
}
