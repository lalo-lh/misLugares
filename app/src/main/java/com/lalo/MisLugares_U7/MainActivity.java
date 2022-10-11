package com.lalo.MisLugares_U7;

import android.Manifest;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import java.util.ArrayList;

import static android.widget.Toast.*;

public class MainActivity extends AppCompatActivity implements LocationListener {

    private RecyclerView recyclerView;
    //public AdaptadorLugares adaptador;
    private RecyclerView.LayoutManager layoutManager;
//localizacion
    private LocationManager manejador;
    private Location mejorLocaliz;
    private static final long Dos_minutos = 2 * 60 * 1000;

    //public static Lugares lugares = new LugaresVector();

    public LugaresBD lugaresBase;
    public AdaptadorLugaresBDRH adaptador;
    ArrayList<Lugar> listaLugares;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //lugares = new LugaresBD(this);
        lugaresBase=new LugaresBD(this);
        listaLugares = new ArrayList<>();

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        //adaptador = new AdaptadorLugares(this, lugares);
        consultaLugares();
        adaptador = new AdaptadorLugaresBDRH(listaLugares);

        /* ****************************************************************************
        Lugares.indicalizaBD(this);
        adaptador = new SimpleCursorAdapter(this, R.layout.elemento_lista, Lugares.listado(),new String[] {"nombre", "direccion"},
        new int[] {R.id.nombre, R.id.direccion}, 0);
        **************************************************************************** */
        recyclerView.setAdapter(adaptador);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }

        manejador = (LocationManager) getSystemService(LOCATION_SERVICE);
        if(manejador.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            actualizaMejorLocaliz(manejador.getLastKnownLocation(LocationManager.GPS_PROVIDER));
        }
        if(manejador.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            actualizaMejorLocaliz(manejador.getLastKnownLocation(LocationManager.NETWORK_PROVIDER));
        }
    }

    private void consultaLugares() {
        SQLiteDatabase db=lugaresBase.getReadableDatabase();

        Lugar lugar=null;
        // listaUsuarios=new ArrayList<Usuario>();
        //select * from usuarios
        Cursor cursor=db.rawQuery("SELECT * FROM lugares",null);

        while (cursor.moveToNext()){
            lugar=new Lugar();
            lugar.setNombre(cursor.getString(1));
            lugar.setDireccion(cursor.getString(2));
            lugar.setTipo(TipoLugar.values()[cursor.getInt(5)]);
            lugar.setFoto(cursor.getString(6));
            lugar.setValoracion(cursor.getFloat(11));


            listaLugares.add(lugar);
        }
    }

    @Override
    protected void onResume(){
        super.onResume();
        activarProveedores();
    }

    private void activarProveedores(){
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
        if(manejador.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            manejador.requestLocationUpdates(LocationManager.GPS_PROVIDER,20*1000,5,this);
        }
        if(manejador.isProviderEnabled(LocationManager.NETWORK_PROVIDER)){
            manejador.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,20*1000,5,this);
        }
    }

    @Override
    protected void onPause(){
        super.onPause();
        manejador.removeUpdates(this);
    }

    private void cargarDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Acerca de ...");
        builder.setMessage("Este programa ha sido desarrollado como ejemplo en el curso de Andorid para demostrar como se pueden lanzar nuevas actividades desde la actividad principal");

        //mostrar el dialogo
        builder.show();
    }

    public void lanzarVistaLugar(View view){
        final EditText entrada = new EditText(this);
        entrada.setText("");
        new AlertDialog.Builder(this)
                .setTitle("Seleccion de lugar")
                .setMessage("indica su id: ")
                .setView(entrada)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        long id = Long.parseLong(entrada.getText().toString());
                        Intent i = new Intent(MainActivity.this,VistaLugar.class);
                        i.putExtra("id",id);
                        startActivity(i);
                    }
                })
                .setNegativeButton("Cancelar",null)
                .show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if (id == R.id.config){
            return true;
        }
        if (id == R.id.menu_buscar){
            lanzarVistaLugar(null);
            return true;
        }
        if (id == R.id.acercaDe){
            cargarDialog();
        }
        if(id == R.id.menu_mapa){
            Intent intent = new Intent(this, MapsActivity.class);
            startActivity(intent);
        }
        if(id == R.id.accion_nueva){
            Lugar lugar = new Lugar();
            ContentValues values = new ContentValues();
            values.put("longitud", -0.166093);
            values.put("latitud", 38.995656);
            //values.put("tipo", lugar.getTipo().ordinal());
            //values.put("fecha", lugar.getFecha());

            long id_lugar = LugaresBD.nuevo(values);

            Intent i = new Intent(this, EdicionLugar.class);
            i.putExtra("id", id_lugar);
            Toast.makeText(this,Long.toString(id_lugar) , LENGTH_SHORT).show();
            startActivity(i);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onLocationChanged(Location location) {
        actualizaMejorLocaliz(location);
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    private void actualizaMejorLocaliz(Location localiz){
        if(localiz != null && (mejorLocaliz == null || localiz.getAccuracy() < 2 * mejorLocaliz.getAccuracy() || localiz.getTime() - mejorLocaliz.getTime() > Dos_minutos)) {
            mejorLocaliz = localiz;
            Lugares.posicionActual.setLatitud(localiz.getLatitude());
            Lugares.posicionActual.setLongitud(localiz.getLongitude());
        }
    }


}
