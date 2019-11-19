package graphics;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import bertrand.Player;

@SuppressWarnings("serial")
public class PlayerRenderer extends JPanel{
	
	Player player;
	
	public PlayerRenderer(Player player) {
		this.player = player;
		setOpaque(false);
   }
	
   @Override
   public void paintComponent(Graphics g) {     
	   super.paintComponent(g);
	   player.render((Graphics2D)g);
   } 
}