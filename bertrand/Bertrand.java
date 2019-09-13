package bertrand;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.MultipleGradientPaint.CycleMethod;
import java.awt.RadialGradientPaint;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
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

public class Bertrand implements ActionListener, KeyListener{

	private static final String FOLDER = ("/Users/"+System.getProperty("user.name")+"/Documents/My Games/Bertrand/Universe");
	private static final String GAMEDATA = ("/Users/"+System.getProperty("user.name")+"/Documents/My Games/Bertrand/Data");
	
	public static Bertrand bert;
	
	Renderer renderer;
	
	public Player player;
	
	public int l = 0, r = 0, u = 0, d = 0, rw, rh, winx, winy;
	
	Toolkit tk = Toolkit.getDefaultToolkit();
	
	ArrayList<GameObj> objects = new ArrayList<GameObj>();
	ArrayList<GameObj> trashObjects = new ArrayList<GameObj>();
	
	int width = 1280, height = 720;
	
	File uniDir, dataDir;
	File[] uniFiles, dataFiles;
	Path universe;
	WatchService watcher;
	WatchKey watchKey;
	
	JFrame window;
	Lens lens = null;
	
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
        
        
        
        uniFiles = uniDir.listFiles();
        
        for(File f:uniFiles) {
        	String filename = f.toString();
			File file = new File(filename);
			System.out.println(filename);
			try {
				GameObj o;
				o = new GameObj((tk.getImage(f.toString())), filename, file, this);
				objects.add(o);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        
		Timer timer = new Timer(20, this);
		window = new JFrame("Bertrand.");
		
		window.setSize(width,height);
	    window.setLocationRelativeTo(null); 
	    window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    window.setIconImage(tk.getImage(getClass().getResource("resources/bert.png")));
	    
		renderer = new Renderer();
	    
		window.add(renderer);
		window.addKeyListener(this);
		window.setResizable(false);
		window.setVisible(true);
		
		timer.start();
		
	}
	
	void update() {
		winx = window.getLocation().x;
		winy = window.getLocation().y;
		
		if(lens!=null) {
			lens.update();
		}
		
		if(player!=null) {
			player.update();
		}
		
		for(WatchEvent<?> event : watchKey.pollEvents()) {
			System.out.println(event.kind());
			
			if(event.kind() == StandardWatchEventKinds.ENTRY_CREATE) {
				
				String filename = uniDir.getPath() +"\\"+ event.context().toString();
				File file = new File(filename);
				System.out.println(filename);
				try {
					GameObj o;
					o = new GameObj((tk.getImage(uniDir.getPath() +"\\"+ event.context().toString())), filename, file, this);
					objects.add(o);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			if(event.kind() == StandardWatchEventKinds.ENTRY_DELETE) {
				String filename = uniDir.getPath() +"\\"+ event.context().toString();
				System.out.println(filename);
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
	
	void render(Graphics2D g){
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
		if(player!=null) {
			if(Math.abs(player.o.x-64)<=64&&Math.abs(player.o.y-64)<=64) {
				g.setColor(Color.WHITE);
				g.drawString("47 72 65 65 74\n 69 6e 67 73 2c\n 20 73 74 72 61\n 6e 67 65 20 6f\n 6e 65 2e 20 49\n 20 61 77 61 69\n 74 20 79 6f 75\n 20 69 6e 20 6d\n 79 20 72 65 61\n 6c 6d 2e", 64, 128);
			}
			player.render(g);		
		}
	}
	
	public static void main(String[] args) {
		bert = new Bertrand();
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		int id = arg0.getKeyCode();
		switch(id) {
		case KeyEvent.VK_W:
			u = 1;
			break;
		case KeyEvent.VK_A:
			l = 1;
			break;
		case KeyEvent.VK_S:
			d = 1;
			break;
		case KeyEvent.VK_D:
			r = 1;
			break;
		default:
			break;
		}
		//System.out.println(u + "" + d + " " + l + "" + r);
		
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		int id = arg0.getKeyCode();
		switch(id) {
		case KeyEvent.VK_W:
			u = 0;
			break;
		case KeyEvent.VK_A:
			l = 0;
			break;
		case KeyEvent.VK_S:
			d = 0;
			break;
		case KeyEvent.VK_D:
			r = 0;
			break;
		default:
			break;
		}
		//System.out.println(u + "" + d + " " + l + "" + r);
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		update();
		renderer.repaint();
		if(lens!=null)lens.renderer.repaint();
	}
}
