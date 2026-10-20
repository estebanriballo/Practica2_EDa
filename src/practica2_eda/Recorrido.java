package practica2_eda;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Recorrido realizado por el tren de la atraccion ShoottheBug.
 *
 * <p>Un recorrido se identifica por el instante en el que sale el tren y por la
 * lista de visitantes que suben a las vagonetas en ese viaje.</p>
 *
 * @author XX - GX
 */
public class Recorrido {

    /** Instante de salida del tren en unidades de tiempo. */
    private final int instanteInicio;

    /** Visitantes que participan en el recorrido. */
    private final List<Visitante> participantes;

    /**
     * Constructor de un recorrido.
     *
     * @param instanteInicio instante de salida del tren en unidades de tiempo
     * @param participantes visitantes que suben al tren
     */
    public Recorrido(int instanteInicio, List<Visitante> participantes) {
        this.instanteInicio = instanteInicio;
        this.participantes = new ArrayList<>(participantes);
    }

    /**
     * Devuelve el instante de salida del tren.
     *
     * @return instante de inicio en unidades de tiempo
     */
    public int getInstanteInicio() {
        return instanteInicio;
    }

    /**
     * Devuelve los participantes del recorrido.
     *
     * @return lista de participantes del recorrido
     */
    public List<Visitante> getParticipantes() {
        return Collections.unmodifiableList(participantes);
    }

    /**
     * Devuelve el numero de participantes del recorrido.
     *
     * @return numero de visitantes que suben al tren
     */
    public int getNumeroParticipantes() {
        return participantes.size();
    }

    /**
     * Devuelve la representacion textual del recorrido con los participantes y
     * su tipo de pase.
     *
     * @return descripcion del recorrido
     */
    @Override
    public String toString() {
        StringBuilder texto = new StringBuilder();
        for (Visitante visitante : participantes) {
            if (texto.length() > 0) {
                texto.append(", ");
            }
            texto.append(visitante.getIdentificador())
                .append(" (").append(visitante.getTipo().getDescripcion()).append(")");
        }
        return texto.toString();
    }
}
