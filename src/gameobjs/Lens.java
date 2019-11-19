package gameobjs;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Toolkit;

import javax.swing.JFrame;

import bertrand.Bertrand;
import bertrand.GameObj;
import bertrand.KeyBinds;
import graphics.LensRenderer;

public class Lens extends JFrame{
	private static final long serialVersionUID = 1L;

	public static Lens lens;
	Toolkit tk = Toolkit.getDefaultToolkit();
	public LensRenderer renderer;
	int width=300, height=300;
	Bertrand bert;
	GameObj o;
	public int winx, winy, offsetx, offsety,shakex,shakey;
	
	public Lens(Bertrand bert, GameObj o){
		lens = this;
		this.bert = bert;
		this.o = o;
		
		setSize(width,height);
		setTitle("LENS");
	    setLocationRelativeTo(null); 
	    setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
	    setIconImage(tk.getImage(bert.getClass().getResource("resources/bert.png")));
	    
		renderer = new LensRenderer();
	    
		add(renderer);
		setAlwaysOnTop(true);
		setResizable(false);
		
		new KeyBinds(bert, renderer, this);
		
		setVisible(true);
	}
	
	public void update() {
		if(!bert.objects.contains(o)) {
			bert.lens = null;
			this.dispose();
		}
		
		winx = getLocation().x;
		winy = getLocation().y;
		shakex = ((int)(Math.random()*2*(Math.random()>0.5?1:-1)));
		shakey = ((int)(Math.random()*2*(Math.random()>0.5?1:-1)));
		
		
		offsetx = bert.winx-winx+shakex;
		offsety =  bert.winy-winy+shakey;
	}
	
	public void render(Graphics2D g) {
		g.setColor(Color.DARK_GRAY);
		g.fillRect(0, 0, width, height);
		g.setColor(Color.GRAY);
		g.fillRect(offsetx,offsety, bert.renderer.getWidth(), bert.renderer.getHeight());
		g.setColor(Color.BLUE);
		for(GameObj o: bert.objects) {
			g.fillRect(offsetx+o.x,offsety+o.y,o.w,o.h);
		}
		g.setColor(Color.RED);
		g.drawImage(tk.getImage(bert.getClass().getResource("resources/oraclered.png")), 64+offsetx-shakex, 64+offsety-shakey, null);
		g.drawString("47 72 65 65 74 69 6e 67 73 2c 20 73 74 72 61 6e 67 65 20 6f 6e 65 2e 20 49 20 61 77 61 69 74 20 79 6f 75 20 69 6e 20 6d 79 20 72 65 61s 6c 6d 2e", 64+offsetx-shakex, 128+offsety-shakey);
	}
}
