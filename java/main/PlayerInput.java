package main;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;

public class PlayerInput implements ComponentListener {

    public PlayerInput(Player p) {

        p.addComponentListener(this);

        Action moveUp = new AbstractAction() {
            private static final long serialVersionUID = 1L;

            public void actionPerformed(ActionEvent e) {
                p.u = 1;
            }
        };
        Action stopUp = new AbstractAction() {
            private static final long serialVersionUID = 1L;

            public void actionPerformed(ActionEvent e) {
                p.u = 0;
            }
        };

        p.renderer.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_W, 0, false),
                "moveUp");
        p.renderer.getActionMap().put("moveUp",
                moveUp);
        p.renderer.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_W, 0, true),
                "stopUp");
        p.renderer.getActionMap().put("stopUp",
                stopUp);

        Action moveDown = new AbstractAction() {
            private static final long serialVersionUID = 1L;

            public void actionPerformed(ActionEvent e) {
                p.d = 1;
            }
        };
        Action stopDown = new AbstractAction() {
            private static final long serialVersionUID = 1L;

            public void actionPerformed(ActionEvent e) {
                p.d = 0;
            }
        };

        p.renderer.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_S, 0, false),
                "moveDown");
        p.renderer.getActionMap().put("moveDown",
                moveDown);
        p.renderer.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_S, 0, true),
                "stopDown");
        p.renderer.getActionMap().put("stopDown",
                stopDown);

        Action moveLeft = new AbstractAction() {
            private static final long serialVersionUID = 1L;

            public void actionPerformed(ActionEvent e) {
                p.l = 1;
            }
        };
        Action stopLeft = new AbstractAction() {
            private static final long serialVersionUID = 1L;

            public void actionPerformed(ActionEvent e) {
                p.l = 0;
            }
        };

        p.renderer.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_A, 0, false),
                "moveLeft");
        p.renderer.getActionMap().put("moveLeft",
                moveLeft);
        p.renderer.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_A, 0, true),
                "stopLeft");
        p.renderer.getActionMap().put("stopLeft",
                stopLeft);

        Action moveRight = new AbstractAction() {
            private static final long serialVersionUID = 1L;

            public void actionPerformed(ActionEvent e) {
                p.r = 1;
            }
        };
        Action stopRight = new AbstractAction() {
            private static final long serialVersionUID = 1L;

            public void actionPerformed(ActionEvent e) {
                p.r = 0;
            }
        };

        p.renderer.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_D, 0, false),
                "moveRight");
        p.renderer.getActionMap().put("moveRight",
                moveRight);
        p.renderer.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_D, 0, true),
                "stopRight");
        p.renderer.getActionMap().put("stopRight",
                stopRight);

    }

    @Override
    public void componentResized(ComponentEvent componentEvent) {

    }

    @Override
    public void componentMoved(ComponentEvent componentEvent) {

    }

    @Override
    public void componentShown(ComponentEvent componentEvent) {

    }

    @Override
    public void componentHidden(ComponentEvent componentEvent) {

    }
}

