SetTitleMatchMode, 2 ;means that the phrase can appear anywhere in the title (normally it's the beginning of the title)
if WinExist("Outlook")
    WinActivate ; Use the window found by WinExist.
	
Loop
{
	FileEncoding UTF-8
	FileReadLine, line, D:\git\fatec\massemail\trancados_todos_erros.csv, %A_Index%
	if (A_Index = 1)
		continue
	if ErrorLevel
		break

	StringSplit, infoArray, line, `,
	nome_aluno := infoArray4
	email_aluno := infoArray5
	curso := infoArray1
	coordenador_nome := infoArray8
	coordenador_email := infoArray9
	coordenador_telefone := infoArray10
	SendInput ^n
	SendInput %email_aluno%
	SendInput {Tab 3}
	SendInput `Comunicado da Fatec Zona Leste - IMPORTANTE`
	Sleep 500
	SendInput {Tab}
	SendInput ^v
	SendInput ^h
	SendInput !n
	SendInput ^a
	SendInput {Raw}`@nome_aluno@`
	SendInput !i
	SendInput ^a
	SendInput %nome_aluno%
	SendInput !a
	SendInput {Enter}
	SendInput ^h
	SendInput !n
	SendInput ^a
	SendInput {Raw}`@curso@`
	SendInput !i
	SendInput ^a
	SendInput %curso%
	SendInput !a
	SendInput {Enter}
	SendInput ^h
	SendInput !n
	SendInput ^a
	SendInput {Raw}`@coordenador_nome@`
	SendInput !i
	SendInput ^a
	SendInput %coordenador_nome%
	SendInput !a
	SendInput {Enter}
	SendInput ^h
	SendInput !n
	SendInput ^a
	SendInput {Raw}`@coordenador_email@`
	SendInput !i
	SendInput ^a
	SendInput %coordenador_email%
	SendInput !a
	SendInput {Enter}
	SendInput ^h
	SendInput !n
	SendInput ^a
	SendInput {Raw}`@coordenador_telefone@`
	SendInput !i
	SendInput ^a
	SendInput %coordenador_telefone%
	SendInput !a
	SendInput {Enter}
	SendInput ^h
	SendInput {Tab 6}
	SendInput {Enter}
	SendInput !s
	Sleep 15000
;	MsgBox, Email criado para Full Name %nome_aluno%   Email  %email_aluno%

}

return



