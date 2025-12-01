-- Criar as tabelas

CREATE TABLE alunos (
    id int AUTO_INCREMENT,
    ra char(15),
    nome char(100),
    email char(100),
    pp char(20),
    pr char(20),
    turma char(20),
    documento char(30),
    estado_atual char(30),
    PRIMARY KEY(id)
);


CREATE TABLE aluno_disciplina (
    id int AUTO_INCREMENT,
    ra char(15),
    codigo char(15),
    situacao char(30),
    nota double(5, 1),
    frequencia double(6, 2),
    pe char(15),
    PRIMARY KEY(id)
);