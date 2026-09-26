package centrosalud;

import java.util.ArrayList;
import java.util.List;

public class Receta {
    private List<Medicamento> medicamentos;

    public Receta() {
        medicamentos = new ArrayList<>();
    }

    // Recibe el objeto Medicamento real (ya encontrado antes en CentroSalud),
    // y cada vez que se agrega, descuenta una unidad de su stock.
    public void agregarMedicamento(Medicamento medicamento) {
        medicamento.descontarStock();
        medicamentos.add(medicamento);
    }

    public void mostrarMedicamentos() {
        System.out.println("Medicamentos:");
        for (Medicamento m : medicamentos) {
            System.out.println("- " + m.getNombre());
        }
    }
}