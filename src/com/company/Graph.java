package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.util.ArrayList;
import java.util.List;


public class Graph extends JComponent {

    // region 0. Constants

    private static final int EMPTY = -1;

    private static final int NOT_DEFINED = -1;

    private static final double NULL_MESSAGE = -1;

    // endregion

    // region 1. Init widgets

    private Image image;
    private Graphics2D graphics;

    private int radius;
    private int numberPoints;
    private int indexFromPoint;
    private int indexToPoint;
    private boolean blnCanDrawPoint;
    private boolean blnCanConnect;

    private int[][] mat;
    private List<PointPosition> pointPositionList;
    private List<Connection> connectionList;

    // endregion

    // region 2. Constructor

    public Graph() {
        this.numberPoints = 0;
        this.radius = 50;
        this.blnCanDrawPoint = false;
        this.blnCanConnect = false;

        this.indexFromPoint = NOT_DEFINED;
        this.indexToPoint = NOT_DEFINED;

        this.pointPositionList = new ArrayList<>();
        this.connectionList = new ArrayList<>();

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent event) {
                if(blnCanDrawPoint) {
                    setNewPoint(event.getX(), event.getY());

                } else if(blnCanConnect) {
                    setNewConnection(event.getX(), event.getY());
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
                }
            }
        });

    }

    // endregion

    // region 3. Main functions

    private void setNewPoint(int x, int y){
        drawPoint(x, y);
        this.blnCanDrawPoint = false;
    }

    private void setNewConnection(int x, int y){
        if(mat[x][y] != EMPTY){
            if(this.indexFromPoint == NOT_DEFINED){
                this.indexFromPoint = mat[x][y];
            } else if(this.indexToPoint == NOT_DEFINED && mat[x][y] != this.indexFromPoint){
                this.indexToPoint = mat[x][y];
                double weight = openWindow();

                if (weight != NULL_MESSAGE) {
                    drawConnection(this.indexFromPoint, this.indexToPoint);

                    this.connectionList.add(
                            new Connection(this.indexFromPoint, this.indexToPoint, weight)
                    );
                }

                this.clearIndexes();
                this.blnCanConnect = false;
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

    private void drawPoint(int x, int y){
        if(this.graphics != null && this.blnCanDrawPoint){
            this.graphics.setPaint(Color.CYAN);
            this.graphics.fillOval(x - radius/2, y - radius/2, radius, radius);

            setNewElement(x, y);
            drawCircle(x, y, Color.BLACK);
        }
    }

    public void drawCircle(int x, int y, Color color){
        if(this.graphics != null){

            this.graphics.setPaint(color);
            this.graphics.fillOval(x - radius/2, y - radius/2, radius, radius);

            this.repaint();
        }
    }

    // endregion

    // region 7. Draw Connection

    private void drawConnection(int indexFromPoint, int indexToPoint){

        PointPosition pointFrom = pointPositionList.get(indexFromPoint);
        PointPosition pointTo = pointPositionList.get(indexToPoint);

        this.graphics.setPaint(Color.black);
        this.graphics.drawLine(
                pointFrom.getX(), pointFrom.getY(), pointTo.getX(), pointTo.getY());

        this.repaint();
    }

    public void clearIndexes(){
        this.indexFromPoint = this.indexToPoint = NOT_DEFINED;
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

    private void redrawImage(){
        clearImage();
        redrawPoints();
        redrawConnections();
    }

    private void redrawConnections() {
        for (Connection connection : this.connectionList){
            drawConnection(connection.getFromPoint(), connection.getToPoint());
        }
    }

    private void redrawPoints(){
        for(PointPosition point : this.pointPositionList){
            drawCircle(point.getX(), point.getY(), Color.BLACK);
        }
    }

    // endregion

    // region 9. Getters and Setters

    public void setBlnCanConnect(boolean blnCanConnect) {
        this.blnCanConnect = blnCanConnect;
    }

    public void setBlnCanDrawPoint(boolean blnCanDrawPoint) {
        this.blnCanDrawPoint = blnCanDrawPoint;
    }

    // endregion

}
