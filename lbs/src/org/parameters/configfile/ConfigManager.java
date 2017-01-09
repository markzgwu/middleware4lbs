package org.parameters.configfile;

import java.io.FileInputStream;
import java.util.Properties;

public enum ConfigManager {
	
	INSTANCE;
	
	private final String default_config_filepath = "middleware4lbs.properties";
	private final String configfile = org.constants.Constants.Classpath+default_config_filepath;
	
	public final Properties config = init();
	
	private Properties init(){
		Properties prop = null;
		try {
			prop = Load();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return prop;
	}
	
	private Properties Load() throws Exception{
        Properties prop = new Properties();// ���Լ��϶���   
        FileInputStream fis = new FileInputStream(configfile);// �����ļ�������   
        prop.load(fis);// �������ļ���װ�ص�Properties������   
        fis.close();// �ر���   
        System.out.println("Loading "+configfile);
        return prop;
	}
	
	public void Message(){
		System.out.println("Loading "+configfile);
		config.list(System.out);
		/*
		for(String key:config.stringPropertyNames()){
			System.out.println(key);
		}
		*/
	}
	
	public String get(String key){
		return config.getProperty(key).trim();
	}
	
	public static void main(String[] args) throws Exception{
		ConfigManager.INSTANCE.Message();
	}

}
