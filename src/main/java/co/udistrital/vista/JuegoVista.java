package co.udistrital.vista;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.Timer;

import co.udistrital.control.ControlVista;

/**
 * Vista principal del juego lista simple enlazada Circular.
 * <p>
 * Extiende {@link JFrame} y construye la interfaz grafica compuesta por:
 * <ul>
 *   <li>Una mesa circular ({@link MesaPanel}) donde se posicionan los jugadores.</li>
 *   <li>Un panel lateral con la animacion del dado y el log de eventos.</li>
 *   <li>Botones de accion: "Lanzar Dado" y "Reiniciar".</li>
 * </ul>
 * </p>
 * <p>
 * Esta clase pertenece exclusivamente a la capa de presentacion (Vista) del
 * patron MVC. No contiene logica de negocio ni referencias al modelo.
 * </p>
 */
public class JuegoVista extends JFrame {

    /** Rutas relativas a la raiz del proyecto para las imagenes de los gatos. */
    private static final String[] CAT_PATHS = {
        "src/Images/Cats/Cat1.jpg",
        "src/Images/Cats/Cat2.jpg",
        "src/Images/Cats/Cat3.jpg",
        "src/Images/Cats/Cat4.jpg",
        "src/Images/Cats/Cat5.jpg",
        "src/Images/Cats/Cat1.jpg"
    };

    /** Rutas relativas a la raiz del proyecto para las caras del dado. */
    private static final String[] DADO_PATHS = {
        "src/Images/Dado/dado1.png",
        "src/Images/Dado/dado2.png",
        "src/Images/Dado/dado3.png",
        "src/Images/Dado/dado4.png",
        "src/Images/Dado/dado5.png",
        "src/Images/Dado/dado6.png"
    };

    private static final int CAT_SIZE  = 80;
    private static final int DADO_SIZE = 70;

    private MesaPanel          mesaPanel;
    private JLabel             dadoLabel;
    private JTextArea          logArea;
    private JButton            btnTurno;
    private JButton            btnReiniciar;
    private ImageIcon[]        dadoIconos;
    private List<JugadorPanel> jugadoresPanels = new ArrayList<>();

    /**
     * Construye la ventana principal, carga los iconos del dado y ensambla la UI.
     */
    public JuegoVista() {
        super("lista simple enlazada Circular");
        dadoIconos = cargarDado();
        buildUI();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(780, 620);
        setLocationRelativeTo(null);
        setResizable(false);
    }

    // =========================================================================
    // Construccion de la UI
    // =========================================================================

    /**
     * Ensambla todos los paneles y componentes de la interfaz grafica.
     */
    private void buildUI() {
        setLayout(new BorderLayout(8, 8));
        getContentPane().setBackground(new Color(34, 40, 49));

        mesaPanel = new MesaPanel();
        add(mesaPanel, BorderLayout.CENTER);

        JPanel rightPanel = new JPanel(new BorderLayout(6, 6));
        rightPanel.setBackground(new Color(34, 40, 49));
        rightPanel.setBorder(BorderFactory.createEmptyBorder(10, 4, 10, 10));

        dadoLabel = new JLabel(dadoIconos[0], SwingConstants.CENTER);
        dadoLabel.setPreferredSize(new Dimension(DADO_SIZE + 20, DADO_SIZE + 20));
        JPanel dadoWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER));
        dadoWrapper.setBackground(new Color(57, 62, 70));
        dadoWrapper.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.WHITE, 1),
                "Dado", 0, 0, null, Color.WHITE));
        dadoWrapper.add(dadoLabel);
        rightPanel.add(dadoWrapper, BorderLayout.NORTH);

        logArea = new JTextArea(12, 18);
        logArea.setEditable(false);
        logArea.setBackground(new Color(57, 62, 70));
        logArea.setForeground(new Color(238, 238, 238));
        logArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        logArea.setLineWrap(true);
        logArea.setWrapStyleWord(true);
        JScrollPane scroll = new JScrollPane(logArea);
        scroll.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.WHITE, 1),
                "Log", 0, 0, null, Color.WHITE));
        rightPanel.add(scroll, BorderLayout.CENTER);
        add(rightPanel, BorderLayout.EAST);

        JPanel botones = new JPanel(new FlowLayout(FlowLayout.CENTER, 16, 8));
        botones.setBackground(new Color(34, 40, 49));

        btnTurno = crearBoton("Lanzar Dado", new Color(0, 173, 181));
        btnTurno.setActionCommand(ControlVista.CMD_TURNO);
        btnTurno.setEnabled(false);

        btnReiniciar = crearBoton("Reiniciar", new Color(238, 108, 77));
        btnReiniciar.setActionCommand(ControlVista.CMD_REINICIAR);

        botones.add(btnTurno);
        botones.add(btnReiniciar);
        add(botones, BorderLayout.SOUTH);
    }

    /**
     * Crea un boton estilizado con el texto y color de fondo indicados.
     *
     * @param texto Etiqueta del boton.
     * @param color Color de fondo del boton.
     * @return Boton configurado.
     */
    private JButton crearBoton(String texto, Color color) {
        JButton btn = new JButton(texto);
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setFont(new Font("SansSerif", Font.BOLD, 14));
        btn.setBorder(BorderFactory.createEmptyBorder(8, 18, 8, 18));
        return btn;
    }

    // =========================================================================
    // API publica para ControlVista
    // =========================================================================

    /**
     * Registra un {@link ActionListener} en los botones de la vista.
     *
     * @param listener Listener que procesara los eventos de los botones.
     */
    public void agregarListener(ActionListener listener) {
        btnTurno.addActionListener(listener);
        btnReiniciar.addActionListener(listener);
    }

    /**
     * Inicializa la mesa con {@code n} jugadores, creando un {@link JugadorPanel}
     * por cada uno. Las imagenes de gatos se reciclan ciclicamente si hay mas
     * jugadores que imagenes disponibles.
     *
     * @param n Numero de jugadores a mostrar en la mesa.
     */
    public void inicializarJugadores(int n) {
        jugadoresPanels.clear();
        mesaPanel.removeAll();

        for (int i = 0; i < n; i++) {
            ImageIcon icono = cargarIcono(CAT_PATHS[i % CAT_PATHS.length], CAT_SIZE, CAT_SIZE);
            JugadorPanel jp = new JugadorPanel(i + 1, icono);
            jugadoresPanels.add(jp);
            mesaPanel.add(jp);
        }
        mesaPanel.setNumJugadores(n);
        mesaPanel.revalidate();
        mesaPanel.repaint();
    }

    /**
     * Resalta visualmente al jugador cuyo turno es el actual.
     *
     * @param idJugador ID del jugador a resaltar.
     */
    public void resaltarJugador(int idJugador) {
        for (JugadorPanel jp : jugadoresPanels) {
            jp.setActivo(jp.getId() == idJugador);
        }
    }

    /**
     * Marca visualmente a un jugador como eliminado (tachado en rojo).
     *
     * @param idJugador ID del jugador eliminado.
     */
    public void eliminarJugadorVisual(int idJugador) {
        for (JugadorPanel jp : jugadoresPanels) {
            if (jp.getId() == idJugador) {
                jp.setEliminado(true);
            }
        }
    }

    /**
     * Inicia una animacion del dado que muestra caras aleatorias durante
     * aproximadamente 640 ms antes de mostrar el valor final.
     *
     * @param valorFinal Valor definitivo del dado (1-6) que se mostrara al terminar.
     */
    public void animarDado(final int valorFinal) {
        final int[] ticks = {0};
        Timer timer = new Timer(80, null);
        timer.addActionListener(e -> {
            int cara = (int) (Math.random() * 6);
            dadoLabel.setIcon(dadoIconos[cara]);
            ticks[0]++;
            if (ticks[0] >= 8) {
                timer.stop();
                dadoLabel.setIcon(dadoIconos[valorFinal - 1]);
            }
        });
        timer.start();
    }

    /**
     * Agrega un mensaje al area de log y desplaza el scroll al final.
     *
     * @param mensaje Texto a mostrar en el log.
     */
    public void mostrarMensaje(String mensaje) {
        logArea.append(mensaje + "\n");
        logArea.setCaretPosition(logArea.getDocument().getLength());
    }

    /**
     * Habilita o deshabilita el boton "Lanzar Dado".
     *
     * @param habilitar {@code true} para habilitar, {@code false} para deshabilitar.
     */
    public void habilitarTurno(boolean habilitar) {
        btnTurno.setEnabled(habilitar);
    }

    // =========================================================================
    // Carga de imagenes
    // =========================================================================

    /**
     * Carga los seis iconos del dado escalados al tamano definido por {@code DADO_SIZE}.
     *
     * @return Arreglo de seis {@link ImageIcon}, uno por cara del dado.
     */
    private ImageIcon[] cargarDado() {
        ImageIcon[] iconos = new ImageIcon[6];
        for (int i = 0; i < 6; i++) {
            iconos[i] = cargarIcono(DADO_PATHS[i], DADO_SIZE, DADO_SIZE);
        }
        return iconos;
    }

    /**
     * Carga y escala una imagen desde el sistema de archivos.
     * Si el archivo no existe, retorna un icono de respaldo (circulo gris).
     *
     * @param path Ruta relativa a la raiz del proyecto.
     * @param w    Ancho deseado en pixeles.
     * @param h    Alto deseado en pixeles.
     * @return {@link ImageIcon} escalado o icono de respaldo.
     */
    private ImageIcon cargarIcono(String path, int w, int h) {
        File f = new File(path);
        if (f.exists()) {
            Image img = new ImageIcon(f.getAbsolutePath()).getImage()
                    .getScaledInstance(w, h, Image.SCALE_SMOOTH);
            return new ImageIcon(img);
        }
        // Fallback: circulo gris si la imagen no se encuentra
        BufferedImage bi = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = bi.createGraphics();
        g.setColor(new Color(120, 120, 120));
        g.fillOval(0, 0, w, h);
        g.dispose();
        return new ImageIcon(bi);
    }

    // =========================================================================
    // Clases internas
    // =========================================================================

    /**
     * Panel que dibuja la mesa circular de fieltro verde y distribuye
     * los {@link JugadorPanel} en posiciones equidistantes sobre una circunferencia.
     */
    private static class MesaPanel extends JPanel {

        private int numJugadores = 0;

        /** Construye el panel con fondo oscuro y layout absoluto. */
        MesaPanel() {
            setLayout(null);
            setBackground(new Color(34, 40, 49));
        }

        /**
         * Establece el numero de jugadores para calcular su distribucion circular.
         *
         * @param n Numero de jugadores activos.
         */
        void setNumJugadores(int n) {
            this.numJugadores = n;
        }

        /** Dibuja el circulo de fieltro verde en el centro del panel. */
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            int cx = getWidth() / 2;
            int cy = getHeight() / 2;
            int r  = Math.min(cx, cy) - 55;
            g2.setColor(new Color(34, 85, 34, 180));
            g2.fillOval(cx - r, cy - r, r * 2, r * 2);
            g2.setColor(new Color(0, 120, 0));
            g2.setStroke(new BasicStroke(3));
            g2.drawOval(cx - r, cy - r, r * 2, r * 2);
        }

        /** Posiciona cada {@link JugadorPanel} en angulos equidistantes del circulo. */
        @Override
        public void doLayout() {
            super.doLayout();
            if (numJugadores == 0) return;
            int cx = getWidth() / 2;
            int cy = getHeight() / 2;
            int r  = Math.min(cx, cy) - 70;
            int pw = 100, ph = 120;

            for (int i = 0; i < getComponentCount(); i++) {
                double angle = 2 * Math.PI * i / numJugadores - Math.PI / 2;
                int x = (int) (cx + r * Math.cos(angle)) - pw / 2;
                int y = (int) (cy + r * Math.sin(angle)) - ph / 2;
                getComponent(i).setBounds(x, y, pw, ph);
            }
        }
    }

    /**
     * Panel que representa visualmente a un jugador en la mesa.
     * <p>
     * Muestra el nombre del jugador sobre su imagen de gato. Cambia de
     * apariencia segun el estado: activo (borde dorado), eliminado (tachado rojo).
     * </p>
     */
    public static class JugadorPanel extends JPanel {

        private final int    id;
        private final JLabel nombreLabel;
        private final JLabel imgLabel;
        private boolean      activo    = false;
        private boolean      eliminado = false;

        /**
         * Construye el panel del jugador con su ID e icono.
         *
         * @param id    Identificador del jugador.
         * @param icono Imagen del gato asignada al jugador.
         */
        JugadorPanel(int id, ImageIcon icono) {
            this.id = id;
            setLayout(new BorderLayout(2, 2));
            setOpaque(false);

            nombreLabel = new JLabel("Jugador " + id, SwingConstants.CENTER);
            nombreLabel.setForeground(Color.WHITE);
            nombreLabel.setFont(new Font("SansSerif", Font.BOLD, 11));

            imgLabel = new JLabel(icono, SwingConstants.CENTER);

            add(nombreLabel, BorderLayout.NORTH);
            add(imgLabel,    BorderLayout.CENTER);
        }

        /**
         * Retorna el ID del jugador representado por este panel.
         *
         * @return ID del jugador.
         */
        public int getId() { return id; }

        /**
         * Marca o desmarca este panel como el jugador activo del turno actual.
         * El nombre se resalta en dorado cuando esta activo.
         *
         * @param activo {@code true} si es el turno de este jugador.
         */
        public void setActivo(boolean activo) {
            this.activo = activo;
            nombreLabel.setForeground(activo ? new Color(255, 215, 0) : Color.WHITE);
            repaint();
        }

        /**
         * Marca este panel como eliminado. El nombre cambia a rojo
         * y se dibuja una X sobre el panel.
         *
         * @param eliminado {@code true} para marcar al jugador como eliminado.
         */
        public void setEliminado(boolean eliminado) {
            this.eliminado = eliminado;
            nombreLabel.setForeground(new Color(200, 50, 50));
            repaint();
        }

        /** Dibuja el borde de estado (activo o eliminado) sobre el panel. */
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            if (activo && !eliminado) {
                g2.setColor(new Color(255, 215, 0, 80));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);
                g2.setColor(new Color(255, 215, 0));
                g2.setStroke(new BasicStroke(2));
                g2.drawRoundRect(1, 1, getWidth() - 2, getHeight() - 2, 12, 12);
            }

            if (eliminado) {
                g2.setColor(new Color(180, 0, 0, 100));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);
                g2.setColor(Color.RED);
                g2.setStroke(new BasicStroke(3));
                g2.drawLine(8, 8, getWidth() - 8, getHeight() - 8);
                g2.drawLine(getWidth() - 8, 8, 8, getHeight() - 8);
            }
        }
    }
}