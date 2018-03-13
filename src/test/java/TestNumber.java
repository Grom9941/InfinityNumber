import org.junit.Test;

import static org.junit.Assert.*;

public class TestNumber {
    @Test
    public void plus(){
        Infinity number1 = new Infinity("222");
        Infinity number2 = new Infinity("3");
        assertEquals("225",number1.plus(number2));
    }

    @Test
    public  void minus(){
        Infinity number1 = new Infinity("200");
        Infinity number2 = new Infinity("77");
        assertEquals("123",number1.minus(number2));

        try
        {
            new Infinity("1").minus(new Infinity("222"));
            assertTrue(false);
        }
        catch(IllegalArgumentException e)
        {
            assertTrue(true);
        }
    }

    @Test
    public void max(){
        Infinity number1 = new Infinity("11");
        Infinity number2 = new Infinity("2");
        assertEquals("11",number1.max(number2));
    }

    @Test
    public void min(){
        Infinity number1 = new Infinity("222");
        Infinity number2 = new Infinity("122");
        assertEquals("122",number1.min(number2));
    }

    @Test
    public void equals(){
        Infinity number1 = new Infinity("222");
        Infinity number2 = new Infinity("222");
        assertTrue(number1.equals(number2));
    }

    @Test
    public void multiplication(){
        Infinity number1 = new Infinity("21");
        Infinity number2 = new Infinity("100");
        assertEquals("2100",number1.multiplication(number2));
    }

    @Test
    public void whole(){
        Infinity number1 = new Infinity("210");
        Infinity number2 = new Infinity("100");
        assertEquals(2,number1.whole(number2));
    }

    @Test
    public void residue(){
        Infinity number1 = new Infinity("100");
        Infinity number2 = new Infinity("100");
        assertEquals("0",number1.residue(number2));
    }
}
