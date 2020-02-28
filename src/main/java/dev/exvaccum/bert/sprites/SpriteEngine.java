package dev.exvaccum.bert.sprites;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Timer;

public class SpriteEngine {

    private Timer timer;
    private int framesPerSecond;
    private Long cycleStartTime;
    private TimerHandler timerHandler;

    private double cycleProgress;
    private double speed;

    private List<ActionListener> listeners;

    public SpriteEngine(int fps, double speed) {
        framesPerSecond = fps;
        timerHandler = new TimerHandler();
        listeners = new ArrayList<>(25);
        this.speed = speed;
    }

    public int getFramesPerSecond() {
        return framesPerSecond;
    }

    public double getCycleProgress() {
        return cycleProgress;
    }

    protected void invaldiate() {
        cycleProgress = 0;
        cycleStartTime = null;
    }

    public void stop() {
        if (timer != null) {
            timer.stop();
        }
        invaldiate();
    }

    public void start() {
        stop();
        timer = new Timer(1000 / framesPerSecond, timerHandler);
        timer.start();
    }

    public void addActionListener(ActionListener actionListener) {
        listeners.add(actionListener);
    }

    public void removeActionListener(ActionListener actionListener) {
        listeners.remove(actionListener);
    }

    protected class TimerHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (cycleStartTime == null) {
                cycleStartTime = System.currentTimeMillis();
            }
            long diff = (long)((System.currentTimeMillis() - cycleStartTime) % (1000/speed));
            cycleProgress = diff / (1000.0/speed);
            ActionEvent ae = new ActionEvent(SpriteEngine.this, ActionEvent.ACTION_PERFORMED, e.getActionCommand());
            for (ActionListener listener : listeners) {
                listener.actionPerformed(ae);
            }
        }
    }
}
