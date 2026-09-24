package centrosalud;

import java.util.ArrayList;

public class Receta {
    private ArrayList<String> medicamentos;

    public Receta() {
        medicamentos = new ArrayList<>();
    }

    public void agregarMedicamento(String medicamento) {
        medicamentos.add(medicamento);
    }

    public void mostrarMedicamentos() {
        System.out.println("Medicamentos:");
        for (String medicamento : medicamentos) {
            System.out.println("- " + medicamento);
        }
    }
}
