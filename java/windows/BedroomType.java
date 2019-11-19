package windows;

import bases.WindowStaticType;

import java.awt.*;

public class BedroomType implements WindowStaticType {
    @Override
    public void paint(Graphics2D g2, float interpolation){
        g2.setColor(Color.ORANGE.darker());
        g2.fillRect(0,0,640,480);
    }

    @Override
    public String getName(){
        return "Bedroom";
    }

    @Override
    public int getWidth() {
        return 640;
    }

    @Override
    public int getHeight() {
        return 480;
    }

    @Override
    public int getRelativeX() {
        return -7;
    }

    @Override
    public int getRelativeY() {
        return 0;
    }
}
