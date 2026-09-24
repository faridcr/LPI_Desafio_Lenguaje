package centrosalud;

public abstract class Persona {
    protected String dni;
    protected String nombre;
    protected int edad;

    public Persona(String dni, String nombre, int edad) {
        this.dni = dni;
        this.nombre = nombre;
        this.edad = edad;
    }

    public String getDni() { return dni; }
    public String getNombre() { return nombre; }
    public int getEdad() { return edad; }

    public void mostrarDatos() {
        System.out.println("DNI: " + dni);
        System.out.println("Nombre: " + nombre);
        System.out.println("Edad: " + edad);
    }
}
