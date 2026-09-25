package centrosalud;

public class Medico extends Persona {
    private String cmp;
    private String especialidad;

    public Medico(String dni, String nombre, String fechaNacimiento,
                   String cmp, String especialidad) {
        super(dni, nombre, fechaNacimiento);
        this.cmp = cmp;
        this.especialidad = especialidad;
    }

    public String getCmp() {
        return cmp;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void atenderCita(CitaMedica cita) {
        System.out.println("El médico " + getNombre()
                + " está atendiendo la cita " + cita.getIdCita());
    }

    @Override
    public void mostrarDatos() {
        super.mostrarDatos();
        System.out.println("CMP: " + cmp);
        System.out.println("Especialidad: " + especialidad);
    }
}