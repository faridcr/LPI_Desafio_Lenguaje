package centrosalud;

// Carga datos de prueba usando las mismas clases y validaciones del modelo
public class BaseDatos {

    public static void cargarDatosDePrueba(CentroSalud centro) {
        try {
            // Integrantes del grupo, registrados como pacientes
            Paciente kevin = new Paciente("70531811", "Kevin Hernan", "Astete Llancare",
                    "31/01/2002", "HC-001");
            Paciente andy = new Paciente("75769516", "Andy Cristhofer", "Acosta Guillen",
                    "01/09/2004", "HC-002");
            Paciente josue = new Paciente("74277338", "Josue David", "Mazuelos Valqui",
                    "01/03/2001", "HC-003");
            Paciente cesar = new Paciente("74973481", "Cesar Farid", "Cruz Cruces",
                    "29/04/2006", "HC-004");

            centro.registrarPaciente(kevin);
            centro.registrarPaciente(andy);
            centro.registrarPaciente(josue);
            centro.registrarPaciente(cesar);

            // Médicos de ejemplo
            Medico m1 = new Medico("12345678", "Carlos", "Torres Diaz",
                    "10/01/1985", "CMP-12345", "Medicina General");
            Medico m2 = new Medico("12345679", "Ana", "Ramirez Soto",
                    "05/09/1980", "CMP-12346", "Pediatría");

            centro.registrarMedico(m1);
            centro.registrarMedico(m2);

            // Inventario inicial de medicamentos
            centro.registrarMedicamento(new Medicamento("Paracetamol", 50));
            centro.registrarMedicamento(new Medicamento("Ibuprofeno", 30));
            centro.registrarMedicamento(new Medicamento("Amoxicilina", 20));
            centro.registrarMedicamento(new Medicamento("Loratadina", 40));
            centro.registrarMedicamento(new Medicamento("Omeprazol", 25));
            centro.registrarMedicamento(new Medicamento("Metformina", 35));
            centro.registrarMedicamento(new Medicamento("Aspirina", 60));
            centro.registrarMedicamento(new Medicamento("Azitromicina", 15));

            System.out.println("Datos de prueba cargados: "
                    + "4 pacientes (equipo), 2 médicos y 8 medicamentos ya registrados.");

        } catch (IllegalArgumentException e) {
            System.out.println("Error al cargar datos de prueba: " + e.getMessage());
        }
    }
}