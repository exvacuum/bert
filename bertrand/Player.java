package bertrand;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Toolkit;

public class Player {
	
	private double vin, hin, vdir, hdir, vv, hv, mv = 5;
	private double tv, acc = 0.3;
	
	Bertrand bert;
	GameObj o;
	Toolkit tk = Toolkit.getDefaultToolkit();
	
	
	Player(Bertrand bert, GameObj o){
		this.bert = bert;
		this.o = o;
	}
	
	void update() {
		if(!bert.objects.contains(o)) {
			bert.player = null;
		}
		hin = bert.r-bert.l;
		vin = bert.d-bert.u;
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
	
	void render(Graphics2D g) {
		g.setColor(Color.BLACK);
		g.drawString(hv + ", " + vv + ": " + tv , o.x, o.y-10);
		g.drawString(hin + ", " + vin, o.x+32, o.y+10);
		g.drawString(hdir + ", " + vdir, o.x+32, o.y+30);
	}
}
