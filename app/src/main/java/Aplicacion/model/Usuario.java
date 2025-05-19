package Aplicacion.model;

import com.google.firebase.Timestamp;

public class Usuario {
    private String uID;
    private String nombre;
    private String apellido;
    private String nombreUsuario;
    private long distanciaMax;
    private Timestamp fecha;

    public Usuario(String uID, String nombre, String apellido, String nombreUsuario, long distanciaMax, Timestamp fecha ){
        this.uID= uID;
        this.nombre = nombre;
        this.apellido = apellido;
        this.nombreUsuario = nombreUsuario;
        this.distanciaMax = distanciaMax;
        this.fecha=fecha;
    }

    public String getuID(){
        return uID;
    }
    public void setNombre(String nombre){
        this.nombre = nombre;
    }
    public String getNombreUsuario(){ return nombreUsuario;}
    public void setNombreUsuario(String nombreUsuario){this.nombreUsuario=nombreUsuario;}

}
