package com.galacticCat.chatbleu.login_register;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.galacticCat.chatbleu.Constants;
import com.galacticCat.chatbleu.R;
import com.galacticCat.chatbleu.model.User;
import com.google.gson.Gson;

public class RegisterActivity extends AppCompatActivity {
    private static final String LOG = RegisterActivity.class.getName();
    private Context mContext;

    private LinearLayout padre;
    private TextView txtUsuario;
    private EditText usuario;

    private TextView txtPassword;
    private EditText password;

    private TextView txtEdad;
    private EditText edad;

    private TextView txtPeso;
    private EditText peso;

    private LinearLayout botones;
    private Button enviar;
    private Button limpiar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v(LOG, "onCreate");

        mContext = this;

        //Padre
        padre = new LinearLayout(mContext);
        padre.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, //Width
                ViewGroup.LayoutParams.MATCH_PARENT)); //Height
        padre.setOrientation(LinearLayout.VERTICAL);
        padre.setPadding(25, 25, 25, 25);
        //padre.setBackgroundColor(mContext.getResources().getColor(R.color.colorPrimary));


        //Usuario
        txtUsuario = new TextView(mContext);
        txtUsuario.setText("Ingrese su usuario");
        txtUsuario.setTextSize(26);
        padre.addView(txtUsuario);

        usuario = new EditText(mContext);
        padre.addView(usuario);

        //Password
        txtPassword = new TextView(mContext);
        txtPassword.setTextSize(26);
        txtPassword.setText("Ingrese su Password");
        padre.addView(txtPassword);

        password = new EditText(mContext);
        password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        padre.addView(password);

        //Edad
        txtEdad = new TextView(mContext);
        txtEdad.setTextSize(26);
        txtEdad.setText("Ingrese su edad");
        padre.addView(txtEdad);

        edad = new EditText(mContext);
        edad.setInputType(InputType.TYPE_CLASS_NUMBER);

        int maxLength = 2;
        edad.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength)});
        padre.addView(edad);

        //Peso
        txtPeso = new TextView(mContext);
        txtPeso.setTextSize(26);
        txtPeso.setText("Ingrese su peso");
        padre.addView(txtPeso);

        peso = new EditText(mContext);
        peso.setInputType(InputType.TYPE_CLASS_NUMBER);

        peso.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength)});
        padre.addView(peso);


        //Panel de botones
        botones = new LinearLayout(mContext);
        botones.setOrientation(LinearLayout.HORIZONTAL);


        enviar = new Button(mContext);
        enviar.setText("Enviar");
        enviar.setLayoutParams(new LinearLayout.LayoutParams(
                0, //Width
                ViewGroup.LayoutParams.MATCH_PARENT,
                1)); //Height
        botones.addView(enviar);

        limpiar = new Button(mContext);
        limpiar.setText("Limpiar");
        limpiar.setLayoutParams(new LinearLayout.LayoutParams(
                0, //Width
                ViewGroup.LayoutParams.MATCH_PARENT,
                1)); //Height
        botones.addView(limpiar);
        padre.addView(botones);

        setContentView(padre);
        addEvents();
        getDataFromIntent();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.v(LOG, "onStart");
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

    private void addEvents() {
        limpiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usuario.setText("");
                password.setText("");
                edad.setText("");
                peso.setText("");
            }

        });

        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateForm();
            }
        });
    }

    private void validateForm(){
        if (usuario.getText().toString().isEmpty()) {
            //Toast.makeText(mContext, "Ingrese el usuario por favor", Toast.LENGTH_SHORT).show();

            //Layout inflater será el método para agregar un mayout
            LayoutInflater inflater = getLayoutInflater();

            //Utilizaremos el layout llamado layout_toast que contiene el id específico layout_vista
            View layouttoast = inflater.inflate(R.layout.layout_toast, (ViewGroup) findViewById(R.id.vista));

            //Llenamos el TextView con id=texto con el mensaje
            ((TextView) layouttoast.findViewById(R.id.texto)).setText("Ingrese el usuario por favor");
            ((ImageView) layouttoast.findViewById(R.id.imageToast)).setImageResource(R.drawable.error);

            //Declaramos el context
            Toast mensaje = new Toast(getBaseContext());

            //Declaramos la ubicación en pantalla
            mensaje.setGravity(Gravity.CENTER_VERTICAL, 0, 0);

            //Declaramos el contenido
            mensaje.setView(layouttoast);
            mensaje.setDuration(Toast.LENGTH_LONG);
            mensaje.show();
            return;
        }
        if (password.getText().toString().isEmpty()) {
            password.setError("Ingrese el password por favor");
            return;
        }
        showPasswordConfirm();

        if (edad.getText().toString().isEmpty()) {
            Snackbar snackbar = Snackbar.make(padre, "Ingrese la edad por favor", Snackbar.LENGTH_LONG);
            snackbar.show();
            return;
        }

        if (peso.getText().toString().isEmpty()) {
            peso.setError("Ingrese el password por favor");
            return;
        }

        User user = new User();
        user.setNombreUsuario(usuario.getText().toString());
        user.setPassword(password.getText().toString());
        user.setEdad(Integer.parseInt(edad.getText().toString()));
        user.setPeso(Integer.parseInt(peso.getText().toString()));

        String json = new Gson().toJson(user);
        Log.e("UsuarioEnviado", json);

        Intent intent = new Intent();
        intent.putExtra(Constants.KEY_REGISTRAR_USUARIO, json);
        setResult(RESULT_OK, intent); //OK: funciono, intent --> retornando el valor
        finish(); //Cierra el activity
    }
    private void getDataFromIntent() {
        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();

        if (Intent.ACTION_SEND.equals(action) && type != null) {
            if ("text/plain".equals(type)) {
                String texto = intent.getStringExtra(Intent.EXTRA_TEXT);
                usuario.setText(texto);
            }
        }
    }

    private void showPasswordConfirm() {
        final Dialog dialogo = new Dialog(mContext);
        dialogo.setContentView(R.layout.layout_password_confirm);


        final EditText nuevoPassword = dialogo.findViewById(R.id.password);
        Button confirm = dialogo.findViewById(R.id.confirmarButton);

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password1 = password.getText().toString();
                String password2 = nuevoPassword.getText().toString();

                if (password1.equals(password2)) {
                    dialogo.dismiss();
                } else {
                    dialogo.dismiss();
                    nuevoPassword.setError("No coinciden");
                }
            }
        });
        dialogo.setCancelable(false);
        dialogo.show();
    }

}
