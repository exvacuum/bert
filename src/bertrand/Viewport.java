package bertrand;

import java.awt.Toolkit;

public class Viewport {
	
	//Offset for drawing everything
	private int xOffset, yOffset;
	public int portOffsetx = Toolkit.getDefaultToolkit().getScreenSize().width/2, portOffsety = Toolkit.getDefaultToolkit().getScreenSize().height/2;
	public boolean progMoved = false;
	
	Toolkit tk = Toolkit.getDefaultToolkit();

	public Viewport(int xOffset, int yOffset){
		//Starting Offset(Usually should be 0,0) 
		this.xOffset = xOffset;
		this.yOffset = yOffset;
		
	}

	//Center View on player
	public void trackPlayer(Player player){
		xOffset =  (int)(player.o.x-player.hv-portOffsetx);
		yOffset =  (int)(player.o.y-player.vv-portOffsety);
	}
	
	//Getters for offset
	public int getxOffset() {
		return xOffset;
	}

	public int getyOffset() {
		return yOffset;
	}

}
