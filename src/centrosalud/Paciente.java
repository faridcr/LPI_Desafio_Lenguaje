package centrosalud;

public class Paciente extends Persona {
    private HistoriaClinica historiaClinica;

    public Paciente(String dni, String nombres, String apellidos, String fechaNacimiento,
                     String numeroHistoria) {
        super(dni, nombres, apellidos, fechaNacimiento);
        this.historiaClinica = new HistoriaClinica(numeroHistoria);
    }

    public HistoriaClinica getHistoriaClinica() {
        return historiaClinica;
    }

    public void solicitarCita() {
        System.out.println(getNombreCompleto() + " solicita una cita médica.");
    }

    public void agregarAtencion(AtencionMedica atencion) {
        historiaClinica.agregarAtencion(atencion);
    }

    public void consultarHistoria() {
        historiaClinica.mostrarHistoria();
    }

    @Override
    public void mostrarDatos() {
        super.mostrarDatos();
        System.out.println("N° Historia Clínica: " + historiaClinica.getNumeroHistoria());
        System.out.println("Atenciones registradas: "
                + historiaClinica.getAtenciones().size());
    }
}