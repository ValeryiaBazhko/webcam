package org.example;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamResolution;


public class WebcamHandler extends Thread  {


    @Override
    public void run() {


        Webcam webcam = Webcam.getDefault();
        webcam.setViewSize(WebcamResolution.VGA.getSize());
        Queue.panelQueue.add(webcam);

    }
}