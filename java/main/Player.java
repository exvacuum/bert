package main;

import javax.swing.*;
import java.awt.*;

public class Player extends JFrame{

    public int u, d, l, r;
    int x, y, width, height, mv = 5;
    float interpolation;
    double hv, vv, mhv, mvv, spd, acc = 0.1;
    Renderer renderer;
    PlayerInput input;

    public Player(){
        setVisible(false);
        setUndecorated(true);
        setSize(100,100);
        setBackground(new Color(0,0,0,0));
        x = 0;
        y = 0;
        setLocation(x,y);
        renderer = new Renderer();
        add(renderer);
        setVisible(true);
        input = new PlayerInput(this);
    }

    void draw(Graphics2D g2){
        setLocation((int)(x+(hv*interpolation)),(int)(y+(vv*interpolation)));
        g2.fillRect(0,36,64,64);
    }

    public void step(){
        move();
    }

    void move() {

        int hin = r-l;
        int vin = d-u;
        int hdir = (int)Math.signum(hv);
        int vdir = (int)Math.signum(vv);
        double tv = Math.hypot(hv, vv);

        if(hin!=0) {

            if(Math.abs(hv+hin)<mv) {
                hv += hin*acc;
            }else {
                hv = hdir*mv;
            }

        }else{

            if(hv*hdir>(acc/3)){
                hv-=hdir*(acc/3);
            }else{
                hv = 0;
            }

        }

        if(vin!=0) {

            if(Math.abs(vv+vin)<mv) {
                vv += vin*acc;
            }else {
                vv = vdir*mv;
            }

        }else{

            if(vv*vdir>(acc/3)){
                vv-=vdir*(acc/3);
            }else{
                vv = 0;
            }

        }


        x+=hv;
        y+=vv;

    }

    class Renderer extends JPanel {

        Renderer(){
            setOpaque(false);
        }

        @Override
        public void paintComponent(Graphics g){
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            draw(g2);
        }
    }
}
