package world.objects;

import bert.App;
import control.Input;
import world.rooms.Room;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

public abstract class InteractiveObject extends GameObject {

    static BufferedImage buttonBgimg;

    static {
        try {
            buttonBgimg = ImageIO.read(App.getResourceAsFile("buttonBg.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public InteractiveObject(Room room) {
        super(room);
    }

    public boolean isClose(){
        return (getDistanceToPlayer()<128);
    }

    public double getDistanceToPlayer(){
        return new Point((int)(room.getX()+bounds.getCenterX()), (int)(room.getY()+bounds.getCenterY()))
                .distance(App.mApp.world.player.getX()+App.mApp.world.player.getWidth()/2,App.mApp.world.player.getY()+App.mApp.world.player.getHeight()/2);
    }

    public abstract void interact();

    @Override
    public boolean isInteractive() {
        return true;
    }

    @Override
    public void paintGraphics(Graphics2D g2, double progress) {
        super.paintGraphics(g2, progress);
        if(isClose()){
            g2.drawImage(buttonBgimg,(int)getBounds().getCenterX()-32,y-84,64, 64,null);
            g2.setFont(App.mApp.h2Font);
            g2.drawString(KeyEvent.getKeyText(Input.KEY_INTERACT), (int)getBounds().getCenterX()-8, y-32);
        }
    }
}
