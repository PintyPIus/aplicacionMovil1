package Aplicacion.controller;

import com.MiApp.app.R;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.app.Activity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import Aplicacion.model.Model;

public class PantallaInicioController extends Activity {
    private static final int RC_SIGN_IN = 9001;
    private GoogleSignInClient mGoogleSignInClient;
    private FirebaseAuth mAuth;
    private Model model;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Esto inicializa la vista
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pantalla_inicio);

        //Obtienes la instancia del firebase y el model
        mAuth = FirebaseAuth.getInstance();
        model = Model.getInstance();

        //Configuracion del Google Sign-in
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        //Configuraciion del boton de login
        Button btnGoogle = findViewById(R.id.btnGoogleLogin);
        btnGoogle.setOnClickListener(v -> signIn());

        Log.i("LOGIN", "Se ha iniciado correctamente");

    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
        Log.i("LOGIN", "SignIn funcional");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account.getIdToken());
                Log.i("LOGIN", "Exito en onActivityResult");
            } catch (ApiException e) {
                Log.w("LOGIN", "Fallo en onActivityResult");
            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        Log.i("LOGIN", "Sign-in correcto: " + user.getDisplayName());
                        model.iniciarUsuario(user, () -> mostrarPantallaUsuario());
                    } else {
                        Log.w("LOGIN", "Fallo en Firebase Sign-in", task.getException());
                    }
                });
    }

    private void mostrarPantallaUsuario(){
        Intent intent = new Intent(this, PantallaUsuarioController.class);
        startActivity(intent);
        finish();
        Log.i("LOGIN", "mostrarPantallaUsuario funcional");
    }
}
