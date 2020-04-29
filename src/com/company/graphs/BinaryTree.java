package com.company.graphs;

import com.company.model.Connection;
import com.company.model.PointPosition;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.util.*;
import java.util.List;


public class BinaryTree extends JComponent {

    // region 0. Constants

    private static final int EMPTY = -1;
    private static final int NOT_DEFINED = -1;
    private static final double NULL_MESSAGE = -1;
    private static final int INFINITY = -1;
    private static final boolean IT_WAS = true;
    private static final boolean FIND_NEW_NEIGHBOR = true;
    private static final int RADIUS = 50;
    public static final int NOT_TYPE = 0;


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
    private boolean blnCanDelete;

    private int[][] matPoint;
    private int[][] matConnection;
    private List<PointPosition> pointPositionList;
    private ArrayList<Connection> connectionList;
    private ArrayList<Connection> connectionListAll;


    private String strText;
    private boolean algorithmRunning;


    // endregion

    // region 2. Constructor

    public BinaryTree() {
        this.algorithmType = NOT_TYPE;
        this.numberPoints = 0;
        this.blnCanDrawPoint = false;
        this.blnCanConnect = false;
        this.blnCanDelete = false;

        this.indexFromPoint = NOT_DEFINED;
        this.indexToPoint = NOT_DEFINED;

        this.pointPositionList = new ArrayList<>();
        this.connectionList = new ArrayList<>();
        this.connectionListAll = new ArrayList<>();


        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent event) {
                int x = event.getX();
                int y = event.getY();

                if(blnCanDrawPoint) {
                    setNewPoint(x, y);
                } else if(blnCanConnect) {
                    setNewConnection(x, y);
                } else if(blnCanDelete) {
                    switchDelete(x, y);
                } else if(algorithmType != NOT_TYPE && matPoint[x][y] != EMPTY) {
                    switch (algorithmType){

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
                        if(matPoint[event.getX()][event.getY()] == EMPTY) {
                            drawConnection(
                                    pointPositionList.get(indexFromPoint).getX(), pointPositionList.get(indexFromPoint).getY(), event.getX(), event.getY(), Color.GRAY
                            );
                        }else{
                            drawConnection(indexFromPoint, matPoint[event.getX()][event.getY()], Color.GRAY);
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
        if(matPoint[x][y] != EMPTY){
            if(this.indexFromPoint == NOT_DEFINED){
                this.indexFromPoint = matPoint[x][y];
            } else if(this.indexToPoint == NOT_DEFINED && matPoint[x][y] != this.indexFromPoint){
                this.indexToPoint = matPoint[x][y];
                double weight = openWindow();

                if (weight != NULL_MESSAGE) {
                    drawAndSetConnection(weight);

                }

                this.clearIndexes();
                this.blnCanConnect = false;
                redrawImage();
            }
        }
    }

    private void switchDelete(int x, int y){
        if(this.matPoint[x][y] != EMPTY){
            deletePoint(x, y);
            this.blnCanDelete = false;
        } else if(this.matConnection[x][y] != EMPTY){
            deleteConnection(x, y);
            this.blnCanDelete = false;
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

    public void nextStep(){
        if(this.algorithmRunning) {
            switch (algorithmType) {

            }
        }
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

            this.graphics.setFont(new Font("TimesRoman", Font.PLAIN, 20));

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

            setNewElementPoint(x, y);
            drawCircle(x, y, Color.BLACK);
        }
    }

    private void drawCircle(PointPosition pointPosition, Color color){
        if(this.graphics != null){

            int x = pointPosition.getX();
            int y = pointPosition.getY();

            this.graphics.setPaint(color);
            this.graphics.fillOval(
                    x - RADIUS /2,y - RADIUS /2, RADIUS, RADIUS
            );

            if(color != Color.GRAY) {
                this.graphics.setPaint(Color.WHITE);
                this.graphics.drawString(String.valueOf(matPoint[x][y]), x - 5, y + 7);
            }

            this.repaint();
        }
    }

    public void drawCircle(int x, int y, Color color){
        if(this.graphics != null){

            this.graphics.setPaint(color);
            this.graphics.fillOval(x - RADIUS /2, y - RADIUS /2, RADIUS, RADIUS);

            if(color != Color.GRAY) {
                this.graphics.setPaint(Color.WHITE);
                this.graphics.drawString(String.valueOf(matPoint[x][y]), x - 5, y + 7);
            }

            this.repaint();
        }
    }

    private void drawRing(int x, int y, Color color){
        if(this.graphics != null){

            this.graphics.setPaint(color);
            this.graphics.drawOval(x - RADIUS /2, y - RADIUS /2, RADIUS, RADIUS);

            this.repaint();
        }
    }

    // endregion

    // region 7. Draw Connections

    private void drawAndSetConnection(double weight){
        drawConnection(this.indexFromPoint, this.indexToPoint, Color.CYAN);
        setNewElementConn(weight);
        drawConnection(this.indexFromPoint, this.indexToPoint, Color.BLACK, weight);
    }

    private void drawConnection(int indexFromPoint, int indexToPoint, Color color){

        PointPosition pointFrom = pointPositionList.get(indexFromPoint);
        PointPosition pointTo = pointPositionList.get(indexToPoint);

        this.graphics.setPaint(color);
        this.graphics.setStroke(new BasicStroke(5));
        this.graphics.drawLine(
                pointFrom.getX(), pointFrom.getY(), pointTo.getX(), pointTo.getY());

        this.graphics.setStroke(new BasicStroke(1));
        redrawNumbers();

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
        this.graphics.setStroke(new BasicStroke(5));
        this.graphics.drawLine(x1, y1, x2, y2);
        this.graphics.setStroke(new BasicStroke(1));

        int strX = (x1 + x2)/2;
        int strY = (y1 + y2)/2;

        if((x1 <= x2 && y1 <= y2) || (x2 <= x1 && y2 <= y1)){
            this.graphics.drawString(String.valueOf(weight), strX + 5, strY - 6);
        }else {
            this.graphics.drawString(String.valueOf(weight), strX + 6, strY + 16);
        }

        redrawNumbers();

        this.repaint();
    }

    private void drawConnection(int x1, int y1, int x2, int y2, Color color){
        this.graphics.setPaint(color);
        this.graphics.setStroke(new BasicStroke(5));
        this.graphics.drawLine(
                x1, y1, x2, y2);
        this.graphics.setStroke(new BasicStroke(1));
        redrawNumbers();

        this.repaint();
    }

    // endregion

    // region 8. Matrix functions and methods

    private void createMatrix(){
        this.matPoint = new int[this.getSize().width][this.getSize().height];
        this.matConnection = new int[this.getSize().width][this.getSize().height];
        for (int x = 0; x < this.getSize().width; x++) {
            for (int y = 0; y < getSize().height; y++) {
                matPoint[x][y] = EMPTY;
                matConnection[x][y] = EMPTY;
            }
        }
    }

    private void setNewElementPoint(int pointX, int pointY){
        BufferedImage bufferedImage = (BufferedImage) this.image;

        for(int x = 0; x < this.getSize().width; x++){
            for (int y = 0; y < this.getSize().height; y++) {
                int rgb = bufferedImage.getRGB(x, y);
                if(rgb == Color.CYAN.getRGB()){
                    this.matPoint[x][y] = this.numberPoints;
                }
            }
        }

        PointPosition point = new PointPosition(numberPoints, pointX, pointY);
        this.pointPositionList.add(point);

        this.numberPoints++;
    }

    private void setNewElementConn(double weight){
        BufferedImage bufferedImage = (BufferedImage) this.image;

        for(int x = 0; x < this.getSize().width; x++){
            for (int y = 0; y < this.getSize().height; y++) {
                int rgb = bufferedImage.getRGB(x, y);
                if(rgb == Color.CYAN.getRGB()){
                    this.matConnection[x][y] = this.connectionListAll.size();
                }
            }
        }


        this.connectionList.add(
                new Connection(this.indexFromPoint, this.indexToPoint, weight)
        );
        this.connectionListAll.add(
                new Connection(this.indexFromPoint, this.indexToPoint, weight)
        );

    }

    // endregion

    // region 9. Redraw functions

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
            int x = point.getX();
            int y = point.getY();
            if(this.matPoint[x][y] == point.getName()) {
                drawCircle(x, y, Color.BLACK);
            }
        }
    }

    private void redrawNumbers(){
        for(PointPosition point : this.pointPositionList){

            int x = point.getX();
            int y = point.getY();

            if(this.matPoint[x][y] == EMPTY){
                continue;
            }

            this.graphics.setPaint(Color.WHITE);
            this.graphics.drawString(String.valueOf(matPoint[x][y]), x - 5, y + 7);

        }
    }

    public void redrawImage(boolean[] b){
        clearImage();
        redrawPoints();
        redrawConnections(b);
    }

    private void redrawConnections(boolean[] b) {
        int index = 0;
        for (Connection connection : this.connectionList){
            if(b[index])
                drawConnection(connection.getFromPoint(), connection.getToPoint(), Color.BLACK, connection.getWeight());
            index++;
        }
    }

    // endregion

    // region 10. Delete methods

    private void deletePointFromMatrix(int num){
        for (int x = 0; x < this.getSize().width; x++) {
            for (int y = 0; y < getSize().height; y++) {
                if(this.matPoint[x][y] == num){
                    this.matPoint[x][y] = EMPTY;
                }
            }
        }
    }

    private void deleteConnectionFromMatrix(int ind){
        Connection con = this.connectionListAll.get(ind);

        PointPosition fromPos = this.pointPositionList.get(con.getFromPoint());
        PointPosition toPos = this.pointPositionList.get(con.getToPoint());

        int xMin = Math.min(fromPos.getX(), toPos.getX());
        int xMax = Math.max(fromPos.getX(), toPos.getX());
        int yMin = Math.min(fromPos.getY(), toPos.getY());
        int yMax = Math.max(fromPos.getY(), toPos.getY());

        int xStart = Math.max(0, xMin - 10);
        int xEnd = Math.min(this.getSize().width, xMax + 10);
        int yStart = Math.max(0, yMin - 10);
        int yEnd = Math.min(this.getSize().height, yMax + 10);

        for(int x = xStart; x < xEnd; x++){
            for (int y = yStart; y < yEnd; y++) {
                if(this.matConnection[x][y] == ind){
                    this.matConnection[x][y] = EMPTY;
                }
            }
        }
    }

    private void deleteConnectionsNumber(int num){
        for (int i = 0; i < this.connectionListAll.size(); i++) {
            int from = this.connectionListAll.get(i).getFromPoint();
            int to = this.connectionListAll.get(i).getToPoint();

            if (from == num || to == num)
                deleteConnectionByIndex(i);
        }
    }

    private void deletePoint(int x, int y){
        int num = this.matPoint[x][y];
        deletePointFromMatrix(num);
        deleteConnectionsNumber(num);
        redrawImage();
    }

    private void deleteConnectionByIndex(int ind){
        Connection con = this.connectionListAll.get(ind);

        for (int i = 0; i < this.connectionList.size(); i++) {
            if(this.connectionList.get(i).equals(con)){
                this.connectionList.remove(i);
                break;
            }
        }

        deleteConnectionFromMatrix(ind);
    }

    private void deleteConnection(int x, int y){
        deleteConnectionByIndex(this.matConnection[x][y]);
        redrawImage();
    }

    // endregion

    // region 11. Getters and Setters

    public void setBlnCanDelete(boolean blnCanDelete) {
        this.blnCanDelete = blnCanDelete;
    }

    public void setAlgorithmType(int algorithmType) {
        this.algorithmType = algorithmType;
    }

    public void setBlnCanConnect(boolean blnCanConnect) {
        this.blnCanConnect = blnCanConnect;
    }

    public void setBlnCanDrawPoint(boolean blnCanDrawPoint) {
        this.blnCanDrawPoint = blnCanDrawPoint;
    }

    public boolean getAlgorithmRunning() {
        return algorithmRunning;
    }

    public String getStrText() {
        return strText;
    }


    // endregion

}
