package org.examples.v7;
/*  Hilbert.java
 *
 *  From "An Introduction to Computer Science Using Java",
 *  Samuel N. Kamin, M. Dennis Mickunas, Edward M. Reingold,
 *  McGraw--Hill, 1998.
 *
 *  See also:  http://www.mhhe.com/engcs/compsci/kamin
 *
 *  Bruce M. Bolden
 *  March 23, 1998
 */
 
import java.awt.*;
import java.awt.event.*;
import java.lang.Math.*;


public class Hilbert
{
    static final int  NORTH = 0, EAST = 1, SOUTH = 2, WEST = 3;
    
    int order;
    int offset;
    int scaleFactor;
    Point  lastPoint;
    Point  nwOfWindow;    //  drawing starts at this location
    Polygon poly;
    
    
    Hilbert( Point p )
    {
        nwOfWindow = p;
        order = 1;
    }
    
    void setOrder( int o )
    {
        order = Math.max(o, 1);
    }
    
    int curveSize( int ord )
    {
        //return (int)(Math.pow(2.0, (double)ord)) - 1;
        return Power.power(2, ord) - 1;
    }
    
    public void paint( Graphics g, Dimension d, Color c )
    {
        int  windowSize = Math.min( d.width-nwOfWindow.x, d.height-nwOfWindow.y ) - 30;//was 10
        scaleFactor = (int) ((windowSize*1.0)/curveSize(order));
        poly = new Polygon();
        hilbert( order );
        g.setColor( c );
        g.drawPolyline( poly.xpoints, poly.ypoints, poly.npoints );
    }
    
    void move( int d )
    {
        switch( d )
        {
        case NORTH:
            lastPoint.y -= scaleFactor;
            break;
        case EAST:
            lastPoint.x += scaleFactor;
            break;
        case SOUTH:
            lastPoint.y += scaleFactor;
            break;
        case WEST:
            lastPoint.x -= scaleFactor;
            break;
        }
    }
    
    void hilbert(
        int  i,
        int  facing,
        int  right,
        int  behind,
        int  left )
    {
        if( i == 0 )
            poly.addPoint( lastPoint.x, lastPoint.y );
        else
        {
            hilbert( i-1, left, behind, right, facing );
            move( right );
            hilbert( i-1, facing, right, behind, left );
            move( behind );
            hilbert( i-1, facing, right, behind, left );
            move( left );
            hilbert( i-1, right, facing, left, behind );
        }
    }
    
    void hilbert( int i )
    {
        lastPoint = new Point( nwOfWindow );
        hilbert( i, NORTH, EAST, SOUTH, WEST );
    }
}


class Power
{
    /**  Divide and conquer power algorithm  */
    static int power( int k, int n )
    {
        if( n == 0 )
            return 1;
        else
        {
            int  t = power( k, n/2 );
            if( (n % 2) == 0 )
                return t * t;
            else
                return k * t * t;
        }
    }
}