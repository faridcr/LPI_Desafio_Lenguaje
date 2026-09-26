package centrosalud;

import java.util.ArrayList;
import java.util.List;

// Clase controladora: junta pacientes, médicos, citas y medicamentos
public class CentroSalud {
    private List<Paciente> pacientes;
    private List<Medico> medicos;
    private List<CitaMedica> citas;
    private List<Medicamento> medicamentos;

    public CentroSalud() {
        pacientes = new ArrayList<>();
        medicos = new ArrayList<>();
        citas = new ArrayList<>();
        medicamentos = new ArrayList<>();
    }

    public void registrarMedicamento(Medicamento medicamento) {
        medicamentos.add(medicamento);
    }

    public Medicamento buscarMedicamento(String nombre) {
        for (Medicamento m : medicamentos) {
            if (m.getNombre().equalsIgnoreCase(nombre)) {
                return m;
            }
        }
        return null;
    }

    // Devuelve una copia: protege la lista real de medicamentos
    public List<Medicamento> getMedicamentos() {
        return new ArrayList<>(medicamentos);
    }

    public void registrarPaciente(Paciente paciente) {
        pacientes.add(paciente);
    }

    public void registrarMedico(Medico medico) {
        medicos.add(medico);
    }

    // Devuelve Persona -> polimorfismo (puede ser Medico o Paciente)
    public Persona buscarPorDni(String dni) {
        for (Paciente p : pacientes) {
            if (p.coincideDni(dni)) {
                return p;
            }
        }
        for (Medico m : medicos) {
            if (m.coincideDni(dni)) {
                return m;
            }
        }
        return null;
    }

    // Conexión real: valida que paciente y médico ya existan antes de crear la cita
    public CitaMedica registrarCita(String idCita, String fecha, String motivo,
                                     String dniPaciente, String dniMedico) {

        Persona posiblePaciente = buscarPorDni(dniPaciente);
        if (!(posiblePaciente instanceof Paciente)) {
            throw new IllegalArgumentException(
                    "No existe un paciente registrado con el DNI " + dniPaciente + ".");
        }

        Persona posibleMedico = buscarPorDni(dniMedico);
        if (!(posibleMedico instanceof Medico)) {
            throw new IllegalArgumentException(
                    "No existe un médico registrado con el DNI " + dniMedico + ".");
        }

        Paciente paciente = (Paciente) posiblePaciente;
        Medico medico = (Medico) posibleMedico;

        CitaMedica cita = new CitaMedica(idCita, fecha, motivo, paciente, medico);
        citas.add(cita);
        return cita;
    }

    public void listarCitas() {
        System.out.println("\n--- CITAS REGISTRADAS (" + citas.size() + ") ---");
        for (CitaMedica c : citas) {
            c.mostrarCita();
            System.out.println();
        }
    }

    public void listarTodos() {
        System.out.println("\n--- PACIENTES REGISTRADOS (" + pacientes.size() + ") ---");
        for (Paciente p : pacientes) {
            p.mostrarDatos();
            System.out.println();
        }

        System.out.println("--- MÉDICOS REGISTRADOS (" + medicos.size() + ") ---");
        for (Medico m : medicos) {
            m.mostrarDatos();
            System.out.println();
        }

        listarCitas();

        System.out.println("--- MEDICAMENTOS EN STOCK (" + medicamentos.size() + ") ---");
        for (Medicamento m : medicamentos) {
            m.mostrarMedicamento();
        }
    }
}