package com.lalo.MisLugares_U7;

import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class VistaLugar extends AppCompatActivity {
    private long id;
    private  Lugar lugar;
    private ImageView imageView;
    final static int RESULTADO_EDITAR = 1;
    final static int RESULTADO_GALERIA = 2;
    final static int RESULTADO_FOTO = 3;
    public static LugaresBD llugar;
    private Uri uriFoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vista_lugar);

        llugar=new LugaresBD(this);
        Bundle extras = getIntent().getExtras();
        id = extras.getLong("id",-1);
        //lugar = llugar.elemento((int)id);
        imageView = findViewById(R.id.foto);

        actualizarVistas();

    }

    public void actualizarVistas(){

        lugar = llugar.elemento((int)id);

        TextView nombre = findViewById(R.id.nombre);
        nombre.setText(lugar.getNombre());

        ImageView logo_tipo = findViewById(R.id.logo_tipo);
        logo_tipo.setImageResource(lugar.getTipo().getRecurso());

        TextView tipo = findViewById(R.id.tipo);
        tipo.setText(lugar.getTipo().getTexto());

        if(lugar.getDireccion().isEmpty()){
            findViewById(R.id.direccion).setVisibility(View.GONE);
            findViewById(R.id.logo_direccion).setVisibility(View.GONE);
        } else{
            TextView direccion = findViewById(R.id.direccion);
            direccion.setText(lugar.getDireccion());
        }

        if(lugar.getTelefono() == 0){
            findViewById(R.id.telefono).setVisibility(View.GONE);
            findViewById(R.id.logo_relefono).setVisibility(View.GONE);
        } else {
            findViewById(R.id.telefono).setVisibility(View.VISIBLE);
            TextView telefono = findViewById(R.id.telefono);
            telefono.setText(Integer.toString(lugar.getTelefono()));
        }

        if(lugar.getUrl().isEmpty()){
            findViewById(R.id.url).setVisibility(View.GONE);
            findViewById(R.id.logo_url).setVisibility(View.GONE);
        } else {
            TextView url = findViewById(R.id.url);
            url.setText(lugar.getUrl());
        }

        if(lugar.getComentario().isEmpty()){
            findViewById(R.id.comentario).setVisibility(View.GONE);
            findViewById(R.id.logo_comentario).setVisibility(View.GONE);
        } else {
            TextView comentario = findViewById(R.id.comentario);
            comentario.setText(lugar.getComentario());
        }

        TextView fecha = findViewById(R.id.fecha);
        fecha.setText(DateFormat.getDateInstance().format(new Date(lugar.getFecha())));

        TextView hora = findViewById(R.id.hora);
        hora.setText(DateFormat.getTimeInstance().format(new Date(lugar.getFecha())));

        RatingBar valoracion = findViewById(R.id.valoracion);
        valoracion.setRating(lugar.getValoracion());
        valoracion.setOnRatingBarChangeListener(
                new RatingBar.OnRatingBarChangeListener() {
                    @Override
                    public void onRatingChanged(RatingBar ratingBar, float valor, boolean fromUser) {
                        lugar.setValoracion(valor);
                        //actualizaLugar((int) id,lugar);
                        modificarVal((int) id,lugar);
                    }
                }
        );

        ponerFoto(imageView, lugar.getFoto());
    }
// update SQlite valoracion
    public static void modificarVal(int id, Lugar lugar){
        SQLiteDatabase db = llugar.getWritableDatabase();
        //ContentValues cv = new ContentValues();
        //cv.put("valoracion", lugar.getValoracion());
        //return db.update("lugares", cv, "_id = ",new String[]{Integer.toString(id)});
        db.execSQL("UPDATE lugares SET valoracion = " + lugar.getValoracion() + " WHERE _id = " + Integer.toString(id));
        db.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.vista_lugar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.accion_compartir:
                return true;
            case R.id.accion_llegar:
                return true;
            case R.id.accion_editar:
                Intent i = new Intent(this,EdicionLugar.class);
                i.putExtra("id",id);
                startActivityForResult(i,RESULTADO_EDITAR);
                return true;
            case R.id.accion_borrar:
                borrarLugar(null);
                return true;
                default:
                    return super.onOptionsItemSelected(item);
        }
    }

    public void borrarLugar(View view){
        new AlertDialog.Builder(this)
                .setTitle("Borrado de lugar")
                .setMessage("¿Estas seguro que quieres eliminar este lugar?")
                .setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //MainActivity.lugares.borrar((int) id);
                    }
                })
                .setNegativeButton("Cancelar", null)
                .show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == RESULTADO_EDITAR){
            actualizarVistas();
            findViewById(R.id.scrollView3).invalidate();
        } else if(requestCode == RESULTADO_GALERIA && resultCode == Activity.RESULT_OK){
            lugar.setFoto(data.getDataString());
            actualizaLugar((int) id,lugar);
            ponerFoto(imageView, lugar.getFoto());
        } else if(requestCode == RESULTADO_FOTO && resultCode == Activity.RESULT_OK){
            lugar.setFoto(uriFoto.toString());
            actualizaLugar((int) id,lugar);
            ponerFoto(imageView, lugar.getFoto());
        }
    }

    protected void ponerFoto(ImageView imageView, String foto) {
        if(foto != null){
            imageView.setImageURI(Uri.parse(foto));
        } else {
            imageView.setImageBitmap(null);
        }
    }

    public void galeria (View view){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        startActivityForResult(intent, RESULTADO_GALERIA);
    }

    public void tomarFoto(){
        ContentValues values = new ContentValues();
        String str = "img_" + (System.currentTimeMillis()/1000);
        values.put(MediaStore.Images.Media.TITLE, str);
        values.put(MediaStore.Images.Media.DESCRIPTION, "Take with phone");
        uriFoto = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //uriFoto = new File(Environment.getExternalStorageDirectory() + "img_" + (System.currentTimeMillis()/1000) + ".jpg");
        intent.putExtra(MediaStore.EXTRA_OUTPUT,uriFoto);
        startActivityForResult(intent, RESULTADO_FOTO);

    }

    public void fotoClick(View view){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED ||
                    checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){
                String[] permission = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
                requestPermissions(permission, RESULTADO_FOTO);
            } else {
                //permission already granted
                tomarFoto();
            }
        } else {
            //System os < marshmallow
            tomarFoto();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case RESULTADO_FOTO : {
                // Si la petición es cancelada, el array resultante estará vacío.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // El permiso ha sido concedido.
                    tomarFoto ();
                break;
                } else {
                    // Permiso denegado, deshabilita la funcionalidad que depende de este permiso.
                    Toast.makeText(this, "No se aceptó permisos", Toast.LENGTH_SHORT).show();
                }

            }
            // otros bloques de 'case' para controlar otros permisos de la aplicación
        }
    }

    public void eliminarFoto(View view){
        lugar.setFoto(null);
        actualizaLugar((int) id,lugar);
        ponerFoto(imageView, null);
    }

    public static void actualizaLugar(int id, Lugar lugar){
        SQLiteDatabase bd = llugar.getWritableDatabase();
        bd.execSQL("UPDATE lugares SET nombre = '" + lugar.getNombre() +
                "', direccion = '" +lugar.getDireccion() +
                "', longitud = " + lugar.getPosicion().getLongitud() +
                ", latitud = " + lugar.getPosicion().getLatitud() +
                ", tipo = " + lugar.getTipo() +
                ", foto = '" + lugar.getFoto() +
                "', telefono = " + lugar.getTelefono() +
                ", url = '" + lugar.getUrl() +
                "', comentario = '" + lugar.getComentario() +
                "', fecha = " + lugar.getFecha() +
                ", valoracion = " + lugar.getValoracion() +
                " WHERE _id = " + id);
        bd.close();
    }

}
