package com.example.guimoye.ledyagenda.Agenda;

import android.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.guimoye.ledyagenda.R;

import org.json.JSONException;
import org.json.JSONObject;

public class ConsultaFragment extends Fragment {

    JsonConsulta ju;
    AlertDialog.Builder builder;
    AlertDialog ad;
    LayoutInflater inflater2;
    ListView lista;
    AutoCompleteTextView umFechaAgenda, umNombreAgenda, umFechaDsde, umFechaHast;
    View v;
    String name;

    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {

        View    v                   =   inflater.inflate(R.layout.fragment_consulta,container,false);
        inflater2                   =   inflater;
        lista                       =   (ListView) v.findViewById(R.id.listaConsultaAgenda);
        FloatingActionButton fab    =   (FloatingActionButton) v.findViewById(R.id.fab);
        ju                          =   new JsonConsulta(v,inflater,container,getActivity(), lista);


        /********************   boton flotante    ************************/

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                builder                 = new AlertDialog.Builder(getActivity());
                LayoutInflater inflater = getActivity().getLayoutInflater();
                final View dialoglayout = inflater.inflate(R.layout.register_agenda, null);

                umFechaAgenda           = (AutoCompleteTextView) dialoglayout.findViewById(R.id.txtFechaAgenda);
                umNombreAgenda          = (AutoCompleteTextView) dialoglayout.findViewById(R.id.txtNombreAgenda);

                final Button btnRegistrarUsuario   = (Button) dialoglayout.findViewById(R.id.btn_registrar_agenda);
                btnRegistrarUsuario.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(umFechaAgenda.getText().length()!=0){
                            if(umNombreAgenda.getText().length()!=0){

                                name = umNombreAgenda.getText().toString();
                                name = name.replaceAll("\n", "%20");
                                name = name.replaceAll(" ", "%20");

                                String sql="http://192.168.11.115:8282/ledy/agenda.php?modelo=1" +
                                        "&fecha="+umFechaAgenda.getText()+"&nombre_evento="+name+"";

                                RequestQueue queue;
                                queue = Volley.newRequestQueue(getActivity());

                                StringRequest stringRequest = new StringRequest(Request.Method.GET, sql,
                                        new Response.Listener<String>() {
                                            @Override
                                            public void onResponse(String response) {

                                                Toast.makeText(getActivity(),"Agenda Registrada con Exito",Toast.LENGTH_LONG).show();

                                            }
                                        }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Log.e("Esto trajoo ","trajo estooooo: "+error);
                                    } });

                                queue.add(stringRequest);


                                umFechaAgenda.setText("");
                                umNombreAgenda.setText("");
                                ad.dismiss();

                            }else{
                                vali(umNombreAgenda);
                            }
                        }else{
                            vali(umFechaAgenda);
                        }
                    }
                });

                builder.setView(dialoglayout);
                ad = builder.create();
                ad.show();



            }
        });

        /************** ventana de busqueda *****************/
        builder = new AlertDialog.Builder(getActivity());
        final View dialoglayout = inflater.inflate(R.layout.consultar_fecha_asistencia, null);

        umFechaDsde = (AutoCompleteTextView) dialoglayout.findViewById(R.id.txtAsistencia_fechaDesde);
        umFechaHast = (AutoCompleteTextView) dialoglayout.findViewById(R.id.txtAsistencia_fechaHasta);
        final Button btnMoficiarUsuario   = (Button) dialoglayout.findViewById(R.id.btn_BuscarAsistencia_Fecha);


        btnMoficiarUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // ju.cambiarFechas(umFechaDsde.getText().toString(),umFechaHast.getText().toString());

                ju.Hilo("http://192.168.11.115:8282/ledy/agenda.php?modelo=3&fecha1="+umFechaDsde.getText()+"&fecha2="+umFechaHast.getText()+"");
                ad.dismiss();
            }
        });

        builder.setView(dialoglayout);
        ad = builder.create();
        ad.show();

        return v;
    }

    private void vali(AutoCompleteTextView t){
        t.setError(getString(R.string.error_field_required));
        v=t;
        v.requestFocus();
    }

}





