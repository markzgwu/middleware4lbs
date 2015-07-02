package me.projects.LISG.methods;

public final class MapsReaderToolV1 {

	static final public String[] catalog = new String[]{"others","restaurants","hotels","banks","hospatials","malls","officess","houses","public_places","schools","parks"};
	static final public String filter(String name){
		String catalog = "others";
		{
			final String[] pattern = {"����","��","�Ƶ�","����","����","��","��¥","ʳ��","����","��"};
			if(match(name,pattern)){
				catalog = "restaurants";
				return catalog;
			}
		}
		
		{
			final String[] pattern = {"����"};
			if(match(name,pattern)){
				catalog = "hotels";
				return catalog;
			}
		}		
		
		{
			final String[] pattern = {"����"};
			if(match(name,pattern)){
				catalog = "banks";
				return catalog;
			}
		}
		
		{
			final String[] pattern = {"ҽԺ"};
			if(match(name,pattern)){
				catalog = "hospatials";
				return catalog;
			}
		}
		
		{
			final String[] pattern = {"�̳�"};
			if(match(name,pattern)){
				catalog = "malls";
				return catalog;
			}
		}		
		
		{
			final String[] pattern = {"д��¥","�Ļ�����","�о�����","��˾"};
			if(match(name,pattern)){
				catalog = "officess";
				return catalog;
			}
		}		
		
		{
			final String[] pattern = {"����","��Ժ"};
			if(match(name,pattern)){
				catalog = "houses";
				return catalog;
			}
		}		
		
		{
			final String[] pattern = {"�㳡","�����","�����"};
			if(match(name,pattern)){
				catalog = "public_places";
				return catalog;
			}
		}
		
		{
			final String[] pattern = {"ѧУ","Сѧ","��ѧ","��ѧ","ѧԺ"};
			if(match(name,pattern)){
				catalog = "schools";
				return catalog;
			}
		}
		
		{
			final String[] pattern = {"��԰"};
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
