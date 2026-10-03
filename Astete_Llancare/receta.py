class Receta:
    def __init__(self):
        self.medicamentos = []
        self.frecuencias = []

    def agregar_medicamento(self, medicamento, frecuencia):
        if medicamento.stock > 0:
            medicamento.descontar_stock()
            self.medicamentos.append(medicamento)
            self.frecuencias.append(frecuencia)
        else:
            print("No hay stock disponible")

    def mostrar_medicamentos(self):
        print("Medicamentos:")

        if len(self.medicamentos) == 0:
            print("No hay medicamentos en la receta")
        else:
            for i in range(len(self.medicamentos)):
                print("-", self.medicamentos[i].nombre, "(", self.frecuencias[i], ")")
