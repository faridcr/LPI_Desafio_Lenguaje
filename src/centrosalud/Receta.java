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
        if (medicamento == null) {
            throw new IllegalArgumentException("Debes seleccionar un medicamento.");
        }

        medicamento.descontarStock(); // baja el stock real del inventario (puede lanzar error si no hay)
        medicamentos.add(medicamento);

        if (frecuencia == null || frecuencia.isBlank()) {
            frecuencia = "Según indicación médica";
        }
        frecuencias.add(frecuencia.trim());
    }

    public int cantidadMedicamentos() {
        return medicamentos.size();
    }

    public void mostrarMedicamentos() {
        System.out.println("Medicamentos:");
        if (medicamentos.isEmpty()) {
            System.out.println("- (sin medicamentos recetados)");
        }
        for (int i = 0; i < medicamentos.size(); i++) {
            System.out.println("- " + medicamentos.get(i).getNombre()
                    + " (" + frecuencias.get(i) + ")");
        }
    }
}