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
		// 创建一个非阻塞的server端的Socket，因为这里是服务端所以用IoAcceptor
		IoAcceptor acceptor = new NioSocketAcceptor();
		// 添加一个日志过滤器
		acceptor.getFilterChain().addLast("logger", new LoggingFilter());
		// 添加一个编码过滤器
		acceptor.getFilterChain().addLast("codec",
				new ProtocolCodecFilter(new TextLineCodecFactory(Charset.forName("UTF-8"),
						LineDelimiter.WINDOWS.getValue(),
						LineDelimiter.WINDOWS.getValue())));

		// 绑定业务处理器,这段代码要在acceptor.bind()方法之前执行，因为绑定套接字之后就不能再做这些准备
		acceptor.setHandler(new MinaServerHanlder());
		// 设置读取数据的缓冲区大小
		acceptor.getSessionConfig().setReadBufferSize(2048);
		acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 10);
		// 绑定一个监听端口
		acceptor.bind(new InetSocketAddress(PORT));
	}
}
