package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class DrawArea extends JComponent {

    // region 0. Constants

    // endregion

    // region 1. Init widgets

    private Image image;

    private Graphics2D graphics;

    private int radius;

    // endregion

    // region 2. Constructor

    public DrawArea() {
        this.setDoubleBuffered(false);

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent event) {
                drawCircle(event.getX(), event.getY());
            }
        });

        this.radius = 50;
    }


    // endregion

    // region 3. Overrided functions

    @Override
    protected void paintComponent(Graphics g) {

        if(this.image == null){
            this.image = this.createImage(this.getSize().width, this.getSize().height);

            this.graphics = (Graphics2D) this.image.getGraphics();
            this.graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            this.clear();
        }
        g.drawImage(this.image, 0, 0, null);

    }

    // endregion

    // region 4. Objects functions

    public void clear(){
        this.graphics.setPaint(Color.white);

        this.graphics.fillRect(0, 0, getSize().width, getSize().height);
        this.graphics.setPaint(getForeground());

        this.graphics.setPaint(Color.black);

        this.repaint();

    }

    // endregion

    // region 5. Draw

    public void drawCircle(int x, int y){
        if(this.graphics != null){
            System.out.printf("%d %d\n", x, y);

            this.graphics.setPaint(Color.black);
            this.graphics.drawOval(x - radius/2, y - radius/2, radius, radius);


            this.repaint();
        }
    }

    // endregion
}
