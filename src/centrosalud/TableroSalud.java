
package centrosalud;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class TableroSalud extends JFrame {

    private final Color azulOscuro = new Color(11, 49, 91);
    private final Color turquesa = new Color(22, 132, 216);
    private final Color fondo = new Color(245, 248, 252);

    public TableroSalud() {
        setTitle("Centro de Salud 10 de Octubre");
        setSize(750, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel principal = new JPanel(new BorderLayout(15, 15));
        principal.setBackground(fondo);
        principal.setBorder(
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        );

        JLabel titulo = new JLabel(
            "CENTRO DE SALUD 10 DE OCTUBRE",
            SwingConstants.CENTER
        );
        titulo.setFont(new Font("Arial", Font.BOLD, 23));
        titulo.setForeground(Color.WHITE);
        titulo.setOpaque(true);
        titulo.setBackground(azulOscuro);
        titulo.setBorder(
            BorderFactory.createEmptyBorder(20, 10, 20, 10)
        );

        JPanel tarjetas = new JPanel(new GridLayout(2, 2, 15, 15));
        tarjetas.setBackground(fondo);

        tarjetas.add(crearTarjeta(
            "PACIENTES", "Registro y consulta", turquesa, 0
        ));
        tarjetas.add(crearTarjeta(
            "MÉDICOS", "Personal médico", azulOscuro, 1
        ));
        tarjetas.add(crearTarjeta(
            "CITAS MÉDICAS", "Atenciones programadas",
            new Color(38, 145, 105), 2
        ));
        tarjetas.add(crearTarjeta(
            "MEDICAMENTOS", "Control de medicamentos",
            new Color(220, 145, 45), 2
        ));

        principal.add(titulo, BorderLayout.NORTH);
        principal.add(tarjetas, BorderLayout.CENTER);
        add(principal);
    }

    private JPanel crearTarjeta(
        String titulo, String descripcion, Color color, int indice
    ) {
        JPanel tarjeta = new JPanel(new GridLayout(3, 1, 5, 5));
        tarjeta.setBackground(Color.WHITE);
        tarjeta.setBorder(
            BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(color, 2),
                BorderFactory.createEmptyBorder(12, 10, 12, 10)
            )
        );

        JLabel nombre = new JLabel(titulo, SwingConstants.CENTER);
        nombre.setFont(new Font("Arial", Font.BOLD, 18));
        nombre.setForeground(color);

        JLabel detalle = new JLabel(
            descripcion, SwingConstants.CENTER
        );
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
boton.setCursor(new java.awt.Cursor(
    java.awt.Cursor.HAND_CURSOR
));
        
        boton.setBorder(
            BorderFactory.createEmptyBorder(10, 15, 10, 15)
        );
        boton.setOpaque(true);
        boton.setContentAreaFilled(true);
        boton.setCursor(new java.awt.Cursor(
            java.awt.Cursor.HAND_CURSOR
        ));
        Color colorOriginal = color;

        boton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                boton.setBackground(colorOriginal.darker());
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                boton.setBackground(colorOriginal);
            }
        });

        boton.addActionListener(e -> {
            VentanaPaciente ventana = new VentanaPaciente();
            ventana.mostrarPestana(indice);
            ventana.setVisible(true);
        });

        tarjeta.add(nombre);
        tarjeta.add(detalle);
        tarjeta.add(boton);

        return tarjeta;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(
                    UIManager.getSystemLookAndFeelClassName()
                );
            } catch (Exception e) {
                // Se conserva el estilo predeterminado.
            }

            new TableroSalud().setVisible(true);
        });
    }
}