from modelo.paciente import Paciente
from modelo.medico import Medico
from modelo.atencion_medica import AtencionMedica


class CentroSalud:
    """Guarda los datos mientras el programa está abierto."""

    def __init__(self):
        self._pacientes = []
        self._medicos = []

    @property
    def pacientes(self) -> tuple:
        return tuple(self._pacientes)

    @property
    def medicos(self) -> tuple:
        return tuple(self._medicos)

    def buscar_por_dni(self, dni: str):
        for paciente in self._pacientes:
            if paciente.dni == dni:
                return paciente

        for medico in self._medicos:
            if medico.dni == dni:
                return medico

        return None

    def registrar_paciente(self, dni: str, nombre: str, edad: int,
                           historia_clinica: str) -> Paciente:
        if self.buscar_por_dni(dni) is not None:
            raise ValueError("Ya existe una persona registrada con ese DNI")

        paciente = Paciente(dni, nombre, edad, historia_clinica)
        self._pacientes.append(paciente)
        return paciente

    def registrar_medico(self, dni: str, nombre: str, edad: int,
                         cmp: str, especialidad: str) -> Medico:
        if self.buscar_por_dni(dni) is not None:
            raise ValueError("Ya existe una persona registrada con ese DNI")

        medico = Medico(dni, nombre, edad, cmp, especialidad)
        self._medicos.append(medico)
        return medico

    def registrar_atencion(self, dni: str, id_atencion: str, diagnostico: str,
                           tratamiento: str, observaciones: str) -> AtencionMedica:
        persona = self.buscar_por_dni(dni)

        if persona is None or not isinstance(persona, Paciente):
            raise ValueError("No existe un paciente registrado con ese DNI")

        atencion = AtencionMedica(
            id_atencion, diagnostico, tratamiento, observaciones
        )
        persona.agregar_atencion(atencion)
        return atencion

    def listar_todos(self) -> str:
        lineas = ["=== PACIENTES REGISTRADOS ==="]

        if not self._pacientes:
            lineas.append("No hay pacientes registrados.")
        else:
            for paciente in self._pacientes:
                lineas.append(paciente.mostrar_datos())
                lineas.append("")

        lineas.append("=== MÉDICOS REGISTRADOS ===")

        if not self._medicos:
            lineas.append("No hay médicos registrados.")
        else:
            for medico in self._medicos:
                lineas.append(medico.mostrar_datos())
                lineas.append("")

        return "\n".join(lineas)
