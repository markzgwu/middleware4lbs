package testcases.tools.maths;

import static org.junit.Assert.*;

import org.junit.Test;
import org.tools.forMaths.MathTool4Integer;

public class ArithmeticLib4IntegerTest {

	@Test
	public void testPow() {
		assertEquals(256,MathTool4Integer.pow(4, 4));
	}

}
