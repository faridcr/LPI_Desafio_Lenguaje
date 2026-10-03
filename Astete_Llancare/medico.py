from persona import Persona

class Medico(Persona):
    def __init__(self, dni, nombres, apellidos, fecha_nacimiento, cmp, especialidad):
        super().__init__(dni, nombres, apellidos, fecha_nacimiento)
        self.cmp = cmp
        self.especialidad = especialidad

    def atender_cita(self, cita):
        print("El medico", self.nombre_completo(), "esta atendiendo la cita", cita.id_cita)

    def mostrar_datos(self):
        super().mostrar_datos()
        print("CMP:", self.cmp)
        print("Especialidad:", self.especialidad)
