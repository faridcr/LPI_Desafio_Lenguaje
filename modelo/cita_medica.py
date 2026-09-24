from datetime import datetime
from enum import Enum


class EstadoCita(Enum):
    PROGRAMADA = "PROGRAMADA"
    CANCELADA = "CANCELADA"


class CitaMedica:
    def __init__(self, id_cita: str, fecha: str, estado: EstadoCita, motivo: str):
        if not id_cita or not id_cita.strip():
            raise ValueError("El id de la cita es obligatorio")
        try:
            datetime.strptime(fecha, "%d/%m/%Y")
        except (ValueError, TypeError):
            raise ValueError("La fecha debe tener el formato dd/mm/aaaa")
        if not isinstance(estado, EstadoCita):
            raise ValueError("Estado de cita no válido")
        if not motivo or not motivo.strip():
            raise ValueError("El motivo es obligatorio")
        self._id_cita = id_cita.strip()
        self._fecha = fecha
        self._estado = estado
        self._motivo = motivo.strip()

    @property
    def id_cita(self) -> str:
        return self._id_cita

    @property
    def fecha(self) -> str:
        return self._fecha

    @property
    def estado(self) -> EstadoCita:
        return self._estado

    @property
    def motivo(self) -> str:
        return self._motivo

    def programar_cita(self) -> None:
        self._estado = EstadoCita.PROGRAMADA

    def cancelar_cita(self) -> None:
        self._estado = EstadoCita.CANCELADA
