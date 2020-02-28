package dev.exvaccum.bert.control;

import dev.exvaccum.bert.Bert;
import dev.exvaccum.bert.world.interfaces.PCInterface;
import dev.exvaccum.bert.world.interfaces.pc.MetaWindow;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class Input implements KeyListener, MouseListener, MouseMotionListener {

    //Default key binds
    public static int KEY_INTERACT = KeyEvent.getExtendedKeyCodeForChar('Z');
    public static int KEY_BACK = KeyEvent.getExtendedKeyCodeForChar('X');
    public static int KEY_UP = KeyEvent.VK_UP;
    public static int KEY_DOWN = KeyEvent.VK_DOWN;
    public static int KEY_LEFT = KeyEvent.VK_LEFT;
    public static int KEY_RIGHT = KeyEvent.VK_RIGHT;

    //Track mouse movement
    public Point mouseLocation = new Point(0,0);
    public Point oldLocation = new Point(0,0);
    public Point locationDiff = new Point(0,0);

    //MetaWindow resize stuff
    boolean[] resizeMode = new boolean[9];
    int resizableWindow = -1;

    public void update(){
    }

    /**
     * @return Location of mouse on screen
     */
    Point getMouseLocation(){
        return new Point(MouseInfo.getPointerInfo().getLocation().x, MouseInfo.getPointerInfo().getLocation().y);
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
            Bert.mBert.world.player.u = 1;
        }
        if (keyEvent.getKeyCode() == KEY_DOWN){
            Bert.mBert.world.player.d = 1;

        }
        if (keyEvent.getKeyCode() == KEY_LEFT){
            Bert.mBert.world.player.l = 1;

        }
        if (keyEvent.getKeyCode() == KEY_RIGHT){
            Bert.mBert.world.player.r = 1;

        }
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
        if(Bert.mBert.player!=null) {

            //Interactions with room objects
            if (keyEvent.getKeyCode() == KEY_INTERACT) {
                if (Bert.mBert.world.currentRoom.getInteractiveObjectClosestToPlayer() != null) {
                    Bert.mBert.world.currentRoom.getInteractiveObjectClosestToPlayer().interact();
                }
            }
            if (keyEvent.getKeyCode() == KEY_BACK) {

            }
            if (keyEvent.getKeyCode() == KEY_UP) {
                Bert.mBert.world.player.u = 0;

            }
            if (keyEvent.getKeyCode() == KEY_DOWN) {
                Bert.mBert.world.player.d = 0;

            }
            if (keyEvent.getKeyCode() == KEY_LEFT) {
                Bert.mBert.world.player.l = 0;

            }
            if (keyEvent.getKeyCode() == KEY_RIGHT) {
                Bert.mBert.world.player.r = 0;
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {

    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        resizeMode = new boolean[9];
        resizableWindow = -1;
        switch (mouseEvent.getButton()) {
            case MouseEvent.BUTTON1:
                for (int i = 0; i < Bert.mBert.interfaces.size(); i++) {

                    //Check for button presses inside of interfaces
                    ArrayList<MButton> buttons = Bert.mBert.interfaces.get(i).buttons;
                    for (int j = 0; j < buttons.size(); j++) {
                        MButton b = buttons.get(j);
                        if (b.containsMouse() && b.owner.usable) {
                            b.performAction();
                        }
                    }

                    //If there are windows inside of this interface
                    if (((PCInterface) Bert.mBert.interfaces.get(i)).windows != null) {
                        ArrayList<MetaWindow> windows = ((PCInterface) Bert.mBert.interfaces.get(i)).windows;
                        for (int j = 0; j < windows.size(); j++) {
                            MetaWindow w = windows.get(j);

                            //Determine if this window should be interacted with
                            boolean doaction = true;
                            for (int k = windows.size() - 1; k >= 0; k--) {
                                if (windows.get(k).contains(getMouseLocation()) && k > j) {
                                    doaction = false;
                                    break;
                                }
                            }

                            //If this window should be interacted with
                            if (doaction) {

                                //Focus window
                                if(windows.get(j).bounds.contains(getMouseLocation().x-windows.get(j).owner.getInsets().left-windows.get(j).owner.getX(),getMouseLocation().y-windows.get(j).owner.getInsets().top-windows.get(j).owner.getY())) {
                                    if (j != windows.size() - 1) {
                                        windows.remove(j);
                                        windows.add(w);
                                    }
                                }

                                //Interact with buttons within this window
                                if (w.windowButtons != null) {
                                    for (int m = 0; m < w.windowButtons.size(); m++) {
                                        WButton b = w.windowButtons.get(m);
                                        if (b.containsMouse() && b.owner.usable) {
                                            b.performAction();
                                            return;
                                        }
                                    }
                                }

                                //Handle edge hovering
                                if (w != null && w.edgeHovers != null) {
                                    if ((w.edgeHovers[0] && w.edgeHovers[2])&&w.resizable) {
                                        resizableWindow = w.id;
                                        resizeMode[0] = true;
                                        return;
                                    } else if ((w.edgeHovers[0] && w.edgeHovers[3])&&w.resizable) {
                                        resizableWindow = w.id;
                                        resizeMode[1] = true;
                                        return;
                                    } else if ((w.edgeHovers[1] && w.edgeHovers[2])&&w.resizable) {
                                        resizableWindow = w.id;
                                        resizeMode[2] = true;
                                        return;
                                    } else if ((w.edgeHovers[1] && w.edgeHovers[3])&&w.resizable) {
                                        resizableWindow = w.id;
                                        resizeMode[3] = true;
                                        return;
                                    } else if (w.edgeHovers[0]&&w.resizable) {
                                        resizableWindow = w.id;
                                        resizeMode[4] = true;
                                        return;
                                    } else if (w.edgeHovers[1]&&w.resizable) {
                                        resizableWindow = w.id;
                                        resizeMode[5] = true;
                                        return;
                                    } else if (w.edgeHovers[2]&&w.resizable) {
                                        resizableWindow = w.id;
                                        resizeMode[6] = true;
                                        return;
                                    } else if (w.edgeHovers[3]&&w.resizable) {
                                        resizableWindow = w.id;
                                        resizeMode[7] = true;
                                        return;
                                    } else if (w.movable) {
                                        resizableWindow = w.id;
                                        resizeMode[8] = true;
                                        return;
                                    }
                                }
                            }
                        }
                    }
                }
            break;
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

        //Track move distance
        oldLocation = mouseLocation;
        Point newLocation = getMouseLocation();
        locationDiff.setLocation(newLocation.x-oldLocation.x,newLocation.y-oldLocation.y);
        mouseLocation.setLocation(getMouseLocation());

        for (int i = 0; i < Bert.mBert.interfaces.size(); i++) {

            //Handle the dragging and resizing of windows
            if (((PCInterface) Bert.mBert.interfaces.get(i)).windows != null) {
                ArrayList<MetaWindow> windows = ((PCInterface) Bert.mBert.interfaces.get(i)).windows;
                for (int j = 0; j < windows.size(); j++) {
                    MetaWindow w = windows.get(j);
                    if(w!=null&&w.edgeHovers!=null) {
                        if (resizeMode[0]&&resizableWindow==w.id) {
                            //Top-Left
                            w.bounds.setBounds(w.bounds.x + locationDiff.x, w.bounds.y + locationDiff.y, w.bounds.width - locationDiff.x, w.bounds.height - locationDiff.y);
                        } else if (resizeMode[1]&&resizableWindow==w.id) {
                            //Bottom-Left
                            w.bounds.setBounds(w.bounds.x+ locationDiff.x, w.bounds.y, w.bounds.width - locationDiff.x, w.bounds.height + locationDiff.y);
                        } else if (resizeMode[2]&&resizableWindow==w.id) {
                            //Top-Right
                            w.bounds.setBounds(w.bounds.x, w.bounds.y + locationDiff.y, w.bounds.width + locationDiff.x, w.bounds.height - locationDiff.y);
                        } else if (resizeMode[3]&&resizableWindow==w.id) {
                            //Bottom-Right
                            w.bounds.setBounds(w.bounds.x , w.bounds.y, w.bounds.width + locationDiff.x, w.bounds.height + locationDiff.y);
                        } else if (resizeMode[4]&&resizableWindow==w.id) {
                            //Left
                            w.bounds.setBounds(w.bounds.x+locationDiff.x, w.bounds.y, w.bounds.width - locationDiff.x, w.bounds.height);
                        } else if (resizeMode[5]&&resizableWindow==w.id) {
                            //Right
                            w.bounds.setBounds(w.bounds.x, w.bounds.y, w.bounds.width + locationDiff.x, w.bounds.height);
                        } else if (resizeMode[6]&&resizableWindow==w.id) {
                            //Top
                            w.bounds.setBounds(w.bounds.x, w.bounds.y + locationDiff.y, w.bounds.width, w.bounds.height - locationDiff.y);
                        } else if (resizeMode[7]&&resizableWindow==w.id) {
                            //Bottom
                            w.bounds.setBounds(w.bounds.x, w.bounds.y, w.bounds.width, w.bounds.height + locationDiff.y);
                        } else if (resizeMode[8]&&resizableWindow==w.id) {
                            //Moving
                            if(w.owner.getBounds().contains(getMouseLocation().x,getMouseLocation().y))w.bounds.setLocation(w.bounds.x + locationDiff.x, w.bounds.y + locationDiff.y);
                            else resizableWindow = -1;
                        }
                    }
                }
            }
        }
    }

    @Override
    public void mouseMoved(MouseEvent mouseEvent) {
        mouseLocation.setLocation(getMouseLocation());
    }
}
