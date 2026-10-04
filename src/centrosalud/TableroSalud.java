package centrosalud;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class TableroSalud extends JFrame {

    // Un solo centro compartido por todas las tarjetas: los datos no se pierden entre módulos
    private final CentroSalud centro = new CentroSalud();
    private VentanaPaciente ventana;

    public TableroSalud() {
        BaseDatos.cargarDatosDePrueba(centro);

        setTitle("Centro de Salud 10 de Octubre");
        setSize(750, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel principal = new JPanel(new BorderLayout(15, 15));
        principal.setBackground(EstiloSalud.FONDO);
        principal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titulo = new JLabel("CENTRO DE SALUD 10 DE OCTUBRE", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 23));
        titulo.setForeground(Color.WHITE);
        titulo.setOpaque(true);
        titulo.setBackground(EstiloSalud.AZUL_OSCURO);
        titulo.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));

        JPanel tarjetas = new JPanel(new GridLayout(2, 2, 15, 15));
        tarjetas.setBackground(EstiloSalud.FONDO);

        tarjetas.add(crearTarjeta("PACIENTES", "Registro y consulta",
                EstiloSalud.AZUL, VentanaPaciente.TAB_PACIENTES));
        tarjetas.add(crearTarjeta("MÉDICOS", "Personal médico",
                EstiloSalud.AZUL_OSCURO, VentanaPaciente.TAB_MEDICOS));
        tarjetas.add(crearTarjeta("CITAS MÉDICAS", "Atenciones programadas",
                EstiloSalud.VERDE, VentanaPaciente.TAB_CITAS));
        tarjetas.add(crearTarjeta("MEDICAMENTOS", "Control de medicamentos",
                EstiloSalud.NARANJA, VentanaPaciente.TAB_MEDICAMENTOS));

        principal.add(titulo, BorderLayout.NORTH);
        principal.add(tarjetas, BorderLayout.CENTER);
        add(principal);
    }

    private JPanel crearTarjeta(String titulo, String descripcion, Color color, int indicePestana) {
        JPanel tarjeta = new JPanel(new GridLayout(3, 1, 5, 5));
        tarjeta.setBackground(Color.WHITE);
        tarjeta.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(color, 2),
                BorderFactory.createEmptyBorder(12, 10, 12, 10)));

        JLabel nombre = new JLabel(titulo, SwingConstants.CENTER);
        nombre.setFont(new Font("Arial", Font.BOLD, 18));
        nombre.setForeground(color);

        JLabel detalle = new JLabel(descripcion, SwingConstants.CENTER);
        detalle.setFont(new Font("Arial", Font.PLAIN, 13));
        detalle.setForeground(new Color(75, 85, 99));

        JButton boton = new JButton("➜  ABRIR MÓDULO");
        boton.setFocusPainted(false);
        boton.setBackground(color);
        boton.setForeground(Color.WHITE);
        boton.setFont(new Font("Arial", Font.BOLD, 13));
        boton.setBorderPainted(false);
        boton.setOpaque(true);
        boton.setContentAreaFilled(true);
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        boton.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        boton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                boton.setBackground(color.darker());
            }

            @Override
            public void mouseExited(MouseEvent e) {
                boton.setBackground(color);
            }
        });

        boton.addActionListener(e -> abrirModulo(indicePestana));

        tarjeta.add(nombre);
        tarjeta.add(detalle);
        tarjeta.add(boton);

        return tarjeta;
    }

    // Reutiliza la misma ventana si ya está abierta; si la cerraron, crea una nueva
    private void abrirModulo(int indicePestana) {
        if (ventana == null || !ventana.isDisplayable()) {
            ventana = new VentanaPaciente(centro);
        }
        ventana.mostrarPestana(indicePestana);
        ventana.setVisible(true);
        ventana.toFront();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                // Se conserva el estilo predeterminado.
            }

            new TableroSalud().setVisible(true);
        });
    }
}