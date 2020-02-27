package world.interfaces.pc;

import org.checkerframework.checker.units.qual.A;
import world.interfaces.PCInterface;
import world.interfaces.pc.files.MetaDirectory;
import world.interfaces.pc.files.MetaFile;
import world.interfaces.pc.files.NavUp;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class FileBrowser extends MetaWindow {

    public MetaDirectory target;
    public ArrayList<MetaDirectory> upLevels;

    public FileBrowser(int x, int y, int width, int height, PCInterface owner) {
        super(x, y, width, height, owner);
        title = "File Browser";
        bgColor = new Color(0,0,0,100);
        type = WindowType.FILES;
        resizable = false;
        upLevels = new ArrayList<>();
        target = new MetaDirectory(this,"C:", generateRoot());
    }

    MetaFile[][] generateRoot(){
        MetaFile[][] toReturn = new MetaFile[][]{
            {
                new MetaDirectory(FileBrowser.this,"Images", new MetaFile[][]{{
                    new NavUp(FileBrowser.this),
                    new MetaFile(FileBrowser.this,"img.bmp", MetaFile.FileType.IMAGE)}}),
                new MetaDirectory(FileBrowser.this,"Audio", new MetaFile[][]{{
                    new NavUp(FileBrowser.this),
                    new MetaFile(FileBrowser.this,"snd.wav", MetaFile.FileType.AUDIO)}}),
            new MetaDirectory(FileBrowser.this,"Documents", new MetaFile[][]{{
                    new NavUp(FileBrowser.this),
                    new MetaDirectory(FileBrowser.this,"School", new MetaFile[][]{{
                            new NavUp(FileBrowser.this),
                            new MetaFile(FileBrowser.this,"philosophy.txt", MetaFile.FileType.DOCUMENT)}}),
                    new MetaDirectory(FileBrowser.this,"Notes", new MetaFile[][]{{
                            new NavUp(FileBrowser.this),
                            new MetaFile(FileBrowser.this,"Crying.txt", MetaFile.FileType.DOCUMENT)}}),
                    new MetaDirectory(FileBrowser.this,"Code", new MetaFile[][]{{
                            new NavUp(FileBrowser.this),
                            new MetaFile(FileBrowser.this,"VOID VOID VOID", MetaFile.FileType.DOCUMENT)}}),
                    new MetaFile(FileBrowser.this,"egg.txt", MetaFile.FileType.DOCUMENT)}})
            }
        };
        return toReturn;
    }

    @Override
    public void update(){
        super.update();
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
