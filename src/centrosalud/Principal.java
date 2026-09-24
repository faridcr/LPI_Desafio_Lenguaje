package centrosalud;

import java.util.ArrayList;

public class Principal {
    public static void main(String[] args) {

        Paciente paciente = new Paciente(
                "12345678", "Juan Perez", 25, "HC-001");

        Medico medico = new Medico(
                "87654321", "Dr. Carlos Torres", 40,
                "CMP-45678", "Medicina General");

        CitaMedica cita = new CitaMedica(
                "C001", "20/09/2026", "PROGRAMADA",
                "Consulta general");

        AtencionMedica atencion = new AtencionMedica(
                "A001", "Gripe", "Reposo y medicación",
                "Control en 7 días");

        System.out.println("=== CENTRO DE SALUD 10 DE OCTUBRE ===");
        paciente.mostrarDatos();
        System.out.println();

        medico.mostrarDatos();
        System.out.println("Especialidad: " + medico.getEspecialidad());

        System.out.println();
        paciente.solicitarCita();
        medico.atenderCita(cita);

        System.out.println("\n=== ATENCIÓN MÉDICA ===");
        atencion.mostrarAtencion();

        Receta receta = new Receta();
        receta.agregarMedicamento("Paracetamol");
        receta.agregarMedicamento("Ibuprofeno");

        System.out.println();
        receta.mostrarMedicamentos();

        ArrayList<AtencionMedica> listaAtenciones = new ArrayList<>();
        listaAtenciones.add(atencion);

        Reporte reporte = new Reporte(
                "Reporte de atenciones", "20/09/2026");

        reporte.generarReporte(listaAtenciones);
    }
}
