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
		final String[] pattern = {"ҽԺ"};
		mySemanticLabelMapper.add(new LinkEntry(catalog, pattern,high_risk));
		}
		
		{
		final String catalog = "nurseries";
		final String[] pattern = {"�׶�","��ͯ"};
		mySemanticLabelMapper.add(new LinkEntry(catalog, pattern,high_risk));
		}		
		
		{
		final String catalog = "restaurants";
		final String[] pattern = {"����","��","�Ƶ�","����","��","��¥","ʳ��","����","��"};
		mySemanticLabelMapper.add(new LinkEntry(catalog, pattern,med_risk));
		}
		
		{
		final String catalog = "hotels";
		final String[] pattern = {"����","�д�","ס��"};
		mySemanticLabelMapper.add(new LinkEntry(catalog, pattern,med_risk));
		}
		
		{
		final String catalog = "banks";
		final String[] pattern = {"����"};
		mySemanticLabelMapper.add(new LinkEntry(catalog, pattern,med_risk));
		}
		
		{
		final String catalog = "malls";
		final String[] pattern = {"�̳�"};
		mySemanticLabelMapper.add(new LinkEntry(catalog, pattern,med_risk));
		}
		
		{
		final String catalog = "offices";
		final String[] pattern = {"д��¥","�Ļ�����","�о�����","��˾"};
		mySemanticLabelMapper.add(new LinkEntry(catalog, pattern,med_risk));
		}
		
		{
		final String catalog = "houses";
		final String[] pattern = {"����","��Ժ"};
		mySemanticLabelMapper.add(new LinkEntry(catalog, pattern,med_risk));
		}
		
		{
		final String catalog = "schools";
		final String[] pattern = {"ѧУ","Сѧ","��ѧ","��ѧ","ѧԺ"};
		mySemanticLabelMapper.add(new LinkEntry(catalog, pattern,med_risk));
		}		
		
		{
		final String catalog = "museums";
		final String[] pattern = {"�㳡","�����","�����"};
		mySemanticLabelMapper.add(new LinkEntry(catalog, pattern,med_risk));
		}		
		
		{
		final String catalog = "parks";
		final String[] pattern = {"��԰"};
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
