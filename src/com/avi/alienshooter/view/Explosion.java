/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avi.alienshooter.view;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;

/**
 *
 * @author Avi
 */
public class Explosion implements AlienViewable {
    //screen coordinate system

    private static final double MIN_DIAMETER = 10;
    private static final double MAX_DIAMETER = 80.0;
    private static final double EXPLOSION_SPEED = 5;
    private static final Color COLOR = Color.RED;
    private double x;
    private double y;
    private double diameter = MIN_DIAMETER;
    private boolean expanding = true;

    public boolean isExpanding() {
        return expanding;
    }

    public void setExpanding(boolean expanding) {
        this.expanding = expanding;
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

    public double getDiameter() {
        return diameter;
    }

    public void setDiameter(double diameter) {
        this.diameter = diameter;
    }

    /**
     * returns the shape of the Alien in screen coordinate system.
     *
     * @param g2
     * @param component
     * @return
     */
    public Shape getShape(Graphics2D g2, AlienShooterComponent component) {
        Scenery scenery = component.getScenery();
        Point boomPosition = new Point((int) x, (int) y);
        AffineTransform at = g2.getTransform();
        at.transform(boomPosition, boomPosition);
        at.setToIdentity();
        g2.setTransform(at);
        Ellipse2D.Double boomCircle = new Ellipse2D.Double((double) boomPosition.getX(), (double) boomPosition.getY(), diameter, diameter);
        at.scale(component.getXScale(), component.getYScale());
        return boomCircle;
    }

    /*
     * public void paint(Graphics2D g2, AlienShooterComponent comp) {
        
     }
     */
    public void paint(Graphics2D g2, AlienShooterComponent component) {
        AffineTransform at = g2.getTransform();
        Shape alienShape = this.getShape(g2, component);
        g2.setColor(COLOR);
        at.setToIdentity();
        g2.fill(alienShape);
        at.scale(component.getXScale(), component.getYScale());
        g2.setTransform(at);
    }

    public boolean isDone() {
        if (diameter < MIN_DIAMETER) {
            return true;
        }
        return false;
    }

    void explodeOrCollapse() {
        if (expanding == true) {
            //make diameter bigger
            double newDiameter = this.getDiameter() + EXPLOSION_SPEED;
            if (newDiameter < MAX_DIAMETER) {
                this.setDiameter(newDiameter);
            } else {
                this.expanding = false;
            }
        } else {
            //make diameter smaller
            double newDiameter = this.getDiameter() - EXPLOSION_SPEED;
            this.setDiameter(newDiameter);
        }
    }
}
