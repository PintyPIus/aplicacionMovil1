package Aplicacion.model;
import android.util.Log;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.Timestamp;

public class Model {
    //hola
    private static final Model miModel = new Model();
    FirebaseFirestore baseDatos = FirebaseFirestore.getInstance();
    private Usuario usuario;
    private FirebaseUser user;
    private Model(){

    }
    public static Model getInstance(){
        return miModel;
    }

    public void iniciarUsuario(FirebaseUser user, Runnable callback){
        this.user = user;
        baseDatos.collection("usuarios").document(user.getUid()).get()
                .addOnSuccessListener(document -> {
                    if (document.exists()) {
                        usuario = new Usuario(
                                document.getId(),
                                document.getString("nombre"),
                                document.getString("apellido"),
                                document.getString("nombreUsuario"),
                                document.getLong("distanciaMax"),
                                document.getTimestamp("fechaInicio")
                        );
                    }
                    else {
                        // Crear un nuevo usuario con valores por defecto
                        usuario = new Usuario(
                                user.getUid(),
                                "NombrePorDefecto",
                                "ApellidoPorDefecto",
                                "nombreUsuarioPorDefecto",
                                10,
                                Timestamp.now()
                        );
                    }
                    Log.i("LOGIN", "iniciarUsuario funcional");
                    callback.run();
                })
                .addOnFailureListener(e -> {
                    Log.e("LOGIN", "iniciarUsuario error", e);
                });
    }

    public void usuarioExistente(FirebaseUser user, ResultadoUsuarioCallback callback) {
        baseDatos.collection("usuarios").document(user.getUid()).get()
                .addOnSuccessListener(document -> {
                    callback.onResultado(document.exists());
                })
                .addOnFailureListener(e -> {
                    Log.e("COMPROBACION", "usuarioExistente error", e);
                    callback.onResultado(false);
                });
    }
    public void comprobarNombreUsuario(String nombre, ResultadoNombreCallback callback) {
        baseDatos.collection("usuarios")
                .whereEqualTo("nombreUsuario", nombre)
                .get()
                .addOnSuccessListener(querySnapshot -> {
                    if (!querySnapshot.isEmpty()) {
                        callback.onResult(false);
                    } else {
                        callback.onResult(true);
                    }
                })
                .addOnFailureListener(e -> {
                    Log.e("COMPROBACION", "Error al comprobar nombre de usuario", e);
                    callback.onResult(false);
                });
    }




    public void setNombre(String nombre){
        baseDatos.collection("usuarios").document(usuario.getuID())
                .update("nombre", nombre);
        usuario.setNombre(nombre);
    }
    public String getNombre(){
        return usuario.getNombre();
    }

    public void setApellido(String apellido){
        baseDatos.collection("usuarios").document(usuario.getuID())
                .update("apellido", apellido);
        usuario.setApellido(apellido);
    }
    public String getApellido(){
        return usuario.getApellido();
    }

    public void setNombreUsuario(String nombreUsuario){
        baseDatos.collection("usuarios").document(usuario.getuID())
                .update("nombreUsuario", nombreUsuario);
        usuario.setNombreUsuario(nombreUsuario);
    }
    public String getNombreUsuario(){
        return usuario.getNombreUsuario();
    }

    public void setDistanciaMax(long distanciaMax){
        baseDatos.collection("usuarios").document(usuario.getuID())
                .update("distanciaMax", distanciaMax);
        usuario.setDistanciaMax(distanciaMax);
    }
    public long getDistanciaMax(){
        return usuario.getDistanciaMax();
    }

    public FirebaseUser getUser(){
        return user;
    }
}
