package practica2_eda;

import java.util.*;
import java.time.*;

public class Practica2_EDa {

    public static void main(String[] args) {
        PriorityQueue<Visitante> a = new PriorityQueue<>();
        
        a.add(new Visitante(TipoVisitante.Standard));
        a.add(new Visitante(TipoVisitante.Standard));
        a.add(new Visitante(TipoVisitante.VIPPass));
        a.add(new Visitante(TipoVisitante.FastPass));
        
        while(!a.isEmpty()){
            System.out.println(a.poll().getIdentificador());
        }
    }
    
}
