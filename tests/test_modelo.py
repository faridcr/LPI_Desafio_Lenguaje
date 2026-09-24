import pytest

from modelo.paciente import Paciente
from modelo.cita_medica import CitaMedica, EstadoCita
from modelo.atencion_medica import AtencionMedica
from modelo.reporte import Reporte


def test_dni_invalido_lanza_error():
    with pytest.raises(ValueError):
        Paciente("123", "Ana Torres", 30, "HC-001")


def test_dni_se_enmascara():
    paciente = Paciente("12345678", "Ana Torres", 30, "HC-001")
    assert paciente.dni_enmascarado == "****5678"
    assert "12345678" not in paciente.dni_enmascarado


def test_cancelar_cita_cambia_estado():
    cita = CitaMedica("C001", "30/09/2026", EstadoCita.PROGRAMADA, "Consulta")
    cita.cancelar_cita()
    assert cita.estado == EstadoCita.CANCELADA


def test_filtrar_por_diagnostico():
    atenciones = [
        AtencionMedica("A1", "Gripe", "Reposo", "-"),
        AtencionMedica("A2", "Gastritis", "Dieta", "-"),
    ]
    resultado = Reporte.filtrar_por_diagnostico(atenciones, "gripe")
    assert [a.id_atencion for a in resultado] == ["A1"]


from servicio.registro import RegistroAtenciones


def test_registro_enmascara_dni_en_resultado():
    registro = RegistroAtenciones()
    texto = registro.registrar("A001", "Ana Torres", "12345678", "Gripe", "Reposo")
    assert "****5678" in texto
    assert "12345678" not in texto
    assert len(registro.atenciones) == 1


def test_registro_rechaza_dni_invalido():
    registro = RegistroAtenciones()
    with pytest.raises(ValueError):
        registro.registrar("A001", "Ana Torres", "12ab", "Gripe", "Reposo")
    assert len(registro.atenciones) == 0
