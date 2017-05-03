package com.example.guimoye.ledyagenda.Asistencia;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.guimoye.ledyagenda.R;

import java.util.ArrayList;

public class ListViewAdapterAsistencia extends BaseAdapter {
    // Declare Variables
    Context context;
    ArrayList<String> cedula;
    ArrayList<String> nombre;
    ArrayList<String> descripcion;
    ArrayList<String> fecha;

    LayoutInflater inflater;

    public ListViewAdapterAsistencia(Context context, ArrayList<String> cedula, ArrayList<String> nombre,
                                     ArrayList<String> descripcion, ArrayList<String> fecha) {

        this.context        =   context;
        this.cedula         =   cedula;
        this.nombre         =   nombre;
        this.descripcion    =   descripcion;
        this.fecha          =   fecha;

        inflater = LayoutInflater.from(context);

    }

    @Override
    public int getCount() {
        return cedula.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public void updateResults(ArrayList<String> correo, ArrayList<String> nombre,
                              ArrayList<String> pregunta, ArrayList<String> respuesta) {
        this.cedula         =   correo;
        this.nombre         =   nombre;
        this.descripcion    =   pregunta;
        this.fecha          =   respuesta;

        //Triggers the list update
        notifyDataSetChanged();
    }


    public View getView(int position, View convertView, ViewGroup parent) {

        // Declare Variables
        TextView txtCedula, txtNombre, txtDescripcion,txtFecha;

        //http://developer.android.com/intl/es/reference/android/view/LayoutInflater.html
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View itemView = inflater.inflate(R.layout.list_modelo_asistencia, parent, false);

        // Locate the TextViews in listview_item.xml
        txtCedula       = (TextView) itemView.findViewById(R.id.row_txt_cedula_asistencia);
        txtNombre       = (TextView) itemView.findViewById(R.id.row_txt_nombre_asistencia);
        txtDescripcion  = (TextView) itemView.findViewById(R.id.row_txt_descripcion_asistencia);
        txtFecha        = (TextView) itemView.findViewById(R.id.row_txt_fecha_asistencia);


        // Capture position and set to the TextViews

        txtCedula.setText(cedula.get(position));
        txtNombre.setText(nombre.get(position));
        txtDescripcion.setText(descripcion.get(position));
        txtFecha.setText(fecha.get(position));

        return itemView;
    }
}