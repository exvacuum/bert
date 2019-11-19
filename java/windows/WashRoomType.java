package windows;

import bases.WindowStaticType;

import java.awt.*;

public class KitchenType implements WindowStaticType {
    @Override
    public void paint(Graphics2D g2, float interpolation){
        g2.setColor(Color.BLACK);
        for(int i = 0; i < 480/32; i++){
            g2.drawLine(i*32, 0,i*32, 480);
        }
        for(int i = 0; i < 480/32; i++){
            g2.drawLine(0, i*32,480, i*32);
        }
    }

    @Override
    public String getName(){
        return "Kitchen";
    }

    @Override
    public int getWidth() {
        return 480;
    }

    @Override
    public int getHeight() {
        return 480;
    }

    @Override
    public int getRelativeX() {
        return 619;
    }

    @Override
    public int getRelativeY() {
        return 0;
    }
}
