package centrosalud;

import java.util.List;

// Genera un reporte de texto a partir de una lista de atenciones
public class Reporte {
    private String tipoReporte;
    private String fecha;

    public Reporte(String tipoReporte, String fecha) {
        this.tipoReporte = tipoReporte;
        this.fecha = fecha;
    }

    public void generarReporte(List<AtencionMedica> atenciones) {
        System.out.println("\n==============================");
        System.out.println("       REPORTE DE SALUD");
        System.out.println("==============================");
        System.out.println("Tipo: " + tipoReporte);
        System.out.println("Fecha: " + fecha);
        System.out.println("Cantidad de atenciones: " + atenciones.size());

        // Streams: filter (descarta sin diagnóstico) + map (arma el texto) + forEach (imprime)
        atenciones.stream()
                .filter(a -> !a.getDiagnostico().isBlank())
                .map(a -> "Atención " + a.getIdAtencion() + " - " + a.getDiagnostico())
                .forEach(System.out::println);
    }
}