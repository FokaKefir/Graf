package com.company;
import javax.swing.*;
import java.awt.*;


public class App {

    // region 0. Constants

    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;

    // endregion

    // region 1. Init Widgets

    private JPanel mainPanel;
    private JButton btnPreorder;
    private JButton btnInorder;
    private JButton btnPostorder;

    private AppListener listener;

    // endregion

    // region 2. Constructor

    public App() {
        this.listener = new AppListener();

        this.btnPreorder.addActionListener(this.listener);
        this.btnInorder.addActionListener(this.listener);
        this.btnPostorder.addActionListener(this.listener);
    }

    // endregion

    // region 3. Main method

    public static void main(String[] args) {
        JFrame frame = new JFrame("App");
        frame.setContentPane(new App().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(new Dimension(WIDTH, HEIGHT));

        frame.setVisible(true);

    }

    // endregion

    // region 4. Getters and Setters

    // endregion


}
