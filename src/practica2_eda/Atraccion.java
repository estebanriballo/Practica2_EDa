package practica2_eda;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Atraccion ShoottheBug: gestiona la cola de espera y el tren de vagonetas.
 *
 * <p>La cola de espera es una cola con prioridad ({@code PriorityQueue}) en la
 * que los visitantes se ordenan por tipo de pase, por instante de llegada y,
 * en ultimo termino, por identificador.</p>
 *
 * <p>El tren dispone de 5 vagonetas individuales, por lo que en cada recorrido
 * suben como maximo 5 visitantes, y tarda 300 unidades de tiempo en realizar
 * el recorrido completo.</p>
 *
 * @author XX - GX
 */
public class Atraccion {

    /** Numero de vagonetas del tren, es decir, visitantes por recorrido. */
    public static final int CAPACIDAD_VAGONETAS = 5;

    /** Duracion del recorrido en unidades de tiempo. */
    public static final int DURACION_ATRACCION = 300;

    /** Cola con prioridad de los visitantes que esperan su turno. */
    private final PriorityQueue<Visitante> espera;

    /** Recorridos realizados por el tren, en orden cronologico. */
    private final List<Recorrido> recorridos;

    /** Instante en el que el tren vuelve a estar disponible. */
    private int proximoDisponible;

    /**
     * Constructor de la atraccion: cola de espera vacia y tren disponible.
     */
    public Atraccion() {
        this.espera = new PriorityQueue<Visitante>();
        this.recorridos = new ArrayList<Recorrido>();
        this.proximoDisponible = 0;
    }

    /**
     * Incorpora un visitante a la cola de espera de la atraccion.
     *
     * @param visitante visitante que llega a la cola
     */
    public void anadirAEspera(Visitante visitante) {
        espera.add(visitante);
    }

    /**
     * Devuelve el numero de visitantes que hay actualmente en la cola de espera.
     *
     * @return numero de visitantes en espera
     */
    public int getVisitantesEnEspera() {
        return espera.size();
    }

    /**
     * Indica si el tren esta disponible en el instante indicado.
     *
     * @param instante instante en unidades de tiempo
     * @return true si el tren puede salir en ese instante
     */
    public boolean hayEspacioEnElTren(int instante) {
        return instante >= proximoDisponible;
    }

    /**
     * Indica si el tren puede salir en el instante indicado cumpliendo la
     * capacidad de las vagonetas.
     *
     * @param instante instante en unidades de tiempo
     * @param permitirParcial si es true, tambien puede salir con menos visitantes
     *        que la capacidad (usado en el vaciado final de la cola)
     * @return true si el tren puede realizar un recorrido
     */
    public boolean puedeSalir(int instante, boolean permitirParcial) {
        if (!hayEspacioEnElTren(instante)) {
            return false;
        }
        if (permitirParcial) {
            return !espera.isEmpty();
        }
        return espera.size() >= CAPACIDAD_VAGONETAS;
    }

    /**
     * Inicia un recorrido: extrae de la cola de espera los visitantes con
     * mayor prioridad y registra el recorrido realizado.
     *
     * @param instante instante de salida del tren en unidades de tiempo
     * @return recorrido realizado por el tren
     */
    public Recorrido iniciarRecorrido(int instante) {
        List<Visitante> grupo = new ArrayList<Visitante>(CAPACIDAD_VAGONETAS);
        while (grupo.size() < CAPACIDAD_VAGONETAS && !espera.isEmpty()) {
            Visitante visitante = espera.poll();
            visitante.setInstanteInicio(instante);
            grupo.add(visitante);
        }
        Recorrido recorrido = new Recorrido(instante, grupo);
        recorridos.add(recorrido);
        proximoDisponible = instante + DURACION_ATRACCION;
        return recorrido;
    }

    /**
     * Devuelve el instante en el que el tren vuelve a estar disponible.
     *
     * @return instante de disponibilidad del tren
     */
    public int getProximoDisponible() {
        return proximoDisponible;
    }

    /**
     * Devuelve la lista de recorridos realizados por el tren.
     *
     * @return lista de recorridos en orden cronologico
     */
    public List<Recorrido> getRecorridos() {
        return Collections.unmodifiableList(recorridos);
    }
}
