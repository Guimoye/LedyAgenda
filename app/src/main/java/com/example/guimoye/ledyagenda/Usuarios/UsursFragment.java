package com.example.guimoye.ledyagenda.Usuarios;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.PopupMenu;
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

public class UsursFragment extends Fragment {

    ListView lista;
    ArrayList<String> tipo = new ArrayList<>();
    ArrayList<String> correo = new ArrayList<>();
    ArrayList<Long> tlfno = new ArrayList<>();
    JsonUsers ju;
    LayoutInflater inflater;
    ViewGroup container;
     int positionn;
    View v;
    // Rutas de los Web Services
    String direccion="http://192.168.11.115:8282/ledy/usuarioGet.php?modelo=";
    String GET1 = direccion+"1";
    String GET2 = direccion+"5&correo=";
    AlertDialog.Builder builder;
    AlertDialog ad;
    AutoCompleteTextView umTipo,umClave,umCorreo,umPregunta,umRespuesta,umNombre;
    String name,pregunta,resp;

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        this.inflater   =   inflater;
        this.container  =   container;
        v               =   inflater.inflate(R.layout.fragment_usuraios,container,false);
        lista           =   (ListView) v.findViewById(R.id.listaUsuarios);
        ju              =   new JsonUsers(v,inflater,container,getActivity().getBaseContext(),lista,correo,tipo,GET1,"1");

        FloatingActionButton fab = (FloatingActionButton) v.findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                builder = new AlertDialog.Builder(getActivity());
                LayoutInflater inflater = getActivity().getLayoutInflater();
                final View dialoglayout = inflater.inflate(R.layout.register_user, null);

                    umCorreo    = (AutoCompleteTextView) dialoglayout.findViewById(R.id.txtCorreoRegister);
                    umClave     = (AutoCompleteTextView) dialoglayout.findViewById(R.id.txtClaveRegister);
                    umTipo      = (AutoCompleteTextView) dialoglayout.findViewById(R.id.txtTipoRegister);
                    umNombre    = (AutoCompleteTextView) dialoglayout.findViewById(R.id.txtNombreRegister);
                    umPregunta  = (AutoCompleteTextView) dialoglayout.findViewById(R.id.txtPreguntaRegister);
                    umRespuesta = (AutoCompleteTextView) dialoglayout.findViewById(R.id.txtRespuestaRegister);
                    //umCorreo.setEnabled(false);

                final Button btnRegistrarUsuario   = (Button) dialoglayout.findViewById(R.id.btn_registrar_users);
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
/*
                Snackbar.make(v, "presione registrar", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/


            }
        });
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
                                ju.Hilo(GET2+ju.getCorreo(position),"2");
                                break;
                            case R.id.action_settings1:
                                builder = new AlertDialog.Builder(getActivity());
                                LayoutInflater inflater = getActivity().getLayoutInflater();
                                final View dialoglayout = inflater.inflate(R.layout.modificar_user, null);

                                umCorreo     = (AutoCompleteTextView) dialoglayout.findViewById(R.id.txtCorreoModificar);
                                umClave      = (AutoCompleteTextView) dialoglayout.findViewById(R.id.txtClaveModificar);
                                umTipo       = (AutoCompleteTextView) dialoglayout.findViewById(R.id.txtTipoModificar);
                                umNombre     = (AutoCompleteTextView) dialoglayout.findViewById(R.id.txtNombreModificar);
                                umPregunta   = (AutoCompleteTextView) dialoglayout.findViewById(R.id.txtPreguntaModificar);
                                umRespuesta  = (AutoCompleteTextView) dialoglayout.findViewById(R.id.txtRespuestaModificar);

                                umCorreo.setText(ju.getCorreo(position));
                                umClave.setText(ju.getClave(position));
                                umTipo.setText(ju.getTipo(position)+"");
                                umNombre.setText(ju.getNombre(position)+"");
                                umPregunta.setText(ju.getPregunta(position));
                                umRespuesta.setText(ju.getRespuesta(position));
                                umTipo.setEnabled(false);
                                umCorreo.setEnabled(false);


                                final Button btnMoficiarUsuario   = (Button) dialoglayout.findViewById(R.id.btn_modificar_usuario);
                                btnMoficiarUsuario.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        name = umNombre.getText().toString();
                                        name = name.replaceAll("\n", "%20");
                                        name = name.replaceAll(" ", "%20");

                                        pregunta = umPregunta.getText().toString();
                                        pregunta = pregunta.replaceAll("\n", "%20");
                                        pregunta = pregunta.replaceAll(" ", "%20");

                                        resp = umRespuesta.getText().toString();
                                        resp = resp.replaceAll("\n", "%20");
                                        resp = resp.replaceAll(" ", "%20");

                                        ju.Hilo(direccion+"4" +
                                                        "&correo="+ju.getCorreo(position)+"" +
                                                        "&clave="+umClave.getText()+"" +
                                                        "&nombre="+name+"" +
                                                        "&pregunta="+pregunta+"" +
                                                        "&respuesta="+resp+""
                                                ,"3");

/*
                                        ju.setUsuario(position,umUsuario.getText().toString());
                                        ju.setClave(position,umClave.getText().toString());
                                        ju.setCorreo(position,umCorreo.getText().toString());
                                        ju.setPregunta(position,umPregunta.getText().toString());
                                        ju.setRespuesta(position,umRespuesta.getText().toString());
                                        ju.setTipo(position,Integer.parseInt(umTipo.getText().toString()));
                                        ju.setTlfno(position, Long.parseLong(umTlfno.getText().toString()));*/
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


        return v;
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

                                String name = umNombre.getText().toString();
                                name = name.replaceAll("\n", "%20");
                                name = name.replaceAll(" ", "%20");

                                String pregunta = umPregunta.getText().toString();
                                pregunta = pregunta.replaceAll("\n", "%20");
                                pregunta = pregunta.replaceAll(" ", "%20");

                                String resp = umRespuesta.getText().toString();
                                resp = resp.replaceAll("\n", "%20");
                                resp = resp.replaceAll(" ", "%20");

                                umTipo.setEnabled(false);
                                String sql=direccion+"9" +
                                        "&correo="+umCorreo.getText().toString()+"" +
                                        "&clave="+umClave.getText().toString()+"" +
                                        "&tipo="+umTipo.getText().toString()+"" +
                                        "&nombre="+name+"" +
                                        "&pregunta="+pregunta+"" +
                                        "&respuesta="+resp+"";
                                ju.Hilo(sql,"4");

                               // Toast.makeText(getActivity(),"deberia registrar dsps de esto",Toast.LENGTH_LONG).show();
                                limpiar();
                                ad.dismiss();

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
