package world.interfaces.pc.files;

import world.interfaces.pc.FileBrowser;
import world.interfaces.pc.MetaWindow;

import java.awt.*;
import java.util.Arrays;

public class NavUp extends MetaFile{

    /**
     * Creates a button with specified dimensions
     *
     * @param owner
     */
    public NavUp(MetaWindow owner) {
        super(owner, "../", FileType.DIRECTORY);
    }

    @Override
    public void performAction(){
        for (int i = 0; i < ((FileBrowser)ownerWindow).target.contents.length; i++) {
            ownerWindow.windowButtons.removeAll(Arrays.asList(((FileBrowser)ownerWindow).target.contents[i]));
        }
        if(getParent().getParent()!=null) {
            ((FileBrowser) ownerWindow).target = getParent().getParent();
            ((FileBrowser) ownerWindow).target.refresh();
            ((FileBrowser) ownerWindow).upLevels.remove(((FileBrowser) ownerWindow).upLevels.size()-1);
        }
    }
}
