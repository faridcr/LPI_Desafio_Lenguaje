package centrosalud;

import java.util.Scanner;

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
            System.out.println("6. Salir");
            System.out.print("Elige una opción: ");

            opcion = Integer.parseInt(sc.nextLine().trim());

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
                    System.out.println("Saliendo del sistema...");
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        } while (opcion != 6);

        sc.close();
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

    // Ahora la atención SIEMPRE se registra sobre un paciente ya existente.
    // Si el DNI no corresponde a ningún paciente registrado, no se crea.
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

        System.out.print("ID Atención: ");
        String idAtencion = sc.nextLine().trim();
        System.out.print("Diagnóstico: ");
        String diagnostico = sc.nextLine().trim();
        System.out.print("Tratamiento: ");
        String tratamiento = sc.nextLine().trim();
        System.out.print("Observaciones: ");
        String observaciones = sc.nextLine().trim();

        AtencionMedica atencion = new AtencionMedica(
                idAtencion, diagnostico, tratamiento, observaciones);

        // Conexión real: la atención queda dentro de la historia clínica
        // de ESE paciente en particular, no suelta en ningún lado.
        paciente.agregarAtencion(atencion);

        System.out.println("Atención registrada en la historia clínica de "
                + paciente.getNombreCompleto() + ".");
    }

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