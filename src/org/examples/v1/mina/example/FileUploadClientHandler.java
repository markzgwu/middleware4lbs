package org.examples.v1.mina.example;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;


public class FileUploadClientHandler extends IoHandlerAdapter {

 public void sessionOpened(IoSession session) throws Exception {
 System.out.println("client open");
 }

 public void sessionClosed(IoSession session) throws Exception {
 System.out.println("client session close");
 }

 public void messageReceived(IoSession session, Object message)
 throws Exception {
 System.out.println("thr result is" + message);
 }
}
