package bases;

import java.awt.*;

public class Room {
    Window win;
    String name;
    int width,height,x,y;
    WindowType winType;

    public Room(){

        win = new Window(this);
    }

    //Load a prebuilt room
    public Room(WindowStaticType winType){

        this.winType = winType;
        name = winType.getName();
        x = winType.getRelativeX();
        y = winType.getRelativeY();
        width = winType.getWidth();
        height = winType.getHeight();
        win = new Window(this);
    }

    //Generate a new room
    public Room(WindowRandomType winType){
    }

    public void draw(float interpolation){
        win.interpolation = interpolation;
        win.repaint();
    }

    public void paint(Graphics2D g2, float interpolation){
        winType.paint(g2, interpolation);
    }
}
