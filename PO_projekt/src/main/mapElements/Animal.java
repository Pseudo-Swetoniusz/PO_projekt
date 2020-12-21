package mapElements;

import classes.MapDirection;
import classes.Vector2d;
import classes.GeneticCode;
import maps.IPositionChangeObserver;
import maps.IWorldMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Animal {
    private MapDirection direction;
    private Vector2d position;
    private final GeneticCode genes;
    private int energy;
    private final int numberOfGenes = 32;
    private final int typesOfGenes = 8;
    private final IWorldMap map;
    private final List<IPositionChangeObserver> observers = new ArrayList<>();
    private int lifeSpan;
    private int numberOfChildren;
    private int numberOfDescendands;
    Animal FirstParent;
    Animal SecondParent;

    // pierwsze zwierzęta, bez rodziców
    public Animal(int initialEnergy, IWorldMap map) {
        this.map = map;
        this.direction = randomDirection();
        this.position = randomPosition();
        this.genes = new GeneticCode();
        this.energy = initialEnergy;
        this.lifeSpan = 0;
        this.numberOfChildren = 0;
        this.FirstParent = null;
        this.SecondParent = null;
    }

    // następne zwierzęta, z rodzicami
    public Animal(Animal parent1, Animal parent2, IWorldMap map) {
        this.map = map;
        this.genes = new GeneticCode(parent1.getGenes(), parent2.getGenes());
        this.energy = parent1.getEnergy()/4 + parent2.getEnergy()/4;
        this.position = randomChildPosition(parent1.getPosition());
        this.direction = randomDirection();
        this.lifeSpan = 0;
        this.numberOfChildren = 0;
        this.FirstParent = parent1;
        this.SecondParent = parent2;
    }

    public GeneticCode getGenes() {
        return this.genes;
    }

    public int getRandomGene() {
        return this.genes.getRandomGene();
    }

    public int getEnergy() {
        return this.energy;
    }

    public Vector2d getPosition() {
        return this.position;
    }

    public String getDirection() { return this.direction.toString(); }

    public int getLifeSpan() {return this.lifeSpan;}

    public int getNumberOfChildren() {return this.numberOfChildren;}

    public void addDescendant() {

    }

    public boolean died() {
        if(energy<=0) {return true;}
        else return false;
    }

    public void eat(int food) {
        this.energy = this.energy + food;
    }

    public void changeEnergy(int moveEnergy) {
        this.energy = this.energy - moveEnergy;
        this.lifeSpan ++;
    }

    public void changeNumberOfChildren() {
        this.numberOfChildren++;
    }

    private Vector2d randomPosition() {
        Random rand = new Random();
        boolean found = false;
        while(!found) {
            int X = rand.nextInt(map.getWidth());
            int Y = rand.nextInt(map.getHeight());
            if(!map.isOccupied(new Vector2d(X,Y))){
                found = true;
                return new Vector2d(X,Y);
            }
        }
        return null;
    }

    private Vector2d randomChildPosition(Vector2d parentPosition){
        Random rand = new Random();
        List<Vector2d> neighbouringPositions = new ArrayList<>();
        for(int i=0; i<=2; i++) {
            for(int j=0; j<=2; j++) {
                int fieldX = i - 1;
                int fieldY = j - 1;
                Vector2d position = new Vector2d(parentPosition.x + fieldX, parentPosition.y + fieldY);
                position = positionInBounds(position);
                if(!this.map.isOccupied(position)) {
                    neighbouringPositions.add(position);
                }
            }
        }
        if(neighbouringPositions.size() > 0) {
            int positionIndex = rand.nextInt(neighbouringPositions.size());
            return neighbouringPositions.get(positionIndex);
        }
        else {
            int x = rand.nextInt(2) - 1;
            int y = rand.nextInt(2) - 1;
            Vector2d position = new Vector2d(parentPosition.x + x, parentPosition.y + y);
            position = positionInBounds(position);
            return position;
        }
    }

    private MapDirection randomDirection() {
        Random rand =  new Random();
        int directionIndex = rand.nextInt(8);
        switch(directionIndex) {
            case 0: return MapDirection.NORTH;
            case 1: return MapDirection.NORTHEAST;
            case 2: return MapDirection.EAST;
            case 3: return MapDirection.SOUTHEAST;
            case 4: return MapDirection.SOUTH;
            case 5: return MapDirection.SOUTHWEST;
            case 6: return MapDirection.WEST;
            case 7: return MapDirection.NORTHWEST;
            default: return null;
        }
    }

    public void rotation(int angle) {
        for (int i=0; i<angle; i++) {
            this.direction = this.direction.next();
        }
    }

    public Vector2d positionInBounds(Vector2d position) {
        int X = position.x;
        int Y = position.y;
        if(X >= map.getWidth()) {X = 0;}
        else if (X < 0) {X = map.getWidth()-1;}
        if(Y >= map.getHeight()) {Y = 0;}
        else if (Y < 0) {Y = map.getHeight()-1;}
        position = new Vector2d(X,Y);
        return position;
    }

    public void move(int angle) {
        rotation(angle);
        Vector2d move = this.direction.toUnitVector();
        Vector2d newPosition = this.position.add(move);
        newPosition = positionInBounds(newPosition);
        Vector2d oldPosition = this.position;
        this.position = newPosition;
        if(!newPosition.equals(oldPosition)) {
            positionChanged(oldPosition);
        }
    }



    public Animal reproduce(Animal parent) {
        Animal child = new Animal (this, parent, this.map);
        if(child.getPosition() != null) {
            return child;
        }
        else {
            return null;
        }
    }

    public void addObserver(IPositionChangeObserver observer){
        observers.add(observer);
    }

    public void removeObserver(IPositionChangeObserver observer){
        for(int i=0; i<observers.size(); i++){
            if(observers.get(i).getClass() == observer.getClass()){
                observers.remove(i);
            }
        }
    }

    public void write() {
        System.out.println(direction.toString() + " " + position.toString() + " " + energy);
        System.out.println(genes.toString());
    }

    public void positionChanged(Vector2d oldPosition){
        for(IPositionChangeObserver observer: observers){
            observer.positionChanged(oldPosition, this);
        }
    }
}
