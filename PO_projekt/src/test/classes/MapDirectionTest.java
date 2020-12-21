package classes;

import org.junit.jupiter.api.Test;
import static org.junit.Assert.assertEquals;

public class MapDirectionTest {

    @Test public void testnext() {
        MapDirection direction1 = MapDirection.NORTH;
        MapDirection direction2 = MapDirection.SOUTH;
        MapDirection direction3 = MapDirection.WEST;
        MapDirection direction4 = MapDirection.EAST;
        MapDirection direction5 = MapDirection.NORTHWEST;
        MapDirection direction6 = MapDirection.SOUTHWEST;
        MapDirection direction7 = MapDirection.NORTHEAST;
        MapDirection direction8 = MapDirection.SOUTHEAST;
        assertEquals(MapDirection.NORTHEAST, direction1.next());
        assertEquals(MapDirection.SOUTHWEST, direction2.next());
        assertEquals(MapDirection.NORTHWEST, direction3.next());
        assertEquals(MapDirection.SOUTHEAST, direction4.next());
        assertEquals(MapDirection.NORTH, direction5.next());
        assertEquals(MapDirection.WEST, direction6.next());
        assertEquals(MapDirection.EAST, direction7.next());
        assertEquals(MapDirection.SOUTH, direction8.next());
    }

    @Test public void testprevious(){
        MapDirection direction1 = MapDirection.NORTH;
        MapDirection direction2 = MapDirection.SOUTH;
        MapDirection direction3 = MapDirection.WEST;
        MapDirection direction4 = MapDirection.EAST;
        MapDirection direction5 = MapDirection.NORTHWEST;
        MapDirection direction6 = MapDirection.SOUTHWEST;
        MapDirection direction7 = MapDirection.NORTHEAST;
        MapDirection direction8 = MapDirection.SOUTHEAST;
        assertEquals(MapDirection.NORTHWEST, direction1.previous());
        assertEquals(MapDirection.SOUTHEAST, direction2.previous());
        assertEquals(MapDirection.SOUTHWEST, direction3.previous());
        assertEquals(MapDirection.NORTHEAST, direction4.previous());
        assertEquals(MapDirection.WEST, direction5.previous());
        assertEquals(MapDirection.SOUTH, direction6.previous());
        assertEquals(MapDirection.NORTH, direction7.previous());
        assertEquals(MapDirection.EAST, direction8.previous());
    }

}

