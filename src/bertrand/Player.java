package bertrand;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JFrame;

import graphics.PlayerRenderer;

public class Player extends JFrame{
	private static final long serialVersionUID = 1L;
	public double vin, hin, vdir, hdir, vv, hv, mv = 5;
	private double tv, acc = 0.3;
	public int l = 0, r = 0, u = 0, d = 0;
	
	Bertrand bert;
	public GameObj o;
	Toolkit tk = Toolkit.getDefaultToolkit();
	Image img;
	PlayerRenderer renderer;
	
	Player(Bertrand bert, Image img, GameObj o){
		this.bert = bert;
		this.o = o;
		this.img = img;
		
		setSize(200,200);
	    setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
	    setIconImage(tk.getImage(bert.getClass().getResource("resources/bert.png")));

		renderer = new PlayerRenderer(this);
	    
		add(renderer);
		
		setUndecorated(true);
		setFocusable(true);
		setBackground(new Color(0, 255, 0, 0));
		setAlwaysOnTop(true);
		setContentPane(renderer);
		getContentPane().setBackground(Color.BLACK);
		setLayout(new BorderLayout());
		setResizable(false);
		
		new KeyBinds(bert,renderer, null);
		
		setVisible(true);
	}
	
	void update() {
		if(!bert.objects.contains(o)) {
			bert.player = null;
			this.dispose();
		}
		hin = r-l;
		vin = d-u;
		hdir = Math.signum(hv);
		vdir = Math.signum(vv);
		tv = Math.hypot(hv, vv);
		
		if(hin!=0) {
		
			if(Math.abs(hv+hin)<mv) {
				hv += hin*acc;
			}else {
				hv = hdir*mv;
			}
		
		}else{
			
			if(hv*hdir>(acc/3)){
				hv-=hdir*(acc/3);
			}else{
				hv = 0;
			}
			
		}
		
		if(vin!=0) {
			
			if(Math.abs(vv+vin)<mv) {
				vv += vin*acc;
			}else {
				vv = vdir*mv;
			}
		
		}else{
			
			if(vv*vdir>(acc/3)){
				vv-=vdir*(acc/3);
			}else{
				vv = 0;
			}
			
		}
		
		o.x+=hv;
		o.y+=vv;
	}
	
	public void render(Graphics2D g) {
        g.drawImage(img, 0, 25, null);
		g.setColor(Color.BLACK);
		g.drawString(String.format("%.3f",hv)  + ", " + String.format("%.3f",vv) + ": " + String.format("%.3f",tv) , 0, 10);
		g.drawString(hin + ", " + vin, 32, 30);
		g.drawString(hdir + ", " + vdir, 32, 50);
	}
}
