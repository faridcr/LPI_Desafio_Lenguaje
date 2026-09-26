package centrosalud;

import java.util.ArrayList;
import java.util.List;

// Composición: contiene los medicamentos reales del inventario
public class Receta {
    private List<Medicamento> medicamentos;
    private List<String> frecuencias; // misma posición que medicamentos

    public Receta() {
        medicamentos = new ArrayList<>();
        frecuencias = new ArrayList<>();
    }

    public void agregarMedicamento(Medicamento medicamento, String frecuencia) {
        medicamento.descontarStock(); // baja el stock real del inventario
        medicamentos.add(medicamento);

        if (frecuencia == null || frecuencia.isBlank()) {
            frecuencia = "Según indicación médica";
        }
        frecuencias.add(frecuencia.trim());
    }

    public void mostrarMedicamentos() {
        System.out.println("Medicamentos:");
        for (int i = 0; i < medicamentos.size(); i++) {
            System.out.println("- " + medicamentos.get(i).getNombre()
                    + " (" + frecuencias.get(i) + ")");
        }
    }
}