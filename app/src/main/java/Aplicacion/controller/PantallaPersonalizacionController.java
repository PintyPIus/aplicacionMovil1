package Aplicacion.controller;

import com.MiApp.app.R;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import com.google.android.material.textfield.TextInputLayout;

import Aplicacion.model.Model;
import Aplicacion.view.ViewFactory;

public class PantallaPersonalizacionController extends Activity {

    private Model model = Model.getInstance();
    private ViewFactory viewFactory;
    private TextInputLayout inputLayoutNombreUsuario;

    private EditText editTextNombreUsuario;
    private EditText editTextNombre;
    private EditText editTextApellido;

    protected void onCreate(Bundle savedInstanceState) {
        //Esto inicializa la vista
        super.onCreate(savedInstanceState);
        try {
            viewFactory = new ViewFactory(this);
            viewFactory.mostrarPantallaPersonalizacion();

            // resto...
        } catch (Exception e) {
            Log.e("ERROR", "Fallo al cargar pantalla usuario", e);
        }


        inputLayoutNombreUsuario = findViewById(R.id.inputLayoutNombreUsuario);
        editTextNombreUsuario = findViewById(R.id.editTextNombreUsuario);
        editTextNombre = findViewById(R.id.editTextoNombre);
        editTextApellido = findViewById(R.id.editTextoApellido);

        // Listener cuando el usuario deja de escribir
        editTextNombreUsuario.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) { // Cuando se pierde el foco (el usuario deja de escribir)
                String nombreIngresado = editTextNombreUsuario.getText().toString().trim();
                if (nombreIngresado.isEmpty()) {
                    inputLayoutNombreUsuario.setError("Debe introducir un nombre");
                } else {
                    model.comprobarNombreUsuario(nombreIngresado, disponible -> {
                        runOnUiThread(() -> {
                            if (disponible) {
                                inputLayoutNombreUsuario.setError(null);
                                model.setNombreUsuario(nombreIngresado);
                            } else {
                                inputLayoutNombreUsuario.setError("Este nombre de usuario ya está en uso");
                            }
                        });
                    });
                }
            }
        });

        editTextNombre.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) { // Cuando se pierde el foco (el usuario deja de escribir)
                String nombreIngresado = editTextNombre.getText().toString().trim();
                if (nombreIngresado.isEmpty()) {
                    model.setNombre("NombreDefault");
                } else {
                    model.setNombre(nombreIngresado);
                }
            }
        });

        editTextApellido.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) { // Cuando se pierde el foco (el usuario deja de escribir)
                String apellidoIngresado = editTextNombre.getText().toString().trim();
                if (apellidoIngresado.isEmpty()) {
                    model.setApellido("ApellidoDefault");
                } else {
                    model.setApellido(apellidoIngresado);
                }
            }
        });
    }
}
