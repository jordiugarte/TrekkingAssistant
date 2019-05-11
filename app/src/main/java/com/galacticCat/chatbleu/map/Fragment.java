package com.galacticCat.chatbleu.map;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.widget.ImageView;

public class Fragment extends DialogFragment {
    public static  final String Argument_Title = "Titulo";
    public static  final String Argument_Full_Snippet = "Full Snippet";

    private  String title;
    private String fullSnippet;

    public static  Fragment newInstance(String title, String fullSnippet){
        Fragment fragment = new Fragment();
        Bundle b = new Bundle();
        b.putString(Argument_Title, title);
        b.putString(Argument_Full_Snippet, fullSnippet);
        fragment.setArguments(b);
        return fragment;
    }
    @Override
    public void onCreate (Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        Bundle args = getArguments();

        title = args.getString(Argument_Title);
        fullSnippet = args.getString(Argument_Full_Snippet);

    }

    @Override
    public Dialog  onCreateDialog(Bundle saveInstanceSatate){
        Dialog dialog = new AlertDialog.Builder(getActivity())
                .setTitle(title)
                .setMessage(fullSnippet)
                .create();
        return  dialog;
    }



}
