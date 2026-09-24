package centrosalud;

public class Medico extends Persona {
    private String cmp;
    private String especialidad;

    public Medico(String dni, String nombre, int edad, String cmp, String especialidad) {
        super(dni, nombre, edad);
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
}