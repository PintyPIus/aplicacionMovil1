package Aplicacion.controller;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.MiApp.app.R;

import Aplicacion.model.Model;

public class PantallaUsuarioController extends Activity {
    private Model model = Model.getInstance();

    protected void onCreate(Bundle savedInstanceState) {
        //Esto inicializa la vista
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pantalla_usuario);

        cargarDatos();
    }

    private void cargarDatos(){
        cargarUsuario();
    }

    private void cargarUsuario(){
        TextView txtNombre = findViewById(R.id.textoNombreUsuario);
        txtNombre.setText(model.getUsuario().getNombreUsuario());
    }
}
