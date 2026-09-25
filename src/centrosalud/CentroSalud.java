package centrosalud;

import java.util.ArrayList;
import java.util.List;

// Clase "registro": guarda en memoria todo lo que se va creando
// durante la ejecución del programa.
public class CentroSalud {
    private List<Paciente> pacientes;
    private List<Medico> medicos;
    private List<CitaMedica> citas;

    public CentroSalud() {
        pacientes = new ArrayList<>();
        medicos = new ArrayList<>();
        citas = new ArrayList<>();
    }

    public void registrarPaciente(Paciente paciente) {
        pacientes.add(paciente);
    }

    public void registrarMedico(Medico medico) {
        medicos.add(medico);
    }

    // Devuelve el tipo general Persona: no sabemos (ni nos importa)
    // si es Medico o Paciente hasta que se llame a mostrarDatos().
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
        return null; // no encontrado
    }

    // Este método es la conexión real que pidió tu profe:
    // busca al paciente y al médico por DNI entre los YA registrados.
    // Si cualquiera de los dos no existe, la cita no se crea.
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

        // instanceof ya confirmó el tipo real, así que el cast es seguro.
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
    }
}