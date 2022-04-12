package dev.exvaccum.bert.world.interfaces.pc.files;

import dev.exvaccum.bert.Bert;
import dev.exvaccum.bert.control.WButton;
import dev.exvaccum.bert.world.interfaces.PCInterface;
import dev.exvaccum.bert.world.interfaces.pc.FileBrowser;
import dev.exvaccum.bert.world.interfaces.pc.ImageViewer;
import dev.exvaccum.bert.world.interfaces.pc.MetaWindow;
import dev.exvaccum.bert.world.interfaces.pc.SoundPlayer;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;

public class MetaFile extends WButton {

    //File type
    FileType type = FileType.DOCUMENT;
    public enum FileType{
        DIRECTORY,
        IMAGE,
        DOCUMENT,
        AUDIO
    }

    //Load available icons
    static BufferedImage image, audio, doc,folder;
    static {
        try {
            image = ImageIO.read(Bert.getResourceAsFile("image.png"));
            audio = ImageIO.read(Bert.getResourceAsFile("music.png"));
            doc = ImageIO.read(Bert.getResourceAsFile("doc.png"));
            folder = ImageIO.read(Bert.getResourceAsFile("folder.png"));
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    //Filename and icon
    public String fname;
    BufferedImage icon;

    //Filename to actual path map
    public static HashMap<String, String> pathMap = new HashMap<>();
    static {
        pathMap.put("me.png","pc/playerStrip.png");
        pathMap.put("church.wav","pc/wonderwall.wav");
    }

    /**
     * Files to interact with in the FileBrowser
     * @param owner Filebrowser window which owns this file
     * @param fname File name
     * @param fileType File type
     */
    public MetaFile(MetaWindow owner, String fname, FileType fileType) {
        super(0, 0, 0, 0, owner);
        setFileType(fileType);
        this.fname = fname;
        setBgC(new Color(0,0,0,0));
        setImg(icon);
        setBordered(false);
    }

    /**
     * Sets file type and icon image
     * @param type Desired type
     */
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

    @Override
    public void draw(Graphics2D g2){
        super.draw(g2);

        //Draw filename if file is visible
        boolean shouldDrawText = false;
        for (int i = 0; i < ((FileBrowser)ownerWindow).target.contents.length; i++) {
            for (int j = 0; j < ((FileBrowser)ownerWindow).target.contents[0].length; j++) {
                if(((FileBrowser)ownerWindow).target.contents[i][j].equals(this)) shouldDrawText = true;
            }
        }
        if(shouldDrawText) {
            g2.setColor(Color.WHITE);
            g2.setFont(ownerWindow.segoe);
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
            g2.drawString(fname.length()<=8?fname:fname.substring(0,5)+"...", x, y + 4 + height);
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        }
    }

    @Override
    public void performAction(){

        //Open appropriate program
        switch (type){
            case IMAGE:
                try {
                    BufferedImage image = ImageIO.read(Bert.getResourceAsFile(pathMap.get(fname)));
                    ownerWindow.owner.windows.add(new ImageViewer(64,64,image.getWidth(),image.getHeight(),(PCInterface)owner,fname));
                }catch (IOException e){
                    e.printStackTrace();
                }
                break;
            case AUDIO:
                ownerWindow.owner.windows.add(new SoundPlayer(64,64,180,100,(PCInterface)owner,fname));
                break;
        }
    }

    /**
     * @return current directory being viewed
     */
    public MetaDirectory getParent() {
        return ((FileBrowser)ownerWindow).upLevels.get(((FileBrowser)ownerWindow).upLevels.size()-1);
    }
}
