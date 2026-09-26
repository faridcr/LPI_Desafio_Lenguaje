package centrosalud;

import javax.swing.*;
import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

// Punto de entrada - versión gráfica (Swing). Usa la misma lógica que Principal.
// El botón "IMPRIMIR RECETA" genera un PDF simple con métodos propios
// (sin librerías externas) y lo abre directo, en vez del diálogo nativo de impresión.
public class VentanaPaciente extends JFrame {

    private CentroSalud centro = new CentroSalud();
    private List<AtencionMedica> atenciones = new ArrayList<>();

    public VentanaPaciente() {

        BaseDatos.cargarDatosDePrueba(centro);

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

    // Pestaña 1: registrar paciente
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

            } catch (IllegalArgumentException ex) {
                txtResultado.setText("Error: " + ex.getMessage());
            }
        });

        panel.add(formulario, BorderLayout.NORTH);
        panel.add(btnRegistrar, BorderLayout.CENTER);
        panel.add(new JScrollPane(txtResultado), BorderLayout.SOUTH);
        return panel;
    }

    // Pestaña 2: registrar médico
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

            } catch (IllegalArgumentException ex) {
                txtResultado.setText("Error: " + ex.getMessage());
            }
        });

        panel.add(formulario, BorderLayout.NORTH);
        panel.add(btnRegistrar, BorderLayout.CENTER);
        panel.add(new JScrollPane(txtResultado), BorderLayout.SOUTH);
        return panel;
    }

    // Pestaña 3: registrar atención + recetar + generar reporte
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
        // Combo lleno con getMedicamentos() (copia de la lista real)
        JComboBox<Medicamento> comboMedicamentos = new JComboBox<>();
        for (Medicamento m : centro.getMedicamentos()) {
            comboMedicamentos.addItem(m);
        }
        JButton btnAgregarMedicamento = new JButton("AGREGAR MEDICAMENTO A ESTA ATENCIÓN");
        filaMedicamento.add(new JLabel("Medicamento:"), BorderLayout.WEST);
        filaMedicamento.add(comboMedicamentos, BorderLayout.CENTER);
        filaMedicamento.add(btnAgregarMedicamento, BorderLayout.EAST);

        JPanel filaFrecuencia = new JPanel(new BorderLayout(8, 8));
        JTextField txtFrecuencia = new JTextField();
        JButton btnImprimir = new JButton("IMPRIMIR RECETA");
        filaFrecuencia.add(new JLabel("Frecuencia (ej: cada 8 horas por 5 días):"), BorderLayout.WEST);
        filaFrecuencia.add(txtFrecuencia, BorderLayout.CENTER);
        filaFrecuencia.add(btnImprimir, BorderLayout.EAST);

        panelReceta.add(filaMedicamento);
        panelReceta.add(filaFrecuencia);

        JTextArea txtResultado = new JTextArea(10, 40);
        txtResultado.setEditable(false);

        // Guarda la atención activa para el botón de recetar
        AtencionMedica[] atencionActual = new AtencionMedica[1];

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

            paciente.agregarAtencion(atencion);
            atenciones.add(atencion);
            atencionActual[0] = atencion;

            txtResultado.setText("Atención registrada para " + paciente.getNombreCompleto()
                    + " (total en su historia: "
                    + paciente.getHistoriaClinica().getAtenciones().size()
                    + ").\nAhora puedes agregarle medicamentos abajo.");
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
                comboMedicamentos.repaint();
                txtFrecuencia.setText("");

                String salida = capturarSalida(() -> atencionActual[0].mostrarAtencion());
                txtResultado.setText(salida);

            } catch (IllegalArgumentException ex) {
                txtResultado.setText("Error: " + ex.getMessage());
            }
        });

        btnReporte.addActionListener(e -> {
            String fechaHoy = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            Reporte reporte = new Reporte("Reporte de atenciones", fechaHoy);

            String salida = capturarSalida(() -> reporte.generarReporte(atenciones));
            txtResultado.setText(salida);
        });

        // En vez de depender de un lector de PDF instalado en el equipo (que puede
        // no existir, como pasó), mostramos la receta en una ventana PROPIA de la
        // aplicación (JDialog). Esa ventana siempre se abre, sin depender del
        // sistema operativo. Desde ahí, opcionalmente, se puede guardar como PDF.
        btnImprimir.addActionListener(e -> {
            if (atencionActual[0] == null) {
                txtResultado.setText("No hay ninguna atención activa para imprimir.");
                return;
            }

            String contenidoReceta = "RECETA MEDICA\n\n"
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

    // Pestaña 4: buscar por DNI + listar todos
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

        panel.add(panelBusqueda, BorderLayout.NORTH);
        panel.add(panelBotones, BorderLayout.CENTER);
        panel.add(new JScrollPane(txtResultado), BorderLayout.SOUTH);
        return panel;
    }

    // Ventana propia de la app para mostrar la receta: SIEMPRE se abre,
    // porque la crea y la controla el propio programa (no depende de
    // que el equipo tenga instalado un lector de PDF).
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

        // Guardar en PDF queda como acción OPCIONAL, elegida por el usuario;
        // ya no depende de que el sistema sepa abrir el archivo solo.
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
        ventanaReceta.setVisible(true); // ventana modal propia: siempre se abre
    }

    // Captura lo que se imprime por consola y lo muestra en el JTextArea
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

    // =========================================================
    // Generación de un PDF simple (solo texto, fuente Helvetica),
    // sin librerías externas. Se usa en el botón "IMPRIMIR RECETA"
    // para evitar el diálogo nativo de impresión de Java.
    // =========================================================
    private static final int PDF_ANCHO_PAGINA = 595;  // A4 en puntos
    private static final int PDF_ALTO_PAGINA = 842;
    private static final int PDF_MARGEN = 50;
    private static final int PDF_TAMANO_FUENTE = 11;
    private static final int PDF_INTERLINEA = 16;
    private static final int PDF_LINEAS_POR_PAGINA =
            (PDF_ALTO_PAGINA - PDF_MARGEN * 2) / PDF_INTERLINEA;

    private void generarPdfSimple(String texto, File destino) throws java.io.IOException {
        List<String> lineas = new ArrayList<>();
        for (String linea : texto.split("\n", -1)) {
            lineas.add(linea);
        }

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

        int numeroFont = 3 + paginas.size() * 2; // la fuente va al final, después de las páginas

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
            byte[] contenidoBytes = contenido.getBytes(java.nio.charset.StandardCharsets.ISO_8859_1);

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

    private void pdfEscribir(ByteArrayOutputStream out, String texto) throws java.io.IOException {
        out.write(texto.getBytes(java.nio.charset.StandardCharsets.ISO_8859_1));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            VentanaPaciente ventana = new VentanaPaciente();
            ventana.setVisible(true);
        });
    }
}