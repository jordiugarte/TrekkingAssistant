package com.galacticCat.chatbleu;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.galacticCat.chatbleu.data.UserData;
import com.galacticCat.chatbleu.db.DataBaseHelper;
import com.galacticCat.chatbleu.model.User;
import com.google.gson.Gson;

import butterknife.ButterKnife;
import butterknife.BindView;

public class SignupActivity extends AppCompatActivity {
    private static final String TAG = "SignupActivity";

    private Context mContext = this;
    private MainActivity main;

    @BindView(R.id.input_name) EditText _nameText;
    @BindView(R.id.input_edad) EditText _edadText;
    @BindView(R.id.input_peso) EditText _pesoText;
    @BindView(R.id.btn_signup) Button _signupButton;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.bind(this);

        _signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });


    }

    public void signup() {
        Log.d(TAG, "Signup");

        if (!validate()) {
            onSignupFailed();
            return;
        }

        _signupButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(SignupActivity.this,
                R.style.AppTheme);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating Account...");
        progressDialog.show();

        final String name = _nameText.getText().toString();
        final int edad = Integer.parseInt(_edadText.getText().toString());
        final int peso = Integer.parseInt(_pesoText.getText().toString());

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onSignupSuccess or onSignupFailed
                        // depending on success
                        onSignupSuccess(name, edad, peso);
                        // onSignupFailed();
                        progressDialog.dismiss();
                    }
                }, 3000);
    }


    public void onSignupSuccess(String name, int edad, int peso) {
        _signupButton.setEnabled(true);
        setResult(RESULT_OK, null);
        llenarPersonalData(name, edad, peso);
        finish();
    }

    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "SignUp failed", Toast.LENGTH_LONG).show();

        _signupButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String name = _nameText.getText().toString();
        int edad = Integer.parseInt(_edadText.getText().toString());
        int peso = Integer.parseInt(_pesoText.getText().toString());

        if (name.isEmpty() || name.length() < 3) {
            _nameText.setError("at least 3 characters");
            valid = false;
        } else {
            _nameText.setError(null);
        }

        if (edad < 0 || edad > 100) {
            _edadText.setError("enter a valid age");
            valid = false;
        } else {
            _edadText.setError(null);
        }
        if (peso < 1 || peso > 120 ) {
            _pesoText.setError("enter a valid weight");
            valid = false;
        } else {
            _pesoText.setError(null);
        }
        User usuario = new User();
        usuario.setNombreUsuario(name);
        usuario.setEdad(edad);
        usuario.setPeso(peso);

        main = new MainActivity();

        //Antes de devolverlo, lo guardamos en la db
        DataBaseHelper dbHelper = new DataBaseHelper(mContext);
        dbHelper.insert(usuario);

        String json = new Gson().toJson(usuario);
        Log.e("UsuarioEnviado", json);




        Intent intent = new Intent();
        intent.putExtra(Constants.KEY_REGISTRAR_USUARIO, json);
        setResult(RESULT_OK, intent); //OK: funciono, intent --> retornando el valor
       // finish(); //Cierra el activity


        return valid;
    }
    private void llenarPersonalData(String name, int edad, int peso) {
        UserData userData = new UserData(getApplicationContext());
//        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mContext);
//        SharedPreferences.Editor editor = prefs.edit();
//        editor.putString(Constants.PREF_USUARIO, name);
//        editor.putInt(Constants.PREF_AGE, edad);
//        editor.putInt(Constants.PREF_WEIGTH, peso);
//        editor.apply();
        userData.setUserName(name);
        userData.setAge(edad);
        userData.setWeight(peso);
        userData.saveData();
    }
}
