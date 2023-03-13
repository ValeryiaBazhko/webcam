package org.example;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class VideoEncoder {
    public static void encode() throws IOException, InterruptedException {
        // Specify the camera device and its settings
        String cameraDevice = "video=USB2.0 HD UVC WebCam";
        int width = 1280;
        int height = 720;
        int framerate = 15;
        VideoOutput videoOutput = new VideoOutput(); // make sure this object is properly initialized

        // FFmpeg command to capture and encode video stream from camera
        String[] ffmpegCmd = {
                "ffmpeg", "-f", "dshow", "-i", cameraDevice, "-video_size",
                width + "x" + height, "-framerate", String.valueOf(framerate), "-c:v",
                "libx264", "-preset", "ultrafast", "-tune", "zerolatency", "-f",
                "mpegts", "-"
        };

        // Start FFmpeg process to encode video stream
        Process ffmpeg = new ProcessBuilder(ffmpegCmd).start();

        // Create a new thread to read and display the decoded video stream
        new Thread(() -> {
            try {
                // FFmpeg command to decode and display video stream
                InputStream ffmpegOutput = ffmpeg.getInputStream();
                byte[] buffer = new byte[4096];

                while (true) {
                    int bytesRead = ffmpegOutput.read(buffer);
                    if (bytesRead < 0) {
                        break;
                    }

                    System.out.println("Bytes read from ffmpegOutput: " + bytesRead);

                    /*try (FileOutputStream fos = new FileOutputStream("image.jpg")) {
                        fos.write(buffer, 0, bytesRead);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }*/

                    // Convert the byte array to a BufferedImage
                    ByteArrayInputStream inputStream = new ByteArrayInputStream(buffer, 0, bytesRead);
                    BufferedImage image = ImageIO.read(inputStream);

                    System.out.println("Image decoded: " + image);

                    // Update the video panel with the new image
                    if (image != null) {
                        System.out.println("New image received");
                        videoOutput.setImage(image); // make sure this method is functioning correctly
                    }
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

        
