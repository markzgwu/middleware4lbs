package org.examples.v1.mina.example;

import java.net.InetSocketAddress;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.serialization.ObjectSerializationCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;


public class Main {
 private static final int PORT = 8080;

 public static void main(String[] args) throws Exception {
 //����˵�ʵ��
 NioSocketAcceptor accept=new NioSocketAcceptor();
 //���filter��codecΪ���л���ʽ������Ϊ�������л���ʽ������ʾ���ݵ��Ƕ���
 accept.getFilterChain().addLast("codec",
 new ProtocolCodecFilter(new ObjectSerializationCodecFactory()));
                //���filter����־��Ϣ
 accept.getFilterChain().addLast("logging", new LoggingFilter());
 //���÷���˵�handler
 accept.setHandler(new FileUploadHandler());
 //��ip
 accept.bind(new InetSocketAddress(PORT));
        
        System.out.println("upload  server started."); 
 }
}

