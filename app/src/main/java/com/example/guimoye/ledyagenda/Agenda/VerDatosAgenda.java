package com.example.guimoye.ledyagenda.Agenda;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.guimoye.ledyagenda.R;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;

public class VerDatosAgenda extends ActionBarActivity {

    FloatingActionButton menuClienteAddFavorito;
    FloatingActionsMenu bExpandibleCliente;

    ImageView imageView;
    String getId;
    Button btn_veragenda;
    String[] id, fecha, nombre_evento, foto, descripcion;
    int [] status;
    AutoCompleteTextView aufecha, aunombre, audescripcion;
    Bitmap[] bitmaps;
    private int PICK_IMAGE_REQUEST  =   1;
    private Bitmap bitmap;
    private View focusView          =   null;

    private JSONArray urls;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_agenda);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        savedInstanceState      =   getIntent().getExtras();
        getId                   =   savedInstanceState.getString("id");
        imageView               =   (ImageView)findViewById(R.id.verdatos_imagen);
        btn_veragenda           =   (Button) findViewById(R.id.btn_veragenda);
        aufecha                 =   (AutoCompleteTextView)findViewById(R.id.veragenda_fecha);
        aunombre                =   (AutoCompleteTextView)findViewById(R.id.veragenda_nombre);
        audescripcion           =   (AutoCompleteTextView)findViewById(R.id.veragenda_descripcion);
        bExpandibleCliente      =   (FloatingActionsMenu) findViewById(R.id.bFlotante_veragenda) ;
        menuClienteAddFavorito  =   (FloatingActionButton) findViewById(R.id.Subveragenda_eliminar) ;

        btn_veragenda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage();
            }
        });

        Log.e("llamooo lenarr","obteniendo el id "+getId);

        menuClienteAddFavorito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                RequestQueue queue;
                queue = Volley.newRequestQueue(getApplicationContext());

                StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://192.168.11.115:8282/ledy/agenda.php?modelo=2&id="+getId+"",
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                try {
                                    Toast.makeText(getApplicationContext(),"Eliminado con exito",Toast.LENGTH_LONG).show();
                                    finish();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Esto trajoo ","trajo estooooo: "+error);
                    } });

                queue.add(stringRequest);
            }
        });



        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Seleccionar Imagen"), PICK_IMAGE_REQUEST);
            }
        });

        getUser();
    }


    private void getUser(){

        RequestQueue queue;
        queue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://192.168.11.115:8282/ledy/agenda.php?modelo=4&id="+getId+"",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(response);
                            urls = jsonObject.getJSONArray("Agenda");


                            bitmaps         =   new Bitmap[urls.length()];

                            id              =   new String[urls.length()];
                            fecha           =   new String[urls.length()];
                            nombre_evento   =   new String[urls.length()];
                            status          =   new int[urls.length()];
                            descripcion     =   new String[urls.length()];
                            foto            =   new String[urls.length()];


                            for(int i=0;i<urls.length();i++){
                                id[i]               =   urls.getJSONObject(i).getString("id");
                                fecha[i]            =   urls.getJSONObject(i).getString("fecha");
                                nombre_evento[i]    =   urls.getJSONObject(i).getString("nombre_evento");
                                status[i]           =   urls.getJSONObject(i).getInt("status");
                                descripcion[i]      =   urls.getJSONObject(i).getString("descripcion");
                                foto[i]             =   urls.getJSONObject(i).getString("foto");


                                aufecha.setText(fecha[i]);
                                aunombre.setText(nombre_evento[i]);
                                audescripcion.setText(descripcion[i]);

                                if(status[i]!=0){
                                    Picasso.with(VerDatosAgenda.this).invalidate(foto[i]);
                                    Picasso.with(VerDatosAgenda.this).load(foto[i]).into(imageView);
                                }



                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Esto trajoo ","trajo estooooo: "+error);
            } });

        queue.add(stringRequest);
    }


    public String getStringImage(Bitmap bmp){

        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        try {
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        }catch (Exception e){ }

        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;


    }

    private void uploadImage(){
        //Showing the progress dialog
        limpiarRequired();

                                if(aufecha.getText().length()!=0){
                                    if(aunombre.getText().length()!=0){
                                        if(audescripcion.getText().length()!=0){

                                            final ProgressDialog loading = ProgressDialog.show(this,"Cargando...","Por favor Espere...",false,false);
                                            StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://192.168.11.115:8282/ledy/AgendaUpdate.php",
                                                    new Response.Listener<String>() {
                                                        @Override
                                                        public void onResponse(String s) {
                                                            //Disimissing the progress dialog
                                                            loading.dismiss();
                                                            //Showing toast message of the response
                                                            if(s!="" || s!= null){
                                                                Toast.makeText(VerDatosAgenda.this, s , Toast.LENGTH_LONG).show();
                                                            }

                                                        }
                                                    },
                                                    new Response.ErrorListener() {
                                                        @Override
                                                        public void onErrorResponse(VolleyError volleyError) {
                                                            //Dismissing the progress dialog
                                                            loading.dismiss();

                                                            //Showing toast
                                                            Toast.makeText(VerDatosAgenda.this, "errado "+volleyError.getMessage().toString(), Toast.LENGTH_LONG).show();
                                                        }
                                                    }){
                                                @Override
                                                protected Map<String, String> getParams() throws AuthFailureError {
                                                    //Converting Bitmap to String

                                                    //Creating parameters
                                                    Map<String,String> params = new Hashtable<String, String>();

                                                    //Adding parameters
                                                    params.put("getid", getId);
                                                    params.put("fecha", aufecha.getText().toString());
                                                    params.put("nombre", aunombre.getText().toString());
                                                    params.put("descripcion", audescripcion.getText().toString());
                                                    params.put("foto", getStringImage(bitmap));


                                                    Log.e("direccionando foto ","direccionan foto idd "+getId);


                                                    //returning parameters
                                                    return params;
                                                }
                                            };

                                            //Creating a Request Queue
                                            RequestQueue requestQueue = Volley.newRequestQueue(this);

                                            //Adding request to the queue
                                            requestQueue.add(stringRequest);


                                        }else{
                                            vali(audescripcion);
                                        }
                                    }else{
                                        vali(aunombre);
                                    }
                                }else{
                                    vali(aufecha);
                                }


    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == this.RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();
            try {
                //Getting the Bitmap from Gallery
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), filePath);
                //Setting the Bitmap to ImageView
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private void vali(AutoCompleteTextView t){
        t.setError(getString(R.string.error_field_required));
        focusView=t;
        focusView.requestFocus();
    }

    private void limpiarRequired(){
        aunombre.setError(null);
        aufecha.setError(null);
        audescripcion.setError(null);
    }

}
