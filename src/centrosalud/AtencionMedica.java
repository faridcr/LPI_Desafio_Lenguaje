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

    // Delega en Receta: la atención no maneja el stock ella misma,
    // se lo pasa a SU receta, que es quien realmente controla eso.
    public void agregarMedicamento(Medicamento medicamento) {
        receta.agregarMedicamento(medicamento);
    }

    public void mostrarAtencion() {
        System.out.println("ID Atención: " + idAtencion);
        System.out.println("Diagnóstico: " + diagnostico);
        System.out.println("Tratamiento: " + tratamiento);
        System.out.println("Observaciones: " + observaciones);
        receta.mostrarMedicamentos();
    }
}