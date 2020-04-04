package com.company;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;


public class App {

    // region 0. Constants

    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;

    // endregion

    // region 1. Init Widgets

    private JFrame frame;
    private JPanel mainPanel;

    private JPanel buttonPanel;
    private JButton btnPreorder;
    private JButton btnInorder;
    private JButton btnPostorder;

    private AppListener listener;

    private DrawArea drawArea;

    // endregion

    // region 2. Constructor

    public App() {
        this.mainPanel.setLayout(new BorderLayout());

        this.drawArea = new DrawArea();
        this.mainPanel.add(this.drawArea, BorderLayout.CENTER);

        this.buttonPanel = new JPanel();

        this.listener = new AppListener();
        this.btnPreorder.addActionListener(this.listener);
        this.btnInorder.addActionListener(this.listener);
        this.btnPostorder.addActionListener(this.listener);

        this.buttonPanel.add(this.btnPreorder);
        this.buttonPanel.add(this.btnInorder);
        this.buttonPanel.add(this.btnPostorder);

        this.mainPanel.add(this.buttonPanel, BorderLayout.NORTH);

        this.frame = new JFrame("App");
        this.frame.setContentPane(this.mainPanel);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setSize(new Dimension(WIDTH, HEIGHT));
        this.frame.setVisible(true);

    }

    // endregion

    // region 3. Main method

    public static void main(String[] args) {
        new App();
    }

    // endregion

    // region 4. Getters and Setters

    // endregion


}
