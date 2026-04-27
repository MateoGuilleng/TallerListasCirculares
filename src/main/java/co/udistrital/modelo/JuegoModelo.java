/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.udistrital.modelo;

/**
 * Gestiona la lógica de la mesa redonda y las reglas del juego. Implementa la
 * estructura de una lista enlazada simple circular.
 */
public class JuegoModelo {

    private Jugador actual;
    private Jugador anterior;
    private int cantidadJugadores;
    private static final int MAX_JUGADORES = 5;


    /**
     * Construye la mesa circular conectando N jugadores. Al finalizar, el
     * puntero 'actual' queda en el Jugador 1 y el 'anterior' en el último
     * Jugador creado.
     *
     * * @param n Cantidad de jugadores iniciales.
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
        anterior = actual; // El anterior del 1 es el último (N)
        actual = cabeza;   // Iniciamos con el 1
    }

    /**
     * Elimina al jugador actual de la mesa puenteando los nodos. El recolector
     * de basura de Java liberará la memoria del nodo desconectado.
     */
    public void eliminarJugadorActual() {
        anterior.setSiguiente(actual.getSiguiente());
        actual = anterior.getSiguiente();
        cantidadJugadores--;
    }

    /**
     * Desplaza los punteros al siguiente jugador en el sentido de la lista.
     */
    public void avanzarTurno() {
        anterior = actual;
        actual = actual.getSiguiente();
    }

    /**
     * @return Un número entero aleatorio entre 1 y 6.
     */
    public int lanzarDado() {
        return (int) (Math.random() * 6) + 1;
    }

    public int getIdActual() {
        return actual.getId();
    }

    public int getTotal() {
        return cantidadJugadores;
    }
}
