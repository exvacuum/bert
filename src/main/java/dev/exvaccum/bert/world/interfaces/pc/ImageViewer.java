package dev.exvaccum.bert.world.interfaces.pc;

import dev.exvaccum.bert.Bert;
import dev.exvaccum.bert.world.interfaces.PCInterface;
import dev.exvaccum.bert.world.interfaces.pc.files.MetaFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class ImageViewer extends MetaWindow {

    //Image to view
    public BufferedImage image;

    /**
     * Image viewer, shows a desired image from file browser
     * @param x x-coordinate of window
     * @param y y-coordinate of window
     * @param width width of window
     * @param height  height of window
     * @param owner PC interface to which this window belongs
     * @param fname filename for image loading
     */
    public ImageViewer(int x, int y, int width, int height, PCInterface owner, String fname) {
        super(x, y, width, height, owner);
        title = "Image Viewer - "+fname;
        bgColor = new Color(0,0,0,50);
        type = WindowType.IMAGE_VIEWER;
        resizable = true;

        //Load image
        try {
            this.image = ImageIO.read(Bert.getResourceAsFile(MetaFile.pathMap.get(fname)));
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
