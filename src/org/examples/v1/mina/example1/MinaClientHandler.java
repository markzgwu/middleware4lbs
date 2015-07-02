package org.examples.v1.mina.example1;

import org.apache.log4j.Logger;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

public class MinaClientHandler extends  IoHandlerAdapter {
	private static final Logger log = Logger.getLogger(MinaClientHandler.class);
	@Override
	public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
		cause.printStackTrace();
	}

	@Override
	public void messageReceived(IoSession session, Object message)throws Exception {
		log.info("client��Ϣ���յ�"+message);
	}

	@Override
	public void messageSent(IoSession session, Object message) throws Exception {
		log.info("client-��Ϣ�Ѿ�����"+message);
	}

	@Override
	public void sessionClosed(IoSession session) throws Exception {
		log.info("client -session�ر����ӶϿ�");
	}

	@Override
	public void sessionCreated(IoSession session) throws Exception {
		System.out.println("client -����session");
	}

	@Override
	public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
		System.out.println("client-ϵͳ������...");
	}

	@Override
	public void sessionOpened(IoSession session) throws Exception {
		System.out.println("client-session��");
	}
}

