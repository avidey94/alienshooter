/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avi.alienshooter.view;

import com.avi.alienshooter.AlienShooter;
import com.avi.alienshooter.resources.ImageManager;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.util.LinkedList;

/**
 *
 * @author Avi
 */
public class Alien {
    //these are world by default

    private double x = 20;
    private double y = 20;
    private int speed = 0;
    private Image alienPicture = null;
    private Color alienColor = Color.YELLOW;

    Alien(int alienXCoordinate, Image alienPicture, int speed) {
        this.x = alienXCoordinate;
        this.alienPicture = alienPicture;
        this.speed = speed;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public Color getAlienColor() {
        return alienColor;
    }

    public void setAlienColor(Color alienColor) {
        this.alienColor = alienColor;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }
    
    

    /**
     * returns the shape of the Alien in screen coordinate system.
     * @param g2
     * @param component
     * @return 
     */
    public Shape getShape(Graphics2D g2, AlienShooterComponent component) {
        Scenery scenery = component.getScenery();
        Point alienPosition = new Point((int) x, (int) y);
        AffineTransform at = g2.getTransform();
        at.transform(alienPosition, alienPosition);
        //at.setToIdentity();
        //g2.setTransform(at);
        Ellipse2D.Double alienCircle = new Ellipse2D.Double((double) alienPosition.getX(), (double) alienPosition.getY(), 80, 80);
        //at.scale(component.getXScale(), component.getYScale());
        //g2.setTransform(at);
        return alienCircle;
    }

    public Image getAlienPicture() {
        return alienPicture;
    }

    
    
    /*
     * public void paint(Graphics2D g2, AlienShooterComponent comp) {
        
     }
     */
    void paint(Graphics2D g2, AlienShooterComponent component) {
        AffineTransform at = g2.getTransform();
        Shape alienShape = this.getShape(g2, component);
        g2.setColor(alienColor);
        at.setToIdentity();
        g2.setTransform(at);
        //g2.fill(alienShape);
        
        Rectangle alienShapeRectangle = alienShape.getBounds();
        //at2.scale(.25,.25);
        g2.drawImage(this.getAlienPicture(), alienShapeRectangle.x, alienShapeRectangle.y, alienShapeRectangle.width, alienShapeRectangle.height, null);
        //g2.drawIma
        //g2.drawImage(alienImage, g2.getTransform(), null);
        at.scale(component.getXScale(), component.getYScale());
        g2.setTransform(at);
    }
}
