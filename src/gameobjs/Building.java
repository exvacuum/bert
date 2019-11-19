package gameobjs;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Toolkit;

import javax.swing.JFrame;

import bertrand.Bertrand;
import bertrand.GameObj;
import graphics.BuildingRenderer;

public class Building extends JFrame{
	private static final long serialVersionUID = 1L;

	public static final int TYPE_HOUSE = 0;
	
	int type, width, height, x, y, offsetx, offsety;
	GameObj o;
	Bertrand bert;
	public BuildingRenderer renderer;
	Toolkit tk = Toolkit.getDefaultToolkit();
	
	public Building(int type, GameObj o, Bertrand bert) {
		this.type = type;
		this.bert = bert;
		this.o = o;
		
		switch(type) {
		case TYPE_HOUSE:
			width = 200;
			height = 200;
			break;
		default:
			width = 200;
			height = 200;
		}
		
		
		setSize(width,height);
		setTitle("HOUSE");
	    setLocationRelativeTo(null); 
	    x = getLocation().x;
	    y = getLocation().y;
	    setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
	    setIconImage(tk.getImage(bert.getClass().getResource("resources/bert.png")));
	    
	    renderer = new BuildingRenderer(this);
	    add(renderer);
		
	    setAlwaysOnTop(true);
		setResizable(false);
		setVisible(true);
	}
	
	public void update() {
		setLocation(x, y);
		
		int winx = getLocation().x;
		int winy = getLocation().y;
		offsetx = bert.winx-winx;
		offsety =  bert.winy-winy;
	}
	
	public void render(Graphics2D g){
		g.setColor(Color.DARK_GRAY);
		g.fillRect(0, 0, width, height);
		g.drawImage(tk.getImage(bert.getClass().getResource("resources/bert.png")), bert.player.o.x+offsetx, bert.player.o.y+offsety, null);
	}
}
