package com.lalo.MisLugares_U7;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public interface Lugares {
    Lugar elemento(int id); //devuelve el elemento dado su id
    void anyade(Lugar lugar); //añada el elemento indicado
    //int nuevo(); //añade un elemento en blanco y devuelve su id
    void borrar(int id); //elimina el elemento con el id indicado
    int tamayo(); //devuelve el numero de elementos
    void actualiza(int id,Lugar lugar); // reemplaza un elemento
    GeoPunto posicionActual = new GeoPunto(0,0);
/*
   private static LugaresBD lugaresBD;

   public static void indicalizaBD(Context contexto){
      lugaresBD = new LugaresBD(contexto);
   }

   public static Cursor listado(){
      SQLiteDatabase bd = lugaresBD.getReadableDatabase();
      return bd.rawQuery("SELECT * FROM lugares", null);
   }


   static void actualizaLugar(int id,Lugar lugar){

      SQLiteDatabase bd = LugaresBD.getWritableDatabase();
      bd.execSQL("UPDATE lugares SET nombre = '" + lugar.getNombre() +
              "', direccion = '" +lugar.getDireccion() +
              "', longitud = " + lugar.getPosicion().getLongitud() +
              "', latitud = " + lugar.getPosicion().getLatitud() +
              "', tipo = " + lugar.getTipo() +
              "', foto = " + lugar.getFoto() +
              "', telefono = " + lugar.getTelefono() +
              "', url = " + lugar.getUrl() +
              "', comentario = " + lugar.getComentario() +
              "', fecha = " + lugar.getFecha() +
              "', valoracion = " + lugar.getValoracion() +
              "WHERE _id = " + id);
      bd.close();
   }
*/

}
