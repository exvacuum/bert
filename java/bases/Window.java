package bases;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import main.Player;

public class Window extends JFrame implements ComponentListener{

    int width, height, x, y;
    float interpolation;
    Room room;
    String name;

    Window(Room room){
        this.room = room;
        this.addComponentListener(this);
        setup();
    }

    void setup(){
        setVisible(false);
        x = room.x;
        y = room.y;
        width = room.width;
        height = room.height;
        name = room.name;
        setTitle(name);
        setLocation(x,y);
        setSize(width,height);
        Renderer renderer = new Renderer();
        add(renderer);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    @Override
    public void componentResized(ComponentEvent componentEvent) {

    }

    @Override
    public void componentMoved(ComponentEvent componentEvent) {
//        Player.player.u = 0;
//        Player.player.d = 0;
//        Player.player.l = 0;
//        Player.player.r = 0;
    }

    @Override
    public void componentShown(ComponentEvent componentEvent) {

    }

    @Override
    public void componentHidden(ComponentEvent componentEvent) {

    }

    private class Renderer extends JPanel {

        @Override
        public void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;
            super.paintComponent(g);
            room.paint(g2, interpolation);
        }
    }
}
