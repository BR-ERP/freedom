@echo "Publica��o do disco de instala��o Windows"
rem "call geradisco.bat"
rem "copy ..\disco\win\disco.zip .\freedom_1_1_3_0.zip"
ftp -s:ftpzip.ftp -A upload.sourceforge.net
rem "del .\freedom_1_1_3_0.zip /Q"
