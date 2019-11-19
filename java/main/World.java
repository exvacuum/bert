package main;

import windows.BedroomType;
import windows.KitchenType;
import bases.Room;
import java.util.ArrayList;

public class World {

    ArrayList<Room> rooms;

    World(){
        rooms = new ArrayList<Room>();
        rooms.add(new Room(new BedroomType()));
        rooms.add(new Room(new KitchenType()));
    }

    void draw(float interpolation){
        for(Room room : rooms){
            room.draw(interpolation);
        }
    }
}
