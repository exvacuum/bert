package bertrand;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.MultipleGradientPaint.CycleMethod;
import java.awt.RadialGradientPaint;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.Timer;

import gameobjs.Building;
import gameobjs.Lens;
import graphics.Renderer;

public class Bertrand implements ActionListener{

	private static final String FOLDER = ("/Users/"+System.getProperty("user.name")+"/Documents/My Games/Bertrand/Universe");
	private static final String GAMEDATA = ("/Users/"+System.getProperty("user.name")+"/Documents/My Games/Bertrand/Data");
	
	public static Bertrand bert;
	
	public Renderer renderer;
	
	public Player player;
	
	public int rw, rh, winx, winy, winOffsetx, winOffsety;
	
	Toolkit tk = Toolkit.getDefaultToolkit();
	
	public ArrayList<GameObj> objects = new ArrayList<GameObj>();
	public ArrayList<Building> buildings = new ArrayList<Building>();
	public ArrayList<GameObj> trashObjects = new ArrayList<GameObj>();
	
	int width = 1280, height = 720;
	
	Viewport viewport = new Viewport((int)(tk.getScreenSize().getWidth()/2-width/2),(int)(tk.getScreenSize().getHeight()/2-height/2));
	
	File uniDir, dataDir;
	File[] uniFiles, dataFiles;
	Path universe;
	WatchService watcher;
	WatchKey watchKey;
	
	JFrame window;
	public Lens lens = null;
	
	boolean userMoved;
	
	Bertrand(){
		bert = this;
		
		uniDir = new File(FOLDER);
        uniDir.mkdirs();
        universe = Paths.get(FOLDER);
        
        dataDir = new File(GAMEDATA);
        dataDir.mkdirs();
        
        try {
        	
        	InputStream source = getClass().getResourceAsStream("resources/bert.ini");
            File dest = new File("/Users/"+System.getProperty("user.name")+"/Documents/My Games/Bertrand/Data/bert.ini");
        	if(!dest.exists()) {
        		Files.copy(source,dest.toPath());
        		source = getClass().getResourceAsStream("resources/bert.png");
                dest = new File("/Users/"+System.getProperty("user.name")+"/Documents/My Games/Bertrand/Universe/bert.png");
            	if(!dest.exists()) {
            		Files.copy(source,dest.toPath());
            	}
        	}
        	watcher = universe.getFileSystem().newWatchService();
	        watchKey = universe.register(watcher, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_DELETE, StandardWatchEventKinds.ENTRY_MODIFY);
		} catch (IOException e) {
			e.printStackTrace();
		}
        
		Timer timer = new Timer(20, this);
		window = new JFrame("Bertrand.");
		
		window.setSize(width,height);
	    window.setLocationRelativeTo(null); 
	    window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    window.setIconImage(tk.getImage(getClass().getResource("resources/bert.png")));
	    
		renderer = new Renderer();
	    
		window.add(renderer);
		window.setResizable(false);
		window.setVisible(true);
		
  
        
        uniFiles = uniDir.listFiles();
        
        for(File f:uniFiles) {
        	String filename = f.toString();
			File file = new File(filename);
			try {
				GameObj o;
				o = new GameObj((tk.getImage(f.toString())), filename, file, this);
				objects.add(o);
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
		
        new KeyBinds(this,renderer, window);
        
		timer.start();
		
	}
	
	void update() {
		if(player!=null) {
			player.update();
			viewport.trackPlayer(player);
			winx = window.getX();
			winy = window.getY();
			winOffsetx -= player.hv;
			winOffsety -= player.vv;
			player.setLocation(winx+player.o.x,winy+player.o.y);
			window.setLocation(-viewport.getxOffset()+winOffsetx,-viewport.getyOffset()+winOffsety);
		}

		if(lens!=null) {
			lens.update();
		}
		
		for(Building b: buildings) {
			b.update();
		}
		
		for(WatchEvent<?> event : watchKey.pollEvents()) {
			
			if(event.kind() == StandardWatchEventKinds.ENTRY_CREATE) {
				
				String filename = uniDir.getPath() +"\\"+ event.context().toString();
				File file = new File(filename);
				try {
					GameObj o;
					o = new GameObj((tk.getImage(uniDir.getPath() +"\\"+ event.context().toString())), filename, file, this);
					objects.add(o);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			if(event.kind() == StandardWatchEventKinds.ENTRY_DELETE) {
				String filename = uniDir.getPath() +"\\"+ event.context().toString();
				for(GameObj o : objects) {
					if(o.filename.equals(filename)) {
						trashObjects.add(o);
					}
				}
				objects.removeAll(trashObjects);
				trashObjects.clear();
			}
		}
	}
	
	public void render(Graphics2D g){
		rw = renderer.getWidth();
		rh = renderer.getHeight();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, rw, rh);
		g.setColor(Color.GRAY);
		g.setPaint(new RadialGradientPaint(new Rectangle2D.Float(0,0,rw,rh), new float[]{0.0f,1f}, new Color[]{new Color(255,255,255,100),new Color(255,255,255,0)}, CycleMethod.NO_CYCLE));
		g.fill(new Ellipse2D.Float(0,0, rw + 1, rh + 1));
		g.setPaint(null);
		g.setColor(Color.BLACK);
		g.drawImage(tk.getImage(getClass().getResource("resources/oracle.png")), 64, 64, null);
		for(GameObj o: objects){
			o.render(g);
		}
	}
	
	public static void main(String[] args) {
		bert = new Bertrand();
	}

		@Override
	public void actionPerformed(ActionEvent arg0) {
		update();
		renderer.repaint();
		if(player!=null)player.renderer.repaint();
		if(lens!=null)lens.renderer.repaint();
		for(Building b: buildings){
			b.renderer.repaint();
		}
	}
}
