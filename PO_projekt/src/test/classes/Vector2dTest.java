package classes;

import org.junit.jupiter.api.Test;

import static org.junit.Assert.*;

public class Vector2dTest {

    @Test public void equalsTest() {
        Vector2d position1 = new Vector2d(1,2);
        Vector2d position2 = new Vector2d(0,-5);
        Vector2d position3 = new Vector2d(1,2);
        String one = "a string";
        assertFalse(position1.equals(position2));
        assertTrue(position1.equals(position3));
        assertFalse(position1.equals(one));
    }

    @Test public void toStringTest(){
        Vector2d position1 = new Vector2d(1,2);
        Vector2d position2 = new Vector2d(-7,-22);
        Vector2d position3= new Vector2d(89,-16);
        assertEquals("(1,2)",position1.toString());
        assertEquals("(-7,-22)",position2.toString());
        assertEquals("(89,-16)",position3.toString());
    }

    @Test public void precedestest(){
        Vector2d position1 = new Vector2d(1,2);
        assertTrue(position1.precedes(position1));
        Vector2d position2 = new Vector2d(3,5);
        assertTrue(position1.precedes(position2));
        Vector2d position3 = new Vector2d(1,10);
        assertTrue(position1.precedes(position3));
        Vector2d position4 = new Vector2d(-2,-10);
        assertFalse(position1.precedes(position4));
        Vector2d position5 = new Vector2d(4,0);
        assertFalse(position1.precedes(position5));
        Vector2d position6 = new Vector2d(0,5);
        assertFalse(position1.precedes(position6));
    }

    @Test public void followsTest(){
        Vector2d position1 = new Vector2d(1,2);
        assertTrue(position1.follows(position1));
        Vector2d position2 = new Vector2d(0,-5);
        assertTrue(position1.follows(position2));
        Vector2d position3 = new Vector2d(1,0);
        assertTrue(position1.follows(position3));
        Vector2d position4 = new Vector2d(3,5);
        assertFalse(position1.follows(position4));
        Vector2d position5 = new Vector2d(3,2);
        assertFalse(position1.follows(position5));
    }

    @Test public void upperRightTest(){
        Vector2d position1 = new Vector2d(1,2);
        Vector2d position2 = new Vector2d(0,-5);
        assertEquals(new Vector2d(1,2),position1.upperRight(position2));
        Vector2d position3 = new Vector2d(4,8);
        Vector2d position4 = new Vector2d(2,12);
        assertEquals(new Vector2d(4,12),position3.upperRight(position4));
    }

    @Test public void lowerLeftTest(){
        Vector2d position1 = new Vector2d(1,2);
        Vector2d position2 = new Vector2d(0,-5);
        assertEquals(new Vector2d(0,-5),position1.lowerLeft(position2));
        Vector2d position3 = new Vector2d(4,8);
        Vector2d position4 = new Vector2d(2,12);
        assertEquals(new Vector2d(2,8),position3.lowerLeft(position4));
    }

    @Test public void addTest() {
        Vector2d position1 = new Vector2d(1,2);
        Vector2d position2 = new Vector2d(0,-5);
        Vector2d position3 = new Vector2d(-12,-4);
        assertEquals(new Vector2d(1,-3),position1.add(position2));
        assertEquals(new Vector2d(-11,-2),position1.add(position3));
        assertEquals(new Vector2d(-12,-9),position2.add(position3));
    }

    @Test public void subtractTest() {
        Vector2d position1 = new Vector2d(1,2);
        Vector2d position2 = new Vector2d(0,-5);
        Vector2d position3 = new Vector2d(-12,-4);
        assertEquals(new Vector2d(1,7),position1.subtract(position2));
        assertEquals(new Vector2d(13,6),position1.subtract(position3));
        assertEquals(new Vector2d(12,-1),position2.subtract(position3));
    }

    @Test public void oppositeTest() {
        Vector2d position1 = new Vector2d(1,2);
        Vector2d position2 = new Vector2d(0,-5);
        Vector2d position3 = new Vector2d(-12,-4);
        assertEquals(new Vector2d(-1,-2),position1.opposite());
        assertEquals(new Vector2d(0,5),position2.opposite());
        assertEquals(new Vector2d(12,4),position3.opposite());
    }

    @Test public void hashCodeTest(){
        Vector2d position1 = new Vector2d(1,2);
        Vector2d position2 = new Vector2d(1,2);
        Vector2d position3 = new Vector2d(-12,-4);
        Vector2d position4 = new Vector2d(-12,-4);
        assertEquals(position1.hashCode(),position2.hashCode());
        assertEquals(position3.hashCode(),position4.hashCode());
    }
}
