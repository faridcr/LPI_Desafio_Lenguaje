package centrosalud;

import java.util.ArrayList;
import java.util.List;

// Antes "historia clínica" era solo un String dentro de Paciente.
// Ahora es su propia clase: tiene un número identificador y guarda
// las atenciones médicas reales que ha recibido el paciente.
public class HistoriaClinica {
    private String numeroHistoria;
    private List<AtencionMedica> atenciones;

    public HistoriaClinica(String numeroHistoria) {
        if (numeroHistoria == null || numeroHistoria.isBlank()) {
            throw new IllegalArgumentException(
                    "El número de historia clínica no puede estar vacío.");
        }
        this.numeroHistoria = numeroHistoria;
        this.atenciones = new ArrayList<>();
    }

    public String getNumeroHistoria() {
        return numeroHistoria;
    }

    // Conecta HistoriaClinica con AtencionMedica: cada atención que recibe
    // el paciente queda registrada aquí, no suelta en otro lado.
    public void agregarAtencion(AtencionMedica atencion) {
        if (atencion == null) {
            throw new IllegalArgumentException(
                    "No se puede agregar una atención vacía a la historia clínica.");
        }
        atenciones.add(atencion);
    }

    public List<AtencionMedica> getAtenciones() {
        return atenciones;
    }

    public void mostrarHistoria() {
        System.out.println("Historia Clínica N°: " + numeroHistoria);
        System.out.println("Atenciones registradas: " + atenciones.size());
        for (AtencionMedica a : atenciones) {
            a.mostrarAtencion();
            System.out.println();
        }
    }
}