# from kivy.app import App
# from kivy.uix.boxlayout import BoxLayout
# from kivy.uix.label import Label
# from kivy.uix.textinput import TextInput
# from kivy.uix.button import Button
# from kivy.uix.spinner import Spinner


# class MainScreen(App):
#     def __init__(self, **kwargs):
#         super(MainScreen, self).__init__(**kwargs)
#         print("Carregando o controlador para o Google Chrome")
#         self.controller = None
#         self.siga_user = None
#         self.siga_password = None
#         self.siga_curso = None
#         self.siga_turno = None

#     def capture_info(self, instance=None):
#         self.siga_user = self.username.text
#         self.siga_password = self.password.text
#         self.siga_curso = self.course.text
#         self.siga_turno = self.turno.text
#         print(f'User: {self.siga_user}')
#         print(f'Pass: {"*" * len(self.siga_password)}')
#         print(f'Course: {self.siga_curso}')
#         print(f'Turno: {self.siga_turno}')

    
#     def build(self):
#         container = BoxLayout()
#         container.orientation = 'vertical'

#         container.add_widget(Label(text='Usuário'))
#         self.username = TextInput(multiline=False)
#         container.add_widget(self.username)

#         container.add_widget(Label(text='Senha'))
#         self.password = TextInput(password=True, multiline=False)
#         container.add_widget(self.password)

#         container.add_widget(Label(text='Nome do Curso'))
#         self.course = Spinner(
#             text='Selecione o curso',
#             values=('Jogos', 'ADS', 'Logistica'),
#             size_hint=(None, None),
#             size=(200, 44)
#         )
#         container.add_widget(self.course)

#         container.add_widget(Label(text='Turno'))
#         self.turno = Spinner(
#             text='Selecione o curso',
#             values=('Manha', 'Tarde', 'Noite'),
#             size_hint=(None, None),
#             size=(200, 44)
#         )
#         container.add_widget(self.turno)

#         self.login_button = Button(text='Login', size_hint=(None, None), size=(200, 44))
#         self.login_button.bind(on_press=self.capture_info)
#         container.add_widget(self.login_button)
#         return container
