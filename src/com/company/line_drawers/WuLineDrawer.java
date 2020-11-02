package com.company.line_drawers;
import com.company.LineDrawer;
import com.company.PixelDrawer;
import java.awt.Color;

import java.awt.*;

public class WuLineDrawer implements LineDrawer {
    private PixelDrawer pd;
    public WuLineDrawer(PixelDrawer pd) {
        this.pd = pd;
    }
  
    @Override
    public void drawLine(int x1, int y1, int x2, int y2) {
        boolean steep = Math.abs(y2 - y1) > Math.abs(x2 - x1);
        int t;
        if (steep)
        {
            t = x1;
            x1 = y1;
            y1 = t;
            t = x2;
            x2 = y2;
            y2 = t;
        }
        if (x1 > x2)
        {
            t = x1;
            x1 = x2;
            x2 = t;
            t = y2;
            y2 = y1;
            y1 = t;
        }

        drawPoint(steep, x1, y1, rightFloatPart(y1));
        drawPoint(steep, x2, y2, rightFloatPart(y1));
        float dx = x2 - x1;
        float dy = y2 - y1;
        float gradient = dx == 0 ? 1 : dy / dx;
        float y = y1 + gradient;
        for (int x = x1 + 1; x <= x2 - 1; x++)
        {
            drawPoint(steep, x, (int)y, rightFloatPart(y));
            drawPoint(steep, x, (int)y + 1, floatPart(y));
            y += gradient;
        }
    }

    void drawPoint(boolean steep, int x, int y, double intensity){
        double alfaChanel = intensity * 255;
        pd.drawPixel(steep ? y : x, steep ? x : y, new Color(0, 0, 0, (int) alfaChanel));
    }

    private double floatPart (double x){
        return x-Math.floor(x);
    }

    private double rightFloatPart (double x){
        return 1-floatPart(x);
    }
}
