package org.examples.v1.mina.example1;
import java.util.Date;

import org.apache.log4j.Logger;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

public class MinaServerHanlder extends IoHandlerAdapter {
	public static Logger logger = Logger.getLogger(MinaServerHanlder.class);
	@Override
	public void exceptionCaught(IoSession session, Throwable cause)throws Exception {
		cause.printStackTrace();
	}

	@Override
	public void messageReceived(IoSession session, Object message)throws Exception {
		 String str = message.toString();
	        if( str.trim().equalsIgnoreCase("quit") ) {
	            session.close(Boolean.TRUE);
	            return;
	        }
	        Date date = new Date();
	        session.write( date.toString() );
		logger.info("server -消息已经接收到!"+message);
	}

	@Override
	public void messageSent(IoSession session, Object message) throws Exception {
		logger.info("server -消息已经发出");
	}

	@Override
	public void sessionClosed(IoSession session) throws Exception {
		logger.info("server-session关闭连接断开");
	}

	@Override
	public void sessionCreated(IoSession session) throws Exception {
		logger.info("server-session创建，建立连接");
	}

	@Override
	public void sessionIdle(IoSession session, IdleStatus status)throws Exception {
		logger.info("server-服务端进入空闲状态..");
	}

	@Override
	public void sessionOpened(IoSession session) throws Exception {
		logger.info("server-服务端与客户端连接打开...");
	}
}
