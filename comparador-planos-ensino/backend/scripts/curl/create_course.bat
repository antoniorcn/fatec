@echo off
setlocal

set "COURSE_NAME=%~1"
if "%COURSE_NAME%"=="" set "COURSE_NAME=Engenharia de Software"

set "COURSE_DESCRIPTION=%~2"
if "%COURSE_DESCRIPTION%"=="" set "COURSE_DESCRIPTION=Curso exemplo criado via script."

echo Enviando curso "%COURSE_NAME%" para http://localhost:8080/api/courses ...
curl -i -X POST "http://localhost:8080/api/courses" ^
  -H "Content-Type: application/json" ^
  -d "{\"name\":\"%COURSE_NAME%\",\"description\":\"%COURSE_DESCRIPTION%\"}"

echo.
echo Pronto! Ajuste os parametros passando nome e descricao entre aspas, se desejar.

endlocal

