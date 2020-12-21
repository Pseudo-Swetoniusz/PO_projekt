package maps;

import classes.MoveDirection;
import classes.Vector2d;
import mapElements.Animal;
import mapElements.Plant;

import java.util.List;


public interface IWorldMap {

    void addAnimal (Animal animal);

    void addFirstAnimals();

    void removeDead();

    void eating();

    void reproduceAnimals();

    void addPlants();

    void run();

    boolean isOccupiedByAnimal(Vector2d position);

    Object objectAt(Vector2d position);

    int getWidth();
    int getHeight();
    Vector2d getJungleLowerLeft();
    int getJungleWidth();
    int getJungleHeight();
    List<Vector2d> getPositionList();
    Animal getAnimalOnPosition(Vector2d position);
    int getStartEnergy();
    List<Plant> getPlantList();
    void changeEnergy();

    void writePlantList();

    int getAnimalNumber();

    int getPlantNumber();

    int getDominantGenotypeNumber();

    boolean isOccupied(Vector2d vector2d);

    String getDominantGenotype();

    double getAverageEnergy();

    double getAverageLifeSpan();

    double getAverageNumberOfChildren();

    List<Vector2d> getDominantAnimalsPositions();
}
