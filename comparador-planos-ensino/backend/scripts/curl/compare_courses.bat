@echo off
setlocal

if "%~1"=="" (
    echo Uso: compare_courses.bat ^<cursoA^> ^<cursoB^>
    exit /b 1
)

if "%~2"=="" (
    echo Uso: compare_courses.bat ^<cursoA^> ^<cursoB^>
    exit /b 1
)

set "COURSE_A=%~1"
set "COURSE_B=%~2"

echo Comparando curso %COURSE_A% com curso %COURSE_B% ...
curl -s "http://localhost:8080/api/comparisons/courses/%COURSE_A%/vs/%COURSE_B%" | python -m json.tool

endlocal

