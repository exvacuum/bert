package dev.exvaccum.bert.world.interfaces.pc.files;

import dev.exvaccum.bert.world.interfaces.pc.FileBrowser;
import dev.exvaccum.bert.world.interfaces.pc.MetaWindow;

import java.awt.*;
import java.util.Arrays;

public class MetaDirectory extends MetaFile{

    //Array for contents
    public MetaFile[][] contents;

    //One-time boolean for use during initialization
    public boolean initializing = true;

    /**
     * A file which itself contains files, these are viewable using the file browser
     * @param owner Owner window
     * @param fname File name
     * @param contents Array of contents, divided into rows and columns
     */
    public MetaDirectory(MetaWindow owner, String fname, MetaFile[][] contents) {
        super(owner, fname, FileType.DIRECTORY);
        this.contents = contents;
        refresh();
    }

    /**
     * Draws all files within the directory
     * @param g2 Graphics2D object for drawing
     */
    public void drawContents(Graphics2D g2){
        for (int i = 0; i < contents.length; i++) {
            for (int j = 0; j < contents[0].length; j++) {
                contents[i][j].setBounds(ownerWindow.bounds.x+10+j*64,ownerWindow.bounds.y+ownerWindow.titleBar.height+10+i*64,48,48);
                contents[i][j].draw(g2);
            }
        }
    }

    @Override
    public void performAction(){

        /*
            When the icon for a directory is clicked:
                - remove old icons
                - navigate into it
                - refresh icons if necessary
         */
        for (int i = 0; i < ((FileBrowser)ownerWindow).target.contents.length; i++) {
            ownerWindow.windowButtons.removeAll(Arrays.asList(((FileBrowser)ownerWindow).target.contents[i]));
        }
        ((FileBrowser)ownerWindow).upLevels.add(((FileBrowser)ownerWindow).target);
        ((FileBrowser)ownerWindow).target = this;
        if(!initializing)((FileBrowser)ownerWindow).target.refresh();
        initializing = false;
    }

    /**
     * Rebuilds currently-visible icons
     */
    public void refresh(){
        for (int i = 0; i < contents.length; i++) {
            ownerWindow.windowButtons.addAll(Arrays.asList(contents[i]));
        }
    }
}
