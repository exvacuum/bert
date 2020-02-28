package dev.exvaccum.bert.world.interfaces.pc;

import dev.exvaccum.bert.Bert;
import dev.exvaccum.bert.world.interfaces.PCInterface;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class ImageViewer extends MetaWindow {

    public BufferedImage image;

    public ImageViewer(int x, int y, int width, int height, PCInterface owner, String fname) {
        super(x, y, width, height, owner);
        title = "Image Viewer";
        bgColor = new Color(0,0,0,0);
        type = WindowType.IMAGE_VIEWER;
        resizable = true;
        try {
            this.image = ImageIO.read(Bert.getResourceAsFile(fname));
        }catch (IOException e){
            e.printStackTrace();
        }
        bounds.height += titleBar.height;
    }

    @Override
    public void draw(Graphics2D g2) {
        super.draw(g2);
        g2.drawImage(image, bounds.x, bounds.y, bounds.width, bounds.height,null);
    }
}
