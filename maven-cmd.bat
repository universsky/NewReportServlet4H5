@echo off
title Maven�ű� @Guangjian %cd%
rem 0����/1����/2����/3��ǳ��/4����/5����/6����/7=��/8=��/9=����/A������/B=��ǳ��/C=����/D=����/E=����/F=����
color 0E
 
rem ################## ��Ҫ���õ��� ####################################################################################
set current=%cd%
rem ��������Ŀ¼
set _DEPLOY=%cd%\deploy
rem ������war����
set _FILE= %cd%\war
rem �����Ŀ��war������·��
set _TARGET=%_DEPLOY%\target\%_FILE%
rem ��������antx�������ļ�·��
set _ANTX_COFIG=%cd%\antx.properties
rem ���������M2_HOME��JBOSS_HOME���Բ�����������������
set _M2_HOME= %M2_HOME%
 
rem ####################################################################################################################
if not exist "%M2_HOME%" (
    set M2_ERROR=Error: M2_HOME not exist
    if exist "%_M2_HOME%" (
            set M2_ERROR=Warn: M2_HOME not exist, use %_M2_HOME% as M2_HOME
    )
)
 
if not "%M2_ERROR%"=="" (
    echo %M2_ERROR%
    pause
    goto :start
)
 
rem --------- ִ�в�����ȡ��һ��ִ�в�����Ϊִ����������� --------
if not "%1" == "%*" goto :start
if /I "%1"=="/q" goto :quit
goto :start
 
rem ------------- �����ű� ----------------------------------------
:start
cls
cd %current%
echo                      �q�����������������������������r
echo        �q��������������       Maven ���ýű�       ���������������r
echo        ��            �t�����������������������������s            ��
echo        ��   ����ֻչʾ�˳��ù��ܣ����๦����鿴����˵��         ��
echo        �t--------------------------------------------------------�s
echo.
echo         1  ���/����/����/��������/dev���/��װ
echo         2  ���eclipse������룬������eclipse����
echo         3  ����/����/����/qa���/��װ
echo         4  ���/����/����/����/pe���/��װ/
echo         5  �ϴ�ftp
echo         6  �鿴�����������νṹ
echo         7  ����
echo         8  ��������
echo         Q  �˳�����
echo        ==========================================================
 
set /P type=       ��ѡ��: [1],[2],[3],[4],[5],[6],[7],[8],[Q] ? 
 
if /I "%type%"=="1" set type=dev
if /I "%type%"=="2" set type=e
if /I "%type%"=="3" set type=qa
if /I "%type%"=="4" set type=pe
if /I "%type%"=="5" set type=f
if /I "%type%"=="6" set type=dt
if /I "%type%"=="7" set type=t
if /I "%type%"=="8" set type=c
if /I "%type%"=="dev" goto :goCleanInstallSkip
if /I "%type%"=="qa" goto :goCleanInstallAssembly
if /I "%type%"=="pe" goto :goCleanInstallAssembly
if /I "%type%"=="sb" goto :goCleanInstallAssembly
if /I "%type%"=="idc" goto :goCleanInstallAssembly
if /I "%type%"=="E" goto :goEclipse
if /I "%type%"=="F" goto :goFtp
if /I "%type%"=="DT" goto :goDependencyTree
if /I "%type%"=="T" goto :goTest
if /I "%type%"=="C" goto :goCommond
if /I "%type%"=="Q" goto :quit
goto :start
 
rem ------------- mvn -Dmaven.test.skip=true clean install Start ---------------------
:goCleanInstallSkip
cls
echo.
echo ------------- Maven ����/����/��������/��� -----------------------
echo copy %_ANTX_COFIG% %UserProfile%\%_ANTX_COFIG:~-15%
copy "%_ANTX_COFIG%" "%UserProfile%\%_ANTX_COFIG:~-15%"
call mvn clean install -Dmaven.test.skip=true
cd %_DEPLOY%
call mvn assembly:assembly
cd %current%
pause
goto :start
rem ------------- mvn -Dmaven.test.skip=true clean install End -----------------------
 
rem ------------- mvn eclipse:clean eclipse:eclipse Start ----------------------------
:goEclipse
cls
echo.
echo ------------- Maven ���eclipse������룬������eclipse���� -------------------
call mvn eclipse:clean
call mvn eclipse:eclipse -DdownloadSources=true
pause
goto :start
rem -------------  mvn eclipse:clean eclipse:eclipse End ----------------------------
 
rem ------------- mvn clean install assembly:assembly Start -------------------------
:goCleanInstallAssembly
cls
echo.
echo ------------- Maven ���/����/����/����/%type%���/��װ/�ַ���� -------------------
if not exist "%_DEPLOY%" (
    echo "%_DEPLOY% not exist"
) else (
    if not exist "%_ANTX_COFIG:~0,-11%_%type%%_ANTX_COFIG:~-11%" (
        echo "%_ANTX_COFIG:~0,-11%_%type%%_ANTX_COFIG:~-11% not exist"
    ) else (
        echo copy %_ANTX_COFIG:~0,-11%_%type%%_ANTX_COFIG:~-11% %UserProfile%\%_ANTX_COFIG:~-15%
        copy "%_ANTX_COFIG:~0,-11%_%type%%_ANTX_COFIG:~-11%" "%UserProfile%\%_ANTX_COFIG:~-15%"
        call mvn clean install
        cd %_DEPLOY%
        call mvn assembly:assembly
        cd %current%
        goto :goFtp
    )
)
pause
goto :start
rem ------------- mvn clean install assembly:assembly End ---------------------------
 
rem ------------- ftp�ϴ� start----------------------------------------------------
:goFtp
set /P upload_ftp=  �Ƿ���Ҫ�ϴ�FTP(Y/N) ?
set res=false
if /I "%upload_ftp%"=="yes" (
    set res=true
)
if /I "%upload_ftp%"=="y" (
    set res=true
)
if "%res%"=="true" (
    copy %_TARGET% .
    (echo open 110.75.5.128
    echo pubftp
    echo look
    echo prompt
    echo put %_FILE%
    echo bye) > ftp.tmp
    ftp -s:ftp.tmp
    del ftp.tmp
    del %_FILE%
    )
)
pause
goto :start
rem ------------- ftp�ϴ� end--------------------------------------------------------
 
rem ------------- mvn dependency:tree Start ---------------------------------------
:goDependencyTree
cls
echo.
call mvn dependency:tree
pause
goto :start
rem ------------- mvn dependency:tree End -----------------------------------------
 
rem ------------- mvn test Start ---------------------------------------
:goTest
cls
echo.
call mvn test
pause
goto :start
rem ------------- mvn test End -----------------------------------------
 
 
rem ------------- customer commond Start ------------------------------------------
:goCommond
cls
echo.
set /P commond= ���������
echo.
start %commond%
echo.
goto :start
rem ------------- customer commond End --------------------------------------------
 
rem ------------- �˳��ű� --------------------------------------------------------
:quit
echo.