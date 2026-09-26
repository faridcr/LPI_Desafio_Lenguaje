package centrosalud;

// Asociación entre Paciente y Medico (ambos ya deben existir)
public class CitaMedica {
    private String idCita;
    private String fecha;
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

        this.idCita = idCita;
        this.fecha = fecha;
        this.motivo = motivo;
        this.paciente = paciente;
        this.medico = medico;
        this.estado = "PENDIENTE";
    }

    public String getIdCita() { return idCita; }
    public String getFecha() { return fecha; }
    public String getEstado() { return estado; }
    public String getMotivo() { return motivo; }
    public Paciente getPaciente() { return paciente; }
    public Medico getMedico() { return medico; }

    // Estado controlado: solo cambia con estas acciones
    public void programarCita() {
        estado = "PROGRAMADA";
    }

    public void cancelarCita() {
        estado = "CANCELADA";
    }

    public void mostrarCita() {
        System.out.println("ID Cita: " + idCita);
        System.out.println("Fecha: " + fecha);
        System.out.println("Estado: " + estado);
        System.out.println("Motivo: " + motivo);
        System.out.println("Paciente: " + paciente.getNombreCompleto());
        System.out.println("Médico: " + medico.getNombreCompleto()
                + " (" + medico.getEspecialidad() + ")");
    }
}