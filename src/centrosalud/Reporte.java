package centrosalud;

import java.util.List;

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

        // Paradigma funcional: filter + map + forEach (funciones de orden superior)
        atenciones.stream()
                .filter(a -> !a.getDiagnostico().isBlank())
                .map(a -> "Atención " + a.getIdAtencion() + " - " + a.getDiagnostico())
                .forEach(System.out::println);
    }
}