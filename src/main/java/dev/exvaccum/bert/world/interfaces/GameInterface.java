package dev.exvaccum.bert.world.interfaces;

import dev.exvaccum.bert.Bert;
import dev.exvaccum.bert.control.MButton;
import dev.exvaccum.bert.world.objects.GameObject;
import dev.exvaccum.bert.world.rooms.Room;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GameInterface extends JDialog{

    //Panel for drawing
    public InterfacePanel pane;

    //Object from which this interface was generated
    GameObject ownerObject;

    //Whether object can be used currently
    public boolean usable = true;

    //List of GUI buttons in the interface
    public ArrayList<MButton> buttons = new ArrayList<>();

    /**
     * Create new user interface
     * @param owner Object from which this interface was generated
     */
    public GameInterface(GameObject owner) {
        super(owner.room);

        //Add input listeners
        addKeyListener(Bert.mBert.input);
        addMouseListener(Bert.mBert.input);
        addMouseMotionListener(Bert.mBert.input);

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        this.ownerObject = owner;
        init();
    }

    /**
     * Initialize user interface window
     */
    void init(){
        setSize(640,480);
        pane = new InterfacePanel(this);
        add(pane);
        setVisible(true);
    }

    /**
     * Handle game logic
     */
    public void update(){
        if(!ownerObject.isClose()){
            usable = false;
        }else{
            usable = true;
        }
    }

    /**
     * Get rid of this interface
     */
    public void destroy(){
        Bert.mBert.interfaces.remove(Bert.mBert.interfaces.indexOf(this));
        dispose();
    }

    public void paintGraphics(Graphics2D g2){
    };

    public void draw(){
        pane.repaint();
    }
}
