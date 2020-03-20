package dev.exvaccum.bert.control.environment.light;

import dev.exvaccum.bert.world.objects.GameObject;

import java.awt.*;

public class Light {
    public int lightRadius = 50;
    public Color lightColor = new Color(255,255,255);
    public Point lightLocation = new Point(0,0);
    public GameObject owner;
    public boolean isOn;
    public float luminosity = 0.5f;

    public Light(GameObject owner){
        this.owner = owner;
    }

    public void update(){
        lightLocation = new Point(owner.getBounds().x,owner.getBounds().y);
    }
}
