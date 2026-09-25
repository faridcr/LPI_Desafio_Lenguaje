package centrosalud;

import java.util.Scanner;

public class Principal {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        CentroSalud centro = new CentroSalud();
        int opcion;

        System.out.println("=== CENTRO DE SALUD 10 DE OCTUBRE ===");

        do {
            System.out.println("\n--- MENÚ ---");
            System.out.println("1. Registrar paciente");
            System.out.println("2. Registrar médico");
            System.out.println("3. Buscar persona por DNI");
            System.out.println("4. Listar todos los registrados");
            System.out.println("5. Salir");
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
                    buscarPorDni(sc, centro);
                    break;
                case 4:
                    centro.listarTodos();
                    break;
                case 5:
                    System.out.println("Saliendo del sistema...");
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        } while (opcion != 5);

        sc.close();
    }

    private static void registrarPaciente(Scanner sc, CentroSalud centro) {
        try {
            System.out.print("DNI (8 dígitos): ");
            String dni = sc.nextLine().trim();
            System.out.print("Nombre: ");
            String nombre = sc.nextLine().trim();
            System.out.print("Fecha de nacimiento (dd/MM/yyyy): ");
            String fechaNacimiento = sc.nextLine().trim();
            System.out.print("Historia clínica: ");
            String historia = sc.nextLine().trim();

            Paciente paciente = new Paciente(dni, nombre, fechaNacimiento, historia);
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
            System.out.print("Nombre: ");
            String nombre = sc.nextLine().trim();
            System.out.print("Fecha de nacimiento (dd/MM/yyyy): ");
            String fechaNacimiento = sc.nextLine().trim();
            System.out.print("CMP: ");
            String cmp = sc.nextLine().trim();
            System.out.print("Especialidad: ");
            String especialidad = sc.nextLine().trim();

            Medico medico = new Medico(dni, nombre, fechaNacimiento, cmp, especialidad);
            centro.registrarMedico(medico);
            System.out.println("Médico registrado con éxito.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
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