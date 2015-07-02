package org.examples.v1.files;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class testFile {

  public static void main(String[] args) {
   //调用方法（对文件的操作）
   operateFile("F:/test.txt");
   // System.out.println(time);
  }
  public static void operateFile(String strPath) {
  try {
   File file = new File(strPath);
   //判断文件是否存在
   if (file.exists()) {
    System.out.println("exit " + file.getPath());
   } else {
    System.out.println("不存在");
   }
   //判断是否为目录
   if (file.isDirectory()) {
    System.out.println("首次判断，该文件是目录且存在！" + file.getPath());
    File[] fileList = file.listFiles();
    for (int i = 0; i < fileList.length; i++) {
     if (fileList[i].isDirectory()) {
      System.out.println("子目录名：" + fileList[i].getName());
      System.out.print("子目录" + i + ":");
      operateFile(fileList[i].getPath());
     } /*else if (fileList[i].isFile()) {
      System.out.println("目录下该文件是文件且存在！" + file.getName());
     } else {
      // 不存在，创建文件
      //FileOutputStream fos = new FileOutputStream(strPath,true);
      // System.out.println("文件创建成功！");
      //System.out.println("全路径："+file.getAbsolutePath());
      //System.out.println("文件不存在："+fileList[i].getName());
     }*/
    }
    //判断是否为文件
   } else if (file.isFile()) {
    System.out.println("首次判断，该文件是文件且存在！" + file.getName());
    //追加文件内容,每次追加一条，
    /*BufferedWriter bw = new BufferedWriter(new FileWriter(file.getAbsolutePath(),true));
    bw.write("序号1|姓名|金额|备注1|备注2|");
    bw.newLine();
    bw.flush();
    bw.close();
    System.out.println("追加内容成功");*/
    
    //读文件内容
    /*int num = 0;
    BufferedReader br = new BufferedReader(new FileReader(file.getAbsolutePath()));
    String str = null;
    while( (str=br.readLine()) != null ){
     System.out.println(str);
     num++;
    }
    System.out.println("读取文件总行数："+num);*/
    
    //修改文件内容(可以先删除，在增加)
    
    //删除一行内容（java本身没有删除的方法，本方法通过先读取文件的内容（需删除的行数除外），放到list中，在重新写入）
    /*int line = 2;
    int num = 0;
    BufferedReader br = new BufferedReader(new FileReader(file.getAbsolutePath()));
    String str = null;
    List list = new ArrayList();
    while( (str=br.readLine()) != null ){
     ++num;
     System.out.println(num+"行："+str);
     if( num == line )
      continue;
     list.add(str);
    }
    System.out.println("list size:"+list.size());
    BufferedWriter bw = new BufferedWriter(new FileWriter(file.getAbsolutePath()));
    for( int i=0;i<list.size();i++ ){
     System.out.println("list["+i+"]"+list.get(i));
     bw.write(list.get(i).toString());
     bw.newLine();
    }
    bw.flush();
    bw.close();
    System.out.println("删除成功");*/
    
    //删除文件
     //file.delete();
     //System.out.println("文件删除成功！");
   } else {
    //当文件不存在时，新建
    System.out.println("该文件不存在");
    String name = file.getName();
    System.out.println("name:" + name);
    if (name.trim().toLowerCase().endsWith(".txt")) {
     System.out.println(".txt文件");
     // 不存在，创建文件 (先创建目录， file.mkdirs();)
     //System.out.println("父路径："+file.getParent());
     //按照父路径 创建目录，然后在该目录下创建文件
     File file1 = new File(file.getParent());
     file1.mkdirs();
     //System.out.println("根据父目录创建目录成功！");
     //创建文件
     FileOutputStream fos = new FileOutputStream(strPath,
       true);
     System.out.println("文件创建成功！");
    }else{
     System.out.println("非.txt文件");
     //创建多级目录(根据参数的路径格式)
     file.mkdirs();
    }
    
   }
  }catch(FileNotFoundException e) {
   System.out.println("找不到指定文件");
   System.exit(-1);
  }catch (Exception e1) {
   System.out.println("error:" + e1);
  }
 }
}
