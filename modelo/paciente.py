from modelo.persona import Persona


class Paciente(Persona):
    def __init__(self, dni: str, nombre: str, edad: int, historia_clinica: str):
        super().__init__(dni, nombre, edad)
        if not isinstance(historia_clinica, str) or not historia_clinica.strip():
            raise ValueError("La historia clínica es obligatoria")
        self._historia_clinica = historia_clinica.strip()

    @property
    def rol(self) -> str:
        return "Paciente"

    @property
    def historia_clinica(self) -> str:
        return self._historia_clinica

    def solicitar_cita(self) -> None:
        print(f"{self._nombre} solicita una cita médica.")

    def consultar_historia(self) -> None:
        print(f"Historia clínica: {self._historia_clinica}")
