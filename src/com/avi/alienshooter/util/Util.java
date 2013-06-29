/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avi.alienshooter.util;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

/**
 *
 * @author Avi
 */
public class Util {

    private static Rectangle worldCoordinate = new Rectangle(0, 0, 1000, 1000);

    public static Point convertRectangle(Point point, Rectangle sourceCoordinate, Rectangle destinationCoordinate) {
        double sourceCoordinateWidth = sourceCoordinate.getWidth();
        double sourceCoordinateHeight = sourceCoordinate.getHeight();
        double destinationCoordinateWidth = destinationCoordinate.getWidth();
        double destinationCoordinateHeight = destinationCoordinate.getHeight();

        double scaleX = destinationCoordinateWidth / sourceCoordinateWidth;
        double scaleY = destinationCoordinateHeight / sourceCoordinateWidth;

        double returnX = point.getX() * scaleX + destinationCoordinate.getX();
        double returnY = point.getY() * scaleY + destinationCoordinate.getY();
        Point returnPoint = new Point((int) returnX, (int) returnY);
        return returnPoint;

    }

    public static Rectangle getWorldCoordinate() {
        return worldCoordinate;
    }

    public static Rectangle invertTransform(AffineTransform at, Rectangle rect) {
        Point2D.Double topLeft = new Point2D.Double(rect.getX(), rect.getY());
        double bottomRightX = rect.getX() + rect.getWidth();
        double bottomRightY = rect.getY() + rect.getHeight();
        Point2D.Double bottomRight = new Point2D.Double(bottomRightX, bottomRightY);
        try {
            at.inverseTransform(topLeft, topLeft);
            at.inverseTransform(bottomRight, bottomRight);
        } catch (Exception ex) {
            //nothing
        }
        double width = bottomRight.getX() - topLeft.getX();
        double height = bottomRight.getY() - topLeft.getY();
        Rectangle returnRect = new Rectangle((int)topLeft.getX(), (int)topLeft.getY(), (int) width, (int) height);
        return returnRect;
    }
}
