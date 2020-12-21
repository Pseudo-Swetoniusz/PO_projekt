package classes;

import org.junit.jupiter.api.Test;
import static org.junit.Assert.*;

public class GeneticCodeTest {

    @Test public void geneticCodeTest1() {
        GeneticCode code1 = new GeneticCode();
        int[] codeArray1 = code1.getCode();
        int[] codeCheck1 = new int[8];
        for(int i=0; i<32; i++){
            System.out.print(codeArray1[i]+" ");
            codeCheck1[codeArray1[i]]++;
        }
        System.out.println(" ");
        for(int i=0; i<8; i++){
            System.out.print(codeCheck1[i]+" ");
            assertFalse(codeCheck1[i] == 0);
        }
    }
    @Test public void geneticCodeTest2() {
        GeneticCode parent1 = new GeneticCode();
        System.out.println(parent1.toString());
        GeneticCode parent2 = new GeneticCode();
        System.out.println(parent2.toString());
        GeneticCode child1 = new GeneticCode(parent1,parent2);
        System.out.println(child1.toString());
    }
}
