package visualization;

import maps.IWorldMap;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WorldSimulation implements ActionListener {
    IWorldMap map;
    MapPanel panel;
    Timer timer;
    OutputFrame frame;
    private final int delay = 500;
    private boolean paused;
    int numberOfDays;
    int numberOfDaysToObserve;
    boolean observe;

    public WorldSimulation(IWorldMap map, MapPanel panel, OutputFrame frame) {
        this.map = map;
        this.panel = panel;
        this.frame = frame;
        this.timer = new Timer(delay,this);
        this.paused = true;
        this.observe = false;
        timer.start();
        this.numberOfDays = 0;
    }

    public void startTimer() {
        paused = false;
        panel.setDominantGenotypeFalse();
    }

    public void pauseTimer() {
        paused = true;
    }

    public void simulate() {
        if(!paused){
            if(this.observe && numberOfDaysToObserve == 0) {
                pauseTimer();
            }
            map.removeDead();
            map.run();
            map.eating();
            map.reproduceAnimals();
            map.addPlants();
            map.changeEnergy();
            frame.actualizeStatistics();
            panel.repaint();
            this.numberOfDays ++;
            if(this.observe) {
                this.numberOfDaysToObserve --;
            }
        }
    }

    public void setNumberOfDaysToObserve(int number) {
        if(this.paused) {
            this.numberOfDaysToObserve = number;
            this.observe = true;
        }
    }

    public void actionPerformed(ActionEvent e) {
        this.simulate();
    }

    public boolean isPaused() {
        return this.paused;
    }
}
