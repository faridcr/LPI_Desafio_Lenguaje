package centrosalud;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

// Clase abstracta: base de la herencia (Medico y Paciente heredan de aquí)
public abstract class Persona {
    // Atributos privados -> encapsulamiento
    private String dni;
    private String nombres;
    private String apellidos;
    private LocalDate fechaNacimiento;

    private static final DateTimeFormatter FORMATO_FECHA =
            DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public Persona(String dni, String nombres, String apellidos, String fechaNacimiento) {
        // Validaciones en el constructor
        if (dni == null || !dni.matches("\\d{8}")) {
            throw new IllegalArgumentException("El DNI debe tener 8 dígitos.");
        }
        if (nombres == null || nombres.isBlank()) {
            throw new IllegalArgumentException("Los nombres no pueden estar vacíos.");
        }
        if (apellidos == null || apellidos.isBlank()) {
            throw new IllegalArgumentException("Los apellidos no pueden estar vacíos.");
        }

        LocalDate fecha;
        try {
            fecha = LocalDate.parse(fechaNacimiento, FORMATO_FECHA);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException(
                    "La fecha de nacimiento debe tener el formato dd/MM/yyyy.");
        }

        if (fecha.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("La fecha de nacimiento no puede ser futura.");
        }

        int edadCalculada = Period.between(fecha, LocalDate.now()).getYears();
        if (edadCalculada > 120) {
            throw new IllegalArgumentException("La fecha de nacimiento no es válida.");
        }

        this.dni = dni;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.fechaNacimiento = fecha;
    }

    public String getNombres() { return nombres; }
    public String getApellidos() { return apellidos; }

    public String getNombreCompleto() {
        return apellidos + " " + nombres;
    }

    public LocalDate getFechaNacimiento() { return fechaNacimiento; }

    // Edad calculada al vuelo, no se guarda como atributo
    public int getEdad() {
        return Period.between(fechaNacimiento, LocalDate.now()).getYears();
    }

    // Ley 29733: no se muestra el DNI completo
    public String getDniEnmascarado() {
        return "****" + dni.substring(4);
    }

    public boolean coincideDni(String dniConsulta) {
        return dni.equals(dniConsulta);
    }

    // Sobrescrito en Medico y Paciente -> polimorfismo
    public void mostrarDatos() {
        System.out.println("DNI: " + getDniEnmascarado());
        System.out.println("Apellidos: " + apellidos);
        System.out.println("Nombres: " + nombres);
        System.out.println("Fecha de nacimiento: " + fechaNacimiento.format(FORMATO_FECHA));
        System.out.println("Edad: " + getEdad() + " años");
    }
}