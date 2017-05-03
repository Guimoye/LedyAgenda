package com.example.guimoye.ledyagenda.Agenda;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Guimoye on 27/10/2016.
 */

public class JsonConsultaUsu {


    ListView lista;
    ArrayList<String> id;
    ArrayList<String> fecha;
    ArrayList<String> nombre_evento;
    ArrayList<Integer> status;
    ArrayList<String> descripcion;
    ArrayList<String> imagenes;
    private JSONArray urls;

    ListViewAdapterUsu adapter;
    Context myContext;
    View v;

    String[] getId;
    String[] getFecha;
    String[] getNombreEvento;
    int[] getStatus;
    String[] getDescripcion;
    String[] getImagenes;

    JSONArray alumnosJSON=null;
   // JsonUsersDelete ju;
    LayoutInflater inflaterr;
    ViewGroup containerr;

    public String getIds(int i){ return getId[i];}
    public String getFechas(int i){ return getFecha[i];}
    public String getNombreEventos(int i){ return getNombreEvento[i];}
    public int getStatutos(int i){ return getStatus[i];}
    public String getDescripcions(int i){ return getDescripcion[i];}
    public String getImagenes(int i){ return getImagenes[i];}
    public ListView getLista (){
        return lista;
    }

    private void reset(){
        getId           = new String[alumnosJSON.length()];
        getFecha        = new String[alumnosJSON.length()];
        getNombreEvento = new String[alumnosJSON.length()];
        getStatus       = new int[alumnosJSON.length()];
        getDescripcion  = new String[alumnosJSON.length()];
        getImagenes     = new String[alumnosJSON.length()];
        id.clear();
        fecha.clear();
        nombre_evento.clear();
        status.clear();
        descripcion.clear();
        imagenes.clear();
    }



    public JsonConsultaUsu(View v, LayoutInflater inflater, ViewGroup container,
                           final Context myContext, ListView lista){
        this.myContext          =   myContext;
        this.lista              =   lista;
        this.inflaterr          =   inflater;
        this.containerr         =   container;
        this.v                  =   v;

        this.id                 =   new ArrayList<>();
        this.fecha              =   new ArrayList<>();
        this.nombre_evento      =   new ArrayList<>();
        this.status             =   new ArrayList<>();
        this.descripcion        =   new ArrayList<>();
        this.imagenes           =   new ArrayList<>();


    }


    public void Hilo(String direccion){

            RequestQueue queue;
            queue = Volley.newRequestQueue(myContext);
        Log.e("linkk ","linkkk "+direccion);


            StringRequest stringRequest = new StringRequest(Request.Method.GET, direccion,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            JSONObject jsonObject = null;
                            try {
                                jsonObject  = new JSONObject(response);
                                urls        = jsonObject.getJSONArray("Agenda");

                                getId           =   new String[urls.length()];
                                getFecha        =   new String[urls.length()];
                                getNombreEvento =   new String[urls.length()];
                                getStatus       =   new int[urls.length()];
                                getDescripcion  =   new String[urls.length()];
                                getImagenes     =   new String[urls.length()];

                                for(int i=0;i<urls.length();i++){

                                    getId[i]            =   urls.getJSONObject(i).getString("id");
                                    getFecha[i]         =   urls.getJSONObject(i).getString("fecha");
                                    getNombreEvento[i]  =   urls.getJSONObject(i).getString("nombre_evento");
                                    getStatus[i]        =   urls.getJSONObject(i).getInt("status");
                                    getDescripcion[i]   =   urls.getJSONObject(i).getString("descripcion");
                                    getImagenes[i]      =   urls.getJSONObject(i).getString("foto");


                                    Log.e("ajaa","ejeee: "+ getId[i]);
                                    Log.e("ajaa","ejeee: "+ getFecha[i]);
                                    Log.e("ajaa","ejeee: "+ getNombreEvento[i]);
                                    Log.e("ajaa","ejeee: "+ getStatus[i]);
                                    Log.e("ajaa","ejeee: "+ getDescripcion[i]);
                                    Log.e("ajaa","ejeee: "+getImagenes[i]);

                                    id.add(getId[i]);
                                    fecha.add(getFecha[i]);
                                    nombre_evento.add(getNombreEvento[i]);
                                    status.add(getStatus[i]);
                                    descripcion.add(getDescripcion[i]);

                                    imagenes.add(getImagenes[i]);

                                }
                                Log.e("linkk ","longitud de arregloo: "+id.size()+" imagen: "+getImagenes.length);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            adapter = new ListViewAdapterUsu(myContext, id,fecha,nombre_evento,status,descripcion,imagenes);
                            lista.setAdapter(adapter);


                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("Esto trajoo ","trajo estooooo: "+error);
                } });

            queue.add(stringRequest);


    }



}
