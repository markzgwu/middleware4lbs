package org.examples.v2.ptree;

import org.examples.v1.junit.TestPRTree;
import org.junit.runner.JUnitCore;

public class RunTests {
    public static void main (String args[]) {
	JUnitCore.main (TestSimpleMBR.class.getName (),
			TestPRTree.class.getName ());
    }
}
