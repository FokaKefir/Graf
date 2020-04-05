package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;


public class Graph extends JComponent {

    // region 0. Constants

    // endregion

    // region 1. Init widgets

    private Image image;
    private Graphics2D graphics;

    private int radius;
    private int numberPoints;
    private boolean blnCanDraw;

    private int[][] mat;

    // endregion

    // region 2. Constructor

    public Graph() {
        this.setDoubleBuffered(false);

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent event) {
                drawPoint(event.getX(), event.getY());
            }
        });

        this.numberPoints = 0;
        this.radius = 50;
        this.blnCanDraw = false;

    }

    // endregion

    // region 3. Setup the Image

    @Override
    protected void paintComponent(Graphics g) {

        if(this.image == null){
            this.image = this.createImage(this.getSize().width, this.getSize().height);
            this.mat = new int[this.getSize().width][this.getSize().height];

            this.graphics = (Graphics2D) this.image.getGraphics();
            this.graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            this.clear();
        }
        g.drawImage(this.image, 0, 0, null);

    }

    // endregion

    // region 4. Clear the paint

    public void clear(){
        this.graphics.setPaint(Color.white);

        this.graphics.fillRect(0, 0, getSize().width, getSize().height);
        this.graphics.setPaint(getForeground());

        this.graphics.setPaint(Color.black);

        this.repaint();

    }

    // endregion

    // region 5. Draw

    private void drawPoint(int x, int y){
        if(this.graphics != null && this.blnCanDraw){
            this.graphics.setPaint(Color.CYAN);
            this.graphics.fillOval(x - radius/2, y - radius/2, radius, radius);

            setMatrixNewElement();

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

    // region 6. Matrix functions and methods

    private void setMatrixNewElement(){
        BufferedImage bufferedImage = (BufferedImage) this.image;
        this.numberPoints++;

        for(int x = 0; x < this.getSize().width; x++){
            for (int y = 0; y < this.getSize().height; y++) {
                int rgb = bufferedImage.getRGB(x, y);
                if(rgb == Color.CYAN.getRGB()){
                    this.mat[x][y] = this.numberPoints;
                }
            }
        }
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

    // region 7. Getters and Setters

    public boolean isBlnCanDraw() {
        return blnCanDraw;
    }

    public void setBlnCanDraw(boolean blnCanDraw) {
        this.blnCanDraw = blnCanDraw;
    }

    // endregion

}
