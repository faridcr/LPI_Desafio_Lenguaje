package centrosalud;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

// Versión gráfica (Swing). Recibe el CentroSalud ya creado, así todas las
// ventanas comparten los mismos datos. Para recetas, el botón "IMPRIMIR RECETA"
// abre una ventana propia de la aplicación y, opcionalmente, guarda un PDF
// generado con métodos propios (sin librerías externas).
public class VentanaPaciente extends JFrame {

    // Posición de cada pestaña (las usa TableroSalud para abrir la correcta)
    public static final int TAB_PACIENTES = 0;
    public static final int TAB_MEDICOS = 1;
    public static final int TAB_ATENCIONES = 2;
    public static final int TAB_CITAS = 3;
    public static final int TAB_MEDICAMENTOS = 4;
    public static final int TAB_BUSCAR = 5;

    private final CentroSalud centro;
    private JTabbedPane pestanas;

    // Componentes que se refrescan cuando cambian los datos
    private JComboBox<Medicamento> comboMedicamentos;
    private DefaultTableModel modeloMedicamentos;
    private DefaultTableModel modeloCitas;

    public VentanaPaciente(CentroSalud centro) {
        if (centro == null) {
            throw new IllegalArgumentException("El centro de salud no puede ser nulo.");
        }
        this.centro = centro;

        setTitle("Centro de Salud 10 de Octubre");
        setSize(800, 620);
        setLocationRelativeTo(null);
        // DISPOSE: cerrar esta ventana no cierra todo el programa
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        comboMedicamentos = new JComboBox<>();

        pestanas = new JTabbedPane();
        pestanas.addTab("Pacientes", crearPanelPacientes());
        pestanas.addTab("Médicos", crearPanelMedicos());
        pestanas.addTab("Atenciones", crearPanelAtenciones());
        pestanas.addTab("Citas", crearPanelCitas());
        pestanas.addTab("Medicamentos", crearPanelMedicamentos());
        pestanas.addTab("Buscar / Listado", crearPanelBuscarYListar());

        // El listener va después de crear las pestañas para evitar que se dispare antes de tiempo
        pestanas.addChangeListener(e -> actualizarDatos());

        add(pestanas);
        actualizarDatos();

        // Se aplica al final, cuando ya existen todos los componentes
        EstiloSalud.aplicar(this);
    }

    public void mostrarPestana(int indice) {
        if (indice >= 0 && indice < pestanas.getTabCount()) {
            pestanas.setSelectedIndex(indice);
        }
    }

    // Refresca combo de medicamentos, tabla de stock y tabla de citas
    private void actualizarDatos() {
        if (modeloMedicamentos == null || modeloCitas == null) {
            return;
        }

        Medicamento seleccionado = (Medicamento) comboMedicamentos.getSelectedItem();
        comboMedicamentos.removeAllItems();
        for (Medicamento m : centro.getMedicamentos()) {
            comboMedicamentos.addItem(m);
        }
        if (seleccionado != null) {
            comboMedicamentos.setSelectedItem(seleccionado);
        }

        modeloMedicamentos.setRowCount(0);
        for (Medicamento m : centro.getMedicamentos()) {
            modeloMedicamentos.addRow(new Object[]{m.getNombre(), m.getStock()});
        }

        modeloCitas.setRowCount(0);
        for (CitaMedica c : centro.getCitas()) {
            modeloCitas.addRow(new Object[]{
                    c.getIdCita(),
                    c.getFecha(),
                    c.getEstado(),
                    c.getPaciente().getNombreCompleto(),
                    c.getMedico().getNombreCompleto()});
        }
    }

    // Modelo de tabla que no permite editar las celdas
    private DefaultTableModel crearModeloTabla(String[] columnas) {
        return new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };
    }

    // ======================= Pestaña 1: pacientes =======================
    private JPanel crearPanelPacientes() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JPanel formulario = new JPanel(new GridLayout(5, 2, 8, 8));
        JTextField txtDni = new JTextField();
        JTextField txtNombres = new JTextField();
        JTextField txtApellidos = new JTextField();
        JTextField txtFecha = new JTextField();
        JTextField txtHistoria = new JTextField();

        formulario.add(new JLabel("DNI (8 dígitos):"));
        formulario.add(txtDni);
        formulario.add(new JLabel("Nombres:"));
        formulario.add(txtNombres);
        formulario.add(new JLabel("Apellidos:"));
        formulario.add(txtApellidos);
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
                        txtNombres.getText().trim(),
                        txtApellidos.getText().trim(),
                        txtFecha.getText().trim(),
                        txtHistoria.getText().trim());

                centro.registrarPaciente(paciente);

                String salida = capturarSalida(() -> paciente.mostrarDatos());
                txtResultado.setText("PACIENTE REGISTRADO:\n\n" + salida);

                txtDni.setText("");
                txtNombres.setText("");
                txtApellidos.setText("");
                txtFecha.setText("");
                txtHistoria.setText("");

            } catch (IllegalArgumentException ex) {
                txtResultado.setText("Error: " + ex.getMessage());
            }
        });

        JPanel panelSuperior = new JPanel(new BorderLayout(10, 10));
        panelSuperior.add(formulario, BorderLayout.NORTH);
        panelSuperior.add(btnRegistrar, BorderLayout.SOUTH);

        panel.add(panelSuperior, BorderLayout.NORTH);
        panel.add(new JScrollPane(txtResultado), BorderLayout.CENTER);
        return panel;
    }

    // ======================= Pestaña 2: médicos =======================
    private JPanel crearPanelMedicos() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JPanel formulario = new JPanel(new GridLayout(6, 2, 8, 8));
        JTextField txtDni = new JTextField();
        JTextField txtNombres = new JTextField();
        JTextField txtApellidos = new JTextField();
        JTextField txtFecha = new JTextField();
        JTextField txtCmp = new JTextField();
        JTextField txtEspecialidad = new JTextField();

        formulario.add(new JLabel("DNI (8 dígitos):"));
        formulario.add(txtDni);
        formulario.add(new JLabel("Nombres:"));
        formulario.add(txtNombres);
        formulario.add(new JLabel("Apellidos:"));
        formulario.add(txtApellidos);
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
                        txtNombres.getText().trim(),
                        txtApellidos.getText().trim(),
                        txtFecha.getText().trim(),
                        txtCmp.getText().trim(),
                        txtEspecialidad.getText().trim());

                centro.registrarMedico(medico);

                String salida = capturarSalida(() -> medico.mostrarDatos());
                txtResultado.setText("MÉDICO REGISTRADO:\n\n" + salida);

                txtDni.setText("");
                txtNombres.setText("");
                txtApellidos.setText("");
                txtFecha.setText("");
                txtCmp.setText("");
                txtEspecialidad.setText("");

            } catch (IllegalArgumentException ex) {
                txtResultado.setText("Error: " + ex.getMessage());
            }
        });

        JPanel panelSuperior = new JPanel(new BorderLayout(10, 10));
        panelSuperior.add(formulario, BorderLayout.NORTH);
        panelSuperior.add(btnRegistrar, BorderLayout.SOUTH);

        panel.add(panelSuperior, BorderLayout.NORTH);
        panel.add(new JScrollPane(txtResultado), BorderLayout.CENTER);
        return panel;
    }

    // ============ Pestaña 3: atención + receta + reporte ============
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

        JButton btnRegistrar = new JButton("REGISTRAR ATENCIÓN");
        JButton btnReporte = new JButton("GENERAR REPORTE");
        JPanel panelBotones = new JPanel(new GridLayout(1, 2, 10, 10));
        panelBotones.add(btnRegistrar);
        panelBotones.add(btnReporte);

        JPanel panelReceta = new JPanel(new GridLayout(2, 1, 8, 8));

        JPanel filaMedicamento = new JPanel(new BorderLayout(8, 8));
        JButton btnAgregarMedicamento = new JButton("AGREGAR A LA RECETA");
        filaMedicamento.add(new JLabel("Medicamento:"), BorderLayout.WEST);
        filaMedicamento.add(comboMedicamentos, BorderLayout.CENTER);
        filaMedicamento.add(btnAgregarMedicamento, BorderLayout.EAST);

        JPanel filaFrecuencia = new JPanel(new BorderLayout(8, 8));
        JTextField txtFrecuencia = new JTextField();
        JButton btnImprimir = new JButton("IMPRIMIR RECETA");
        filaFrecuencia.add(new JLabel("Frecuencia (ej: cada 8 horas):"), BorderLayout.WEST);
        filaFrecuencia.add(txtFrecuencia, BorderLayout.CENTER);
        filaFrecuencia.add(btnImprimir, BorderLayout.EAST);

        panelReceta.add(filaMedicamento);
        panelReceta.add(filaFrecuencia);

        JTextArea txtResultado = new JTextArea(10, 40);
        txtResultado.setEditable(false);

        // Atención y paciente activos (para recetar e imprimir)
        AtencionMedica[] atencionActual = new AtencionMedica[1];
        Paciente[] pacienteActual = new Paciente[1];

        btnRegistrar.addActionListener(e -> {
            try {
                String dni = txtDniPaciente.getText().trim();
                Persona encontrada = centro.buscarPorDni(dni);

                if (!(encontrada instanceof Paciente)) {
                    txtResultado.setText("No existe un paciente registrado con ese DNI.\n"
                            + "Regístralo primero en la pestaña \"Pacientes\".");
                    return;
                }

                Paciente paciente = (Paciente) encontrada;
                String id = txtId.getText().trim();

                if (centro.existeAtencion(id)) {
                    throw new IllegalArgumentException("Ya existe una atención con el ID " + id + ".");
                }

                AtencionMedica atencion = new AtencionMedica(
                        id,
                        txtDiagnostico.getText().trim(),
                        txtTratamiento.getText().trim(),
                        txtObservaciones.getText().trim());

                paciente.agregarAtencion(atencion);
                atencionActual[0] = atencion;
                pacienteActual[0] = paciente;

                txtResultado.setText("Atención registrada para " + paciente.getNombreCompleto()
                        + " (total en su historia: "
                        + paciente.getHistoriaClinica().getAtenciones().size()
                        + ").\nAhora puedes agregarle medicamentos abajo.");

            } catch (IllegalArgumentException ex) {
                txtResultado.setText("Error: " + ex.getMessage());
            }
        });

        btnAgregarMedicamento.addActionListener(e -> {
            if (atencionActual[0] == null) {
                txtResultado.setText("Primero registra una atención antes de recetar.");
                return;
            }

            Medicamento seleccionado = (Medicamento) comboMedicamentos.getSelectedItem();
            if (seleccionado == null) {
                txtResultado.setText("No hay medicamentos disponibles.");
                return;
            }

            try {
                atencionActual[0].agregarMedicamento(seleccionado, txtFrecuencia.getText());
                txtFrecuencia.setText("");
                actualizarDatos(); // refresca el stock mostrado

                String salida = capturarSalida(() -> atencionActual[0].mostrarAtencion());
                txtResultado.setText(salida);

            } catch (IllegalArgumentException ex) {
                txtResultado.setText("Error: " + ex.getMessage());
            }
        });

        btnReporte.addActionListener(e -> {
            String fechaHoy = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            Reporte reporte = new Reporte("Reporte de atenciones", fechaHoy);

            // Incluye las atenciones de TODOS los pacientes del centro
            String salida = capturarSalida(
                    () -> reporte.generarReporte(centro.getTodasLasAtenciones()));
            txtResultado.setText(salida);
        });

        btnImprimir.addActionListener(e -> {
            if (atencionActual[0] == null) {
                txtResultado.setText("No hay ninguna atención activa para imprimir.");
                return;
            }

            String contenidoReceta = "RECETA MEDICA\n\n"
                    + "Paciente: " + pacienteActual[0].getNombreCompleto() + "\n\n"
                    + capturarSalida(() -> atencionActual[0].mostrarAtencion());

            mostrarVentanaReceta(contenidoReceta, atencionActual[0].getIdAtencion());
        });

        JPanel panelSuperior = new JPanel(new BorderLayout(10, 10));
        panelSuperior.add(formulario, BorderLayout.NORTH);
        panelSuperior.add(panelBotones, BorderLayout.CENTER);
        panelSuperior.add(panelReceta, BorderLayout.SOUTH);

        panel.add(panelSuperior, BorderLayout.NORTH);
        panel.add(new JScrollPane(txtResultado), BorderLayout.CENTER);
        return panel;
    }

    // ======================= Pestaña 4: citas =======================
    private JPanel crearPanelCitas() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JPanel formulario = new JPanel(new GridLayout(5, 2, 8, 8));
        JTextField txtIdCita = new JTextField();
        JTextField txtFecha = new JTextField();
        JTextField txtMotivo = new JTextField();
        JTextField txtDniPaciente = new JTextField();
        JTextField txtDniMedico = new JTextField();

        formulario.add(new JLabel("ID Cita:"));
        formulario.add(txtIdCita);
        formulario.add(new JLabel("Fecha (dd/MM/yyyy):"));
        formulario.add(txtFecha);
        formulario.add(new JLabel("Motivo:"));
        formulario.add(txtMotivo);
        formulario.add(new JLabel("DNI del paciente:"));
        formulario.add(txtDniPaciente);
        formulario.add(new JLabel("DNI del médico:"));
        formulario.add(txtDniMedico);

        JButton btnRegistrar = new JButton("REGISTRAR CITA");
        JLabel lblMensaje = new JLabel(" ");

        modeloCitas = crearModeloTabla(
                new String[]{"ID", "Fecha", "Estado", "Paciente", "Médico"});
        JTable tabla = new JTable(modeloCitas);
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JButton btnProgramar = new JButton("PROGRAMAR");
        JButton btnAtendida = new JButton("MARCAR ATENDIDA");
        JButton btnCancelar = new JButton("CANCELAR CITA");
        JPanel panelEstados = new JPanel(new GridLayout(1, 3, 10, 10));
        panelEstados.add(btnProgramar);
        panelEstados.add(btnAtendida);
        panelEstados.add(btnCancelar);

        btnRegistrar.addActionListener(e -> {
            try {
                centro.registrarCita(
                        txtIdCita.getText().trim(),
                        txtFecha.getText().trim(),
                        txtMotivo.getText().trim(),
                        txtDniPaciente.getText().trim(),
                        txtDniMedico.getText().trim());

                lblMensaje.setText("Cita registrada con éxito (estado PENDIENTE).");
                txtIdCita.setText("");
                txtFecha.setText("");
                txtMotivo.setText("");
                txtDniPaciente.setText("");
                txtDniMedico.setText("");
                actualizarDatos();

            } catch (IllegalArgumentException ex) {
                lblMensaje.setText("Error: " + ex.getMessage());
            }
        });

        btnProgramar.addActionListener(e ->
                cambiarEstadoCita(tabla, lblMensaje, c -> c.programarCita()));
        btnAtendida.addActionListener(e ->
                cambiarEstadoCita(tabla, lblMensaje, c -> c.getMedico().atenderCita(c)));
        btnCancelar.addActionListener(e ->
                cambiarEstadoCita(tabla, lblMensaje, c -> c.cancelarCita()));

        JPanel panelRegistro = new JPanel(new BorderLayout(8, 8));
        panelRegistro.add(formulario, BorderLayout.NORTH);
        panelRegistro.add(btnRegistrar, BorderLayout.CENTER);
        panelRegistro.add(lblMensaje, BorderLayout.SOUTH);

        panel.add(panelRegistro, BorderLayout.NORTH);
        panel.add(new JScrollPane(tabla), BorderLayout.CENTER);
        panel.add(panelEstados, BorderLayout.SOUTH);
        return panel;
    }

    // Aplica una acción (programar, atender, cancelar) a la cita seleccionada en la tabla
    private void cambiarEstadoCita(JTable tabla, JLabel lblMensaje, Consumer<CitaMedica> accion) {
        int fila = tabla.getSelectedRow();
        if (fila < 0) {
            lblMensaje.setText("Selecciona una cita de la tabla.");
            return;
        }

        try {
            CitaMedica cita = centro.getCitas().get(fila);
            accion.accept(cita);
            lblMensaje.setText("La cita " + cita.getIdCita() + " ahora está " + cita.getEstado() + ".");
            actualizarDatos();
        } catch (IllegalArgumentException ex) {
            lblMensaje.setText("Error: " + ex.getMessage());
        }
    }

    // ===================== Pestaña 5: medicamentos =====================
    private JPanel crearPanelMedicamentos() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JPanel formulario = new JPanel(new GridLayout(2, 2, 8, 8));
        JTextField txtNombre = new JTextField();
        JTextField txtStock = new JTextField();
        formulario.add(new JLabel("Nombre del medicamento:"));
        formulario.add(txtNombre);
        formulario.add(new JLabel("Stock inicial:"));
        formulario.add(txtStock);

        JButton btnRegistrar = new JButton("REGISTRAR MEDICAMENTO");
        JLabel lblMensaje = new JLabel(" ");

        modeloMedicamentos = crearModeloTabla(new String[]{"Medicamento", "Stock disponible"});
        JTable tabla = new JTable(modeloMedicamentos);

        btnRegistrar.addActionListener(e -> {
            try {
                int stock = Integer.parseInt(txtStock.getText().trim());
                centro.registrarMedicamento(new Medicamento(txtNombre.getText().trim(), stock));

                lblMensaje.setText("Medicamento registrado con éxito.");
                txtNombre.setText("");
                txtStock.setText("");
                actualizarDatos();

            } catch (NumberFormatException ex) {
                lblMensaje.setText("Error: el stock debe ser un número entero.");
            } catch (IllegalArgumentException ex) {
                lblMensaje.setText("Error: " + ex.getMessage());
            }
        });

        JPanel panelSuperior = new JPanel(new BorderLayout(8, 8));
        panelSuperior.add(formulario, BorderLayout.NORTH);
        panelSuperior.add(btnRegistrar, BorderLayout.CENTER);
        panelSuperior.add(lblMensaje, BorderLayout.SOUTH);

        panel.add(panelSuperior, BorderLayout.NORTH);
        panel.add(new JScrollPane(tabla), BorderLayout.CENTER);
        return panel;
    }

    // ================= Pestaña 6: buscar por DNI + listar =================
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
                String salida = capturarSalida(() -> encontrada.mostrarDatos());
                txtResultado.setText(salida);
            }
        });

        btnListar.addActionListener(e -> {
            String salida = capturarSalida(() -> centro.listarTodos());
            txtResultado.setText(salida);
        });

        JPanel panelSuperior = new JPanel(new BorderLayout(10, 10));
        panelSuperior.add(panelBusqueda, BorderLayout.NORTH);
        panelSuperior.add(panelBotones, BorderLayout.SOUTH);

        panel.add(panelSuperior, BorderLayout.NORTH);
        panel.add(new JScrollPane(txtResultado), BorderLayout.CENTER);
        return panel;
    }

    // ======================== Ventana de la receta ========================
    // Ventana propia de la app: siempre se abre, no depende de un lector de PDF instalado.
    private void mostrarVentanaReceta(String contenido, String idAtencion) {
        JDialog ventanaReceta = new JDialog(this, "Receta - Atención " + idAtencion, true);
        ventanaReceta.setSize(450, 500);
        ventanaReceta.setLocationRelativeTo(this);

        JTextArea texto = new JTextArea(contenido);
        texto.setEditable(false);
        texto.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 13));
        texto.setMargin(new Insets(15, 15, 15, 15));

        JButton btnGuardarPdf = new JButton("GUARDAR COMO PDF");
        JButton btnCerrar = new JButton("CERRAR");

        btnGuardarPdf.addActionListener(ev -> {
            JFileChooser selector = new JFileChooser();
            selector.setSelectedFile(new File("receta_" + idAtencion + ".pdf"));
            if (selector.showSaveDialog(ventanaReceta) == JFileChooser.APPROVE_OPTION) {
                try {
                    generarPdfSimple(contenido, selector.getSelectedFile());
                    JOptionPane.showMessageDialog(ventanaReceta,
                            "Receta guardada en:\n" + selector.getSelectedFile().getAbsolutePath());
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(ventanaReceta,
                            "Error al guardar el PDF: " + ex.getMessage());
                }
            }
        });

        btnCerrar.addActionListener(ev -> ventanaReceta.dispose());

        JPanel panelBotones = new JPanel(new GridLayout(1, 2, 10, 10));
        panelBotones.add(btnGuardarPdf);
        panelBotones.add(btnCerrar);

        JPanel panelContenido = new JPanel(new BorderLayout(10, 10));
        panelContenido.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelContenido.add(new JScrollPane(texto), BorderLayout.CENTER);
        panelContenido.add(panelBotones, BorderLayout.SOUTH);

        ventanaReceta.setContentPane(panelContenido);
        ventanaReceta.setVisible(true);
    }

    // Captura lo que los métodos del modelo imprimen por consola para mostrarlo en la GUI
    private String capturarSalida(Runnable accion) {
        PrintStream original = System.out;
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        System.setOut(new PrintStream(buffer, true, StandardCharsets.UTF_8));
        try {
            accion.run();
        } finally {
            System.setOut(original);
        }
        return buffer.toString(StandardCharsets.UTF_8);
    }

    // =========================================================
    // Generación de un PDF simple (solo texto, fuente Helvetica),
    // sin librerías externas.
    // =========================================================
    private static final int PDF_ANCHO_PAGINA = 595;  // A4 en puntos
    private static final int PDF_ALTO_PAGINA = 842;
    private static final int PDF_MARGEN = 50;
    private static final int PDF_TAMANO_FUENTE = 11;
    private static final int PDF_INTERLINEA = 16;
    private static final int PDF_MAX_CARACTERES = 85; // ancho máximo de línea
    private static final int PDF_LINEAS_POR_PAGINA =
            (PDF_ALTO_PAGINA - PDF_MARGEN * 2) / PDF_INTERLINEA;

    // Corta las líneas largas para que no se salgan de la página
    private List<String> envolverLineas(String texto) {
        List<String> resultado = new ArrayList<>();
        for (String linea : texto.replace("\r", "").split("\n", -1)) {
            while (linea.length() > PDF_MAX_CARACTERES) {
                int corte = linea.lastIndexOf(' ', PDF_MAX_CARACTERES);
                if (corte <= 0) {
                    corte = PDF_MAX_CARACTERES;
                }
                resultado.add(linea.substring(0, corte));
                linea = linea.substring(corte).stripLeading();
            }
            resultado.add(linea);
        }
        return resultado;
    }

    private void generarPdfSimple(String texto, File destino) throws IOException {
        List<String> lineas = envolverLineas(texto);

        // Reparte las líneas en páginas si no entran en una sola
        List<List<String>> paginas = new ArrayList<>();
        for (int i = 0; i < lineas.size(); i += PDF_LINEAS_POR_PAGINA) {
            paginas.add(lineas.subList(i, Math.min(i + PDF_LINEAS_POR_PAGINA, lineas.size())));
        }
        if (paginas.isEmpty()) {
            paginas.add(new ArrayList<>());
        }

        ByteArrayOutputStream pdf = new ByteArrayOutputStream();
        List<Integer> offsets = new ArrayList<>();

        pdfEscribir(pdf, "%PDF-1.4\n");

        // Objeto 1: catálogo
        offsets.add(pdf.size());
        pdfEscribir(pdf, "1 0 obj\n<< /Type /Catalog /Pages 2 0 R >>\nendobj\n");

        // Objeto 2: árbol de páginas
        StringBuilder kids = new StringBuilder();
        for (int i = 0; i < paginas.size(); i++) {
            kids.append(3 + i * 2).append(" 0 R ");
        }
        offsets.add(pdf.size());
        pdfEscribir(pdf, "2 0 obj\n<< /Type /Pages /Kids [" + kids.toString().trim()
                + "] /Count " + paginas.size() + " >>\nendobj\n");

        int numeroFont = 3 + paginas.size() * 2; // la fuente va al final

        // Un objeto "página" + un objeto "contenido" por cada página
        for (int i = 0; i < paginas.size(); i++) {
            int numPagina = 3 + i * 2;
            int numContenido = numPagina + 1;

            offsets.add(pdf.size());
            pdfEscribir(pdf, numPagina + " 0 obj\n<< /Type /Page /Parent 2 0 R "
                    + "/MediaBox [0 0 " + PDF_ANCHO_PAGINA + " " + PDF_ALTO_PAGINA + "] "
                    + "/Resources << /Font << /F1 " + numeroFont + " 0 R >> >> "
                    + "/Contents " + numContenido + " 0 R >>\nendobj\n");

            String contenido = pdfConstruirContenido(paginas.get(i));
            byte[] contenidoBytes = contenido.getBytes(StandardCharsets.ISO_8859_1);

            offsets.add(pdf.size());
            pdfEscribir(pdf, numContenido + " 0 obj\n<< /Length " + contenidoBytes.length + " >>\nstream\n");
            pdf.write(contenidoBytes);
            pdfEscribir(pdf, "\nendstream\nendobj\n");
        }

        // Objeto de la fuente
        offsets.add(pdf.size());
        pdfEscribir(pdf, numeroFont + " 0 obj\n<< /Type /Font /Subtype /Type1 "
                + "/BaseFont /Helvetica /Encoding /WinAnsiEncoding >>\nendobj\n");

        int xrefInicio = pdf.size();
        int totalObjetos = offsets.size() + 1; // +1 por el objeto libre 0
        pdfEscribir(pdf, "xref\n0 " + totalObjetos + "\n0000000000 65535 f \n");
        for (int offset : offsets) {
            pdfEscribir(pdf, String.format("%010d 00000 n \n", offset));
        }
        pdfEscribir(pdf, "trailer\n<< /Size " + totalObjetos + " /Root 1 0 R >>\nstartxref\n"
                + xrefInicio + "\n%%EOF");

        try (java.io.FileOutputStream fos = new java.io.FileOutputStream(destino)) {
            pdf.writeTo(fos);
        }
    }

    // Arma el stream de contenido (BT...ET) con cada línea de texto.
    private String pdfConstruirContenido(List<String> lineas) {
        StringBuilder sb = new StringBuilder();
        sb.append("BT\n/F1 ").append(PDF_TAMANO_FUENTE).append(" Tf\n");
        sb.append(PDF_MARGEN).append(" ").append(PDF_ALTO_PAGINA - PDF_MARGEN).append(" Td\n");
        sb.append(PDF_INTERLINEA).append(" TL\n");
        for (String linea : lineas) {
            sb.append("(").append(pdfEscapar(linea)).append(") Tj\nT*\n");
        }
        sb.append("ET");
        return sb.toString();
    }

    // Escapa paréntesis y backslashes: obligatorio dentro de un PDF.
    private String pdfEscapar(String texto) {
        return texto.replace("\\", "\\\\")
                .replace("(", "\\(")
                .replace(")", "\\)");
    }

    private void pdfEscribir(ByteArrayOutputStream out, String texto) throws IOException {
        out.write(texto.getBytes(StandardCharsets.ISO_8859_1));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            CentroSalud centro = new CentroSalud();
            BaseDatos.cargarDatosDePrueba(centro);
            new VentanaPaciente(centro).setVisible(true);
        });
    }
}