package world.interfaces.pc;

import bert.App;
import control.Input;
import control.MButton;
import control.WButton;
import org.checkerframework.checker.units.qual.A;
import world.interfaces.PCInterface;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

public class MetaWindow{

    public WindowType type = WindowType.DEFAULT;
    public enum WindowType{
        DEFAULT,
        CMD,
        PIP,
        FILES
    }

    public static int nextid = 0;
    public boolean resizable = true;

    String title = "";
    public Font segoe = new Font("Segoe UI", Font.PLAIN, 12);
    public PCInterface owner;
    public Rectangle titleBar;
    public Rectangle bounds = new Rectangle(0,0,0,0);
    Rectangle[] edges;
    public boolean[] edgeHovers = new boolean[4];
    public boolean movable;
    public boolean focused;
    public WButton closeButton;
    public int id;
    public ArrayList<WButton> windowButtons = new ArrayList<>();
    Color bgColor;

    static BufferedImage closeIcon;
    static{
        try{
            closeIcon = ImageIO.read(App.getResourceAsFile("closeicon.png"));
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    MetaWindow(int x, int y, int width, int height, PCInterface owner){
        id = MetaWindow.nextid;
        MetaWindow.nextid++;
        bounds.x = x;
        bounds.y = y;
        bounds.width = width;
        bounds.height = height;
        this.owner = owner;
        titleBar = new Rectangle(x,y,width,20);
        edges = new Rectangle[]{new Rectangle(x-5,y-25,5,height+30),new Rectangle(width,y-25,5,height+30),new Rectangle(x-5,y-25,width+10,5),new Rectangle(x-5,height,width+10,5)};
        closeButton = new WButton(x+width-20,y,20,20, this){
            {
                setImg(closeIcon);
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
        g2.setColor(bgColor);
        g2.fillRect(bounds.x,bounds.y,bounds.width,bounds.height);
        g2.setColor(Color.WHITE);
        g2.fillRect(titleBar.x,titleBar.y,titleBar.width,titleBar.height);
        g2.setColor(Color.BLACK);
        g2.setFont(segoe);
        g2.drawString(title,bounds.x+5,bounds.y+15);
        for (int i = 0; i < windowButtons.size(); i++) {
            windowButtons.get(i).draw(g2);
        }
        if(!owner.windows.get(owner.windows.size()-1).equals(this)){
            g2.setColor(new Color(0,0,0,100));
            g2.fillRect(titleBar.x,titleBar.y,bounds.width,bounds.height);
        }
    }

    public void update(){
        focused=owner.windows.indexOf(this)==owner.windows.size()-1;
        titleBar = new Rectangle(bounds.x,bounds.y,bounds.width,20);
        edges = new Rectangle[]{new Rectangle(bounds.x-5,bounds.y-5,10,bounds.height+10),new Rectangle(bounds.x+bounds.width-5,bounds.y-5,10,bounds.height+10),new Rectangle(bounds.x-5,bounds.y-5,bounds.width+10,10),new Rectangle(bounds.x-5,bounds.y+bounds.height-5,bounds.width+10,10)};
        closeButton.setLocation(bounds.x+bounds.width-20,bounds.y);
        edgeHovers = new boolean[4];
        for (int i = 0; i < 4; i++) {
            if (edges[i].contains(App.mApp.input.mouseLocation.getX()-owner.getX()-owner.getInsets().left,App.mApp.input.mouseLocation.getY()-owner.getY()-owner.getInsets().top)){
                edgeHovers[i] = true;
            }
        }
        movable = titleBar.contains(App.mApp.input.mouseLocation.getX()-owner.getX()-owner.getInsets().left,App.mApp.input.mouseLocation.getY()-owner.getY()-owner.getInsets().top);
    }

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
