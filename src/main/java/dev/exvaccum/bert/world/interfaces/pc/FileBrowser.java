package dev.exvaccum.bert.world.interfaces.pc;

import dev.exvaccum.bert.world.interfaces.PCInterface;
import dev.exvaccum.bert.world.interfaces.pc.files.MetaDirectory;
import dev.exvaccum.bert.world.interfaces.pc.files.MetaFile;
import dev.exvaccum.bert.world.interfaces.pc.files.NavUp;

import java.awt.*;
import java.util.ArrayList;

public class FileBrowser extends MetaWindow {

    //Target dir
    public MetaDirectory target;

    //Full path e.g. [C:,Documents,Notes]
    public ArrayList<MetaDirectory> upLevels;

    /**
     * File Browser for exploring the PC's mock filesystem
     * @param x x-coordinate of window
     * @param y y-coordinate of window
     * @param width width of window
     * @param height  height of window
     * @param owner PC interface to which this window belongs
     */
    public FileBrowser(int x, int y, int width, int height, PCInterface owner) {
        super(x, y, width, height, owner);
        title = "File Browser";
        bgColor = new Color(0,0,0,100);
        type = WindowType.FILES;
        resizable = false;
        upLevels = new ArrayList<>();
        target = new MetaDirectory(this,"C:", generateRoot());
    }

    /**
     * Generates the files
     * @return the root directory
     */
    MetaFile[][] generateRoot(){
        MetaFile[][] toReturn = new MetaFile[][]{
            {
                new MetaDirectory(FileBrowser.this,"Images", new MetaFile[][]{{
                    new NavUp(FileBrowser.this),
                    new MetaFile(FileBrowser.this,"me.png", MetaFile.FileType.IMAGE)}
                }),
                new MetaDirectory(FileBrowser.this,"Audio", new MetaFile[][]{{
                    new NavUp(FileBrowser.this),
                    new MetaFile(FileBrowser.this,"church.wav", MetaFile.FileType.AUDIO)}
                }),
                new MetaDirectory(FileBrowser.this,"Documents", new MetaFile[][]{{
                    new NavUp(FileBrowser.this),
                    new MetaDirectory(FileBrowser.this,"School", new MetaFile[][]{{
                            new NavUp(FileBrowser.this),
                            new MetaFile(FileBrowser.this,"sample.txt", MetaFile.FileType.DOCUMENT)}
                    }),
                    new MetaDirectory(FileBrowser.this,"Notes", new MetaFile[][]{{
                            new NavUp(FileBrowser.this),
                            new MetaFile(FileBrowser.this,"Crying.txt", MetaFile.FileType.DOCUMENT)}
                    }),
                    new MetaDirectory(FileBrowser.this,"Code", new MetaFile[][]{{
                            new NavUp(FileBrowser.this),
                            new MetaFile(FileBrowser.this,"VOID VOID VOID", MetaFile.FileType.DOCUMENT)}
                    }),
                    new MetaFile(FileBrowser.this,"sample.txt", MetaFile.FileType.DOCUMENT)}
                })
            }
        };
        return toReturn;
    }

    @Override
    public void update(){
        super.update();

        //Set title to active directory
        if(focused){
            StringBuilder sb = new StringBuilder();
            sb.append("File Browser - ");
            for (int i = 0; i < upLevels.size(); i++) {
                sb.append(upLevels.get(i).fname+"/");
            }
            sb.append(target.fname);
            title = sb.toString();
        }
    }

    @Override
    public void draw(Graphics2D g2) {
        super.draw(g2);
        target.drawContents(g2);
    }
}
