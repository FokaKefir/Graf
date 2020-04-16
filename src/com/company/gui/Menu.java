package com.company.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Menu implements ActionListener {

    // region 0. Constants

    private static final int WIDTH = 1200;
    private static final int HEIGHT = 500;

    private static final int BTN_WIDTH = 500;
    private static final int BTN_HEIGHT = 500;

    // endregion

    // region 1. Init Widgets

    private JFrame frame;

    private JPanel mainPanel;

    private JButton btnGraph;
    private JButton btnBinaryTree;

    private ImageIcon image;

    // endregion

    // region 2. Constructor

    public Menu(){
        this.btnGraph = new JButton("Graph");
        this.btnBinaryTree = new JButton("Binary Tree");

        this.btnGraph.addActionListener(this);
        this.btnBinaryTree.addActionListener(this);

        this.btnGraph.setSize(new Dimension(BTN_WIDTH, BTN_HEIGHT));
        this.btnBinaryTree.setSize(new Dimension(BTN_WIDTH, BTN_HEIGHT));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.add(this.btnGraph);
        buttonPanel.add(this.btnBinaryTree);

        this.image = new ImageIcon(getClass().getResource("images/graph.png"));

        this.mainPanel = new JPanel();
        this.mainPanel.setLayout(new BorderLayout());
        this.mainPanel.setBackground(Color.WHITE);
        this.mainPanel.add(new JLabel(this.image), BorderLayout.CENTER);
        this.mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        this.frame = new JFrame("App");
        this.frame.setContentPane(this.mainPanel);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setResizable(false);
        this.frame.setSize(new Dimension(WIDTH, HEIGHT));
        this.frame.setBackground(Color.WHITE);
        this.frame.setVisible(true);

    }

    // endregion

    // region 3. Main method

    public static void main(String[] args) {
        new Menu();
    }

    // endregion

    // region 4. Listener

    @Override
    public void actionPerformed(ActionEvent event) {
        Object source = event.getSource();

        if(this.btnGraph.equals(source)){

        }else if(this.btnBinaryTree.equals(source)){

        }
    }


    // endregion


}
