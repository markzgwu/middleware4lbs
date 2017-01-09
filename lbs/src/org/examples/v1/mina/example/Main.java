package org.examples.v1.mina.example;

import java.net.InetSocketAddress;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.serialization.ObjectSerializationCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;


public class Main {
 private static final int PORT = 8080;

 public static void main(String[] args) throws Exception {
 //服务端的实例
 NioSocketAcceptor accept=new NioSocketAcceptor();
 //添加filter，codec为序列化方式。这里为对象序列化方式，即表示传递的是对象。
 accept.getFilterChain().addLast("codec",
 new ProtocolCodecFilter(new ObjectSerializationCodecFactory()));
                //添加filter，日志信息
 accept.getFilterChain().addLast("logging", new LoggingFilter());
 //设置服务端的handler
 accept.setHandler(new FileUploadHandler());
 //绑定ip
 accept.bind(new InetSocketAddress(PORT));
        
        System.out.println("upload  server started."); 
 }
}

