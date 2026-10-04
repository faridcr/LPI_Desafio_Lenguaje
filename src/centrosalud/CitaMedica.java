package centrosalud;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

// Asociación entre Paciente y Medico (ambos ya deben existir)
public class CitaMedica {
    private static final DateTimeFormatter FORMATO_FECHA =
            DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private String idCita;
    private LocalDate fecha;
    private String estado;
    private String motivo;
    private Paciente paciente;
    private Medico medico;

    public CitaMedica(String idCita, String fecha, String motivo,
                      Paciente paciente, Medico medico) {

        if (paciente == null) {
            throw new IllegalArgumentException(
                    "No se puede crear una cita sin un paciente registrado.");
        }
        if (medico == null) {
            throw new IllegalArgumentException(
                    "No se puede crear una cita sin un médico registrado.");
        }
        if (idCita == null || idCita.isBlank()) {
            throw new IllegalArgumentException("El ID de la cita no puede estar vacío.");
        }
        if (motivo == null || motivo.isBlank()) {
            throw new IllegalArgumentException("El motivo de la cita no puede estar vacío.");
        }
        if (fecha == null || fecha.isBlank()) {
            throw new IllegalArgumentException("La fecha de la cita no puede estar vacía.");
        }

        LocalDate fechaCita;
        try {
            fechaCita = LocalDate.parse(fecha.trim(), FORMATO_FECHA);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException(
                    "La fecha de la cita debe tener el formato dd/MM/yyyy.");
        }
        if (fechaCita.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("La fecha de la cita no puede ser pasada.");
        }

        this.idCita = idCita.trim();
        this.fecha = fechaCita;
        this.motivo = motivo.trim();
        this.paciente = paciente;
        this.medico = medico;
        this.estado = "PENDIENTE";
    }

    public String getIdCita() { return idCita; }
    public String getFecha() { return fecha.format(FORMATO_FECHA); }
    public String getEstado() { return estado; }
    public String getMotivo() { return motivo; }
    public Paciente getPaciente() { return paciente; }
    public Medico getMedico() { return medico; }

    // Estado controlado: solo cambia con estas acciones
    public void programarCita() {
        if (estado.equals("CANCELADA") || estado.equals("ATENDIDA")) {
            throw new IllegalArgumentException(
                    "No se puede programar una cita que ya está " + estado + ".");
        }
        estado = "PROGRAMADA";
    }

    public void cancelarCita() {
        if (estado.equals("ATENDIDA")) {
            throw new IllegalArgumentException("No se puede cancelar una cita ya atendida.");
        }
        estado = "CANCELADA";
    }

    public void marcarAtendida() {
        if (estado.equals("CANCELADA")) {
            throw new IllegalArgumentException("No se puede atender una cita cancelada.");
        }
        estado = "ATENDIDA";
    }

    public void mostrarCita() {
        System.out.println("ID Cita: " + idCita);
        System.out.println("Fecha: " + getFecha());
        System.out.println("Estado: " + estado);
        System.out.println("Motivo: " + motivo);
        System.out.println("Paciente: " + paciente.getNombreCompleto());
        System.out.println("Médico: " + medico.getNombreCompleto()
                + " (" + medico.getEspecialidad() + ")");
    }
}