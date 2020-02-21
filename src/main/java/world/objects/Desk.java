package world.objects;

import bert.App;
import world.interfaces.PCInterface;
import world.rooms.Room;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

public class Desk extends InteractiveObject {

    public PCInterface os = null;

    public Desk(int x, int y, Room room){
        super(room);
        this.x = x;
        this.y = y;
        init();
    }

    void init(){
        try{
            img = ImageIO.read(App.getResourceAsFile("computer.png"));
        }catch (IOException e){
            e.printStackTrace();
        }
        bounds = new Rectangle(x+16,y+80,img.getWidth()-32, img.getHeight()-96);
    }

    @Override
    public void paintGraphics(Graphics2D g2, double progress) {
        super.paintGraphics(g2, progress);
        if(os!=null)g2.drawImage(os.screenImg,x+50,y+32,48,24,null);
    }

    @Override
    public void interact() {
        if(os==null){
            os = new PCInterface(this);
            App.mApp.interfaces.add(os);
        }else{
            os.destroy();
            os=null;
        }
    }
}
