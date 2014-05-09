/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avi.alienshooter.view;

import com.avi.alienshooter.AlienShooter;
import com.avi.alienshooter.resources.ImageManager;
import com.avi.alienshooter.util.Util;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;

/**
 *
 * @author Avi
 */
public class Gun implements AlienViewable {

    //center x
    private double x = 0;
    //center y
    private double y = 0;
    private boolean moving = false;
    private boolean movingRight = true;
    private boolean firing = false;
    private Dimension gunDimension = new Dimension(50, 50);

    public Gun(Scenery scenery) {
        Rectangle worldCoordinateSystem = Util.getWorldCoordinate();
        double middle = (worldCoordinateSystem.getWidth() / 2);
        this.x = middle;

    }

    public double getY() {

        //return in world
        AlienShooter alienShooter = AlienShooter.getInstance();
        AlienWorldView viewer = alienShooter.getFrame();
        AlienShooterComponent comp = viewer.getBody();
        Scenery scenery = comp.getScenery();
        Rectangle groundRectangle = scenery.getGroundRectangle(comp);
        double y = groundRectangle.getY() - gunDimension.height;
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public boolean isMoving() {
        return moving;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
    }

    public boolean isMovingRight() {
        return movingRight;
    }

    public void setMovingRight(boolean movingRight) {
        this.movingRight = movingRight;
    }

    public boolean isFiring() {
        return firing;
    }

    public void setFiring(boolean firing) {
        this.firing = firing;
    }
    
    
    public Dimension getGunDimension() {
        return gunDimension;
    }

    public void setGunDimension(Dimension gunDimension) {
        this.gunDimension = gunDimension;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }
    
    public void moveLeft() {
        moving = true;
        movingRight = false;
    }
    
    public void moveRight() {
        moving = true;
        movingRight = true;
    }
    
    public void stop() {
        moving = false;
    }

    @Override
    public void paint(Graphics2D g2, AlienShooterComponent component) {
        try {
            Scenery scenery = component.getScenery();
            Rectangle ground = scenery.getGroundRectangle(component);

            Point gunLocationCenter = new Point((int) x, (int) ground.getY());
            AffineTransform at = g2.getTransform();

            at.transform(gunLocationCenter, gunLocationCenter);
            at.setToIdentity();
            //at.scale(1/gunLocationCenter.getX(), 1/gunLocationCenter.getY());
            g2.setTransform(at);
            ImageManager im = AlienShooter.getInstance().getImageManager();
            Image robotImage = im.getRobot();
            Rectangle gunRect = new Rectangle((int) gunLocationCenter.x, (int) gunLocationCenter.y, gunDimension.width, gunDimension.height);


            //AffineTransform at = g2.getTransform();
            int xOffset = -gunRect.width / 2;
            int yOffset = -gunRect.height;
            gunRect.translate(xOffset, yOffset);

            //gunRect = Util.invertTransform(at, gunRect);
            //gunRect = at.inverseTransform(null, null)
            g2.setColor(Color.RED);
            //g2.draw(gunRect);
            g2.drawImage(robotImage, gunRect.x, gunRect.y, gunRect.width, gunRect.height, null);
            at.scale(component.getXScale(), component.getYScale());
            g2.setTransform(at);
        } catch (Exception ex) {
            //nothing
        }

    }
}
