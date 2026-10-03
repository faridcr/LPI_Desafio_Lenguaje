class HistoriaClinica:
    def __init__(self, numero_historia):
        self.numero_historia = numero_historia
        self.atenciones = []

    def agregar_atencion(self, atencion):
        self.atenciones.append(atencion)

    def mostrar_historia(self):
        print("Historia Clinica N°:", self.numero_historia)
        print("Atenciones registradas:", len(self.atenciones))

        for atencion in self.atenciones:
            atencion.mostrar_atencion()
            print()
