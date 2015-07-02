package org.examples.v1;

import static org.junit.Assert.assertEquals;

import java.awt.geom.Rectangle2D;
import java.util.Collections;
import java.util.List;

import org.junit.Test;
import org.khelekore.prtree.*;
public final class PRtree {

    @Test
    public void testCreate () {
	MBR mbr = new SimpleMBR (0, 1, 0, 1);
	assertEquals (0.0, mbr.getMin (0), 0.05);
	assertEquals (0.0, mbr.getMin (1), 0.05);
	assertEquals (1.0, mbr.getMax (0), 0.05);
	assertEquals (1.0, mbr.getMax (1), 0.05);
	assertEquals (2, mbr.getDimensions ());
    }

    @Test
    public void testUnion () {
	MBR mbr1 = new SimpleMBR (0, 1, 0, 1);
	MBR mbr2 = new SimpleMBR (2, 3, 2, 3);
	MBR mbr = mbr1.union (mbr2);
	assertEquals (0.0, mbr.getMin (0), 0.05);
	assertEquals (0.0, mbr.getMin (1), 0.05);
	assertEquals (3.0, mbr.getMax (0), 0.05);
	assertEquals (3.0, mbr.getMax (1), 0.05);
    }
    
	public static void main(String[] args) {


	}

}
