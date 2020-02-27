package world.interfaces.pc.files;

import world.interfaces.pc.FileBrowser;
import world.interfaces.pc.MetaWindow;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class MetaDirectory extends MetaFile{
    public MetaFile[][] contents = new MetaFile[6][4];
    public boolean initializing = true;
    /**
     * Creates a button with specified dimensions
     *
     * @param owner
     */
    public MetaDirectory(MetaWindow owner, String fname, MetaFile[][] contents) {
        super(owner, fname, FileType.DIRECTORY);
        this.contents = contents;
        refresh();
    }

    public void drawContents(Graphics2D g2){
        for (int i = 0; i < contents.length; i++) {
            for (int j = 0; j < contents[0].length; j++) {
                contents[i][j].setBounds(ownerWindow.bounds.x+10+j*50,ownerWindow.bounds.y+ownerWindow.titleBar.height+10+i*50,32,32);
                contents[i][j].draw(g2);
            }
        }
    }

    @Override
    public void performAction(){
        for (int i = 0; i < ((FileBrowser)ownerWindow).target.contents.length; i++) {
            ownerWindow.windowButtons.removeAll(Arrays.asList(((FileBrowser)ownerWindow).target.contents[i]));
        }
        ((FileBrowser)ownerWindow).upLevels.add(((FileBrowser)ownerWindow).target);
        ((FileBrowser)ownerWindow).target = this;
        if(!initializing)((FileBrowser)ownerWindow).target.refresh();
        initializing = false;
    }

    public void refresh(){
        for (int i = 0; i < contents.length; i++) {
            ownerWindow.windowButtons.addAll(Arrays.asList(contents[i]));
        }
    }
}
