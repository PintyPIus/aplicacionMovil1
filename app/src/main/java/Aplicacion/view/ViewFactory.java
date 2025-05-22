package Aplicacion.view;

import android.app.Activity;
import com.MiApp.app.R;
public class ViewFactory {
    private final Activity activity;
    public ViewFactory(Activity activity){
        this.activity = activity;
    }
    public void mostrarPantallaInicio(){
        activity.setContentView(R.layout.pantalla_inicio);
    }
    public void mostrarPantallaUsuario(){
        activity.setContentView(R.layout.pantalla_usuario);
    }
    public void mostrarPantallaPersonalizacion(){
        activity.setContentView(R.layout.pantalla_personalizacion);
    }
}
