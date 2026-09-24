import tkinter as tk
from tkinter import messagebox

from servicio.registro import RegistroAtenciones


class VentanaPaciente(tk.Tk):
    """Ventana principal (programación orientada a eventos con tkinter)."""

    def __init__(self):
        super().__init__()
        self._registro = RegistroAtenciones()

        self.title("Centro de Salud 10 de Octubre")
        self._centrar(650, 550)

        principal = tk.Frame(self, padx=20, pady=20)
        principal.pack(fill="both", expand=True)

        tk.Label(principal, text="CENTRO DE SALUD 10 DE OCTUBRE",
                 font=("Arial", 18, "bold")).pack(pady=(0, 15))

        # Datos de la atención
        panel_datos = tk.Frame(principal)
        panel_datos.pack(fill="x")
        panel_datos.columnconfigure(1, weight=1)

        self._campos = {}
        etiquetas = [
            ("id", "ID Atención:"),
            ("paciente", "Paciente:"),
            ("dni", "DNI:"),
            ("diagnostico", "Diagnóstico:"),
            ("tratamiento", "Tratamiento:"),
        ]
        for fila, (clave, texto) in enumerate(etiquetas):
            tk.Label(panel_datos, text=texto, anchor="w").grid(
                row=fila, column=0, sticky="w", pady=5)
            entrada = tk.Entry(panel_datos)
            entrada.grid(row=fila, column=1, sticky="ew", padx=(10, 0), pady=5)
            self._campos[clave] = entrada

        # Botones
        panel_botones = tk.Frame(principal)
        panel_botones.pack(fill="x", pady=15)
        panel_botones.columnconfigure((0, 1), weight=1)

        tk.Button(panel_botones, text="REGISTRAR ATENCIÓN",
                  command=self._registrar).grid(row=0, column=0, sticky="ew", padx=(0, 5))
        tk.Button(panel_botones, text="LIMPIAR",
                  command=self._limpiar).grid(row=0, column=1, sticky="ew", padx=(5, 0))

        # Resultado
        panel_resultado = tk.Frame(principal)
        panel_resultado.pack(fill="both", expand=True)
        scroll = tk.Scrollbar(panel_resultado)
        scroll.pack(side="right", fill="y")
        self._resultado = tk.Text(panel_resultado, height=8, state="disabled",
                                  yscrollcommand=scroll.set)
        self._resultado.pack(side="left", fill="both", expand=True)
        scroll.config(command=self._resultado.yview)

    def _centrar(self, ancho: int, alto: int) -> None:
        x = (self.winfo_screenwidth() - ancho) // 2
        y = (self.winfo_screenheight() - alto) // 2
        self.geometry(f"{ancho}x{alto}+{x}+{y}")

    def _mostrar_resultado(self, texto: str) -> None:
        self._resultado.config(state="normal")
        self._resultado.delete("1.0", "end")
        self._resultado.insert("1.0", texto)
        self._resultado.config(state="disabled")

    # Evento: REGISTRAR
    def _registrar(self) -> None:
        datos = {clave: campo.get() for clave, campo in self._campos.items()}
        try:
            texto = self._registro.registrar(
                datos["id"], datos["paciente"], datos["dni"],
                datos["diagnostico"], datos["tratamiento"])
        except ValueError as error:
            messagebox.showerror("Datos inválidos", str(error))
            return
        self._mostrar_resultado(texto)

    # Evento: LIMPIAR
    def _limpiar(self) -> None:
        for campo in self._campos.values():
            campo.delete(0, "end")
        self._mostrar_resultado("")
