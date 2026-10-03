from persona import Persona
from historia_clinica import HistoriaClinica

class Paciente(Persona):
    def __init__(self, dni, nombres, apellidos, fecha_nacimiento, numero_historia):
        super().__init__(dni, nombres, apellidos, fecha_nacimiento)
        self.historia_clinica = HistoriaClinica(numero_historia)

    def solicitar_cita(self):
        print(self.nombre_completo(), "solicita una cita medica")

    def agregar_atencion(self, atencion):
        self.historia_clinica.agregar_atencion(atencion)

    def consultar_historia(self):
        self.historia_clinica.mostrar_historia()

    def mostrar_datos(self):
        super().mostrar_datos()
        print("N° Historia Clinica:", self.historia_clinica.numero_historia)
        print("Atenciones registradas:", len(self.historia_clinica.atenciones))
