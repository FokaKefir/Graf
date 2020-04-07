package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class App implements ActionListener {

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
    private JButton btnAddPoint;
    private JButton btnAddConnection;


    private Graph graph;

    // endregion

    // region 2. Constructor

    public App() {
        this.graph = new Graph();

        this.btnPreorder = new JButton("Preorder");
        this.btnInorder = new JButton("Inorder");
        this.btnPostorder = new JButton("Postorder");
        this.btnAddPoint = new JButton("Add new point");
        this.btnAddConnection = new JButton("Add new connection");

        this.btnPreorder.addActionListener(this);
        this.btnInorder.addActionListener(this);
        this.btnPostorder.addActionListener(this);
        this.btnAddPoint.addActionListener(this);
        this.btnAddConnection.addActionListener(this);

        this.buttonPanel = new JPanel();
        this.buttonPanel.add(this.btnPreorder);
        this.buttonPanel.add(this.btnInorder);
        this.buttonPanel.add(this.btnPostorder);
        this.buttonPanel.add(this.btnAddPoint);
        this.buttonPanel.add(this.btnAddConnection);


        this.mainPanel = new JPanel();
        this.mainPanel.setLayout(new BorderLayout());
        this.mainPanel.add(this.graph, BorderLayout.CENTER);
        this.mainPanel.add(this.buttonPanel, BorderLayout.NORTH);

        this.frame = new JFrame("App");
        this.frame.setContentPane(this.mainPanel);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setResizable(false);
        this.frame.setSize(new Dimension(WIDTH, HEIGHT));
        this.frame.setVisible(true);

    }

    // endregion

    // region 3. Main method

    public static void main(String[] args) {
        new App() ;
    }

    // endregion

    // region 4. Listener

    @Override
    public void actionPerformed(ActionEvent event) {
        Object source = event.getSource();
        if (btnPreorder.equals(source)) {
            System.out.println("preorder");
        } else if (btnInorder.equals(source)) {
            System.out.println("inorder");
        } else if (btnPostorder.equals(source)) {
            System.out.println("postorder");
        } else if (btnAddPoint.equals(source)) {
            this.graph.redrawImage();
            this.graph.setBlnCanConnect(false);
            this.graph.setBlnCanDrawPoint(true);
        } else if (btnAddConnection.equals(source)) {
            this.graph.redrawImage();
            this.graph.clearIndexes();
            this.graph.setBlnCanDrawPoint(false);
            this.graph.setBlnCanConnect(true);
        }

    }
    // endregion

}
