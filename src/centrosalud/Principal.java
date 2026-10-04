package centrosalud;

import java.util.Scanner;

// Punto de entrada - versión consola
public class Principal {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        CentroSalud centro = new CentroSalud();
        int opcion;

        System.out.println("=== CENTRO DE SALUD 10 DE OCTUBRE ===");
        BaseDatos.cargarDatosDePrueba(centro);

        do {
            System.out.println("\n--- MENÚ ---");
            System.out.println("1. Registrar paciente");
            System.out.println("2. Registrar médico");
            System.out.println("3. Registrar atención médica (a un paciente)");
            System.out.println("4. Buscar persona por DNI");
            System.out.println("5. Listar todos los registrados");
            System.out.println("6. Registrar cita médica");
            System.out.println("7. Salir");
            System.out.print("Elige una opción: ");

            opcion = leerOpcion(sc);

            switch (opcion) {
                case 1:
                    registrarPaciente(sc, centro);
                    break;
                case 2:
                    registrarMedico(sc, centro);
                    break;
                case 3:
                    registrarAtencion(sc, centro);
                    break;
                case 4:
                    buscarPorDni(sc, centro);
                    break;
                case 5:
                    centro.listarTodos();
                    break;
                case 6:
                    registrarCita(sc, centro);
                    break;
                case 7:
                    System.out.println("Saliendo del sistema...");
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        } while (opcion != 7);

        sc.close();
    }

    // Si el usuario escribe letras, devuelve -1 en vez de cerrar el programa
    private static int leerOpcion(Scanner sc) {
        try {
            return Integer.parseInt(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private static void registrarPaciente(Scanner sc, CentroSalud centro) {
        try {
            System.out.print("DNI (8 dígitos): ");
            String dni = sc.nextLine().trim();
            System.out.print("Nombres: ");
            String nombres = sc.nextLine().trim();
            System.out.print("Apellidos: ");
            String apellidos = sc.nextLine().trim();
            System.out.print("Fecha de nacimiento (dd/MM/yyyy): ");
            String fechaNacimiento = sc.nextLine().trim();
            System.out.print("N° Historia clínica: ");
            String numeroHistoria = sc.nextLine().trim();

            Paciente paciente = new Paciente(dni, nombres, apellidos, fechaNacimiento, numeroHistoria);
            centro.registrarPaciente(paciente);
            System.out.println("Paciente registrado con éxito.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void registrarMedico(Scanner sc, CentroSalud centro) {
        try {
            System.out.print("DNI (8 dígitos): ");
            String dni = sc.nextLine().trim();
            System.out.print("Nombres: ");
            String nombres = sc.nextLine().trim();
            System.out.print("Apellidos: ");
            String apellidos = sc.nextLine().trim();
            System.out.print("Fecha de nacimiento (dd/MM/yyyy): ");
            String fechaNacimiento = sc.nextLine().trim();
            System.out.print("CMP: ");
            String cmp = sc.nextLine().trim();
            System.out.print("Especialidad: ");
            String especialidad = sc.nextLine().trim();

            Medico medico = new Medico(dni, nombres, apellidos, fechaNacimiento, cmp, especialidad);
            centro.registrarMedico(medico);
            System.out.println("Médico registrado con éxito.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // La atención siempre se registra sobre un paciente ya existente
    private static void registrarAtencion(Scanner sc, CentroSalud centro) {
        System.out.print("DNI del paciente: ");
        String dni = sc.nextLine().trim();

        Persona encontrada = centro.buscarPorDni(dni);

        if (!(encontrada instanceof Paciente)) {
            System.out.println("No existe un paciente registrado con ese DNI. "
                    + "Regístralo primero (opción 1).");
            return;
        }

        Paciente paciente = (Paciente) encontrada;

        try {
            System.out.print("ID Atención: ");
            String idAtencion = sc.nextLine().trim();
            if (centro.existeAtencion(idAtencion)) {
                System.out.println("Error: ya existe una atención con ese ID.");
                return;
            }
            System.out.print("Diagnóstico: ");
            String diagnostico = sc.nextLine().trim();
            System.out.print("Tratamiento: ");
            String tratamiento = sc.nextLine().trim();
            System.out.print("Observaciones: ");
            String observaciones = sc.nextLine().trim();

            AtencionMedica atencion = new AtencionMedica(
                    idAtencion, diagnostico, tratamiento, observaciones);

            paciente.agregarAtencion(atencion);

            System.out.println("Atención registrada en la historia clínica de "
                    + paciente.getNombreCompleto() + ".");

            recetarMedicamentos(sc, centro, atencion);

        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // Permite agregar medicamentos a la receta de la atención (baja el stock real)
    private static void recetarMedicamentos(Scanner sc, CentroSalud centro,
                                            AtencionMedica atencion) {
        System.out.println("\nMedicamentos disponibles:");
        for (Medicamento m : centro.getMedicamentos()) {
            System.out.println(" - " + m);
        }

        while (true) {
            System.out.print("Medicamento a recetar (Enter para terminar): ");
            String nombre = sc.nextLine().trim();
            if (nombre.isEmpty()) {
                break;
            }

            Medicamento medicamento = centro.buscarMedicamento(nombre);
            if (medicamento == null) {
                System.out.println("No existe ese medicamento.");
                continue;
            }

            System.out.print("Frecuencia (ej: cada 8 horas por 5 días): ");
            String frecuencia = sc.nextLine().trim();

            try {
                atencion.agregarMedicamento(medicamento, frecuencia);
                System.out.println("Medicamento agregado a la receta.");
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private static void registrarCita(Scanner sc, CentroSalud centro) {
        try {
            System.out.print("ID Cita: ");
            String idCita = sc.nextLine().trim();
            System.out.print("Fecha (dd/MM/yyyy): ");
            String fecha = sc.nextLine().trim();
            System.out.print("Motivo: ");
            String motivo = sc.nextLine().trim();
            System.out.print("DNI del paciente: ");
            String dniPaciente = sc.nextLine().trim();
            System.out.print("DNI del médico: ");
            String dniMedico = sc.nextLine().trim();

            CitaMedica cita = centro.registrarCita(idCita, fecha, motivo, dniPaciente, dniMedico);
            cita.programarCita();
            System.out.println("Cita registrada y programada con éxito.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // Polimorfismo: mostrarDatos() ejecuta la versión correcta sola
    private static void buscarPorDni(Scanner sc, CentroSalud centro) {
        System.out.print("Ingresa el DNI a buscar: ");
        String dni = sc.nextLine().trim();

        Persona encontrada = centro.buscarPorDni(dni);

        if (encontrada == null) {
            System.out.println("No se encontró ninguna persona con ese DNI.");
        } else {
            System.out.println("\n--- PERSONA ENCONTRADA ---");
            encontrada.mostrarDatos();
        }
    }
}