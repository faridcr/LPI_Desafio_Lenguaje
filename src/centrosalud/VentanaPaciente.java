package centrosalud;

import javax.swing.*;
import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class VentanaPaciente extends JFrame {

    // ===== Estado compartido: vive mientras la ventana esté abierta =====
    private CentroSalud centro = new CentroSalud();
    private List<AtencionMedica> atenciones = new ArrayList<>();

    public VentanaPaciente() {

        setTitle("Centro de Salud 10 de Octubre");
        setSize(700, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JTabbedPane pestañas = new JTabbedPane();
        pestañas.addTab("Pacientes", crearPanelPacientes());
        pestañas.addTab("Médicos", crearPanelMedicos());
        pestañas.addTab("Atenciones", crearPanelAtenciones());
        pestañas.addTab("Buscar / Listado", crearPanelBuscarYListar());

        add(pestañas);
    }

    // =========================================================
    // PESTAÑA 1: REGISTRAR PACIENTE (usa Paciente + CentroSalud)
    // =========================================================
    private JPanel crearPanelPacientes() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JPanel formulario = new JPanel(new GridLayout(4, 2, 8, 8));
        JTextField txtDni = new JTextField();
        JTextField txtNombre = new JTextField();
        JTextField txtFecha = new JTextField();
        JTextField txtHistoria = new JTextField();

        formulario.add(new JLabel("DNI (8 dígitos):"));
        formulario.add(txtDni);
        formulario.add(new JLabel("Nombre:"));
        formulario.add(txtNombre);
        formulario.add(new JLabel("Fecha nacimiento (dd/MM/yyyy):"));
        formulario.add(txtFecha);
        formulario.add(new JLabel("Historia clínica:"));
        formulario.add(txtHistoria);

        JButton btnRegistrar = new JButton("REGISTRAR PACIENTE");
        JTextArea txtResultado = new JTextArea(8, 40);
        txtResultado.setEditable(false);

        btnRegistrar.addActionListener(e -> {
            try {
                Paciente paciente = new Paciente(
                        txtDni.getText().trim(),
                        txtNombre.getText().trim(),
                        txtFecha.getText().trim(),
                        txtHistoria.getText().trim());

                centro.registrarPaciente(paciente);

                // Reutilizamos mostrarDatos() (polimórfico) para mostrar
                // la confirmación con los datos ya validados y calculados.
                String salida = capturarSalida(() -> paciente.mostrarDatos());
                txtResultado.setText("PACIENTE REGISTRADO:\n\n" + salida);

            } catch (IllegalArgumentException ex) {
                txtResultado.setText("Error: " + ex.getMessage());
            }
        });

        panel.add(formulario, BorderLayout.NORTH);
        panel.add(btnRegistrar, BorderLayout.CENTER);
        panel.add(new JScrollPane(txtResultado), BorderLayout.SOUTH);
        return panel;
    }

    // =========================================================
    // PESTAÑA 2: REGISTRAR MÉDICO (usa Medico + CentroSalud)
    // =========================================================
    private JPanel crearPanelMedicos() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JPanel formulario = new JPanel(new GridLayout(5, 2, 8, 8));
        JTextField txtDni = new JTextField();
        JTextField txtNombre = new JTextField();
        JTextField txtFecha = new JTextField();
        JTextField txtCmp = new JTextField();
        JTextField txtEspecialidad = new JTextField();

        formulario.add(new JLabel("DNI (8 dígitos):"));
        formulario.add(txtDni);
        formulario.add(new JLabel("Nombre:"));
        formulario.add(txtNombre);
        formulario.add(new JLabel("Fecha nacimiento (dd/MM/yyyy):"));
        formulario.add(txtFecha);
        formulario.add(new JLabel("CMP:"));
        formulario.add(txtCmp);
        formulario.add(new JLabel("Especialidad:"));
        formulario.add(txtEspecialidad);

        JButton btnRegistrar = new JButton("REGISTRAR MÉDICO");
        JTextArea txtResultado = new JTextArea(8, 40);
        txtResultado.setEditable(false);

        btnRegistrar.addActionListener(e -> {
            try {
                Medico medico = new Medico(
                        txtDni.getText().trim(),
                        txtNombre.getText().trim(),
                        txtFecha.getText().trim(),
                        txtCmp.getText().trim(),
                        txtEspecialidad.getText().trim());

                centro.registrarMedico(medico);

                String salida = capturarSalida(() -> medico.mostrarDatos());
                txtResultado.setText("MÉDICO REGISTRADO:\n\n" + salida);

            } catch (IllegalArgumentException ex) {
                txtResultado.setText("Error: " + ex.getMessage());
            }
        });

        panel.add(formulario, BorderLayout.NORTH);
        panel.add(btnRegistrar, BorderLayout.CENTER);
        panel.add(new JScrollPane(txtResultado), BorderLayout.SOUTH);
        return panel;
    }

    // =========================================================
    // PESTAÑA 3: REGISTRAR ATENCIÓN + GENERAR REPORTE
    // (usa AtencionMedica + Reporte, con streams incluido)
    // =========================================================
    private JPanel crearPanelAtenciones() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JPanel formulario = new JPanel(new GridLayout(5, 2, 8, 8));
        JTextField txtDniPaciente = new JTextField();
        JTextField txtId = new JTextField();
        JTextField txtDiagnostico = new JTextField();
        JTextField txtTratamiento = new JTextField();
        JTextField txtObservaciones = new JTextField();

        formulario.add(new JLabel("DNI del paciente:"));
        formulario.add(txtDniPaciente);
        formulario.add(new JLabel("ID Atención:"));
        formulario.add(txtId);
        formulario.add(new JLabel("Diagnóstico:"));
        formulario.add(txtDiagnostico);
        formulario.add(new JLabel("Tratamiento:"));
        formulario.add(txtTratamiento);
        formulario.add(new JLabel("Observaciones:"));
        formulario.add(txtObservaciones);

        JPanel panelBotones = new JPanel(new GridLayout(1, 2, 10, 10));
        JButton btnRegistrar = new JButton("REGISTRAR ATENCIÓN");
        JButton btnReporte = new JButton("GENERAR REPORTE");
        panelBotones.add(btnRegistrar);
        panelBotones.add(btnReporte);

        JTextArea txtResultado = new JTextArea(10, 40);
        txtResultado.setEditable(false);

        btnRegistrar.addActionListener(e -> {
            String dni = txtDniPaciente.getText().trim();
            Persona encontrada = centro.buscarPorDni(dni);

            if (!(encontrada instanceof Paciente)) {
                txtResultado.setText("No existe un paciente registrado con ese DNI.\n"
                        + "Regístralo primero en la pestaña \"Pacientes\".");
                return;
            }

            Paciente paciente = (Paciente) encontrada;

            AtencionMedica atencion = new AtencionMedica(
                    txtId.getText().trim(),
                    txtDiagnostico.getText().trim(),
                    txtTratamiento.getText().trim(),
                    txtObservaciones.getText().trim());

            // Conexión real: queda dentro de la historia clínica de ESE paciente.
            paciente.agregarAtencion(atencion);
            atenciones.add(atencion); // también se guarda aquí para el reporte global

            String salida = capturarSalida(() -> atencion.mostrarAtencion());
            txtResultado.setText("ATENCIÓN REGISTRADA para " + paciente.getNombre()
                    + " (total en su historia: "
                    + paciente.getHistoriaClinica().getAtenciones().size() + "):\n\n" + salida);

            txtId.setText("");
            txtDiagnostico.setText("");
            txtTratamiento.setText("");
            txtObservaciones.setText("");
        });

        btnReporte.addActionListener(e -> {
            String fechaHoy = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            Reporte reporte = new Reporte("Reporte de atenciones", fechaHoy);

            // generarReporte() usa stream().filter().map().forEach() por dentro,
            // igual que en el proyecto original.
            String salida = capturarSalida(() -> reporte.generarReporte(atenciones));
            txtResultado.setText(salida);
        });

        panel.add(formulario, BorderLayout.NORTH);
        panel.add(panelBotones, BorderLayout.CENTER);
        panel.add(new JScrollPane(txtResultado), BorderLayout.SOUTH);
        return panel;
    }

    // =========================================================
    // PESTAÑA 4: BUSCAR POR DNI + LISTAR TODOS (usa CentroSalud)
    // =========================================================
    private JPanel crearPanelBuscarYListar() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JPanel panelBusqueda = new JPanel(new BorderLayout(8, 8));
        JTextField txtDniBuscar = new JTextField();
        JPanel panelBotones = new JPanel(new GridLayout(1, 2, 10, 10));
        JButton btnBuscar = new JButton("BUSCAR POR DNI");
        JButton btnListar = new JButton("LISTAR TODOS");
        panelBotones.add(btnBuscar);
        panelBotones.add(btnListar);

        panelBusqueda.add(new JLabel("DNI a buscar:"), BorderLayout.WEST);
        panelBusqueda.add(txtDniBuscar, BorderLayout.CENTER);

        JTextArea txtResultado = new JTextArea(12, 40);
        txtResultado.setEditable(false);

        btnBuscar.addActionListener(e -> {
            String dni = txtDniBuscar.getText().trim();
            Persona encontrada = centro.buscarPorDni(dni);

            if (encontrada == null) {
                txtResultado.setText("No se encontró ninguna persona con ese DNI.");
            } else {
                // Polimorfismo: mostrarDatos() ejecuta la versión de Medico o
                // Paciente según el tipo REAL del objeto encontrado.
                String salida = capturarSalida(() -> encontrada.mostrarDatos());
                txtResultado.setText(salida);
            }
        });

        btnListar.addActionListener(e -> {
            String salida = capturarSalida(() -> centro.listarTodos());
            txtResultado.setText(salida);
        });

        panel.add(panelBusqueda, BorderLayout.NORTH);
        panel.add(panelBotones, BorderLayout.CENTER);
        panel.add(new JScrollPane(txtResultado), BorderLayout.SOUTH);
        return panel;
    }

    // =========================================================
    // Utilidad: captura lo que un método imprime con System.out.println
    // y lo devuelve como texto, para poder mostrarlo en un JTextArea.
    // Así reutilizamos mostrarDatos(), mostrarAtencion(), etc. sin
    // tener que reescribirlos para que devuelvan String.
    // =========================================================
    private String capturarSalida(Runnable accion) {
        PrintStream original = System.out;
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        System.setOut(new PrintStream(buffer));
        try {
            accion.run();
        } finally {
            System.setOut(original);
        }
        return buffer.toString();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            VentanaPaciente ventana = new VentanaPaciente();
            ventana.setVisible(true);
        });
    }
}