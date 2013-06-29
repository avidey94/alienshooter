/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avi.alienshooter.view;

import com.avi.alienshooter.AlienShooter;
import com.avi.alienshooter.resources.ImageManager;
import com.avi.alienshooter.util.Util;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;

/**
 * This class is responsible for painting the background
 * @author Avi
 */
public class Scenery implements AlienViewable{
    private final int GROUND_PERCENT = 10;
    private Color backgroundColor = Color.BLACK;
    private Image backgroundImage;
    private Image groundImage;
    
    public Scenery() {
        AlienShooter instance = AlienShooter.getTheInstance();
        ImageManager im = instance.getImageManager();
        backgroundImage = im.getBackgroundImage();
        groundImage = im.getGroundImage();
    }
    
    @Override
    public void paint(Graphics2D g2, AlienShooterComponent parent) {
        //clear the background and fill it black
        g2.setColor(backgroundColor);
        Rectangle worldCoordinateSystem = Util.getWorldCoordinate();
        g2.drawImage(backgroundImage, 0, 0, (int)worldCoordinateSystem.getWidth(), (int)worldCoordinateSystem.getHeight(), null);
        g2.draw(worldCoordinateSystem);
        //g2.fill(worldCoordinateSystem);
        //draw the ground
        g2.setColor(Color.GREEN);
        Rectangle ground = this.getGroundRectangle(parent);
        g2.drawImage(groundImage, (int)ground.getX(), (int)ground.getY(), (int)ground.getWidth(), (int)ground.getHeight(), null);
        //g2.fill(ground)
        //g2.draw(ground);
    }
    
    public Rectangle getGroundRectangle(AlienShooterComponent parent) {
        Rectangle worldCoordinateSystem = Util.getWorldCoordinate();
        double width = worldCoordinateSystem.getWidth();
        double height = worldCoordinateSystem.getHeight();
        
        double groundHeight = (int)((double)GROUND_PERCENT/100.0 * (double)height);
        int groundX = 0;
        double groundY = height - groundHeight;
        Rectangle returnRect = new Rectangle((int)groundX,(int)groundY,(int)width,(int)groundHeight);
        return returnRect;
    }

    public Color getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
    }
    
    
}
