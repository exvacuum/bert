package graphics;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import gameobjs.Building;

@SuppressWarnings("serial")
public class BuildingRenderer extends JPanel{
	
	Building building;
	
	public BuildingRenderer(Building building) {
		this.building = building;
   }
	
   @Override
   public void paintComponent(Graphics g) {     
	   super.paintComponent(g);
	   building.render((Graphics2D)g);
   } 
}