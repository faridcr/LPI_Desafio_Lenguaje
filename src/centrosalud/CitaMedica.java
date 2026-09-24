package centrosalud;

public class CitaMedica {
    private String idCita;
    private String fecha;
    private String estado;
    private String motivo;

    public CitaMedica(String idCita, String fecha, String estado, String motivo) {
        this.idCita = idCita;
        this.fecha = fecha;
        this.estado = estado;
        this.motivo = motivo;
    }

    public String getIdCita() { return idCita; }
    public String getFecha() { return fecha; }
    public String getEstado() { return estado; }
    public String getMotivo() { return motivo; }

    public void programarCita() {
        estado = "PROGRAMADA";
    }

    public void cancelarCita() {
        estado = "CANCELADA";
    }
}
