/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package co.udistrital.vista;
import java.util.Scanner;

public class JuegoVista {
    private Scanner sc = new Scanner(System.in);

    public int leerEntero(String msj) {
        System.out.println(msj);
        return sc.nextInt();
    }

    public void escribir(String msj) {
        System.out.println(msj);
    }
}