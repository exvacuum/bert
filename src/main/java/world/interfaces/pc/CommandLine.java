package world.interfaces.pc;

import world.interfaces.PCInterface;

import java.awt.*;
import java.util.ArrayList;

public class CommandLine extends MetaWindow {

    public ArrayList<String> lines = new ArrayList<>();

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
    }
}
