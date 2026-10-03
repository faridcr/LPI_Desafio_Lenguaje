class Reporte:
    def __init__(self, tipo_reporte, fecha):
        self.tipo_reporte = tipo_reporte
        self.fecha = fecha

    def generar_reporte(self, atenciones):
        print("\n--- REPORTE DE SALUD ---")
        print("Tipo:", self.tipo_reporte)
        print("Fecha:", self.fecha)
        print("Cantidad de atenciones:", len(atenciones))

        for atencion in atenciones:
            print("Atencion", atencion.id_atencion, "-", atencion.diagnostico)
