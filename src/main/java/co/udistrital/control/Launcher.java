package co.udistrital.control;

/**
 * Punto de entrada de la aplicacion lista Circular.
 * <p>
 * Sigue el patron MVC: unicamente instancia {@link ControlPrincipal},
 * que se encarga de crear y conectar el modelo, la vista y el controlador de eventos.
 * </p>
 */
public class Launcher {

    /**
     * Metodo principal. Instancia el controlador maestro para arrancar la aplicacion.
     *
     * @param args Argumentos de linea de comandos (no utilizados).
     */
    public static void main(String[] args) {
        new ControlPrincipal();
    }
}