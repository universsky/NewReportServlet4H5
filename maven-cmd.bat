@echo off
title Maven脚本 @Guangjian %cd%
rem 0＝黑/1＝蓝/2＝绿/3＝浅绿/4＝红/5＝紫/6＝黄/7=白/8=灰/9=淡蓝/A＝淡绿/B=淡浅绿/C=淡红/D=淡紫/E=淡黄/F=亮白
color 0E
 
rem ################## 需要配置的项 ####################################################################################
set current=%cd%
rem 发布编译目录
set _DEPLOY=%cd%\deploy
rem 编译后的war包名
set _FILE= %cd%\war
rem 编译的目标war包完整路径
set _TARGET=%_DEPLOY%\target\%_FILE%
rem 开发环境antx的配置文件路径
set _ANTX_COFIG=%cd%\antx.properties
rem 如果配置了M2_HOME和JBOSS_HOME可以不配置以下两个变量
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
 
rem --------- 执行参数，取第一个执行参数作为执行命令的依据 --------
if not "%1" == "%*" goto :start
if /I "%1"=="/q" goto :quit
goto :start
 
rem ------------- 启动脚本 ----------------------------------------
:start
cls
cd %current%
echo                      ╭──────────────╮
echo        ╭──────┤       Maven 调用脚本       ├──────╮
echo        │            ╰──────────────╯            │
echo        │   下面只展示了常用功能，更多功能请查看帮助说明         │
echo        ╰--------------------------------------------------------╯
echo.
echo         1  清除/创建/编译/跳过测试/dev打包/安装
echo         2  清除eclipse编译代码，并构建eclipse工程
echo         3  创建/编译/测试/qa打包/安装
echo         4  清除/创建/编译/测试/pe打包/安装/
echo         5  上传ftp
echo         6  查看依赖包的树形结构
echo         7  测试
echo         8  定义命令
echo         Q  退出程序
echo        ==========================================================
 
set /P type=       请选择: [1],[2],[3],[4],[5],[6],[7],[8],[Q] ? 
 
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
echo ------------- Maven 创建/编译/跳过测试/打包 -----------------------
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
echo ------------- Maven 清除eclipse编译代码，并构建eclipse工程 -------------------
call mvn eclipse:clean
call mvn eclipse:eclipse -DdownloadSources=true
pause
goto :start
rem -------------  mvn eclipse:clean eclipse:eclipse End ----------------------------
 
rem ------------- mvn clean install assembly:assembly Start -------------------------
:goCleanInstallAssembly
cls
echo.
echo ------------- Maven 清除/创建/编译/测试/%type%打包/安装/分发打包 -------------------
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
 
rem ------------- ftp上传 start----------------------------------------------------
:goFtp
set /P upload_ftp=  是否需要上传FTP(Y/N) ?
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
rem ------------- ftp上传 end--------------------------------------------------------
 
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
set /P commond= 请输入命令：
echo.
start %commond%
echo.
goto :start
rem ------------- customer commond End --------------------------------------------
 
rem ------------- 退出脚本 --------------------------------------------------------
:quit
echo.