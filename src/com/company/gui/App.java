package com.company.gui;

import com.company.graphs.BinaryTree;
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
    private JTextField txtFieldGraph;
    private JButton btnBreadthFirstSearch;
    private JButton btnDepthFirstSearch;
    private JButton btnDijkstra;
    private JButton btnKruskal;
    private JButton btnAddPointGraph;
    private JButton btnAddConnectionGraph;
    private JButton btnNextStepGraph;
    private JButton btnDeleteGraph;
    private JButton btnBackGraph;
    private Graph graph;

    private JPanel mainPanelBinaryTree;
    private JTextField txtFieldBT;
    private JButton btnPreorder;
    private JButton btnInorder;
    private JButton btnPostOrder;
    private JButton btnAddPointBT;
    private JButton btnNextStepBT;
    private JButton btnDeleteBT;
    private JButton btnBackBT;
    private BinaryTree binaryTree;

    private ImageIcon img;

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

        this.btnAddPointGraph = new JButton("Add new point");
        this.btnAddConnectionGraph = new JButton("Add new connection");
        this.btnNextStepGraph = new JButton("Next");
        this.btnDeleteGraph = new JButton("Delete");
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
        this.btnBackGraph = new JButton(){
            {
                setSize(IMAGE_WIDTH, IMAGE_HEIGHT);
                setMaximumSize(getSize());
            }
        };

        this.btnBackGraph.setIcon(this.img);

        this.btnBreadthFirstSearch.addActionListener(this);
        this.btnDepthFirstSearch.addActionListener(this);
        this.btnDijkstra.addActionListener(this);
        this.btnKruskal.addActionListener(this);
        this.btnAddPointGraph.addActionListener(this);
        this.btnAddConnectionGraph.addActionListener(this);
        this.btnNextStepGraph.addActionListener(this);
        this.btnDeleteGraph.addActionListener(this);
        this.btnBackGraph.addActionListener(this);

        this.txtFieldGraph = new JTextField(5);
        this.txtFieldGraph.setFont(new Font("Arial", Font.BOLD, 20));
        this.txtFieldGraph.setText(MAIN_INFO);
        this.txtFieldGraph.setEnabled(false);
        this.txtFieldGraph.setBorder(new LineBorder(Color.WHITE));
        this.txtFieldGraph.setDisabledTextColor(Color.BLACK);
        this.txtFieldGraph.setHorizontalAlignment(JTextField.CENTER);

        JPanel editButtonPanel = new JPanel();
        editButtonPanel.add(this.btnAddPointGraph);
        editButtonPanel.add(this.btnAddConnectionGraph);
        editButtonPanel.add(this.btnNextStepGraph);
        editButtonPanel.add(this.btnDeleteGraph);

        JPanel programsButtonPanel = new JPanel();
        programsButtonPanel.setLayout(new BoxLayout(programsButtonPanel, BoxLayout.Y_AXIS));
        programsButtonPanel.add(this.btnBackGraph);
        programsButtonPanel.add(Box.createRigidArea(new Dimension(0, 50)));
        programsButtonPanel.add(this.btnBreadthFirstSearch);
        programsButtonPanel.add(this.btnDepthFirstSearch);
        programsButtonPanel.add(this.btnDijkstra);
        programsButtonPanel.add(this.btnKruskal);


        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(editButtonPanel, BorderLayout.NORTH);
        panel.add(this.txtFieldGraph, BorderLayout.SOUTH);

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
        this.binaryTree = new BinaryTree();

        this.btnAddPointBT = new JButton("Add new point");
        this.btnNextStepBT = new JButton("Next");
        this.btnDeleteBT = new JButton("Delete");
        this.btnPreorder = new JButton("Breadth-First Search"){
            {
                setSize(BUTTON_WEIGHT, BUTTON_HEIGHT);
                setMaximumSize(getSize());
            }
        };
        this.btnInorder = new JButton("Depth-First Search"){
            {
                setSize(BUTTON_WEIGHT, BUTTON_HEIGHT);
                setMaximumSize(getSize());
            }
        };
        this.btnPostOrder = new JButton("Dijkstra") {
            {
                setSize(BUTTON_WEIGHT, BUTTON_HEIGHT);
                setMaximumSize(getSize());
            }
        };
        this.btnBackBT = new JButton(){
            {
                setSize(IMAGE_WIDTH, IMAGE_HEIGHT);
                setMaximumSize(getSize());
            }
        };

        this.btnBackBT.setIcon(this.img);

        this.btnPreorder.addActionListener(this);
        this.btnInorder.addActionListener(this);
        this.btnPostOrder.addActionListener(this);
        this.btnAddPointBT.addActionListener(this);

        this.btnNextStepBT.addActionListener(this);
        this.btnDeleteBT.addActionListener(this);
        this.btnBackBT.addActionListener(this);

        this.txtFieldBT = new JTextField(5);
        this.txtFieldBT.setFont(new Font("Arial", Font.BOLD, 20));
        this.txtFieldBT.setText(MAIN_INFO);
        this.txtFieldBT.setEnabled(false);
        this.txtFieldBT.setBorder(new LineBorder(Color.WHITE));
        this.txtFieldBT.setDisabledTextColor(Color.BLACK);
        this.txtFieldBT.setHorizontalAlignment(JTextField.CENTER);

        JPanel editButtonPanel = new JPanel();
        editButtonPanel.add(this.btnAddPointBT);
        editButtonPanel.add(this.btnNextStepBT);
        editButtonPanel.add(this.btnDeleteBT);

        JPanel programsButtonPanel = new JPanel();
        programsButtonPanel.setLayout(new BoxLayout(programsButtonPanel, BoxLayout.Y_AXIS));
        programsButtonPanel.add(this.btnBackBT);
        programsButtonPanel.add(Box.createRigidArea(new Dimension(0, 50)));
        programsButtonPanel.add(this.btnPreorder);
        programsButtonPanel.add(this.btnInorder);
        programsButtonPanel.add(this.btnPostOrder);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(editButtonPanel, BorderLayout.NORTH);
        panel.add(this.txtFieldBT, BorderLayout.SOUTH);

        JPanel editorPanel = new JPanel();
        editorPanel.setLayout(new BorderLayout());
        editorPanel.add(this.binaryTree, BorderLayout.CENTER);
        editorPanel.add(panel, BorderLayout.NORTH);

        this.mainPanelBinaryTree = new JPanel();
        this.mainPanelBinaryTree.setLayout(new BorderLayout());
        this.mainPanelBinaryTree.add(editorPanel, BorderLayout.CENTER);
        this.mainPanelBinaryTree.add(programsButtonPanel, BorderLayout.WEST);
    }

    public App() {
        this.img = new ImageIcon(getClass().getResource("images/back.png"));
        this.img = new ImageIcon(this.img.getImage().getScaledInstance(IMAGE_WIDTH, IMAGE_HEIGHT,  java.awt.Image.SCALE_SMOOTH));

        initMenu();
        initGraph();
        initBinaryTree();

        setVisibleButtons(true);

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

        if(this.frame.getContentPane().equals(this.mainPanelMenu)) {
            if (btnGraph.equals(source)) {
                setNewPanel(this.mainPanelGraph);
            } else if (btnBinaryTree.equals(source)) {
                setNewPanel(this.mainPanelBinaryTree);
            }
        }

        if(this.frame.getContentPane().equals(this.mainPanelGraph)) {
            if (btnBreadthFirstSearch.equals(source)) {
                this.graph.redrawImage();
                this.graph.setBlnCanConnect(false);
                this.graph.setBlnCanDrawPoint(false);
                this.graph.setAlgorithmType(Graph.BREADTH_FIRST_SEARCH);
                this.txtFieldGraph.setText("Choose the first point!");
                setVisibleButtons(false);
            } else if (btnDepthFirstSearch.equals(source)) {
                this.graph.redrawImage();
                this.graph.setBlnCanConnect(false);
                this.graph.setBlnCanDrawPoint(false);
                this.graph.setAlgorithmType(Graph.DEPTH_FIRST_SEARCH);
                this.txtFieldGraph.setText("Choose the first point!");
                setVisibleButtons(false);
            } else if (btnDijkstra.equals(source)) {
                this.graph.redrawImage();
                this.graph.setBlnCanConnect(false);
                this.graph.setBlnCanDrawPoint(false);
                this.graph.setAlgorithmType(Graph.DIJKSTRA);
                this.txtFieldGraph.setText("Choose the first point!");
                setVisibleButtons(false);
            } else if (btnKruskal.equals(source)) {
                this.graph.redrawImage();
                this.graph.setBlnCanConnect(false);
                this.graph.setBlnCanDrawPoint(false);
                this.graph.setAlgorithmType(Graph.KRUSKAL);
                this.graph.kruskal();
                this.txtFieldGraph.setText("Sorting the connection list!");
                setVisibleButtons(false);
            } else if (btnAddPointGraph.equals(source)) {
                this.graph.redrawImage();
                this.graph.setBlnCanConnect(false);
                this.graph.setBlnCanDrawPoint(true);
                this.graph.setBlnCanDelete(false);
                this.graph.setAlgorithmType(Graph.NOT_TYPE);
            } else if (btnAddConnectionGraph.equals(source)) {
                this.graph.redrawImage();
                this.graph.clearIndexes();
                this.graph.setBlnCanDrawPoint(false);
                this.graph.setBlnCanConnect(true);
                this.graph.setBlnCanDelete(false);
                this.graph.setAlgorithmType(Graph.NOT_TYPE);
            } else if (btnNextStepGraph.equals(source)) {
                if (this.graph.getAlgorithmRunning()) {
                    this.graph.nextStep();
                    this.txtFieldGraph.setText(this.graph.getStrText());
                } else {
                    setVisibleButtons(true);
                    this.graph.redrawImage();
                    this.txtFieldGraph.setText(MAIN_INFO);
                }
            } else if (btnDeleteGraph.equals(source)) {
                this.graph.redrawImage();
                this.graph.clearIndexes();
                this.graph.setBlnCanDrawPoint(false);
                this.graph.setBlnCanConnect(false);
                this.graph.setBlnCanDelete(true);
                this.graph.setAlgorithmType(Graph.NOT_TYPE);
            } else if (btnBackGraph.equals(source)) {
                setNewPanel(this.mainPanelMenu);
            }
        }

        if(this.frame.getContentPane().equals(this.mainPanelBinaryTree)) {
            if (btnPreorder.equals(source)) {

            } else if (btnInorder.equals(source)) {

            } else if (btnPostOrder.equals(source)) {

            } else if (btnAddPointBT.equals(source) && !this.binaryTree.getBlnCanConnect()) {
                this.binaryTree.redrawImage();
                this.binaryTree.setBlnCanConnect(false);
                this.binaryTree.setBlnCanDrawPoint(true);
                this.binaryTree.setBlnCanDelete(false);
                this.binaryTree.setAlgorithmType(BinaryTree.NOT_TYPE);
            } else if (btnNextStepBT.equals(source)) {

            } else if (btnDeleteBT.equals(source)) {
                this.binaryTree.redrawImage();
                this.binaryTree.setBlnCanConnect(false);
                this.binaryTree.setBlnCanDrawPoint(false);
                this.binaryTree.setBlnCanDelete(true);
                this.binaryTree.setAlgorithmType(BinaryTree.NOT_TYPE);
            } else if (btnBackBT.equals(source)) {
                setNewPanel(this.mainPanelMenu);
            }
        }

    }

    // endregion

    // region 5. Functions and methods

    private void setNewPanel(JPanel newPanel){
        this.frame.setContentPane(newPanel);
        this.frame.validate();
    }

    private void setVisibleButtons(boolean cond){
        this.btnAddPointGraph.setVisible(cond);
        this.btnAddConnectionGraph.setVisible(cond);
        this.btnDeleteGraph.setVisible(cond);
        this.btnNextStepGraph.setVisible(!cond);

        this.btnAddPointBT.setVisible(cond);
        this.btnDeleteBT.setVisible(cond);
        this.btnNextStepBT.setVisible(!cond);

    }

    // endregion

}
