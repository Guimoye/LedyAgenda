package com.example.guimoye.ledyagenda.Perfil;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.guimoye.ledyagenda.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PerfilFragment extends Fragment {


    String getCorreo;
    final String JSON_ARRAY="result";
    private JSONArray urls;
    LayoutInflater inflater;
    ViewGroup container;
    View v;
    // Rutas de los Web Services
    String direccion="http://192.168.11.115:8282/ledy/usuarioGet.php?modelo=";
    String GET2 = direccion+"6&correo=";
    AlertDialog.Builder builder;
    AlertDialog ad;
    AutoCompleteTextView umTipo,umClave,umCorreo,umPregunta,umRespuesta,umNombre;

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        this.inflater   =   inflater;
        this.container  =   container;
        v               =   inflater.inflate(R.layout.fragment_perfil,container,false);

        umCorreo     = (AutoCompleteTextView) v.findViewById(R.id.txtCorreoModificar);
        umClave      = (AutoCompleteTextView) v.findViewById(R.id.txtClaveModificar);
        umTipo       = (AutoCompleteTextView) v.findViewById(R.id.txtTipoModificar);
        umNombre     = (AutoCompleteTextView) v.findViewById(R.id.txtNombreModificar);
        umPregunta   = (AutoCompleteTextView) v.findViewById(R.id.txtPreguntaModificar);
        umRespuesta  = (AutoCompleteTextView) v.findViewById(R.id.txtRespuestaModificar);

        Bundle bundle   =   this.getArguments();
        getCorreo       =   bundle.getString("correo");
        Log.e("obtenidoo","correo obtenido perfil: "+getCorreo);

        umTipo.setEnabled(false);
       // umCorreo.setEnabled(false);
        getUser();


        final Button btnMoficiarUsuario   = (Button) v.findViewById(R.id.btn_modificar_usuario);
        btnMoficiarUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                validacions();

            }

        });


        return v;
    }




    private void getUser(){

        RequestQueue queue;
        queue = Volley.newRequestQueue(getActivity());

        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                "http://192.168.11.115:8282/ledy/usuarioGet.php?modelo=6&correo="+getCorreo+"",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("perfiiil","da estoo para perfil ");
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(response);
                            Log.e("queda en cero ","da estoo para perfil "+jsonObject);
                            urls = jsonObject.getJSONArray("result");

                            Log.e("queda en cero2 ","da estoo para perfil "+urls.getJSONObject(0).getString("clave"));

                            umCorreo.setText(urls.getJSONObject(0).getString("correo"));
                            umClave.setText(urls.getJSONObject(0).getString("clave"));
                            umTipo.setText(urls.getJSONObject(0).getString("tipo"));
                            umNombre.setText(urls.getJSONObject(0).getString("nombre"));
                            umPregunta.setText(urls.getJSONObject(0).getString("pregunta"));
                            umRespuesta.setText(urls.getJSONObject(0).getString("respuesta"));

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


    private void limpiarRequired(){
        umTipo.setError(null);
        umClave.setError(null);
        umCorreo.setError(null);
        umNombre.setError(null);
        umPregunta.setError(null);
        umRespuesta.setError(null);
    }

    private void validacions(){
        limpiarRequired();
        if(umCorreo.getText().toString().contains("@") && umCorreo.getText().toString().contains(".com") ){
            if(umClave.getText().length()!=0){
                if(umTipo.getText().length()!=0){
                    if(umNombre.getText().length()!=0){
                        if(umPregunta.getText().length()!=0){
                            if(umRespuesta.getText().length()!=0){



                                RequestQueue queue;
                                queue = Volley.newRequestQueue(getActivity());

                                StringRequest stringRequest = new StringRequest(Request.Method.GET,
                                        "http://192.168.11.115:8282/ledy/usuarioGet.php?modelo=4" +
                                                "&correo="+umCorreo.getText()+"" +
                                                "&clave="+umClave.getText()+"" +
                                                "&tipo="+umTipo.getText()+"" +
                                                "&nombre="+umNombre.getText()+"" +
                                                "&pregunta="+umPregunta.getText()+"" +
                                                "&respuesta="+umRespuesta.getText()+"",
                                        new Response.Listener<String>() {
                                            @Override
                                            public void onResponse(String response) {
                                                Toast.makeText(getActivity(),"Perfil modificado con exito",Toast.LENGTH_LONG).show();
                                                getUser();
                                            }
                                        }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Log.e("Esto trajoo ","trajo estooooo: "+error);
                                    } });

                                queue.add(stringRequest);

                                limpiar();


                            }else{
                                vali(umRespuesta);
                            }
                        }else{
                            vali(umPregunta);
                        }
                    }else{
                        vali(umNombre);
                    }

            }else{
                vali(umTipo);
            }


            }else{
                vali(umClave);
            }
        }else{
            umCorreo.setError(getString(R.string.error_invalid_email));
            v=umCorreo;
            v.requestFocus();
        }

    }

    private void vali(AutoCompleteTextView t){
        t.setError(getString(R.string.error_field_required));
        v=t;
        v.requestFocus();
    }
    private void limpiar(){
        umTipo.setText(null);
        umClave.setText(null);
        umCorreo.setText(null);
        umNombre.setText(null);
        umPregunta.setText(null);
        umRespuesta.setText(null);
    }

}
