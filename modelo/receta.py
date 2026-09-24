class Receta:
    def __init__(self):
        self._medicamentos = []

    @property
    def medicamentos(self) -> tuple:
        return tuple(self._medicamentos)  # solo lectura

    def agregar_medicamento(self, medicamento: str) -> None:
        if not medicamento or not medicamento.strip():
            raise ValueError("El medicamento no puede estar vacío")
        self._medicamentos.append(medicamento.strip())

    def mostrar_medicamentos(self) -> None:
        print("Medicamentos:")
        for medicamento in self._medicamentos:
            print(f"- {medicamento}")
