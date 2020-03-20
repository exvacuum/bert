package dev.exvaccum.bert.world.objects;

import dev.exvaccum.bert.Bert;
import dev.exvaccum.bert.control.environment.light.Light;
import dev.exvaccum.bert.control.environment.light.LightSource;
import dev.exvaccum.bert.world.rooms.Room;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class GameObject {

    Rectangle bounds;
    public Room room;
    int x,y;
    BufferedImage img;
    public Light light;

    /**
     * Create new object in a specified room
     * @param room target room
     */
    public GameObject(Room room){
        this.room = room;
        if(this instanceof LightSource) light = new Light(this);
    }

    /**
     * Draw object to room grpahics
     * @param g2 Graphics2D object for drawing
     * @param progress Progress of spritesheet animation
     */
    public void paintGraphics(Graphics2D g2, double progress){
        g2.drawImage(img, x, y,null);
        if(Bert.debug) {
            g2.setColor(Color.RED);
            g2.drawRect(x, y, img.getWidth(), img.getHeight());
            g2.setColor(Color.blue);
            g2.drawRect(bounds.x, bounds.y, bounds.width, bounds.height);
        }
    };

    public void update(){};

    /**
     * @return object bounds
     */
    public Rectangle getBounds(){
        return bounds;
    }

    /**
     * @return whether object can be interacted with, overridden in InteractiveObject
     */
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
