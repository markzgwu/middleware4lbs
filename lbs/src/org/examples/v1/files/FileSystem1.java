package org.examples.v1.files;
import java.io.File;
import java.util.ArrayList;

import org.parameters.I_constant;
public class FileSystem1 {
private static ArrayList<String> filelist = new ArrayList<String>(); 

public static void main(String[] args) {
 
 long a = System.currentTimeMillis();
 refreshFileList(I_constant.default_userreq_folder);
 System.out.println(System.currentTimeMillis() - a);
}

public static void refreshFileList(String strPath) { 
 File dir = new File(strPath); 
 File[] files = dir.listFiles(); 
 
 if (files == null) 
     return; 
 for (int i = 0; i < files.length; i++) { 
     if (files[i].isDirectory()) { 
         refreshFileList(files[i].getAbsolutePath()); 
     } else { 
         String strFileName = files[i].getAbsolutePath().toLowerCase();
         System.out.println(strFileName);
         filelist.add(files[i].getAbsolutePath());                    
     } 
 } 
}
}
