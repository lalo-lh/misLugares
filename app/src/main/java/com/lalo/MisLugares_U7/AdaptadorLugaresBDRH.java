package com.lalo.MisLugares_U7;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;

public class AdaptadorLugaresBDRH extends RecyclerView.Adapter<AdaptadorLugaresBDRH.ViewHolder>{

    private LayoutInflater inflador;
    private Context contexto;
    //protected com.lalo.MisLugares_U7.LugaresBD lugares;
    protected Cursor cursor;
    ArrayList<Lugar> listaLugar;

    TextView nombre, direccion, distancia;
    ImageView foto;
    RatingBar valoracion;

    public AdaptadorLugaresBDRH(ArrayList<Lugar> listaLugar){
        this.listaLugar = listaLugar;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView nombre, direccion, distancia;
        public ImageView foto;
        public RatingBar valoracion;

        public ViewHolder(View itemView) {
            super(itemView);
            nombre = (TextView) itemView.findViewById(R.id.nombre);
            direccion = (TextView) itemView.findViewById(R.id.direccion);
            foto = (ImageView) itemView.findViewById(R.id.foto);
            valoracion = (RatingBar) itemView.findViewById(R.id.valoracion);
            distancia = (TextView) itemView.findViewById(R.id.distancia);
        }
    }

    @Override
    public AdaptadorLugaresBDRH.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflamos la vista desde el xml
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.elemento_lista, null,false);
        return new AdaptadorLugaresBDRH.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(AdaptadorLugaresBDRH.ViewHolder holder, int posicion) {
        /*
        Lugar lugar = LugaresBD.elemento(posicion);
        personalizaVista(holder, lugar);
        */
        //personalizaVista(holder, listaLugar.get(posicion));

        holder.nombre.setText(listaLugar.get(posicion).getNombre());
        holder.direccion.setText(listaLugar.get(posicion).getDireccion());
        int id = R.drawable.otros;
        switch(listaLugar.get(posicion).getTipo()) {
            case RESTAURANTE:id = R.drawable.restaurante; break;
            case BAR:        id = R.drawable.bar;         break;
            case COPAS:      id = R.drawable.copas;       break;
            case ESPECTACULO:id = R.drawable.espectaculos; break;
            case HOTEL:      id = R.drawable.hotel;       break;
            case COMPRAS:    id = R.drawable.compras;     break;
            case EDUCACION:  id = R.drawable.educacion;   break;
            case DEPORTE:    id = R.drawable.deporte;     break;
            case NATURALEZA: id = R.drawable.naturaleza;  break;
            case GASOLINERA: id = R.drawable.gasolinera;  break;
        }
        holder.foto.setImageResource(id);
        //holder.foto.setScaleType(ImageView.ScaleType.FIT_END);
        holder.valoracion.setRating(listaLugar.get(posicion).getValoracion());
        //holder.distancia.setText("metros");

        if(Lugares.posicionActual != null && listaLugar.get(posicion).getPosicion() != null){
            int d = (int) Lugares.posicionActual.distancia(listaLugar.get(posicion).getPosicion());
            if(d < 2000){
                holder.distancia.setText(d + " m");
            }else {
                holder.distancia.setText(d / 1000 + "km");
            }
        }

    }

    // Personalizamos un ViewHolder a partir de un lugar
    public void personalizaVista(AdaptadorLugaresBDRH.ViewHolder holder, Lugar lugar) {
        holder.nombre.setText(lugar.getNombre());
        holder.direccion.setText(lugar.getDireccion());
        int id = R.drawable.otros;
        switch(lugar.getTipo()) {
            case RESTAURANTE:id = R.drawable.restaurante; break;
            case BAR:        id = R.drawable.bar;         break;
            case COPAS:      id = R.drawable.copas;       break;
            case ESPECTACULO:id = R.drawable.espectaculos; break;
            case HOTEL:      id = R.drawable.hotel;       break;
            case COMPRAS:    id = R.drawable.compras;     break;
            case EDUCACION:  id = R.drawable.educacion;   break;
            case DEPORTE:    id = R.drawable.deporte;     break;
            case NATURALEZA: id = R.drawable.naturaleza;  break;
            case GASOLINERA: id = R.drawable.gasolinera;  break;
        }
        holder.foto.setImageResource(id);
        //holder.foto.setScaleType(ImageView.ScaleType.FIT_END);
        holder.valoracion.setRating(lugar.getValoracion());

        if(Lugares.posicionActual != null && lugar.getPosicion() != null){
            int d = (int) Lugares.posicionActual.distancia(lugar.getPosicion());
            if(d < 2000){
                holder.distancia.setText(d + " m");
            }else {
                holder.distancia.setText(d / 1000 + "km");
            }
        }

    }

    // Indicamos el número de elementos de la lista
    @Override public int getItemCount() {
        return listaLugar.size();
    }

}
