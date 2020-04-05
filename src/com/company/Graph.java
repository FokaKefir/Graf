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

    // endregion

    // region 1. Init widgets

    private Image image;
    private Graphics2D graphics;

    private int radius;
    private int numberPoints;
    private boolean blnCanDraw;
    private boolean blnCanConnect;

    private int[][] mat;
    private List<GraphPoint> points;

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

        this.points = new ArrayList<GraphPoint>();

    }

    // endregion

    // region 3.  Main functions

    private void setNewPoint(int x, int y){
        drawPoint(x, y);
    }

    private void setNewConnection(int x, int y){
        if(mat[x][y] != EMPTY){
            drawConnection(x, y);
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

            this.clear();
        }
        g.drawImage(this.image, 0, 0, null);

    }

    // endregion

    // region 5. Clear the paint

    public void clear(){
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

            if(this.numberPoints >= 1){
                this.blnCanConnect = true;
            }
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

    private void drawConnection(int x, int y){
        int indexFrom = this.numberPoints - 1;
        int indexTo = mat[x][y];

        GraphPoint pointFrom = points.get(indexFrom);
        GraphPoint pointTo = points.get(indexTo);

        this.graphics.setPaint(Color.black);
        this.graphics.drawLine(
                pointFrom.getPosition().width, pointFrom.getPosition().height, pointTo.getPosition().width, pointTo.getPosition().height);

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

        GraphPoint point = new GraphPoint(numberPoints, new Dimension(pointX, pointY));
        points.add(point);

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

    public boolean isBlnCanDraw() {
        return blnCanDraw;
    }

    public void setBlnCanDraw(boolean blnCanDraw) {
        this.blnCanDraw = blnCanDraw;
    }

    // endregion

}
