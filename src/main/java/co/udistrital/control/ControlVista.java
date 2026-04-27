package co.udistrital.control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.Timer;

import co.udistrital.vista.JuegoVista;

/**
 * Controlador de eventos de la interfaz grafica.
 * <p>
 * Recibe {@link ControlPrincipal} y {@link JuegoVista} por inyeccion de
 * dependencias; nunca los instancia directamente, respetando el principio
 * de inversion de dependencias del patron MVC.
 * Responsabilidades:
 * <ul>
 *   <li>Capturar las acciones del usuario (botones "Lanzar Dado" y "Reiniciar").</li>
 *   <li>Delegar toda la logica de negocio a {@link ControlPrincipal}.</li>
 *   <li>Actualizar la vista con los resultados de cada turno.</li>
 *   <li>Solicitar y validar el numero de jugadores al inicio de cada partida.</li>
 * </ul>
 * </p>
 */
public class ControlVista implements ActionListener {

    /** Comando de accion para el boton "Lanzar Dado". */
    public static final String CMD_TURNO     = "TURNO";

    /** Comando de accion para el boton "Reiniciar". */
    public static final String CMD_REINICIAR = "REINICIAR";

    private final ControlPrincipal controlPrincipal;
    private final JuegoVista       vista;

    /**
     * Construye el controlador de vista con las dependencias inyectadas.
     *
     * @param controlPrincipal Controlador maestro que gestiona la logica del juego.
     * @param vista            Vista principal de la aplicacion.
     */
    public ControlVista(ControlPrincipal controlPrincipal, JuegoVista vista) {
        this.controlPrincipal = controlPrincipal;
        this.vista            = vista;
    }

    /**
     * Inicia el flujo de la aplicacion solicitando el numero de jugadores.
     * Debe ser llamado por {@link ControlPrincipal} una vez que la vista esta visible.
     */
    public void arrancar() {
        pedirJugadoresYArrancar();
    }

    /**
     * Procesa los eventos de los botones de la interfaz.
     *
     * @param e Evento de accion generado por un componente de la vista.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();
        if (CMD_TURNO.equals(cmd)) {
            manejarTurno();
        } else if (CMD_REINICIAR.equals(cmd)) {
            manejarReiniciar();
        }
    }

    /**
     * Solicita al usuario el numero de jugadores mediante un dialogo,
     * valida la entrada (minimo 2) e inicializa la partida.
     */
    private void pedirJugadoresYArrancar() {
        int n = 0;
        while (true) {
            String input = JOptionPane.showInputDialog(
                    vista,
                    "Cuantos jugadores participan? (minimo 2)",
                    "Mini-Pig Circular",
                    JOptionPane.QUESTION_MESSAGE);

            if (input == null) System.exit(0);

            if (esNumeroValido(input)) {
                n = Integer.parseInt(input.trim());
                if (n >= 2) break;
            }
            JOptionPane.showMessageDialog(vista,
                    "Ingresa un numero de 2 o mas jugadores.",
                    "Valor invalido", JOptionPane.WARNING_MESSAGE);
        }

        controlPrincipal.iniciarJuego(n);
        vista.inicializarJugadores(n);
        vista.resaltarJugador(controlPrincipal.getIdActual());
        vista.mostrarMensaje("Juego iniciado con " + n + " jugadores. Buena suerte!");
        vista.habilitarTurno(true);
    }

    /**
     * Maneja el turno actual: ejecuta la logica, anima el dado y actualiza
     * el estado visual de los jugadores. El boton se bloquea durante la animacion.
     */
    private void manejarTurno() {
        vista.habilitarTurno(false);

        final int     idJugador = controlPrincipal.getIdActual();
        final String  resultado = controlPrincipal.ejecutarTurno();
        final int     dado      = controlPrincipal.getUltimoDado();
        final boolean eliminado = resultado.contains("ELIMINADO");

        vista.animarDado(dado);

        // Espera a que termine la animacion del dado (~700 ms) antes de actualizar la UI
        Timer delay = new Timer(700, ev -> {
            vista.mostrarMensaje(resultado);
            if (eliminado) vista.eliminarJugadorVisual(idJugador);

            if (controlPrincipal.juegoTerminado()) {
                int ganador = controlPrincipal.getIdGanador();
                vista.resaltarJugador(ganador);
                vista.mostrarMensaje("*** EL GANADOR ES EL JUGADOR " + ganador + " ***");
                JOptionPane.showMessageDialog(vista,
                        "Gano el Jugador " + ganador + "!",
                        "Fin del juego", JOptionPane.INFORMATION_MESSAGE);
            } else {
                vista.resaltarJugador(controlPrincipal.getIdActual());
                vista.habilitarTurno(true);
            }
        });
        delay.setRepeats(false);
        delay.start();
    }

    /**
     * Reinicia el modelo y solicita una nueva configuracion de jugadores
     * sin cerrar ni recrear la ventana.
     */
    private void manejarReiniciar() {
        controlPrincipal.reiniciarModelo();
        vista.habilitarTurno(false);
        vista.mostrarMensaje("--- Juego reiniciado ---");
        pedirJugadoresYArrancar();
    }

    /**
     * Verifica que la cadena recibida represente un numero entero valido.
     *
     * @param valor Cadena a validar.
     * @return {@code true} si la cadena es un entero parseable.
     */
    private boolean esNumeroValido(String valor) {
        if (valor == null || valor.trim().isEmpty()) return false;
        try {
            Integer.parseInt(valor.trim());
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }
}