package Aplicacion.controller;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.MiApp.app.R;

import Aplicacion.model.Model;
import Aplicacion.view.ViewFactory;

public class PantallaUsuarioController extends Activity {
    private Model model = Model.getInstance();
    private ViewFactory viewFactory;

    protected void onCreate(Bundle savedInstanceState) {
        //Esto inicializa la vista
        super.onCreate(savedInstanceState);
        try {
            viewFactory = new ViewFactory(this);
            viewFactory.mostrarPantallaUsuario();

            // resto...
        } catch (Exception e) {
            Log.e("ERROR", "Fallo al cargar pantalla usuario", e);
        }

        TextView txtNombreUsuario = findViewById(R.id.textoNombreUsuario);
        txtNombreUsuario.setText(model.getNombreUsuario());

        TextView txtNombreYApellido = findViewById(R.id.textoNombreYApellido);
        String nombreYApellido = "Bienvenido, " + model.getNombre() + " " + model.getApellido();
        txtNombreYApellido.setText(nombreYApellido);
    }
}
