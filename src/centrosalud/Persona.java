package centrosalud;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public abstract class Persona {
    private String dni;
    private String nombre;
    private LocalDate fechaNacimiento;

    private static final DateTimeFormatter FORMATO_FECHA =
            DateTimeFormatter.ofPattern("dd/MM/yyyy");

    // Ahora recibe la fecha de nacimiento como texto ("dd/MM/yyyy"),
    // no la edad directamente.
    public Persona(String dni, String nombre, String fechaNacimiento) {
        if (dni == null || !dni.matches("\\d{8}")) {
            throw new IllegalArgumentException("El DNI debe tener 8 dígitos.");
        }
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío.");
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
        this.nombre = nombre;
        this.fechaNacimiento = fecha;
    }

    public String getNombre() { return nombre; }

    public LocalDate getFechaNacimiento() { return fechaNacimiento; }

    // La edad ya NO se guarda como atributo: se calcula cada vez que se pide,
    // comparando la fecha de nacimiento con "hoy". Así nunca queda desactualizada.
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
        System.out.println("Nombre: " + nombre);
        System.out.println("Fecha de nacimiento: " + fechaNacimiento.format(FORMATO_FECHA));
        System.out.println("Edad: " + getEdad() + " años");
    }
}