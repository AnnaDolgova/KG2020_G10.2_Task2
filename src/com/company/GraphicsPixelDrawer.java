package com.company;

import java.awt.*;

public class GraphicsPixelDrawer
implements PixelDrawer{
    private Graphics g;

    public GraphicsPixelDrawer(Graphics g) {
        this.g = g;
    }

    @Override
    public void drawPixel(int x, int y, Color c) {
      g.setColor(c);
      g.fillRect(x, y, 1, 1);
    }
    //public void drawPoint(){}
}
