package co.udistrital.control;

import co.udistrital.modelo.JuegoModelo;
import co.udistrital.vista.JuegoVista;

import javax.swing.SwingUtilities;

/**
 * Controlador maestro. Punto de entrada real de la aplicacion.
 * Crea y conecta el Modelo, la Vista y el ControlVista.
 * El Launcher unicamente instancia esta clase.
 *
 * Forma parte de la capa de control dentro de la arquitectura MVC.
 */
public class ControlPrincipal {

    private JuegoModelo modelo;
    private JuegoVista  vista;
    private ControlVista controlVista;
    private int ultimoDado = 0;

    public ControlPrincipal() {
        modelo       = new JuegoModelo();
        vista        = new JuegoVista();
        controlVista = new ControlVista(this, vista);

        SwingUtilities.invokeLater(() -> {
            vista.agregarListener(controlVista);
            vista.setVisible(true);
            controlVista.arrancar();
        });
    }

    // -------------------------------------------------------------------------
    // API de negocio (usada por ControlVista)
    // -------------------------------------------------------------------------

    /** Inicializa la mesa circular con n jugadores. */
    public void iniciarJuego(int n) {
        modelo.crearMesaCircular(n);
    }

    /**
     * Ejecuta un turno: lanza el dado y aplica la regla par/impar.
     * @return Mensaje descriptivo del resultado.
     */
    public String ejecutarTurno() {
        ultimoDado = modelo.lanzarDado();
        int id = modelo.getIdActual();

        if (ultimoDado % 2 != 0) {
            modelo.eliminarJugadorActual();
            return "Jugador " + id + " saco " + ultimoDado + " (IMPAR) - ELIMINADO";
        } else {
            modelo.avanzarTurno();
            return "Jugador " + id + " saco " + ultimoDado + " (PAR) - CONTINUA";
        }
    }

    /** Reinicia el modelo para una nueva partida. */
    public void reiniciarModelo() {
        modelo    = new JuegoModelo();
        ultimoDado = 0;
    }

    public int  getUltimoDado()     { return ultimoDado; }
    public boolean juegoTerminado() { return modelo.getTotal() <= 1; }
    public int  getIdActual()       { return modelo.getIdActual(); }
    public int  getIdGanador()      { return modelo.getIdActual(); }
    public int  getTotalJugadores() { return modelo.getTotal(); }
}
