tag_map = {
    14: "ra",
    16: "estado_atual",
    17: "nome",
    18: "turma",
    20: "pp",
    21: "pr",
    22: "email",
    23: "documento"
}


aluno_disciplina_field_map = {
    "ra": "ra",
    "codigo": "codigo",
    "status": "situacao",
    "NF": "nota",
    "FR": "frequencia",
    "PE": "pe"
}


def print_hierarchical_elements(root):
    elements = root.find_elements(By.XPATH, "//*")
    if len(elements) > 0:
        for element in elements:
            print(f"Element[{element.text}]")
            print_hierarchical_elements(element)