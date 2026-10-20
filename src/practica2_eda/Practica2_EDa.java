package practica2_eda;

import java.util.Scanner;

/**
 * Punto de entrada del programa: simulacion de la atraccion ShoottheBug.
 *
 * <p>El programa pide por consola los cuatro parametros que definen la
 * simulacion (N, E, F y L) y a continuacion ejecuta la simulacion, informando
 * de todos los recorridos realizados y del visitante que mas tiempo ha
 * esperado.</p>
 *
 * @author XX - GX
 */
public class Practica2_EDa {

    /**
     * Constructor de la clase principal.
     */
    public Practica2_EDa() {
    }

    /**
     * Metodo principal del programa.
     *
     * @param args argumentos de linea de comandos, no utilizados
     */
    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);

        System.out.println("Simulacion de la atraccion ShoottheBug");
        System.out.println("--------------------------------------");
        int numeroLotes = pedirEnteroPositivo(teclado, "Numero de lotes de visitantes (N)");
        int visitantesStandard = pedirEnteroNoNegativo(teclado, "Visitantes Standard por lote (E)");
        int visitantesFastPass = pedirEnteroNoNegativo(teclado, "Visitantes Fast pass por lote (F)");
        int intervalo = pedirEnteroPositivo(teclado, "Tiempo entre lotes en unidades de tiempo (L)");

        Simulacion simulacion = new Simulacion(numeroLotes, visitantesStandard, visitantesFastPass, intervalo);
        simulacion.ejecutar();

        teclado.close();
    }

    /**
     * Pide por consola un numero entero mayor que cero.
     *
     * @param teclado lector de la entrada estandar
     * @param mensaje texto que se muestra al usuario
     * @return valor entero introducido por el usuario
     */
    private static int pedirEnteroPositivo(Scanner teclado, String mensaje) {
        int valor = pedirEnteroNoNegativo(teclado, mensaje);
        while (valor <= 0) {
            System.out.println("El valor debe ser mayor que cero. Vuelve a intentarlo.");
            valor = pedirEnteroNoNegativo(teclado, mensaje);
        }
        return valor;
    }

    /**
     * Pide por consola un numero entero mayor o igual que cero.
     *
     * @param teclado lector de la entrada estandar
     * @param mensaje texto que se muestra al usuario
     * @return valor entero introducido por el usuario
     */
    private static int pedirEnteroNoNegativo(Scanner teclado, String mensaje) {
        int valor;
        while (true) {
            System.out.print(mensaje + ": ");
            if (teclado.hasNextInt()) {
                valor = teclado.nextInt();
                if (valor >= 0) {
                    return valor;
                }
                System.out.println("El valor no puede ser negativo.");
            } else {
                teclado.next();
                System.out.println("Se esperaba un numero entero.");
            }
        }
    }
}
