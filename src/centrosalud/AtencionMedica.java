package centrosalud;

public class AtencionMedica {
    private String idAtencion;
    private String diagnostico;
    private String tratamiento;
    private String observaciones;

    public AtencionMedica(String idAtencion, String diagnostico,
                          String tratamiento, String observaciones) {
        this.idAtencion = idAtencion;
        this.diagnostico = diagnostico;
        this.tratamiento = tratamiento;
        this.observaciones = observaciones;
    }

    public String getIdAtencion() { return idAtencion; }
    public String getDiagnostico() { return diagnostico; }
    public String getTratamiento() { return tratamiento; }
    public String getObservaciones() { return observaciones; }

    public void mostrarAtencion() {
        System.out.println("ID Atención: " + idAtencion);
        System.out.println("Diagnóstico: " + diagnostico);
        System.out.println("Tratamiento: " + tratamiento);
        System.out.println("Observaciones: " + observaciones);
    }
}
