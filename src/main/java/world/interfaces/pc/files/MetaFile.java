package world.interfaces.pc.files;

import bert.App;
import control.MButton;
import control.WButton;
import world.interfaces.GameInterface;
import world.interfaces.pc.MetaWindow;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class MetaFile extends WButton {

    FileType type = FileType.DOCUMENT;
    public enum FileType{
        DIRECTORY,
        IMAGE,
        DOCUMENT,
        AUDIO
    }

    BufferedImage icon;
    static BufferedImage image, audio, doc,folder;
    static {
        try {
            image = ImageIO.read(App.getResourceAsFile("image.png"));
            audio = ImageIO.read(App.getResourceAsFile("music.png"));
            doc = ImageIO.read(App.getResourceAsFile("doc.png"));
            folder = ImageIO.read(App.getResourceAsFile("folder.png"));
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * Creates a button with specified dimensions
     *
     * @param owner
     */
    public MetaFile(MetaWindow owner, String fname) {
        super(0, 0, 0, 0, owner);
        setFileType(FileType.DOCUMENT);
    }

    public void setFileType(FileType type){
        this.type = type;
        switch (type){
            case DIRECTORY:
                icon = folder;
                break;
            case DOCUMENT:
                icon = doc;
                break;
            case IMAGE:
                icon = image;
                break;
            case AUDIO:
                icon = audio;
                break;
        }
    }

}
