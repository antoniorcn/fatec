from selenium.webdriver.common.by import By
import time

import os
# from login import MainScreen
from controllers import ChromeController
from dotenv import load_dotenv


def print_hierarchical_elements(root):
    elements = root.find_elements(By.XPATH, "//*")
    if len(elements) > 0:
        for element in elements:
            print(f"Element[{element.text}]")
            print_hierarchical_elements(element)


def stop_until_enter():
    input("Pressione enter para continuar")


class Principal:
    def __init__(self):
        load_dotenv(dotenv_path="./production.env")
        self.SPIDER_SIGA_USER = os.getenv("SPIDER_SIGA_USER")
        self.SPIDER_SIGA_PASSWORD = os.getenv("SPIDER_SIGA_PASSWORD")
        self.SPIDER_SIGA_UNIDADE=os.getenv("SPIDER_SIGA_UNIDADE")
        self.SPIDER_SIGA_MODULO=os.getenv("SPIDER_SIGA_MODULO")
        self.SPIDER_SIGA_GRUPO=os.getenv("SPIDER_SIGA_GRUPO")
        self.SPIDER_SIGA_CURSO = os.getenv("SPIDER_SIGA_CURSO")
        self.SPIDER_SIGA_TURNO = os.getenv("SPIDER_SIGA_TURNO")
        self.SPIDER_DB_HOST = os.getenv("SPIDER_DB_HOST")
        self.SPIDER_DB_DATABASE = os.getenv("SPIDER_DB_DATABASE")
        self.SPIDER_DB_USER = os.getenv("SPIDER_DB_USER")
        self.SPIDER_DB_PASSWORD = os.getenv("SPIDER_DB_PASSWORD")


    def verify_credentials(self):
        if self.SPIDER_SIGA_USER and self.SPIDER_SIGA_PASSWORD and\
            self.SPIDER_SIGA_CURSO and self.SPIDER_SIGA_TURNO:
            print(f'User: {self.SPIDER_SIGA_USER}')
            print(f'Pass: {"*" * len(self.SPIDER_SIGA_PASSWORD)}')
            print(f'Course: {self.SPIDER_SIGA_CURSO}')
        else:
            print("Credenciais não encontradas. Verifique o arquivo de ambiente.")
            # print("abrindo interface de login")
            # MainScreen().run()

    def executar_extracao(self):    
        print("Efetuando o login")
        print("Login concluido")
        # retrieve_students_list(controller, "D.S.M.", "Manhã")

    def connect(self):
        self.controller = ChromeController(db_user=self.SPIDER_DB_USER, db_password=self.SPIDER_DB_PASSWORD, 
                                        db_host=self.SPIDER_DB_HOST, db_database=self.SPIDER_DB_DATABASE,
                                        time_factor=0.3)
        
    def close(self):
        self.controller.close()

    def efetuar_login(self):
        self.controller.login(self.SPIDER_SIGA_USER, self.SPIDER_SIGA_PASSWORD)
        # self.controller.retrieve_students_details(self.SPIDER_SIGA_CURSO, self.SPIDER_SIGA_TURNO, "Todos")

    def escolher_campus(self):
        self.controller.choose_campus(self.SPIDER_SIGA_UNIDADE, self.SPIDER_SIGA_MODULO, self.SPIDER_SIGA_GRUPO)

if __name__ == '__main__':
    principal = Principal()
    principal.verify_credentials()
    principal.connect()
    principal.efetuar_login()
    principal.escolher_campus()
    principal.controller.switch_to_frame("sistema")
    if principal.controller.is_in_main_directory():
        if principal.controller.move_to_students_directory():
            principal.controller.retrieve_students_list(principal.SPIDER_SIGA_CURSO, principal.SPIDER_SIGA_TURNO, "Todos")
    principal.close()
    
    