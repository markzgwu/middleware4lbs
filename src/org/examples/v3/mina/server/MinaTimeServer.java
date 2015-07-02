package org.examples.v3.mina.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;


public class MinaTimeServer {

private static final int PORT = 8888;

    public static void main(String[] args) throws IOException {
        // Create the acceptor
        IoAcceptor acceptor = new NioSocketAcceptor();
        
        // Add two filters : a logger and a codec
        acceptor.getFilterChain().addLast( "logger", new LoggingFilter() );
        acceptor.getFilterChain().addLast( "codec", new ProtocolCodecFilter( new TextLineCodecFactory( Charset.forName( "UTF-8" ))));
   
        // Attach the business logic to the server
        acceptor.setHandler( new HelloWorldServerHandler() );

        // Configurate the buffer size and the iddle time
        acceptor.getSessionConfig().setReadBufferSize( 2048 );
        acceptor.getSessionConfig().setIdleTime( IdleStatus.BOTH_IDLE, 10 );
        
        // And bind !
        acceptor.bind( new InetSocketAddress(PORT) );
    }
}
