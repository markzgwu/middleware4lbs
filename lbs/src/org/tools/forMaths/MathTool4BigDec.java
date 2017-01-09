package org.tools.forMaths;

import java.math.BigDecimal;

public class MathTool4BigDec {
	
	public static double Round(double value){
		return Round(value,5);
	}
	
	public static double Round12(double value){
		return Round(value,12);
	}
	
	public static double Round(double value,int scale){
		BigDecimal v = new BigDecimal(value);
		return v.setScale(scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}
	
    /**
     * �ṩ��ȷ�ӷ������add����
     * @param value1 ������
     * @param value2 ����
     * @return ���������ĺ�
     */
    public static double add(double value1,double value2){
        BigDecimal b1 = new BigDecimal(Double.valueOf(value1));
        BigDecimal b2 = new BigDecimal(Double.valueOf(value2));
        return b1.add(b2).doubleValue();
    }
    
    /**
     * �ṩ��ȷ���������sub����
     * @param value1 ������
     * @param value2 ����
     * @return ���������Ĳ�
     */
    public static double sub(double value1,double value2){
        BigDecimal b1 = new BigDecimal(Double.valueOf(value1));
        BigDecimal b2 = new BigDecimal(Double.valueOf(value2));
        return b1.subtract(b2).doubleValue();
    }
    
    /**
     * �ṩ��ȷ�˷������mul����
     * @param value1 ������
     * @param value2 ����
     * @return ���������Ļ�
     */
    public static double mul(double value1,double value2){
        BigDecimal b1 = new BigDecimal(Double.valueOf(value1));
        BigDecimal b2 = new BigDecimal(Double.valueOf(value2));
        return b1.multiply(b2).doubleValue();
    }
    
    /**
     * �ṩ��ȷ�ĳ������㷽��div
     * @param value1 ������
     * @param value2 ����
     * @param scale ��ȷ��Χ
     * @return ������������
     */
    public static double div(double value1,double value2){
        BigDecimal b1 = new BigDecimal(Double.valueOf(value1));
        BigDecimal b2 = new BigDecimal(Double.valueOf(value2));
        double result = b1.divide(b2).doubleValue();
        return result;
    }
}
