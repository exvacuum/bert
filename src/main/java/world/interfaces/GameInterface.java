package world.interfaces;

import bert.App;
import control.MButton;
import world.objects.GameObject;
import world.rooms.Room;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GameInterface extends JDialog{

    public InterfacePanel pane;

    GameObject ownerObject;
    Room owner;
    public boolean usable = true;
    public ArrayList<MButton> buttons = new ArrayList<>();

    public GameInterface(GameObject owner) {
        super(owner.room);
        addKeyListener(App.mApp.input);
        addMouseListener(App.mApp.input);
        addMouseMotionListener(App.mApp.input);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        this.ownerObject = owner;
        init();
    }

    void init(){
        setSize(640,480);
        pane = new InterfacePanel(this);
        add(pane);
        setVisible(true);
    }

    public void update(){
        if(!ownerObject.isClose()){
            usable = false;
        }else{
            usable = true;
        }
    }

    public void destroy(){
        App.mApp.interfaces.remove(App.mApp.interfaces.indexOf(this));
        dispose();
    }

    public void paintGraphics(Graphics2D g2){
    };

    public void draw(){
        pane.repaint();
    }
}
