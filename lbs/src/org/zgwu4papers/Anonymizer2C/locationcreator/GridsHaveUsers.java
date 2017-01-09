package org.zgwu4papers.Anonymizer2C.locationcreator;

public final class GridsHaveUsers {
	
	final int x=5;
	final int y=5;
	GridHasUsers[][] GridHasUsersMatrix= new GridHasUsers[x][y];
	
	final int usernumber = 10;//用户数量
	
	public void initGrids(){
		
		for(int i=0;i<x;i++){
			for(int j=0;j<y;j++){
				 GridHasUsersMatrix[i][j] = new GridHasUsers();
			}
		}
	}	
	
	public void fillUserstoGrids(){

		for(int i=0;i<usernumber;i++){
			
			int x1 = RondomUtil.random(x);
			int y1 = RondomUtil.random(y);
			//System.out.println("adding a user "+i+" to grid"+x1+";"+y1);
			GridHasUsers currentGrid = GridHasUsersMatrix[x1][y1];
			currentGrid.Users.add(i);
			//System.out.println("adding a user "+i+" to grid"+x1+";"+y1);
		}
	}
	
	public void showGrids(){
		String output = "empty!";
		for(int i=0;i<x;i++){
			for(int j=0;j<y;j++){
				GridHasUsers grid = GridHasUsersMatrix[i][j];
				output = grid.showArraylist();
				System.out.println("Grid["+i+"]["+j+"]:"+output);
			}
		}
	}
	
	public static void main(String[] args) {
		GridsHaveUsers currentGrids = new GridsHaveUsers();
		currentGrids.initGrids();
		currentGrids.fillUserstoGrids();
		currentGrids.showGrids();
	}

}
