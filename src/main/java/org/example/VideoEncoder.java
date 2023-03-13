package org.example;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class VideoEncoder {
    public static void encode() throws IOException, InterruptedException {
                // Specify the camera device and its settings
                String cameraDevice = "/dev/video0";
                int width = 640;
                int height = 480;
                int framerate = 30;

                // FFmpeg command to capture and encode video stream from camera
                String[] ffmpegCmd = {
                        "ffmpeg", "-f", "v4l2", "-input_format", "mjpeg", "-s",
                        width + "x" + height, "-r", String.valueOf(framerate), "-i",
                        cameraDevice, "-c:v", "libx264", "-preset", "ultrafast", "-tune",
                        "zerolatency", "-f", "mpegts", "-"
                };

                // Start FFmpeg process to encode video stream
                Process ffmpeg = new ProcessBuilder(ffmpegCmd).start();

                // Create a new thread to read and display the decoded video stream
                new Thread(() -> {
                    try {
                        // FFmpeg command to decode and display video stream
                        String[] ffplayCmd = {"ffplay", "-i", "-"};
                        Process ffplay = new ProcessBuilder(ffplayCmd).start();

                        // Connect FFmpeg's output stream to FFplay's input stream
                        InputStream ffmpegOutput = ffmpeg.getInputStream();
                        OutputStream ffplayInput = ffplay.getOutputStream();
                        byte[] buffer = new byte[4096];
                        int bytesRead;
                        while ((bytesRead = ffmpegOutput.read(buffer)) != -1) {
                            ffplayInput.write(buffer, 0, bytesRead);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }).start();

                // Wait for FFmpeg process to exit
                try {
                    ffmpeg.waitFor();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        
