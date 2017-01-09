package org.examples.v1.mina.example1;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.LineDelimiter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

public class MinaServer {
	private static final int PORT = 9725;
	public static void main(String[] args) throws Exception{
		// ����һ����������server�˵�Socket����Ϊ�����Ƿ����������IoAcceptor
		IoAcceptor acceptor = new NioSocketAcceptor();
		// ���һ����־������
		acceptor.getFilterChain().addLast("logger", new LoggingFilter());
		// ���һ�����������
		acceptor.getFilterChain().addLast("codec",
				new ProtocolCodecFilter(new TextLineCodecFactory(Charset.forName("UTF-8"),
						LineDelimiter.WINDOWS.getValue(),
						LineDelimiter.WINDOWS.getValue())));

		// ��ҵ������,��δ���Ҫ��acceptor.bind()����֮ǰִ�У���Ϊ���׽���֮��Ͳ���������Щ׼��
		acceptor.setHandler(new MinaServerHanlder());
		// ���ö�ȡ���ݵĻ�������С
		acceptor.getSessionConfig().setReadBufferSize(2048);
		acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 10);
		// ��һ�������˿�
		acceptor.bind(new InetSocketAddress(PORT));
	}
}
