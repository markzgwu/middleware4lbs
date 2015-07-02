package org.tools.forFile;

import java.io.*;

import org.parameters.I_constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class FileTool {
	private final static Logger logger = LoggerFactory.getLogger(FileTool.class);
	
	static FileWorker cur_fileworker=null;
	
	public static void main(String[] args) {
		String file1 = I_constant.default_POIDB_filepath;
		System.out.println(file1);
		String file2 = null;
		for(int i=0;i<3;i++){
			file2 = GenFileName(file1,"backup",i);
			logger.info(file2);
		}

	}
	
	public static boolean DeleteFile(String filepath){
		File f = new File(filepath);
		boolean b = f.delete();
		return b;
	}
	
	public static String GenFileName(String filepath,String postfix){
		return GenFileNameMode0(filepath,postfix);
	}
	
	public static String GenIndexNameFromDB(String dbfilepath){
		return GenFileNameMode0(dbfilepath,"index");
	}
	
	public static String GenFileName(String filepath,String postfix,int mode){
		String f = filepath;
		switch(mode){
		case 1:
			f = GenFileNameMode1(filepath,postfix);
			break;
		case 2:
			f = GenFileNameMode2(filepath,postfix);
			break;
		default:
			f = GenFileNameMode0(filepath,postfix);
		}
		return f;
	}
	
	private static String GenFileNameMode0(String filepath,String postfix){
		String newfilename = filepath+"."+postfix;
		return newfilename;
	}
	
	private static String[] NameAndExt(String fileName){
		String name = "";
		String extention = "";
		if(fileName.length()>0 && fileName!=null){  //--截取文件名
			int i = fileName.lastIndexOf(".");
			if(i>-1 && i<fileName.length()){
				name = fileName.substring(0, i); //--文件名
				extention = fileName.substring(i+1); //--扩展名
				}
		}		
		
		String[] NameAndExt = new String[2];
		
		NameAndExt[0] = name;
		NameAndExt[1] = extention;
		return NameAndExt;
	}
	
	private static String GenFileNameMode1(String filepath,String postfix){
		String[] ThisNameAndExt =  NameAndExt(filepath);
		String name = ThisNameAndExt[0];
		String extention = ThisNameAndExt[1];
		String newfilename = name+"_"+postfix+"."+extention;
		return newfilename;
	}
	
	private static String GenFileNameMode2(String filepath,String postfix){
		String[] ThisNameAndExt =  NameAndExt(filepath);
		String name = ThisNameAndExt[0];
		String extention = ThisNameAndExt[1];
		String newfilename = name+"."+postfix+"."+extention;
		return newfilename;
	}
	
	public static boolean RenameFile(String oldfilepath,String newfilepath){
		boolean b = false;
		final File oldf = new File(oldfilepath);
		if(oldf   !=   null)   { 
		    final File newf   =   new  File(newfilepath); 
		    b = oldf.renameTo(newf); 
		}
		return b;
	}
	
	public static BufferedReader getBufferedReader(final String filepath) throws Exception{
        final File file = new File(filepath);
        logger.debug("Getting Buffer Reader!");
        final BufferedReader reader = new BufferedReader(new FileReader(file));
        return reader;
	}
	
	public static BufferedWriter getBufferedWriter(final String filepath) throws Exception{
        File file = new File(filepath);
        FileWriter filewriter = new FileWriter(file, true);
        final BufferedWriter bufferedwriter = new BufferedWriter(filewriter); ;
        logger.debug("Getting Buffer Writer!");
        return bufferedwriter;
	}	
	
	
	
	public static void ReadJsonFileByLines(String filepath) throws Exception{
        final BufferedReader reader = getBufferedReader(filepath);
        String tempString = null;
        int line = 1;
        // 一次读入一行，直到读入null为文件结束
        while ((tempString = reader.readLine()) != null) {
            // 显示行号
            System.out.println("line " + line + ": " + tempString);
            line++;
        }
        reader.close();
	}   
    
	public static void saveManyLinestoFile(String filePath, String content){
		FileTool.appendFile(filePath,content);
	} 
    
	public static void saveOneLinetoFile(String filePath, String content){
		FileTool.appendFile(filePath,content+"\n");
	}
	
	public static FileWorker ObtainFileObj(String filePath) throws Exception{
		FileWorker fileworker = null;
		if(cur_fileworker==null){
			fileworker = new FileWorker(filePath);
		}else{
			fileworker = cur_fileworker;
		}
		return fileworker;
	}
	
	public static void appendFile(String filePath, String detail){
		try {
			appendFile_nobuffer(filePath, detail);
			//appendFile_buffer(filePath, detail);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void appendFile_nobuffer(String filePath, String detail) throws Exception {
        final FileWriter filewriter = new FileWriter(new File(filePath), true);
        filewriter.append(detail);
        filewriter.close();
	}	
	
	public static void appendFile_buffer(String filePath, String detail)throws Exception {
            final FileWorker fileworker = ObtainFileObj(filePath);
            fileworker.append_buffer(detail);
	}
		
}
