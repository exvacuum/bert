package dev.exvaccum.bert.world.interfaces.pc.files;

import dev.exvaccum.bert.world.interfaces.pc.FileBrowser;
import dev.exvaccum.bert.world.interfaces.pc.MetaWindow;

import java.util.Arrays;

public class NavUp extends MetaFile{

    /**
     * Navigation button that brings the user up a level
     * @param owner
     */
    public NavUp(MetaWindow owner) {
        super(owner, "../", FileType.DIRECTORY);
    }

    @Override
    public void performAction(){

        //Remove current directory's contents from visibility
        for (int i = 0; i < ((FileBrowser)ownerWindow).target.contents.length; i++) {
            ownerWindow.windowButtons.removeAll(Arrays.asList(((FileBrowser)ownerWindow).target.contents[i]));
        }

        /*
            If can navigate up:
                - set target directory
                - refresh icons
                - remove a level from the full path
         */
        if(getParent().getParent()!=null) {
            ((FileBrowser) ownerWindow).target = getParent().getParent();
            ((FileBrowser) ownerWindow).target.refresh();
            ((FileBrowser) ownerWindow).upLevels.remove(((FileBrowser) ownerWindow).upLevels.size()-1);
        }
    }
}
