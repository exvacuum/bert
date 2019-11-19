package bertrand;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Toolkit;

import javax.swing.JFrame;

public class Lens extends JFrame{
	private static final long serialVersionUID = 1L;

	static Lens lens;
	Toolkit tk = Toolkit.getDefaultToolkit();
	LensRenderer renderer;
	int width=300, height=300;
	Bertrand bert;
	GameObj o;
	public int winx, winy, offsetx, offsety;
	
	Lens(Bertrand bert, GameObj o){
		lens = this;
		this.bert = bert;
		this.o = o;
		
		setSize(width,height);
	    setLocationRelativeTo(null); 
	    setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
	    setIconImage(tk.getImage(getClass().getResource("resources/bert.png")));
	    
		renderer = new LensRenderer();
	    
		add(renderer);
		
		setResizable(false);
		setVisible(true);
	}
	
	void update() {
		if(!bert.objects.contains(o)) {
			bert.lens = null;
			this.dispose();
		}
		
		winx = getLocation().x;
		winy = getLocation().y;
		offsetx = bert.winx-winx+((int)(Math.random()*5*(Math.random()>0.5?1:-1)));
		offsety =  bert.winy-winy+((int)(Math.random()*5*(Math.random()>0.5?1:-1)));
	}
	
	void render(Graphics2D g) {
		g.setColor(Color.DARK_GRAY);
		g.fillRect(0, 0, width, height);
		g.setColor(Color.GRAY);
		g.fillRect(offsetx,offsety, bert.renderer.getWidth(), bert.renderer.getHeight());
		g.setColor(Color.BLUE);
		for(GameObj o: bert.objects) {
			g.fillRect(offsetx+o.x,offsety+o.y,o.w,o.h);
		}
	}
}
