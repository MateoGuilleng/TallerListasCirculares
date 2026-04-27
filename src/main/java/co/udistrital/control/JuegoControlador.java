/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.udistrital.control;

import co.udistrital.modelo.JuegoModelo;
import co.udistrital.vista.JuegoVista;

/**
 * Clase controladora que orquesta la comunicación entre el modelo y la vista.
 * Aplica el patrón de diseño MVC.
 */
public class JuegoControlador {

    private JuegoModelo modelo;
    private JuegoVista vista;

    /**
     * Constructor que inicializa los componentes y arranca el flujo del juego.
     */
    public JuegoControlador() {
        modelo = new JuegoModelo();
        vista = new JuegoVista();
        iniciarSesionJuego();
    }

    /**
     * Ejecuta el bucle principal del juego hasta que solo quede un ganador.
     */
    private void iniciarSesionJuego() {
        int n = vista.leerEntero("¿Cuántos jugadores participan?");
        modelo.crearMesaCircular(n);

        while (modelo.getTotal() > 1) {
            int dado = modelo.lanzarDado();
            int id = modelo.getIdActual();

            if (dado % 2 != 0) {
                vista.escribir("Jugador " + id + " sacó " + dado + " (IMPAR) -> ELIMINADO");
                modelo.eliminarJugadorActual();
            } else {
                vista.escribir("Jugador " + id + " sacó " + dado + " (PAR) -> CONTINÚA");
                modelo.avanzarTurno();
            }
        }
        vista.escribir("\n*** EL GANADOR ES EL JUGADOR " + modelo.getIdActual() + " ***");
    }
}
