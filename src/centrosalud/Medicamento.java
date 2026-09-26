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

    // Sin setStock(): la única forma de bajar el stock es esta acción,
    // igual que programarCita()/cancelarCita() en CitaMedica.
    public void descontarStock() {
        if (stock <= 0) {
            throw new IllegalArgumentException("No hay stock disponible de " + nombre + ".");
        }
        stock--;
    }

    public void mostrarMedicamento() {
        System.out.println(nombre + " (stock disponible: " + stock + ")");
    }

    // Java llama a este método automáticamente cada vez que necesita
    // "convertir" el objeto a texto — por ejemplo, un JComboBox lo usa
    // para decidir qué mostrar por cada opción de la lista desplegable.
    @Override
    public String toString() {
        return nombre + " (stock: " + stock + ")";
    }
}