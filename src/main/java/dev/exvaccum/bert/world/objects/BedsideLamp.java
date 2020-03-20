package dev.exvaccum.bert.world.objects;

import dev.exvaccum.bert.control.Utilities;
import dev.exvaccum.bert.control.environment.light.Light;
import dev.exvaccum.bert.control.environment.light.LightSource;
import dev.exvaccum.bert.world.rooms.Room;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class BedsideLamp extends InteractiveObject implements LightSource {

    /**
     * Create new interactive object
     *
     * @param room target room
     */
    public BedsideLamp(int x, int y, Room room) {
        super(room);
        this.x = x;
        this.y = y;
        try{
            img = ImageIO.read(Utilities.getResourceAsFile("lamp.png"));

        }catch (IOException e){
            e.printStackTrace();
        }

        bounds = new Rectangle(x,y,img.getWidth(), img.getHeight());
        light.isOn = false;
        light.lightColor = new Color(255, 241, 224);
        light.lightRadius = 250;
        light.lightLocation = new Point((int)bounds.getCenterX(), bounds.y+32);
        light.luminosity = 0.5f;
    }

    @Override
    public void interact() {
        light.isOn ^= true;
    }
}
