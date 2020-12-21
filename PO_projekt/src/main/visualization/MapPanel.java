package visualization;

import classes.Vector2d;
import mapElements.Animal;
import mapElements.Plant;
import maps.IWorldMap;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

public class MapPanel extends JPanel implements MouseListener {
    IWorldMap map;
    OutputFrame frame;
    int width;
    int height;
    int scaleX;
    int scaleY;
    int mapIndex;
    boolean dominantGenotype;

    public MapPanel(IWorldMap map, OutputFrame frame, int mapIndex) {
        this.map = map;
        this.width = 700;
        this.height = 450;
        this.mapIndex = mapIndex;
        this.frame = frame;
        this.scaleX = (int) (this.width/map.getWidth());
        this.scaleY = (int) (this.height/map.getHeight());
        setPreferredSize(new Dimension(this.width,this.height));
        addMouseListener(this);
        dominantGenotype = false;
    }

    public Vector2d countPosition(Vector2d position) {
        int x  = position.x*this.scaleX;
        int y = position.y*scaleY;
        return new Vector2d(x,y);
    }

    public void setDominantGenotypeTrue() {
        dominantGenotype = true;
    }

    public void setDominantGenotypeFalse() {
        dominantGenotype = false;
    }

    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        Graphics2D graphics2D = (Graphics2D) graphics;

        //narysuj obszar poza dżunglą
        Rectangle2D steppe = new Rectangle2D.Double(0,0,this.width,this.height);
        Color steppeColor = new Color(154,146,124);
        graphics2D.setColor(steppeColor);
        graphics2D.fill(steppe);

        //narysuj dżunglę
        Vector2d junglePlace = countPosition(map.getJungleLowerLeft());
        Rectangle2D jungle = new Rectangle2D.Double(junglePlace.x,junglePlace.y,map.getJungleWidth()*scaleX,map.getJungleHeight()*scaleY);
        Color jungleColor = new Color(88,90,81);
        graphics2D.setColor(jungleColor);
        graphics2D.fill(jungle);

        //wyrysuj trawę

        for(Plant plant: map.getPlantList()) {
            Rectangle2D plantShape = new Rectangle2D.Double(countPosition(plant.getPosition()).x,countPosition(plant.getPosition()).y,scaleX,scaleY);
            Color plantColor = new Color(40,100,20);
            graphics2D.setColor(plantColor);
            graphics2D.fill(plantShape);
        }

        //wyrysuj zwierzęta

        for(Vector2d position: map.getPositionList()) {
            Ellipse2D animalShape = new Ellipse2D.Double(countPosition(position).x, countPosition(position).y, scaleX, scaleY);
            Animal animal = map.getAnimalOnPosition(position);
            Color animalColor = new Color(110,50,10);
            if(animal.getEnergy() >= map.getStartEnergy()) {
                animalColor = new Color(120,27,12);
            }
            else if(animal.getEnergy() < map.getStartEnergy()/2) {
                animalColor = new Color(40,20,12);
            }
            else if(animal.getEnergy() <= 0) {
                animalColor = new Color(0,0,0);
            }
            graphics2D.setColor(animalColor);
            graphics2D.fill(animalShape);
        }

        //wyrysuj zwierzęta z dominującym genotypem
        if(dominantGenotype){
            for(Vector2d position: map.getDominantAnimalsPositions()){
                Ellipse2D animalShape = new Ellipse2D.Double(countPosition(position).x, countPosition(position).y, scaleX, scaleY);
                Color animalColor = new Color(150,140,10);
                graphics2D.setColor(animalColor);
                graphics2D.fill(animalShape);
            }
        }

    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        x = x/scaleX;
        y = y/scaleY;
        Vector2d position = new Vector2d(x,y);
        frame.writeChosenGenom(position,this.mapIndex);
        frame.setChosenAnimal(position, this.mapIndex);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }
}
