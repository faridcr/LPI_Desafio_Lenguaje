from datetime import datetime

class Persona:
    def __init__(self, dni, nombres, apellidos, fecha_nacimiento):
        if len(dni) != 8 or dni.isdigit() == False:
            raise ValueError("El DNI debe tener exactamente 8 numeros")

        if nombres == "":
            raise ValueError("El nombre no puede estar vacio")

        if apellidos == "":
            raise ValueError("El apellido no puede estar vacio")

        try:
            datetime.strptime(fecha_nacimiento, "%d/%m/%Y")
        except ValueError:
            raise ValueError("La fecha debe ser valida y tener formato dd/mm/aaaa")

        self.dni = dni
        self.nombres = nombres
        self.apellidos = apellidos
        self.fecha_nacimiento = fecha_nacimiento

    def nombre_completo(self):
        return self.apellidos + " " + self.nombres

    def coincide_dni(self, dni):
        return self.dni == dni

    def mostrar_datos(self):
        print("DNI:", self.dni)
        print("Nombres:", self.nombres)
        print("Apellidos:", self.apellidos)
        print("Fecha de nacimiento:", self.fecha_nacimiento)
