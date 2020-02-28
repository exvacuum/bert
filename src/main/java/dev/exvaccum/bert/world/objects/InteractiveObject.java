package dev.exvaccum.bert.world.objects;

import dev.exvaccum.bert.Bert;
import dev.exvaccum.bert.control.Input;
import dev.exvaccum.bert.world.rooms.Room;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

public abstract class InteractiveObject extends GameObject {

    //Prompt background
    static BufferedImage buttonBgimg;
    static {
        try {
            buttonBgimg = ImageIO.read(Bert.getResourceAsFile("buttonBg.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Create new interactive object
     * @param room target room
     */
    public InteractiveObject(Room room) {
        super(room);
    }

    /**
     * @return if player is sufficiently close to player
     */
    public boolean isClose(){
        return (getDistanceToPlayer()<128);
    }

    /**
     * @return distance to player
     */
    public double getDistanceToPlayer(){
        return new Point((int)(room.getX()+bounds.getCenterX()), (int)(room.getY()+bounds.getCenterY()))
                .distance(Bert.mBert.world.player.getX()+ Bert.mBert.world.player.getWidth()/2, Bert.mBert.world.player.getY()+ Bert.mBert.world.player.getHeight()/2);
    }

    /**
     * Action to be performed when player interacts with the object
     */
    public abstract void interact();

    @Override
    public boolean isInteractive() {
        return true;
    }

    @Override
    public void paintGraphics(Graphics2D g2, double progress) {
        super.paintGraphics(g2, progress);

        //Display prompt if player is close
        if(isClose()){
            g2.drawImage(buttonBgimg,(int)getBounds().getCenterX()-32,y-84,64, 64,null);
            g2.setFont(Bert.mBert.h2Font);
            g2.drawString(KeyEvent.getKeyText(Input.KEY_INTERACT), (int)getBounds().getCenterX()-8, y-32);
        }
    }
}
