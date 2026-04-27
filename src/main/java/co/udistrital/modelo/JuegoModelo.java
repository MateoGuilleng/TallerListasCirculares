/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.udistrital.modelo;

/**
 *
 * @author USER
 */
public class JuegoModelo {

    private Jugador actual;
    private Jugador anterior;
    private int cantidadJugadores;

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
        anterior = actual;
        actual = cabeza;
    }

    public void eliminarJugadorActual() {
        anterior.setSiguiente(actual.getSiguiente());
        actual = anterior.getSiguiente();
        cantidadJugadores--;
    }

    public void avanzarTurno() {
        anterior = actual;
        actual = actual.getSiguiente();
    }

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
