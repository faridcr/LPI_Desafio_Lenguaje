from cita_medica import CitaMedica

class CentroSalud:
    def __init__(self):
        self.pacientes = []
        self.medicos = []
        self.citas = []
        self.medicamentos = []

    def registrar_paciente(self, paciente):
        self.pacientes.append(paciente)

    def registrar_medico(self, medico):
        self.medicos.append(medico)

    def registrar_medicamento(self, medicamento):
        self.medicamentos.append(medicamento)

    def buscar_por_dni(self, dni):
        for paciente in self.pacientes:
            if paciente.coincide_dni(dni):
                return paciente

        for medico in self.medicos:
            if medico.coincide_dni(dni):
                return medico

        return None

    def registrar_cita(self, id_cita, fecha, motivo, paciente, medico):
        cita = CitaMedica(id_cita, fecha, motivo, paciente, medico)
        self.citas.append(cita)
        return cita

    def listar_todos(self):
        print("\n--- PACIENTES ---")
        for paciente in self.pacientes:
            paciente.mostrar_datos()
            print()

        print("--- MEDICOS ---")
        for medico in self.medicos:
            medico.mostrar_datos()
            print()

        print("--- CITAS ---")
        for cita in self.citas:
            cita.mostrar_cita()
            print()

        print("--- MEDICAMENTOS ---")
        for medicamento in self.medicamentos:
            medicamento.mostrar_medicamento()
