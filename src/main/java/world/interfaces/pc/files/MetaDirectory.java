package world.interfaces.pc.files;

import world.interfaces.pc.FileBrowser;
import world.interfaces.pc.MetaWindow;

import java.awt.*;
import java.util.ArrayList;

public class MetaDirectory extends MetaFile{
    ArrayList<MetaFile> contents;

    /**
     * Creates a button with specified dimensions
     *
     * @param owner
     */
    public MetaDirectory(MetaWindow owner, String fname, ArrayList<MetaFile> contents) {
        super(owner, fname);
        this.contents = contents;
        setFileType(FileType.DIRECTORY);
    }

    public void drawContents(Graphics2D g2){
        for (int i = 0; i < contents.size()/5; i++) {
            for (int j = 0; j < 5; j++) {
                contents.get(j+i*5).setBounds(ownerWindow.bounds.x+10+i*50,ownerWindow.bounds.y+ownerWindow.titleBar.height+10+i*50,32,32);
                contents.get(j+i*5).draw(g2);
            }
        }
    }

    @Override
    public void performAction(){
        ((FileBrowser)ownerWindow).target = this;
        ownerWindow.windowButtons.clear();
    }
}
