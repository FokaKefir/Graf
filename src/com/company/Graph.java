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

    // endregion

    // region 1. Init widgets

    private Image image;
    private Graphics2D graphics;

    private int radius;
    private int numberPoints;
    private int indexFromPoint;
    private int indexToPoint;
    private boolean blnCanDraw;
    private boolean blnCanConnect;

    private int[][] mat;
    private List<PointPosition> pointPositionList;
    private List<Connection> connectionList;

    // endregion

    // region 2. Constructor

    public Graph() {
        this.setDoubleBuffered(false);

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent event) {
                if(blnCanDraw)
                    setNewPoint(event.getX(), event.getY());
                else if(blnCanConnect)
                    setNewConnection(event.getX(), event.getY());

            }
        });

        this.numberPoints = 0;
        this.radius = 50;
        this.blnCanDraw = false;
        this.blnCanConnect = false;

        this.indexFromPoint = NOT_DEFINED;
        this.indexToPoint = NOT_DEFINED;

        this.pointPositionList = new ArrayList<>();
        this.connectionList = new ArrayList<>();

    }

    // endregion

    // region 3.  Main functions

    private void setNewPoint(int x, int y){
        drawPoint(x, y);
    }

    private void setNewConnection(int x, int y){
        if(mat[x][y] != EMPTY){
            if(this.indexFromPoint == NOT_DEFINED){
                this.indexFromPoint = mat[x][y];
            } else if(this.indexToPoint == NOT_DEFINED && mat[x][y] != this.indexFromPoint){
                this.indexToPoint = mat[x][y];
                
                this.drawConnection();

                this.connectionList.add(
                        new Connection(this.indexFromPoint, this.indexToPoint, 0)
                );

                this.clearIndexes();
            }
        }
    }

    // endregion

    // region 4. Setup the Image

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
        if(this.graphics != null && this.blnCanDraw){
            this.graphics.setPaint(Color.CYAN);
            this.graphics.fillOval(x - radius/2, y - radius/2, radius, radius);

            setNewElement(x, y);
            drawCircle(x, y, Color.BLACK);

            this.blnCanDraw = false;
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

    private void drawConnection(){

        PointPosition pointFrom = pointPositionList.get(this.indexFromPoint);
        PointPosition pointTo = pointPositionList.get(this.indexToPoint);

        this.graphics.setPaint(Color.black);
        this.graphics.drawLine(
                pointFrom.getX(), pointFrom.getY(), pointTo.getX(), pointTo.getY());

        this.repaint();

        this.blnCanConnect = false;
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

    // region 9. Getters and Setters

    public int getIndexFromPoint() {
        return indexFromPoint;
    }

    public int getIndexToPoint() {
        return indexToPoint;
    }

    public boolean getBlnCanConnect() {
        return blnCanConnect;
    }

    public boolean getBlnCanDraw() {
        return blnCanDraw;
    }

    public void setIndexFromPoint(int indexFromPoint) {
        this.indexFromPoint = indexFromPoint;
    }

    public void setIndexToPoint(int indexToPoint) {
        this.indexToPoint = indexToPoint;
    }

    public void setBlnCanConnect(boolean blnCanConnect) {
        this.blnCanConnect = blnCanConnect;
    }

    public void setBlnCanDraw(boolean blnCanDraw) {
        this.blnCanDraw = blnCanDraw;
    }

    // endregion

}
