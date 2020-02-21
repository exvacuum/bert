package world.rooms;

import bert.App;
import world.objects.GameObject;
import world.objects.InteractiveObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.ArrayList;

public class Room extends JDialog implements ComponentListener {
    private ArrayList<GameObject> objects = new ArrayList<GameObject>();
    private Insets decoration;
    private Rectangle bounds;
    public JPanel pane;
    public Point oldLocation, relLocation;
    public int dx, dy;
    World world;
    boolean shouldTranslate = true;
    public String title = "Room";

    public Room(App owner) {
        super(owner);
        addKeyListener(App.mApp.input);
        addMouseListener(App.mApp.input);
        addMouseMotionListener(App.mApp.input);
        pane = new Pane();
        add(pane);
        setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
        addComponentListener(this);
        setResizable(false);
        setVisible(true);
        decoration = getInsets();
        setTitle(title);
        world = owner.world;
        reCalibrate();
    }

    public void reCalibrate(){
        oldLocation = getLocation();
        relLocation = getLocation();
    }

    public ArrayList<GameObject> getObjects() {
        return objects;
    }

    public Rectangle getWindowBounds() {
        return new Rectangle(getX()+decoration.left, getY(), getWidth()-decoration.left-decoration.right, getHeight()-decoration.bottom);
    }

    public boolean contains(Rectangle r){
        return bounds.contains(r);
    }

    public void paint(Graphics2D g2){
    }

    public void update(){
    }

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
        if(App.mApp.world.currentRoom!=null&&!App.mApp.ethereal){
            if(App.mApp.world.currentRoom.equals(this)){
                dx = oldLocation.x-getLocation().x;
                dy = oldLocation.y-getLocation().y;
                oldLocation = getLocation();
                App.mApp.world.player.setLocation(App.mApp.world.player.getLocation().x-dx, App.mApp.world.player.getLocation().y-dy);
                App.mApp.world.updateCurrentRoom();
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
