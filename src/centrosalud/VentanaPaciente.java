package centrosalud;

import javax.swing.*;
import java.awt.*;

public class VentanaPaciente extends JFrame {

    private JTextField txtId;
    private JTextField txtPaciente;
    private JTextField txtDni;
    private JTextField txtDiagnostico;
    private JTextField txtTratamiento;
    private JTextArea txtResultado;

    public VentanaPaciente() {

        // Título de la ventana
        setTitle("Centro de Salud 10 de Octubre");

        // Tamaño de la ventana
        setSize(650, 550);

        // Centrar la ventana
        setLocationRelativeTo(null);

        // Cerrar el programa al cerrar la ventana
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Panel principal
        JPanel panelPrincipal = new JPanel(new BorderLayout(15, 15));
        panelPrincipal.setBorder(
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        );

        // =========================
        // TÍTULO
        // =========================

        JLabel titulo = new JLabel(
        		"CENTRO DE SALUD 10 DE OCTUBRE",
                SwingConstants.CENTER
        );

        titulo.setFont(new Font("Arial", Font.BOLD, 22));

        panelPrincipal.add(titulo, BorderLayout.NORTH);

        // =========================
        // DATOS DE LA ATENCIÓN
        // =========================

        JPanel panelDatos = new JPanel(
                new GridLayout(5, 2, 10, 10)
        );

        panelDatos.add(new JLabel("ID Atención:"));
        txtId = new JTextField();
        panelDatos.add(txtId);

        panelDatos.add(new JLabel("Paciente:"));
        txtPaciente = new JTextField();
        panelDatos.add(txtPaciente);

        panelDatos.add(new JLabel("DNI:"));
        txtDni = new JTextField();
        panelDatos.add(txtDni);

        panelDatos.add(new JLabel("Diagnóstico:"));
        txtDiagnostico = new JTextField();
        panelDatos.add(txtDiagnostico);

        panelDatos.add(new JLabel("Tratamiento:"));
        txtTratamiento = new JTextField();
        panelDatos.add(txtTratamiento);

        panelPrincipal.add(panelDatos, BorderLayout.CENTER);

        // =========================
        // BOTONES
        // =========================

        JPanel panelBotones = new JPanel(
                new GridLayout(1, 2, 10, 10)
        );

        JButton btnRegistrar = new JButton("REGISTRAR ATENCIÓN");
        JButton btnLimpiar = new JButton("LIMPIAR");

        panelBotones.add(btnRegistrar);
        panelBotones.add(btnLimpiar);

        // =========================
        // RESULTADO
        // =========================

        txtResultado = new JTextArea(7, 40);
        txtResultado.setEditable(false);

        JScrollPane scroll = new JScrollPane(txtResultado);

        JPanel panelInferior = new JPanel(new BorderLayout(10, 10));

        panelInferior.add(panelBotones, BorderLayout.NORTH);
        panelInferior.add(scroll, BorderLayout.CENTER);

        panelPrincipal.add(panelInferior, BorderLayout.SOUTH);

        // =========================
        // EVENTO REGISTRAR
        // =========================

        btnRegistrar.addActionListener(e -> {

            String id = txtId.getText();
            String paciente = txtPaciente.getText();
            String dni = txtDni.getText();
            String diagnostico = txtDiagnostico.getText();
            String tratamiento = txtTratamiento.getText();

            txtResultado.setText(
                    "========== ATENCIÓN REGISTRADA ==========\n\n"
                    + "ID Atención: " + id + "\n"
                    + "Paciente: " + paciente + "\n"
                    + "DNI: " + dni + "\n"
                    + "Diagnóstico: " + diagnostico + "\n"
                    + "Tratamiento: " + tratamiento
            );
        });

        // =========================
        // EVENTO LIMPIAR
        // =========================

        btnLimpiar.addActionListener(e -> {

            txtId.setText("");
            txtPaciente.setText("");
            txtDni.setText("");
            txtDiagnostico.setText("");
            txtTratamiento.setText("");
            txtResultado.setText("");
        });

        // Mostrar ventana
        add(panelPrincipal);
    }

    // =========================
    // MÉTODO PRINCIPAL
    // =========================

    public static void main(String[] args) {

        VentanaPaciente ventana = new VentanaPaciente();

        ventana.setVisible(true);
    }
}