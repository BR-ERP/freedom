@echo off
set execdirmd=%~f0
set removedmd=

:strip_modulo

set removedmd=%execdirmd:~-1%

set execdirmd=%execdirmd:~0,-1%

if NOT "%removedmd%"=="\" goto strip_modulo

SET FREEDOMMD=freedomgrh
SET FREEDOMCL=org.freedom.modulos.grh.FreedomGRH

set CMDENV=%execdirmd%\freedomenv 

set CMDFREEDOM=

call %CMDENV% %FREEDOMMD% %FREEDOMCL%

%CMDFREEDOM%

