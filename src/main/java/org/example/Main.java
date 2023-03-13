package org.example;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamResolution;
import org.bytedeco.javacv.FFmpegFrameRecorder;
import org.bytedeco.javacv.FrameGrabber;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) throws InterruptedException, IOException {
        ExecutorService service = Executors.newCachedThreadPool();

        WebcamHandler webcamHandler = new WebcamHandler();
        service.submit(webcamHandler);

        VideoEncoder.encode();
        MainFrame.init();

    }
}