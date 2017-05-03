package com.example.guimoye.ledyagenda.Agenda;

/**
 * Created by Guimoye on 02/12/2016.
 */

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.guimoye.ledyagenda.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ListViewAdapterUsu extends BaseAdapter {

    Context context;
    ArrayList<String> getId;
    ArrayList<String> getFecha;
    ArrayList<String> getNombreEvento;
    ArrayList<Integer> getStatus;
    ArrayList<String> getDescripcion;
    ArrayList<String> getImagenes;

    LayoutInflater inflater;

    public ListViewAdapterUsu(Context context, ArrayList<String> getId, ArrayList<String> getFecha,
                              ArrayList<String> getNombreEvento, ArrayList<Integer> getStatus,
                              ArrayList<String> getDescripcion, ArrayList<String> getImagenes) {
        this.context            =   context;
        this.getId              =   getId;
        this.getFecha           =   getFecha;
        this.getNombreEvento    =   getNombreEvento;
        this.getStatus          =   getStatus;
        this.getDescripcion     =   getDescripcion;
        this.getImagenes        =   getImagenes;
        inflater = LayoutInflater.from(context);
//        Log.e("llamooo lenarr","llenarrr "+getId.get(0));
    }

    public int getCount() {
        return getId.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


    public View getView(final int position, View convertView, ViewGroup parent) {

        TextView txtNombreEvento,txtDescripcionEvento,txtFechaEvento;
        CircleImageView imgPermiso;

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View itemView = inflater.inflate(R.layout.list_modelo_consulta, parent, false);

        txtNombreEvento         = (TextView) itemView.findViewById(R.id.lnombre_evento);
        txtDescripcionEvento    = (TextView) itemView.findViewById(R.id.ldescripcion_evento);
        txtFechaEvento          = (TextView) itemView.findViewById(R.id.lfecha_evento);
        imgPermiso              = (CircleImageView) itemView.findViewById(R.id.lImagen);

        txtNombreEvento.setText(getNombreEvento.get(position));
        txtDescripcionEvento.setText(getDescripcion.get(position));
        txtFechaEvento.setText(getFecha.get(position));

        if(getStatus.get(position)!=0){
            Picasso.with(context).invalidate(getImagenes.get(position));
            Picasso.with(context).load(getImagenes.get(position)).into(imgPermiso);
        }




        return itemView;
    }

/*
    private void cambiarEstados(int es, final String msg,int i){
        RequestQueue queue;
        queue = Volley.newRequestQueue(context);

        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                "http://192.168.11.115:8282/mario/usuarioGet.php?modelo=7&id="+correo.get(i)+"&status="+es+"",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Toast.makeText(context,"Usuario "+msg+" permiso",Toast.LENGTH_LONG).show();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Esto trajoo ","trajo estooooo: "+error);
            } });

        Log.e("Esto trajoo ","trajo estooooo: "+"http://192.168.11.115:8282/mario/usuarioGet.php?modelo=7&id"+correo.get(i)+"&status="+es+"");
        queue.add(stringRequest);
    }

    */
}