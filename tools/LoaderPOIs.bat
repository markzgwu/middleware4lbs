@echo off  
set CLASSPATH=\%JAVA_HOME%\bin\;..\build\classes;  
set LIB_PATH=..\WebContent\WEB-INF\lib
rem echo %LIB_PATH%
set CLASSPATH=%CLASSPATH%;%JAVA_HOME%\lib;
set CLASSPATH=%CLASSPATH%;%LIB_PATH%\*.jar

java  org.assistants.dbsp.baidumaps.Program_Entry
  
pause 