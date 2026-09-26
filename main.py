from servicio.centro_salud import CentroSalud


def mostrar_menu():
    print("\n========================================")
    print("   CENTRO DE SALUD 10 DE OCTUBRE")
    print("========================================")
    print("1. Registrar paciente")
    print("2. Registrar médico")
    print("3. Registrar atención")
    print("4. Buscar por DNI")
    print("5. Listar pacientes y médicos")
    print("6. Salir")


def registrar_paciente(centro):
    print("\n=== REGISTRAR PACIENTE ===")

    dni = input("DNI: ")
    nombre = input("Nombre completo: ")

    try:
        edad = int(input("Edad: "))
        historia = input("N° Historia clínica: ")

        paciente = centro.registrar_paciente(
            dni, nombre, edad, historia
        )

        print("\nPaciente registrado correctamente.")
        print(paciente.mostrar_datos())

    except ValueError as error:
        print("\nError:", error)


def registrar_medico(centro):
    print("\n=== REGISTRAR MÉDICO ===")

    dni = input("DNI: ")
    nombre = input("Nombre completo: ")

    try:
        edad = int(input("Edad: "))
        cmp = input("CMP: ")
        especialidad = input("Especialidad: ")

        medico = centro.registrar_medico(
            dni, nombre, edad, cmp, especialidad
        )

        print("\nMédico registrado correctamente.")
        print(medico.mostrar_datos())

    except ValueError as error:
        print("\nError:", error)


def registrar_atencion(centro):
    print("\n=== REGISTRAR ATENCIÓN ===")

    dni = input("DNI del paciente: ")
    id_atencion = input("ID Atención: ")
    diagnostico = input("Diagnóstico: ")
    tratamiento = input("Tratamiento: ")
    observaciones = input("Observaciones: ")

    try:
        atencion = centro.registrar_atencion(
            dni,
            id_atencion,
            diagnostico,
            tratamiento,
            observaciones
        )

        paciente = centro.buscar_por_dni(dni)

        print("\nAtención registrada correctamente.")
        print("Paciente:", paciente.nombre)
        print("Historia clínica:", paciente.historia_clinica)
        print("ID Atención:", atencion.id_atencion)
        print("Diagnóstico:", atencion.diagnostico)
        print("Tratamiento:", atencion.tratamiento)

    except ValueError as error:
        print("\nError:", error)


def buscar_persona(centro):
    print("\n=== BUSCAR POR DNI ===")
    dni = input("Ingrese DNI: ")

    persona = centro.buscar_por_dni(dni)

    if persona is None:
        print("\nNo se encontró ninguna persona con ese DNI.")
    else:
        print("\n=== PERSONA ENCONTRADA ===")
        print(persona.mostrar_datos())

        if persona.rol == "Paciente" and persona.atenciones:
            print("\n=== ATENCIONES ===")

            for atencion in persona.atenciones:
                print("\nID:", atencion.id_atencion)
                print("Diagnóstico:", atencion.diagnostico)
                print("Tratamiento:", atencion.tratamiento)
                print("Observaciones:", atencion.observaciones)
                print("Observado por:", atencion.medico.nombre)

def main():
    centro = CentroSalud()

    while True:
        mostrar_menu()
        opcion = input("\nSeleccione una opción: ")

        if opcion == "1":
            registrar_paciente(centro)

        elif opcion == "2":
            registrar_medico(centro)

        elif opcion == "3":
            registrar_atencion(centro)

        elif opcion == "4":
            buscar_persona(centro)

        elif opcion == "5":
            print()
            print(centro.listar_todos())

        elif opcion == "6":
            print("\nPrograma finalizado.")
            break

        else:
            print("\nOpción no válida. Intente nuevamente.")


if __name__ == "__main__":
    main()
