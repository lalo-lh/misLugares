package com.lalo.MisLugares_U7;

import java.util.ArrayList;
import java.util.List;

public class LugaresVector implements Lugares {
    public static List<Lugar> vectorLugares = ejemploLugares();

    public LugaresVector() {
        vectorLugares = ejemploLugares();
    }

    public Lugar elemento(int id){
        return vectorLugares.get(id);
    }

    public void anyade(Lugar lugar){
        vectorLugares.add(lugar);
    }

    public int nuevo(){
        Lugar lugar = new Lugar();
        vectorLugares.add(lugar);
        return vectorLugares.size()-1;
    }

    public void borrar(int id){
        vectorLugares.remove(id);
    }

    public int tamayo(){
        return vectorLugares.size();
    }

    public void actualiza(int id, Lugar lugar){
        vectorLugares.set(id, lugar);
    }

    private static ArrayList<Lugar> ejemploLugares(){
        ArrayList<Lugar> lugares = new ArrayList<Lugar>();


        lugares.add(new Lugar("Escuela Politecnica Superior",
                "C/ Paranim, 1 4673 Gandia",
                -0.166093,
                38.995656,
                TipoLugar.EDUCACION,
                962849300,
                "http://www.epsg.upv.es",
                "Uno de los mejores lugares para formarse", 3));

        lugares.add(new Lugar("Al de siempre",
                "P.Industrial Moli - 433444",
                -0.190642,
                38.925857,
                TipoLugar.BAR,
                63647890,
                "",
                "No te pierdas el arroz.", 3));
        lugares.add(new Lugar("androidcurso.com",
                "ciberespacio",
                0.0,
                0.0,
                TipoLugar.EDUCACION,
                962849300,
                "http://androidcurso.com",
                "Amplia tus conocimientos sobre Android.", 5));

        lugares.add(new Lugar("Barranco del Infierno",
                "Vía Verde del río Serpis. Villalonga (Valencia)",
                -0.295058,
                38.867180,
                TipoLugar.NATURALEZA,
                0,
                "http://sosegaos.blogspot.com.es/2009/02/lorcha-villalonga-viaverde-del-rio.html",
                "Espectacular ruta para bici o andar", 4));

        lugares.add(new Lugar("La Vital",
                "Avda. de la Vital, 0 46701 Gandía (Valencia)",
                -0.1720092,
                38.9705949,
                TipoLugar.COMPRAS,
                962881070,
                "http://www.lavital.es/",
                "El típico centro comercial", 2));


      return lugares;
    }

}
