package com.galacticCat.chatbleu.tools;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.support.v4.app.ActivityCompat;

import static android.content.Context.CAMERA_SERVICE;

public class SOSFlashlight {
    public static SOSFlashlight instance = new SOSFlashlight();

    private boolean active;
    private Thread t;

    public SOSFlashlight() {
    }

    public static SOSFlashlight getInstance(){
        return instance;
    }

    public void flashLight(final Activity activity, Context context) {
        active = true;
        ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CAMERA}, 60);
        final CameraManager cameraManager = (CameraManager) context.getSystemService(CAMERA_SERVICE);
            t = new Thread() {
                @Override
                public void run() {
                    try {
                        while (active) {
                            Thread.sleep(100);
                            activity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    String cameraID = null;
                                    try {
                                        cameraID = cameraManager.getCameraIdList()[0];
                                    } catch (CameraAccessException e) {
                                        e.printStackTrace();
                                    }
                                    try {
                                        cameraManager.setTorchMode(cameraID, true);
                                        Thread.sleep(100);
                                        cameraManager.setTorchMode(cameraID, false);
                                        Thread.sleep(100);
                                    } catch (CameraAccessException e) {
                                        e.printStackTrace();
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };
            t.start();
    }

    public void stopFlashLight(){
        active = false;
        if(t!=null){
            //t.stop();
            t.interrupt();
        }
    }
}
