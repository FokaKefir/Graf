package com.company.gui;

import com.company.graphs.BinaryTree;
import com.company.graphs.Graph;
import com.company.swing.StyledButtonUI;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.event.*;

public class App extends MouseAdapter implements ActionListener {

    // region 0. Constants

    private static final int IMAGE_WIDTH = 50;
    private static final int IMAGE_HEIGHT = 50;

    private static final int BUTTON_WEIGHT = 200;
    private static final int BUTTON_HEIGHT = 50;

    private static final String MAIN_INFO = "Add new points and edges, then choose the algorithm type!";

    private static final Color COLOR_ADD = new Color(170,255,100);
    private static final Color COLOR_NEXT = new Color(170,255,100);
    private static final Color COLOR_DELETE = new Color(240,90,90);
    private static final Color COLOR_ALG = new Color(250,200,65);
    private static final Color COLOR_BACKGROUND = new Color(248,250,190);

    // endregion

    // region 1. Init Widgets

    private JFrame frame;

    private JPanel mainPanelMenu;
    private JTextPane txtInfo;
    private JButton btnGraph;
    private JButton btnBinaryTree;

    private JPanel mainPanelGraph;
    private JTextField txtFieldGraph;
    private JTextArea txtListGraph;
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
    private JTextArea txtListBT;
    private JButton btnPreorder;
    private JButton btnInorder;
    private JButton btnPostorder;
    private JButton btnAddPointBT;
    private JButton btnNextStepBT;
    private JButton btnDeleteBT;
    private JButton btnBackBT;
    private BinaryTree binaryTree;

    private ImageIcon imgBack;
    private ImageIcon imgIcon;

    // endregion

    // region 2. Constructor

    private void initMenu(){
        this.btnGraph = new JButton("Graph");
        this.btnBinaryTree = new JButton("Binary Tree");

        this.btnGraph.setToolTipText("Graph");
        this.btnBinaryTree.setToolTipText("Binary tree");

        this.btnGraph.setUI(new StyledButtonUI());
        this.btnBinaryTree.setUI(new StyledButtonUI());

        this.btnGraph.addActionListener(this);
        this.btnBinaryTree.addActionListener(this);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.add(this.btnBinaryTree);
        buttonPanel.add(this.btnGraph);

        ImageIcon image = new ImageIcon(getClass().getResource("images/graph.png"));

        this.txtInfo = new JTextPane();
        this.txtInfo.setFont(new Font("Calibri", Font.BOLD, 22));
        // TODO set text
        this.txtInfo.setText("The whole universe is based on the concept of graph theory where love is an edge,\n " +
                "that is connecting two vertices or people either directly or indirectly.");
        StyledDocument doc = this.txtInfo.getStyledDocument();
        SimpleAttributeSet center = new SimpleAttributeSet();
        StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
        doc.setParagraphAttributes(0, doc.getLength(), center, false);

        this.mainPanelMenu = new JPanel();
        this.mainPanelMenu.setLayout(new BorderLayout());
        this.mainPanelMenu.setBackground(Color.WHITE);
        this.mainPanelMenu.add(new JLabel(image), BorderLayout.NORTH);
        this.mainPanelMenu.add(this.txtInfo, BorderLayout.CENTER);
        this.mainPanelMenu.add(buttonPanel, BorderLayout.SOUTH);


    }

    private void initGraph(){
        this.graph = new Graph();
        this.graph.addMouseListener(this);

        this.btnAddPointGraph = new JButton("Add new points");
        this.btnAddConnectionGraph = new JButton("Add new edge");
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

        this.btnBackGraph.setIcon(this.imgBack);

        this.btnBreadthFirstSearch.setToolTipText("<html>" + "Breadth-First Search is a traversing algorithm"+ "<br>" + " where you should start traversing from a selected point" + "<br>"  + "and traverse the graph layerwise thus exploring the neighbour points." + "</html>");
        this.btnDepthFirstSearch.setToolTipText("<html>" + "Depth-First Search explores all the points" + "<br>" + "by going forward if possible or uses backtracking." + "</html>");
        this.btnDijkstra.setToolTipText("<html>"+ "Dijkstra's algorithm is an algorithm for finding"+ "<br>" +  "the shortest paths between points in a graph."+ "</html>");
        this.btnKruskal.setToolTipText("<html>" + "Kruskal's algorithm is a minimum-spanning-tree algorithm" + "<br>" + "which finds an edge of the least possible weight that connects any two trees in the graph." + "</html>");
        this.btnDeleteGraph.setToolTipText("Delete points and edges");

        this.btnBreadthFirstSearch.setBackground(COLOR_ALG);
        this.btnDepthFirstSearch.setBackground(COLOR_ALG);
        this.btnDijkstra.setBackground(COLOR_ALG);
        this.btnKruskal.setBackground(COLOR_ALG);

        this.btnAddPointGraph.setBackground(COLOR_ADD);
        this.btnAddConnectionGraph.setBackground(COLOR_ADD);
        this.btnNextStepGraph.setBackground(COLOR_NEXT);

        this.btnDeleteGraph.setBackground(COLOR_DELETE);
        this.btnBackGraph.setBackground(Color.WHITE);

        this.btnBreadthFirstSearch.setUI(new StyledButtonUI());
        this.btnDepthFirstSearch.setUI(new StyledButtonUI());
        this.btnDijkstra.setUI(new StyledButtonUI());
        this.btnKruskal.setUI(new StyledButtonUI());
        this.btnAddPointGraph.setUI(new StyledButtonUI());
        this.btnAddConnectionGraph.setUI(new StyledButtonUI());
        this.btnNextStepGraph.setUI(new StyledButtonUI());
        this.btnDeleteGraph.setUI(new StyledButtonUI());
        this.btnBackGraph.setUI(new StyledButtonUI());

        this.btnBreadthFirstSearch.addActionListener(this);
        this.btnDepthFirstSearch.addActionListener(this);
        this.btnDijkstra.addActionListener(this);
        this.btnKruskal.addActionListener(this);
        this.btnAddPointGraph.addActionListener(this);
        this.btnAddConnectionGraph.addActionListener(this);
        this.btnNextStepGraph.addActionListener(this);
        this.btnDeleteGraph.addActionListener(this);
        this.btnBackGraph.addActionListener(this);

        setVisibleButtonsGraph(true);

        this.txtFieldGraph = new JTextField(5);
        this.txtFieldGraph.setFont(new Font("Calibri", Font.BOLD, 22));
        this.txtFieldGraph.setText(MAIN_INFO);
        this.txtFieldGraph.setEnabled(false);
        this.txtFieldGraph.setBorder(new LineBorder(COLOR_BACKGROUND));
        this.txtFieldGraph.setBackground(COLOR_BACKGROUND);
        this.txtFieldGraph.setDisabledTextColor(Color.BLACK);
        this.txtFieldGraph.setHorizontalAlignment(JTextField.CENTER);

        this.txtListGraph = new JTextArea(100, 12);
        this.txtListGraph.setFont(new Font("Calibri", Font.BOLD, 18));
        this.txtListGraph.setText("Edge list:");
        this.txtListGraph.setBackground(COLOR_BACKGROUND);
        this.txtListGraph.setMaximumSize(this.txtListGraph.getSize());

        JPanel editButtonPanel = new JPanel();
        editButtonPanel.setBackground(COLOR_BACKGROUND);
        editButtonPanel.add(this.btnAddPointGraph);
        editButtonPanel.add(this.btnAddConnectionGraph);
        editButtonPanel.add(this.btnNextStepGraph);
        editButtonPanel.add(Box.createRigidArea(new Dimension(60, 0)));
        editButtonPanel.add(this.btnDeleteGraph);

        JPanel programsButtonPanel = new JPanel();
        programsButtonPanel.setLayout(new BoxLayout(programsButtonPanel, BoxLayout.Y_AXIS));
        programsButtonPanel.setBackground(COLOR_BACKGROUND);
        programsButtonPanel.add(this.btnBackGraph);
        programsButtonPanel.add(Box.createRigidArea(new Dimension(0, 120)));
        programsButtonPanel.add(this.btnBreadthFirstSearch);
        programsButtonPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        programsButtonPanel.add(this.btnDepthFirstSearch);
        programsButtonPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        programsButtonPanel.add(this.btnDijkstra);
        programsButtonPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        programsButtonPanel.add(this.btnKruskal);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(editButtonPanel, BorderLayout.NORTH);
        panel.add(Box.createRigidArea(new Dimension(0, 20)), BorderLayout.CENTER);
        panel.add(this.txtFieldGraph, BorderLayout.SOUTH);
        panel.setBackground(COLOR_BACKGROUND);

        JPanel editorPanel = new JPanel();
        editorPanel.setLayout(new BorderLayout());
        editorPanel.add(this.graph, BorderLayout.CENTER);
        editorPanel.add(panel, BorderLayout.NORTH);
        editorPanel.add(this.txtListGraph, BorderLayout.EAST);
        editorPanel.setBackground(COLOR_BACKGROUND);

        this.mainPanelGraph = new JPanel();
        this.mainPanelGraph.setLayout(new BorderLayout());
        this.mainPanelGraph.add(editorPanel, BorderLayout.CENTER);
        this.mainPanelGraph.add(programsButtonPanel, BorderLayout.WEST);
    }

    private void initBinaryTree(){
        this.binaryTree = new BinaryTree();
        this.binaryTree.addMouseListener(this);

        this.btnAddPointBT = new JButton("Add new point");
        this.btnNextStepBT = new JButton("Next");
        this.btnDeleteBT = new JButton("Delete");
        this.btnPreorder = new JButton("Preorder"){
            {
                setSize(BUTTON_WEIGHT, BUTTON_HEIGHT);
                setMaximumSize(getSize());
            }
        };
        this.btnInorder = new JButton("Inorder"){
            {
                setSize(BUTTON_WEIGHT, BUTTON_HEIGHT);
                setMaximumSize(getSize());
            }
        };
        this.btnPostorder = new JButton("Postorder") {
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

        this.btnBackBT.setIcon(this.imgBack);

        this.btnDeleteBT.setToolTipText( "<html>" + "Delete nodes" + "</html>" );
        this.btnPreorder.setToolTipText("<html>" + "In this traversal method, the root node is visited first,"+ "<br>"+ "then the left subtree and finally the right subtree." +"</html>");
        this.btnInorder.setToolTipText("<html>" + "In this traversal method, the left subtree is visited first," + "<br>" + "then the root and later the right sub-tree." + "</html>" );
        this.btnPostorder.setToolTipText("<html>"+  "First we traverse the left subtree,"+"<br>"+"then the right subtree and finally the root node." + "</html>");

        this.btnAddPointBT.setBackground(COLOR_ADD);
        this.btnNextStepBT.setBackground(COLOR_NEXT);
        this.btnDeleteBT.setBackground(COLOR_DELETE);
        this.btnPreorder .setBackground(COLOR_ALG);
        this.btnInorder.setBackground(COLOR_ALG);
        this.btnPostorder.setBackground(COLOR_ALG);
        this.btnBackBT.setBackground(Color.WHITE);

        this.btnAddPointBT.setUI(new StyledButtonUI());
        this.btnNextStepBT.setUI(new StyledButtonUI());
        this.btnDeleteBT.setUI(new StyledButtonUI());
        this.btnPreorder .setUI(new StyledButtonUI());
        this.btnInorder.setUI(new StyledButtonUI());
        this.btnPostorder.setUI(new StyledButtonUI());
        this.btnBackBT.setUI(new StyledButtonUI());

        this.btnPreorder.addActionListener(this);
        this.btnInorder.addActionListener(this);
        this.btnPostorder.addActionListener(this);
        this.btnAddPointBT.addActionListener(this);
        this.btnNextStepBT.addActionListener(this);
        this.btnDeleteBT.addActionListener(this);
        this.btnBackBT.addActionListener(this);

        setVisibleButtonsBT(true);

        this.txtFieldBT = new JTextField(5);
        this.txtFieldBT.setFont(new Font("Calibri", Font.BOLD, 22));
        this.txtFieldBT.setText(MAIN_INFO);
        this.txtFieldBT.setEnabled(false);
        this.txtFieldBT.setBorder(new LineBorder(COLOR_BACKGROUND));
        this.txtFieldBT.setDisabledTextColor(Color.BLACK);
        this.txtFieldBT.setHorizontalAlignment(JTextField.CENTER);
        this.txtFieldBT.setBackground(COLOR_BACKGROUND);

        this.txtListBT = new JTextArea(100, 12);
        this.txtListBT.setFont(new Font("Calibri", Font.BOLD, 18));
        this.txtListBT.setText("Father list:");
        this.txtListBT.setMaximumSize(this.txtListBT.getSize());
        this.txtListBT.setBackground(COLOR_BACKGROUND);

        JPanel editButtonPanel = new JPanel();
        editButtonPanel.add(this.btnAddPointBT);
        editButtonPanel.add(this.btnNextStepBT);
        editButtonPanel.add(Box.createRigidArea(new Dimension(60, 0)));
        editButtonPanel.add(this.btnDeleteBT);
        editButtonPanel.setBackground(COLOR_BACKGROUND);

        JPanel programsButtonPanel = new JPanel();
        programsButtonPanel.setLayout(new BoxLayout(programsButtonPanel, BoxLayout.Y_AXIS));
        programsButtonPanel.setBackground(COLOR_BACKGROUND);
        programsButtonPanel.add(this.btnBackBT);
        programsButtonPanel.add(Box.createRigidArea(new Dimension(0, 120)));
        programsButtonPanel.add(this.btnPreorder);
        programsButtonPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        programsButtonPanel.add(this.btnInorder);
        programsButtonPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        programsButtonPanel.add(this.btnPostorder);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(editButtonPanel, BorderLayout.NORTH);
        panel.add(this.txtFieldBT, BorderLayout.SOUTH);

        JPanel editorPanel = new JPanel();
        editorPanel.setLayout(new BorderLayout());
        editorPanel.add(this.binaryTree, BorderLayout.CENTER);
        editorPanel.add(panel, BorderLayout.NORTH);
        editorPanel.add(this.txtListBT, BorderLayout.EAST);
        editorPanel.setBackground(COLOR_BACKGROUND);

        this.mainPanelBinaryTree = new JPanel();
        this.mainPanelBinaryTree.setLayout(new BorderLayout());
        this.mainPanelBinaryTree.add(editorPanel, BorderLayout.CENTER);
        this.mainPanelBinaryTree.add(programsButtonPanel, BorderLayout.WEST);
    }

    public App() {
        this.imgBack = new ImageIcon(getClass().getResource("images/back.png"));
        this.imgBack = new ImageIcon(this.imgBack.getImage().getScaledInstance(IMAGE_WIDTH, IMAGE_HEIGHT,  Image.SCALE_SMOOTH));

        this.imgIcon = new ImageIcon(getClass().getResource("images/icon.png"));

        UIManager.put("ToolTip.background", Color.WHITE);
        UIManager.put("ToolTip.foreground", Color.BLACK);
        UIManager.put("ToolTip.font",new Font("Arial", Font.BOLD, 14));
        ToolTipManager.sharedInstance().setDismissDelay(60000);
        ToolTipManager.sharedInstance().setInitialDelay(200);

        initMenu();
        initGraph();
        initBinaryTree();


        this.frame = new JFrame("Graph application!");
        this.frame.setIconImage(this.imgIcon.getImage());
        this.frame.setContentPane(this.mainPanelMenu);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setResizable(false);
        this.frame.setSize(
                new Dimension((int) (Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 1.2),
                              (int) (Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 1.2))
        );
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
                setVisibleButtonsGraph(false);
            } else if (btnDepthFirstSearch.equals(source)) {
                this.graph.redrawImage();
                this.graph.setBlnCanConnect(false);
                this.graph.setBlnCanDrawPoint(false);
                this.graph.setAlgorithmType(Graph.DEPTH_FIRST_SEARCH);
                this.txtFieldGraph.setText("Choose the first point!");
                setVisibleButtonsGraph(false);
            } else if (btnDijkstra.equals(source)) {
                this.graph.redrawImage();
                this.graph.setBlnCanConnect(false);
                this.graph.setBlnCanDrawPoint(false);
                this.graph.setAlgorithmType(Graph.DIJKSTRA);
                this.txtFieldGraph.setText("Choose the first point!");
                setVisibleButtonsGraph(false);
            } else if (btnKruskal.equals(source)) {
                this.graph.redrawImage();
                this.graph.setBlnCanConnect(false);
                this.graph.setBlnCanDrawPoint(false);
                this.graph.setAlgorithmType(Graph.KRUSKAL);
                this.graph.kruskal();
                this.txtFieldGraph.setText("Sorting the edges list!");
                setVisibleButtonsGraph(false);
            } else if (btnAddPointGraph.equals(source)) {
                this.graph.redrawImage();
                this.graph.setBlnCanConnect(false);
                this.graph.setBlnCanDrawPoint(!this.graph.getBlnCanDrawPoint());
                this.graph.setBlnCanDelete(false);
                this.graph.setAlgorithmType(Graph.NOT_TYPE);
            } else if (btnAddConnectionGraph.equals(source)) {
                this.graph.redrawImage();
                this.graph.clearIndexes();
                this.graph.setBlnCanDrawPoint(false);
                this.graph.setBlnCanConnect(!this.graph.getBlnCanConnect());
                this.graph.setBlnCanDelete(false);
                this.graph.setAlgorithmType(Graph.NOT_TYPE);
            } else if (btnNextStepGraph.equals(source)) {
                if (this.graph.getAlgorithmRunning()) {
                    this.graph.nextStep();
                    this.txtFieldGraph.setText(this.graph.getStrText());
                } else {
                    setVisibleButtonsGraph(true);
                    this.graph.redrawImage();
                    this.txtFieldGraph.setText(MAIN_INFO);
                }
            } else if (btnDeleteGraph.equals(source)) {
                this.graph.redrawImage();
                this.graph.clearIndexes();
                this.graph.setBlnCanDrawPoint(false);
                this.graph.setBlnCanConnect(false);
                this.graph.setBlnCanDelete(!this.graph.getBlnCanDelete());
                this.graph.setAlgorithmType(Graph.NOT_TYPE);
            } else if (btnBackGraph.equals(source)) {
                setNewPanel(this.mainPanelMenu);
            }
        }

        if(this.frame.getContentPane().equals(this.mainPanelBinaryTree)) {
            if (btnPreorder.equals(source) && this.binaryTree.getBlnCanRunAlgorithm()) {
                this.binaryTree.redrawImage();
                this.binaryTree.setBlnCanDrawPoint(false);
                this.binaryTree.setBlnCanConnect(false);
                this.binaryTree.setAlgorithmType(BinaryTree.PREORDER);
                this.binaryTree.preorder();
                this.txtFieldBT.setText("The algorithm starts from the root.");
                setVisibleButtonsBT(false);
            } else if (btnInorder.equals(source) && this.binaryTree.getBlnCanRunAlgorithm()) {
                this.binaryTree.redrawImage();
                this.binaryTree.setBlnCanDrawPoint(false);
                this.binaryTree.setBlnCanConnect(false);
                this.binaryTree.setAlgorithmType(BinaryTree.INORDER);
                this.binaryTree.inorder();
                this.txtFieldBT.setText("The algorithm begins from the root.");
                setVisibleButtonsBT(false);
            } else if (btnPostorder.equals(source) && this.binaryTree.getBlnCanRunAlgorithm()) {
                this.binaryTree.redrawImage();
                this.binaryTree.setBlnCanDrawPoint(false);
                this.binaryTree.setBlnCanConnect(false);
                this.binaryTree.setAlgorithmType(BinaryTree.POSTORDER);
                this.binaryTree.postorder();
                this.txtFieldBT.setText("The algorithm begins from the root.");
                setVisibleButtonsBT(false);
            } else if (btnAddPointBT.equals(source)) {
                this.binaryTree.redrawImage();
                this.binaryTree.setBlnCanDrawPoint(!this.binaryTree.getBlnCanDrawPoint());
                this.binaryTree.setBlnCanConnect(false);
                this.binaryTree.setBlnCanDelete(false);
                this.binaryTree.setAlgorithmType(BinaryTree.NOT_TYPE);
            } else if (btnNextStepBT.equals(source)) {
                if (this.binaryTree.getAlgorithmRunning()) {
                    this.binaryTree.nextStep();
                    this.txtFieldBT.setText(this.binaryTree.getStrText());
                } else {
                    setVisibleButtonsBT(true);
                    this.binaryTree.redrawImage();
                    this.txtFieldBT.setText(MAIN_INFO);
                }
            } else if (btnDeleteBT.equals(source)) {
                this.binaryTree.redrawImage();
                this.binaryTree.setBlnCanDrawPoint(false);
                this.binaryTree.setBlnCanConnect(false);
                this.binaryTree.setBlnCanDelete(!this.binaryTree.getBlnCanDelete());
                this.binaryTree.setAlgorithmType(BinaryTree.NOT_TYPE);
            } else if (btnBackBT.equals(source)) {
                setNewPanel(this.mainPanelMenu);
            }
        }

    }

    @Override
    public void mouseClicked(MouseEvent event) {
        if(this.frame.getContentPane().equals(this.mainPanelGraph)){
            this.txtListGraph.setText("Edge list:\n" + this.graph.getStrConnectionList());
        }

        if(this.frame.getContentPane().equals(this.mainPanelBinaryTree)){
            this.txtListBT.setText("Father list:\n" + this.binaryTree.getStrFatherList());
        }


    }

    // endregion

    // region 5. Functions and methods

    private void setNewPanel(JPanel newPanel){
        this.frame.setContentPane(newPanel);
        this.frame.validate();
    }

    private void setVisibleButtonsGraph(boolean cond){
        this.btnAddPointGraph.setVisible(cond);
        this.btnAddConnectionGraph.setVisible(cond);
        this.btnDeleteGraph.setVisible(cond);
        this.btnNextStepGraph.setVisible(!cond);
    }

    private void setVisibleButtonsBT(boolean cond){
        this.btnAddPointBT.setVisible(cond);
        this.btnDeleteBT.setVisible(cond);
        this.btnNextStepBT.setVisible(!cond);
    }

    // endregion

}
