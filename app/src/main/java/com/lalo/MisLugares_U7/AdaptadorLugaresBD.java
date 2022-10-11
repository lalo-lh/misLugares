package com.lalo.MisLugares_U7;

import android.content.Context;
import android.database.Cursor;

public class AdaptadorLugaresBD extends AdaptadorLugares {
    protected Cursor cursor;

    public AdaptadorLugaresBD(Context context, Lugares lugares, Cursor cursor ){
        super(context, lugares);
        this.cursor = cursor;
    }

    public Cursor getCursor() {
        return cursor;
    }

    public void setCursor(Cursor cursor) {
        this.cursor = cursor;
    }

    public Lugar lugarPosicion(int posicion) {
        cursor.moveToPosition(posicion);
        return LugaresBD.extraeLugar(cursor);

    }
    public int idPosicion(int posicion){
        cursor.moveToPosition(posicion);
        return cursor.getInt(0);
    }

    public int posicionId(int id){
        int pos = 0;
        while (pos < getItemCount() && idPosicion(pos)!= id) pos ++;
        if(pos >= getItemCount()) return -1;
        else return pos;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int posicion){
        Lugar lugar = lugarPosicion(posicion);
        personalizaVista(holder, lugar);
        holder.itemView.setTag(new Integer(posicion));
    }

    @Override
    public int getItemCount(){
        return cursor.getCount();
    }

}
