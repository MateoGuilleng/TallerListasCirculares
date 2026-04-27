/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.udistrital.modelo;

/**
 *
 * @author USER
 */

public class Jugador {
    private int id;
    private Jugador siguiente;

    public Jugador(int id) {
        this.id = id;
        this.siguiente = null;
    }

    public int getId() { return id; }
    public Jugador getSiguiente() { return siguiente; }
    public void setSiguiente(Jugador siguiente) { this.siguiente = siguiente; }
}