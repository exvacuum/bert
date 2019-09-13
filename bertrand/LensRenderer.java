package bertrand;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class LensRenderer extends JPanel{
	
	LensRenderer() {
   }
	
   @Override
   public void paintComponent(Graphics g) {     
	   super.paintComponent(g);
	   Lens.lens.render((Graphics2D)g);
   } 
}