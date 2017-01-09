package org.examples.v1.mina.example;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;

public class FileUploadHandler extends IoHandlerAdapter {

 private BufferedOutputStream out;

 private int count;

 private String fileName = "D:/log/test.jpg";

 private static final Log log = LogFactory.getLog(FileUploadHandler.class);


 public void sessionOpened(IoSession session) throws Exception {
 System.out.println("server open");
 }

 public void exceptionCaught(IoSession session, Throwable cause)
 throws Exception {
 System.out.println("exception");
 session.close(true);
 super.exceptionCaught(session, cause);
 }

 public void messageReceived(IoSession session, Object message) {
 System.out.println("server received");

 try {
 if (message instanceof FileUploadRequest) {
                 //FileUploadRequest Ϊ���ݹ�����ʹ�õ�DO��
 FileUploadRequest request = (FileUploadRequest) message;
 System.out.println(request.getFilename());
 if (out == null) {
 //�½�һ���ļ��������BufferedOutputStream����㶨�����ļ���λ��
 out = new BufferedOutputStream(new FileOutputStream(
 "D:/log/" + request.getFilename()));
 out.write(request.getFileContent());
 } else {
 out.write(request.getFileContent());
 }
 count += request.getFileContent().length;

 } else if (message instanceof String) {
 if (((String)message).equals("finish")) {
 System.out.println("size is"+count);
 //�����ǽ����ļ������Ҫ����flush��close���򴫵ݵ��ļ���������
 out.flush();
 out.close();
 //��ִ�ͻ�����Ϣ���ϴ��ļ��ɹ�
 session.write("success");
 }
 }

 } catch (Exception e) {
 e.printStackTrace();
 }
 }

 public void sessionClosed(IoSession session) throws Exception {
 System.out.println("server session close");
 }
}

