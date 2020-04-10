package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;


public class Graph extends JComponent {

    // region 0. Constants

    private static final int EMPTY = -1;
    private static final int NOT_DEFINED = -1;
    private static final double NULL_MESSAGE = -1;
    private static final int RADIUS = 50;
    public static final int NOT_TYPE = 0;
    public static final int BREADTH_FIRST_SEARCH = 1;
    public static final int DEPTH_FIRST_SEARCH = 2;
    public static final int DIJKSTRA = 3;

    // endregion

    // region 1. Init widgets

    private Image image;
    private Graphics2D graphics;

    private int algorithmType;
    private int numberPoints;
    private int indexFromPoint;
    private int indexToPoint;
    private boolean blnCanDrawPoint;
    private boolean blnCanConnect;

    private int[][] mat;
    private List<PointPosition> pointPositionList;
    private ArrayList<Connection> connectionList;
    private Vector<ArrayList<Integer>> adjacencyList;

    // endregion

    // region 2. Constructor

    public Graph() {
        this.algorithmType = NOT_TYPE;
        this.numberPoints = 0;
        this.blnCanDrawPoint = false;
        this.blnCanConnect = false;

        this.indexFromPoint = NOT_DEFINED;
        this.indexToPoint = NOT_DEFINED;

        this.pointPositionList = new ArrayList<>();
        this.connectionList = new ArrayList<>();
        this.adjacencyList = new Vector<>();

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent event) {
                int x = event.getX();
                int y = event.getY();

                if(blnCanDrawPoint) {
                    setNewPoint(x, y);
                } else if(blnCanConnect) {
                    setNewConnection(x, y);
                } else if(algorithmType != NOT_TYPE && mat[x][y] != EMPTY) {

                    switch (algorithmType){
                        case BREADTH_FIRST_SEARCH:
                            breadthFirstSearch(mat[x][y]);
                            break;

                        case DEPTH_FIRST_SEARCH:
                            depthFirstSearch(mat[x][y]);
                            break;

                        case DIJKSTRA:
                            dijkstra(mat[x][y]);
                            break;
                    }
                }

            }
        });

        this.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent event) {}

            @Override
            public void mouseMoved(MouseEvent event) {
                if(blnCanDrawPoint){
                    redrawImage();
                    drawCircle(event.getX(), event.getY(), Color.GRAY);
                } else if(blnCanConnect) {
                    if(indexFromPoint != NOT_DEFINED && indexToPoint == NOT_DEFINED){
                        redrawImage();
                        if(mat[event.getX()][event.getY()] == EMPTY) {
                            drawConnection(
                                    pointPositionList.get(indexFromPoint).getX(), pointPositionList.get(indexFromPoint).getY(), event.getX(), event.getY(), Color.GRAY
                            );
                        }else{
                            drawConnection(indexFromPoint, mat[event.getX()][event.getY()], Color.GRAY);
                        }
                    }
                }
            }
        });

    }

    // endregion

    // region 3. Main functions

    private void setNewPoint(int x, int y){
        drawAndSetPoint(x, y);
        this.blnCanDrawPoint = false;
        redrawPoints();
    }

    private void setNewConnection(int x, int y){
        if(mat[x][y] != EMPTY){
            if(this.indexFromPoint == NOT_DEFINED){
                this.indexFromPoint = mat[x][y];
            } else if(this.indexToPoint == NOT_DEFINED && mat[x][y] != this.indexFromPoint){
                this.indexToPoint = mat[x][y];
                double weight = openWindow();

                if (weight != NULL_MESSAGE) {
                    drawConnection(this.indexFromPoint, this.indexToPoint, Color.BLACK, weight);

                    this.connectionList.add(
                            new Connection(this.indexFromPoint, this.indexToPoint, weight)
                    );
                }

                this.clearIndexes();
                this.blnCanConnect = false;
                redrawImage();
            }
        }
    }

    private double openWindow(){

        String string = JOptionPane.showInputDialog(this, "What's the weight?");

        double weight;

        if(string == null)
            weight = NULL_MESSAGE;
        else {
            try {
                weight = Double.parseDouble(string);
            } catch (Exception error) {
                weight = 0.0;
            }
        }
        return weight;

    }

    public void clearIndexes(){
        this.indexFromPoint = this.indexToPoint = NOT_DEFINED;
    }

    // endregion

    // region 4. Painting

    @Override
    protected void paintComponent(Graphics g) {

        if(this.image == null){
            this.image = this.createImage(this.getSize().width, this.getSize().height);
            createMatrix();

            this.graphics = (Graphics2D) this.image.getGraphics();
            this.graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            this.clearImage();
        }
        g.drawImage(this.image, 0, 0, null);

    }

    // endregion

    // region 5. Clear the paint

    public void clearImage(){
        this.graphics.setPaint(Color.white);

        this.graphics.fillRect(0, 0, getSize().width, getSize().height);
        this.graphics.setPaint(getForeground());

        this.graphics.setPaint(Color.black);

        this.repaint();

    }

    // endregion

    // region 6. Draw Point

    private void drawAndSetPoint(int x, int y){
        if(this.graphics != null && this.blnCanDrawPoint){
            this.graphics.setPaint(Color.CYAN);
            this.graphics.fillOval(x - RADIUS /2, y - RADIUS /2, RADIUS, RADIUS);

            setNewElement(x, y);
            drawCircle(x, y, Color.BLACK);
        }
    }

    private void drawPoint(PointPosition pointPosition, Color color){
        if(this.graphics != null){

            this.graphics.setPaint(color);
            this.graphics.fillOval(pointPosition.getX() - RADIUS /2, pointPosition.getY() - RADIUS /2, RADIUS, RADIUS);

            this.repaint();
        }
    }

    public void drawCircle(int x, int y, Color color){
        if(this.graphics != null){

            this.graphics.setPaint(color);
            this.graphics.fillOval(x - RADIUS /2, y - RADIUS /2, RADIUS, RADIUS);

            this.repaint();
        }
    }

    // endregion

    // region 7. Draw Connection

    private void drawConnection(int indexFromPoint, int indexToPoint, Color color){

        PointPosition pointFrom = pointPositionList.get(indexFromPoint);
        PointPosition pointTo = pointPositionList.get(indexToPoint);

        this.graphics.setPaint(color);
        this.graphics.drawLine(
                pointFrom.getX(), pointFrom.getY(), pointTo.getX(), pointTo.getY());

        this.repaint();
    }

    private void drawConnection(int indexFromPoint, int indexToPoint, Color color, double weight){

        PointPosition pointFrom = pointPositionList.get(indexFromPoint);
        PointPosition pointTo = pointPositionList.get(indexToPoint);

        int x1 = pointFrom.getX();
        int y1 = pointFrom.getY();
        int x2 = pointTo.getX();
        int y2 = pointTo.getY();

        this.graphics.setPaint(color);
        this.graphics.drawLine(x1, y1, x2, y2);

        this.graphics.drawString(String.valueOf(weight), (x1 + x2)/2, (y1 + y2)/2);

        this.repaint();
    }

    private void drawConnection(int x1, int y1, int x2, int y2, Color color){
        this.graphics.setPaint(color);
        this.graphics.drawLine(
                x1, y1, x2, y2);

        this.repaint();
    }

    // endregion

    // region 8. Matrix functions and methods

    private void createMatrix(){
        this.mat = new int[this.getSize().width][this.getSize().height];
        for (int x = 0; x < this.getSize().width; x++) {
            for (int y = 0; y < getSize().height; y++) {
                mat[x][y] = EMPTY;
            }
        }
    }

    private void setNewElement(int pointX, int pointY){
        BufferedImage bufferedImage = (BufferedImage) this.image;

        for(int x = 0; x < this.getSize().width; x++){
            for (int y = 0; y < this.getSize().height; y++) {
                int rgb = bufferedImage.getRGB(x, y);
                if(rgb == Color.CYAN.getRGB()){
                    this.mat[x][y] = this.numberPoints;
                }
            }
        }

        PointPosition point = new PointPosition(numberPoints, pointX, pointY);
        this.pointPositionList.add(point);

        this.numberPoints++;
    }

    private void printMatrix(){
        for (int i = 0; i < this.getSize().width; i++) {
            for (int j = 0; j < this.getSize().height; j++) {
                System.out.print(mat[i][j]);
            }
            System.out.println();
        }
    }

    // endregion

    // region 9. Redraw image

    public void redrawImage(){
        clearImage();
        redrawPoints();
        redrawConnections();
    }

    private void redrawConnections() {
        for (Connection connection : this.connectionList){
            drawConnection(connection.getFromPoint(), connection.getToPoint(), Color.BLACK, connection.getWeight());
        }
    }

    private void redrawPoints(){
        for(PointPosition point : this.pointPositionList){
            drawCircle(point.getX(), point.getY(), Color.BLACK);
        }
    }

    // endregion

    // region 10. Getters and Setters

    public void setAlgorithmType(int algorithmType) {
        this.algorithmType = algorithmType;
    }

    public void setBlnCanConnect(boolean blnCanConnect) {
        this.blnCanConnect = blnCanConnect;
    }

    public void setBlnCanDrawPoint(boolean blnCanDrawPoint) {
        this.blnCanDrawPoint = blnCanDrawPoint;
    }

    // endregion

    //region 11. Adjacency list methods

    private void addToAdjacencyList(int from, int to){
        ArrayList<Integer> arrayList = adjacencyList.get(from);
        arrayList.add(to);
        adjacencyList.set(from, arrayList);
    }

    private void createAdjacencyList(){
        this.adjacencyList.clear();
        this.adjacencyList.setSize(this.numberPoints);
        for (int i = 0; i < this.numberPoints; i++) {
            ArrayList<Integer> list = new ArrayList<>();
            adjacencyList.set(i, list);
        }

        for (Connection connection : connectionList){
            int from = connection.getFromPoint();
            int to = connection.getToPoint();
            double weight = connection.getWeight();
            addToAdjacencyList(from, to);
            addToAdjacencyList(to, from);
        }
    }

    // endregion

    // region 12. Breadth-First Search

    private void breadthFirstSearch(int index){
        this.algorithmType = NOT_TYPE;
        createAdjacencyList();

        ArrayList<Integer> neighbors = adjacencyList.get(index);

        for(Integer neighbor : neighbors){
            System.out.println(neighbor);
        }
    }

    // endregion

    // region 13. Depth-First Search

    private void depthFirstSearch(int index){
        this.algorithmType = NOT_TYPE;
        createAdjacencyList();
    }

    // endregion

    // region 14. Dijkstra

    private void dijkstra(int index){
        this.algorithmType = NOT_TYPE;
        createAdjacencyList();
    }

    // endregion

    // region 15. Kruskal

    private void sortingConnectionList(){
        boolean ok = true;
        int len = connectionList.size();

        while(ok){
            ok = false;
            for (int i = 0; i < len - 1; i++) {
                if(connectionList.get(i).getWeight() > connectionList.get(i+1).getWeight()){
                    Connection tmp = connectionList.get(i);
                    connectionList.set(i, connectionList.get(i+1));
                    connectionList.set(i+1, tmp);

                    ok = true;
                }
            }

            len--;
        }
    }

    private void connectTwoComponent(int from, int to, int[] comp){
        int number = comp[to];
        for (int i = 0; i < this.numberPoints; i++) {
            if(comp[i] == number){
                comp[i] = comp[from];
            }
        }
    }

    public void kruskal(){
        this.algorithmType = NOT_TYPE;

        sortingConnectionList();

        int[] comp = new int[this.numberPoints];
        for (int i = 0; i < this.numberPoints; i++) {
            comp[i] = i;
        }

        for(Connection connection : connectionList){
            int from = connection.getFromPoint();
            int to = connection.getToPoint();

            if(comp[from] != comp[to]){
                connectTwoComponent(from, to, comp);

                drawPoint(pointPositionList.get(from), Color.RED);
                drawPoint(pointPositionList.get(to), Color.RED);
                drawConnection(from, to, Color.RED, connection.getWeight());

            }
        }
    }

    // endregion

}
