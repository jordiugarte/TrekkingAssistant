package com.galacticCat.chatbleu.login_register;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.provider.SyncStateContract;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.galacticCat.chatbleu.Constants;
import com.galacticCat.chatbleu.MainActivity;
import com.galacticCat.chatbleu.R;
import com.galacticCat.chatbleu.model.User;
import com.google.gson.Gson;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String LOG = LoginActivity.class.getName();

    private Context mContext;
    private User mUser;

    private ImageView mLogoImageView;

    private EditText mUsuarioEditText;
    private EditText mPasswordEditText;

    private Button mIniciarSesionButton;

    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.w(LOG, "onCreate");

        setContentView(R.layout.activity_login);
        mContext = this;

        initViews();
        addEvents();
        //createObject();
        createObjectFromString();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.w(LOG, "onStart");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.w(LOG, "onPause");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.w(LOG, "onResume");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.w(LOG, "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.w(LOG, "onDestroy");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.w(LOG, "onRestart");
    }

    private void initViews() {
        mLogoImageView = findViewById(R.id.logo);
        mUsuarioEditText = findViewById(R.id.usuario);
        mPasswordEditText = findViewById(R.id.password);
        mIniciarSesionButton = findViewById(R.id.iniciarSesion);
    }

    private void addEvents() {
        mIniciarSesionButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        String usuario = mUsuarioEditText.getText().toString();
        String password = mPasswordEditText.getText().toString();
        Log.e("Mis datos", usuario + " " + password);

        Intent intent = new Intent(mContext, MainActivity.class);
        intent.putExtra(Constants.KEY_USUARIO, usuario);
        intent.putExtra(Constants.KEY_PASSWORD, password);
        startActivity(intent);

        if (validarUsuario(usuario, password)) {

            intent.putExtra(Constants.KEY_USUARIO, usuario);
            intent.putExtra(Constants.KEY_PASSWORD, password);
            startActivity(intent);
        } else {
            Toast.makeText(mContext, "Usuario o password invalido", Toast.LENGTH_SHORT)
                    .show();
        }
    }

    private void createObject() {
        mUser = new User();

        String json = new Gson().toJson(mUser);
        Log.e("Mi Usuario", json);
    }

    private void createObjectFromString() {
        String json = "{\"nombreUsuario\",\"password\",\"edad\",\"peso\"}";
        mUser = new Gson().fromJson(json, User.class);
        Toast.makeText(mContext, mUser.getPassword(), Toast.LENGTH_LONG).show();
    }
    public void registrarClick(View view) {
        Intent intent = new Intent(mContext, RegisterActivity.class);
        startActivityForResult(intent, Constants.CODIGO_TRANSACCION);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Constants.CODIGO_TRANSACCION) {
            //Objeto usuario
            if (resultCode == RESULT_OK) {
                if (data != null) {
                    String json = data.getStringExtra(Constants.KEY_REGISTRAR_USUARIO);
                    Log.e("Usuario recibido", json);

                    User usuarioRecibido = new Gson().fromJson(json, User.class);
                    mUsuarioEditText.setText(usuarioRecibido.getNombreUsuario());
                    mPasswordEditText.setText(usuarioRecibido.getPassword());
                }
            }
        }
    }
    private boolean validarUsuario(String usuario, String password) {
        if (usuario == null || usuario.isEmpty()) {
            return false;
        }

        if (password == null || password.isEmpty()) {
            return false;
        }

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mContext);
        String usuarioGuardado = prefs.getString(Constants.PREF_USUARIO, "");
        String passwordGuardado = prefs.getString(Constants.PREF_PASSWORD, "");

        return usuario.equals(usuarioGuardado) && password.equals(passwordGuardado);
    }

    public void eliminarDatos(View view) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mContext);
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        editor.apply();
    }
}
