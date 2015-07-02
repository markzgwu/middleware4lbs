package org.tools.forLog;

import java.util.logging.Logger;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

public interface ILog {
	Logger logger = Logger.getAnonymousLogger();
	/*
	final Logger logger = LoggerFactory.getLogger(
		(
			new Object() {  
				//静态方法中获取当前类名  
		        public String getClassName() {  
		            String className = this.getClass().getName(); 
		            //return className;
		            return className.substring(0, className.lastIndexOf('$'));  
		        }  
			}.getClassName()
		)
	);
	*/
}
