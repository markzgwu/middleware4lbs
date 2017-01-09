package org.examples.v3.mina.server;

	import org.apache.mina.core.service.IoHandlerAdapter;
	import org.apache.mina.core.session.IdleStatus;
	import org.apache.mina.core.session.IoSession;

	public class HelloWorldServerHandler extends IoHandlerAdapter
	{
	    /**
	     * Trap exceptions.
	     */
	    @Override
	    public void exceptionCaught( IoSession session, Throwable cause ) throws Exception
	    {
	        cause.printStackTrace();
	    }

	    /**
	     * If the message is 'quit', we exit by closing the session. Otherwise,
	     * we return the message.
	     */
	    @Override
	    public void messageReceived( IoSession session, Object message ) throws Exception
	    {
	        String str = message.toString();
	        
	        if( str.trim().equalsIgnoreCase("quit") ) {
	            // "Quit" ? let's get out ...
	            session.close(true);
	            return;
	        }
	        
	        System.out.println("Received message:"+str);

	        // Send the "Hello, I am Server!" back to the client
	        String helloWorld = "Hello, I am Server!";
	        session.write(helloWorld);
	        System.out.println("Message written...");
	    }

	    /**
	     * On idle, we just write a message on the console
	     */
	    @Override
	    public void sessionIdle( IoSession session, IdleStatus status ) throws Exception
	    {
	        System.out.println( "IDLE " + session.getIdleCount( status ));
	    }
	}

