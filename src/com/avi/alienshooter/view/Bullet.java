/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avi.alienshooter.view;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

/**
 *
 * @author Avi
 */
public class Bullet {

    private static final int BULLETWIDTH = 5;
    private static final int BULLETHEIGHT = 10;
    private double x;
    private double y = 700;
    private Color bulletColor = Color.YELLOW;

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

    public Color getBulletColor() {
        return bulletColor;
    }

    public void setBulletColor(Color bulletColor) {
        this.bulletColor = bulletColor;
    }

    void paint(Graphics2D g2, AlienShooterComponent component) {
        Rectangle bullet = new Rectangle((int)this.getX(), (int)this.getY(), BULLETWIDTH, BULLETHEIGHT);

        g2.setColor(bulletColor);
        g2.fill(bullet);
        g2.draw(bullet);
    }
}
