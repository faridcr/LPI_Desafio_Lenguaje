from receta import Receta

class AtencionMedica:
    def __init__(self, id_atencion, diagnostico, tratamiento, observaciones):
        self.id_atencion = id_atencion
        self.diagnostico = diagnostico
        self.tratamiento = tratamiento
        self.observaciones = observaciones
        self.receta = Receta()

    def agregar_medicamento(self, medicamento, frecuencia):
        self.receta.agregar_medicamento(medicamento, frecuencia)

    def mostrar_atencion(self):
        print("ID Atencion:", self.id_atencion)
        print("Diagnostico:", self.diagnostico)
        print("Tratamiento:", self.tratamiento)
        print("Observaciones:", self.observaciones)
        self.receta.mostrar_medicamentos()
