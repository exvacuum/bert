package dev.exvaccum.bert.world.interfaces.pc;

import dev.exvaccum.bert.Bert;
import dev.exvaccum.bert.world.interfaces.PCInterface;
import dev.exvaccum.bert.world.interfaces.pc.files.MetaFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class PopUp extends MetaWindow {

    //Message to view
    public String message;

    /**
     * Dialog box, shows a popup
     * @param x x-coordinate of window
     * @param y y-coordinate of window
     * @param width width of window
     * @param height  height of window
     * @param owner PC interface to which this window belongs
     */
    public PopUp(int x, int y, int width, int height, PCInterface owner, String message) {
        super(x, y, width, height, owner);
        title = "";
        bgColor = new Color(255,255,255);
        type = WindowType.DIALOG;
        resizable = false;
        this.message = message;
        bounds.height += titleBar.height;
    }

    @Override
    public void draw(Graphics2D g2) {
        super.draw(g2);
        g2.setColor(Color.BLACK);
        g2.drawString(message,bounds.x+25,bounds.y+ bounds.height/2);
    }
}
