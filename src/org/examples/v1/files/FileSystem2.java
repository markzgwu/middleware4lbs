package org.examples.v1.files;

import java.io.File;
import java.util.LinkedList;

import org.parameters.I_constant;
public class FileSystem2 {
    public static void main(String[] args) {
        
        long a = System.currentTimeMillis();
        
        LinkedList<String> list = new LinkedList<String>();
        File dir = new File(I_constant.default_userreq_folder);
        File file[] = dir.listFiles();
        for (int i = 0; i < file.length; i++) {
            if (file[i].isDirectory())
                list.add(file[i].getAbsolutePath());
            else
                System.out.println(file[i].getAbsolutePath());
        }
        String tmpstr;
        File tmp;
        while (!list.isEmpty()) {
            tmpstr = list.removeFirst();
            tmp = new File(tmpstr);
            if (tmp.isDirectory()) {
                file = tmp.listFiles();
                if (file == null)
                    continue;
                for (int i = 0; i < file.length; i++) {
                    if (file[i].isDirectory())
                        list.add(file[i].getAbsolutePath());
                    else
                        System.out.println(file[i].getAbsolutePath());
                }
            } else {
                System.out.println(tmp.getAbsolutePath());
            }
        }
        
        System.out.println(System.currentTimeMillis() - a);
    }
}