package centrosalud;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public abstract class Persona {
    private String dni;
    private String nombres;
    private String apellidos;
    private LocalDate fechaNacimiento;

    private static final DateTimeFormatter FORMATO_FECHA =
            DateTimeFormatter.ofPattern("dd/MM/yyyy");

    // Ahora nombres y apellidos van separados, como en el DNI real.
    public Persona(String dni, String nombres, String apellidos, String fechaNacimiento) {
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

    // Método de conveniencia: junta ambos para mostrarlos en mensajes,
    // sin obligar a todo el resto del código a concatenarlos cada vez.
    public String getNombreCompleto() {
        return apellidos + " " + nombres;
    }

    public LocalDate getFechaNacimiento() { return fechaNacimiento; }

    public int getEdad() {
        return Period.between(fechaNacimiento, LocalDate.now()).getYears();
    }

    // Ley N.° 29733: el DNI completo no se muestra
    public String getDniEnmascarado() {
        return "****" + dni.substring(4);
    }

    public boolean coincideDni(String dniConsulta) {
        return dni.equals(dniConsulta);
    }

    public void mostrarDatos() {
        System.out.println("DNI: " + getDniEnmascarado());
        System.out.println("Apellidos: " + apellidos);
        System.out.println("Nombres: " + nombres);
        System.out.println("Fecha de nacimiento: " + fechaNacimiento.format(FORMATO_FECHA));
        System.out.println("Edad: " + getEdad() + " años");
    }
}