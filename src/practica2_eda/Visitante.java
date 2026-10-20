package practica2_eda;

import java.time.*;

public class Visitante implements Comparable<Visitante> {
    private static int num = 0;
    private int identificador;
    private TipoVisitante tVis;
    private LocalTime horaLlegada;
    private LocalTime horaInicio;

    public Visitante(TipoVisitante tVis) {
        num++;
        this.identificador = num;
        this.tVis = tVis;
        this.horaLlegada = LocalTime.now();
    }

    public static int getNum() {
        return num;
    }

    public int getIdentificador() {
        return identificador;
    }

    public void setIdentificador(int identificador) {
        this.identificador = identificador;
    }

    public TipoVisitante gettVis() {
        return tVis;
    }

    public void settVis(TipoVisitante tVis) {
        this.tVis = tVis;
    }

    public LocalTime getHoraLlegada() {
        return horaLlegada;
    }

    public void setHoraLlegada(LocalTime horaLlegada) {
        this.horaLlegada = horaLlegada;
    }

    public LocalTime getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(LocalTime horaInicio) {
        this.horaInicio = horaInicio;
    }

    @Override
    public int compareTo(Visitante o) {
        if (this.tVis.compareTo(o.gettVis()) == 0){
            if (this.horaLlegada.compareTo(o.horaLlegada) == 0){
                if (this.identificador > o.identificador){
                    return 1;
                } else if (this.identificador < o.identificador){
                    return -1;
                }
            } else {
                return this.horaLlegada.compareTo(o.horaLlegada);
            }
        } else {
            return this.tVis.compareTo(o.gettVis());
        }
        
        return 0;
    }
}
