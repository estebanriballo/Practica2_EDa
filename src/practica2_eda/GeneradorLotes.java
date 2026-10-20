package practica2_eda;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * Generador de lotes de visitantes de la atraccion ShoottheBug.
 *
 * <p>La simulacion se define con cuatro parametros: el numero de lotes
 * ({@code N}), el numero de visitantes Standard por lote ({@code E}), el numero
 * de visitantes Fast pass por lote ({@code F}) y el tiempo entre lotes
 * ({@code L}).</p>
 *
 * <p>El lote <em>k</em> (contando desde 0) llega en el instante {@code k * L}.
 * Cada lote esta formado por {@code E} visitantes Standard, seguidos de
 * {@code F} visitantes Fast pass y, por ultimo, de un visitante VIP pass;
 * todos los visitantes de un mismo lote llegan al instante.</p>
 *
 * <p>Los identificadores de los visitantes se asignan de forma correlativa
 * desde el 1 y en el mismo orden en que se crean.</p>
 *
 * @author XX - GX
 */
public class GeneradorLotes {

    /** Numero de lotes de visitantes (N). */
    private final int numeroLotes;

    /** Numero de visitantes Standard por lote (E). */
    private final int visitantesStandard;

    /** Numero de visitantes Fast pass por lote (F). */
    private final int visitantesFastPass;

    /** Tiempo entre lotes en unidades de tiempo (L). */
    private final int intervalo;

    /** Siguiente identificador disponible para asignar a un visitante. */
    private int siguienteIdentificador;

    /**
     * Constructor del generador de lotes.
     *
     * @param numeroLotes numero de lotes de visitantes (N)
     * @param visitantesStandard numero de visitantes Standard por lote (E)
     * @param visitantesFastPass numero de visitantes Fast pass por lote (F)
     * @param intervalo tiempo entre lotes en unidades de tiempo (L)
     * @throws IllegalArgumentException si algun parametro tiene un valor no valido
     */
    public GeneradorLotes(int numeroLotes, int visitantesStandard, int visitantesFastPass, int intervalo) {
        if (numeroLotes <= 0) {
            throw new IllegalArgumentException("El numero de lotes (N) debe ser mayor que cero");
        }
        if (visitantesStandard < 0) {
            throw new IllegalArgumentException("El numero de visitantes Standard (E) no puede ser negativo");
        }
        if (visitantesFastPass < 0) {
            throw new IllegalArgumentException("El numero de visitantes Fast pass (F) no puede ser negativo");
        }
        if (intervalo <= 0) {
            throw new IllegalArgumentException("El tiempo entre lotes (L) debe ser mayor que cero");
        }
        this.numeroLotes = numeroLotes;
        this.visitantesStandard = visitantesStandard;
        this.visitantesFastPass = visitantesFastPass;
        this.intervalo = intervalo;
        this.siguienteIdentificador = 1;
    }

    /**
     * Genera el lote de visitantes indicado.
     *
     * <p>Los visitantes se devuelven en una cola FIFO, en el mismo orden en el
     * que llegan a la atraccion: primero los Standard, despues los Fast pass y
     * por ultimo el VIP pass.</p>
     *
     * @param indiceLote indice del lote a generar, empezando en 0
     * @return cola con los visitantes del lote
     * @throws IllegalArgumentException si el indice de lote no es valido
     */
    public Queue<Visitante> generarLote(int indiceLote) {
        if (indiceLote < 0 || indiceLote >= numeroLotes) {
            throw new IllegalArgumentException("Indice de lote fuera de rango: " + indiceLote);
        }
        Queue<Visitante> lote = new ArrayDeque<>();
        int instante = getInstanteLlegada(indiceLote);
        for (int i = 0; i < visitantesStandard; i++) {
            lote.add(crearVisitante(TipoVisitante.Standard, instante));
        }
        for (int i = 0; i < visitantesFastPass; i++) {
            lote.add(crearVisitante(TipoVisitante.FastPass, instante));
        }
        lote.add(crearVisitante(TipoVisitante.VIPPass, instante));
        return lote;
    }

    /**
     * Devuelve el instante de llegada del lote indicado.
     *
     * @param indiceLote indice del lote, empezando en 0
     * @return instante de llegada en unidades de tiempo
     */
    public int getInstanteLlegada(int indiceLote) {
        return indiceLote * intervalo;
    }

    /**
     * Devuelve el numero de lotes de visitantes.
     *
     * @return numero de lotes (N)
     */
    public int getNumeroLotes() {
        return numeroLotes;
    }

    /**
     * Devuelve el numero de visitantes Standard por lote.
     *
     * @return numero de visitantes Standard por lote (E)
     */
    public int getVisitantesStandard() {
        return visitantesStandard;
    }

    /**
     * Devuelve el numero de visitantes Fast pass por lote.
     *
     * @return numero de visitantes Fast pass por lote (F)
     */
    public int getVisitantesFastPass() {
        return visitantesFastPass;
    }

    /**
     * Devuelve el tiempo entre lotes en unidades de tiempo.
     *
     * @return intervalo entre lotes (L)
     */
    public int getIntervalo() {
        return intervalo;
    }

    /**
     * Crea un visitante con el identificador correlativo que le corresponde.
     *
     * @param tipo tipo de pase del visitante
     * @param instante instante de llegada en unidades de tiempo
     * @return visitante recien creado
     */
    private Visitante crearVisitante(TipoVisitante tipo, int instante) {
        Visitante visitante = new Visitante(siguienteIdentificador, tipo, instante);
        siguienteIdentificador++;
        return visitante;
    }
}
