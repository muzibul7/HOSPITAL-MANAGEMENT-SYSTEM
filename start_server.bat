@echo off
echo Compiling Java server...
javac SimpleHTTPServer.java
if %errorlevel% neq 0 (
    echo Compilation failed!
    pause
    exit /b %errorlevel%
)
echo Starting server...
java SimpleHTTPServer
pause 