package org.example;
import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import jmapps.ui.VideoPanel;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class MainFrame {

    private static JFrame frame;
    private static JPanel videoPanel;
    private static JPanel controlsPanel;
    private static JPanel infoPanel;
    private static JButton matchBtn;

    private static VideoOutput videoOutput;


    public static void init() throws InterruptedException, IOException {
        frame = new JFrame("ChatBox Client");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setVisible(true);
        frame.setSize(new Dimension(900, 600));
        frame.setLayout(new BorderLayout());

        videoPanel = new JPanel();
        videoPanel.setPreferredSize(new Dimension(640, 480));

        controlsPanel = new JPanel();
        controlsPanel.setLayout(new FlowLayout());
        controlsPanel.setPreferredSize(new Dimension(200, 480));
        controlsPanel.setBackground(Color.GREEN);

        infoPanel = new JPanel();
        infoPanel.setPreferredSize(new Dimension(640, 120));

        Webcam webcam = Queue.panelQueue.take();
        WebcamPanel panel = new WebcamPanel(webcam);
        panel.setMirrored(true);
        panel.setPreferredSize(new Dimension(160, 120));
        controlsPanel.add(panel);

        videoOutput = new VideoOutput();
        videoOutput.setPreferredSize(new Dimension(640,480));
        videoPanel.add(videoOutput);

        VideoEncoder.encode();



        matchBtn = new JButton("Match");
        matchBtn.setPreferredSize(new Dimension(160, 40));
        controlsPanel.add(matchBtn);

        frame.add(infoPanel, BorderLayout.SOUTH);
        frame.add(controlsPanel, BorderLayout.EAST);
        frame.add(videoPanel, BorderLayout.CENTER);
        frame.pack();
    }
}