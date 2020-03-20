package dev.exvaccum.bert;

import dev.exvaccum.bert.control.Input;
import dev.exvaccum.bert.control.MinimHelper;
import ddf.minim.Minim;
import dev.exvaccum.bert.control.Utilities;
import dev.exvaccum.bert.player.Player;
import dev.exvaccum.bert.world.interfaces.GameInterface;
import dev.exvaccum.bert.world.rooms.Bedroom;
import dev.exvaccum.bert.world.rooms.World;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class Bert extends JFrame {

    //Game instance singleton
    public static Bert mBert;

    //Boolean to determine whether to display debug information
    public static boolean debug = false;

    //Player
    public Player player;

    //Windows
    public World world = new World();

    //Interfaces
    public ArrayList<GameInterface> interfaces = new ArrayList<>();

    //Graphics Objects
    JPanel pane = new MPanel();

    //Boolean determining whether clipping through window boundaries is permitted
    public boolean ethereal;

    //Input handler
    public Input input = new Input();
    public ControllerObj controller = ControllerObj.PLAYER;
    public enum ControllerObj{
        PLAYER, COMPUTER
    }

    //Audio handling is done using the wonderful Minim library
    public Minim minim;
    MinimHelper helper;

    //Fonts
    public Font h1Font, h2Font, pFont, biosFont;

    //Frame Limiter
    public static final int TPS = 120;
    long next;


    /**
     *
     * BERT:06/23
     *
     */
    public static void main(String[] args) {

        //Create instance of game
        new Bert();
    }

    public Bert(){

        //Initialize window
        init();

        while (true){

            //Step 60 TPS
            long time = System.currentTimeMillis();
            if(time>=next) {
                step();
                next = time + 1000 / TPS;
            }

            //Draw as often as possible
            draw();
        }
    }

    /**
     * Initialize Game
     *  - Make static reference
     *  - Minim audio library
     *  - Fonts
     *  - Initialize main invisible window
     *  - Initialize world
     */
    void init(){

        //Assign value to static instance
        if(mBert == null){
            mBert = this;
        }

        //Create Minim audio-related objects
        helper = new MinimHelper();
        minim = new Minim(helper);

        try {

            //Custom Handwriting fonts
            h1Font = Font.createFont(Font.TRUETYPE_FONT, Utilities.getResourceAsFile("hwfont.ttf")).deriveFont(76f);
            h2Font = Font.createFont(Font.TRUETYPE_FONT, Utilities.getResourceAsFile("hwfont.ttf")).deriveFont(48f);
            pFont = Font.createFont(Font.TRUETYPE_FONT, Utilities.getResourceAsFile("hwfont.ttf")).deriveFont(24f);

            //BIOS Font
            biosFont = Font.createFont(Font.TRUETYPE_FONT, Utilities.getResourceAsFile("amibios.ttf")).deriveFont(8f);

            //Register the custom fonts
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(h1Font);
            ge.registerFont(h2Font);
            ge.registerFont(pFont);
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }

        //Initialize window
        setVisible(false);
        setUndecorated(true);
        setBackground(new Color(0,0,0,0));
        setSize(Toolkit.getDefaultToolkit().getScreenSize());
        try {
            setIconImage(ImageIO.read(Utilities.getResourceAsFile("icon0.png")));
        }catch (IOException e){
            e.printStackTrace();
        }
        setVisible(true);
        pane.setOpaque(false);
        add(pane);

        //Initialize World
        world.add(new Bedroom());
        player = new Player();
        world.add(player);
    }

    /**
     * Handle game logic
     *  - Update world logic
     *  - Update input variables
     *  - Update UI
     */
    void step(){
        world.update();
        input.update();
        for (int i = 0; i < interfaces.size(); i++) {
            GameInterface iF = interfaces.get(i);
            iF.update();
        }
    }

    /**
     * Handle painting
     *  - Repaint main window (invisible)
     *  - Repaint player
     *  - Repaint world
     */
    void draw(){
        pane.repaint();
        player.pane.repaint();
        world.repaint();
    }

    class MPanel extends JPanel{

        @Override
        public void paintComponent(Graphics g){
            Graphics2D g2 = (Graphics2D) g;
            super.paintComponent(g2);
            g2.setColor(Color.RED);
            for(Rectangle r: world.roomBounds){
                if (debug) {
                    g2.setStroke(new BasicStroke(5));
                    g2.drawRect(r.x, r.y, r.width, r.height);
                }
            }
        }
    }
}
