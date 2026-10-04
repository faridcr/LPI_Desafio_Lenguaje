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

    // ---------------- Medicamentos ----------------

    public void registrarMedicamento(Medicamento medicamento) {
        if (medicamento == null) {
            throw new IllegalArgumentException("El medicamento no puede ser nulo.");
        }
        if (buscarMedicamento(medicamento.getNombre()) != null) {
            throw new IllegalArgumentException(
                    "Ya existe un medicamento llamado " + medicamento.getNombre() + ".");
        }
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

    // ---------------- Personas ----------------

    // Un DNI no puede repetirse entre pacientes ni médicos
    private boolean existeDni(Persona persona) {
        for (Paciente p : pacientes) {
            if (p.tieneMismoDni(persona)) {
                return true;
            }
        }
        for (Medico m : medicos) {
            if (m.tieneMismoDni(persona)) {
                return true;
            }
        }
        return false;
    }

    public void registrarPaciente(Paciente paciente) {
        if (paciente == null) {
            throw new IllegalArgumentException("El paciente no puede ser nulo.");
        }
        if (existeDni(paciente)) {
            throw new IllegalArgumentException("Ya existe una persona registrada con ese DNI.");
        }
        pacientes.add(paciente);
    }

    public void registrarMedico(Medico medico) {
        if (medico == null) {
            throw new IllegalArgumentException("El médico no puede ser nulo.");
        }
        if (existeDni(medico)) {
            throw new IllegalArgumentException("Ya existe una persona registrada con ese DNI.");
        }
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

    // ---------------- Atenciones ----------------

    // Reúne las atenciones de todos los pacientes (para los reportes)
    public List<AtencionMedica> getTodasLasAtenciones() {
        List<AtencionMedica> todas = new ArrayList<>();
        for (Paciente p : pacientes) {
            todas.addAll(p.getHistoriaClinica().getAtenciones());
        }
        return todas;
    }

    public boolean existeAtencion(String idAtencion) {
        for (AtencionMedica a : getTodasLasAtenciones()) {
            if (a.getIdAtencion().equalsIgnoreCase(idAtencion)) {
                return true;
            }
        }
        return false;
    }

    // ---------------- Citas ----------------

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

        for (CitaMedica c : citas) {
            if (c.getIdCita().equalsIgnoreCase(idCita == null ? "" : idCita.trim())) {
                throw new IllegalArgumentException("Ya existe una cita con el ID " + idCita + ".");
            }
        }

        Paciente paciente = (Paciente) posiblePaciente;
        Medico medico = (Medico) posibleMedico;

        CitaMedica cita = new CitaMedica(idCita, fecha, motivo, paciente, medico);
        citas.add(cita);
        return cita;
    }

    // Copia: protege la lista real de citas
    public List<CitaMedica> getCitas() {
        return new ArrayList<>(citas);
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