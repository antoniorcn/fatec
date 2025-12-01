@echo off
setlocal

if "%~1"=="" (
    echo Uso: create_discipline_sample.bat ^<courseId^>
    exit /b 1
)

set "COURSE_ID=%~1"
set "TITLE=Matematica Aplicada para Engenharia"
set "CODE=MAT-ENG-101"
set "SYLLABUS=Fundamentos de aritmetica, algebra linear, calculo diferencial e integral aplicados a problemas reais de engenharia; resolucao de equacoes diferenciais de primeira e segunda ordem; analise de series e aproximacoes numericas utilizadas em simulacoes."
set "COMPLEMENT=Discussao de casos praticos envolvendo modelagem matematica de sistemas termicos, eletricos e mecanicos; elaboracao de relatorios tecnicos; indicacoes bibliograficas e roteiros de estudo guiado para reforcar a resolucao de problemas complexos."
set "KEYWORDS_JSON=[\"matematica\",\"calculo\",\"algebra\",\"engenharia\",\"modelagem\"]"

echo Criando disciplina exemplo de matematica para o curso %COURSE_ID% ...
curl -i -X POST ^
  "http://localhost:8080/api/disciplines" ^
  -H "Content-Type: application/json" ^
  -d ^
"{""title"":""%TITLE%"",""code"":""%CODE%"",""syllabus"":""%SYLLABUS%"",""complementaryContent"":""%COMPLEMENT%"",""keywords"":%KEYWORDS_JSON%,""courseId"":%COURSE_ID%}"

endlocal

