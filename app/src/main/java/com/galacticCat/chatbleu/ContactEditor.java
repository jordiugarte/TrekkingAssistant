package com.galacticCat.chatbleu;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

public class ContactEditor extends AppCompatDialogFragment {
    private EditText editName;
    private EditText editNumber;
    private ExampleDialogListener listener;
    EditText nombre;
    EditText numero;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_editor, null);
        nombre = view.findViewById(R.id.edit_nombre);
        numero = view.findViewById(R.id.edit_numero);

        Context context = getContext();
        SharedPreferences sharedPreferences = context.getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        nombre.setText(sharedPreferences.getString("ANAME", ""));
        numero.setText(sharedPreferences.getString("ANUMBER", ""));

        nombre.getText().toString();
        builder.setView(view)
                .setTitle(getResources().getString(R.string.edit_contact))
                .setNegativeButton(getResources().getString(R.string.exit_save), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String username = editName.getText().toString();
                        String password = editNumber.getText().toString();
                        listener.applyTexts(username, password);
                    }
                })
                .setPositiveButton(getResources().getString(R.string.call), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        startActivity(new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", numero.getText().toString(), null)));
                    }
                });
        ;

        editName = view.findViewById(R.id.edit_nombre);
        editNumber = view.findViewById(R.id.edit_numero);

        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            listener = (ExampleDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    "must implement ExampleDialogListener");
        }
    }

    public interface ExampleDialogListener {
        void applyTexts(String nombre, String numero);
    }
}
