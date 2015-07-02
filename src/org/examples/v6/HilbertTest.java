package org.examples.v6;

public final class HilbertTest {
	
	public static void main(String[] args) {
		int n = 2;
		Hilbert hilbertdata = new Hilbert(n);
		hilbertdata.hilbertCurve();
		
		for(int i = 0;i<16;i++){
			int[] grid = hilbertdata.hilbertDecoding(i);
			System.out.println(grid[0]+";"+grid[1]);
		}
		
		for(int i = 0;i<4;i++){
			for(int j = 0;j<4;j++){
			int hid = hilbertdata.hilbertCurve(i, j);
				System.out.println(hid);
			}
		}

	}

}
