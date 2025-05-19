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
    private Model(){

    }
    public static Model getInstance(){
        return miModel;
    }

    public void iniciarUsuario(FirebaseUser user, Runnable callback){
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
                    } else {
                        usuario = new Usuario(
                                document.getId(),
                                "realName",
                                "realSubname",
                                "userName",
                                10,
                                Timestamp.now()
                        );
                    }
                    Log.i("LOGIN", "iniciarUsuario funcional");
                    callback.run();
                })
                .addOnFailureListener(e -> {
                    Log.i("LOGIN", "iniciarUsuario error");
                });
    }

    public void setNombre(String nombre){
        baseDatos.collection("usuarios").document(usuario.getuID())
                .update("nombre", nombre)
                .addOnSuccessListener(unused -> {
                    usuario.setNombre(nombre);  // Actualizas el objeto en memoria
                    Log.d("FIRESTORE", "Nombre actualizado correctamente.");
                })
                .addOnFailureListener(e -> {
                    Log.e("FIRESTORE", "Error al actualizar nombre", e);
                });
        usuario.setNombre(nombre);
    }

    public Usuario getUsuario(){
        return this.usuario;
    }
}
