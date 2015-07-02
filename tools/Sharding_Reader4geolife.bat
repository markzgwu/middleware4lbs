@echo off 
set OLDPATH=%PATH%
set PATH=%JAVA_HOME%\lib;%JAVA_HOME%\bin;%PATH%
echo %PATH%
echo.

set LIB_PATH=..\WebContent\WEB-INF\lib
echo %LIB_PATH%
echo.

set TOOL_PATH=..\tools
echo %TOOL_PATH%
echo.

set CLASSPATH=%JAVA_HOME%\bin;..\build\classes;  
set CLASSPATH=%TOOL_PATH%\*.jar;%LIB_PATH%\*.jar;%CLASSPATH%
echo %classpath%
echo.

REM java -Dcom.sun.management.jmxremote -Dcom.sun.management.jmxremote.authenticate=false -Djava.ext.dirs=%LIB_PATH% org.zgwu4lab.lbs.framework.client.userdb.Sharding_Reader4geolife

REM java -agentpath:C:\PROGRA~1\JPROFI~1\bin\WINDOW~1\jprofilerti.dll=port=8849 -Djava.ext.dirs=%LIB_PATH% org.zgwu4lab.lbs.framework.client.userdb.Sharding_Reader4geolife

REM java -Djava.ext.dirs=%LIB_PATH% org.zgwu4lab.lbs.framework.client.userdb.Sharding_Reader4geolife

set JAVA_OPTS=-Xms64M -Xmx256M
REM set PROBE=-agentpath:C:\PROGRA~1\JPROFI~1\bin\WINDOW~1\jprofilerti.dll=port=8849
set MAINCLASS=org.zgwu4lab.lbs.framework.assistant.userdb.Sharding_Reader4geolife
java %JAVA_OPTS% -Djava.ext.dirs=%LIB_PATH% %MAINCLASS% 170

set PATH=%OLDPATH%