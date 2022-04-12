package dev.exvaccum.bert.world.rooms;

import dev.exvaccum.bert.Bert;
import dev.exvaccum.bert.world.objects.Bed;
import dev.exvaccum.bert.world.objects.Desk;
import dev.exvaccum.bert.world.objects.GameObject;
import dev.exvaccum.bert.world.objects.Painting;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Bedroom extends Room{

    //Background Image
    BufferedImage bg;

    /**
     * Bedroom
     */
    public Bedroom(){
        super();

        //Load background
        try {
            bg = ImageIO.read(Bert.getResourceAsFile("bg.png"));
        }catch (IOException e){
            e.printStackTrace();
        }

        //Setup window
        setSize(640,480);
        setLocation(Toolkit.getDefaultToolkit().getScreenSize().width/2-getWidth()/2,Toolkit.getDefaultToolkit().getScreenSize().height/2-getHeight()/2);
        title = "Bedroom";
        setTitle(title);

        //Add objects to room
        getObjects().add(new Bed(16,250,this));
        getObjects().add(new Desk(400, 260, this));
        getObjects().add(new Painting(300, 200, this));

        //Recalibrate location
        reCalibrate();
    }

    @Override
    public void paint(Graphics2D g2){
        g2.drawImage(bg, 0,0, getWidth(), getHeight(), null);
        for(int i = 0; i < getObjects().size(); i++){
            GameObject o = getObjects().get(i);
            o.paintGraphics(g2, 0);
        }
    }
}
