package centrosalud;

public abstract class Persona {
    private String dni;
    private String nombre;
    private int edad;

    public Persona(String dni, String nombre, int edad) {
        if (dni == null || !dni.matches("\\d{8}")) {
            throw new IllegalArgumentException("El DNI debe tener 8 dígitos.");
        }
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío.");
        }
        if (edad < 0 || edad > 120) {
            throw new IllegalArgumentException("La edad no es válida.");
        }
        this.dni = dni;
        this.nombre = nombre;
        this.edad = edad;
    }

    public String getNombre() { return nombre; }
    public int getEdad() { return edad; }

    // Ley N.° 29733: el DNI completo no se muestra
    public String getDniEnmascarado() {
        return "****" + dni.substring(4);
    }

    public void mostrarDatos() {
        System.out.println("DNI: " + getDniEnmascarado());
        System.out.println("Nombre: " + nombre);
        System.out.println("Edad: " + edad);
    }
}