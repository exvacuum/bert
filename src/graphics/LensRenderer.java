package graphics;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import gameobjs.Lens;

@SuppressWarnings("serial")
public class LensRenderer extends JPanel{
	
	public LensRenderer() {
   }
	
   @Override
   public void paintComponent(Graphics g) {     
	   super.paintComponent(g);
	   Lens.lens.render((Graphics2D)g);
   } 
}