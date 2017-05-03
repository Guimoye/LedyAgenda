package com.example.guimoye.ledyagenda.Loggin;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import com.example.guimoye.ledyagenda.R;
import com.example.guimoye.ledyagenda.Usuarios.JsonUsers;

public class Register extends AppCompatActivity {

    private AutoCompleteTextView txtTipoRegister,txtClaveRegister,
            txtCorreoRegister,txtPreguntaRegister,txtRespuestaRegister,txtNombreRegister;
    private Button bRegister;
    private String nombre,clave,email,pregunta,respuesta, tipo;
    private View focusView = null;
    String direccion="http://192.168.11.115:8282/ledy/usuarioGet.php?modelo=";
    JsonUsers ju;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_user);
        txtNombreRegister       =   (AutoCompleteTextView) findViewById(R.id.txtNombreRegister);
        txtTipoRegister         =   (AutoCompleteTextView) findViewById(R.id.txtTipoRegister);
        txtClaveRegister        =   (AutoCompleteTextView) findViewById(R.id.txtClaveRegister);
        txtCorreoRegister       =   (AutoCompleteTextView) findViewById(R.id.txtCorreoRegister);
        txtPreguntaRegister     =   (AutoCompleteTextView) findViewById(R.id.txtPreguntaRegister);
        txtRespuestaRegister    =   (AutoCompleteTextView) findViewById(R.id.txtRespuestaRegister);
        bRegister               =   (Button) findViewById(R.id.btn_registrar_users);

        txtTipoRegister.setEnabled(false);
        bRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validacions();
            }
        });

    }

    private void validacions(){
        limpiarRequired();
        if(txtCorreoRegister.getText().toString().contains("@") && txtCorreoRegister.getText().toString().contains(".com") ){
            if(txtClaveRegister.getText().length()!=0){
            if(txtTipoRegister.getText().length()!=0){
                if(txtNombreRegister.getText().length()!=0){
                        if(txtPreguntaRegister.getText().length()!=0){
                            if(txtRespuestaRegister.getText().length()!=0){

                                email       =   txtCorreoRegister.getText().toString();
                                clave       =   txtClaveRegister.getText().toString();
                                tipo        =   txtTipoRegister.getText().toString();
                                nombre      =   txtNombreRegister.getText().toString();
                                pregunta    =   txtPreguntaRegister.getText().toString();
                                respuesta   =   txtRespuestaRegister.getText().toString();
                               // Toast.makeText(this.getApplicationContext(),"deberia registrar dsps de esto",Toast.LENGTH_LONG).show();

                                String sql  =direccion+"9" +
                                            "&correo="+email+"" +
                                            "&clave="+clave+"" +
                                            "&tipo="+tipo+"" +
                                            "&nombre="+nombre+"" +
                                            "&pregunta="+pregunta+"" +
                                            "&respuesta="+respuesta+"";
                                ju              =   new JsonUsers(getBaseContext(),sql,"5");
                                limpiar();

                            }else{
                                vali(txtRespuestaRegister);
                            }
                        }else{
                            vali(txtPreguntaRegister);
                        }

                }else{
                    vali(txtNombreRegister);
                }

            }else{
                vali(txtTipoRegister);
            }

            }else{
                vali(txtClaveRegister);
            }
            }else{
                txtCorreoRegister.setError(getString(R.string.error_invalid_email));
                focusView   =   txtCorreoRegister;
                focusView.requestFocus();
            }



    }

    private void vali(AutoCompleteTextView t){
        t.setError(getString(R.string.error_field_required));
        focusView=t;
        focusView.requestFocus();
    }

    private void limpiarRequired(){
        txtNombreRegister.setError(null);
        txtClaveRegister.setError(null);
        txtCorreoRegister.setError(null);
        txtPreguntaRegister.setError(null);
        txtRespuestaRegister.setError(null);
    }

    private void limpiar(){
        txtNombreRegister.setText(null);
        txtClaveRegister.setText(null);
        txtCorreoRegister.setText(null);
        txtPreguntaRegister.setText(null);
        txtRespuestaRegister.setText(null);
    }
}
