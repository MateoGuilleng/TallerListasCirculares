package co.udistrital.modelo;

/**
 * Representa un nodo dentro de la lista enlazada circular de la mesa de juego.
 * <p>
 * Cada jugador conoce su identificador unico y la referencia al jugador
 * que tiene a su derecha en el circulo.
 * </p>
 */
public class Jugador {

    private int     id;
    private Jugador siguiente;

    /**
     * Crea un nuevo jugador con el identificador indicado.
     * El puntero {@code siguiente} se inicializa en {@code null}.
     *
     * @param id Numero de identificacion unico del jugador.
     */
    public Jugador(int id) {
        this.id        = id;
        this.siguiente = null;
    }

    /**
     * Retorna el identificador del jugador.
     *
     * @return ID del jugador.
     */
    public int getId() {
        return id;
    }

    /**
     * Retorna el nodo del jugador siguiente en el circulo.
     *
     * @return Referencia al siguiente {@link Jugador}.
     */
    public Jugador getSiguiente() {
        return siguiente;
    }

    /**
     * Establece el jugador que sera el sucesor de este nodo en la lista circular.
     *
     * @param siguiente Nodo que ocupara la posicion siguiente.
     */
    public void setSiguiente(Jugador siguiente) {
        this.siguiente = siguiente;
    }
}