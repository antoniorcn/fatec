from selenium.webdriver.common.by import By
import time

import os
from login import MainScreen
# import getch


# if "SPIDER_SIGA_USER" not in os.environ:
#     print("Digite seu usuario do SIGA: ")
#     SIGA_USER = input()

# if "SPIDER_SIGA_PASSWORD" not in os.environ:
#     print("Digite sua senha no SIGA:")
#     SIGA_PASSWORD = input()

# def getPass():
#     passwor = ''
#     while True:
#         x = getch.getch()
#         # x = msvcrt.getch().decode("utf-8")
#         if x == '\r' or x == '\n':
#             break
#         print('*', end='', flush=True)
#         passwor +=x
#     return passwor


def print_hierarchical_elements(root):
    elements = root.find_elements(By.XPATH, "//*")
    if len(elements) > 0:
        for element in elements:
            print(f"Element[{element.text}]")
            print_hierarchical_elements(element)

def stop_until_enter():
    input("Pressione enter para continuar")


def verify_credentials(user, password, course, instance):
        global SIGA_USER, SIGA_PASSWORD
        SIGA_USER = user
        SIGA_PASSWORD = password
        print(f'User: {user}')
        print(f'Pass: {"*" * len(password)}')
        print(f'Course: {course}')
        executa_extracao()

def executa_extracao():    
    print("Efetuando o login")
    print("Login concluido")

    # retrieve_students_list(controller, "D.S.M.", "Manhã")

if __name__ == '__main__':
    MainScreen().run()