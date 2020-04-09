package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class App implements ActionListener {

    // region 0. Constants

    private static final int WIDTH = 1200;
    private static final int HEIGHT = 800;

    // endregion

    // region 1. Init Widgets

    private JFrame frame;
    private JPanel mainPanel;

    private JPanel buttonPanel;

    private JButton btnBreadthFirstSearch;
    private JButton btnDepthFirstSearch;
    private JButton btnDijkstra;
    private JButton btnKruskal;
    private JButton btnAddPoint;
    private JButton btnAddConnection;

    private Graph graph;

    // endregion

    // region 2. Constructor

    public App() {
        this.graph = new Graph();

        this.btnBreadthFirstSearch = new JButton("Breadth-First Search");
        this.btnDepthFirstSearch = new JButton("Depth-First Search");
        this.btnDijkstra = new JButton("Dijkstra");
        this.btnKruskal = new JButton("Kruskal");
        this.btnAddPoint = new JButton("Add new point");
        this.btnAddConnection = new JButton("Add new connection");

        this.btnBreadthFirstSearch.addActionListener(this);
        this.btnDepthFirstSearch.addActionListener(this);
        this.btnDijkstra.addActionListener(this);
        this.btnKruskal.addActionListener(this);
        this.btnAddPoint.addActionListener(this);
        this.btnAddConnection.addActionListener(this);

        this.buttonPanel = new JPanel();

        this.buttonPanel.add(this.btnBreadthFirstSearch);
        this.buttonPanel.add(this.btnDepthFirstSearch);
        this.buttonPanel.add(this.btnDijkstra);
        this.buttonPanel.add(this.btnKruskal);
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

        if(btnBreadthFirstSearch.equals(source)){
            this.graph.setBlnCanConnect(false);
            this.graph.setBlnCanDrawPoint(false);
            this.graph.setAlgorithmType(Graph.BREADTH_FIRST_SEARCH);
        }else if(btnDepthFirstSearch.equals(source)){
            this.graph.setBlnCanConnect(false);
            this.graph.setBlnCanDrawPoint(false);
            this.graph.setAlgorithmType(Graph.DEPTH_FIRST_SEARCH);
        }else if(btnDijkstra.equals(source)){
            this.graph.setBlnCanConnect(false);
            this.graph.setBlnCanDrawPoint(false);
            this.graph.setAlgorithmType(Graph.DIJKSTRA);
        }else if(btnKruskal.equals(source)){
            this.graph.setBlnCanConnect(false);
            this.graph.setBlnCanDrawPoint(false);
            this.graph.kruskal();
        }else if (btnAddPoint.equals(source)) {
            this.graph.redrawImage();
            this.graph.setBlnCanConnect(false);
            this.graph.setBlnCanDrawPoint(true);
            this.graph.setAlgorithmType(Graph.NOT_TYPE);
        } else if (btnAddConnection.equals(source)) {
            this.graph.redrawImage();
            this.graph.clearIndexes();
            this.graph.setBlnCanDrawPoint(false);
            this.graph.setBlnCanConnect(true);
            this.graph.setAlgorithmType(Graph.NOT_TYPE);
        }

    }

    // endregion

}
