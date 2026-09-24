class AtencionMedica:
    def __init__(self, id_atencion: str, diagnostico: str,
                 tratamiento: str, observaciones: str):
        if not id_atencion or not id_atencion.strip():
            raise ValueError("El id de la atención es obligatorio")
        if not diagnostico or not diagnostico.strip():
            raise ValueError("El diagnóstico es obligatorio")
        self._id_atencion = id_atencion.strip()
        self._diagnostico = diagnostico.strip()
        self._tratamiento = tratamiento
        self._observaciones = observaciones

    @property
    def id_atencion(self) -> str:
        return self._id_atencion

    @property
    def diagnostico(self) -> str:
        return self._diagnostico

    @property
    def tratamiento(self) -> str:
        return self._tratamiento

    @property
    def observaciones(self) -> str:
        return self._observaciones

    def mostrar_atencion(self) -> None:
        print(f"ID Atención: {self._id_atencion}")
        print(f"Diagnóstico: {self._diagnostico}")
        print(f"Tratamiento: {self._tratamiento}")
        print(f"Observaciones: {self._observaciones}")
