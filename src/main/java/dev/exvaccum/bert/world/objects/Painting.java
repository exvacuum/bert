package dev.exvaccum.bert.world.objects;

import dev.exvaccum.bert.Bert;
import dev.exvaccum.bert.world.rooms.Room;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Painting extends GameObject{

    //Sprite
    BufferedImage img;

    //Positional Vars
    int x, y;

    public Painting(int x, int y, Room room){
        super(room);
        this.x = x;
        this.y = y;
        init();
    }

    void init(){
        try{
            img = ImageIO.read(Bert.getResourceAsFile("painting.png"));
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void paintGraphics(Graphics2D g2, double progress) {
        g2.drawImage(img, x, y,img.getWidth()/2,img.getHeight()/2,null);
    }
}
