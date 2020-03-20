package dev.exvaccum.bert.world.interfaces.pc;

import dev.exvaccum.bert.Bert;
import dev.exvaccum.bert.control.Utilities;
import dev.exvaccum.bert.control.WButton;
import dev.exvaccum.bert.control.WDraggable;
import dev.exvaccum.bert.world.interfaces.PCInterface;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

public class MetaWindow{

    //Type of window
    public WindowType type = WindowType.DEFAULT;
    public enum WindowType{
        DEFAULT,
        CMD,
        PIP,
        FILES,
        IMAGE_VIEWER,
        NOTEPAD,
        DIALOG
    }

    //Incrementing int for assigning unique ids to the windows
    public static int nextid = 0;
    public int id;

    public boolean resizable = true;
    String title = "";
    public Font segoe = new Font("Segoe UI", Font.PLAIN, 12);

    //Owner PC Interface
    public PCInterface owner;

    //Bounds of window and title bar
    public Rectangle titleBar;
    public boolean movable;
    public WButton closeButton;
    //Load close button icon
    static BufferedImage closeIcon;
    static{
        try{
            closeIcon = ImageIO.read(Utilities.getResourceAsFile("closeicon.png"));
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public Rectangle bounds = new Rectangle(0,0,0,0);

    //Window GUI buttons
    public ArrayList<WButton> windowButtons = new ArrayList<>();
    public ArrayList<WDraggable> windowDraggables = new ArrayList<>();

    //Window edge bounds
    Rectangle[] edges;
    public boolean[] edgeHovers = new boolean[4];

    public boolean focused;

    Color bgColor;

    /**
     * Window elements inside of a PC interface
     * @param x x-coordinate of window
     * @param y y-coordinate of window
     * @param width width of window
     * @param height  height of window
     * @param owner PC interface to which this window belongs
     */
    MetaWindow(int x, int y, int width, int height, PCInterface owner){
        this.owner = owner;

        //Set id and increment next id
        id = MetaWindow.nextid;
        MetaWindow.nextid++;

        //Set boundaries
        bounds.setBounds(x,y,width,height);
        titleBar = new Rectangle(x,y,width,20);
        edges = new Rectangle[]{new Rectangle(x-5,y-25,5,height+30),new Rectangle(width,y-25,5,height+30),new Rectangle(x-5,y-25,width+10,5),new Rectangle(x-5,height,width+10,5)};

        //Create close button
        closeButton = new WButton(x+width-20,y,20,20, this){
            {
                setImg(closeIcon);
                setBordered(false);
            }

            @Override
            public void performAction() {
                close();
                windowButtons.remove(this);
            }
        };
        windowButtons.add(closeButton);
        bgColor = Color.BLACK;
    }

    public void draw(Graphics2D g2){

        //Draw background
        g2.setColor(bgColor);
        g2.fillRect(bounds.x,bounds.y,bounds.width,bounds.height);

        //Draw title bar
        g2.setColor(Color.WHITE);
        g2.fillRect(titleBar.x,titleBar.y,titleBar.width,titleBar.height);
        g2.setColor(Color.BLACK);
        g2.setFont(segoe);
        g2.drawString(title,bounds.x+5,bounds.y+15);

        //Draw GUI buttons
        for (int i = 0; i < windowButtons.size(); i++) {
            windowButtons.get(i).draw(g2);
        }

        //Draw overlay if not focused
        if(!focused){
            g2.setColor(new Color(0,0,0,100));
            g2.fillRect(titleBar.x,titleBar.y,bounds.width,bounds.height);
        }
    }

    /**
     * Handle game logic
     */
    public void update(){

        //If window is at the end of the windows array, it is the currently-focused one
        focused=owner.windows.indexOf(this)==owner.windows.size()-1;

        //Update boundaries
        titleBar = new Rectangle(bounds.x,bounds.y,bounds.width,20);
        edges = new Rectangle[]{new Rectangle(bounds.x-5,bounds.y-5,10,bounds.height+10),new Rectangle(bounds.x+bounds.width-5,bounds.y-5,10,bounds.height+10),new Rectangle(bounds.x-5,bounds.y-5,bounds.width+10,10),new Rectangle(bounds.x-5,bounds.y+bounds.height-5,bounds.width+10,10)};
        closeButton.setLocation(bounds.x+bounds.width-20,bounds.y);

        //Check edge and title bar hovering
        edgeHovers = new boolean[4];
        for (int i = 0; i < 4; i++) {
            if (edges[i].contains(Bert.mBert.input.mouseLocation.getX()-owner.getX()-owner.getInsets().left, Bert.mBert.input.mouseLocation.getY()-owner.getY()-owner.getInsets().top)){
                edgeHovers[i] = true;
            }
        }
        movable = titleBar.contains(Bert.mBert.input.mouseLocation.getX()-owner.getX()-owner.getInsets().left, Bert.mBert.input.mouseLocation.getY()-owner.getY()-owner.getInsets().top);
    }

    /**
     * Closes and removes this window
     */
    void close(){
        owner.windows.remove(owner.windows.indexOf(MetaWindow.this));
    }

    public boolean contains(Point p){
        return bounds.contains(p);
    }

    public boolean contains(int x, int y){
        return bounds.contains(x,y);
    }
}
