package dev.exvaccum.bert.player;

import dev.exvaccum.bert.Bert;
import dev.exvaccum.bert.sprites.SpriteEngine;
import dev.exvaccum.bert.sprites.SpriteSheet;
import dev.exvaccum.bert.sprites.SpriteSheetBuilder;
import dev.exvaccum.bert.world.rooms.Room;
import dev.exvaccum.bert.world.rooms.World;

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

    //Player will experience various control styles throughout game
    public ControlState controlState = ControlState.PLATFORMER;
    enum ControlState{
        PLATFORMER,
        FLYING
    }

    //Spritesheet
    SpriteSheet sheet;
    SpriteEngine engine;

    //Collision bounds
    Rectangle bounds = new Rectangle(getX()+16,getY()+32,64,128);

    //Drawing panel
    public JPanel pane;

    //Position relative to
    public Point relPos;

    //Input directions
    public int u,d,l,r;

    //Horizontal mirroring control
    public int xScale = 1;

    //Movement velocity
    public int vel = 1;

    /**
     * Player object
     */
    public Player(){
        super(Bert.mBert);
        init();
    }

    /**
     * Initialize the player
     */
    void init() {

        //Add input listeners
        addKeyListener(Bert.mBert.input);
        addMouseListener(Bert.mBert.input);
        addMouseMotionListener(Bert.mBert.input);

        //Startup sprite engine
        try {
            BufferedImage img = ImageIO.read(Bert.getResourceAsFile("playerStrip.png"));
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

        //Setup transparent window
        setUndecorated(true);
        setSize(96,192);
        setBackground(new Color(0,0,0,0));
        pane = new PlayerPane();
        pane.setOpaque(false);
        add(pane);
        addFocusListener(this);
        setLocation(Toolkit.getDefaultToolkit().getScreenSize().width/2-getWidth()/2-60,Toolkit.getDefaultToolkit().getScreenSize().height/2-getHeight()/2+130);
        setVisible(true);

        //Focus this
        requestFocus();
    }

    public void paint(Graphics2D g2, double progress) {

        //Draw player sprite
        g2.drawImage(sheet.getSprite(progress), 0-(sheet.getSprite(progress).getWidth()*Math.min(0,xScale)), 0,sheet.getSprite(progress).getWidth()*xScale,sheet.getSprite(progress).getHeight(), null);

        if(Bert.debug) {
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

    /**
     * Handle game logic
     */
    public void update(){

        //Movement
        if(r-l!=0||d-u!=0){
            if(r-l!=0)xScale = r-l;
            Room currentRoom = Bert.mBert.world.updateCurrentRoom();
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
    }

    @Override
    public void focusGained(FocusEvent focusEvent) {
    }

    @Override
    public void focusLost(FocusEvent focusEvent) {

        //When window focus lost, cease movement and re-request focus
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