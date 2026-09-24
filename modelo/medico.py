from modelo.persona import Persona
from modelo.cita_medica import CitaMedica


class Medico(Persona):
    def __init__(self, dni: str, nombre: str, edad: int, cmp: str, especialidad: str):
        super().__init__(dni, nombre, edad)
        if not cmp or not cmp.strip():
            raise ValueError("El CMP es obligatorio")
        if not especialidad or not especialidad.strip():
            raise ValueError("La especialidad es obligatoria")
        self._cmp = cmp.strip()
        self._especialidad = especialidad.strip()

    @property
    def rol(self) -> str:
        return "Médico"

    @property
    def cmp(self) -> str:
        return self._cmp

    @property
    def especialidad(self) -> str:
        return self._especialidad

    def atender_cita(self, cita: CitaMedica) -> None:
        print(f"El médico {self._nombre} está atendiendo la cita {cita.id_cita}")
