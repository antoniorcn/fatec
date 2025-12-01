from kivy.app import App
from kivy.uix.boxlayout import BoxLayout
from kivy.uix.label import Label
from kivy.uix.textinput import TextInput
from kivy.uix.button import Button
from kivy.uix.spinner import Spinner
from controllers import ChromeController
from dotenv import load_dotenv
import os

load_dotenv(dotenv_path="./production.env")
SIGA_USER = os.getenv("SPIDER_SIGA_USER")
SIGA_PASSWORD = os.getenv("SPIDER_SIGA_PASSWORD")

DB_HOST = os.getenv("SPIDER_DB_HOST")
DB_DATABASE = os.getenv("SPIDER_DB_DATABASE")
DB_USER = os.getenv("SPIDER_DB_USER")
DB_PASSWORD = os.getenv("SPIDER_DB_PASSWORD")

class MainScreen(App):
    def __init__(self, **kwargs):
        super(MainScreen, self).__init__(**kwargs)
        print("Carregando o controlador para o Google Chrome")
        self.controller = None
    
    def build(self):
        container = BoxLayout()
        container.orientation = 'vertical'

        container.add_widget(Label(text='Usuário'))
        self.username = TextInput(multiline=False)
        container.add_widget(self.username)

        container.add_widget(Label(text='Senha'))
        self.password = TextInput(password=True, multiline=False)
        container.add_widget(self.password)

        container.add_widget(Label(text='Nome do Curso'))
        self.course = Spinner(
            text='Selecione o curso',
            values=('Jogos', 'ADS', 'Logistica'),
            size_hint=(None, None),
            size=(200, 44)
        )
        container.add_widget(self.course)

        self.login_button = Button(text='Login', size_hint=(None, None), size=(200, 44))
        self.login_button.bind(on_press=self.efetuar_login)
        container.add_widget(self.login_button)
        return container

    def efetuar_login(self, instance=None):
        print(f'User: {self.username.text}')
        print(f'Pass: {"*" * len(self.password.text)}')
        print(f'Course: {self.course}')
        self.controller = ChromeController(db_user=DB_USER, db_password=DB_PASSWORD, db_host=DB_HOST, db_database=DB_DATABASE)
        self.controller.login(self.username.text, self.password.text)
        self.controller.retrieve_students_details("D.S.M.", "Manhã", "Todos")
        self.controller.close()
