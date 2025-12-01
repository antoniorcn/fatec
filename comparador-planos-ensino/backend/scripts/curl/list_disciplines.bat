@echo off
setlocal

set "COURSE_ID=%~1"

if "%COURSE_ID%"=="" (
    echo Listando todas as disciplinas...
    curl -s "http://localhost:8080/api/disciplines" | python -m json.tool
) else (
    echo Listando disciplinas do curso %COURSE_ID% ...
    curl -s "http://localhost:8080/api/disciplines?courseId=%COURSE_ID%" | python -m json.tool
)

endlocal

