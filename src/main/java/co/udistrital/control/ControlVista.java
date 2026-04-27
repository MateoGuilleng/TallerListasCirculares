package co.udistrital.control;

import co.udistrital.vista.JuegoVista;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Controlador de eventos de la interfaz grafica.
 * Recibe la Vista y el ControlPrincipal por inyeccion de dependencias.
 *
 * Forma parte de la capa de control dentro de la arquitectura MVC.
 */
public class ControlVista implements ActionListener {

    public static final String CMD_TURNO     = "TURNO";
    public static final String CMD_REINICIAR = "REINICIAR";

    private final ControlPrincipal controlPrincipal;
    private final JuegoVista       vista;

    public ControlVista(ControlPrincipal controlPrincipal, JuegoVista vista) {
        this.controlPrincipal = controlPrincipal;
        this.vista            = vista;
    }

    public void arrancar() {
        pedirJugadoresYArrancar();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();
        if (CMD_TURNO.equals(cmd)) {
            manejarTurno();
        } else if (CMD_REINICIAR.equals(cmd)) {
            manejarReiniciar();
        }
    }

    private void pedirJugadoresYArrancar() {
        int n = 0;
        while (true) {
            String input = JOptionPane.showInputDialog(
                    vista,
                    "Cuantos jugadores participan? (minimo 2)",
                    "Lista Circular",
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

    private void manejarTurno() {
        vista.habilitarTurno(false);

        final int     idJugador = controlPrincipal.getIdActual();
        final String  resultado = controlPrincipal.ejecutarTurno();
        final int     dado      = controlPrincipal.getUltimoDado();
        final boolean eliminado = resultado.contains("ELIMINADO");

        vista.animarDado(dado);

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

    private void manejarReiniciar() {
        controlPrincipal.reiniciarModelo();
        vista.habilitarTurno(false);
        vista.mostrarMensaje("--- Juego reiniciado ---");
        pedirJugadoresYArrancar();
    }

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