package world.interfaces;

import javax.swing.*;
import java.awt.*;

class InterfacePanel extends JPanel{

    GameInterface owner;

    public InterfacePanel(GameInterface owner){
        this.owner = owner;
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        super.paintComponent(g2);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        owner.paintGraphics(g2);
    }
}
