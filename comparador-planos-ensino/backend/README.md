## Comparador de Planos de Ensino

Aplicação Spring Boot em Kotlin para cadastrar cursos, disciplinas e comparar automaticamente as ementas de dois cursos distintos.

### Stack
- Kotlin + Spring Boot 3.2
- Spring Data JPA com MariaDB (H2 apenas para testes automatizados)
- Validação via Bean Validation (`jakarta.validation`)

### Pré-requisitos
1. **JDK 21** instalado e com `JAVA_HOME` configurado.
2. **Gradle 8.7**. O repositório traz os scripts `gradlew`/`gradlew.bat`, porém o arquivo `gradle-wrapper.jar` não foi incluído devido a limitações do ambiente. Execute `gradle wrapper` uma vez para gerar o wrapper completo ou instale Gradle globalmente e use `gradle bootRun`.
3. **MariaDB** rodando localmente (porta padrão 3306) com usuário `root` e senha `123456`. O banco `fatec-comparador` será criado automaticamente se não existir.

### Como executar
```bash
# gerar o wrapper (apenas se ainda não existir)
gradle wrapper

# compilar e subir a API
./gradlew bootRun
```
No Windows use `gradlew.bat bootRun`.

> Certifique-se de que o serviço MariaDB esteja ativo antes de iniciar a aplicação. Caso utilize outra porta/usuário/senha, ajuste `src/main/resources/application.yml`.

### Principais endpoints
- `POST /api/courses` cria um curso.
- `GET /api/courses` lista cursos com suas disciplinas.
- `POST /api/disciplines` registra uma disciplina vinculada a um curso.
- `GET /api/disciplines?courseId={id}` lista as disciplinas de um curso específico.
- `GET /api/comparisons/courses/{cursoA}/vs/{cursoB}` compara conteúdos dos cursos e retorna sobreposição de palavras-chave e disciplinas relacionadas.
- `POST /api/comparisons/texts/similarity` recebe dois textos livres e retorna o grau (0.0 a 1.0) de similaridade calculado a partir de embeddings Word2Vec.

### Scripts de teste rápido
Os arquivos `.bat` em `scripts/curl/` usam `curl` (disponível no Windows 10+) e `python -m json.tool` apenas para formatar as respostas:

- `scripts\curl\create_course.bat ["Nome"] ["Descrição"]`
- `scripts\curl\list_courses.bat`
- `scripts\curl\create_discipline.bat <courseId> ["Título"] ["COD123"]`
- `scripts\curl\create_discipline_sample.bat <courseId>` (carrega um exemplo completo de matemática)
- `scripts\curl\list_disciplines.bat [courseId]`
- `scripts\curl\compare_courses.bat <courseA> <courseB>`

Execute com o servidor rodando em `http://localhost:8080`.

As requisições de criação/edição aceitam JSON simples, por exemplo:
```json
{
  "name": "Engenharia de Software",
  "description": "Curso focado em engenharia de sistemas e gestão de projetos."
}
```

Consulte `src/main/resources/application.yml` para ajustar as credenciais/host do MariaDB conforme seu ambiente.

### Similaridade semântica com Word2Vec
O serviço de comparação textual utiliza um modelo Word2Vec carregado em memória.

1. Ajuste o caminho do arquivo em `app.embeddings.word2vec.location`. O valor padrão aponta para `classpath:embeddings/sample-word2vec.txt`, apenas demonstrativo. Substitua pelo seu modelo (ex.: `file:C:/models/cbow_s50.txt`).
2. Informe a dimensão correta do vetor (`app.embeddings.word2vec.dimension`).
3. Opcionalmente limite o vocabulário carregado com `app.embeddings.word2vec.max-vocabulary`.

Exemplo de requisição:
```http
POST /api/comparisons/texts/similarity
Content-Type: application/json

{
  "textA": "Projeto Integrador em Engenharia de Software",
  "textB": "Gestão de projetos e desenvolvimento de software"
}
```
Resposta:
```json
{
  "similarityScore": 0.87
}
```
