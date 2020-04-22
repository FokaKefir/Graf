package com.company.gui;

import com.company.graphs.Graph;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;

public class App implements ActionListener {

    // region 0. Constants

    private static final int WIDTH = 1200;
    private static final int HEIGHT = 800;

    private static final String MAIN_INFO = "Add the points and the connections, then choose the algorithm type!";

    // endregion

    // region 1. Init Widgets

    private JFrame frame;
    private JPanel contentPane;

    private JPanel mainPanelMenu;
    private JButton btnGraph;
    private JButton btnBinaryTree;

    private JPanel mainPanelGraph;
    private JTextField txtField;
    private JButton btnBreadthFirstSearch;
    private JButton btnDepthFirstSearch;
    private JButton btnDijkstra;
    private JButton btnKruskal;
    private JButton btnAddPoint;
    private JButton btnAddConnection;
    private JButton btnNextStep;
    private Graph graph;

    private JPanel mainPanelBinaryTree;

    // endregion

    // region 2. Constructor

    private void initMenu(){
        this.btnGraph = new JButton("Graph");
        this.btnBinaryTree = new JButton("Binary Tree");

        this.btnGraph.addActionListener(this);
        this.btnBinaryTree.addActionListener(this);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.add(this.btnGraph);
        buttonPanel.add(this.btnBinaryTree);

        ImageIcon image = new ImageIcon(getClass().getResource("images/graph.png"));

        this.mainPanelMenu = new JPanel();
        this.mainPanelMenu.setLayout(new BorderLayout());
        this.mainPanelMenu.setBackground(Color.WHITE);
        this.mainPanelMenu.add(new JLabel(image), BorderLayout.CENTER);
        this.mainPanelMenu.add(buttonPanel, BorderLayout.SOUTH);
    }

    private void initGraph(){
        this.graph = new Graph();

        this.btnBreadthFirstSearch = new JButton("Breadth-First Search");
        this.btnDepthFirstSearch = new JButton("Depth-First Search");
        this.btnDijkstra = new JButton("Dijkstra");
        this.btnKruskal = new JButton("Kruskal");
        this.btnAddPoint = new JButton("Add new point");
        this.btnAddConnection = new JButton("Add new connection");
        this.btnNextStep = new JButton("Next");

        this.btnBreadthFirstSearch.addActionListener(this);
        this.btnDepthFirstSearch.addActionListener(this);
        this.btnDijkstra.addActionListener(this);
        this.btnKruskal.addActionListener(this);
        this.btnAddPoint.addActionListener(this);
        this.btnAddConnection.addActionListener(this);
        this.btnNextStep.addActionListener(this);

        setVisibleButtons(true);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(this.btnBreadthFirstSearch);
        buttonPanel.add(this.btnDepthFirstSearch);
        buttonPanel.add(this.btnDijkstra);
        buttonPanel.add(this.btnKruskal);
        buttonPanel.add(this.btnAddPoint);
        buttonPanel.add(this.btnAddConnection);
        buttonPanel.add(this.btnNextStep);

        this.txtField = new JTextField(5);
        this.txtField.setFont(new Font("Arial", Font.BOLD, 20));
        this.txtField.setText(MAIN_INFO);
        this.txtField.setEnabled(false);
        this.txtField.setBorder(new LineBorder(Color.WHITE));
        this.txtField.setDisabledTextColor(Color.BLACK);
        this.txtField.setHorizontalAlignment(JTextField.CENTER);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(buttonPanel, BorderLayout.NORTH);
        panel.add(Box.createVerticalStrut(15), BorderLayout.CENTER);
        panel.add(this.txtField, BorderLayout.SOUTH);

        this.mainPanelGraph = new JPanel();
        this.mainPanelGraph.setLayout(new BorderLayout());
        this.mainPanelGraph.add(this.graph, BorderLayout.CENTER);
        this.mainPanelGraph.add(panel, BorderLayout.NORTH);
    }

    private void initBinaryTree(){

    }

    public App() {
        initMenu();
        initGraph();
        initBinaryTree();

        this.frame = new JFrame("App");
        this.frame.setContentPane(this.mainPanelMenu);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setResizable(false);
        this.frame.setSize(new Dimension(WIDTH, HEIGHT));
        this.frame.setVisible(true);

        this.contentPane = (JPanel) this.frame.getContentPane();

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

        if (btnGraph.equals(source)){
            switchPane(this.mainPanelGraph);
        }else if(btnBinaryTree.equals(source)){
            switchPane(this.mainPanelBinaryTree);
        }

        if(btnBreadthFirstSearch.equals(source)){
            this.graph.setBlnCanConnect(false);
            this.graph.setBlnCanDrawPoint(false);
            this.graph.setAlgorithmType(Graph.BREADTH_FIRST_SEARCH);
            this.txtField.setText("Choose the first point!");
            setVisibleButtons(false);
        }else if(btnDepthFirstSearch.equals(source)){
            this.graph.setBlnCanConnect(false);
            this.graph.setBlnCanDrawPoint(false);
            this.graph.setAlgorithmType(Graph.DEPTH_FIRST_SEARCH);
            this.txtField.setText("Choose the first point!");
            setVisibleButtons(false);
        }else if(btnDijkstra.equals(source)){
            this.graph.setBlnCanConnect(false);
            this.graph.setBlnCanDrawPoint(false);
            this.graph.setAlgorithmType(Graph.DIJKSTRA);
            this.txtField.setText("Choose the first point!");
            setVisibleButtons(false);
        }else if(btnKruskal.equals(source)){
            this.graph.setBlnCanConnect(false);
            this.graph.setBlnCanDrawPoint(false);
            this.graph.setAlgorithmType(Graph.KRUSKAL);
            this.graph.kruskal();
            this.txtField.setText("Sorting the connection list!");
            setVisibleButtons(false);
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
        } else if (btnNextStep.equals(source)){
            if(this.graph.getAlgorithmRunning()) {
                this.graph.nextStep();
                this.txtField.setText(this.graph.getStrText());
            } else {
                setVisibleButtons(true);
                this.graph.redrawImage();
                this.txtField.setText(MAIN_INFO);
            }
        }

    }

    // endregion

    // region 5. Functions and methods

    private void switchPane(JPanel newPanel){
        this.contentPane.removeAll();
        this.contentPane.add(newPanel);
        this.contentPane.revalidate();
        this.contentPane.repaint();
    }

    private void setVisibleButtons(boolean cond){
        this.btnBreadthFirstSearch.setVisible(cond);
        this.btnDepthFirstSearch.setVisible(cond);
        this.btnDijkstra.setVisible(cond);
        this.btnKruskal.setVisible(cond);
        this.btnAddPoint.setVisible(cond);
        this.btnAddConnection.setVisible(cond);
        this.btnNextStep.setVisible(!cond);

    }

    // endregion

}
