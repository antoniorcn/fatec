import PyPDF2

# Função para extrair o texto do PDF
def extrair_texto_pdf(caminho_pdf):
    with open(caminho_pdf, "rb") as arquivo_pdf:
        leitor_pdf = PyPDF2.PdfReader(arquivo_pdf)
        texto = ""
        for pagina in leitor_pdf.pages:
            texto += pagina.extract_text()
        return texto

# Função para avaliar cada critério
def avaliar_criterio(nome_criterio, peso):
    while True:
        try:
            nota = float(input(f"Digite a nota para '{nome_criterio}' (0 a 10): "))
            if 0 <= nota <= 10:
                return nota * peso / 100
            else:
                print("Nota inválida! Por favor, insira um valor entre 0 e 10.")
        except ValueError:
            print("Entrada inválida! Por favor, insira um número.")

# Função principal para avaliar o artigo
def avaliar_artigo(caminho_pdf):
    texto = extrair_texto_pdf(caminho_pdf)
    print(f"\nConteúdo do artigo extraído:\n{texto[:1000]}...\n")  # Exibe os primeiros 1000 caracteres do artigo

    print("Vamos avaliar o artigo com base nos seguintes critérios:\n")

    criterios = {
        "Originalidade e Contribuição para o Campo": 20,
        "Qualidade Metodológica": 20,
        "Relevância e Aplicabilidade": 15,
        "Clareza e Estrutura": 15,
        "Qualidade da Revisão de Literatura": 10,
        "Rigor Teórico": 10,
        "Reprodutibilidade e Transparência": 5,
        "Qualidade da Apresentação de Resultados": 5
    }

    nota_final = 0

    for criterio, peso in criterios.items():
        nota_final += avaliar_criterio(criterio, peso)

    print(f"\nNota final do artigo: {nota_final:.2f}/10")

# Caminho do arquivo PDF
caminho_pdf = input("Insira o caminho para o arquivo PDF: ")

# Avaliar o artigo
avaliar_artigo(caminho_pdf)