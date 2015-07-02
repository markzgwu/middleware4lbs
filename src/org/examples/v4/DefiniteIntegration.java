package org.examples.v4;

import static java.lang.Math.*;
public class DefiniteIntegration {

			 // 0~1区间n等分
			 private static int n = 1000000;

			 // 随便定义个曲线e的x次方, 取其x在0~1的定积分;
			 public static double f(double x) {
			  double f;
			  f = pow(E, x);
			  return f;
			 }

			 // 梯形法求定积分
			 /**
			  * x0: 坐标下限, xn: 坐标上限
			  */
			 public static double getDefiniteIntegralByTrapezium(double x0, double xn) {
			  double h = abs(xn - x0) / n;
			  double sum = 0;
			  for (double xi = 0; xi <= xn; xi = xi + h) {
			   sum += (f(xi) + f(xi + h)) * h / 2;
			  }
			  return sum;
			 }
			 /**
			  * x0: 坐标下限, xn: 坐标上限
			  */
			 // 矩形法求定积分, 右边界
			 public static double getDefiniteIntegralByRectangle1(double x0, double xn) {
			  //h: 步长
			  double h = abs(xn - x0) / n;
			  double sum = 0;
			  for (double xi = 0; xi <= xn; xi = xi + h) {
			   sum += f(xi + h) * h;
			  }
			  return sum;
			 }

			 // 矩形法求定积分, 左边界
			 public static double getDefiniteIntegralByRectangle2(double x0, double xn) {
			  double h = abs(xn - x0) / n;
			  double sum = 0;
			  for (double xi = 0; xi <= xn; xi = xi + h) {
			   sum += f(xi) * h;
			  }
			  return sum;
			 }

			 /**
			  * 测试定积分
			  */
			 public static void main(String[] args) {
			  System.out.println(getDefiniteIntegralByTrapezium(0, 1));
			  System.out.println(getDefiniteIntegralByRectangle1(0, 1));
			  System.out.println(getDefiniteIntegralByRectangle2(0, 1));
			 }

			}
