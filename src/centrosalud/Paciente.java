package centrosalud;

// Herencia: Paciente extiende Persona
public class Paciente extends Persona {
    // Composición: cada paciente tiene su propia historia clínica
    private HistoriaClinica historiaClinica;

    public Paciente(String dni, String nombres, String apellidos, String fechaNacimiento,
                     String numeroHistoria) {
        super(dni, nombres, apellidos, fechaNacimiento);
        this.historiaClinica = new HistoriaClinica(numeroHistoria); // se crea automáticamente
    }

    public HistoriaClinica getHistoriaClinica() {
        return historiaClinica;
    }

    public void solicitarCita() {
        System.out.println(getNombreCompleto() + " solicita una cita médica.");
    }

    // Delega en HistoriaClinica
    public void agregarAtencion(AtencionMedica atencion) {
        historiaClinica.agregarAtencion(atencion);
    }

    public void consultarHistoria() {
        historiaClinica.mostrarHistoria();
    }

    // Polimorfismo: sobrescribe mostrarDatos() de Persona
    @Override
    public void mostrarDatos() {
        super.mostrarDatos();
        System.out.println("N° Historia Clínica: " + historiaClinica.getNumeroHistoria());
        System.out.println("Atenciones registradas: "
                + historiaClinica.getAtenciones().size());
    }
}