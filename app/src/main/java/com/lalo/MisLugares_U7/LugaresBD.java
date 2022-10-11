package com.lalo.MisLugares_U7;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class LugaresBD extends SQLiteOpenHelper implements Lugares{
    public static LugaresBD llugar;

    public LugaresBD(Context context){
        super(context, "lugares", null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase bd){

        bd.execSQL("CREATE TABLE lugares (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nombre TEXT, " +
                "direccion TEXT, " +
                "longitud REAL, " +
                "latitud REAL, " +
                "tipo INTEGER, " +
                "foto TEXT, " +
                "telefono INTEGER, "+
                "url TEXT, " +
                "comentario TEXT, " +
                "fecha LONG, "+
                "valoracion REAL)");
        bd.execSQL("INSERT INTO lugares VALUES (null, "+
                "'Escuela Politecnica Superior de Gandia', "+
                "'C/Paranimf, 1 46730 Gandia (SPAIN)', -0.166093, 38.995656, "+
                TipoLugar.EDUCACION.ordinal() + ", '', 962849300, "+
                "'http://www.epsg.upv.es', "+
                "'Uno de los mejores lugares para formarse.', " +
                System.currentTimeMillis() + ", 3.0)");

        bd.execSQL("INSERT INTO lugares VALUES (null, " +
                "'Al de siempre', "+
                "'P.Industrial Junto Moli Nou - 46722, Benifla (Valencia)', -0.190642, 38.925857, "+
                TipoLugar.BAR.ordinal() + ", '', 636472405, " +
                "'', " +
                "'No te pierdas el arroz en calabaza.', " +
                System.currentTimeMillis() + ", 3.0)");

        bd.execSQL("INSERT INTO lugares VALUES (null, 'androidcurso.com', "+
                "'ciberespacio Calle francisco i madero 232 colonia amado nervo', 0.0, 0.0, " + TipoLugar.BAR.ordinal() + ", '', "+
                "962849300, 'http://androidcurso.com', "+
                "'Amplia tus conocimientos sobre Android.', " +
                System.currentTimeMillis() + ", 5.0)");

        bd.execSQL("INSERT INTO lugares VALUES (null, 'Barranco del Infierno', "+
                "'Via Verde del rio Serpis. Villalonga(Valencia)', -0.295058, "+
                        "38.867180, "+ TipoLugar.NATURALEZA.ordinal() + ", '', 0, "+
                "'http://sosegaos.blogspot.com.es/2009/02/lorcha-villalonga-via-verde-del-"+
                        "rio.html', 'Espectacular ruta para bici o andar', " +
                System.currentTimeMillis() + ", 4.0)");

        bd.execSQL("INSERT INTO lugares VALUES (null, 'La vital', "+
                "'Avda. La vital, 0 46701 Gandia (Valencia)', -0.1720092, 38.9705949, " +
                TipoLugar.COMPRAS.ordinal() + ", '', 962881070, "+
                "'http://www.lavital.es', 'El tipico centro comercial', " +
                System.currentTimeMillis() + ", 2.0)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){

    }

    @Override
    public Lugar elemento(int id) {

        Cursor cursor = getReadableDatabase().rawQuery(
                "SELECT * FROM lugares WHERE _id = " + id, null);
        try {
            if (cursor.moveToNext())
                return extraeLugar(cursor);
            else
                throw new SQLException("Error al acceder al elemento _id = " + id);
        } catch (Exception e) {
            throw e;
        } finally {
            if (cursor!=null) cursor.close();
        }
    }

    @Override
    public void anyade(Lugar lugar) {
    }

    @Override
    public void borrar(int id) {
    }

    @Override
    public int tamayo() {
        return 0;
    }

    @Override
    public void actualiza(int id, Lugar lugar) {
    }

    public static Lugar extraeLugar(Cursor cursor){
        Lugar lugar = new Lugar();
        lugar.setNombre(cursor.getString(1));
        lugar.setDireccion(cursor.getString(2));
        lugar.setPosicion(new GeoPunto(cursor.getDouble(3),
                cursor.getDouble(4)));
        lugar.setTipo(TipoLugar.values()[cursor.getInt(5)]);
        lugar.setFoto(cursor.getString(6));
        lugar.setTelefono(cursor.getInt(7));
        lugar.setUrl(cursor.getString(8));
        lugar.setComentario(cursor.getString(9));
        lugar.setFecha(cursor.getLong(10));
        lugar.setValoracion(cursor.getFloat(11));
        return lugar;
    }

/*
    public Cursor extraeCursor(){
        String consulta = "SELECT * FROM lugares";
        SQLiteDatabase bd = getReadableDatabase();
        return bd.rawQuery(consulta, null);
    }
*/
//@Override
public static int nuevo(ContentValues values) {
    int id = -1;
    Lugar lugar = new Lugar();
    SQLiteDatabase bd = llugar.getWritableDatabase();
    /*
    bd.execSQL("INSERT INTO lugares (longitud, latitud, tipo, fecha) "+
            "VALUES ( " + lugar.getPosicion().getLongitud() +"," +
            lugar.getPosicion().getLatitud()+") "+
            lugar.getTipo().ordinal()+ ", " + lugar.getFecha()+")");

    */
    long newRow = bd.insert("lugares","nombre", values);
    Cursor c = bd.rawQuery("SELECT _id FROM lugares WHERE fecha = " +
            lugar.getFecha(),null);

    if (c.moveToNext()){
        id = c.getInt(0);
    }
    c.close();
    bd.close();

    return id;

}

//Modificar valores
/*
    public static void modificarVal(int id, Lugar lugar){
        //LugaresBD llugar = new LugaresBD();
        SQLiteDatabase db = LugaresBD.getWritableDatabase();

        db.execSQL("UPDATE lugares SET nombre = '" + lugar.getNombre() +
                "', direccion = '" + lugar.getDireccion() +
                "', longitud = " + lugar.getPosicion().getLongitud() +
                " , latitud = " + lugar.getPosicion().getLatitud() +
                " , tipo = " + lugar.getTipo().ordinal() +
                " , foto = '" + lugar.getFoto() +
                "', telefono = " + lugar.getTelefono() +
                " , url = '" + lugar.getUrl() +
                "', comentario = '" + lugar.getComentario() +
                "' , valoracion = " + lugar.getValoracion() +
                " WHERE _id = " + Integer.toString(id));
        db.close();
        //Toast.makeText(,"subio",Toast.LENGTH_SHORT).show();

    }
*/
}
