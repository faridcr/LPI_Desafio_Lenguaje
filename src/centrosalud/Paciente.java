package centrosalud;

public class Paciente extends Persona {
    private String historiaClinica;

    public Paciente(String dni, String nombre, int edad, String historiaClinica) {
        super(dni, nombre, edad);
        this.historiaClinica = historiaClinica;
    }

    public String getHistoriaClinica() {
        return historiaClinica;
    }

    public void solicitarCita() {
        System.out.println(nombre + " solicita una cita médica.");
    }

    public void consultarHistoria() {
        System.out.println("Historia clínica: " + historiaClinica);
    }
}
