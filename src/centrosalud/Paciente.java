package centrosalud;

public class Paciente extends Persona {
    private String historiaClinica;

    public Paciente(String dni, String nombre, String fechaNacimiento,
                     String historiaClinica) {
        super(dni, nombre, fechaNacimiento);
        this.historiaClinica = historiaClinica;
    }

    public String getHistoriaClinica() {
        return historiaClinica;
    }

    public void solicitarCita() {
        System.out.println(getNombre() + " solicita una cita médica.");
    }

    public void consultarHistoria() {
        System.out.println("Historia clínica: " + historiaClinica);
    }

    @Override
    public void mostrarDatos() {
        super.mostrarDatos();
        System.out.println("Historia clínica: " + historiaClinica);
    }
}