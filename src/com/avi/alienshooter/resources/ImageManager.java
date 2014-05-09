/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avi.alienshooter.resources;

import java.awt.Image;
import java.net.URL;
import java.util.LinkedList;
import javax.imageio.ImageIO;

/**
 *
 * @author Avi
 */
public class ImageManager {
    private LinkedList<Image> alienSet = new LinkedList<>();
    
    private Image robot = null;
    private Image background = null;
    private Image ground = null;
    private Image fail = null;
    private Image ryan = null;
    
    public void loadImages() throws Exception {
        //aliens should be 300x400
        
        URL url = this.getClass().getResource("Kieran.png");
        Image keiranFace = ImageIO.read(url);
        alienSet.add(keiranFace);
        
         url = this.getClass().getResource("Spaceship.png");
        Image spaceShip = ImageIO.read(url);
        alienSet.add(spaceShip);
        
        
        
        url = this.getClass().getResource("Jastin.png");
        Image jastinFace = ImageIO.read(url);
        alienSet.add(jastinFace);
        
        url = this.getClass().getResource("cage.png");
        Image cageFace = ImageIO.read(url);
        alienSet.add(cageFace);
        
        url = this.getClass().getResource("Yoda.png");
        Image yodaFace = ImageIO.read(url);
        alienSet.add(yodaFace);
        
        url = this.getClass().getResource("RobotHead.png");
        robot = ImageIO.read(url);
        
        url = this.getClass().getResource("Background.png");
        background = ImageIO.read(url);
        
        url = this.getClass().getResource("Ground.png");
        ground = ImageIO.read(url);
        
        url = this.getClass().getResource("SammyTrans.png");
        Image sammyFace = ImageIO.read(url);
        alienSet.add(sammyFace);
        
        url = this.getClass().getResource("fail.png");
        fail = ImageIO.read(url);
        
        url = this.getClass().getResource("ryan.png");
        Image ryan = ImageIO.read(url);
        alienSet.add(ryan);
    }

    public LinkedList<Image> getAlienSet() {
        return alienSet;
    }

   

    public Image getRobot() {
        return robot;
    }

    public Image getBackgroundImage() {
        return background;
    }

    public Image getGroundImage() {
        return ground;
    }
    
    
    
    
}
