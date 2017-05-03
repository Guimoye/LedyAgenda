package com.example.guimoye.ledyagenda.Asistencia;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;

import com.example.guimoye.ledyagenda.R;

import java.util.ArrayList;

public class AsistenciaFragmentUsu extends Fragment {

    ListView lista;
    ArrayList<String> cedula = new ArrayList<>();
    JsonAsistencia ju;
    LayoutInflater inflater;
    ViewGroup container;
     int positionn;
    View v;
    // Rutas de los Web Services
    String direccion="http://192.168.11.115:8282/ledy/asistencia.php?modelo=";
    String GET1 = direccion+"6";
    String GET2 = direccion+"3&cedula=";
    AlertDialog.Builder builder;
    AlertDialog ad;
    AutoCompleteTextView umCedula, umDescripcion, umFecha,umNombre, umFechaDsde, umFechaHast;
    String name,des;

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        this.inflater               =   inflater;
        this.container              =   container;
        v                           =   inflater.inflate(R.layout.fragment_asistencia,container,false);
        lista                       =   (ListView) v.findViewById(R.id.listaAsistencia);
        ju                          =   new JsonAsistencia(v,inflater,container,getActivity().getBaseContext(),lista, cedula,GET1,"1");
        FloatingActionButton fab    =   (FloatingActionButton) v.findViewById(R.id.fab);


        /************** ventana de busqueda *****************/
        builder = new AlertDialog.Builder(getActivity());
        final View dialoglayout = inflater.inflate(R.layout.consultar_fecha_asistencia, null);

        umFechaDsde = (AutoCompleteTextView) dialoglayout.findViewById(R.id.txtAsistencia_fechaDesde);
        umFechaHast = (AutoCompleteTextView) dialoglayout.findViewById(R.id.txtAsistencia_fechaHasta);
        final Button btnMoficiarUsuario   = (Button) dialoglayout.findViewById(R.id.btn_BuscarAsistencia_Fecha);


        btnMoficiarUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ju.cambiarFechas(umFechaDsde.getText().toString(),umFechaHast.getText().toString());
                ju.Hilo(GET1,"1");
                ad.dismiss();
            }
        });

        builder.setView(dialoglayout);
        ad = builder.create();
        ad.show();

        /********************   boton flotante    ************************/

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                builder = new AlertDialog.Builder(getActivity());
                LayoutInflater inflater = getActivity().getLayoutInflater();
                final View dialoglayout = inflater.inflate(R.layout.register_asistencia, null);

                    umCedula        = (AutoCompleteTextView) dialoglayout.findViewById(R.id.txtCedulaRegister);
                    umNombre        = (AutoCompleteTextView) dialoglayout.findViewById(R.id.txtNombreRegister);
                    umDescripcion   = (AutoCompleteTextView) dialoglayout.findViewById(R.id.txtDescripcionRegister);
                    umFecha         = (AutoCompleteTextView) dialoglayout.findViewById(R.id.txtFechaRegister);

                final Button btnRegistrarUsuario   = (Button) dialoglayout.findViewById(R.id.btn_registrar_asistencia);
                btnRegistrarUsuario.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        validacions();
                        //ad.dismiss();
                    }
                });

                builder.setView(dialoglayout);
                ad = builder.create();
                ad.show();



            }
        });

        fab.setEnabled(false);
/*
        ju.getLista().setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView parent, View view, final int position, long id) {
                positionn=position;
                PopupMenu popup = new PopupMenu(getActivity(), view);
                popup.getMenuInflater().inflate(R.menu.main, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

                    //MENU FILA
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.action_settings:
                                ju.Hilo(GET2+ju.getCedula(position),"2");
                                break;
                            case R.id.action_settings1:
                                builder                 = new AlertDialog.Builder(getActivity());
                                LayoutInflater inflater = getActivity().getLayoutInflater();
                                final View dialoglayout = inflater.inflate(R.layout.modificar_asistencia, null);

                                umCedula        = (AutoCompleteTextView) dialoglayout.findViewById(R.id.txtCedulaModificar);
                                umNombre        = (AutoCompleteTextView) dialoglayout.findViewById(R.id.txtNombreModificar);
                                umDescripcion   = (AutoCompleteTextView) dialoglayout.findViewById(R.id.txtDescripcionModificar);
                                umFecha         = (AutoCompleteTextView) dialoglayout.findViewById(R.id.txtFechaModificar);

                                umCedula.setText(ju.getCedula(position));
                                umNombre.setText(ju.getNombre(position)+"");
                                umDescripcion.setText(ju.getDescripcion(position));
                                umFecha.setText(ju.getFecha(position));


                                Button btnMoficiarUsuario   = (Button) dialoglayout.findViewById(R.id.btn_modificar_asistencia);
                                btnMoficiarUsuario.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        name = umNombre.getText().toString();
                                        name = name.replaceAll("\n", "%20");
                                        name = name.replaceAll(" ", "%20");

                                        des = umDescripcion.getText().toString();
                                        des = des.replaceAll("\n", "%20");
                                        des = des.replaceAll(" ", "%20");
                                        Log.e("modificando","nombreeeeess: "+name+" descripcion "+des);

                                        ju.Hilo("http://192.168.11.115:8282/ledy/asistencia.php?modelo="+"2" +
                                                        "&cedula="+ju.getCedula(position)+"" +
                                                        "&cedulaNu="+umCedula.getText()+"" +
                                                        "&nombre="+name+"" +
                                                        "&descripcion="+ des+"" +
                                                        "&fecha="+ umFecha.getText()+""
                                                ,"3");

                                        ad.dismiss();
                                    }
                                });

                                builder.setView(dialoglayout);
                                ad = builder.create();
                                ad.show();

                                break;
                        }
                        return true;
                    }
                    //FIN MENU FILA
                });
                popup.show();
                return true;
            }
        });

        */

        return v;
    }

    private void limpiarRequired(){
        umCedula.setError(null);
        umNombre.setError(null);
        umDescripcion.setError(null);
        umFecha.setError(null);
    }

    private void validacions(){
        limpiarRequired();
        if(umCedula.getText().length()!=0){
                    if(umNombre.getText().length()!=0){
                        if(umDescripcion.getText().length()!=0){
                            if(umFecha.getText().length()!=0){


                                name = umNombre.getText().toString();
                                name = name.replaceAll("\n", "%20");
                                name = name.replaceAll(" ", "%20");

                                des = umDescripcion.getText().toString();
                                des = des.replaceAll("\n", "%20");
                                des = des.replaceAll(" ", "%20");

                                String sql=direccion+"5" +
                                        "&cedula="+ umCedula.getText().toString()+"" +
                                        "&nombre="+name+"" +
                                        "&descripcion="+ des+"" +
                                        "&fecha="+ umFecha.getText().toString()+"";
                                ju.Hilo(sql,"4");

                               // Toast.makeText(getActivity(),"deberia registrar dsps de esto",Toast.LENGTH_LONG).show();
                                limpiar();
                                ad.dismiss();

                            }else{
                                vali(umFecha);
                            }
                        }else{
                            vali(umDescripcion);
                        }
                    }else{
                        vali(umNombre);
                    }

        }else{
            vali(umCedula);
        }

    }

    private void vali(AutoCompleteTextView t){
        t.setError(getString(R.string.error_field_required));
        v=t;
        v.requestFocus();
    }

    private void limpiar(){
        umCedula.setText(null);
        umNombre.setText(null);
        umDescripcion.setText(null);
        umFecha.setText(null);
    }

}
