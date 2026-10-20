package practica2_eda;

/**
 * Visitante que espera su turno para subir a las vagonetas de la atraccion
 * ShoottheBug.
 *
 * <p>Cada visitante esta identificado por un numero entero unico que se le
 * asigna en el momento de su creacion, por lo que los identificadores se
 * entregan de forma correlativa desde el 1.</p>
 *
 * <p>Los instantes de llegada e inicio se expresan en <em>unidades de tiempo
 * de la simulacion</em> (no son horas del reloj), tal y como define el
 * enunciado de la practica.</p>
 *
 * <p>La clase implementa {@code Comparable} para poder usarse como elemento de
 * una cola con prioridad. El orden establecido es: primero por tipo de pase
 * (prioridad), despues por instante de llegada y, en ultimo lugar, por
 * identificador de visitante.</p>
 *
 * @author XX - GX
 */
public class Visitante implements Comparable<Visitante> {

    /** Valor que indica que el visitante todavia no ha iniciado su recorrido. */
    public static final int SIN_INICIO = -1;

    /** Identificador numerico unico del visitante. */
    private final int identificador;

    /** Tipo de pase que porta el visitante. */
    private final TipoVisitante tipo;

    /** Instante (en unidades de tiempo) en el que el visitante llega a la cola. */
    private final int instanteLlegada;

    /** Instante (en unidades de tiempo) en el que el visitante inicia su recorrido. */
    private int instanteInicio;

    /**
     * Constructor de un visitante que acaba de llegar a la cola de espera.
     *
     * @param identificador identificador numerico unico del visitante
     * @param tipo tipo de pase que porta el visitante
     * @param instanteLlegada instante de llegada en unidades de tiempo
     */
    public Visitante(int identificador, TipoVisitante tipo, int instanteLlegada) {
        this.identificador = identificador;
        this.tipo = tipo;
        this.instanteLlegada = instanteLlegada;
        this.instanteInicio = SIN_INICIO;
    }

    /**
     * Devuelve el identificador numerico del visitante.
     *
     * @return identificador del visitante
     */
    public int getIdentificador() {
        return identificador;
    }

    /**
     * Devuelve el tipo de pase que porta el visitante.
     *
     * @return tipo de pase del visitante
     */
    public TipoVisitante getTipo() {
        return tipo;
    }

    /**
     * Devuelve el instante en el que el visitante llego a la cola de espera.
     *
     * @return instante de llegada en unidades de tiempo
     */
    public int getInstanteLlegada() {
        return instanteLlegada;
    }

    /**
     * Devuelve el instante en el que el visitante inicio su recorrido.
     *
     * @return instante de inicio en unidades de tiempo o {@link #SIN_INICIO} si
     *         el visitante aun no ha subido a las vagonetas
     */
    public int getInstanteInicio() {
        return instanteInicio;
    }

    /**
     * Registra el instante en el que el visitante inicia su recorrido.
     *
     * @param instanteInicio instante de inicio en unidades de tiempo
     */
    public void setInstanteInicio(int instanteInicio) {
        this.instanteInicio = instanteInicio;
    }

    /**
     * Indica si el visitante ya ha iniciado su recorrido.
     *
     * @return true si el visitante ya ha subido a las vagonetas
     */
    public boolean haIniciadoRecorrido() {
        return instanteInicio != SIN_INICIO;
    }

    /**
     * Devuelve el tiempo que el visitante ha esperado desde su llegada hasta el
     * inicio de su recorrido.
     *
     * @return tiempo de espera en unidades de tiempo, o -1 si todavia no ha
     *         iniciado su recorrido
     */
    public int getTiempoEspera() {
        if (!haIniciadoRecorrido()) {
            return -1;
        }
        return instanteInicio - instanteLlegada;
    }

    /**
     * Compara dos visitantes siguiendo el orden de acceso a la atraccion: por
     * prioridad del tipo de pase, por instante de llegada y, si todo coincide,
     * por identificador.
     *
     * @param otro visitante con el que se compara
     * @return valor negativo, cero o positivo segun este visitante deba
     *         acceder antes, a la vez o despues que el otro
     */
    @Override
    public int compareTo(Visitante otro) {
        int comparacion = Integer.compare(this.tipo.getPrioridad(), otro.tipo.getPrioridad());
        if (comparacion != 0) {
            return comparacion;
        }
        comparacion = Integer.compare(this.instanteLlegada, otro.instanteLlegada);
        if (comparacion != 0) {
            return comparacion;
        }
        return Integer.compare(this.identificador, otro.identificador);
    }

    /**
     * Devuelve la representacion textual del visitante.
     *
     * @return identificador y tipo de pase del visitante
     */
    @Override
    public String toString() {
        return "V" + identificador + " (" + tipo.getDescripcion() + ")";
    }
}
