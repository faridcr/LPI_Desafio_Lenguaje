package centrosalud;

public class Medicamento {
    private String nombre;
    private int stock;

    public Medicamento(String nombre, int stockInicial) {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre del medicamento no puede estar vacío.");
        }
        if (stockInicial < 0) {
            throw new IllegalArgumentException("El stock inicial no puede ser negativo.");
        }
        this.nombre = nombre;
        this.stock = stockInicial;
    }

    public String getNombre() { return nombre; }
    public int getStock() { return stock; }

    // Sin setStock(): el stock solo baja con esta acción -> encapsulamiento
    public void descontarStock() {
        if (stock <= 0) {
            throw new IllegalArgumentException("No hay stock disponible de " + nombre + ".");
        }
        stock--;
    }

    public void mostrarMedicamento() {
        System.out.println(nombre + " (stock disponible: " + stock + ")");
    }

    // Usado por el JComboBox de la GUI para mostrar cada opción
    @Override
    public String toString() {
        return nombre + " (stock: " + stock + ")";
    }
}