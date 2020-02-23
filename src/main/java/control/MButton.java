package control;

import bert.App;
import world.interfaces.GameInterface;
import world.interfaces.PCInterface;

import java.awt.*;
import java.awt.image.BufferedImage;

public class MButton extends Rectangle{

    //Colors
    private Color bgC =  Color.WHITE;
    private Color textC =  Color.BLACK;
    private Color borderC = Color.BLACK;

    //Text & Font
    private String text = "Text";
    private Font fnt = App.mApp.biosFont;

    //Image
    BufferedImage img = null;

    //Allows borderless buttons
    private boolean bordered = true;

    public GameInterface owner;

    /**
     * Creates a button with specified dimensions
     * @param x X coordinate
     * @param y Y coordinate
     * @param width width
     * @param height height
     */
    public MButton(int x, int y, int width, int height, GameInterface owner){
        this.owner = owner;
        setBounds(x,y,width, height);
    }

    /**
     * Draw this button
     * @param g2 Graphics2D object for drawing
     */
    public void draw(Graphics2D g2){

        //Background
        g2.setColor(bgC);
        g2.fillRect(x,y,width,height);

        //Border
        if(bordered) {
            g2.setColor(borderC);
            g2.drawRect(x, y, width, height);
        }

        if(img!=null) {
            //Image
            g2.drawImage(img,(int)getX(),(int)getY(),(int)getWidth(),(int)getHeight(),null);
        }else {
            //Text
            g2.setFont(fnt);
            g2.setColor(textC);
            g2.drawString(text, (float) getX() + 10, (float) getMaxY() - 10);
        }

        //Hover
        if(containsMouse()&&owner.usable) {
            g2.setColor(new Color(0, 0, 0, 100));
            g2.fillRect(x, y, width, height);
        }
    }

    /**
     * Action to perform, should be overridden when creating.
     */
    public void performAction(){};

    public void setBordered(boolean bordered){
        this.bordered = bordered;
    }

     public boolean containsMouse(){
        return contains(App.mApp.input.mouseLocation.getX()-owner.getX()-owner.getInsets().left,App.mApp.input.mouseLocation.getY()-owner.getY()-owner.getInsets().top);
     }

     public void setText(String text){
        this.text = text;
     }

    public void setBgC(Color bgC){
        this.bgC = bgC;
    }

    public void setTextC(Color textC){
        this.textC = textC;
    }

    public void setBorderC(Color borderC){
        this.borderC = borderC;
    }

    public void setFnt(Font fnt){
        this.fnt = fnt;
    }

    public void setImg(BufferedImage img) {
        this.img = img;
    }
}
