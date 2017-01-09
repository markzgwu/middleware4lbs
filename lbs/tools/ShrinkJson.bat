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

java -Djava.ext.dirs=%LIB_PATH% org.zgwu4lab.lbs.clients.url.loader.ShrinkJson %2

set PATH=%OLDPATH%