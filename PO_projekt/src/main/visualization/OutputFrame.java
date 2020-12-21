package visualization;

import classes.Vector2d;
import mapElements.Animal;
import maps.IWorldMap;
import maps.WorldOfEvolution;
import parser.MapStructure;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OutputFrame  extends JFrame implements ActionListener {
    int typeOfFrame;
    IWorldMap FirstMap;
    IWorldMap SecondMap;
    WorldSimulation FirstSimulation;
    WorldSimulation SecondSimulation;
    MapPanel FirstMapPanel;
    MapPanel SecondMapPanel;
    JButton startSimulationFirst;
    JButton pauseSimulationFirst;
    JButton setNumberOfDaysFirst;
    JButton startSimulationSecond;
    JButton pauseSimulationSecond;
    JButton setNumberOfDaysSecond;
    JLabel numberOfAnimalsFirst;
    JLabel numberOfPlantFirst;
    JLabel numberOfDominantGenotypesFirst;
    JLabel averageEnergyFirst;
    JLabel averageLifeSpanFirst;
    JLabel averageChildNumberFirst;
    JLabel numberOfAnimalsSecond;
    JLabel numberOfPlantSecond;
    JLabel numberOfDominantGenotypesSecond;
    JLabel averageEnergySecond;
    JLabel averageLifeSpanSecond;
    JLabel averageChildNumberSecond;

    JLabel genotypeFirst;
    JLabel genotypeSecond;

    JButton dominantGenotypeFirst;
    JButton dominantGenotypeSecond;

    Animal chosenAnimal;
    int numberOfAnimalChildren;
    int numberOfAnimalDescendands;
    int dayOfDeath;

    int width;
    int height;

    public OutputFrame(int typeOfFrame, MapStructure structure) {
        super("World of Evolution");
        this.typeOfFrame = typeOfFrame;
        if(typeOfFrame == 1) {
            this.width = 720;
            this.height = 800;
        }
        else {
            this.width = 1430;
            this.height = 800;
        }
        setSize(new Dimension(this.width, this.height));
        setVisible(true);
        setLayout(new FlowLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        if(typeOfFrame == 1) {
            this.FirstMap = new WorldOfEvolution(structure.getWidth(),structure.getHeight(),structure.getJungleRatio(),structure.getPlantEnergy(), structure.getMoveEnergy(), structure.getNumberOfFirstAnimals(), structure.getStartEnergy());
            this.FirstMapPanel = new MapPanel(FirstMap, this,1);
            this.FirstSimulation = new WorldSimulation(FirstMap,FirstMapPanel, this);

            add(FirstMapPanel);
        }

        else {
            this.FirstMap = new WorldOfEvolution(structure.getWidth(),structure.getHeight(),structure.getJungleRatio(),structure.getPlantEnergy(), structure.getMoveEnergy(), structure.getNumberOfFirstAnimals(), structure.getStartEnergy());
            this.FirstMapPanel = new MapPanel(FirstMap, this,1);
            this.FirstSimulation = new WorldSimulation(FirstMap,FirstMapPanel, this);

            this.SecondMap = new WorldOfEvolution(structure.getWidth(),structure.getHeight(),structure.getJungleRatio(),structure.getPlantEnergy(), structure.getMoveEnergy(), structure.getNumberOfFirstAnimals(), structure.getStartEnergy());
            this.SecondMapPanel = new MapPanel(SecondMap, this,2);
            this.SecondSimulation = new WorldSimulation(SecondMap,SecondMapPanel, this);

            add(FirstMapPanel);
            add(SecondMapPanel);
        }

        JPanel buttonPanelFirst = new JPanel();
        buttonPanelFirst.setPreferredSize(new Dimension(320,40));
        buttonPanelFirst.setBorder(BorderFactory.createLineBorder(Color.black));

        startSimulationFirst = new JButton("start");
        pauseSimulationFirst = new JButton("pause");
        JTextField numberOfDaysFirst = new JTextField("number of days");
        setNumberOfDaysFirst = new JButton("submit");

        startSimulationFirst.addActionListener(this);
        pauseSimulationFirst.addActionListener(this);
        buttonPanelFirst.add(startSimulationFirst);
        buttonPanelFirst.add(pauseSimulationFirst);
        buttonPanelFirst.add(numberOfDaysFirst);
        buttonPanelFirst.add(setNumberOfDaysFirst);

        add(buttonPanelFirst);

        JPanel genotypePanelFirst = new JPanel();
        genotypePanelFirst.setPreferredSize(new Dimension(375,40));
        genotypePanelFirst.setBorder(BorderFactory.createLineBorder(Color.black));
        JLabel genotypeTitleFirst = new JLabel("genotype:");
        genotypeFirst = new JLabel ("pause and choose animal");

        genotypePanelFirst.add(genotypeTitleFirst);
        genotypePanelFirst.add(genotypeFirst);

        add(genotypePanelFirst);

        if(typeOfFrame == 2) {
            JPanel buttonPanelSecond = new JPanel();
            buttonPanelSecond.setBorder(BorderFactory.createLineBorder(Color.black));
            buttonPanelSecond.setPreferredSize(new Dimension(320,40));
            startSimulationSecond = new JButton("start");
            pauseSimulationSecond = new JButton("pause");
            JTextField numberOfDaysSecond = new JTextField("number of days");
            setNumberOfDaysSecond = new JButton("submit");
            startSimulationSecond.addActionListener(this);
            pauseSimulationSecond.addActionListener(this);
            buttonPanelSecond.add(startSimulationSecond);
            buttonPanelSecond.add(pauseSimulationSecond);
            buttonPanelSecond.add(numberOfDaysSecond);
            buttonPanelSecond.add(setNumberOfDaysSecond);
            add(buttonPanelSecond);

            JPanel genotypePanelSecond = new JPanel();
            genotypePanelSecond.setPreferredSize(new Dimension(375,40));
            genotypePanelSecond.setBorder(BorderFactory.createLineBorder(Color.black));
            JLabel genotypeTitleSecond = new JLabel("genotype:");
            genotypeSecond = new JLabel ("pause and choose animal");

            genotypePanelSecond.add(genotypeTitleSecond);
            genotypePanelSecond.add(genotypeSecond);

            add(genotypePanelSecond);
        }

        JPanel statisticsFirst = new JPanel();
        statisticsFirst.setLayout(new GridLayout(6, 1));
        statisticsFirst.setPreferredSize(new Dimension(700,150));
        statisticsFirst.setBorder(BorderFactory.createLineBorder(Color.black));
        numberOfAnimalsFirst = new JLabel("Number of animals: " + FirstMap.getAnimalNumber());
        numberOfPlantFirst = new JLabel("Number of plants: " + FirstMap.getPlantNumber());
        numberOfDominantGenotypesFirst = new JLabel("Number of dominant genotypes: " + FirstMap.getDominantGenotypeNumber() + System.lineSeparator() + FirstMap.getDominantGenotype());
        averageEnergyFirst = new JLabel("Average energy: " + FirstMap.getAverageEnergy());
        averageLifeSpanFirst = new JLabel("Average life span: " + FirstMap.getAverageLifeSpan());
        averageChildNumberFirst = new JLabel("Average number of children: " + FirstMap.getAverageNumberOfChildren());

        statisticsFirst.add(numberOfAnimalsFirst);
        statisticsFirst.add(numberOfPlantFirst);
        statisticsFirst.add(numberOfDominantGenotypesFirst);
        statisticsFirst.add(averageEnergyFirst);
        statisticsFirst.add(averageLifeSpanFirst);
        statisticsFirst.add(averageChildNumberFirst);

        add(statisticsFirst);

        if(typeOfFrame == 2) {
            JPanel statisticsSecond = new JPanel();
            statisticsSecond.setLayout(new GridLayout(6, 1));
            statisticsSecond.setPreferredSize(new Dimension(700,150));
            statisticsSecond.setBorder(BorderFactory.createLineBorder(Color.black));
            numberOfAnimalsSecond = new JLabel("Number of animals: " + SecondMap.getAnimalNumber());
            numberOfPlantSecond = new JLabel("Number of plants: " + SecondMap.getPlantNumber());
            numberOfDominantGenotypesSecond = new JLabel("Number of dominant genotypes: " + SecondMap.getDominantGenotypeNumber() + System.lineSeparator() + SecondMap.getDominantGenotype());
            averageEnergySecond = new JLabel("Average energy: " + SecondMap.getAverageEnergy());
            averageLifeSpanSecond = new JLabel("Average life span: " + SecondMap.getAverageLifeSpan());
            averageChildNumberSecond = new JLabel("Average number of children: " + SecondMap.getAverageNumberOfChildren());

            statisticsSecond.add(numberOfAnimalsSecond);
            statisticsSecond.add(numberOfPlantSecond);
            statisticsSecond.add(numberOfDominantGenotypesSecond);
            statisticsSecond.add(averageEnergySecond);
            statisticsSecond.add(averageLifeSpanSecond);
            statisticsSecond.add(averageChildNumberSecond);

            add(statisticsSecond);
        }

        dominantGenotypeFirst = new JButton("draw dominant genotype");
        dominantGenotypeFirst.setPreferredSize(new Dimension(700,30));
        dominantGenotypeFirst.addActionListener(this);
        add(dominantGenotypeFirst);

        if(typeOfFrame == 2) {
            dominantGenotypeSecond = new JButton("draw dominant genotype");
            dominantGenotypeSecond.setPreferredSize(new Dimension(700,30));
            dominantGenotypeSecond.addActionListener(this);
            add(dominantGenotypeSecond);
        }

    }

    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if(source == startSimulationFirst) {
            FirstSimulation.startTimer();
        }
        else if(source == pauseSimulationFirst){
            FirstSimulation.pauseTimer();
        }
        else if(source == startSimulationSecond) {
            SecondSimulation.startTimer();
        }
        else if(source == pauseSimulationSecond){
            SecondSimulation.pauseTimer();
        }
        else if(source == dominantGenotypeFirst){
            FirstMapPanel.setDominantGenotypeTrue();
            FirstMapPanel.repaint();
        }
        else if(source == dominantGenotypeSecond){
            SecondMapPanel.setDominantGenotypeTrue();
            SecondMapPanel.repaint();
        }
    }

    public void actualizeStatistics() {
        numberOfAnimalsFirst.setText("Number of animals: " + FirstMap.getAnimalNumber());
        numberOfPlantFirst.setText("Number of plants: " + FirstMap.getPlantNumber());
        numberOfDominantGenotypesFirst.setText("Dominant genotype: " + FirstMap.getDominantGenotype());
        averageEnergyFirst.setText("Average energy: " + FirstMap.getAverageEnergy());
        averageLifeSpanFirst.setText("Average life span: " + FirstMap.getAverageLifeSpan());
        averageChildNumberFirst.setText("Average number of children: " + FirstMap.getAverageNumberOfChildren());

        numberOfAnimalsSecond.setText("Number of animals: " + SecondMap.getAnimalNumber());
        numberOfPlantSecond.setText("Number of plants: " + SecondMap.getPlantNumber());
        numberOfDominantGenotypesSecond.setText("Dominant genotype: " + SecondMap.getDominantGenotype());
        averageEnergySecond.setText("Average energy: " + SecondMap.getAverageEnergy());
        averageLifeSpanSecond.setText("Average life span: " + SecondMap.getAverageLifeSpan());
        averageChildNumberSecond.setText("Average number of children: " + SecondMap.getAverageNumberOfChildren());

    }

    public void writeChosenGenom(Vector2d position, int mapIndex) {
        Animal animal;
        if(mapIndex == 1 && FirstSimulation.isPaused()) {
            animal = FirstMap.getAnimalOnPosition(position);
            if(animal != null) {
                genotypeFirst.setText(animal.getGenes().toString());
            }
        }
        else if (mapIndex == 2 && SecondSimulation.isPaused()){
            animal = SecondMap.getAnimalOnPosition(position);
            if(animal != null) {
                genotypeSecond.setText(animal.getGenes().toString());
            }
        }
    }

    public void setChosenAnimal(Vector2d position, int map) {
        if(map == 1) {
            this.chosenAnimal = FirstMap.getAnimalOnPosition(position);
        }
        else {
            this.chosenAnimal = SecondMap.getAnimalOnPosition(position);
        }
    }

    public void actualizeStatisticsOnAnimal() {
        numberOfAnimalChildren = chosenAnimal.getNumberOfChildren();
    }
}
