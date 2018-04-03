import org.junit.Test;

import static org.junit.Assert.*;

public class TestNumber {
    @Test
    public void plus(){
        assertEquals("225",new Infinity("222").plus(new Infinity("3")));
        assertEquals("22915",new Infinity("22912").plus(new Infinity("3")));
        assertEquals("565",new Infinity("010").plus(new Infinity("555")));
        assertEquals("233",new Infinity("000").plus(new Infinity("233")));
        assertEquals("10554",new Infinity("9999").plus(new Infinity("555")));
        assertEquals("11109",new Infinity("9990").plus(new Infinity("1119")));
        }

    @Test
    public  void minus(){
        assertEquals("20100190",new Infinity("20100200").minus(new Infinity("10")));
//        assertEquals("1990",new Infinity("2000").minus(new Infinity("10")));
//        assertEquals("2656",new Infinity("3100").minus(new Infinity("444")));
   //     assertEquals("5291",new Infinity("9090").minus(new Infinity("3799")));

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
        assertEquals("222",new Infinity("222").max(new Infinity("122")));
        assertEquals("223",new Infinity("222").max(new Infinity("223")));
        assertEquals("999",new Infinity("100").max(new Infinity("999")));
        assertEquals("10",new Infinity("001").max(new Infinity("010")));
    }

    @Test
    public void min(){
        assertEquals("122",new Infinity("222").min(new Infinity("122")));
        assertEquals("222",new Infinity("222").min(new Infinity("223")));
        assertEquals("100",new Infinity("100").min(new Infinity("999")));
        assertEquals("1",new Infinity("001").min(new Infinity("010")));
    }

    @Test
    public void equals(){
        assertFalse(new Infinity("001").equals(new Infinity("010")));
        assertTrue(new Infinity("001").equals(new Infinity("000001")));
        assertTrue(new Infinity("0010").equals(new Infinity("010")));
        assertFalse(new Infinity("101").equals(new Infinity("01010")));
    }

    @Test
    public void multiplication(){
        assertEquals("2100",new Infinity("21").multiplication(new Infinity("100")));
        assertEquals("30",new Infinity("010").multiplication(new Infinity("3")));
        assertEquals("44",new Infinity("11").multiplication(new Infinity("04")));
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
