from abc import ABC, abstractmethod
from modelo.validaciones import validar_dni, enmascarar_dni


class Persona(ABC):
    """Clase base para pacientes y médicos."""

    def __init__(self, dni: str, nombre: str, edad: int):
        validar_dni(dni)

        if not isinstance(nombre, str) or not nombre.strip():
            raise ValueError("El nombre es obligatorio")

        if not isinstance(edad, int) or not 0 <= edad <= 120:
            raise ValueError("La edad debe estar entre 0 y 120")

        self._dni = dni
        self._nombre = nombre.strip()
        self._edad = edad

    @property
    @abstractmethod
    def rol(self) -> str:
        pass

    @property
    def dni(self) -> str:
        return self._dni

    @property
    def nombre(self) -> str:
        return self._nombre

    @property
    def edad(self) -> int:
        return self._edad

    @property
    def dni_enmascarado(self) -> str:
        return enmascarar_dni(self._dni)

    def mostrar_datos(self) -> str:
        return (
            f"Rol: {self.rol}\n"
            f"DNI: {self.dni_enmascarado}\n"
            f"Nombre: {self._nombre}\n"
            f"Edad: {self._edad}"
        )
