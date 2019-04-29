package com.galacticCat.chatbleu.services;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.galacticCat.chatbleu.R;

import org.w3c.dom.Text;

public class Notification {

    public Notification(Context context, String message, int icon) {
        Toast toast = new Toast(context);
        View view = LayoutInflater.from(context).inflate(R.layout.toast, null);

        TextView toastTextView = view.findViewById(R.id.notificationView);
        ImageView toastIconView = view.findViewById(R.id.notificationIcon);

        toastTextView.setText(message);
        toastIconView.setImageResource(icon);

        toast.setView(view);
        toast.setDuration(Toast.LENGTH_LONG);

        toast.setGravity(Gravity.CENTER, 32, 32);
        toast.show();
    }
}
