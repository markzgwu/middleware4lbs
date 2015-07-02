package org.examples.v1.mina.example1;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import org.apache.log4j.Logger;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoConnector;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.LineDelimiter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.transport.socket.nio.NioSocketConnector;


public class MinaClient {
	private static Logger log = Logger.getLogger(MinaClient.class);
	private static String HOST="localhost";
	private static int PORT=9725;
	public static void main(String[] args) {
		IoConnector conn = new NioSocketConnector();
		// �������ӳ�ʱʱ��
		conn.setConnectTimeoutMillis(30000L);
		// ��ӹ�����
		conn.getFilterChain().addLast("codec",
				        new ProtocolCodecFilter(new TextLineCodecFactory(Charset
						.forName("UTF-8"), LineDelimiter.WINDOWS.getValue(),
						LineDelimiter.WINDOWS.getValue())));
		// ���ҵ����handler
		conn.setHandler(new MinaClientHandler()); 
		IoSession session =null;
		try {
			ConnectFuture future = conn.connect(new InetSocketAddress(HOST, PORT));// ��������
			future.awaitUninterruptibly();// �ȴ����Ӵ������
			session = future.getSession();// ���session
			session.write("Hello World!");// ������Ϣ
		} catch (Exception e) {
			log.error("�ͻ��������쳣...", e);
		}

		session.getCloseFuture().awaitUninterruptibly();// �ȴ����ӶϿ�
		conn.dispose();

	}
}

