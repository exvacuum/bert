package bertrand;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Renderer extends JPanel{
	
	Renderer() {
   }
	
   @Override
   public void paintComponent(Graphics g) {     
	   super.paintComponent(g);
	   Bertrand.bert.render((Graphics2D)g);
   } 
}