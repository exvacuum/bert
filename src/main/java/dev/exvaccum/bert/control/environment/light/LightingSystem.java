package dev.exvaccum.bert.control.environment.light;

import dev.exvaccum.bert.world.rooms.Room;

import java.awt.*;
import java.awt.image.VolatileImage;
import java.util.ArrayList;

public class LightingSystem{

    public Room room;
    public ArrayList<Light> lights;
    public VolatileImage lightMap;
    public float ambientLevel = 0.05f;
    public LightingSystem(Room room){
        this.room = room;
        lights = new ArrayList<>();
        lightMap = room.getGraphicsConfiguration().createCompatibleVolatileImage(room.getWidth(), room.getHeight(),Transparency.TRANSLUCENT);
    }

    public void update(){
        for (int i = 0; i < room.getObjects().size(); i++) {
            if (room.getObjects().get(i) instanceof LightSource && !lights.contains(room.getObjects().get(i).light)) lights.add(room.getObjects().get(i).light);
        }
    }

    // rendering to the image
    void renderOffscreen() {
        do {
            if (lightMap.validate(room.getGraphicsConfiguration()) ==
                    VolatileImage.IMAGE_INCOMPATIBLE)
            {
                // old vImg doesn't work with new GraphicsConfig; re-create it
                lightMap = room.getGraphicsConfiguration().createCompatibleVolatileImage(room.getWidth(), room.getHeight(),Transparency.TRANSLUCENT);
            }
            Graphics2D img2 = lightMap.createGraphics();
            img2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            img2.setBackground(new Color(0,0,0,0));
            img2.setColor(new Color(0,0,0));
            img2.fillRect(0,0,lightMap.getWidth(),lightMap.getHeight());

            lights.forEach(light -> {
                img2.setComposite(AlphaComposite.DstOver);
                if(light.isOn) {
                    img2.setColor(new Color(light.lightColor.getRed(),light.lightColor.getGreen(),light.lightColor.getBlue(),(int)(light.luminosity*255)));
                    img2.fillOval(light.lightLocation.x - light.lightRadius, light.lightLocation.y - light.lightRadius, light.lightRadius * 2, light.lightRadius * 2);
                }
                img2.setComposite(AlphaComposite.DstOut);
                if(light.isOn) {
                    RadialGradientPaint lightPaint = new RadialGradientPaint(light.lightLocation, light.lightRadius, new float[]{0.0f, 1.0f}, new Color[]{new Color(light.lightColor.getRed(),light.lightColor.getGreen(),light.lightColor.getBlue(),(int)(light.luminosity*255)),new Color(light.lightColor.getRed(), light.lightColor.getGreen(), light.lightColor.getBlue(),0)});
                    img2.setPaint(lightPaint);
                    img2.fillOval(light.lightLocation.x - light.lightRadius, light.lightLocation.y - light.lightRadius, light.lightRadius * 2, light.lightRadius * 2);
                }
            });
            img2.dispose();
        } while (lightMap.contentsLost());
    }

    public void draw(Graphics2D g2){
        lightMap = room.getGraphicsConfiguration().createCompatibleVolatileImage(room.getWidth(), room.getHeight(),Transparency.TRANSLUCENT);
        do {
            int returnCode = lightMap.validate(room.getGraphicsConfiguration());
            if (returnCode == VolatileImage.IMAGE_RESTORED) {
                // Contents need to be restored
                renderOffscreen();      // restore contents
            } else if (returnCode == VolatileImage.IMAGE_INCOMPATIBLE) {
                // old vImg doesn't work with new GraphicsConfig; re-create it
                lightMap = room.getGraphicsConfiguration().createCompatibleVolatileImage(room.getWidth(), room.getHeight(),Transparency.TRANSLUCENT);
                renderOffscreen();
            }
            g2.setComposite(AlphaComposite.getInstance(Transparency.TRANSLUCENT,(1-ambientLevel)));
            g2.drawImage(lightMap,0,0,null);
        } while (lightMap.contentsLost());
    }
}
