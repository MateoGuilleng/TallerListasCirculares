/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.udistrital.modelo;

/**
 * Representa un nodo en la lista circular. Cada jugador conoce su identidad y
 * al compañero que tiene a su derecha.
 *
 * * @author Sara
 * @version 1.0
 */
public class Jugador {

    private int id;
    private Jugador siguiente;

    /**
     * Crea un nuevo jugador con un identificador único.
     *
     * @param id El número de identificación del jugador.
     */
    public Jugador(int id) {
        this.id = id;
        this.siguiente = null;
    }

    /**
     * @return El ID del jugador.
     */
    public int getId() {
        return id;
    }

    /**
     * @return El nodo del jugador siguiente en el círculo.
     */
    public Jugador getSiguiente() {
        return siguiente;
    }

    /**
     * @param siguiente El nodo que será el sucesor en la lista.
     */
    public void setSiguiente(Jugador siguiente) {
        this.siguiente = siguiente;
    }
}
