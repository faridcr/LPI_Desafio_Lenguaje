package centrosalud;

public class CitaMedica {
    private String idCita;
    private String fecha;
    private String estado;
    private String motivo;
    private Paciente paciente; // ya no es texto: es el objeto real
    private Medico medico;     // ya no es texto: es el objeto real

    // La cita ahora EXIGE un Paciente y un Medico ya existentes.
    // Si cualquiera de los dos es null, la cita no se puede crear:
    // así se cumple literalmente "si no existe uno, el otro no funciona".
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
        this.estado = "PENDIENTE"; // toda cita nace pendiente hasta programarse
    }

    public String getIdCita() { return idCita; }
    public String getFecha() { return fecha; }
    public String getEstado() { return estado; }
    public String getMotivo() { return motivo; }

    // Devolvemos los objetos reales, no solo su nombre o DNI.
    public Paciente getPaciente() { return paciente; }
    public Medico getMedico() { return medico; }

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
        // Usamos los datos REALES del paciente y del médico conectados,
        // no texto suelto escrito a mano.
        System.out.println("Paciente: " + paciente.getNombre());
        System.out.println("Médico: " + medico.getNombre()
                + " (" + medico.getEspecialidad() + ")");
    }
}