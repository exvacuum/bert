package world.interfaces;

import bert.App;
import control.MButton;
import control.WButton;
import ddf.minim.AudioPlayer;
import world.interfaces.pc.CommandLine;
import world.interfaces.pc.MetaWindow;
import world.interfaces.pc.PIP;
import world.objects.GameObject;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class PCInterface extends GameInterface implements Runnable{

    GameObject owner;
    public BufferedImage biosLogo, intertiaStarLogo, taskbarlogo, cmdIcon, pipIcon;
    public BufferedImage screenImg;
    String[] sysinfo;
    int lines = 0;
    long currMem = 0;
    long memory;
    AudioPlayer bootSoundPlayer, OSSoundPlayer, shutDownPlayer;
    RadialGradientPaint desktop;
    boolean showMenu = false;
    public ArrayList<MetaWindow> windows = new ArrayList<>();
    public boolean[] edgeHover;

    Screen screen = Screen.BOOT;
    enum Screen{
        BOOT,
        LOGIN,
        DESKTOP
    }

    DesktopState desktopState = DesktopState.DEFAULT;
    enum DesktopState{
        DEFAULT,
        TRAY_OPEN
    }

    public PCInterface(GameObject owner) {
        super(owner);
        Thread t = new Thread(this);
        t.start();
    }

    void init(){
        super.init();
        bootSoundPlayer = App.mApp.minim.loadFile(App.getResourceAsFile("startup.wav").getPath());
        bootSoundPlayer.play();
        setVisible(false);
        setBackground(Color.BLACK);
        setVisible(true);
        Insets insets = getInsets();
        setSize(640+insets.left+insets.right,360+insets.top+insets.bottom);
        setResizable(false);
        screenImg = new BufferedImage(getWidth()-insets.left-insets.right,getHeight()-insets.top-insets.bottom, BufferedImage.TYPE_INT_RGB);
        try{
            biosLogo = ImageIO.read(App.getResourceAsFile("bioslogo.png"));
            intertiaStarLogo = ImageIO.read(App.getResourceAsFile("instar.png"));
            taskbarlogo = ImageIO.read(App.getResourceAsFile("taskbarlogo.png"));
            cmdIcon = ImageIO.read(App.getResourceAsFile("cmdicon.png"));
            pipIcon = ImageIO.read(App.getResourceAsFile("pipicon.png"));
        }catch (IOException e){
            e.printStackTrace();
        }

        setTitle("guerrOS");
        pane.setBackground(Color.BLACK);
        memory = 16777216l;
        sysinfo = new String[]{
                "NAX Veneron 1Ae-7900",
                "Total Physical Memory:",
                currMem + "KB"
        };

        desktop = new RadialGradientPaint(new Point(getWidth(),getHeight()),getWidth(),new float[]{0.0f,1.0f},new Color[]{new Color(136,0,204),new Color(26,0,51)});
    }

    public void paintGraphics(Graphics2D g2){
        Graphics2D ig = (Graphics2D)screenImg.getGraphics();
        switch (screen) {
            case BOOT:
                g2.setColor(Color.WHITE);
                g2.setFont(App.mApp.biosFont);
                g2.drawImage(biosLogo, 10, 10, null);
                g2.drawImage(intertiaStarLogo, getWidth() - intertiaStarLogo.getWidth() - 20, 10, null);
                g2.drawString("NAIBIOS (C)2002 Avuvyvfgvp Nofheqvgl Vap.,", 20, biosLogo.getHeight() + 30);
                g2.drawString("u628pw7 Release 10/21/2002 S", 20, biosLogo.getHeight() + 60);
                for (int i = 0; i < lines; i++) {
                    g2.drawString(sysinfo[i], 20, biosLogo.getHeight() + 90 + (30 * i));
                }
                g2.drawString("(C) Avuvyvfgvp Nofheqvgl Vap.,", 20, getHeight() - 70);
                g2.drawString("01010100-01101001-01101110-01111001-01010101-01010010-01001100-u628pw7", 20, getHeight() - 50);
                ig.setColor(Color.WHITE);
                ig.setFont(App.mApp.biosFont);
                ig.drawImage(biosLogo, 10, 10, null);
                ig.drawImage(intertiaStarLogo, getWidth() - intertiaStarLogo.getWidth() - 20, 10, null);
                ig.drawString("NAIBIOS (C)2002 Avuvyvfgvp Nofheqvgl Vap.,", 20, biosLogo.getHeight() + 30);
                ig.drawString("PLACEHOLDER Release 10/21/2002 S", 20, biosLogo.getHeight() + 60);
                for (int i = 0; i < lines; i++) {
                    ig.drawString(sysinfo[i], 20, biosLogo.getHeight() + 90 + (30 * i));
                }
                ig.drawString("(C) Avuvyvfgvp Nofheqvgl Vap.,", 20, getHeight() - 70);
                ig.drawString("01010100-01101001-01101110-01111001-01010101-01010010-01001100-PLACEHOLDER", 20, getHeight() - 50);
                break;
            case DESKTOP:
                g2.setPaint(desktop);
                g2.fillRect(0,0,getWidth(),getHeight());
                g2.setColor(new Color(0,0,0,200));
                if(showMenu){
                    g2.fillRect(0,getHeight()-getInsets().bottom-getInsets().top-200,100,150);
                    g2.fillRect(-100,getHeight()-getInsets().bottom-getInsets().top-50,200,100);
                }else{
                    g2.fillRoundRect(-100,getHeight()-getInsets().bottom-getInsets().top-50,200,100,100,100);
                }
                for (int i = 0; i < buttons.size(); i++) {
                    MButton button = buttons.get(i);
                    button.draw(g2);
                }
                for (int i = 0; i < windows.size(); i++){
                    MetaWindow window = windows.get(i);
                    window.draw(g2);
                }
                if(!usable){
                    g2.setColor(new Color(0,0,0,200));
                    g2.fillRect(0,0,getWidth(),getHeight());
                }
                ig.setPaint(desktop);
                ig.fillRect(0,0,getWidth(),getHeight());
                ig.setColor(new Color(0,0,0,200));
                ig.fillRect(0,getHeight()-getInsets().bottom-getInsets().top-50,getWidth(),50);
                if(showMenu){
                    ig.fillRect(0,getHeight()-getInsets().bottom-getInsets().top-200,100,150);
                    ig.fillRect(-100,getHeight()-getInsets().bottom-getInsets().top-50,200,100);
                }else{
                    ig.fillRoundRect(-100,getHeight()-getInsets().bottom-getInsets().top-50,200,100,100,100);
                }
                for (int i = 0; i < buttons.size(); i++) {
                    MButton button = buttons.get(i);
                    button.draw(ig);
                }
                for (int i = 0; i < windows.size(); i++){
                    MetaWindow window = windows.get(i);
                    window.draw(ig);
                }
                break;
        }
    }

    void boot(){
        try{
            for (int i = 0; i < sysinfo.length; i++) {
                    Thread.sleep(2000);
                    lines++;
                    pane.repaint();
            }
            while (currMem<memory){
                    currMem += 10;
                    sysinfo[2] = Math.min(currMem, memory) + "KB";
                    pane.repaint();
            }
            sysinfo[2] = memory + "KB OK";
            pane.repaint();
            while(true){
                Thread.sleep(1);
                if(bootSoundPlayer.length()-bootSoundPlayer.position()<=0){
                    break;
                }
            }
            bootSoundPlayer.close();
            setScreen(Screen.DESKTOP);
            OSSoundPlayer = App.mApp.minim.loadFile(App.getResourceAsFile("osstart.wav").getPath());
            OSSoundPlayer.play();
        }catch(InterruptedException e){
            e.printStackTrace();
        }
    }

    void step(){
        edgeHover = new boolean[4];
        for (int i = 0; i < windows.size(); i++) {
            windows.get(i).update();
        }
        if(windows.size()>0) {
            for (int j = 0; j < 4; j++) {
                if (windows.get(windows.size() - 1).edgeHovers[j]) edgeHover[j] = true;
            }
        }
        if((edgeHover[0]&&edgeHover[2])||(edgeHover[1]&&edgeHover[3])){
            setCursor(Cursor.getPredefinedCursor(Cursor.NW_RESIZE_CURSOR));
        }else if((edgeHover[0]&&edgeHover[3])||(edgeHover[1]&&edgeHover[2])){
            setCursor(Cursor.getPredefinedCursor(Cursor.NE_RESIZE_CURSOR));
        }else if(edgeHover[0]||edgeHover[1]){
            setCursor(Cursor.getPredefinedCursor(Cursor.E_RESIZE_CURSOR));
        }else if(edgeHover[2]||edgeHover[3]){
            setCursor(Cursor.getPredefinedCursor(Cursor.N_RESIZE_CURSOR));
        }else {
            boolean textHover = false;
            for (int i = 0; i < windows.size(); i++) {
                Rectangle bounds = windows.get(i).bounds;
                Rectangle bar = windows.get(i).titleBar;
                if (windows.get(i).type== MetaWindow.WindowType.CMD&&windows.get(i).focused&&new Rectangle(bounds.x,bounds.y+bar.height*2,bounds.width,bounds.height-bar.height).contains(App.mApp.input.mouseLocation)) {
                    textHover = true;
                    break;
                }
            }
            if (textHover) {
                setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
            } else {
                setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            }
        }

        switch (screen){

        }
    }

    void setScreen(Screen screen){
        this.screen = screen;
        refreshGUI();
    }

    void setDesktopState(DesktopState desktopState){
        this.desktopState = desktopState;
        refreshGUI();
    }

    void refreshGUI(){
        buttons.clear();
        switch (screen){
            case BOOT:
                break;
            case DESKTOP:
                buttons.add(new MButton(10,getHeight()-getInsets().bottom-getInsets().top-40,32,32, this){
                    {
                        setImg(taskbarlogo);
                        setBgC(new Color(0,0,0,0));
                        setBordered(false);
                    }

                    @Override
                    public void performAction() {
                        showMenu^=true;
                        if(showMenu){
                            setDesktopState(DesktopState.TRAY_OPEN);
                        }else{
                            setDesktopState(DesktopState.DEFAULT);
                        }
                    }
                });
                switch (desktopState){
                    case DEFAULT:
                        break;
                    case TRAY_OPEN:
                        buttons.add(new MButton(10,getHeight()-getInsets().bottom-getInsets().top-180,32,32, this){
                            {
                                setImg(cmdIcon);
                                setBgC(new Color(0,0,0,0));
                                setBordered(false);
                            }

                            @Override
                            public void performAction() {
                                showMenu = false;
                                setDesktopState(DesktopState.DEFAULT);
                                windows.add(new CommandLine(50,50,320,240, PCInterface.this));
                            }
                        });
                        buttons.add(new MButton(10,getHeight()-getInsets().bottom-getInsets().top-140,32,32, this){
                            {
                                setImg(pipIcon);
                                setBgC(new Color(0,0,0,0));
                                setBordered(false);
                            }

                            @Override
                            public void performAction() {
                                showMenu = false;
                                setDesktopState(DesktopState.DEFAULT);
                                windows.add(new PIP(50,50,320,240, PCInterface.this));
                            }
                        });
                        break;
                }
                break;
        }
    }

    @Override
    public void destroy() {
        if (bootSoundPlayer!=null&&bootSoundPlayer.isPlaying()) {
            bootSoundPlayer.pause();
            bootSoundPlayer.close();
        }
        if (OSSoundPlayer!=null&&OSSoundPlayer.isPlaying()){
            OSSoundPlayer.pause();
            OSSoundPlayer.close();
        }
        shutDownPlayer = App.mApp.minim.loadFile(App.getResourceAsFile("shutdown.wav").getPath());
        shutDownPlayer.setGain(0.25f);
        shutDownPlayer.play();
        App.mApp.interfaces.remove(App.mApp.interfaces.indexOf(this));
        dispose();
    }

    @Override
    public void run() {
        boot();
        while (App.mApp.interfaces.contains(this)) {
            step();
            draw();
        }
    }
}
