package com.galacticCat.chatbleu.login_register;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
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

import com.galacticCat.chatbleu.R;
import com.galacticCat.chatbleu.model.User;
import com.google.gson.Gson;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String LOG = LoginActivity.class.getName();

    private Context mContext;
    private User mUser;

    private ImageView mFotoImageView;

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
        mFotoImageView = findViewById(R.id.foto);
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

        Intent intent = new Intent(mContext, ListaActivity.class);
        intent.putExtra(SyncStateContract.Constants.KEY_USUARIO, usuario);
        intent.putExtra(SyncStateContract.Constants.KEY_PASSWORD, password);
        startActivity(intent);
    }

    private void createObject() {
        mUser = new User();
        mUser.setNombreUsuario("dilanAA");
        mUser.setPassword("bbcita420");
        mUser.setEdad(19);
        mUser.setEmail("nalid102@gmail.com");
        mUser.setPeso(46);

        String json = new Gson().toJson(mUser);
        Log.e("Mi Usuario", json);
    }

    private void createObjectFromString() {
        String json = "{\"nombreUsuario\":\"IgnacioElBroko\",\"password\":\"todopoderosoYO\",\"edad\":19,\"email\":\"nachitotigredelriozenteno@gmail.com\",\"codigoUpb\":46036,\"celular\":77511999}";
        mUser = new Gson().fromJson(json, User.class);
        Toast.makeText(mContext, mUser.getPassword(), Toast.LENGTH_LONG).show();
    }

    public void registrarClick(View view) {
        Toast.makeText(mContext, "El click funciona", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(mContext, RegisterActivity.class);
        startActivityForResult(intent, SyncStateContract.Constants.CODIGO_TRANSACCION);
    }

    public void llamarAJordiClick(View view) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + "78898825"));
        startActivity(intent);
    }

    public void compartirTextClick(View view) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT, "Hola muchachos!");
        intent.setType("text/plain");
        startActivity(intent);

    }

    public void tomarUnaFotoClick(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, Constants.CODIGO_TRANSACCION_FOTO);
    }

    public void llevameAlaU(View view) {
        /*String name = "Campus UPB";
        Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                Uri.parse("geo:0,0?q=-16.575137, -68.126868 (" + name + ")"));
        startActivity(intent);*/

        String url = "http://www.upb.edu/";
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SyncStateContract.Constants.CODIGO_TRANSACCION) {
            //Objeto usuario
            if (resultCode == RESULT_OK) {
                if (data != null) {
                    String json = data.getStringExtra(SyncStateContract.Constants.KEY_REGISTRAR_USUARIO);
                    Log.e("Usuario recibido", json);

                    User usuarioRecibido = new Gson().fromJson(json, User.class);
                    mUsuarioEditText.setText(usuarioRecibido.getNombreUsuario());
                    mPasswordEditText.setText(usuarioRecibido.getPassword());
                }
            }
        } else if (requestCode == SyncStateContract.Constants.CODIGO_TRANSACCION_FOTO) {
            //Foto
            if (resultCode == RESULT_OK) {
                Log.e("Foto", "Valida");
                Bitmap thumbnail = data.getParcelableExtra("data"); // Obtenemos el Bitmap (imagen) capturada
                // Mostramos nuestra imagen en el imageView
                mFotoImageView.setImageBitmap(thumbnail);
            } else {
                Log.e("Foto cancelada", "Canceled");
            }
        }
    }
}
