package org.Hilbert;
//package net.zhoubian.app.util;

public class HilbertBigint {
	private int[][] grid; //��ά��������
	private int[][] series; //����hilbert��Ŷ�Ӧ�ĺ����꣬�����������
	int rank; //����
	public HilbertBigint(int n){
		this.rank = n;
		grid = new int[(int)Math.pow(2, n)][(int)Math.pow(2, n)];
		series = new int[(int)Math.pow(2, 2*n)][2];
		hilbertCurve();
	}
	public void hilbertCurve(){
		//����hilbert�������
		for(int i=1;i<=rank;i++){
			if(i==1){
				grid[0][0] = 0;
				series[0] = new int[]{0,0};
				grid[0][1] = 3;
				series[3] = new int[]{0,1};
				grid[1][0] = 1;
				series[1] = new int[]{1,0};
				grid[1][1] = 2;
				series[2] = new int[]{1,1};
			}else{
				for(int j=0;j<(int)Math.pow(2, (i-1));j++){
					for(int k=0;k<(int)Math.pow(2, (i-1));k++){
						//��1���޶�Ӧ������
						grid[j+(int)Math.pow(2,(i-1))][k] = (int)(grid[j][k]+Math.pow(4,(i-1)));
						series[(int)(grid[j][k]+Math.pow(4,(i-1)))] = new int[]{j+(int)Math.pow(2,(i-1)),k};
						//��2���޶�Ӧ������
                        grid[j+(int)Math.pow(2,(i-1))][k+(int)Math.pow(2,(i-1))] = (int)(grid[j][k]+2*Math.pow(4,(i-1)));
                        series[(int)(grid[j][k]+2*Math.pow(4,(i-1)))] = new int[]{j+(int)Math.pow(2,(i-1)),k+(int)Math.pow(2,(i-1))};
                      //�������޶�Ӧ������
                        grid[(int)(Math.pow(2,(i-1))-1)-k][(int)(Math.pow(2,i)-1)-j] = (int)(grid[j][k]+3*Math.pow(4,(i-1)));
                        series[(int)(grid[j][k]+3*Math.pow(4,(i-1)))] = new int[]{(int)(Math.pow(2,(i-1))-1)-k,(int)(Math.pow(2,i)-1)-j};
					}
				}
				for(int j=0;j<(int)Math.pow(2,(i-1));j++){
					for(int k=0;k<=j;k++){
						int temp = grid[j][k];
						grid[j][k] = grid[k][j];
						series[grid[k][j]] = new int[]{j,k};
						grid[k][j] = temp;
						series[temp] = new int[]{k,j};
					}
				}
			}
		}
	}
	//������������(x,y)������Hilbert��������
	public int hilbertCurve(int i,int j){
		int value = grid[i][j];
		return value;
	}
	public int[][] getHilbertGrid(){
		return grid;
	}
	//����Hilbert��ţ�������������(x,y)
	public int[] hilbertDecoding(int code){
//		int[] decode = new int[2];
//		for(int i=0;i<(int)Math.pow(2, rank);i++){
//			for(int j=0;j<(int)Math.pow(2, rank);j++){
//				if(grid[i][j]!=code)
//					continue;
//				else{
//					decode[0] = i;
//					decode[1] = j;
//					break;
//				}
//			}
//		}
//		return decode;
		return series[code];
	}
	public void print(){
		for(int i=0;i<(int)Math.pow(2, rank);i++){
			for(int j=0;j<(int)Math.pow(2, rank);j++){
				if(j==0){
					System.out.println("");
					System.out.print(grid[i][j]);
				}else{
					System.out.print(","+grid[i][j]);
				}
			}
		}
	}
}
