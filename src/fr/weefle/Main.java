package fr.weefle;

import java.awt.EventQueue;
import javax.swing.*;

public class Main extends JFrame {

    public Main() {

        initUI();

    }

    private void initUI() {

        add(new Board());

        setExtendedState(JFrame.MAXIMIZED_BOTH);

        setResizable(false);
        pack();

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