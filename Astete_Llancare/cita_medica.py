class CitaMedica:
    def __init__(self, id_cita, fecha, motivo, paciente, medico):
        self.id_cita = id_cita
        self.fecha = fecha
        self.motivo = motivo
        self.paciente = paciente
        self.medico = medico
        self.estado = "PENDIENTE"

    def programar_cita(self):
        self.estado = "PROGRAMADA"

    def cancelar_cita(self):
        self.estado = "CANCELADA"

    def mostrar_cita(self):
        print("ID Cita:", self.id_cita)
        print("Fecha:", self.fecha)
        print("Estado:", self.estado)
        print("Motivo:", self.motivo)
        print("Paciente:", self.paciente.nombre_completo())
        print("Medico:", self.medico.nombre_completo())
        print("Especialidad:", self.medico.especialidad)
