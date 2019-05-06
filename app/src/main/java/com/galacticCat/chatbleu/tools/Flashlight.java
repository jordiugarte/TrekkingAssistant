package com.galacticCat.chatbleu.tools;

import android.app.Activity;
import android.content.Context;
import android.Manifest;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.support.v4.app.ActivityCompat;

import static android.content.Context.CAMERA_SERVICE;

public class Flashlight {

    public Flashlight(Activity activity, Context context, boolean active){
        ActivityCompat.requestPermissions(activity, new String[]{ Manifest.permission.CAMERA}, 60);
        final CameraManager cameraManager =  (CameraManager) context.getSystemService(CAMERA_SERVICE);
        try {
            String cameraID = cameraManager.getCameraIdList()[0];
            cameraManager.setTorchMode(cameraID, active);
        } catch (CameraAccessException e){
            e.printStackTrace();
        }
    }
}
