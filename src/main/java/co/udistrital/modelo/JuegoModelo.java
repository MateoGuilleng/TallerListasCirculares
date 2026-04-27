package co.udistrital.modelo;

/**
 * Gestiona la logica de la mesa redonda y las reglas del juego.
 * <p>
 * Implementa una lista enlazada simple circular donde cada nodo es un
 * {@link Jugador}. Dos punteros ({@code actual} y {@code anterior}) permiten
 * recorrer y eliminar nodos en O(1) sin necesidad de busqueda previa.
 * </p>
 */
public class JuegoModelo {

    private Jugador actual;
    private Jugador anterior;
    private int     cantidadJugadores;

    /**
     * Construye la mesa circular conectando {@code n} jugadores en orden.
     * <p>
     * Al finalizar, {@code actual} apunta al Jugador 1 y {@code anterior}
     * al ultimo jugador creado, cerrando el ciclo.
     * </p>
     *
     * @param n Cantidad de jugadores iniciales (minimo 2).
     */
    public void crearMesaCircular(int n) {
        this.cantidadJugadores = n;
        Jugador cabeza = new Jugador(1);
        actual = cabeza;

        for (int i = 2; i <= n; i++) {
            Jugador nuevo = new Jugador(i);
            actual.setSiguiente(nuevo);
            actual = nuevo;
        }
        actual.setSiguiente(cabeza);
        anterior = actual; // El anterior del Jugador 1 es el ultimo (N)
        actual   = cabeza; // Iniciamos con el Jugador 1
    }

    /**
     * Elimina al jugador actual de la mesa puenteando los nodos.
     * <p>
     * El nodo desconectado sera liberado por el recolector de basura de Java.
     * Tras la eliminacion, {@code actual} pasa a ser el siguiente jugador en el circulo.
     * </p>
     */
    public void eliminarJugadorActual() {
        anterior.setSiguiente(actual.getSiguiente());
        actual = anterior.getSiguiente();
        cantidadJugadores--;
    }

    /**
     * Desplaza los punteros al siguiente jugador en el sentido de la lista,
     * sin eliminar al jugador actual.
     */
    public void avanzarTurno() {
        anterior = actual;
        actual   = actual.getSiguiente();
    }

    /**
     * Genera un numero aleatorio simulando el lanzamiento de un dado de seis caras.
     *
     * @return Entero aleatorio entre 1 y 6 (inclusive).
     */
    public int lanzarDado() {
        return (int) (Math.random() * 6) + 1;
    }

    /**
     * Retorna el ID del jugador cuyo turno es el actual.
     *
     * @return ID del jugador actual.
     */
    public int getIdActual() {
        return actual.getId();
    }

    /**
     * Retorna la cantidad de jugadores que siguen activos en la mesa.
     *
     * @return Total de jugadores activos.
     */
    public int getTotal() {
        return cantidadJugadores;
    }
}