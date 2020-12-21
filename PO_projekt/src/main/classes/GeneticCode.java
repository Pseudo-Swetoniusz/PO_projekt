package classes;

import mapElements.Animal;

import java.util.Arrays;
import java.util.Objects;
import java.util.Random;

public class GeneticCode {
    private int[] code;
    private final int numberOfGenes = 32;
    private final int typesOfGenes = 8;

    public GeneticCode() {
        this.code = new int[numberOfGenes];
        generateCode();
        repairCode();
    }

    public GeneticCode(GeneticCode parent1, GeneticCode parent2) {
        Random rand = new Random();
        int firstSplit = rand.nextInt(numberOfGenes-2);
        int secondSplit = rand.nextInt(numberOfGenes - firstSplit-2) + firstSplit+1;
        int randomInterval = rand.nextInt(numberOfGenes)%3;
        this.code = new int[numberOfGenes];
        for(int i=0; i<=firstSplit; i++){
            if(randomInterval == 0) {
                code[i] = parent2.getCode()[i];
            }
            else code[i] = parent1.getCode()[i];
        }
        for(int i=firstSplit+1; i<=secondSplit;i++){
            if(randomInterval == 1) {
                code[i] = parent2.getCode()[i];
            }
            else code[i] = parent1.getCode()[i];
        }
        for(int i=secondSplit+1; i<numberOfGenes;i++){
            if(randomInterval == 2) {
                code[i] = parent2.getCode()[i];
            }
            else code[i] = parent1.getCode()[i];
        }
        Arrays.sort(code);
        repairCode();
    }

    public void generateCode(){
        Random rand = new Random();
        for(int i=0; i<numberOfGenes; i++){
            code[i] = rand.nextInt(typesOfGenes);
        }
        Arrays.sort(code);
    }

    public void repairCode(){
        int[] typeOfGen = new int[typesOfGenes];
        boolean fixed;
        int newGenPosition;
        for(int i=0; i<numberOfGenes; i++){
            typeOfGen[code[i]]++;
        }
        for(int i=0; i<typesOfGenes; i++){
            if(typeOfGen[i] == 0) {
                Random rand = new Random();
                fixed = false;
                while(!fixed){
                    newGenPosition = rand.nextInt(numberOfGenes);
                    if(typeOfGen[code[newGenPosition]]>=2){
                        typeOfGen[code[newGenPosition]]--;
                        code[newGenPosition] = i;
                        typeOfGen[i]++;
                        fixed = true;
                    }
                }
            }
        }
        Arrays.sort(code);
    }

    public int getRandomGene() {
        Random rand = new Random();
        int randomGene = rand.nextInt(numberOfGenes);
        return code[randomGene];
    }

    public int[] getCode() {
        return code;
    }

    public String toString() {
        String result = "";
        for (int i=0; i<numberOfGenes; i++){
            result = result + code[i] + " ";
        }
        return result;
    }

    public boolean equals(Object other){
        if (this == other)
            return true;
        if(!(other instanceof GeneticCode))
            return false;
        GeneticCode that = (GeneticCode) other;
        if (Arrays.equals(this.code,that.code)){
            return true;}
        else return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = hash*31 + Arrays.hashCode(this.code);
        return hash;
    }
}
