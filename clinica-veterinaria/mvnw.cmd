@echo off
setlocal

set "WRAPPER_DIR=%~dp0.mvn\wrapper"
set "PROPS_FILE=%WRAPPER_DIR%\maven-wrapper.properties"

if not exist "%PROPS_FILE%" (
  echo Maven wrapper properties not found: %PROPS_FILE%
  exit /b 1
)

for /f "usebackq tokens=1,* delims==" %%A in ("%PROPS_FILE%") do (
  if "%%A"=="distributionUrl" set "DISTRIBUTION_URL=%%B"
)

if "%DISTRIBUTION_URL%"=="" (
  echo distributionUrl not found in %PROPS_FILE%
  exit /b 1
)

for %%F in ("%DISTRIBUTION_URL%") do set "ZIP_NAME=%%~nxF"
set "ZIP_BASENAME=%ZIP_NAME:-bin.zip=%"
set "MAVEN_BASE=%USERPROFILE%\.m2\wrapper\dists\%ZIP_BASENAME%"
set "ZIP_FILE=%MAVEN_BASE%\%ZIP_NAME%"
set "EXTRACT_DIR=%MAVEN_BASE%\%ZIP_BASENAME%"
set "MAVEN_CMD=%EXTRACT_DIR%\bin\mvn.cmd"

if not exist "%MAVEN_CMD%" (
  echo Downloading Maven from %DISTRIBUTION_URL%
  if not exist "%MAVEN_BASE%" mkdir "%MAVEN_BASE%"
  powershell -NoProfile -ExecutionPolicy Bypass -Command "Invoke-WebRequest -Uri '%DISTRIBUTION_URL%' -OutFile '%ZIP_FILE%'"
  if errorlevel 1 exit /b 1
  powershell -NoProfile -ExecutionPolicy Bypass -Command "Expand-Archive -LiteralPath '%ZIP_FILE%' -DestinationPath '%MAVEN_BASE%' -Force"
  if errorlevel 1 exit /b 1
)

"%MAVEN_CMD%" %*
