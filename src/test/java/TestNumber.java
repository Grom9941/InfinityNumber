import org.junit.Test;

import static org.junit.Assert.*;

public class TestNumber {
    @Test
    public void plus(){
        Main number1 = new Main("222");
        Main number2 = new Main("3");
        Main number3 = new Main("1");
        assertEquals("226",number1.plus(number2));
    }

    @Test
    public  void minus(){
        Main number1 = new Main("77");
        Main number2 = new Main("277");
        assertEquals("-200",number1.minus(number2));
    }

    @Test
    public void max(){
        Main number1 = new Main("11");
        Main number2 = new Main("2");
        assertEquals("11",number1.max(number2));
    }

    @Test
    public void min(){
        Main number1 = new Main("222");
        Main number2 = new Main("122");
        assertEquals("122",number1.min(number2));
    //    try {
    //        new Main("!99");
    //    } catch (IllegalArgumentException)
     //   assertEquals(IllegalArgumentException.class, () ->new Main("56789."));
    }

    @Test
    public void equals(){
        Main number1 = new Main("222");
        Main number2 = new Main("222");
        assertTrue(number1.equals(number2));
    }

    @Test
    public void multiplication(){
        Main number1 = new Main("21");
        Main number2 = new Main("100");
        assertEquals("2100",number1.multiplication(number2));
    }

    @Test
    public void whole(){
        Main number1 = new Main("210");
        Main number2 = new Main("100");
        assertEquals(2,number1.whole(number2));
    }

    @Test
    public void residue(){
        Main number1 = new Main("100");
        Main number2 = new Main("100");
        assertEquals("0",number1.residue(number2));
    }
}
