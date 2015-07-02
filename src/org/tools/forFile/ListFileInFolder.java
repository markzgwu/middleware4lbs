package org.tools.forFile;

import java.io.File;
import java.util.ArrayList;

import org.parameters.I_constant;

public final class ListFileInFolder {

	final private ArrayList<String> filelist = new ArrayList<String>(); 
	
	public final ArrayList<String> getFilelist() {
		return filelist;
	}

	public void refreshFileList(String strPath) { 
		 File dir = new File(strPath); 
		 File[] files = dir.listFiles(); 
		 
		 if (files == null) {
			 dir = null;
		     return; 
		 }

		 for (int i = 0; i < files.length; i++) { 
		     if (files[i].isDirectory()) { 
		         refreshFileList(files[i].getAbsolutePath()); 
		     } else { 
		         //String strFileName = files[i].getAbsolutePath().toLowerCase();
		         //System.out.println(strFileName);
		         filelist.add(files[i].getAbsolutePath());                    
		     } 
		 } 
		}
	
	public static void main(String[] args) {
		ListFileInFolder task = new ListFileInFolder();
		task.refreshFileList(I_constant.default_userreq_folder);
		for(String file:task.getFilelist()){
			System.out.println(file);
		}
	}

}
