package org.examples.v1.mina.example;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;

import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.serialization.ObjectSerializationCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

public class MainClient {

 private static final int PORT = 8080;

 /**
  * @param args
  * @throws IOException
  */
 public static void main(String[] args) throws Exception {
 //�ͻ��˵�ʵ��
 NioSocketConnector connector = new NioSocketConnector();
 connector.getFilterChain().addLast("codec",
 new ProtocolCodecFilter(new ObjectSerializationCodecFactory()));
 connector.getFilterChain().addLast("logging", new LoggingFilter());
 FileUploadClientHandler h = new FileUploadClientHandler();
 connector.setHandler(h);
 //������Ҫ���ϣ������޷����������readFuture����session�ж�ȡ������˷��ص���Ϣ��
 connector.getSessionConfig().setUseReadOperation(true);

 ConnectFuture cf = connector.connect(new InetSocketAddress("localhost",
 PORT));

 IoSession session;
 //�ȴ����ӳɹ�
 cf.awaitUninterruptibly();
 session = cf.getSession();

 System.out.println("client send begin");
 
 //�����ļ���ʼ
 String fileName = "test.jpg";
 FileInputStream fis = new FileInputStream(new File(fileName));
 byte[] a = new byte[1024 * 4];
 FileUploadRequest request = new FileUploadRequest();
 request.setFilename(fileName);
 request.setHostname("localhost");
 while (fis.read(a, 0, a.length) != -1) {
 request.setFileContent(a);
 //��session��д����Ϣ������˻��
 session.write(request);
 }
 //������ɵı�־
 session.write(new String("finish"));

 System.out.println("client send finished and wait success");
 //��������ȡ�÷���˵���Ϣ
 Object result = session.read().awaitUninterruptibly().getMessage();
 if (result.equals("success")) {
 System.out.println("success!");
 //�رտͻ���
 connector.dispose();
 }
 }
}