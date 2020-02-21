package world.rooms;

import player.Player;

import java.awt.*;
import java.util.ArrayList;

public class World extends ArrayList<Room> {

    public ArrayList<Rectangle> roomBounds = new ArrayList<Rectangle>();
    public Player player;
    public Room currentRoom;


    public void add(Player player){
        this.player = player;
    }

    @Override
    public boolean add(Room room){
        super.add(room);
        roomBounds.add(room.getWindowBounds());
        return false;
    }

    public void update(){
        for(int i = 0; i < this.size(); i++){
            Room r = this.get(i);
            roomBounds.set(i, r.getWindowBounds());
            r.update();
        }
        if(updateCurrentRoom()!=null) {
                //System.out.println(getCurrentRoom().title);
        }
        player.update(this);
    }

    public void repaint(){
        for(Room r: this){
            r.pane.repaint();
        }
    }

    public Room updateCurrentRoom() {
        for (int i = 0; i < this.size(); i++) {
            if(player!=null)
            if(roomBounds.get(i).contains(player.getBBox())){
                player.relPos = new Point(player.getLocation().x- this.get(i).getLocation().x,player.getLocation().y-this.get(i).getLocation().y);
                currentRoom = this.get(i);
                return this.get(i);
            }
        }
        return null;
    }
}
