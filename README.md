# Centro de Salud 10 de Octubre

Sistema de gestión para un centro de salud, desarrollado en **Java** con **Programación Orientada a Objetos**. Incluye una versión por **consola** y una versión con **interfaz gráfica (Swing)**.

Proyecto del curso de Lenguaje de Programación (UPN) – Desafío Lenguaje.

## Integrantes

| Integrante |
|---|
| Kevin Hernan Astete Llancare |
| Andy Cristhofer Acosta Guillen |
| Josue David Mazuelos Valqui |
| Cesar Farid Cruz Cruces |

## Funcionalidades

- Registro de **pacientes** (cada uno con su historia clínica) y de **médicos**.
- Registro de **atenciones médicas** con diagnóstico, tratamiento y observaciones.
- **Receta médica** con medicamentos del inventario; el stock baja automáticamente.
- Gestión de **citas médicas** (pendiente, programada, atendida y cancelada).
- Control de **medicamentos** y su stock.
- Búsqueda de personas por DNI y listado general.
- **Reporte** de atenciones.
- Receta imprimible: se muestra en una ventana y se puede guardar como **PDF** (sin librerías externas).

## Requisitos

- **Java 17** o superior (JDK)
- **Eclipse IDE** (o cualquier IDE para Java)

No necesita librerías externas.

## Cómo ejecutar

1. Clona el repositorio:
   ```bash
   git clone <URL-del-repositorio>
   ```
2. En Eclipse: **File → Import → Git → Projects from Git**, o usa la vista **Git Repositories** → clic derecho en el repo → **Import Projects...**
3. Ejecuta una de las clases principales:

| Versión | Clase | Cómo |
|---|---|---|
| Interfaz gráfica | `TableroSalud` | Clic derecho → **Run As → Java Application** |
| Consola | `Principal` | Clic derecho → **Run As → Java Application** |

Al iniciar, el sistema carga datos de prueba (4 pacientes, 2 médicos y 8 medicamentos).

## Estructura del proyecto

```
src/
└── centrosalud/
    ├── Persona.java
    ├── Paciente.java
    ├── Medico.java
    ├── HistoriaClinica.java
    ├── AtencionMedica.java
    ├── Receta.java
    ├── Medicamento.java
    ├── CitaMedica.java
    ├── CentroSalud.java
    ├── BaseDatos.java
    ├── Reporte.java
    ├── Principal.java          (versión consola)
    ├── EstiloSalud.java
    ├── TableroSalud.java       (pantalla principal gráfica)
    └── VentanaPaciente.java    (módulos con pestañas)
```

## Clases

| Clase | Descripción |
|---|---|
| `Persona` | Clase abstracta base. Valida DNI, nombres y fecha de nacimiento. Muestra el DNI enmascarado. |
| `Paciente` | Hereda de `Persona`. Tiene su propia `HistoriaClinica`. |
| `Medico` | Hereda de `Persona`. Tiene CMP y especialidad, y puede atender citas. |
| `HistoriaClinica` | Guarda las atenciones del paciente. |
| `AtencionMedica` | Diagnóstico, tratamiento y observaciones. Nace con su `Receta`. |
| `Receta` | Lista de medicamentos con su frecuencia. Descuenta el stock. |
| `Medicamento` | Nombre y stock. El stock solo baja con `descontarStock()`. |
| `CitaMedica` | Une a un paciente y un médico, con fecha, motivo y estado. |
| `CentroSalud` | Clase controladora: pacientes, médicos, citas y medicamentos. |
| `BaseDatos` | Carga los datos de prueba. |
| `Reporte` | Genera el reporte de atenciones usando streams. |
| `Principal` | Menú de consola. |
| `EstiloSalud` | Colores y estilos de la interfaz. |
| `TableroSalud` | Pantalla principal con las 4 tarjetas de módulos. |
| `VentanaPaciente` | Ventana con 6 pestañas: Pacientes, Médicos, Atenciones, Citas, Medicamentos y Buscar/Listado. |

## Conceptos de POO aplicados

- **Herencia:** `Paciente` y `Medico` extienden `Persona`.
- **Polimorfismo:** `mostrarDatos()` se sobrescribe en `Paciente` y `Medico`; `buscarPorDni()` devuelve una `Persona`.
- **Encapsulamiento:** atributos privados, sin `setStock()` en `Medicamento`, y listas protegidas (copias o listas de solo lectura).
- **Composición:** `Paciente` → `HistoriaClinica` → `AtencionMedica` → `Receta`.
- **Asociación:** `CitaMedica` conecta un `Paciente` con un `Medico`.
- **Abstracción:** `Persona` es una clase abstracta.
- **Manejo de excepciones:** validaciones con `IllegalArgumentException` en los constructores y mensajes de error en la interfaz.
- **Streams:** `Reporte` usa `filter`, `map` y `forEach`.

## Validaciones

- El DNI debe tener 8 dígitos y no puede repetirse.
- La fecha de nacimiento usa el formato `dd/MM/yyyy`, no puede ser futura ni mayor a 120 años.
- No se pueden registrar medicamentos, citas ni atenciones con un ID repetido.
- La fecha de una cita no puede ser pasada.
- No se puede recetar un medicamento sin stock.

## Privacidad

Los datos personales se muestran de forma parcial (DNI enmascarado), en línea con la **Ley N.° 29733** de Protección de Datos Personales.

## Flujo de trabajo con Git

Cada integrante trabaja en su propia rama (por ejemplo `T1-Cruz-Cruces`) y luego se une a `master`:

```text
1. Team → Fetch from Upstream     (traer lo último)
2. Team → Merge... → origin/master (actualizar tu rama)
3. Hacer cambios → Team → Commit
4. Team → Push to origin
5. Merge de tu rama a master
```

## Limitaciones

- Los datos se guardan solo en memoria: al cerrar el programa se pierden.
- Las recetas en PDF contienen únicamente texto.

## Posibles mejoras

- Guardar los datos en archivos o en una base de datos.
- Registrar qué médico realizó cada atención.
- Agregar búsqueda y filtros por fecha en las citas.
