@echo off
setlocal

if "%~1"=="" (
    echo Uso: create_discipline.bat ^<courseId^> ["Titulo da disciplina"] ["COD123"]
    exit /b 1
)

set "COURSE_ID=%~1"
set "DISCIPLINE_TITLE=%~2"
if "%DISCIPLINE_TITLE%"=="" set "DISCIPLINE_TITLE=Algoritmos e Estruturas de Dados"

set "DISCIPLINE_CODE=%~3"
if "%DISCIPLINE_CODE%"=="" set "DISCIPLINE_CODE=ALG101"

set "SYLLABUS=Introducao a algoritmos, tipos de dados, funcoes e estruturas de controle."
set "COMPLEMENT=Projetos práticos com pseudo-código e implementacoes simples."
set "KEYWORDS_JSON=[\"algoritmos\",\"estrutura\",\"logica\"]"

echo Criando disciplina "%DISCIPLINE_TITLE%" no curso %COURSE_ID% ...
curl -i -X POST "http://localhost:8080/api/disciplines" ^
  -H "Content-Type: application/json" ^
  -d "{""title"":""%DISCIPLINE_TITLE%"",""code"":""%DISCIPLINE_CODE%"",""syllabus"":""%SYLLABUS%"",""complementaryContent"":""%COMPLEMENT%"",""keywords"":%KEYWORDS_JSON%,""courseId"":%COURSE_ID%}"

endlocal

