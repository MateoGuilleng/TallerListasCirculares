/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.udistrital.vista;

import java.util.Scanner;

/**
 * Encargada de la interacción con el usuario vía consola.
 */
public class JuegoVista {

    private Scanner sc = new Scanner(System.in);

    /**
     * Solicita un dato numérico al usuario.
     *
     * @param msj Mensaje informativo para el usuario.
     * @return El número entero ingresado.
     */
    public int leerEntero(String msj) {
        System.out.println(msj);
        return sc.nextInt();
    }

    /**
     * Imprime un mensaje en la consola.
     *
     * @param msj Cadena de texto a mostrar.
     */
    public void escribir(String msj) {
        System.out.println(msj);
    }
}
