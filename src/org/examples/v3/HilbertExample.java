package org.examples.v3;

public final class HilbertExample {

	class Hilbert{ //Hilbert������ߵ�����˳��ʵ���˿ռ����ڽ������Ҫ��
	       int[][] grid;   //��ά��������
	       int rank;       //����
	       Hilbert(int n) //���캯��
	       {
	           rank = n;
	           grid = new int[(int)Math.pow(2,n)][(int)Math.pow(2,n)]; //���ݽ�������������
	       }
	       void hilbertCurve() //����Hilbert�������
	       {
	             for(int i=1;i<=rank;i++)
	             {
	                if(i==1)
	                {
	                   grid[0][0]=0;
	                   grid[0][1]=3;
	                   grid[1][0]=1;
	                   grid[1][1]=2;
	                }
	                else
	                {
	                   for(int j=0;j<(int)Math.pow(2,(i-1));j++)
	                      for(int k=0;k<(int)Math.pow(2,(i-1));k++)
	                      {
	grid[j+(int)Math.pow(2,(i-1))][k] = (int)(grid[j][k]+Math.pow(4,(i-1))); //��1���޶�Ӧ������
	                                              grid[j+(int)Math.pow(2,(i-1))][k+(int)Math.pow(2,(i-1))] = (int)(grid[j][k]+2*Math.pow(4,(i-1))); //��2���޶�Ӧ������
	                                              grid[(int)(Math.pow(2,(i-1))-1)-k][(int)(Math.pow(2,i)-1)-j] = (int)(grid[j][k]+3*Math.pow(4,(i-1)));//�������޶�Ӧ������
	                      }
	                   for(int j=0;j<(int)Math.pow(2,(i-1));j++) //��0���޶�Ӧ������
	                      for(int k=0;k<=j;k++)
	                       {
	                          int temp = grid[j][k];
	                          grid[j][k] = grid[k][j];   //��б�Խ��߷�ת
	                          grid[k][j] = temp;
	                       }
	                }
	             }
	         
	       }
	 
	       int hilbertCurve(int i,int j) //�����������꣨x,y��,����Hilbert��������
	       {
	           hilbertCurve();
	           int value = grid[i][j];
	           return value;
	       }
	      
	       int[][] getHilbertGrid()
	       {
	           return grid;
	       }
	      
	       int[] hilbertDecoding(int code) //����Hilbert��ţ������������꣨x,y��
	       {
	           int[] decode = new int[2];
	           for(int i=0;i<(int)Math.pow(2,rank);i++)
	               for(int j=0;j<(int)Math.pow(2,rank);j++)
	               {
	                   if(grid[i][j] != code)
	                       continue;
	                   else
	                   {
	                       decode[0] = i;
	                       decode[1] = j;
	                       break;
	                   }
	               }
	           return decode;
	       }
	    }	
	
	public static void main(String[] args) {

	}

}
