package bert;

import control.Input;
import control.MinimHelper;
import ddf.minim.Minim;
import player.Player;
import world.interfaces.GameInterface;
import world.rooms.Bedroom;
import world.rooms.World;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class App extends JFrame {

    public static App mApp;

    public static boolean debug = false;

    //Player
    public Player player;

    //World Vars
    int ox = 0;
    int oy = 0;

    public static final int TPS = 60;
    public static final int TICK_DELAY = 1000/TPS;

    int iconImg = 0;

    //Windows
    public World world = new World();

    //Interfaces
    public ArrayList<GameInterface> interfaces = new ArrayList<>();

    //Graphics Objects
    JPanel pane = new MPanel();

    public boolean ethereal;

    //Input handler
    public Input input = new Input();

    //Audio
    public Minim minim;
    MinimHelper helper;

    //Fonts
    public Font h1Font, h2Font, pFont, biosFont;

    public static void main(String[] args) {
        mApp = new App();
    }

    public App(){
        init();

        while (true){
            if(System.currentTimeMillis()%TICK_DELAY==0) {
                step();
            }
            draw();
        }
    }

    void init(){

        helper = new MinimHelper();
        minim = new Minim(helper);

        try {
            //Custom Handwriting fonts
            h1Font = Font.createFont(Font.TRUETYPE_FONT, getResourceAsFile("hwfont.ttf")).deriveFont(76f);
            h2Font = Font.createFont(Font.TRUETYPE_FONT, getResourceAsFile("hwfont.ttf")).deriveFont(48f);
            pFont = Font.createFont(Font.TRUETYPE_FONT, getResourceAsFile("hwfont.ttf")).deriveFont(24f);
            //BIOS Font
            biosFont = Font.createFont(Font.TRUETYPE_FONT, getResourceAsFile("amibios.ttf")).deriveFont(8f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            //register the font
            ge.registerFont(h1Font);
            ge.registerFont(h2Font);
            ge.registerFont(pFont);
        } catch (IOException e) {
            e.printStackTrace();
        } catch(FontFormatException e) {
            e.printStackTrace();
        }

        if(mApp==null){
            mApp=this;
        }

        setVisible(false);
        setUndecorated(true);
        setBackground(new Color(0,0,0,0));
        setSize(Toolkit.getDefaultToolkit().getScreenSize());
        try {
            setIconImage(ImageIO.read(getResourceAsFile("icon0.png")));
        }catch (IOException e){
            e.printStackTrace();
        }
        setVisible(true);
        pane.setOpaque(false);
        add(pane);
        world.add(new Bedroom(this));
        player = new Player(this);
        world.add(player);
//        JDialog toggles = new JDialog();
//        JButton etherealButton = new JButton("Toggle Window Binding");
//        etherealButton.addActionListener(actionEvent -> ethereal^=true);
//        toggles.add(etherealButton);
//        toggles.pack();
//        toggles.setVisible(true);
    }

    void step(){
        world.update();
        input.update();
        for (int i = 0; i < interfaces.size(); i++) {
            GameInterface iF = interfaces.get(i);
            iF.update();
        }
    }

    void draw(){
        pane.repaint();
        player.pane.repaint();
        world.repaint();
    }

    /**
     * Utility function for getting a file from the resource folder, necessary to make a functional JAR
     * @param resourcePath path to resource
     * @return File from resources folder
     */
    public static File getResourceAsFile(String resourcePath) {
        try {
            InputStream in = ClassLoader.getSystemClassLoader().getResourceAsStream(resourcePath);
            if (in == null) {
                return null;
            }

            File tempFile = File.createTempFile(String.valueOf(in.hashCode()), ".tmp");
            tempFile.deleteOnExit();

            try (FileOutputStream out = new FileOutputStream(tempFile)) {
                //copy stream
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = in.read(buffer)) != -1) {
                    out.write(buffer, 0, bytesRead);
                }
            }
            return tempFile;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
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
