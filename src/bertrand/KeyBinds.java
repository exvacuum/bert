package bertrand;

import java.awt.event.ActionEvent;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

public class KeyBinds implements ComponentListener{
	
Bertrand bert;
	

	@Override
	public void componentHidden(ComponentEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void componentMoved(ComponentEvent arg0) {
		if(bert.player!=null&&!bert.viewport.progMoved&&bert.userMoved) {
			bert.player.u = 0;
			bert.player.d = 0;
			bert.player.l = 0;
			bert.player.r = 0;
		}
		
	}
	
	@Override
	public void componentResized(ComponentEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void componentShown(ComponentEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public KeyBinds(Bertrand bert, JPanel renderer, JFrame component) {
		this.bert = bert;
		
		if(component!=null)component.addComponentListener(this);
		
		Action moveUp = new AbstractAction() {
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e) {
				if(bert.player!=null)bert.player.u = 1;
		    }
		};
		Action stopUp = new AbstractAction() {
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e) {
				if(bert.player!=null)bert.player.u = 0;
		    }
		};
		
		renderer.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_W, 0, false),
	            "moveUp");
		renderer.getActionMap().put("moveUp",
		                 moveUp);
		renderer.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_W, 0, true),
		"stopUp");
		renderer.getActionMap().put("stopUp",
		stopUp);
		
		Action moveDown = new AbstractAction() {
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e) {
				if(bert.player!=null)bert.player.d = 1;
		    }
		};
		Action stopDown = new AbstractAction() {
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e) {
				if(bert.player!=null)bert.player.d = 0;
		    }
		};
		
		renderer.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_S, 0, false),
	            "moveDown");
		renderer.getActionMap().put("moveDown",
		                 moveDown);
		renderer.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_S, 0, true),
		"stopDown");
		renderer.getActionMap().put("stopDown",
		stopDown);
		
		Action moveLeft = new AbstractAction() {
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e) {
				if(bert.player!=null)bert.player.l = 1;
		    }
		};
		Action stopLeft = new AbstractAction() {
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e) {
				if(bert.player!=null)bert.player.l = 0;
		    }
		};
		
		renderer.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_A, 0, false),
	            "moveLeft");
		renderer.getActionMap().put("moveLeft",
		                 moveLeft);
		renderer.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_A, 0, true),
		"stopLeft");
		renderer.getActionMap().put("stopLeft",
		stopLeft);
		
		Action moveRight = new AbstractAction() {
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e) {
				if(bert.player!=null)bert.player.r = 1;
		    }
		};
		Action stopRight = new AbstractAction() {
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e) {
				if(bert.player!=null)bert.player.r = 0;
		    }
		};
		
		renderer.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_D, 0, false),
	            "moveRight");
		renderer.getActionMap().put("moveRight",
		                 moveRight);
		renderer.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_D, 0, true),
		"stopRight");
		renderer.getActionMap().put("stopRight",
		stopRight);
	}
}
