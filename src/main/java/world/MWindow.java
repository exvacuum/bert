package world;

import sprites.TestPane;

import javax.swing.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

public class MWindow extends JDialog implements ComponentListener {

    //Private Vars

    /**
     * Creates a new window
     * @param x X-Position
     * @param y Y-Position
     * @param w Width
     * @param h Height
     */
    public MWindow(int x, int y, int w, int h){
        setSize(w,h);
        setTitle("0623");
        setLocation(x,y);
        setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
        addComponentListener(this);
        add(new TestPane());
        setVisible(true);
    }

    @Override
    public void componentResized(ComponentEvent componentEvent) {

    }

    @Override
    public void componentMoved(ComponentEvent componentEvent) {

    }

    @Override
    public void componentShown(ComponentEvent componentEvent) {

    }

    @Override
    public void componentHidden(ComponentEvent componentEvent) {
        System.exit(0);
    }
}
