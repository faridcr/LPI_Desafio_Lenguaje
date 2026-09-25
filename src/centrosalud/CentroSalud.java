package centrosalud;

import java.util.ArrayList;
import java.util.List;

// Clase "registro": guarda en memoria todo lo que se va creando
// durante la ejecución del programa (mientras el programa corre,
// los datos "se mantienen guardados").
public class CentroSalud {
    private List<Paciente> pacientes;
    private List<Medico> medicos;

    public CentroSalud() {
        pacientes = new ArrayList<>();
        medicos = new ArrayList<>();
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
    }
}