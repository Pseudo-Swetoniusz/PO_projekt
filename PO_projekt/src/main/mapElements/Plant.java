package mapElements;

import classes.Vector2d;

public class Plant {
    private int plantEnergy;
    private Vector2d position;

    public Plant(int plantEnergy, Vector2d position) {
        this.plantEnergy = plantEnergy;
        this.position = position;
    }

    public Vector2d getPosition() {
        return this.position;
    }

    public int getPlantEnergy() {
        return this.plantEnergy;
    }
}
