package practica2_eda;

import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.*;

public class GeneradorLotes {
    private static int N = 0;
    private int E;
    private int F;
    private int L;

    public GeneradorLotes(int E, int F, int L) {
        N++;
        this.E = E;
        this.F = F;
        this.L = L;
    }

    public static int getN() {
        return N;
    }

    public int getE() {
        return E;
    }

    public void setE(int E) {
        this.E = E;
    }

    public int getF() {
        return F;
    }

    public void setF(int F) {
        this.F = F;
    }

    public int getL() {
        return L;
    }

    public void setL(int L) {
        this.L = L;
    }
    
    public Queue getLote(){
        Queue<Visitante> q = new LinkedBlockingQueue<>();

        for (int i = 0; i < this.E; i++){
            q.add(new Visitante(TipoVisitante.Standard));
        }
        
        for (int i = 0; i < this.F; i++){
            q.add(new Visitante(TipoVisitante.FastPass));
        }
        
        q.add(new Visitante(TipoVisitante.VIPPass));
    
        return q;
    }
}
