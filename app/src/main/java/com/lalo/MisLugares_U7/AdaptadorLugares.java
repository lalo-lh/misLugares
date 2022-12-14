package com.lalo.MisLugares_U7;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

public class AdaptadorLugares  extends RecyclerView.Adapter<AdaptadorLugares.ViewHolder> {

    private LayoutInflater inflador;
    private Context contexto;
    protected com.lalo.MisLugares_U7.Lugares lugares;

    TextView nombre, direccion, distancia;
    ImageView foto;
    RatingBar valoracion;

    public AdaptadorLugares(Context contexto, Lugares lugares) {
        this.contexto = contexto;
        this.lugares = lugares;
        inflador = (LayoutInflater) contexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflamos la vista desde el xml
        View v = inflador.inflate(R.layout.elemento_lista, null);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int posicion) {
        Lugar lugar = lugares.elemento(posicion);
        personalizaVista(holder, lugar);
    }

    // Personalizamos un ViewHolder a partir de un lugar
    public void personalizaVista(ViewHolder holder, Lugar lugar) {
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
    // Indicamos el n??mero de elementos de la lista
    @Override public int getItemCount() {
        return lugares.tamayo();
    }
}
