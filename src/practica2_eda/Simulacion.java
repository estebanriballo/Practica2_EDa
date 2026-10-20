package practica2_eda;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * Simulacion del comportamiento de la atraccion ShoottheBug.
 *
 * <p>La simulacion se articula sobre dos colas:</p>
 * <ul>
 *   <li>la <em>pista</em>, una cola FIFO donde se recogen los visitantes que
 *       llegan al parque, y</li>
 *   <li>la <em>cola de espera</em> de la atraccion, una cola con prioridad en
 *       la que el orden de acceso lo marcan el tipo de pase, el instante de
 *       llegada y el identificador del visitante.</li>
 * </ul>
 *
 * <p>El avance del tiempo se realiza evento a evento: en cada instante solo
 * se producen dos hechos, la llegada de un lote de visitantes y la salida
 * del tren. El tren solo sale si hay al menos 5 visitantes en la cola
 * (las 5 vagonetas del tren); una vez han llegado todos los lotes se realiza
 * un vaciado final de la cola con los visitantes que queden.</p>
 *
 * @author XX - GX
 */
public class Simulacion {

    /** Generador de los lotes de visitantes. */
    private final GeneradorLotes generador;

    /** Cola de recogida de los visitantes que llegan al parque. */
    private final Queue<Visitante> pista;

    /** Atraccion, con su cola de espera prioritaria y su tren. */
    private final Atraccion atraccion;

    /** Indice del proximo lote que hay que generar. */
    private int siguienteLote;

    /** Numero de recorridos que se han informado hasta el momento. */
    private int numeroRecorridos;

    /**
     * Constructor de la simulacion.
     *
     * @param numeroLotes numero de lotes de visitantes (N)
     * @param visitantesStandard numero de visitantes Standard por lote (E)
     * @param visitantesFastPass numero de visitantes Fast pass por lote (F)
     * @param intervalo tiempo entre lotes en unidades de tiempo (L)
     */
    public Simulacion(int numeroLotes, int visitantesStandard, int visitantesFastPass, int intervalo) {
        this.generador = new GeneradorLotes(numeroLotes, visitantesStandard, visitantesFastPass, intervalo);
        this.pista = new ArrayDeque<Visitante>();
        this.atraccion = new Atraccion();
        this.siguienteLote = 0;
        this.numeroRecorridos = 0;
    }

    /**
     * Ejecuta la simulacion completa e informa de todos los recorridos
     * realizados y del visitante que mas tiempo ha esperado.
     */
    public void ejecutar() {
        mostrarParametros();
        int instante = 0;
        while (true) {
            recogerLotes(instante);
            pasarPistaAEspera();
            intentarSalida(instante);
            int siguiente = calcularSiguienteInstante(instante);
            if (siguiente < 0) {
                break;
            }
            instante = siguiente;
        }
        mostrarInformeFinal();
    }

    /**
     * Recoge en la pista todos los lotes que llegan en el instante indicado.
     *
     * @param instante instante actual en unidades de tiempo
     */
    private void recogerLotes(int instante) {
        while (siguienteLote < generador.getNumeroLotes()
                && generador.getInstanteLlegada(siguienteLote) <= instante) {
            pista.addAll(generador.generarLote(siguienteLote));
            siguienteLote++;
        }
    }

    /**
     * Traslada los visitantes recogidos en la pista a la cola de espera
     * prioritaria de la atraccion.
     */
    private void pasarPistaAEspera() {
        while (!pista.isEmpty()) {
            atraccion.anadirAEspera(pista.poll());
        }
    }

    /**
     * Intenta que el tren salga en el instante indicado.
     *
     * <p>El tren solo sale cuando hay 5 visitantes en la cola. Cuando ya no
     * queda ningun lote por llegar se admite la salida con menos visitantes,
     * de forma que la cola se vacia por completo y todos los visitantes
     * llegan a subir a las vagonetas.</p>
     *
     * @param instante instante actual en unidades de tiempo
     * @return true si el tren ha salido en este instante
     */
    private boolean intentarSalida(int instante) {
        boolean sinMasLlegadas = (siguienteLote == generador.getNumeroLotes());
        if (!atraccion.puedeSalir(instante, sinMasLlegadas)) {
            return false;
        }
        Recorrido recorrido = atraccion.iniciarRecorrido(instante);
        mostrarRecorrido(recorrido);
        return true;
    }

    /**
     * Calcula el siguiente instante en el que puede ocurrir algo relevante.
     *
     * @param instante instante actual en unidades de tiempo
     * @return siguiente instante con eventos, o -1 si no queda ningun evento
     */
    private int calcularSiguienteInstante(int instante) {
        int siguiente = Integer.MAX_VALUE;
        if (siguienteLote < generador.getNumeroLotes()) {
            siguiente = Math.min(siguiente, generador.getInstanteLlegada(siguienteLote));
        }
        if (atraccion.getProximoDisponible() > instante) {
            siguiente = Math.min(siguiente, atraccion.getProximoDisponible());
        }
        return (siguiente == Integer.MAX_VALUE) ? -1 : siguiente;
    }

    /**
     * Devuelve el visitante que mas tiempo ha esperado desde su llegada hasta
     * el inicio de su recorrido. En caso de empate gana el que llego antes.
     *
     * @return visitante con mayor tiempo de espera, o null si no hubo recorridos
     */
    public Visitante getVisitanteConMayorEspera() {
        Visitante mejor = null;
        for (Recorrido recorrido : atraccion.getRecorridos()) {
            for (Visitante visitante : recorrido.getParticipantes()) {
                if (mejor == null || compararEspera(visitante, mejor) > 0) {
                    mejor = visitante;
                }
            }
        }
        return mejor;
    }

    /**
     * Compara el tiempo de espera de dos visitantes. En caso de empate se
     * compara el instante de llegada y, si tambien coincide, el identificador
     * (menor identificador significa que el visitante llego antes).
     *
     * @param primero primer visitante a comparar
     * @param segundo segundo visitante a comparar
     * @return valor negativo, cero o positivo
     */
    private int compararEspera(Visitante primero, Visitante segundo) {
        int comparacion = Integer.compare(primero.getTiempoEspera(), segundo.getTiempoEspera());
        if (comparacion != 0) {
            return comparacion;
        }
        comparacion = Integer.compare(primero.getInstanteLlegada(), segundo.getInstanteLlegada());
        if (comparacion != 0) {
            return comparacion;
        }
        return -Integer.compare(primero.getIdentificador(), segundo.getIdentificador());
    }

    /**
     * Muestra los parametros con los que se ejecuta la simulacion.
     */
    private void mostrarParametros() {
        System.out.println("======================================================");
        System.out.println(" Simulacion de la atraccion ShoottheBug");
        System.out.println("======================================================");
        System.out.println("Numero de lotes (N)              : " + generador.getNumeroLotes());
        System.out.println("Visitantes Standard por lote (E)  : " + generador.getVisitantesStandard());
        System.out.println("Visitantes Fast pass por lote (F) : " + generador.getVisitantesFastPass());
        System.out.println("Tiempo entre lotes (L)            : " + generador.getIntervalo());
        System.out.println("Vagonetas por recorrido           : " + Atraccion.CAPACIDAD_VAGONETAS);
        System.out.println("Duracion del recorrido            : " + Atraccion.DURACION_ATRACCION);
        System.out.println();
        System.out.println("--- Recorridos realizados ---");
    }

    /**
     * Muestra la informacion de un recorrido: instante de inicio y visitantes
     * que lo realizan con su tipo de pase.
     *
     * @param recorrido recorrido a mostrar
     */
    private void mostrarRecorrido(Recorrido recorrido) {
        numeroRecorridos++;
        System.out.println("Recorrido " + numeroRecorridos + " (inicio t=" + recorrido.getInstanteInicio()
                + "): " + recorrido);
    }

    /**
     * Muestra el visitante que mas tiempo ha esperado y el resumen de la
     * simulacion.
     */
    private void mostrarInformeFinal() {
        System.out.println();
        System.out.println("--- Visitante que mas tiempo ha esperado ---");
        Visitante visitante = getVisitanteConMayorEspera();
        if (visitante == null) {
            System.out.println("No se ha realizado ningun recorrido.");
            return;
        }
        System.out.println("Visitante " + visitante.getIdentificador()
                + " (" + visitante.getTipo().getDescripcion() + ")");
        System.out.println("  Llego a la cola en t=" + visitante.getInstanteLlegada()
                + " e inicio su recorrido en t=" + visitante.getInstanteInicio());
        System.out.println("  Tiempo de espera: " + visitante.getTiempoEspera() + " unidades de tiempo");
        System.out.println();
        System.out.println("Total de recorridos: " + atraccion.getRecorridos().size());
        System.out.println("Visitantes que han subido a las vagonetas: "
                + contarVisitantesServidos());
    }

    /**
     * Cuenta los visitantes que han realizado algun recorrido.
     *
     * @return numero de visitantes servidos
     */
    private int contarVisitantesServidos() {
        int total = 0;
        for (Recorrido recorrido : atraccion.getRecorridos()) {
            total += recorrido.getNumeroParticipantes();
        }
        return total;
    }

    /**
     * Devuelve la atraccion sobre la que se ha ejecutado la simulacion.
     *
     * @return atraccion de la simulacion
     */
    public Atraccion getAtraccion() {
        return atraccion;
    }
}
