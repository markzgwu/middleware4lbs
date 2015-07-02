package org.tools.forMaths;

import java.math.BigDecimal;

public final class MathTool4Integer {
	public static int pow(int base,int power){
        BigDecimal b1 = new BigDecimal(base);
        BigDecimal b2 = b1.pow(power);
		return b2.toBigInteger().intValue();
	}
}
