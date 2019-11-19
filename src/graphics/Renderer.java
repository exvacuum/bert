package graphics;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import bertrand.Bertrand;

@SuppressWarnings("serial")
public class Renderer extends JPanel{
	
	public Renderer() {
   }
	
   @Override
   public void paintComponent(Graphics g) {     
	   super.paintComponent(g);
	   Bertrand.bert.render((Graphics2D)g);
   } 
}