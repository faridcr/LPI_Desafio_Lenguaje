import re


def validar_dni(dni: str) -> None:
    """Lanza ValueError si el DNI no tiene exactamente 8 dígitos."""
    if not isinstance(dni, str) or not re.fullmatch(r"[0-9]{8}", dni):
        raise ValueError("El DNI debe tener 8 dígitos")


def enmascarar_dni(dni: str) -> str:
    """Ley N.° 29733: no se muestra el DNI completo."""
    validar_dni(dni)
    return "****" + dni[4:]
