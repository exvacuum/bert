package dev.exvaccum.bert.world.rooms;

import dev.exvaccum.bert.player.Player;

import java.awt.*;
import java.util.ArrayList;

public class World extends ArrayList<Room> {

    //List of rectangles to represent room boundaries
    public ArrayList<Rectangle> roomBounds = new ArrayList<Rectangle>();

    //Player
    public Player player;

    //Current Room
    public Room currentRoom;

    /**
     * Add a player object to the world
     * @param player to add
     */
    public void add(Player player){
        this.player = player;
    }

    /**
     * Add a room to the world
     * @param room to add
     * @return succes
     */
    @Override
    public boolean add(Room room){
        super.add(room);
        return roomBounds.add(room.getWindowBounds());
    }

    /**
     * Handle game logic
     */
    public void update(){

        //Update room bounds
        for(int i = 0; i < this.size(); i++){
            Room r = this.get(i);
            roomBounds.set(i, r.getWindowBounds());
            r.update();
        }

        //Update player
        player.update();
    }

    /**
     * Paint Room
     */
    public void repaint(){
        for(Room r: this){
            r.pane.repaint();
        }
    }

    /**
     * @return Room which player is currently inside of
     */
    public Room updateCurrentRoom() {
        for (int i = 0; i < this.size(); i++) {
            if(player!=null)
            if(roomBounds.get(i).contains(player.getBounds().getCenterX(), player.getBounds().getCenterY())){
                player.relPos = new Point(player.position.x- this.get(i).getLocation().x,player.position.y-this.get(i).getLocation().y);
                currentRoom = this.get(i);
                return this.get(i);
            }
        }
        return null;
    }
}
