package me.projects.LISG.methods;

public final class MapsReaderToolV1 {

	static final public String[] catalog = new String[]{"others","restaurants","hotels","banks","hospatials","malls","officess","houses","public_places","schools","parks"};
	static final public String filter(String name){
		String catalog = "others";
		{
			final String[] pattern = {"饭店","宴","酒店","宾馆","饺子","肉","酒楼","食堂","餐厅","菜"};
			if(match(name,pattern)){
				catalog = "restaurants";
				return catalog;
			}
		}
		
		{
			final String[] pattern = {"宾馆"};
			if(match(name,pattern)){
				catalog = "hotels";
				return catalog;
			}
		}		
		
		{
			final String[] pattern = {"银行"};
			if(match(name,pattern)){
				catalog = "banks";
				return catalog;
			}
		}
		
		{
			final String[] pattern = {"医院"};
			if(match(name,pattern)){
				catalog = "hospatials";
				return catalog;
			}
		}
		
		{
			final String[] pattern = {"商场"};
			if(match(name,pattern)){
				catalog = "malls";
				return catalog;
			}
		}		
		
		{
			final String[] pattern = {"写字楼","文化中心","研究中心","公司"};
			if(match(name,pattern)){
				catalog = "officess";
				return catalog;
			}
		}		
		
		{
			final String[] pattern = {"社区","号院"};
			if(match(name,pattern)){
				catalog = "houses";
				return catalog;
			}
		}		
		
		{
			final String[] pattern = {"广场","博物馆","纪念馆"};
			if(match(name,pattern)){
				catalog = "public_places";
				return catalog;
			}
		}
		
		{
			final String[] pattern = {"学校","小学","中学","大学","学院"};
			if(match(name,pattern)){
				catalog = "schools";
				return catalog;
			}
		}
		
		{
			final String[] pattern = {"公园"};
			if(match(name,pattern)){
				catalog = "parks";
				return catalog;
			}
		}		
		
		return catalog;
	}
	
	static public boolean match(String name,String[] pattern){
		boolean match = false;
		for(String element:pattern){
			match = match || name.contains(element);
			//name.lastIndexOf(element);
		}
		return match;
	}
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
