package dev.exvaccum.bert.world.interfaces.pc;

import ddf.minim.AudioPlayer;
import ddf.minim.analysis.BeatDetect;
import ddf.minim.analysis.FFT;
import dev.exvaccum.bert.Bert;
import dev.exvaccum.bert.control.Utilities;
import dev.exvaccum.bert.control.WButton;
import dev.exvaccum.bert.control.WDraggable;
import dev.exvaccum.bert.world.interfaces.PCInterface;
import dev.exvaccum.bert.world.interfaces.pc.files.MetaFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class SoundPlayer extends MetaWindow {

    //Audio to play
    public AudioPlayer audio;
    FFT fft;
    BeatDetect beatDetect;

    //Controls
    WButton playbackButton;
    WDraggable barButton;
    static BufferedImage pauseIcon, playIcon;
    static{
        try {
            pauseIcon = ImageIO.read(Utilities.getResourceAsFile("pauseicon.png"));
            playIcon = ImageIO.read(Utilities.getResourceAsFile("playicon.png"));
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * Sound player, shows a desired audio from file browser
     * @param x x-coordinate of window
     * @param y y-coordinate of window
     * @param width width of window
     * @param height  height of window
     * @param owner PC interface to which this window belongs
     * @param fname filename for image loading
     */
    public SoundPlayer(int x, int y, int width, int height, PCInterface owner, String fname) {
        super(x, y, width, height, owner);
        title = "Audio Player - "+fname;
        bgColor = new Color(0,0,0,50);
        type = WindowType.IMAGE_VIEWER;
        resizable = true;

        //Load audio
        audio = Bert.mBert.minim.loadFile(Utilities.getResourceAsFile(MetaFile.pathMap.get(fname)).getPath(),1024);
        fft = new FFT(audio.bufferSize(),audio.sampleRate());
        beatDetect = new BeatDetect();
        beatDetect.detectMode(BeatDetect.SOUND_ENERGY);

        bounds.height += titleBar.height;

        playbackButton = new WButton(bounds.x+bounds.width-24,bounds.y+titleBar.height,24,24,this){
            {
                setImg(pauseIcon);
            }

            @Override
            public void performAction(){
                if(audio.isPlaying()){
                    audio.pause();
                    setImg(playIcon);
                }else{
                    audio.play();
                    setImg(pauseIcon);
                }
            }
        };

        barButton = new WDraggable(bounds.x+24,bounds.y+bounds.height-20,bounds.width-24,16, this){
            {
                setBgC(new Color(0,0,0,0));
                setText("");
                setBordered(false);
            }

            @Override
            public void performAction(){
                audio.cue((int)((Bert.mBert.input.mouseLocation.x-ownerWindow.owner.getX()-ownerWindow.owner.getInsets().left-ownerWindow.bounds.x-24)/(bounds.width-24.0)*audio.length()));
            }
        };

        windowButtons.add(playbackButton);
        windowDraggables.add(barButton);

        audio.play();


    }

    @Override
    public void update(){
        super.update();
        playbackButton.setBounds(bounds.x,bounds.y+bounds.height-24,24,24);
        barButton.setBounds(bounds.x+24,bounds.y+bounds.height-20,bounds.width-24,16);
    }

    @Override
    public void draw(Graphics2D g2) {
        super.draw(g2);

        fft.forward(audio.mix);
        for (int i = 0; i < fft.specSize(); i++) {
            g2.setColor(Color.MAGENTA.darker().darker());
            g2.drawLine(bounds.x+(int) (i / (double) fft.specSize() * bounds.width), bounds.y+bounds.height, bounds.x+(int) (i / (double) fft.specSize() * bounds.width), Math.max(bounds.y+(int) (bounds.height - fft.getBand(i) * 10),bounds.y+titleBar.height));
        }

        g2.setColor(Color.MAGENTA);
        for (int i = 0; i < bounds.width; i++) {
            g2.drawLine(bounds.x+i, (int) (bounds.y+bounds.height+titleBar.height - (bounds.height+titleBar.height)/2 + audio.left.get(i/2) * 25), bounds.x+i + 1, (int) (bounds.y+bounds.height+titleBar.height - (bounds.height+titleBar.height)/2 + audio.left.get(i/2 + 1) * 25));
            g2.drawLine(bounds.x+i, (int) (bounds.y+bounds.height+titleBar.height - (bounds.height+titleBar.height)/2 + audio.right.get(i/2) * 25), bounds.x+i + 1, (int) (bounds.y+bounds.height+titleBar.height - (bounds.height+titleBar.height)/2 + audio.right.get(i/2 + 1) * 25));
        }

        g2.setColor(Color.GREEN);
        g2.fillRect(bounds.x+24,bounds.y+bounds.height-20,(int)((audio.position()/(double)audio.length())*(bounds.width-24)),16);

        playbackButton.draw(g2);
        barButton.draw(g2);

    }

    @Override
    public void close(){
        super.close();
        if(audio.isPlaying())audio.close();
    }
}
