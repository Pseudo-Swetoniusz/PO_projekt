package maps;

import classes.GeneticCode;
import classes.Vector2d;
import mapElements.Animal;
import mapElements.Plant;

import java.util.*;

public class WorldOfEvolution implements IPositionChangeObserver, IWorldMap {
    private final int width;
    private final int height;
    private final double jungleRatio;
    private final Vector2d lowerLeft;
    private final Vector2d upperRight;
    private final Vector2d jungleLowerLeft;
    private final Vector2d jungleUpperRight;
    private final int area;
    private final int jungleArea;
    private final int jungleWidth;
    private final int jungleHeight;
    private final int plantEnergy;
    private final int moveEnergy;
    private final int numberOfFirstAnimals;
    private final int startEnergy;

    private Map<Vector2d, List<Animal>> animalMap = new HashMap<>();
    private List<Animal> animalList = new ArrayList<>();
    private Map<Vector2d, Plant> plantMap = new HashMap<>();
    private List<Plant> plantList = new ArrayList<>();
    private List<Vector2d> positionList = new ArrayList<>();
    private Map<GeneticCode, Integer> genotypeTypes = new LinkedHashMap<>();

    private int numberOfJunglePlants = 0;

    public WorldOfEvolution(int width, int height, double jungleRatio, int plantEnergy, int moveEnergy, int numberOfFirstAnimals, int startEnergy) {
        this.width = width;
        this.height = height;
        this.jungleRatio = jungleRatio;
        this.area = this.width * this.height;
        this.lowerLeft = new Vector2d(0,0);
        this.upperRight = new Vector2d(this.width-1,this.height-1);
        this.plantEnergy = plantEnergy;
        this.moveEnergy = moveEnergy;
        this.numberOfFirstAnimals = numberOfFirstAnimals;
        this.startEnergy = startEnergy;

        double jungleField = (this.width*this.height*this.jungleRatio)/(1+this.jungleRatio);
        double jungleUnit = Math.sqrt(jungleField/(this.width*this.height));
        int jungleWidth = (int) (jungleUnit*this.width);
        int jungleHeight = (int) (jungleUnit*this.height);
        this.jungleWidth = jungleWidth;
        this.jungleHeight = jungleHeight;
        this.jungleArea = jungleWidth*jungleHeight;
        this.jungleLowerLeft = new Vector2d((this.width-jungleWidth)/2+1,(this.height-jungleHeight)/2+1);
        this.jungleUpperRight = new Vector2d(this.width-(this.width-jungleWidth)/2,this.height - (this.height-jungleHeight)/2);
        addFirstAnimals();
        addPlants();
    }

    public int getWidth() {return this.width;}
    public int getHeight() {return this.height;}
    public Vector2d getJungleLowerLeft() {return this.jungleLowerLeft;}
    public int getJungleWidth() {return this.jungleWidth;}
    public int getJungleHeight() {return this.jungleHeight;}
    public List<Vector2d> getPositionList() {return this.positionList;}
    public Animal getAnimalOnPosition(Vector2d position) {
        List<Animal> list = animalMap.get(position);
        if(list == null || (list != null && list.size() == 0)) {
            return null;
        }
        else return list.get(0);
    }
    public int getStartEnergy() {return this.startEnergy;}
    public List<Plant> getPlantList() {return this.plantList;}
    public int getAnimalNumber() { return animalList.size();}
    public int getPlantNumber() {return plantList.size();}
    public int getDominantGenotypeNumber() {return genotypeTypes.get(dominantGenotype());}
    public String getDominantGenotype() {
        GeneticCode genes = dominantGenotype();
        String geneString = genes.toString();
        return geneString;
    }
    public double getAverageEnergy() {
        int sum = 0;
        int numberOfLiveAnimals = 0;
        for(Animal animal: animalList) {
            if(!animal.died()){
                sum = sum + animal.getEnergy();
                numberOfLiveAnimals++;
            }
        }
        if(numberOfLiveAnimals > 0) {
            return (double) sum/numberOfLiveAnimals;}
        else
            return 0.0;
    }
    public double getAverageLifeSpan() {
        int sum = 0;
        int numberOfDeadAnimals = 0;
        for(Animal animal: animalList) {
            if(animal.died()){
                sum = sum + animal.getLifeSpan();
                numberOfDeadAnimals++;
            }
        }
        if(numberOfDeadAnimals > 0) {
            return (double) sum/numberOfDeadAnimals;
        }
        else {
            return 0.0;
        }
    }
    public double getAverageNumberOfChildren() {
        int sum = 0;
        for(Animal animal: animalList) {
            sum = sum + animal.getNumberOfChildren();
        }
        if(animalList.size()>0) {
            return (double) sum/animalList.size();
        }
        else {
            return 0.0;
        }
    }
    public List<Vector2d> getDominantAnimalsPositions() {
        List <Vector2d> dominantAnimalsPositions = new ArrayList<>();
        GeneticCode dominantGenotype = dominantGenotype();
        for(Animal animal: animalList){
            if(animal.getGenes().equals(dominantGenotype)){
                dominantAnimalsPositions.add(animal.getPosition());
            }
        }
        return dominantAnimalsPositions;
    }

    public boolean isInJungle(Vector2d position) {
        if(position.follows(jungleLowerLeft) && position.precedes(jungleUpperRight)){
            return true;
        }
        else return false;
    }

    public int numberOfAnimalsInJungle() {
        int sum = 0;
        for(Vector2d position: positionList) {
            if(isInJungle(position)) {
                sum++;
            }
        }
        return sum;
    }

    private void addToHash(Animal animal) {
        List<Animal> list = animalMap.get(animal.getPosition());
        if(list != null) {
            int i;
            for(i = 0; i< list.size(); i++) {
                if(list.get(i).getEnergy()<animal.getEnergy()){
                    break;
                }
            }
            list.add(i,animal);
        }
        else {
            list  = new ArrayList<>();
            list.add(animal);
            animalMap.put(animal.getPosition(),list);
        }
    }

    private void removeFromHash(Animal animal) {
        List<Animal> list = animalMap.get(animal.getPosition());
        list.remove(animal);
        if(list == null || list.size()==0) {
            positionList.remove(animal.getPosition());
        }
    }

    public void addAnimal(Animal animal) {
        if(animalMap.get(animal.getPosition())==null) {
            positionList.add(animal.getPosition());
        }
        addToHash(animal);
        animalList.add(animal);
        animal.addObserver(this);
        if(genotypeTypes.get(animal.getGenes()) == null) {
            genotypeTypes.put(animal.getGenes(),1);
        }
        else {
            int value = genotypeTypes.get(animal.getGenes());
            genotypeTypes.put(animal.getGenes(),value+1);
        }
    }

    public void addFirstAnimals() {
        for(int i =0; i< this.numberOfFirstAnimals; i++) {
            Animal animal = new Animal(startEnergy,this);
            addAnimal(animal);
        }
    }

    public void addPlants() {
        int numberOfAnimalsInJungle = numberOfAnimalsInJungle();
        if(this.jungleArea - numberOfJunglePlants - numberOfAnimalsInJungle > 0) {
            boolean added = false;
            Random rand = new Random();
            for(int i=0; i<jungleArea*2; i++) {
                int x = rand.nextInt(this.jungleWidth);
                x = x + this.jungleLowerLeft.x;
                int y = rand.nextInt(this.jungleHeight);
                y = y + this.jungleLowerLeft.y;
                Vector2d countedPosition = new Vector2d(x,y);
                if(!isOccupied(countedPosition)) {
                    Plant plant = new Plant(this.plantEnergy, countedPosition);
                    plantMap.put(countedPosition,plant);
                    plantList.add(plant);
                    numberOfJunglePlants ++;
                    added = true;
                    break;
                }
            }
            if(!added) {
                List<Vector2d> unoccupiedPositions = new ArrayList<>();
                for(int oX = jungleLowerLeft.x; oX<jungleUpperRight.x; oX++) {
                    for(int oY = jungleLowerLeft.y; oY<jungleUpperRight.x; oY++) {
                        Vector2d currentPosition = new Vector2d(oX,oY);
                        if(isInJungle(currentPosition)&&!isOccupied(currentPosition)) {
                            unoccupiedPositions.add(currentPosition);
                        }
                    }
                }
                if(unoccupiedPositions.size()>0){
                    int positionIndex = rand.nextInt(unoccupiedPositions.size());
                    Plant plant = new Plant(this.plantEnergy, unoccupiedPositions.get(positionIndex));
                    plantMap.put(unoccupiedPositions.get(positionIndex),plant);
                    plantList.add(plant);
                    numberOfJunglePlants ++;
                }
            }
        }
        if(this.area - this.jungleArea - plantList.size() + numberOfJunglePlants - positionList.size() + numberOfAnimalsInJungle > 0) {
            boolean added = false;
            Random rand = new Random();
            for(int i = 0; i<2*(area - jungleArea); i++){
                int partX = rand.nextInt(1);
                int partY = rand.nextInt(1);
                int x = rand.nextInt(this.jungleLowerLeft.x);
                int y = rand.nextInt(this.jungleLowerLeft.y);
                if (partX == 1) {x = x+ this.jungleUpperRight.x;}
                if (partY == 1) {y = y+this.jungleUpperRight.y;}
                Vector2d countedPosition = new Vector2d(x,y);
                if(!isOccupied(countedPosition)) {
                    Plant plant = new Plant(this.plantEnergy, countedPosition);
                    plantMap.put(countedPosition,plant);
                    plantList.add(plant);
                    added = true;
                    break;
                }
            }
            if(!added) {
                List<Vector2d> unoccupiedPositions = new ArrayList<>();
                for(int oX = 0; oX<this.width; oX++) {
                    for(int oY = 0; oY<this.height; oY++) {
                        Vector2d currentPosition = new Vector2d(oX,oY);
                        if(!isInJungle(currentPosition)&&!isOccupied(currentPosition)) {
                            unoccupiedPositions.add(currentPosition);
                        }
                    }
                }
                if(unoccupiedPositions.size() > 0) {
                    int positionIndex = rand.nextInt(unoccupiedPositions.size());
                    Plant plant = new Plant(this.plantEnergy, unoccupiedPositions.get(positionIndex));
                    plantMap.put(unoccupiedPositions.get(positionIndex),plant);
                    plantList.add(plant);
                }
            }
        }

    }

    public void positionChanged(Vector2d oldPosition, Animal animal){
        List<Animal> list = animalMap.get(oldPosition);
        list.remove(animal);
        if(list == null || (list != null && list.size() == 0)) {
            positionList.remove(oldPosition);
        }
        if((animalMap.get(animal.getPosition()) != null && animalMap.get(animal.getPosition()).size() == 0) || animalMap.get(animal.getPosition()) == null) {
            positionList.add(animal.getPosition());
        }
        addToHash(animal);

    }

    public void run() {
        for(Animal animal: animalList) {
            if(!animal.died()&&animal.getEnergy()>this.moveEnergy) {animal.move(animal.getRandomGene());}
        }
    }

    public void eating() {
        for(Vector2d position: positionList) {
            if(isOccupiedByPlant(position)) {
                List<Animal> eatingList = new ArrayList<>();
                for(Animal animal: animalMap.get(position)) {
                    if(animal.getEnergy() < animalMap.get(position).get(0).getEnergy()) {
                    break;
                    }
                    eatingList.add(animal);
                }
                for(Animal animal: eatingList) {
                    animal.eat(plantEnergy/eatingList.size());
                }
                Plant plant = plantMap.get(position);
                plantList.remove(plant);
                plantMap.remove(position);
                if(isInJungle(position)) {
                    numberOfJunglePlants --;
                }
            }
        }
    }

    public void removeDead() {
        List<Animal> toRemove = new ArrayList<>();
        for(Animal animal: animalList) {
            if(animal.died()){
                toRemove.add(animal);
            }
        }
        for(Animal animal: toRemove) {
            removeFromHash(animal);
            int value = genotypeTypes.get(animal.getGenes());
            genotypeTypes.put(animal.getGenes(),value-1);
            animalList.remove(animal);
        }
    }

    public void reproduceAnimals() {
        List<Animal> childrenToAdd = new ArrayList<>();
        for(Vector2d position: positionList) {
            List<Animal> list = animalMap.get(position);
            if(list.size()>=2 && list.get(0).getEnergy()>= this.moveEnergy*4/3 && list.get(1).getEnergy() >= this.moveEnergy*4/3) {
                Animal child = list.get(0).reproduce(list.get(1));
                if(child != null) {
                    childrenToAdd.add(child);
                    list.get(0).changeNumberOfChildren();
                    list.get(1).changeNumberOfChildren();
                }
            }
        }
        for(Animal child: childrenToAdd){
            addAnimal(child);
        }
    }

    public void changeEnergy() {
        for(Animal animal: animalList) {
            animal.changeEnergy(moveEnergy);
        }
    }

    public boolean isOccupiedByAnimal(Vector2d position) {
        if(animalMap.get(position) != null && animalMap.get(position).size() > 0) {
            return true;
        }
        else return false;
    }

    public boolean isOccupiedByPlant(Vector2d position) {
        if(plantMap.get(position) != null) {
            return true;
        }
        else return false;
    }

    public boolean isOccupied (Vector2d position) {
        if(isOccupiedByAnimal(position) || isOccupiedByPlant(position)) {
            return true;
        }
        else return false;
    }

    public Object objectAt(Vector2d position) {
        if(isOccupiedByAnimal(position)) {
            return animalMap.get(position).get(0);
        }
        else if(isOccupiedByPlant(position)) {
            return plantMap.get(position);
        }
        else {return null;}
    }

    public GeneticCode dominantGenotype() {
        int max = 0;
        GeneticCode genes = new GeneticCode();
        for(Map.Entry<GeneticCode,Integer> mapElement: genotypeTypes.entrySet()) {
           int value = mapElement.getValue();
           if(value>max) {
               max = value;
               genes = mapElement.getKey();
           }
        }
        return genes;
    }

    public void writeAnimalList() {
        System.out.println("-- Animal List ------------------------------------");
        for(Animal animal: animalList) {
            System.out.println(animal.getDirection() + " " + animal.getPosition().toString() + " " + animal.getEnergy());
        }
        System.out.println("------------------------------------");
    }

    public void writePlantList() {
        System.out.println("-- Plant List ------------------------------------");
        for(Plant plant: plantList) {
            System.out.println("*" + plant.getPosition().toString());
        }
        System.out.println("------------------------------------");
    }

    public void writePositions() {
        System.out.println("-- Position List ------------------------------------");
        for(Vector2d position: positionList) {
            System.out.println(position.toString());
            List<Animal> list = animalMap.get(position);
            for(Animal animal: list) {
                System.out.println("    ->" + animal.getDirection() + " " + animal.getPosition().toString() + " " + animal.getEnergy());
            }
        }
        System.out.println("------------------------------------");
    }
}
