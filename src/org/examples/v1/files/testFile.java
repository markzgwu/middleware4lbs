package org.examples.v1.files;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class testFile {

  public static void main(String[] args) {
   //���÷��������ļ��Ĳ�����
   operateFile("F:/test.txt");
   // System.out.println(time);
  }
  public static void operateFile(String strPath) {
  try {
   File file = new File(strPath);
   //�ж��ļ��Ƿ����
   if (file.exists()) {
    System.out.println("exit " + file.getPath());
   } else {
    System.out.println("������");
   }
   //�ж��Ƿ�ΪĿ¼
   if (file.isDirectory()) {
    System.out.println("�״��жϣ����ļ���Ŀ¼�Ҵ��ڣ�" + file.getPath());
    File[] fileList = file.listFiles();
    for (int i = 0; i < fileList.length; i++) {
     if (fileList[i].isDirectory()) {
      System.out.println("��Ŀ¼����" + fileList[i].getName());
      System.out.print("��Ŀ¼" + i + ":");
      operateFile(fileList[i].getPath());
     } /*else if (fileList[i].isFile()) {
      System.out.println("Ŀ¼�¸��ļ����ļ��Ҵ��ڣ�" + file.getName());
     } else {
      // �����ڣ������ļ�
      //FileOutputStream fos = new FileOutputStream(strPath,true);
      // System.out.println("�ļ������ɹ���");
      //System.out.println("ȫ·����"+file.getAbsolutePath());
      //System.out.println("�ļ������ڣ�"+fileList[i].getName());
     }*/
    }
    //�ж��Ƿ�Ϊ�ļ�
   } else if (file.isFile()) {
    System.out.println("�״��жϣ����ļ����ļ��Ҵ��ڣ�" + file.getName());
    //׷���ļ�����,ÿ��׷��һ����
    /*BufferedWriter bw = new BufferedWriter(new FileWriter(file.getAbsolutePath(),true));
    bw.write("���1|����|���|��ע1|��ע2|");
    bw.newLine();
    bw.flush();
    bw.close();
    System.out.println("׷�����ݳɹ�");*/
    
    //���ļ�����
    /*int num = 0;
    BufferedReader br = new BufferedReader(new FileReader(file.getAbsolutePath()));
    String str = null;
    while( (str=br.readLine()) != null ){
     System.out.println(str);
     num++;
    }
    System.out.println("��ȡ�ļ���������"+num);*/
    
    //�޸��ļ�����(������ɾ����������)
    
    //ɾ��һ�����ݣ�java����û��ɾ���ķ�����������ͨ���ȶ�ȡ�ļ������ݣ���ɾ�����������⣩���ŵ�list�У�������д�룩
    /*int line = 2;
    int num = 0;
    BufferedReader br = new BufferedReader(new FileReader(file.getAbsolutePath()));
    String str = null;
    List list = new ArrayList();
    while( (str=br.readLine()) != null ){
     ++num;
     System.out.println(num+"�У�"+str);
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
    System.out.println("ɾ���ɹ�");*/
    
    //ɾ���ļ�
     //file.delete();
     //System.out.println("�ļ�ɾ���ɹ���");
   } else {
    //���ļ�������ʱ���½�
    System.out.println("���ļ�������");
    String name = file.getName();
    System.out.println("name:" + name);
    if (name.trim().toLowerCase().endsWith(".txt")) {
     System.out.println(".txt�ļ�");
     // �����ڣ������ļ� (�ȴ���Ŀ¼�� file.mkdirs();)
     //System.out.println("��·����"+file.getParent());
     //���ո�·�� ����Ŀ¼��Ȼ���ڸ�Ŀ¼�´����ļ�
     File file1 = new File(file.getParent());
     file1.mkdirs();
     //System.out.println("���ݸ�Ŀ¼����Ŀ¼�ɹ���");
     //�����ļ�
     FileOutputStream fos = new FileOutputStream(strPath,
       true);
     System.out.println("�ļ������ɹ���");
    }else{
     System.out.println("��.txt�ļ�");
     //�����༶Ŀ¼(���ݲ�����·����ʽ)
     file.mkdirs();
    }
    
   }
  }catch(FileNotFoundException e) {
   System.out.println("�Ҳ���ָ���ļ�");
   System.exit(-1);
  }catch (Exception e1) {
   System.out.println("error:" + e1);
  }
 }
}
