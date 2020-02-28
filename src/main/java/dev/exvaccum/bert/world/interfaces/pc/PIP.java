package dev.exvaccum.bert.world.interfaces.pc;

import dev.exvaccum.bert.Bert;
import dev.exvaccum.bert.world.interfaces.PCInterface;

import java.awt.*;

public class PIP extends MetaWindow {

    /**
     * Picture-in-picture mode, contains a copy of the desktop
     * @param x x-coordinate of window
     * @param y y-coordinate of window
     * @param width width of window
     * @param height  height of window
     * @param owner PC interface to which this window belongs
     */
    public PIP(int x, int y, int width, int height, PCInterface owner) {
        super(x, y, width, height, owner);
        title = "Picture-In-Picture";
        type = WindowType.PIP;
    }

    @Override
    public void draw(Graphics2D g2) {
        super.draw(g2);

        //Drawing breaks down after a bit, hidden message can be seen if window manipulated
        if(bounds.width>300) {
            g2.setColor(Color.WHITE);
            g2.setFont(Bert.mBert.h2Font);
            g2.drawString("2gC-7524", bounds.x, bounds.y + titleBar.height + 55);
        }

        //Draw desktop
        g2.drawImage(owner.screenImg, bounds.x,bounds.y+titleBar.height,bounds.width,bounds.height-titleBar.height,null);
    }
}
