package control;

import bert.App;
import world.interfaces.PCInterface;
import world.interfaces.pc.MetaWindow;

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
    public Point oldLocation = new Point(0,0);
    public Point locationDiff = new Point(0,0);

    boolean[] resizeMode = new boolean[9];
    int resizableWindow = -1;

    public void update(){
    }

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
        resizeMode = new boolean[9];
        resizableWindow = -1;
        switch (mouseEvent.getButton()) {
            case MouseEvent.BUTTON1:
                //Check for button presses
                for (int i = 0; i < App.mApp.interfaces.size(); i++) {
                    ArrayList<MButton> buttons = App.mApp.interfaces.get(i).buttons;
                    for (int j = 0; j < buttons.size(); j++) {
                        MButton b = buttons.get(j);
                        if (b.containsMouse() && b.owner.usable) {
                            b.performAction();
                        }
                    }

                    if (((PCInterface) App.mApp.interfaces.get(i)).windows != null) {
                        ArrayList<MetaWindow> windows = ((PCInterface) App.mApp.interfaces.get(i)).windows;
                        for (int j = 0; j < windows.size(); j++) {
                            MetaWindow w = windows.get(j);

                            boolean doaction = true;
                            for (int k = windows.size() - 1; k >= 0; k--) {
                                if (windows.get(k).contains(getMouseLocation()) && k > j) {
                                    doaction = false;
                                    break;
                                }
                            }
                            if (doaction) {

                                if(windows.get(j).bounds.contains(getMouseLocation().x-windows.get(j).owner.getInsets().left-windows.get(j).owner.getX(),getMouseLocation().y-windows.get(j).owner.getInsets().top-windows.get(j).owner.getY())) {
                                    if (j != windows.size() - 1) {
                                        windows.remove(j);
                                        windows.add(w);
                                    }
                                }
                                if (w.windowButtons != null) {
                                    for (int m = 0; m < w.windowButtons.size(); m++) {
                                        WButton b = w.windowButtons.get(m);
                                        if (b.containsMouse() && b.owner.usable) {
                                            b.performAction();
                                            return;
                                        }
                                    }
                                }
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

        oldLocation = mouseLocation;
        Point newLocation = getMouseLocation();
        locationDiff.setLocation(newLocation.x-oldLocation.x,newLocation.y-oldLocation.y);
        mouseLocation.setLocation(getMouseLocation());

        for (int i = 0; i < App.mApp.interfaces.size(); i++) {
            if (((PCInterface) App.mApp.interfaces.get(i)).windows != null) {
                ArrayList<MetaWindow> windows = ((PCInterface) App.mApp.interfaces.get(i)).windows;
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
