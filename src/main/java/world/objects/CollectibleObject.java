package world.objects;

import world.rooms.Room;

public class CollectibleObject extends GameObject {
    public CollectibleObject(Room room) {
        super(room);
    }

    @Override
    public boolean isClose() {
        return false;
    }

    @Override
    public double getDistanceToPlayer() {
        return 0;
    }
}
