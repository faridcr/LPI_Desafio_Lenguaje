package centrosalud;

public class Paciente extends Persona {
    private HistoriaClinica historiaClinica; // ya no es String: es un objeto real

    // Sigue recibiendo el número como texto (para no complicar el registro),
    // pero internamente crea el objeto HistoriaClinica de verdad.
    public Paciente(String dni, String nombre, String fechaNacimiento,
                     String numeroHistoria) {
        super(dni, nombre, fechaNacimiento);
        this.historiaClinica = new HistoriaClinica(numeroHistoria);
    }

    public HistoriaClinica getHistoriaClinica() {
        return historiaClinica;
    }

    public void solicitarCita() {
        System.out.println(getNombre() + " solicita una cita médica.");
    }

    // Delega en HistoriaClinica: el Paciente no guarda las atenciones él
    // mismo, se las pasa a su historia clínica, que es la responsable.
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