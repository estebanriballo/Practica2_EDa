package practica2_eda;

import java.util.*;
import java.util.concurrent.*;

public class Simulacion {
    private Queue<Visitante> llegadas;
    private Atraccion atraccion;
    private Visitante masEsperado;

    public Simulacion() {
        this.llegadas = new LinkedBlockingQueue<>();
        this.atraccion = new Atraccion();
    }
    
    public void ejecutar(){
        GeneradorLotes g;
    }
}
