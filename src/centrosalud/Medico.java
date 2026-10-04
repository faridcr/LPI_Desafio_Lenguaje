package centrosalud;

// Herencia: Medico extiende Persona
public class Medico extends Persona {
    private String cmp;
    private String especialidad;

    public Medico(String dni, String nombres, String apellidos, String fechaNacimiento,
                  String cmp, String especialidad) {
        super(dni, nombres, apellidos, fechaNacimiento); // llama al constructor de Persona

        if (cmp == null || cmp.isBlank()) {
            throw new IllegalArgumentException("El CMP no puede estar vacío.");
        }
        if (especialidad == null || especialidad.isBlank()) {
            throw new IllegalArgumentException("La especialidad no puede estar vacía.");
        }

        this.cmp = cmp.trim();
        this.especialidad = especialidad.trim();
    }

    public String getCmp() {
        return cmp;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    // Atiende una cita: solo el médico asignado puede hacerlo
    public void atenderCita(CitaMedica cita) {
        if (cita == null) {
            throw new IllegalArgumentException("La cita no puede ser nula.");
        }
        if (cita.getMedico() != this) {
            throw new IllegalArgumentException("Esta cita pertenece a otro médico.");
        }
        cita.marcarAtendida();
        System.out.println("El médico " + getNombreCompleto()
                + " atendió la cita " + cita.getIdCita());
    }

    // Polimorfismo: sobrescribe mostrarDatos() de Persona
    @Override
    public void mostrarDatos() {
        super.mostrarDatos();
        System.out.println("CMP: " + cmp);
        System.out.println("Especialidad: " + especialidad);
    }
}