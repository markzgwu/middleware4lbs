package me.projects.LISG.methods;

import java.util.ArrayList;

public final class MapsReaderToolV2{
	
	public boolean match(String name,String[] pattern){
		boolean match = false;
		for(String element:pattern){
			match = match || name.contains(element);
			//name.lastIndexOf(element);
		}
		return match;
	}
	
	public String filter(String name){
		String catalog = "null";
		for(LinkEntry element:SemanticLabelMapper){
			if(match(name,element.pattern)){
				catalog = element.catalog;
				break;
			}
			//name.lastIndexOf(element);
		}
		return catalog;
	}
	
	//static public final ArrayList<String> CatalogArray = initCatalogArray();
	public final ArrayList<String> initCatalogArray(){
		ArrayList<String> CatalogArray = new ArrayList<String>();
		for(LinkEntry element:SemanticLabelMapper){
			CatalogArray.add(element.catalog);
		}
		return CatalogArray;
	}
	
	public final double getSemanticLabelMapperConfigRiskLevel(String catalog){
		double catalogConfigRiskLevel = 0;
		for(LinkEntry e:SemanticLabelMapper){
			if(catalog.equals(e.catalog)){
				catalogConfigRiskLevel = e.risk;
				break;
			}
		}
		return catalogConfigRiskLevel;
	}
	public final ArrayList<LinkEntry> SemanticLabelMapper = initSemanticLabelMapper();
	public final ArrayList<LinkEntry> initSemanticLabelMapper(){
		ArrayList<LinkEntry> mySemanticLabelMapper = new ArrayList<LinkEntry>();
		double high_risk = 0.29;
		double med_risk = 0.05;
		double low_risk = 0.01;
		
		{
		final String catalog = "hospatials";
		final String[] pattern = {"医院"};
		mySemanticLabelMapper.add(new LinkEntry(catalog, pattern,high_risk));
		}
		
		{
		final String catalog = "nurseries";
		final String[] pattern = {"幼儿","儿童"};
		mySemanticLabelMapper.add(new LinkEntry(catalog, pattern,high_risk));
		}		
		
		{
		final String catalog = "restaurants";
		final String[] pattern = {"饭店","宴","酒店","饺子","肉","酒楼","食堂","餐厅","菜"};
		mySemanticLabelMapper.add(new LinkEntry(catalog, pattern,med_risk));
		}
		
		{
		final String catalog = "hotels";
		final String[] pattern = {"宾馆","招待","住宿"};
		mySemanticLabelMapper.add(new LinkEntry(catalog, pattern,med_risk));
		}
		
		{
		final String catalog = "banks";
		final String[] pattern = {"银行"};
		mySemanticLabelMapper.add(new LinkEntry(catalog, pattern,med_risk));
		}
		
		{
		final String catalog = "malls";
		final String[] pattern = {"商场"};
		mySemanticLabelMapper.add(new LinkEntry(catalog, pattern,med_risk));
		}
		
		{
		final String catalog = "offices";
		final String[] pattern = {"写字楼","文化中心","研究中心","公司"};
		mySemanticLabelMapper.add(new LinkEntry(catalog, pattern,med_risk));
		}
		
		{
		final String catalog = "houses";
		final String[] pattern = {"社区","号院"};
		mySemanticLabelMapper.add(new LinkEntry(catalog, pattern,med_risk));
		}
		
		{
		final String catalog = "schools";
		final String[] pattern = {"学校","小学","中学","大学","学院"};
		mySemanticLabelMapper.add(new LinkEntry(catalog, pattern,med_risk));
		}		
		
		{
		final String catalog = "museums";
		final String[] pattern = {"广场","博物馆","纪念馆"};
		mySemanticLabelMapper.add(new LinkEntry(catalog, pattern,med_risk));
		}		
		
		{
		final String catalog = "parks";
		final String[] pattern = {"公园"};
		mySemanticLabelMapper.add(new LinkEntry(catalog, pattern,low_risk));
		}
		
		{
		final String catalog = "others";
		final String[] pattern = {""};
		mySemanticLabelMapper.add(new LinkEntry(catalog, pattern,low_risk));
		}
		return mySemanticLabelMapper;
	}	
	
	public static void main(String[] args) {
		//System.out.println(initCatalogArray());

	}

}
