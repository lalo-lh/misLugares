package com.lalo.MisLugares_U7;

import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.MainThread;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.Date;

public class EdicionLugar extends AppCompatActivity {
    private EditText nombre;
    private Spinner tipo;
    private EditText direccion;
    private EditText telefono;
    private EditText url;
    private EditText comentario;
    private LugaresBD llugar;

    private long id;
    private Lugar lugar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ediciolugar);

        llugar=new LugaresBD(this);

        Bundle extras = getIntent().getExtras();

        id = extras.getLong("id",-1);
        //lugar = MainActivity.lugares.elemento((int) id);
        //lugar = MainActivity.adaptador.lugarPosicion((int) id);
        //lugar = MainActivity.adaptador.lugarPosicion((int) id);
        lugar = llugar.elemento((int)id);
        tipo = findViewById(R.id.tipo);
        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,TipoLugar.getNombres());
        adaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tipo.setAdapter(adaptador);
        tipo.setSelection(lugar.getTipo().ordinal());

        nombre = findViewById(R.id.nombre);
        nombre.setText(lugar.getNombre());

        direccion = findViewById(R.id.direccion);
        direccion.setText(lugar.getDireccion());

        telefono = findViewById(R.id.telefono);
        telefono.setText(Integer.toString(lugar.getTelefono()));

        url = findViewById(R.id.url);
        url.setText(lugar.getUrl());

        comentario = findViewById(R.id.comentario);
        comentario.setText(lugar.getComentario());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edicion_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.edicion_canc:
                finish();
                return true;
            case R.id.edicion_guardar:
                lugar.setNombre(nombre.getText().toString());
                lugar.setTipo(TipoLugar.values()[tipo.getSelectedItemPosition()]);
                lugar.setDireccion(direccion.getText().toString());
                lugar.setTelefono(Integer.parseInt(telefono.getText().toString()));
                lugar.setUrl(url.getText().toString());
                lugar.setComentario(comentario.getText().toString());
                //VistaLugar.actualizaLugar((int) id,lugar);
                modificarVal((int)id, lugar);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    public void modificarVal(int id, Lugar lugar){
        LugaresBD llugar = new LugaresBD(this);
        SQLiteDatabase db = llugar.getWritableDatabase();
        db.execSQL("UPDATE lugares SET nombre = '" + lugar.getNombre() +
                "', direccion = '" + lugar.getDireccion() +
                "', longitud = " + lugar.getPosicion().getLongitud() +
                " , latitud = " + lugar.getPosicion().getLatitud() +
                " , tipo = " + lugar.getTipo().ordinal() +
                " , foto = '" + lugar.getFoto() +
                "', telefono = " + lugar.getTelefono() +
                " , url = '" + lugar.getUrl() +
                "', comentario = '" + lugar.getComentario() +
                "', valoracion = " + lugar.getValoracion() +
                " WHERE _id = " + Integer.toString(id));

        //cv.put("valoracion", lugar.getValoracion());
        //return db.update("lugares", cv, "_id = ",new String[]{Integer.toString(id)});
        //db.execSQL("UPDATE lugares SET valoracion = " + lugar.getValoracion() + " WHERE _id = " + Integer.toString(id));
        db.close();
    }

}
