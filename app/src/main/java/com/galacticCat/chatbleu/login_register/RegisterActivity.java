package com.galacticCat.chatbleu.login_register;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.galacticCat.chatbleu.R;

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
            public void onClick(View v) {
                validateForm();
            }

        });

    }
