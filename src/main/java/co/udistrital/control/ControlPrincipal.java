package co.udistrital.control;

import javax.swing.SwingUtilities;

import co.udistrital.modelo.JuegoModelo;
import co.udistrital.vista.JuegoVista;

/**
 * Controlador maestro de la aplicacion Mini-Pig Circular.
 * <p>
 * Actua como orquestador principal dentro de la arquitectura MVC:
 * instancia el modelo ({@link JuegoModelo}), la vista ({@link JuegoVista})
 * y el controlador de eventos ({@link ControlVista}), inyectando las
 * dependencias necesarias entre ellos.
 * </p>
 * <p>
 * Es la unica clase que el {@link Launcher} necesita instanciar para
 * arrancar la aplicacion.
 * </p>
 */
public class ControlPrincipal {

    private JuegoModelo  modelo;
    private JuegoVista   vista;
    private ControlVista controlVista;
    private int          ultimoDado = 0;

    /**
     * Constructor principal. Crea el modelo, la vista y el controlador de eventos,
     * los conecta entre si y arranca la interfaz grafica en el hilo de Swing.
     */
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

    /**
     * Inicializa la mesa circular con el numero de jugadores indicado.
     *
     * @param n Cantidad de jugadores que participaran en la partida (minimo 2).
     */
    public void iniciarJuego(int n) {
        modelo.crearMesaCircular(n);
    }

    /**
     * Ejecuta un turno completo: lanza el dado y aplica la regla par/impar.
     * <ul>
     *   <li>Dado impar: el jugador actual es eliminado de la mesa.</li>
     *   <li>Dado par: el jugador continua y el turno avanza al siguiente.</li>
     * </ul>
     *
     * @return Mensaje descriptivo del resultado del turno.
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

    /**
     * Reinicia el modelo para comenzar una nueva partida sin recrear la vista.
     */
    public void reiniciarModelo() {
        modelo     = new JuegoModelo();
        ultimoDado = 0;
    }

    /**
     * Retorna el valor del dado obtenido en el ultimo turno ejecutado.
     *
     * @return Valor entre 1 y 6.
     */
    public int getUltimoDado() { return ultimoDado; }

    /**
     * Indica si el juego ha terminado, es decir, si solo queda un jugador.
     *
     * @return {@code true} si queda un unico jugador activo.
     */
    public boolean juegoTerminado() { return modelo.getTotal() <= 1; }

    /**
     * Retorna el ID del jugador cuyo turno es el actual.
     *
     * @return ID del jugador actual.
     */
    public int getIdActual() { return modelo.getIdActual(); }

    /**
     * Retorna el ID del ganador. Solo debe llamarse cuando {@link #juegoTerminado()} es {@code true}.
     *
     * @return ID del jugador ganador.
     */
    public int getIdGanador() { return modelo.getIdActual(); }

    /**
     * Retorna la cantidad de jugadores que siguen activos en la mesa.
     *
     * @return Total de jugadores activos.
     */
    public int getTotalJugadores() { return modelo.getTotal(); }
}