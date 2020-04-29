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

    private static final int IMAGE_WIDTH = 50;
    private static final int IMAGE_HEIGHT = 50;

    private static final int BUTTON_WEIGHT = 200;
    private static final int BUTTON_HEIGHT = 50;

    private static final String MAIN_INFO = "Add the points and the connections, then choose the algorithm type!";

    // endregion

    // region 1. Init Widgets

    private JFrame frame;

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
    private JButton btnDelete;
    private JButton btnBack;
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


        this.btnAddPoint = new JButton("Add new point");
        this.btnAddConnection = new JButton("Add new connection");
        this.btnNextStep = new JButton("Next");
        this.btnDelete = new JButton("Delete");

        this.btnBreadthFirstSearch = new JButton("Breadth-First Search"){
            {
                setSize(BUTTON_WEIGHT, BUTTON_HEIGHT);
                setMaximumSize(getSize());
            }
        };
        this.btnDepthFirstSearch = new JButton("Depth-First Search"){
            {
                setSize(BUTTON_WEIGHT, BUTTON_HEIGHT);
                setMaximumSize(getSize());
            }
        };
        this.btnDijkstra = new JButton("Dijkstra") {
            {
                setSize(BUTTON_WEIGHT, BUTTON_HEIGHT);
                setMaximumSize(getSize());
            }
        };
        this.btnKruskal = new JButton("Kruskal") {
            {
                setSize(BUTTON_WEIGHT, BUTTON_HEIGHT);
                setMaximumSize(getSize());
            }
        };
        this.btnBack = new JButton(){
            {
                setSize(IMAGE_WIDTH, IMAGE_HEIGHT);
                setMaximumSize(getSize());
            }
        };

        ImageIcon img = new ImageIcon(getClass().getResource("images/back.png"));
        img = new ImageIcon(img.getImage().getScaledInstance(IMAGE_WIDTH, IMAGE_HEIGHT,  java.awt.Image.SCALE_SMOOTH));
        this.btnBack.setIcon(img);

        this.btnBreadthFirstSearch.addActionListener(this);
        this.btnDepthFirstSearch.addActionListener(this);
        this.btnDijkstra.addActionListener(this);
        this.btnKruskal.addActionListener(this);
        this.btnAddPoint.addActionListener(this);
        this.btnAddConnection.addActionListener(this);
        this.btnNextStep.addActionListener(this);
        this.btnDelete.addActionListener(this);
        this.btnBack.addActionListener(this);

        setVisibleButtons(true);

        this.txtField = new JTextField(5);
        this.txtField.setFont(new Font("Arial", Font.BOLD, 20));
        this.txtField.setText(MAIN_INFO);
        this.txtField.setEnabled(false);
        this.txtField.setBorder(new LineBorder(Color.WHITE));
        this.txtField.setDisabledTextColor(Color.BLACK);
        this.txtField.setHorizontalAlignment(JTextField.CENTER);

        JPanel editButtonPanel = new JPanel();
        editButtonPanel.add(this.btnAddPoint);
        editButtonPanel.add(this.btnAddConnection);
        editButtonPanel.add(this.btnNextStep);
        editButtonPanel.add(this.btnDelete);

        JPanel programsButtonPanel = new JPanel();
        programsButtonPanel.setLayout(new BoxLayout(programsButtonPanel, BoxLayout.Y_AXIS));
        programsButtonPanel.add(this.btnBack);
        programsButtonPanel.add(Box.createRigidArea(new Dimension(0, 50)));
        programsButtonPanel.add(this.btnBreadthFirstSearch);
        programsButtonPanel.add(this.btnDepthFirstSearch);
        programsButtonPanel.add(this.btnDijkstra);
        programsButtonPanel.add(this.btnKruskal);


        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(editButtonPanel, BorderLayout.NORTH);
        panel.add(this.txtField, BorderLayout.SOUTH);

        JPanel editorPanel = new JPanel();
        editorPanel.setLayout(new BorderLayout());
        editorPanel.add(this.graph, BorderLayout.CENTER);
        editorPanel.add(panel, BorderLayout.NORTH);

        this.mainPanelGraph = new JPanel();
        this.mainPanelGraph.setLayout(new BorderLayout());
        this.mainPanelGraph.add(editorPanel, BorderLayout.CENTER);
        this.mainPanelGraph.add(programsButtonPanel, BorderLayout.WEST);
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
            setNewPanel(this.mainPanelGraph);
        }else if(btnBinaryTree.equals(source)){
            setNewPanel(this.mainPanelBinaryTree);
        }

        if(btnBreadthFirstSearch.equals(source)){
            this.graph.redrawImage();
            this.graph.setBlnCanConnect(false);
            this.graph.setBlnCanDrawPoint(false);
            this.graph.setAlgorithmType(Graph.BREADTH_FIRST_SEARCH);
            this.txtField.setText("Choose the first point!");
            setVisibleButtons(false);
        }else if(btnDepthFirstSearch.equals(source)){
            this.graph.redrawImage();
            this.graph.setBlnCanConnect(false);
            this.graph.setBlnCanDrawPoint(false);
            this.graph.setAlgorithmType(Graph.DEPTH_FIRST_SEARCH);
            this.txtField.setText("Choose the first point!");
            setVisibleButtons(false);
        }else if(btnDijkstra.equals(source)){
            this.graph.redrawImage();
            this.graph.setBlnCanConnect(false);
            this.graph.setBlnCanDrawPoint(false);
            this.graph.setAlgorithmType(Graph.DIJKSTRA);
            this.txtField.setText("Choose the first point!");
            setVisibleButtons(false);
        }else if(btnKruskal.equals(source)){
            this.graph.redrawImage();
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
            this.graph.setBlnCanDelete(false);
            this.graph.setAlgorithmType(Graph.NOT_TYPE);
        } else if (btnAddConnection.equals(source)) {
            this.graph.redrawImage();
            this.graph.clearIndexes();
            this.graph.setBlnCanDrawPoint(false);
            this.graph.setBlnCanConnect(true);
            this.graph.setBlnCanDelete(false);
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
        } else if (btnDelete.equals(source)){
            this.graph.redrawImage();
            this.graph.clearIndexes();
            this.graph.setBlnCanDrawPoint(false);
            this.graph.setBlnCanConnect(false);
            this.graph.setBlnCanDelete(true);
            this.graph.setAlgorithmType(Graph.NOT_TYPE);
        } else if (btnBack.equals(source)){
            setNewPanel(this.mainPanelMenu);
        }

    }

    // endregion

    // region 5. Functions and methods

    private void setNewPanel(JPanel newPanel){
        this.frame.setContentPane(newPanel);
        this.frame.validate();
    }

    private void setVisibleButtons(boolean cond){
        this.btnAddPoint.setVisible(cond);
        this.btnAddConnection.setVisible(cond);
        this.btnDelete.setVisible(cond);
        this.btnNextStep.setVisible(!cond);

    }

    // endregion

}
