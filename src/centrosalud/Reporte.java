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

        for (AtencionMedica atencion : atenciones) {
            System.out.println("\nAtención: " + atencion.getIdAtencion());
            System.out.println("Diagnóstico: " + atencion.getDiagnostico());
        }
    }
}
