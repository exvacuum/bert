package dev.exvaccum.bert.sprites;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class TestPane extends JPanel {

    private SpriteSheet spriteSheet;
    private SpriteEngine spriteEngine;

    public TestPane() {
        try {
            BufferedImage sheet = ImageIO.read(new File("res/playerStrip.png"));
            spriteSheet = new SpriteSheetBuilder().
                    withSheet(sheet).
                    withColumns(4).
                    withRows(1).
                    withSpriteCount(4).
                    withSpriteSize(96,192).
                    build();

            spriteEngine = new SpriteEngine(25,1);
            spriteEngine.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    repaint();
                }
            });
            spriteEngine.start();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(200, 200);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        BufferedImage sprite = spriteSheet.getSprite(spriteEngine.getCycleProgress());
        int x = (getWidth() - sprite.getWidth()) / 2;
        int y = (getHeight() - sprite.getHeight()) / 2;
        g2d.drawImage(sprite, x, y, this);
        g2d.dispose();
    }

}
