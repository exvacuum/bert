package world.interfaces.pc;

import world.interfaces.PCInterface;
import world.interfaces.pc.files.MetaDirectory;
import world.interfaces.pc.files.MetaFile;

import java.awt.*;
import java.util.ArrayList;

public class FileBrowser extends MetaWindow {

    public MetaDirectory target;

    public FileBrowser(int x, int y, int width, int height, PCInterface owner) {
        super(x, y, width, height, owner);
        title = "File Browser";
        bgColor = new Color(100,100,100);
        type = WindowType.FILES;
        resizable = false;

        target = new MetaDirectory(this, "C:", generateRoot());
    }

    ArrayList<MetaFile> generateRoot(){
        return new ArrayList<MetaFile>() {
            {
                add(new MetaDirectory(FileBrowser.this,"Pictures", new ArrayList<MetaFile>() {
                    {
                        add(new MetaFile(FileBrowser.this,"egg.jpg"){
                            {
                                setFileType(FileType.IMAGE);
                            }
                        });
                    }
                }));
                add(new MetaDirectory(FileBrowser.this,"Audio", new ArrayList<MetaFile>() {
                    {
                        add(new MetaFile(FileBrowser.this,"egg.wav"){
                            {
                                setFileType(FileType.AUDIO);
                            }
                        });
                    }
                }));
            }
        };
    }

    @Override
    public void update(){
        super.update();
        if(focused){
        }
    }

    @Override
    public void draw(Graphics2D g2) {
        super.draw(g2);
        target.drawContents(g2);
    }
}
