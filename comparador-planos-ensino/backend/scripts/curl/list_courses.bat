@echo off
setlocal

echo Listando cursos em http://localhost:8080/api/courses ...
curl -s "http://localhost:8080/api/courses" | python -m json.tool

endlocal

