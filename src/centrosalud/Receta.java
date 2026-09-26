package centrosalud;

import java.util.ArrayList;
import java.util.List;

public class Receta {
    private List<Medicamento> medicamentos;
    private List<String> frecuencias; // misma posición (índice) que medicamentos

    public Receta() {
        medicamentos = new ArrayList<>();
        frecuencias = new ArrayList<>();
    }

    // Recibe el objeto Medicamento real (ya encontrado antes en CentroSalud)
    // y la frecuencia escrita a mano por el usuario. Cada vez que se agrega,
    // descuenta una unidad de su stock.
    public void agregarMedicamento(Medicamento medicamento, String frecuencia) {
        medicamento.descontarStock();
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