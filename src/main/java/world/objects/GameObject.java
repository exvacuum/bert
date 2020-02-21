package world.objects;

import bert.App;
import world.rooms.Room;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class GameObject {

    Rectangle bounds;
    public Room room;
    int x,y;
    BufferedImage img;

    public GameObject(Room room){
        this.room = room;
    }

    public void paintGraphics(Graphics2D g2, double progress){
        g2.drawImage(img, x, y,null);
        if(App.debug) {
            g2.setColor(Color.RED);
            g2.drawRect(x, y, img.getWidth(), img.getHeight());
            g2.setColor(Color.blue);
            g2.drawRect(bounds.x, bounds.y, bounds.width, bounds.height);
        }
    };

    public Rectangle getBounds(){
        return bounds;
    }
    
    public boolean isInteractive(){
        return false;
    }

    public boolean isClose(){
        return false;
    };

    public double getDistanceToPlayer(){
        return 0;
    };
}
