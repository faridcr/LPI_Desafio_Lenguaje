from centro_salud import CentroSalud
from paciente import Paciente
from medico import Medico
from atencion_medica import AtencionMedica
from base_datos import cargar_datos

centro = CentroSalud()
cargar_datos(centro)

opcion = 0

while opcion != 6:
    print("\n=== CENTRO DE SALUD 10 DE OCTUBRE ===")
    print("1. Registrar paciente")
    print("2. Registrar medico")
    print("3. Registrar atencion medica")
    print("4. Buscar persona por DNI")
    print("5. Listar todos")
    print("6. Salir")

    try:
        opcion = int(input("Elige una opcion: "))
    except ValueError:
        print("ERROR: Debes ingresar un numero del 1 al 6")
        continue

    if opcion == 1:
        print("\n--- REGISTRAR PACIENTE ---")
        try:
            dni = input("DNI: ")
            nombres = input("Nombres: ")
            apellidos = input("Apellidos: ")
            fecha = input("Fecha de nacimiento (dd/mm/aaaa): ")
            historia = input("Numero de historia clinica: ")

            if historia == "":
                raise ValueError("El numero de historia clinica no puede estar vacio")

            if centro.buscar_por_dni(dni) != None:
                raise ValueError("Ya existe una persona con ese DNI")

            paciente = Paciente(dni, nombres, apellidos, fecha, historia)
            centro.registrar_paciente(paciente)
            print("Paciente registrado correctamente")

        except ValueError as error:
            print("ERROR:", error)

    elif opcion == 2:
        print("\n--- REGISTRAR MEDICO ---")
        try:
            dni = input("DNI: ")
            nombres = input("Nombres: ")
            apellidos = input("Apellidos: ")
            fecha = input("Fecha de nacimiento (dd/mm/aaaa): ")
            cmp = input("CMP: ")
            especialidad = input("Especialidad: ")

            if cmp == "":
                raise ValueError("El CMP no puede estar vacio")

            if especialidad == "":
                raise ValueError("La especialidad no puede estar vacia")

            if centro.buscar_por_dni(dni) != None:
                raise ValueError("Ya existe una persona con ese DNI")

            medico = Medico(dni, nombres, apellidos, fecha, cmp, especialidad)
            centro.registrar_medico(medico)
            print("Medico registrado correctamente")

        except ValueError as error:
            print("ERROR:", error)

    elif opcion == 3:
        print("\n--- REGISTRAR ATENCION MEDICA ---")
        dni = input("DNI del paciente: ")

        if len(dni) != 8 or dni.isdigit() == False:
            print("ERROR: El DNI debe tener exactamente 8 numeros")
        else:
            persona = centro.buscar_por_dni(dni)

            if persona == None:
                print("ERROR: No se encontro el paciente")
            elif persona not in centro.pacientes:
                print("ERROR: El DNI ingresado no pertenece a un paciente")
            else:
                id_atencion = input("ID Atencion: ")
                diagnostico = input("Diagnostico: ")
                tratamiento = input("Tratamiento: ")
                observaciones = input("Observaciones: ")

                if id_atencion == "":
                    print("ERROR: El ID de atencion no puede estar vacio")
                elif diagnostico == "":
                    print("ERROR: El diagnostico no puede estar vacio")
                elif tratamiento == "":
                    print("ERROR: El tratamiento no puede estar vacio")
                else:
                    atencion = AtencionMedica(id_atencion, diagnostico, tratamiento, observaciones)
                    persona.agregar_atencion(atencion)
                    print("Atencion registrada correctamente")

                    agregar = 0
                    while agregar != 2:
                        try:
                            agregar = int(input("Desea agregar medicamento? 1. Si  2. No: "))
                        except ValueError:
                            print("ERROR: Debes ingresar 1 o 2")
                            continue

                        if agregar == 1:
                            print("\n--- MEDICAMENTOS DISPONIBLES ---")
                            for i in range(len(centro.medicamentos)):
                                print(i + 1, centro.medicamentos[i].nombre, "- Stock:", centro.medicamentos[i].stock)

                            try:
                                opcion_medicamento = int(input("Seleccione medicamento: "))
                            except ValueError:
                                print("ERROR: Debes ingresar solo numeros")
                                continue

                            if opcion_medicamento < 1 or opcion_medicamento > len(centro.medicamentos):
                                print("ERROR: Medicamento no valido")
                            else:
                                medicamento = centro.medicamentos[opcion_medicamento - 1]

                                if medicamento.stock <= 0:
                                    print("ERROR: No hay stock disponible")
                                else:
                                    frecuencia = input("Frecuencia: ")
                                    if frecuencia == "":
                                        print("ERROR: La frecuencia no puede estar vacia")
                                    else:
                                        atencion.agregar_medicamento(medicamento, frecuencia)
                                        print("Medicamento agregado correctamente")

                        elif agregar != 2:
                            print("ERROR: Debes ingresar 1 o 2")

    elif opcion == 4:
        print("\n--- BUSCAR PERSONA POR DNI ---")
        dni = input("DNI a buscar: ")

        if len(dni) != 8 or dni.isdigit() == False:
            print("ERROR: El DNI debe tener exactamente 8 numeros")
        else:
            persona = centro.buscar_por_dni(dni)
            if persona == None:
                print("No se encontro ninguna persona")
            else:
                persona.mostrar_datos()

    elif opcion == 5:
        centro.listar_todos()

    elif opcion == 6:
        print("Saliendo del sistema...")

    else:
        print("ERROR: Opcion no valida")
