package dev.exvaccum.bert.world.interfaces.pc;

import dev.exvaccum.bert.world.interfaces.PCInterface;

import java.awt.*;
import java.util.ArrayList;

public class CommandLine extends MetaWindow {

    //Lines in console
    public ArrayList<String> lines = new ArrayList<>();

    /**
     * CLI
     * @param x x-coordinate of window
     * @param y y-coordinate of window
     * @param width width of window
     * @param height  height of window
     * @param owner PC interface to which this window belongs
     */
    public CommandLine(int x, int y, int width, int height, PCInterface owner) {
        super(x, y, width, height, owner);
        title = "Command Line Interface";
        bgColor = new Color(0,0,0,150);
        type = WindowType.CMD;
    }

    @Override
    public void update(){
        super.update();
        if(focused){
        }
    }

    @Override
    public void draw(Graphics2D g2) {
        super.draw(g2);
        g2.setFont(segoe);
        g2.drawString("THIS WILL WORK SOON",bounds.x+10,bounds.y+50);
    }
}
