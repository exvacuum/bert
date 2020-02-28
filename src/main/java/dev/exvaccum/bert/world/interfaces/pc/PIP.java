package dev.exvaccum.bert.world.interfaces.pc;

import dev.exvaccum.bert.Bert;
import dev.exvaccum.bert.world.interfaces.PCInterface;

import java.awt.*;

public class PIP extends MetaWindow {

    public PIP(int x, int y, int width, int height, PCInterface owner) {
        super(x, y, width, height, owner);
        title = "Picture-In-Picture";
        type = WindowType.PIP;
    }

    @Override
    public void draw(Graphics2D g2) {
        super.draw(g2);
        if(bounds.width>300) {
            g2.setColor(Color.WHITE);
            g2.setFont(Bert.mBert.h2Font);
            g2.drawString("2gC-7524", bounds.x, bounds.y + titleBar.height + 55);
        }
        g2.drawImage(owner.screenImg, bounds.x,bounds.y+titleBar.height,bounds.width,bounds.height-titleBar.height,null);
    }
}
