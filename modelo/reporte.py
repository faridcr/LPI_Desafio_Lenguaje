from modelo.atencion_medica import AtencionMedica


class Reporte:
    def __init__(self, tipo_reporte: str, fecha: str):
        self._tipo_reporte = tipo_reporte
        self._fecha = fecha

    @staticmethod
    def filtrar_por_diagnostico(atenciones: list, texto: str) -> list:
        """Función de orden superior: filter con lambda."""
        texto = texto.lower()
        return list(filter(lambda a: texto in a.diagnostico.lower(), atenciones))

    @staticmethod
    def listar_diagnosticos(atenciones: list) -> list:
        """Función de orden superior: map con lambda."""
        return list(map(lambda a: a.diagnostico, atenciones))

    def generar_reporte(self, atenciones: list) -> None:
        print("\n==============================")
        print("       REPORTE DE SALUD")
        print("==============================")
        print(f"Tipo: {self._tipo_reporte}")
        print(f"Fecha: {self._fecha}")
        print(f"Cantidad de atenciones: {len(atenciones)}")

        for atencion in atenciones:
            print(f"\nAtención: {atencion.id_atencion}")
            print(f"Diagnóstico: {atencion.diagnostico}")
        print("==============================")
