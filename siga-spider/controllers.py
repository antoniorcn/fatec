from selenium.webdriver import Keys
from selenium.webdriver.chrome.service import Service
from selenium import webdriver
from webdriver_manager.chrome import ChromeDriverManager
from selenium.webdriver import ChromeService
from selenium.webdriver.common.by import By
from selenium.webdriver.support.ui import Select
from utils import tag_map, aluno_disciplina_field_map
import mysql.connector
import time
import os

class ChromeController:
    def __init__(self, db_user, db_password, db_host, db_database):
        self.DB_USER = db_user
        self.DB_PASSWORD = db_password
        self.DB_HOST = db_host
        self.DB_DATABASE = db_database
        self.cdm = ChromeDriverManager().install()
        folder = os.path.dirname(self.cdm)
        chromedriver_path = os.path.join(folder, "chromedriver.exe")
        self.service = ChromeService(chromedriver_path)
        self.options = webdriver.ChromeOptions()
        self.browser = webdriver.Chrome(service=self.service, options=self.options)
        self.current_element = None
        print(self.cdm)

    def switch_to_default_content(self):
        self.browser.switch_to.default_content()
        time.sleep(2)

    def switch_to_frame(self, frame_info, by=By.NAME):
        frame = self.retrieve_element(by=by, locator=frame_info)
        self.browser.switch_to.frame(frame)
        time.sleep(2)

    def click_link(self, locator, by=By.ID):
        self.retrieve_element(by=by, locator=locator).click()
        time.sleep(5)

    def retrieve_element(self, locator, by=By.ID, origin_element=None):
        if origin_element is None:
            origin_element = self.browser
        element = origin_element.find_element(by=by, value=locator)
        return element

    def retrieve_elements(self, locator, by=By.XPATH, origin_element=None):
        if origin_element is None:
            origin_element = self.browser        
        elements = origin_element.find_elements(by=by, value=locator)
        return elements
    
    def retrieve_element_text(self, locator, by=By.ID, origin_element=None):
        elemento = self.retrieve_element(by=by, locator=locator, 
                                         origin_element=origin_element)
        return elemento.get_attribute("innerText").strip()

    def select_option_by_value(self, locator, value, by=By.ID, origin_element=None):
        element = Select(self.retrieve_element(by=by, locator=locator,
                                               origin_element=origin_element))
        element.select_by_value(str(value))
        time.sleep(5)

    def select_option_typing_by_value(self, locator, text_value, by=By.ID, 
                                      origin_element=None):
        element = self.retrieve_element(by=by, locator=locator, 
                                        origin_element=origin_element)
        element.click()
        element.send_keys(text_value)
        element.send_keys(Keys.ENTER)
        time.sleep(5)

    def select_option_by_visible_text(self, locator, text_value, by=By.ID, 
                                      origin_element=None):
        element = Select(self.retrieve_element(by=by, locator=locator, 
                                               origin_element=origin_element))
        element.select_by_visible_text(text_value)
        time.sleep(5)

    def check_element(self, locator, text_value, by=By.ID, 
                      origin_element=None):
        try:
            elemento = self.retrieve_element(by=by, locator=locator,
                                             origin_element=origin_element)
            return text_value.strip() == elemento.get_attribute("innerText").strip()
        except Exception as e:
            print(f"elemento {locator} não contém o valor {text_value}")
            return False
        
    def select_table_row_by_locator(self, locator, text_value, by=By.ID, origin_element=None):
        elemento = self.retrieve_element(by=by, locator=locator, 
                                         origin_element=origin_element)
        
    def click_menu(self, menu_id, sub_menu_number):
        script = f"""
            var temp_menu_option = document.evaluate("{menu_id}", document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;
            cmItemMouseOver(temp_menu_option, 0, null, 0, {sub_menu_number});
            cmItemMouseDown(temp_menu_option, 0, null, 0, {sub_menu_number}); 
            cmItemMouseUp(temp_menu_option, 0, null, 0, {sub_menu_number});
        """
        self.browser.execute_script(script)
        time.sleep(5)

    def click(self):
        if self.current_element is not None:
            self.current_element.click()
            time.sleep(2)

    def get_page_source(self):
        html = self.browser.page_source
        time.sleep(2)
        # print(html)
        return html
    
    def send_text_to_element(self, locator, text_value, by=By.ID):
        element = self.retrieve_element(locator, by=by)
        if element is not None:
            element.click()
            element.clear()
            element.send_keys(text_value)

    def login(self, user, password):
        self.browser.get('https://siga.cps.sp.gov.br/fatec/Login.aspx')
        self.browser.maximize_window()
        window = self.browser.current_window_handle
        print("Window: ", window)
        time.sleep(5)

        self.browser.find_element(By.XPATH, "//input[@id='vSIS_USUARIOID']").send_keys(user)
        self.browser.find_element(By.ID, "vSIS_USUARIOSENHA").send_keys(password)
        self.browser.find_element(By.NAME, "BTCONFIRMA").click()
        time.sleep(5)            
    
    def choose_campus(self):
        print("Escolhendo o Campus")
        if not self.is_in_students_directory():
            faculdades = self.browser.retrieve_elements(By.XPATH, "//table[@id='Grid1ContainerTbl']/tbody/tr")
            for idx, faculdade in enumerate(faculdades):
                print("ID: ", idx,   "Tag : ", faculdade.tag_name)
                if idx == 1:
                    faculdade.click()
                    break
            self.click_link("span_vBT_ABRIR_0001")
            self.switch_to_frame("sistema")
            self.get_page_source()
            self.click_menu("//*[@id='cmSubMenuID4Table']/tbody/tr[1]", sub_menu_number=13)
        return self.is_in_students_directory()

    def move_to_students_directory(self):
        print("Acessando cadastro de estudantes")
        if not self.is_in_students_directory():
            self.click_link("span_vBT_ABRIR_0001")
            self.switch_to_frame("sistema")
            self.get_page_source()
            self.click_menu("//*[@id='cmSubMenuID4Table']/tbody/tr[1]", sub_menu_number=13)
        return self.is_in_students_directory()
    
    def is_in_students_directory(self):
        is_in = self.check_element("TITULO_MPAGE", "Catálogo de Alunos da Unidade")
        print(f"Está no diretório de estudantes: {is_in}")
        return is_in
    
    def pontuation_text_extraction( self, texto, *labels ):
        lista = []
        # print("Analisando conteudo do texto: ", texto)
        for i in range(len(labels)):
            start_word = labels[i]
            start_index = texto.index(start_word) + len(start_word)
            finish_index = len(texto)
            if i < len(labels) - 1:
                finish_word = labels[i + 1]
                finish_index = texto.index(finish_word)
            info = texto[start_index:finish_index]
            lista.append(info)
        return lista

    def retrieve_student_class_object_from_class_data(self, class_data):
        lista = str(class_data.text).split('\n')
        # print(" - TAG: ", class_data.tag_name)
        # print("Lista de textos: ", lista)
        disciplina = dict()
        disciplina["codigo"] = lista[0].strip()
        disciplina["horas"] = int(lista[1].split()[1].strip())
        disciplina["nome"] = lista[2].strip()
        # print("Informações obtidas ate o momento: ", disciplina)
        if len(lista) > 3:
            disciplina["status"] = lista[3].strip()
            # print("Tipo da lista4: {}  conteudo: {}".format(type(lista[4]), lista[4]))
            data = self.pontuation_text_extraction(str(lista[4]), "NF:", "FR:", "PE:")
            disciplina["NF"] = data[0].strip()
            disciplina["FR"] = data[1].strip()
            disciplina["PE"] = data[2].strip()
        # self.retrieve_elements("./table/body/tr", by=By.XPATH, origin_element=class_data)
        return disciplina
    
    def save_class_data(self, connection, disciplina):
        sql_insert = self.prepare_sql_based_class_data('aluno_disciplina', disciplina, aluno_disciplina_field_map)
        print("Executando SQL: ", sql_insert)
        cursor = connection.cursor()
        cursor.execute(sql_insert)
        connection.commit()
            
    def retrieve_current_student_history(self, ra):
        history_array = []
        if self.is_in_students_directory():
            # Abrir a grade de histórico
            self.click_link("vBTHISTORICO_0001")
            self.switch_to_frame("gxp0_ifrm", by = By.ID)
            details = self.retrieve_elements("//*[@id='W0002GRADE']/table[3]/tbody/tr/td", by=By.XPATH)
            for idx_semestre, semestre in enumerate(details):
                # print("--- ", idx_semestre, " - TAG: ", semestre.tag_name)
                classes = self.retrieve_elements("./div", by=By.XPATH, origin_element=semestre)
                for idx_class, class_data in enumerate(classes):
                    disciplina = self.retrieve_student_class_object_from_class_data(class_data)
                    disciplina["ra"] = ra
                    print("Informações da disciplina: ", disciplina)
                    history_array.append(disciplina)                    
            # Fechar a grade
            print("Fechando a ****GRADE****")
            self.switch_to_default_content()
            self.switch_to_frame("sistema")
            self.click_link("gxp0_cls")
        return history_array
    
    def set_curso_turno_filter(self, curso, turno, situacao):
        print(f"Filtrando por curso:{curso} no turno:{turno}")
        if self.is_in_students_directory():
            self.select_option_typing_by_value("vFILTER_ACD_CURSOID", curso, by=By.ID)
            self.select_option_typing_by_value("vFILTER_ACD_PERIODOID", turno, by=By.ID)
            self.select_option_typing_by_value("vGER_TIPOSITUACAOCURSOID", situacao, by=By.ID)
            self.click_link("BTFILTERON")

    def retrieve_student_detail(self, ra):
        print(f"Buscando dados do estudante:{ra}")
        student_detail = dict()
        history = []
        if self.is_in_students_directory():
            try:
                self.send_text_to_element("vFILTER_ACD_ALUNOCURSOREGISTROACADEMICOCURSO", ra, by=By.ID)
                self.click_link("BTFILTERON")
                history = self.retrieve_current_student_history(ra)
               
                # Visualizar detalhes aba Dados Pessoais
                # self.click_link("span_vPRO_PESSOALNOME_0001")
                # print("Link executado")
                # ra = self.retrieve_element_text("span_W0009vACD_ALUNOCURSOREGISTROACADEMICOCURSO")
                # print(f"Ra do aluno: {ra}")
                # student_detail["cpf"] = self.retrieve_element_text("span_W0026W0012vPRO_PESSOALDOCSCPF")
                # student_detail["pai"] = self.retrieve_element_text("span_W0026W0012vPRO_PESSOALPAINOME")
                # student_detail["mae"] = self.retrieve_element_text("span_W0026W0012vPRO_PESSOALMAENOME")
                # student_detail["email_fatec"] = self.retrieve_element_text("W0026W0012TEXTEMAILINSTITUCIONALEMAIL")
                # student_detail["email_pessoal"] = self.retrieve_element_text("span_W0026W0012vGRID2_PRO_PESSOALEMAIL_0001")
                # student_detail["telefone"] = self.retrieve_element_text("span_W0026W0012vGRID3_PRO_PESSOALTELEFONE_0001")
            except Exception as e:
                print("Erro ao executar o link: ", e)
        print(f"Detalhes do aluno: {student_detail}")
        print(f"Historico de disciplinas: {history}")
        return (student_detail, history)

    def retrieve_student_list(self, curso, turno):
        print(f"Buscando lista de estudantes no curso:{curso} no turno:{turno}")
        if self.move_to_students_directory():
            return self.retrieve_elements("//*[@id='Grid1ContainerTbl']/tbody/tr")
        else:
            return None
    
    def prepare_sql_based_class_data(self, table_name, class_data, field_map ): 
        tamanho = len(class_data.keys())
        print(f"Quantidade de campos {tamanho}")
        sql_insert_field = f"""INSERT INTO {table_name} ( """
        sql_insert_values = """ VALUES ( """
        for idx_campo, field_name in enumerate(class_data.keys()):
            table_field_name = field_map.get(field_name, None)
            print(f"Verificando campo:{field_name}   table_field: {table_field_name}")
            if field_name is not None and table_field_name is not None:
                value = class_data.get(field_name, None)
                sql_insert_field += f"{table_field_name}"
                if value is None:
                    sql_insert_values += f"NULL"
                elif isinstance(value, str):
                    sql_insert_values += f"'{value}'"
                else:
                    sql_insert_values += f"{value}"
                if idx_campo < tamanho - 1:
                    sql_insert_field += ", "
                    sql_insert_values += ", "
            if idx_campo >= tamanho - 1:
                sql_insert_field += " )"
                sql_insert_values += " )"
                break
        sql_insert = sql_insert_field + sql_insert_values
        return sql_insert

    def prepare_sql_based_student_element(self, student_element ):
        sql_insert_field = """INSERT INTO alunos ( """
        sql_insert_values = """ VALUES ( """
        campos = student_element.find_elements(by=By.XPATH, value= "./child::*")
        execute = False
        for idx_campo, el in enumerate(campos):
            print("--- ", idx_campo, " - TAG: ", el.tag_name, "\t", el.text)
            field_name = tag_map.get(idx_campo, None)
            value = el.text.strip()
            if field_name == 'nome':
                print("Nome encontrado: ", value)
                if value == "":    
                    execute = False
                    break
            if field_name != None:
                sql_insert_field += f"{field_name}"
                sql_insert_values += f"'{value}'"
                execute = True
                if idx_campo < 23:
                    sql_insert_field += ", "
                    sql_insert_values += ", "
                else:
                    sql_insert_field += " )"
                    sql_insert_values += " )"
                    break
        sql_insert = sql_insert_field + sql_insert_values
        return execute, sql_insert
    
    def retrieve_students_list(self, curso, turno, situacao):
        if self.move_to_students_directory():
            print("Cadastro de estudantes acessado")
            print("Conectando no banco de dados")
            cnx = mysql.connector.connect(user=self.DB_USER, password=self.DB_PASSWORD,
                                    host=self.DB_HOST,
                                    database=self.DB_DATABASE)
            self.set_curso_turno_filter(curso, turno, situacao)
            elementos = self.retrieve_student_list(curso, turno)
            if elementos is not None:
                for idx, elemento in enumerate(elementos):
                    if idx == 0:
                        continue
                    print("ID: ", idx,   "Tag : ", elemento.tag_name)
                    execute, sql_insert = self.prepare_sql_based_student_element(elemento)
                    if execute: 
                        print("Executando: ", sql_insert)
                        cursor = cnx.cursor()
                        cursor.execute(sql_insert)
                        cnx.commit()
                
            cnx.close()


    def retrieve_students_details(self, curso, turno, situacao):
        if self.move_to_students_directory():
            print("Recuperar detalhes de alunos do SIGA")
            print("Conectando no banco de dados")
            cnx = mysql.connector.connect(user=self.DB_USER, password=self.DB_PASSWORD,
                                    host=self.DB_HOST,
                                    database=self.DB_DATABASE)
            print("Conectado no banco de dados MariaDB")
            print("Lendo RAs dos alunos do banco de dados: ")
            sql_select_Query = "select ra from alunos"
            cursor = cnx.cursor()
            cursor.execute(sql_select_Query)
            records = cursor.fetchall()
            for row in records:
                ra = row[0]
                print(f"Buscando dados do aluno RA {ra}")
                self.set_curso_turno_filter(curso, turno, situacao)
                student_detail, student_history = self.retrieve_student_detail( ra )
                time.sleep(5)
                print(f"Salvando os dados no DB")
                for disciplina in student_history:
                    self.save_class_data(cnx, disciplina)
            cnx.close()

    def close(self):
        self.browser.close()