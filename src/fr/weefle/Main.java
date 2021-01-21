package fr.weefle;

import java.awt.EventQueue;
import javax.swing.JFrame;

public class Main extends JFrame {

    public static AudioPlayer player;

    public Main() {

        initUI();
    }

    private void initUI() {

        add(new Board());

        setResizable(false);
        pack();

        String audioFilePath = "src/resources/game_theme.wav";
        player = new AudioPlayer(audioFilePath);
        player.loop();
        setTitle("SpaceConquest");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {

        EventQueue.invokeLater(() -> {
            Main ex = new Main();
            ex.setVisible(true);
        });
    }
}