from modelo.persona import Persona


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

    def mostrar_datos(self) -> str:
        return (
            super().mostrar_datos()
            + f"\nCMP: {self._cmp}"
            + f"\nEspecialidad: {self._especialidad}"
        )
