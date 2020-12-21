package maps;

import classes.Vector2d;
import mapElements.Animal;

public interface IPositionChangeObserver {

    void positionChanged(Vector2d oldPosition, Animal animal);

}
