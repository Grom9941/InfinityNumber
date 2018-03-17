import java.util.*;

public class Infinity implements Comparable<Infinity> {

    private ArrayList<Byte> number = new ArrayList<>();

    /**
     * Преобразование в массив
     */
    Infinity(String string) {
        boolean zero = true;
        int i = 0;
        
        while (i < string.length()) {
            if (string.charAt(i) != '[' && string.charAt(i) != ']' && string.charAt(i) != ',' && string.charAt(i) != ' ') {
                if (Character.getNumericValue(string.charAt(i)) != 0) {
                    zero = false;
                } else {
                    i++;
                }
                // if (!Character.isDigit(string.charAt(i))) throw new IllegalArgumentException("Не правильная запись числа");

                if (!zero) {

                    if (i + 2 < string.length() && string.charAt(i+1) != '[' && string.charAt(i+1) != ',' && string.charAt(i+1) != ' ' &&  string.charAt(i+1) != ']' && string.charAt(i+2) != ' ' && string.charAt(i+2) != '[' && string.charAt(i+2) != ',' && string.charAt(i+2) != ']' ) {

                        if ((Character.getNumericValue(string.charAt(i)) == 1 && Character.getNumericValue(string.charAt(i + 1)) == 2 && Character.getNumericValue(string.charAt(i + 2)) < 7) || (Character.getNumericValue(string.charAt(i)) == 1 && Character.getNumericValue(string.charAt(i + 1)) == 1)) {

                            number.add((byte) (Character.getNumericValue(string.charAt(i)) * 100 + Character.getNumericValue(string.charAt(i + 1)) * 10 + Character.getNumericValue(string.charAt(i + 2))));
                            i += 3;
                        } else {

                            number.add((byte) (Character.getNumericValue(string.charAt(i)) * 10 + Character.getNumericValue(string.charAt(i + 1))));
                            i += 2;
                        }

                    } else if (i + 1 < string.length() && string.charAt(i+1) != '[' && string.charAt(i+1) != ',' && string.charAt(i+1) != ']' && string.charAt(i+1) != ' ' ) {

                        number.add((byte) (Character.getNumericValue(string.charAt(i)) * 10 + Character.getNumericValue(string.charAt(i + 1))));
                        i += 2;

                    } else {

                        number.add((byte) Character.getNumericValue(string.charAt(i)));
                        i++;

                    }
                }
            } else i++;
        }
    }
    
    /**
     * Преобразование в строку
     */
    private String Main(ArrayList<Byte> array) {
        boolean zero = true;
        StringBuilder string = new StringBuilder();

        for (Byte anArray : array) {

            if (anArray != 0 || !zero) {
                string.append(anArray);
                zero = false;
            }
        }

        return string.toString();
    }

    /**
     * Добавление в начало массива 0
     */
    private ArrayList<Byte> Add0(int value, ArrayList<Byte> array) {

        for (int i = 0; i < value; i++) {
            array.add(0, (byte) 0);
        }

        return array;
    }

    /**
     * Сложение
     */
    public String plus(Infinity number1) {

        if (this.number.size() > number1.number.size()) {
            number1.number = Add0(this.number.size() - number1.number.size(), number1.number);
        } else this.number = Add0(number1.number.size() - this.number.size(), this.number);

        int ost = 0;
        int value;

        for (int i = this.number.size() - 1; i >= 0; i--) {

            value = (this.number.get(i) + number1.number.get(i) + ost) % 10;
            ost = (this.number.get(i) + number1.number.get(i)) / 10;

            this.number.remove(i);
            this.number.add(i, (byte) value);
        }

        if (ost != 0) this.number.add(0, (byte) ost);
        return Main(this.number);
    }

    /**
     * Замена символа
     */
    private ArrayList<Byte> change(int i, ArrayList<Byte> array, int value) {

        array.remove(i);
        array.add(i, (byte) value);

        return array;
    }

    /**
     * Вычитание
     */
    public String minus(Infinity number1) {

        int value;
        int ost;
        int compareNumber = new Infinity(this.number.toString()).compareTo(new Infinity(number1.number.toString()));

        if (compareNumber == 0) return "0";
        if (compareNumber < 0) throw new IllegalArgumentException("Отрицательное число");


        number1.number = Add0(this.number.size() - number1.number.size(), number1.number);

        for (int i = this.number.size() - 1; i >= 0; i--) {

            if (this.number.get(i) >= number1.number.get(i)) {

                value = this.number.get(i) - number1.number.get(i);

                this.number = change(i, this.number, value);

            } else {

                value = 10 + this.number.get(i) - number1.number.get(i);
                int j = i - 1;
                ost = 9;
                this.number = change(i, this.number, value);

                while (this.number.get(j) == 0) {

                    value = ost;
                    this.number = change(j, this.number, value);
                    j--;
                }

                value = this.number.get(j) - 1;
                this.number = change(j, this.number, value);
            }
        }

        return Main(this.number);
    }


    /**
     * Умножение
     */
    public String multiplication(Infinity number1) {

        ArrayList<Byte> intermediate = new ArrayList<>();
        for (int i = 0; i < this.number.size() * number1.number.size() + 1; i++) {
            intermediate.add((byte) -1);
        }

        int value;
        int placeNumber = 0;
        int place = 0;
        int getResult;

        for (int i = number1.number.size() - 1; i >= 0; i--) {

            place = placeNumber;
            for (int j = this.number.size() - 1; j >= 0; j--) {

                if (intermediate.get(place) == -1) getResult = 0;
                else getResult = intermediate.get(place);

                value = getResult + this.number.get(j) * number1.number.get(i);
                intermediate = change(place, intermediate, value % 10);
                place++;

                if (value > 10) {
                    intermediate = change(place, intermediate, value / 10);
                }
            }
            placeNumber++;
        }
        ArrayList<Byte> result = new ArrayList<>();
        while (place > 0) {
            result.add(intermediate.get(place - 1));
            place--;
        }
        return Main(result);
    }

    /**
     * Деление (целая часть)
     */
    public long whole(Infinity number1) {

        long whole = 0;
        int compareNumber = new Infinity(this.number.toString()).compareTo(new Infinity(number1.number.toString()));

        while (compareNumber > 0 || compareNumber == 0) {
            this.number = new Infinity(new Infinity(Main(this.number)).minus(new Infinity(Main(number1.number)))).number;
            whole++;
            compareNumber = new Infinity(this.number.toString()).compareTo(new Infinity(number1.number.toString()));
        }

        return whole;
    }

    /**
     * Деление (остаток)
     */
    public String residue(Infinity number1) {

        long whole = new Infinity(this.number.toString()).whole(new Infinity(number1.number.toString()));

        for (int i = 0; i < whole; i++) {

            this.number = new Infinity(new Infinity(Main(this.number)).minus(new Infinity(Main(number1.number)))).number;
        }

        if (this.number.size() == 0) return "0";
        return Main(this.number);
    }

    /**
     * Максимальное
     */
    public String max(Infinity number1) {

        int compareNumber = new Infinity(this.number.toString()).compareTo(new Infinity(number1.number.toString()));

        if (compareNumber > 0) return Main(this.number);
        if (compareNumber < 0) return Main(number1.number);

        return "Одинаковые числа";
    }

    /**
     * Минимальное
     */
    public String min(Infinity number1) {

        int compareNumber = new Infinity(this.number.toString()).compareTo(new Infinity(number1.number.toString()));

        if (compareNumber > 0) return Main(number1.number);
        if (compareNumber < 0) return Main(this.number);

        return "Одинаковые числа";
    }


    @Override
    public boolean equals(Object object) {

        if (this == object) return true;

        if (object instanceof Infinity) {

            Infinity other = (Infinity) object;
            return this.number.equals(other.number);
        }

        return false;
    }

    @Override
    public int hashCode() {

        final int prime = 31;

        return number.hashCode() * prime;
    }

    @Override
    public String toString() {

        StringBuilder string = new StringBuilder();
        for (Byte digit : number) {

            string.append(digit);
        }

        return string.toString();
    }


    @Override
    public int compareTo(Infinity number1) {
        long lengthThis = 0;
        long lengthNumber1 = 0;
        for (Byte digit : this.number){
            lengthThis+=Math.log10(digit)+1;
        }
        for (Byte digit : this.number){
            lengthNumber1+=Math.log10(digit)+1;
        }
        if (lengthThis > lengthNumber1) return 1;
        if (lengthThis < lengthNumber1) return -1;


        for (int i = 0; i < lengthThis; i++) {


            if (this.number.get(i) > number1.number.get(i)) return 1;
            if (this.number.get(i) < number1.number.get(i)) return -1;
        }

        return 0;
    }
}