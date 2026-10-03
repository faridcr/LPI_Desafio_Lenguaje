package centrosalud;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.JTableHeader;

public final class EstiloSalud {

    // Paleta de colores del Centro de Salud
    public static final Color AZUL_OSCURO =
            new Color(11, 49, 91);

    public static final Color AZUL =
            new Color(22, 132, 216);

    public static final Color TURQUESA =
            new Color(0, 150, 136);

    public static final Color FONDO =
            new Color(245, 248, 252);

    public static final Color BLANCO =
            Color.WHITE;

    public static final Color TEXTO =
            new Color(40, 55, 75);

    private EstiloSalud() {
        // Evita crear objetos de esta clase.
    }

    public static void aplicar(JFrame ventana) {

        // Estilo general de las pestañas
        UIManager.put("TabbedPane.selected",
                AZUL);
        UIManager.put("TabbedPane.background",
                AZUL_OSCURO);
        UIManager.put("TabbedPane.foreground",
                BLANCO);
        UIManager.put("TabbedPane.contentAreaColor",
                FONDO);
        UIManager.put("Button.focus",
                new Color(180, 220, 245));

        SwingUtilities.updateComponentTreeUI(ventana);

        ventana.getContentPane().setBackground(FONDO);

        // Aplica el estilo a los componentes existentes.
        estilizarContenedor(ventana.getContentPane());

        ventana.revalidate();
        ventana.repaint();
    }

    private static void estilizarContenedor(
            Container contenedor) {

        for (Component componente :
                contenedor.getComponents()) {

            if (componente instanceof JPanel) {
                componente.setBackground(FONDO);

                ((JPanel) componente).setBorder(
                        new EmptyBorder(8, 8, 8, 8));
            }

            if (componente instanceof JLabel) {
                componente.setForeground(TEXTO);
                componente.setFont(
                        new Font("Segoe UI",
                                Font.PLAIN, 13));
            }

            if (componente instanceof JButton) {
                JButton boton = (JButton) componente;

                String texto = boton.getText() == null
                        ? ""
                        : boton.getText().toLowerCase();

                boton.setFont(new Font(
                        "Segoe UI", Font.BOLD, 13));

                boton.setForeground(BLANCO);
                boton.setOpaque(true);
                boton.setFocusPainted(false);
                boton.setBorderPainted(false);
                boton.setBorder(
                        new EmptyBorder(9, 16, 9, 16));

                // Botones de acciones principales
                if (texto.contains("registrar")
                        || texto.contains("guardar")
                        || texto.contains("actualizar")) {
                    boton.setBackground(TURQUESA);
                } else if (texto.contains("eliminar")
                        || texto.contains("borrar")) {
                    boton.setBackground(
                            new Color(200, 65, 65));
                } else {
                    boton.setBackground(AZUL);
                }
            }

            if (componente instanceof JTextField) {
                JTextField campo =
                        (JTextField) componente;

                campo.setFont(new Font(
                        "Segoe UI", Font.PLAIN, 13));
                campo.setForeground(TEXTO);
                campo.setBackground(BLANCO);
                campo.setBorder(
                        BorderFactory.createCompoundBorder(
                            BorderFactory.createLineBorder(
                                new Color(205, 218, 232)),
                            new EmptyBorder(6, 8, 6, 8)));
            }

            if (componente instanceof JTextArea) {
                JTextArea area = (JTextArea) componente;
                area.setFont(new Font(
                        "Segoe UI", Font.PLAIN, 13));
                area.setForeground(TEXTO);
                area.setBackground(BLANCO);
                area.setLineWrap(true);
                area.setWrapStyleWord(true);
            }

            if (componente instanceof JComboBox<?>) {
                componente.setFont(new Font(
                        "Segoe UI", Font.PLAIN, 13));
                componente.setBackground(BLANCO);
                componente.setForeground(TEXTO);
            }

            if (componente instanceof JTable) {
                JTable tabla = (JTable) componente;

                tabla.setFont(new Font(
                        "Segoe UI", Font.PLAIN, 12));
                tabla.setRowHeight(26);
                tabla.setBackground(BLANCO);
                tabla.setForeground(TEXTO);
                tabla.setSelectionBackground(
                        new Color(205, 230, 250));
                tabla.setSelectionForeground(TEXTO);
                tabla.setGridColor(
                        new Color(225, 232, 240));

                JTableHeader cabecera =
                        tabla.getTableHeader();

                if (cabecera != null) {
                    cabecera.setBackground(AZUL_OSCURO);
                    cabecera.setForeground(BLANCO);
                    cabecera.setFont(new Font(
                            "Segoe UI", Font.BOLD, 12));
                }
            }

            if (componente instanceof JTabbedPane) {
                JTabbedPane pestanas =
                        (JTabbedPane) componente;

                pestanas.setFont(new Font(
                        "Segoe UI", Font.BOLD, 13));
                pestanas.setBackground(AZUL_OSCURO);
                pestanas.setForeground(BLANCO);
            }

            // Procesa los componentes internos sin
            // cambiar sus eventos ni sus funciones.
            if (componente instanceof Container) {
                estilizarContenedor(
                        (Container) componente);
            }
        }
    }
}
          
