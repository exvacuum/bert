package main;

public class Bertrand{

    static final int TPS = 60;
    static final int TSKIP = 1000 / TPS;
    static final int FRAMESKIPTOLERANCE = 5;

    static Bertrand bert;

    long startTime = System.currentTimeMillis();
    long nextTick = GetTickCount();
    int loops;
    float interpolation;

    public static void main(String[] args){
        bert = new Bertrand();
    }

    Player player;
    World world;

    Bertrand(){
        init();
        while(true){
            loops = 0;
            while( GetTickCount() > nextTick && loops < FRAMESKIPTOLERANCE) {
                step();

                nextTick += TSKIP;
                loops++;
            }

            interpolation = (float)( GetTickCount() + TSKIP - nextTick )
                    / (float)( TSKIP );

            draw(interpolation);
        }
    }

    void init(){
        player = new Player();
        world = new World();
    }

    void step(){
        player.step();
    }

    void draw(float interpolation){
        world.draw(interpolation);
        player.interpolation = interpolation;
        player.repaint();
    }

    long GetTickCount(){
        return System.currentTimeMillis()-startTime;
    }
}
