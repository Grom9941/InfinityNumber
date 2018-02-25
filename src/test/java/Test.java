import static org.junit.Assert.*;

public class Test {

    @org.junit.Test
    public void plus(){
        Main number1 = new Main("222");
        Main number2 = new Main("3");
        assertEquals(new Main("225"),number1.plus(number2));
    }
}
