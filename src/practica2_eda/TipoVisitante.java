package practica2_eda;

/**
 * Tipos de pase que puede tener un visitante de la atraccion ShoottheBug.
 *
 * <p>Cada tipo de pase lleva asociado un valor de <em>prioridad</em> que determina
 * el orden de acceso a la atraccion: cuanto menor es la prioridad, antes accede
 * el visitante a las vagonetas.</p>
 *
 * <p>El orden de prioridad de mayor a menor es: VIP pass, Fast pass y Standard.</p>
 *
 * @author XX - GX
 */
public enum TipoVisitante {

    /** Pase con maxima prioridad de acceso a la atraccion. */
    VIPPass("VIP pass", 0),

    /** Pase con prioridad intermedia, por detrás del VIP pass. */
    FastPass("Fast pass", 1),

    /** Pase estandar, el de menor prioridad de acceso. */
    Standard("Standard", 2);

    /** Descripcion del pase tal y como se muestra en el informe de recorridos. */
    private final String descripcion;

    /** Prioridad de acceso: valores menores implican acceso antes. */
    private final int prioridad;

    /**
     * Constructor del enumerado.
     *
     * @param descripcion texto descriptivo del tipo de pase
     * @param prioridad valor de prioridad de acceso a la atraccion
     */
    private TipoVisitante(String descripcion, int prioridad) {
        this.descripcion = descripcion;
        this.prioridad = prioridad;
    }

    /**
     * Devuelve la descripcion legible del tipo de pase.
     *
     * @return descripcion del pase
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Devuelve la prioridad de acceso del tipo de pase.
     *
     * @return valor de prioridad (0 = maxima prioridad)
     */
    public int getPrioridad() {
        return prioridad;
    }

    /**
     * Devuelve el tipo de pase a partir de su descripcion, ignorando
     * mayusculas y minusculas.
     *
     * @param descripcion descripcion buscada
     * @return tipo de pase correspondiente
     * @throws IllegalArgumentException si no existe ningun pase con esa descripcion
     */
    public static TipoVisitante desdeDescripcion(String descripcion) {
        for (TipoVisitante tipo : values()) {
            if (tipo.descripcion.equalsIgnoreCase(descripcion)) {
                return tipo;
            }
        }
        throw new IllegalArgumentException("Tipo de pase desconocido: " + descripcion);
    }

    /**
     * Devuelve la representacion textual del tipo de pase.
     *
     * @return descripcion del pase
     */
    @Override
    public String toString() {
        return descripcion;
    }
}
