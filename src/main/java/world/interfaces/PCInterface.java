package world.interfaces;

import bert.App;
import control.MButton;
import ddf.minim.AudioPlayer;
import world.objects.GameObject;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class PCInterface extends GameInterface implements Runnable{

    GameObject owner;
    BufferedImage biosLogo, intertiaStarLogo, taskbarlogo;
    public BufferedImage screenImg;
    String[] sysinfo;
    int lines = 0;
    long currMem = 0;
    long memory;
    AudioPlayer bootSoundPlayer, OSSoundPlayer, shutDownPlayer;
    RadialGradientPaint desktop;
    boolean showMenu = false;

    Screen screen = Screen.BOOT;
    enum Screen{
        BOOT,
        LOGIN,
        DESKTOP
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
        }catch (IOException e){
            e.printStackTrace();
        }

        setTitle("guerrOS");
        pane.setBackground(Color.BLACK);
        memory = 16777216l;
        sysinfo = new String[]{
                "NAX Veneron MTpXSFk=",
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
                g2.drawString("PLACEHOLDER Release MM/DD/2002 S", 20, biosLogo.getHeight() + 60);
                for (int i = 0; i < lines; i++) {
                    g2.drawString(sysinfo[i], 20, biosLogo.getHeight() + 90 + (30 * i));
                }
                g2.drawString("(C) Avuvyvfgvp Nofheqvgl Vap.,", 20, getHeight() - 70);
                g2.drawString("01010100-01101001-01101110-01111001-01010101-01010010-01001100-PLACEHOLDER", 20, getHeight() - 50);
                ig.setColor(Color.WHITE);
                ig.setFont(App.mApp.biosFont);
                ig.drawImage(biosLogo, 10, 10, null);
                ig.drawImage(intertiaStarLogo, getWidth() - intertiaStarLogo.getWidth() - 20, 10, null);
                ig.drawString("NAIBIOS (C)2002 Avuvyvfgvp Nofheqvgl Vap.,", 20, biosLogo.getHeight() + 30);
                ig.drawString("PLACEHOLDER Release MM/DD/2002 S", 20, biosLogo.getHeight() + 60);
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
                g2.fillRect(0,getHeight()-getInsets().bottom-getInsets().top-50,getWidth(),50);
                if(showMenu){
                    g2.fillRect(0,getHeight()-getInsets().bottom-getInsets().top-200,100,150);
                }
                for (int i = 0; i < buttons.size(); i++) {
                    MButton button = buttons.get(i);
                    button.draw(g2);
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
                }
                for (int i = 0; i < buttons.size(); i++) {
                    MButton button = buttons.get(i);
                    button.draw(ig);
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
        switch (screen){

        }
    }

    void setScreen(Screen screen){
        this.screen = screen;
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
                    }
                });
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
