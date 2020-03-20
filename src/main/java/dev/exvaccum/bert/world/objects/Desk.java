package dev.exvaccum.bert.world.objects;

import dev.exvaccum.bert.Bert;
import dev.exvaccum.bert.control.Utilities;
import dev.exvaccum.bert.control.environment.light.LightSource;
import dev.exvaccum.bert.world.interfaces.PCInterface;
import dev.exvaccum.bert.world.rooms.Room;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

public class Desk extends InteractiveObject implements LightSource {

    public PCInterface os = null;

    public Desk(int x, int y, Room room){
        super(room);
        this.x = x;
        this.y = y;
        init();
    }

    void init(){
        try{
            img = ImageIO.read(Utilities.getResourceAsFile("computer.png"));
        }catch (IOException e){
            e.printStackTrace();
        }
        bounds = new Rectangle(x+16,y+80,img.getWidth()-32, img.getHeight()-96);
        light.lightRadius = 100;
        light.lightLocation = new Point((int)bounds.getCenterX(),bounds.y-40);
    }

    @Override
    public void paintGraphics(Graphics2D g2, double progress) {
        super.paintGraphics(g2, progress);
        if(os!=null) {
            g2.drawImage(os.screenImg, x + 50, y + 32, 48, 24, null);
        }else{
            light.isOn = false;
        }
    }

    @Override
    public void interact() {
        if(os==null){
            os = new PCInterface(this);
            Bert.mBert.interfaces.add(os);
            light.isOn = true;
        }else{
            os.destroy();
            os=null;
        }
    }

    @Override
    public void update(){
        light.lightLocation = new Point((int)bounds.getCenterX(),bounds.y-40);
        if(os!=null){
            light.lightColor = Utilities.averageColor(os.screenImg,0,0,os.screenImg.getWidth(),os.screenImg.getHeight());
        }
    }
}
