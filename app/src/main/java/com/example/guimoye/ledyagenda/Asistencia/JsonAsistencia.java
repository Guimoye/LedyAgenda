package com.example.guimoye.ledyagenda.Asistencia;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;


public class JsonAsistencia {


    ListView lista;
    ArrayList<String> cedula,nombre, descripcion, fecha;

    String direccion="http://192.168.11.115:8282/ledy/asistencia.php?modelo=";
    String resultado;
    ListViewAdapterAsistencia adapter;
    Context myContext;
    View v;

    String GET = "";
    String nro="";
    String nroSelect="";

    String[] Scedula,Snombre, Sdescripcion, Sfecha;
    JSONArray alumnosJSON=null;
    LayoutInflater inflaterr;
    ViewGroup containerr;
    String fecha1,fecha2;

    public String getCedula(int i) { return Scedula[i]; }
    public String getNombre(int i){ return Snombre[i];}
    public String getDescripcion(int i) { return Sdescripcion[i];}
    public String getFecha(int i) { return Sfecha[i]; }
    public ListView getLista (){
        return lista;
    }

    private void reset(){
        Scedula         = new String[alumnosJSON.length()];
        Snombre         = new String[alumnosJSON.length()];
        Sdescripcion    = new String[alumnosJSON.length()];
        Sfecha          = new String[alumnosJSON.length()];

        cedula.clear();
        nombre.clear();
        descripcion.clear();
        fecha.clear();
    }

    public JsonAsistencia(View v, LayoutInflater inflater, ViewGroup container, Context myContext,
                          ListView lista, ArrayList<String> cedula,String GET, String nroSelect){
        this.myContext      =   myContext;
        this.lista          =   lista;
        this.cedula         =   cedula;
        this.GET            =   GET;
        this.nroSelect      =   nroSelect;
        this.inflaterr      =   inflater;
        this.containerr     =   container;
        this.v              =   v;

        this.cedula         =   new ArrayList<>();
        this.nombre         =   new ArrayList<>();
        this.descripcion    =   new ArrayList<>();
        this.fecha          =   new ArrayList<>();
       // Hilo(GET,nroSelect);
    }

    /*
    public JsonAsistencia(Context myContext, String GET, String nroSelect){
        this.myContext      =   myContext;
        this.GET            =   GET;
        this.nroSelect      =   nroSelect;

        this.cedula         =   new ArrayList<>();
        this.nombre         =   new ArrayList<>();
        this.descripcion    =   new ArrayList<>();
        this.fecha          =   new ArrayList<>();
        Hilo(GET,nroSelect);
    }
    */

    public void cambiarFechas(String fecha1,String fecha2){
        this.fecha1=fecha1;
        this.fecha2=fecha2;
    }

    public void Hilo(String GET, final String nro){
        this.GET    =   GET;
        this.nro    =   nro;

       class ObtenerWebService extends AsyncTask<String,Void,String> {

            @Override
            protected String doInBackground(String... params) {

                String cadena = params[0];
                URL url = null; // Url de donde queremos obtener información
                String devuelve ="";



                if(params[1]=="1"){    // Consulta de todos los usuarios
                    try {
                        url = new URL(cadena+"&fecha1="+fecha1+"&fecha2="+fecha2+"");
                        HttpURLConnection connection = (HttpURLConnection) url.openConnection(); //Abrir la conexión
                        connection.setRequestProperty("User-Agent", "Mozilla/5.0" +
                                " (Linux; Android 1.5; es-ES) Ejemplo HTTP");
                        //connection.setHeader("content-type", "application/json");

                        int respuesta = connection.getResponseCode();
                        StringBuilder result = new StringBuilder();

                        if (respuesta == HttpURLConnection.HTTP_OK){

                            InputStream in = new BufferedInputStream(connection.getInputStream());  // preparo la cadena de entrada

                            BufferedReader reader = new BufferedReader(new InputStreamReader(in));  // la introduzco en un BufferedReader

                            // El siguiente proceso lo hago porque el JSONOBject necesita un String y tengo
                            // que tranformar el BufferedReader a String. Esto lo hago a traves de un
                            // StringBuilder.

                            String line;
                            while ((line = reader.readLine()) != null) {
                                result.append(line);        // Paso toda la entrada al StringBuilder
                            }

                            //Creamos un objeto JSONObject para poder acceder a los atributos (campos) del objeto.
                            JSONObject respuestaJSON = new JSONObject(result.toString());   //Creo un JSONObject a partir del StringBuilder pasado a cadena
                            //Accedemos al vector de resultados

                            Log.e("epaaaa ","sapooo respuestaaa line "+result.length()+"");
                            Log.e("epaaaa ","sapooo respuestaaa lineee "+respuestaJSON+"");
                                alumnosJSON = respuestaJSON.getJSONArray("Asistencia");   // estado es el nombre del campo en el JSON

                                reset();
                                for(int i=0;i<result.length();i++){
                                    devuelve = devuelve +
                                            alumnosJSON.getJSONObject(i).getString("cedula") + " " +
                                            alumnosJSON.getJSONObject(i).getString("nombre") + " " +
                                            alumnosJSON.getJSONObject(i).getString("descripcion") + " " +
                                            alumnosJSON.getJSONObject(i).getString("fecha") + "\n";

                                    cedula.add(alumnosJSON.getJSONObject(i).getString("cedula"));
                                    nombre.add(alumnosJSON.getJSONObject(i).getString("nombre"));
                                    descripcion.add(alumnosJSON.getJSONObject(i).getString("descripcion"));
                                    fecha.add(alumnosJSON.getJSONObject(i).getString("fecha"));

                                    Scedula[i]      =   alumnosJSON.getJSONObject(i).getString("cedula");
                                    Snombre[i]      =   alumnosJSON.getJSONObject(i).getString("nombre");
                                    Sdescripcion[i] =   alumnosJSON.getJSONObject(i).getString("descripcion");
                                    Sfecha[i]       =   alumnosJSON.getJSONObject(i).getString("fecha");
                                    Log.e("paso ","veces ");
                                }


                        }


                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    return devuelve;


                }
                else if(params[1]=="2"){    // eliminar alumnos

                    try {
                        url = new URL(cadena);
                        HttpURLConnection connection = (HttpURLConnection) url.openConnection(); //Abrir la conexión
                        connection.setRequestProperty("User-Agent", "Mozilla/5.0" +
                                " (Linux; Android 1.5; es-ES) Ejemplo HTTP");
                        //connection.setHeader("content-type", "application/json");

                        int respuesta = connection.getResponseCode();

                        if (respuesta == HttpURLConnection.HTTP_OK){
                            devuelve = "eliminado con exito";
                            Log.e("errorrr","errrorrr: "+devuelve);

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    return devuelve;


                }
                else if(params[1]=="3"){    // update

                    try {
                        url = new URL(cadena);
                        HttpURLConnection connection = (HttpURLConnection) url.openConnection(); //Abrir la conexión
                        connection.setRequestProperty("User-Agent", "Mozilla/5.0" +
                                " (Linux; Android 1.5; es-ES) Ejemplo HTTP");
                        //connection.setHeader("content-type", "application/json");

                        int respuesta = connection.getResponseCode();
                        StringBuilder result = new StringBuilder();
                        if (respuesta == HttpURLConnection.HTTP_OK){

                            InputStream in = new BufferedInputStream(connection.getInputStream());  // preparo la cadena de entrada
                            BufferedReader reader = new BufferedReader(new InputStreamReader(in));  // la introduzco en un BufferedReader

                            // El siguiente proceso lo hago porque el JSONOBject necesita un String y tengo
                            // que tranformar el BufferedReader a String. Esto lo hago a traves de un
                            // StringBuilder.

                            String line;
                            while ((line = reader.readLine()) != null) {
                                result.append(line);        // Paso toda la entrada al StringBuilder
                            }

                            //Creamos un objeto JSONObject para poder acceder a los atributos (campos) del objeto.
                            JSONObject respuestaJSON = new JSONObject(result.toString());   //Creo un JSONObject a partir del StringBuilder pasado a cadena
                            //Accedemos al vector de resultados

                            String resultJSON = respuestaJSON.getString("Estado");   // estado es el nombre del campo en el JSON
                            Log.e("sapooo, ","siiiii: "+resultJSON);
                            if (resultJSON.equals("1")){      // hay un alumno que mostrar
                                devuelve = "asistencia modificadada satisfactoriamente";
                            }else if (resultJSON.equals("0")){
                                devuelve = "disculpe el usuario introducido ya existe";
                            }

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    return devuelve;
                }
                else if(params[1]=="4"){    // registrar
                    try {
                        url = new URL(cadena);
                        HttpURLConnection connection = (HttpURLConnection) url.openConnection(); //Abrir la conexión
                        connection.setRequestProperty("User-Agent", "Mozilla/5.0" +
                                " (Linux; Android 1.5; es-ES) Ejemplo HTTP");
                        //connection.setHeader("content-type", "application/json");

                        int respuesta = connection.getResponseCode();
                        StringBuilder result = new StringBuilder();
                        if (respuesta == HttpURLConnection.HTTP_OK){

                            InputStream in = new BufferedInputStream(connection.getInputStream());  // preparo la cadena de entrada
                            BufferedReader reader = new BufferedReader(new InputStreamReader(in));  // la introduzco en un BufferedReader

                            // El siguiente proceso lo hago porque el JSONOBject necesita un String y tengo
                            // que tranformar el BufferedReader a String. Esto lo hago a traves de un
                            // StringBuilder.

                            String line;
                            while ((line = reader.readLine()) != null) {
                                result.append(line);        // Paso toda la entrada al StringBuilder
                            }

                            //Creamos un objeto JSONObject para poder acceder a los atributos (campos) del objeto.
                            JSONObject respuestaJSON = new JSONObject(result.toString());   //Creo un JSONObject a partir del StringBuilder pasado a cadena
                            //Accedemos al vector de resultados

                            String resultJSON = respuestaJSON.getString("Estado");   // estado es el nombre del campo en el JSON
                            Log.e("sapooo, ","siiiii: "+resultJSON);
                            if (resultJSON.equals("1")){      // hay un alumno que mostrar
                                devuelve = "asistencia registrada satisfactoriamente";
                            }else if (resultJSON.equals("0")){
                                devuelve = "disculpe el usuario introducido ya existe";
                            }

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return devuelve;
                }
                else if(params[1]=="5") {    // register login
                   try {
                        url = new URL(cadena);
                        HttpURLConnection connection = (HttpURLConnection) url.openConnection(); //Abrir la conexión
                        connection.setRequestProperty("User-Agent", "Mozilla/5.0" +
                                " (Linux; Android 1.5; es-ES) Ejemplo HTTP");
                        //connection.setHeader("content-type", "application/json");

                        int respuesta = connection.getResponseCode();
                       StringBuilder result = new StringBuilder();
                       if (respuesta == HttpURLConnection.HTTP_OK){

                           InputStream in = new BufferedInputStream(connection.getInputStream());  // preparo la cadena de entrada
                           BufferedReader reader = new BufferedReader(new InputStreamReader(in));  // la introduzco en un BufferedReader

                           // El siguiente proceso lo hago porque el JSONOBject necesita un String y tengo
                           // que tranformar el BufferedReader a String. Esto lo hago a traves de un
                           // StringBuilder.

                           String line;
                           while ((line = reader.readLine()) != null) {
                               result.append(line);        // Paso toda la entrada al StringBuilder
                           }

                           //Creamos un objeto JSONObject para poder acceder a los atributos (campos) del objeto.
                           JSONObject respuestaJSON = new JSONObject(result.toString());   //Creo un JSONObject a partir del StringBuilder pasado a cadena
                           //Accedemos al vector de resultados

                           String resultJSON = respuestaJSON.getString("Estado");   // estado es el nombre del campo en el JSON
                            Log.e("sapooo, ","siiiii: "+resultJSON);
                           if (resultJSON.equals("1")){      // hay un alumno que mostrar
                               devuelve = "asistencia registrada satisfactoriamente";
                           }else if (resultJSON.equals("0")){
                               devuelve = "disculpe el usuario introducido ya existe";
                           }

                       }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                   return devuelve;
                }
                return null;
            }

            @Override
            protected void onCancelled(String s) {
                super.onCancelled(s);
            }

            @Override
            protected void onPostExecute(String s) {
                resultado   =   s;

                if(nro.equals("1")){
                    adapter = new ListViewAdapterAsistencia(myContext, cedula, nombre, descripcion, fecha);
                    lista.setAdapter(adapter);
                }else{
                    if(nro.equals("2") || nro.equals("3") || nro.equals("4")){
                        Toast.makeText(myContext,s,Toast.LENGTH_SHORT).show();
                        lista.setAdapter(null);
                        Hilo(direccion+"6"+"&fecha1="+fecha1+"&fecha2="+fecha2+"","1");
                    }else{
                        if(nro.equals("5") || nro.equals("6")){
                            Toast.makeText(myContext,s,Toast.LENGTH_SHORT).show();
                        }
                    }if(nro.equals("7")){

                    }
                }

                //super.onPostExecute(s);
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onProgressUpdate(Void... values) {
                super.onProgressUpdate(values);
            }
        }

        ObtenerWebService g = new ObtenerWebService();
        g.execute(GET,nro);


    }




}
