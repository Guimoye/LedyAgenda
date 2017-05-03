package com.example.guimoye.ledyagenda.Usuarios;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.guimoye.ledyagenda.R;

import java.util.ArrayList;

public class ListViewAdapterUsuars extends BaseAdapter {
    // Declare Variables
    Context context;
    ArrayList<String> correo;
    ArrayList<String> clave;
    ArrayList<String> tipo;
    ArrayList<String> nombre;
    ArrayList<String> pregunta;
    ArrayList<String> respuesta;

    LayoutInflater inflater;

    public ListViewAdapterUsuars(Context context,ArrayList<String> correo, ArrayList<String> clave,
                                 ArrayList<String> tipo, ArrayList<String> nombre,
                                 ArrayList<String> pregunta, ArrayList<String> respuesta) {

        this.context    =   context;
        this.correo     =   correo;
        this.clave      =   clave;
        this.tipo       =   tipo;
        this.nombre     =   nombre;
        this.pregunta   =   pregunta;
        this.respuesta  =   respuesta;

        inflater = LayoutInflater.from(context);

    }

    @Override
    public int getCount() {
        return correo.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public void updateResults(ArrayList<String> correo, ArrayList<String> clave,
                              ArrayList<String> tipo, ArrayList<String> nombre,
                              ArrayList<String> pregunta, ArrayList<String> respuesta) {
        this.correo     =   correo;
        this.clave      =   clave;
        this.tipo       =   tipo;
        this.nombre     =   nombre;
        this.pregunta   =   pregunta;
        this.respuesta  =   respuesta;

        //Triggers the list update
        notifyDataSetChanged();
    }


    public View getView(int position, View convertView, ViewGroup parent) {

        // Declare Variables
        TextView txtCorreo, txtClave, txtTipo,txtNombre,txtPregunta,txtRespuesta;

        //http://developer.android.com/intl/es/reference/android/view/LayoutInflater.html
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View itemView = inflater.inflate(R.layout.list_modelo_usuarios, parent, false);

        // Locate the TextViews in listview_item.xml
        txtCorreo       = (TextView) itemView.findViewById(R.id.row_txt_correo);
        txtClave        = (TextView) itemView.findViewById(R.id.row_txt_clave);
        txtTipo         = (TextView) itemView.findViewById(R.id.row_txt_tipo);
        txtNombre       = (TextView) itemView.findViewById(R.id.row_txt_nombre);
        txtPregunta     = (TextView) itemView.findViewById(R.id.row_txt_pregunta);
        txtRespuesta    = (TextView) itemView.findViewById(R.id.row_txt_respuesta);


        // Capture position and set to the TextViews

        txtCorreo.setText(correo.get(position));
        txtClave.setText(clave.get(position));
        txtTipo.setText(tipo.get(position));
        txtNombre.setText(nombre.get(position));
        txtPregunta.setText(pregunta.get(position));
        txtRespuesta.setText(respuesta.get(position));

        return itemView;
    }
}