from modelo.paciente import Paciente
from modelo.medico import Medico
from modelo.cita_medica import CitaMedica, EstadoCita
from modelo.atencion_medica import AtencionMedica
from modelo.receta import Receta
from modelo.reporte import Reporte


def main() -> None:
    # Todos los datos son ficticios (Ley N.° 29733)
    paciente = Paciente("12345678", "Juan Perez", 25, "HC-001")
    medico = Medico("87654321", "Dr. Carlos Torres", 40, "CMP-45678", "Medicina General")
    cita = CitaMedica("C001", "30/09/2026", EstadoCita.PROGRAMADA, "Consulta general")
    atencion = AtencionMedica("A001", "Gripe", "Reposo y medicación", "Control en 7 días")

    print("=== CENTRO DE SALUD SANTA ROSA ===")
    paciente.mostrar_datos()
    print()

    medico.mostrar_datos()
    print(f"Especialidad: {medico.especialidad}")

    print()
    paciente.solicitar_cita()
    medico.atender_cita(cita)

    print("\n=== ATENCIÓN MÉDICA ===")
    atencion.mostrar_atencion()

    receta = Receta()
    receta.agregar_medicamento("Paracetamol")
    receta.agregar_medicamento("Ibuprofeno")
    print()
    receta.mostrar_medicamentos()

    atenciones = [
        atencion,
        AtencionMedica("A002", "Gripe estacional", "Hidratación", "Volver si hay fiebre"),
        AtencionMedica("A003", "Gastritis", "Dieta blanda", "Control en 15 días"),
    ]

    reporte = Reporte("Reporte de atenciones", "30/09/2026")
    reporte.generar_reporte(atenciones)

    # Programación funcional: filter y map
    gripes = Reporte.filtrar_por_diagnostico(atenciones, "gripe")
    print("\nAtenciones por gripe:", [a.id_atencion for a in gripes])
    print("Diagnósticos:", Reporte.listar_diagnosticos(atenciones))

    # Manejo de errores: datos inválidos
    try:
        Paciente("123", "Error Prueba", 20, "HC-999")
    except ValueError as e:
        print("Error controlado:", e)


if __name__ == "__main__":
    main()
