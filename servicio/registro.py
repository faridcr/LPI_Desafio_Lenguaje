from modelo.atencion_medica import AtencionMedica
from modelo.validaciones import validar_dni, enmascarar_dni


class RegistroAtenciones:
    """Lógica de registro de atenciones, separada de la interfaz gráfica."""

    def __init__(self):
        self._atenciones = []

    @property
    def atenciones(self) -> tuple:
        return tuple(self._atenciones)  # solo lectura

    def registrar(self, id_atencion: str, paciente: str, dni: str,
                  diagnostico: str, tratamiento: str) -> str:
        """Valida los datos, guarda la atención y devuelve el texto del resultado.

        Lanza ValueError si algún dato es inválido.
        """
        paciente = (paciente or "").strip()
        dni = (dni or "").strip()
        if not paciente:
            raise ValueError("El nombre del paciente es obligatorio")
        validar_dni(dni)
        if not (tratamiento or "").strip():
            raise ValueError("El tratamiento es obligatorio")

        atencion = AtencionMedica(id_atencion, diagnostico, tratamiento.strip(), "")
        self._atenciones.append(atencion)

        return (
            "========== ATENCIÓN REGISTRADA ==========\n\n"
            f"ID Atención: {atencion.id_atencion}\n"
            f"Paciente: {paciente}\n"
            f"DNI: {enmascarar_dni(dni)}\n"
            f"Diagnóstico: {atencion.diagnostico}\n"
            f"Tratamiento: {atencion.tratamiento}"
        )
