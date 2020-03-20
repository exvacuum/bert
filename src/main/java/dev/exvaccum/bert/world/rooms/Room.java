package dev.exvaccum.bert.world.rooms;

import dev.exvaccum.bert.Bert;
import dev.exvaccum.bert.control.environment.light.LightingSystem;
import dev.exvaccum.bert.world.objects.GameObject;
import dev.exvaccum.bert.world.objects.InteractiveObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class Room extends JDialog implements ComponentListener, MouseListener {

    //List of objects in the room
    private ArrayList<GameObject> objects = new ArrayList<GameObject>();

    //Window insets, necessary for drawing calculations
    private Insets decoration;

    public JPanel pane;

    //Old location
    public Point oldLocation;
    public int dx, dy;
    World world;
    public String title = "Room";

    //Lighting
    public LightingSystem lightingSystem;

    /**
     * Basic room class, player-visitable area
     */
    public Room() {
        super(Bert.mBert);

        //Add input listeners
        addKeyListener(Bert.mBert.input);
        addMouseListener(Bert.mBert.input);
        addMouseMotionListener(Bert.mBert.input);

        //Create panel for graphics
        pane = new Pane();
        add(pane);

        //Hide on close, will cause game to end when closed
        setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
        addComponentListener(this);
        addMouseListener(this);

        //Disable resize
        setResizable(false);
        setVisible(true);

        //Check insets
        decoration = getInsets();

        //Set title and world
        setTitle(title);
        world = Bert.mBert.world;

        //Calibrate position
        reCalibrate();

        //Initiate lighting system
        lightingSystem = new LightingSystem(this);
    }

    /**
     * Calibrate window location
     */
    public void reCalibrate(){
        oldLocation = getLocation();
    }

    /**
     * @return a list of objects in the room
     */
    public ArrayList<GameObject> getObjects() {
        return objects;
    }

    /**
     * @return Bounds of room, taking insets into account
     */
    public Rectangle getWindowBounds() {
        return new Rectangle(getX()+decoration.left, getY(), getWidth()-decoration.left-decoration.right, getHeight()-decoration.bottom);
    }

    /**
     * Paints things to panel. To be overridden
     * @param g2 Graphics2D object for drawing
     */
    public void paint(Graphics2D g2){
    }

    /**
     * Handle game logic
     */
    public void update(){
        objects.forEach(object -> {object.update();});
        lightingSystem.update();
    }

    /**
     * @return Interactive object which is in range and closest to player
     */
    public InteractiveObject getInteractiveObjectClosestToPlayer(){
        InteractiveObject closest = null;
        for (GameObject o:objects) {
            if(o.isInteractive()){
                if(o.isClose()){
                    if(closest==null||o.getDistanceToPlayer()<closest.getDistanceToPlayer()){
                        closest = (InteractiveObject) o;
                    }
                }
            }
        }
        return closest;
    }

    @Override
    public void componentResized(ComponentEvent componentEvent) {

    }

    @Override
    public void componentMoved(ComponentEvent componentEvent) {

        //Shift player with window if necessary
        if(Bert.mBert.world.currentRoom!=null&&!Bert.mBert.ethereal){
            if(Bert.mBert.world.currentRoom.equals(this)){
                dx = oldLocation.x-getLocation().x;
                dy = oldLocation.y-getLocation().y;
                oldLocation = getLocation();
                Bert.mBert.world.player.position = new Point(Bert.mBert.world.player.position.x-dx, Bert.mBert.world.player.position.y-dy);
                Bert.mBert.world.updateCurrentRoom();
            }
        }
    }

    @Override
    public void componentShown(ComponentEvent componentEvent) {

    }

    @Override
    public void componentHidden(ComponentEvent componentEvent) {
        System.exit(0);
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {

    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        Bert.mBert.controller= Bert.ControllerObj.PLAYER;
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }

    class Pane extends JPanel{

        {
            setBackground(Color.WHITE);
        }

        @Override
        public void paintComponent(Graphics g){
            Graphics2D g2 = (Graphics2D) g;
            super.paintComponent(g2);
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            Room.this.paint(g2);
        }
    }
}
