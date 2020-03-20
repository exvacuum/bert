package dev.exvaccum.bert.world.objects;

import dev.exvaccum.bert.control.Utilities;
import dev.exvaccum.bert.world.rooms.Room;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Switch extends InteractiveObject {

    static BufferedImage onImg, offImg;
    boolean active;

    /**
     * Create new interactive object
     *
     * @param room target room
     */
    public Switch(int x, int y, Room room) {
        super(room);
        this.x = x;
        this.y = y;
        try{
            onImg = ImageIO.read(Utilities.getResourceAsFile("switch0.png"));
            offImg = ImageIO.read(Utilities.getResourceAsFile("switch1.png"));
            img = offImg;
        }catch (IOException e){
            e.printStackTrace();
        }

        bounds = new Rectangle(x,y,img.getWidth(), img.getHeight());
    }

    @Override
    public void interact() {
        active ^= true;
        if(active) {
            img = onImg;
            onAction();
        }else {
            img = offImg;
            offAction();
        }
    }

    public void onAction(){
    }

    public  void offAction(){
    }
}
