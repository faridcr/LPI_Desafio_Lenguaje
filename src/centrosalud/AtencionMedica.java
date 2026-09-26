package centrosalud;

public class AtencionMedica {
    private String idAtencion;
    private String diagnostico;
    private String tratamiento;
    private String observaciones;
    private Receta receta;

    public AtencionMedica(String idAtencion, String diagnostico,
                          String tratamiento, String observaciones) {
        this.idAtencion = idAtencion;
        this.diagnostico = diagnostico;
        this.tratamiento = tratamiento;
        this.observaciones = observaciones;
        this.receta = new Receta(); // toda atención nace con su propia receta, vacía
    }

    public String getIdAtencion() { return idAtencion; }
    public String getDiagnostico() { return diagnostico; }
    public String getTratamiento() { return tratamiento; }
    public String getObservaciones() { return observaciones; }
    public Receta getReceta() { return receta; }

    // Delega en Receta, pasando también la frecuencia de toma escrita a mano.
    public void agregarMedicamento(Medicamento medicamento, String frecuencia) {
        receta.agregarMedicamento(medicamento, frecuencia);
    }

    public void mostrarAtencion() {
        System.out.println("ID Atención: " + idAtencion);
        System.out.println("Diagnóstico: " + diagnostico);
        System.out.println("Tratamiento: " + tratamiento);
        System.out.println("Observaciones: " + observaciones);
        receta.mostrarMedicamentos();
    }
}