package parser;

public class MapStructure {
    private final int width;
    private final int height;
    private final int startEnergy;
    private final int moveEnergy;
    private final int plantEnergy;
    private final double jungleRatio;
    private final int numberOfFirstAnimals;

    public MapStructure(int width, int height, int startEnergy, int moveEnergy, int plantEnergy, double jungleRatio, int numberOfFirstAnimals) {
        this.width = width;
        this.height = height;
        this.startEnergy = startEnergy;
        this.moveEnergy = moveEnergy;
        this.plantEnergy = plantEnergy;
        this.jungleRatio = jungleRatio;
        this.numberOfFirstAnimals = numberOfFirstAnimals;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public int getStartEnergy() {
        return this.startEnergy;
    }

    public int getMoveEnergy() {
        return this.moveEnergy;
    }

    public int getPlantEnergy() {
        return this.plantEnergy;
    }

    public double getJungleRatio() {
        return this.jungleRatio;
    }

    public int getNumberOfFirstAnimals() {
        return this.numberOfFirstAnimals;
    }

    public void write() {
        System.out.println(this.width);
        System.out.println(this.height);
        System.out.println(this.startEnergy);
        System.out.println(this.moveEnergy);
        System.out.println(this.plantEnergy);
        System.out.println(this.jungleRatio);
        System.out.println(this.numberOfFirstAnimals);
    }
}
