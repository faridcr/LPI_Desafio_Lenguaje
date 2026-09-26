from modelo.persona import Persona


class Paciente(Persona):
    def __init__(self, dni: str, nombre: str, edad: int, historia_clinica: str):
        super().__init__(dni, nombre, edad)

        if not isinstance(historia_clinica, str) or not historia_clinica.strip():
            raise ValueError("La historia clínica es obligatoria")

        self._historia_clinica = historia_clinica.strip()
        self._atenciones = []

    @property
    def rol(self) -> str:
        return "Paciente"

    @property
    def historia_clinica(self) -> str:
        return self._historia_clinica

    @property
    def atenciones(self) -> tuple:
        return tuple(self._atenciones)

    def agregar_atencion(self, atencion) -> None:
        self._atenciones.append(atencion)

    def mostrar_datos(self) -> str:
        return (
            super().mostrar_datos()
            + f"\nN° Historia Clínica: {self._historia_clinica}"
            + f"\nAtenciones registradas: {len(self._atenciones)}"
        )
