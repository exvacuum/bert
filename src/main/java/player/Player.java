package player;

import bert.App;
import sprites.SpriteEngine;
import sprites.SpriteSheet;
import sprites.SpriteSheetBuilder;
import world.rooms.Room;
import world.rooms.World;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player extends JDialog implements FocusListener {

    enum ControlState{
        PLATFORMER,
        FLYING
    }
    public ControlState controlState = ControlState.PLATFORMER;

    //Spritesheet
    SpriteSheet sheet;
    public JPanel pane;
    SpriteEngine engine;
    Rectangle bounds = new Rectangle(getX()+16,getY()+32,64,128);
    public Point relPos;
    public int u,d,l,r;
    public int xScale = 1;
    public int vel = 1;
    public Player(Frame owner){
        super(owner);
        init();
    }

    void init() {

        addKeyListener(App.mApp.input);
        addMouseListener(App.mApp.input);
        addMouseMotionListener(App.mApp.input);

        try {
            BufferedImage img = ImageIO.read(App.getResourceAsFile("playerStrip.png"));
            sheet = new SpriteSheetBuilder().
                    withSheet(img).
                    withColumns(4).
                    withRows(1).
                    withSpriteCount(4).
                    withSpriteSize(96, 192).
                    build();


            engine = new SpriteEngine(60,1);

            engine.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    repaint();
                }
            });

            engine.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

        setUndecorated(true);
        setSize(96,192);
        setBackground(new Color(0,0,0,0));
        pane = new PlayerPane();
        pane.setOpaque(false);
        add(pane);
        addFocusListener(this);
        setLocation(Toolkit.getDefaultToolkit().getScreenSize().width/2-getWidth()/2-60,Toolkit.getDefaultToolkit().getScreenSize().height/2-getHeight()/2+130);
        setVisible(true);
        requestFocus();
    }

    public void paint(Graphics2D g2, double progress) {
        g2.drawImage(sheet.getSprite(progress), 0-(sheet.getSprite(progress).getWidth()*Math.min(0,xScale)), 0,sheet.getSprite(progress).getWidth()*xScale,sheet.getSprite(progress).getHeight(), null);
        if(App.debug) {
            g2.setColor(Color.BLUE);
            g2.setStroke(new BasicStroke(3));
            g2.drawRect(bounds.x, bounds.y, bounds.width, bounds.height);
            g2.setColor(Color.RED);
            g2.drawRect(0, 0, getWidth(), getHeight());
        }
    }

    public Rectangle getBBox(){
        return bounds;
    }

    public void update(World world){
        if(r-l!=0||d-u!=0){
            if(r-l!=0)xScale = r-l;
            Room currentRoom = App.mApp.world.currentRoom;
            switch (controlState){
                case PLATFORMER:
                    if(new Rectangle(currentRoom.getX(),currentRoom.getY(),currentRoom.getWidth(),currentRoom.getHeight()).contains(new Rectangle((int)(getBBox().getX()+((r-l)*vel)),(int)(getBBox().getY()),getBBox().width,getBBox().height)))
                    setLocation(getX()+((r-l)*vel),getY());
                    break;
                case FLYING:
                    setLocation(getX()+((r-l)*vel),getY()+((d-u)*vel));
                    break;
            }
        }
        bounds = new Rectangle(getX()+16,getY()+32,64,128);
        relPos = new Point();
    }

    @Override
    public void focusGained(FocusEvent focusEvent) {
    }

    @Override
    public void focusLost(FocusEvent focusEvent) {
        u=d=l=r=0;
        requestFocus();
    }

    class PlayerPane extends JPanel{

        @Override
        public void paintComponent(Graphics g){
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            Player.this.paint(g2, engine.getCycleProgress());
        }
    }

}