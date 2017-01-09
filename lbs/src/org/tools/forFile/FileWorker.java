package org.tools.forFile;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

public final class FileWorker {
	final File file ;
	final FileWriter filewriter;
	final BufferedWriter bufferedwriter ;
	public FileWorker(final String filepath) throws Exception{
		file = new File(filepath);
		filewriter = new FileWriter(file, true);
	    bufferedwriter = new BufferedWriter(filewriter);		
	}
	
	public void append_nobuffer(final String content) throws Exception{
		filewriter.append(content);
		//filewriter.flush();
	}
	
	public void append_buffer(final String content) throws Exception{
		bufferedwriter.append(content);
		bufferedwriter.flush();
	}
	
	public void append_flush(final String content) throws Exception{
		bufferedwriter.append(content);
		bufferedwriter.flush();
		filewriter.flush();
	}
	
	public void close() throws Exception{
		bufferedwriter.close();
		filewriter.close();
	}
}
