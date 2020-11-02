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
        drawPoint(steep, x1, y1, rfpart(y1));
        drawPoint(steep, x2, y2, rfpart(y1));
        float dx = x2 - x1;
        float dy = y2 - y1;
        float gradient = dy / dx;
        float y = y1 + gradient;
        for (int x = x1 + 1; x <= x2 - 1; x++)
        {
            drawPoint(steep, x, (int)y, rfpart(y));
            drawPoint(steep, x, (int)y + 1, fpart(y));
            y += gradient;
        }
    }

    void drawPoint(boolean steep, int x, int y, double intensity){
        double alfaChanel = Math.round(intensity*100.0)/100.0;
        pd.drawPixel(steep ? y : x, steep ? x : y, steep ? new Color(blend(0xffffff, 0x0000ff, (float) alfaChanel))  : new Color(blend(0xffffff, 0xff0000, (float) alfaChanel)));
    }

    private int blend (int a, int b, float ratio) {
        if (ratio > 1f) {
            ratio = 1f;
        } else if (ratio < 0f) {
            ratio = 0f;
        }

        float iRatio = 1.0f - ratio;

        int aA = (a >> 24 & 0xff);
        int aR = ((a & 0xff0000) >> 16);
        int aG = ((a & 0xff00) >> 8);
        int aB = (a & 0xff);

        int bA = (b >> 24 & 0xff);
        int bR = ((b & 0xff0000) >> 16);
        int bG = ((b & 0xff00) >> 8);
        int bB = (b & 0xff);

        int A = (int)((aA * iRatio) + (bA * ratio));
        int R = (int)((aR * iRatio) + (bR * ratio));
        int G = (int)((aG * iRatio) + (bG * ratio));
        int B = (int)((aB * iRatio) + (bB * ratio));

        return A << 24 | R << 16 | G << 8 | B;
    }

    private double fpart (double x){
        return x-Math.floor(x);
    }

    private double rfpart (double x){
        return 1-fpart(x);
    }
}
