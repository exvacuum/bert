package world.objects;

import bert.App;
import world.rooms.Room;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

public class Bed extends GameObject{

    public Bed(int x, int y, Room room){
        super(room);
        this.x = x;
        this.y = y;
        init();
    }

    void init(){
        try{
            img = ImageIO.read(App.getResourceAsFile("bed.png"));
        }catch (IOException e){
            e.printStackTrace();
        }
        bounds = new Rectangle(x+32,y+80,img.getWidth()-48,img.getHeight()-96);
    }
}
