package control;

import bert.App;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class Input implements KeyListener, MouseListener, MouseMotionListener {

    public static int KEY_INTERACT = KeyEvent.getExtendedKeyCodeForChar('Z');
    public static int KEY_BACK = KeyEvent.getExtendedKeyCodeForChar('X');
    public static int KEY_UP = KeyEvent.VK_UP;
    public static int KEY_DOWN = KeyEvent.VK_DOWN;
    public static int KEY_LEFT = KeyEvent.VK_LEFT;
    public static int KEY_RIGHT = KeyEvent.VK_RIGHT;

    public Point mouseLocation = new Point(0,0);

    public void update(){
        mouseLocation.setLocation(MouseInfo.getPointerInfo().getLocation().x, MouseInfo.getPointerInfo().getLocation().y);
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        if (keyEvent.getKeyCode() == KEY_INTERACT){
        }
        if (keyEvent.getKeyCode() == KEY_BACK){

        }
        if (keyEvent.getKeyCode() == KEY_UP) {
            App.mApp.world.player.u = 1;
        }
        if (keyEvent.getKeyCode() == KEY_DOWN){
            App.mApp.world.player.d = 1;

        }
        if (keyEvent.getKeyCode() == KEY_LEFT){
            App.mApp.world.player.l = 1;

        }
        if (keyEvent.getKeyCode() == KEY_RIGHT){
            App.mApp.world.player.r = 1;

        }
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
        if(App.mApp.player!=null) {
            if (keyEvent.getKeyCode() == KEY_INTERACT) {
                if (App.mApp.world.currentRoom.getInteractiveObjectClosestToPlayer() != null) {
                    App.mApp.world.currentRoom.getInteractiveObjectClosestToPlayer().interact();
                }
            }
            if (keyEvent.getKeyCode() == KEY_BACK) {

            }
            if (keyEvent.getKeyCode() == KEY_UP) {
                App.mApp.world.player.u = 0;

            }
            if (keyEvent.getKeyCode() == KEY_DOWN) {
                App.mApp.world.player.d = 0;

            }
            if (keyEvent.getKeyCode() == KEY_LEFT) {
                App.mApp.world.player.l = 0;

            }
            if (keyEvent.getKeyCode() == KEY_RIGHT) {
                App.mApp.world.player.r = 0;
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {

    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        //Check for button presses
        for (int i = 0; i < App.mApp.interfaces.size(); i++) {
            ArrayList<MButton> buttons = App.mApp.interfaces.get(i).buttons;
            for (int j = 0; j < buttons.size(); j++){
                MButton b = buttons.get(j);
                if(b.containsMouse()&&b.owner.usable){
                    b.performAction();
                }
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseDragged(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseMoved(MouseEvent mouseEvent) {

    }
}
