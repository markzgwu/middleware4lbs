package me.projects.QP.methods.prediction;



public class StrictMatcher extends AbsMatcher {
	
	//cellid1 是否是 轨迹中经过的位置
	public int isPast(String cellid1, final String[] onepath){
		int r = 0;
		for(int i=0;i<onepath.length;i++){
			if(cellid1.equals(onepath[i])){
				r=1;
				break;
			}
		}
		return r;
	}

	public int isHistoryAndCellid(final String[] History,final String Xf,final String[] onepath){
		int r = 0;
		if(History.length!=(onepath.length-1)){
			return 0;
		}
		
		if(!Xf.equals(onepath[onepath.length-1])){
			return 0;
		}
		
		for(int i=0;i<History.length;i++){
			if(!History[i].equals(onepath[i])){
				return 0;
			}
		}
		r=1;
		return r;
	}
	
	//history是否是轨迹的一部分
	public int isHistory(final String[] History,final String[] onepath){
		int r = 0;
		if(History.length!=(onepath.length-1)){
			return 0;
		}
		for(int i=0;i<History.length;i++){
			if(!History[i].equals(onepath[i])){
				return 0;
			}
		}
		r=1;
		return r;
	}
	
	public static void main(String[] args) {
		String[] onepath={"C0000","C0001","C0002"};
		String Xf = "C0002";
		AbsMatcher a = new PartMatcher();
		//String[] History = {"C0002"};
		System.out.println(a.isHistory(new String[]{"C0000"},onepath));
		System.out.println(a.isHistory(new String[]{"C0001"},onepath));
		System.out.println(a.isHistory(new String[]{"C0002"},onepath));
		System.out.println(a.isHistory(new String[]{"C0001","C0002"},onepath));
		System.out.println(a.isHistory(new String[]{"C0000","C0001"},onepath));
		System.out.println(a.isHistory(new String[]{"C0000","C0001","C0002"},onepath));
		
		System.out.println(a.isHistoryAndCellid(new String[]{"C0000","C0001"},Xf,onepath));
		
	}

	@Override
	public boolean isRegExMatch(String[] strings, String[] onepath) {
		// TODO Auto-generated method stub
		return false;
	}

}
