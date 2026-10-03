from paciente import Paciente
from medico import Medico
from medicamento import Medicamento

def cargar_datos(centro):
    paciente1 = Paciente("70531811", "Kevin Hernan", "Astete Llancare", "31/01/2002", "HC-001")
    paciente2 = Paciente("75769516", "Andy Cristhofer", "Acosta Guillen", "01/09/2004", "HC-002")
    paciente3 = Paciente("74277338", "Josue David", "Mazuelos Valqui", "01/03/2001", "HC-003")
    paciente4 = Paciente("74973481", "Cesar Farid", "Cruz Cruces", "29/04/2006", "HC-004")

    centro.registrar_paciente(paciente1)
    centro.registrar_paciente(paciente2)
    centro.registrar_paciente(paciente3)
    centro.registrar_paciente(paciente4)

    medico1 = Medico("12345678", "Carlos", "Torres Diaz", "10/01/1985", "CMP-12345", "Medicina General")
    medico2 = Medico("12345679", "Ana", "Ramirez Soto", "05/09/1980", "CMP-12346", "Pediatria")

    centro.registrar_medico(medico1)
    centro.registrar_medico(medico2)

    centro.registrar_medicamento(Medicamento("Paracetamol", 50))
    centro.registrar_medicamento(Medicamento("Ibuprofeno", 30))
    centro.registrar_medicamento(Medicamento("Amoxicilina", 20))
    centro.registrar_medicamento(Medicamento("Loratadina", 40))
    centro.registrar_medicamento(Medicamento("Omeprazol", 25))
    centro.registrar_medicamento(Medicamento("Metformina", 35))
    centro.registrar_medicamento(Medicamento("Aspirina", 60))
    centro.registrar_medicamento(Medicamento("Azitromicina", 15))

    print("Datos de prueba cargados")
