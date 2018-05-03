import java.util.*;

import static java.lang.StrictMath.log10;

public class Infinity implements Comparable<Infinity> {

    private List<Byte> number = new ArrayList<>();

    /**
     * Преобразование в массив
     * @param string Бесконечно большое число
     */
    public Infinity(String string) {
        boolean zero = true;
        int i = 0;

        while (i < string.length()) {

                if (Character.getNumericValue(string.charAt(i)) != 0) {
                    zero = false;
                } else if (zero) {
                    i++;
                }
                //Строка ниже почему то не работает я так и не понял почему.
                // if (!Character.isDigit(string.charAt(i))) throw new IllegalArgumentException("Не правильная запись числа");

                if (!zero) {
                    if (Character.getNumericValue(string.charAt(i)) == 0) {

                        number.add((byte) Character.getNumericValue(string.charAt(i)));

                        i++;
                    } else if (i + 2 < string.length()) {

                        if ((Character.getNumericValue(string.charAt(i)) == 1 && Character.getNumericValue(string.charAt(i + 1)) == 2
                             && Character.getNumericValue(string.charAt(i + 2)) < 7) || (Character.getNumericValue(string.charAt(i)) == 1
                              && Character.getNumericValue(string.charAt(i + 1)) < 2)) {

                            number.add((byte) (Character.getNumericValue(string.charAt(i)) * 100 +
                             Character.getNumericValue(string.charAt(i + 1)) * 10 +
                              Character.getNumericValue(string.charAt(i + 2))));

                            i += 3;
                        } else {

                            number.add((byte) (Character.getNumericValue(string.charAt(i)) * 10 +
                             Character.getNumericValue(string.charAt(i + 1))));

                            i += 2;
                        }

                    } else if (i + 1 < string.length()) {

                        number.add((byte) (Character.getNumericValue(string.charAt(i)) * 10 +
                         Character.getNumericValue(string.charAt(i + 1))));

                        i += 2;

                    } else {

                        number.add((byte) Character.getNumericValue(string.charAt(i)));

                        i++;
                    }
                }
        }
    }

    /**
     * Преобразование в строку
     * @param array Массив который необходимо преобразовать
     * @return Строка полученная из начальных данных
     */
    private String main(List<Byte> array) {
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
     * Выделение цифры из байтового числа
     * @param number Байтовое число
     * @param rang Место в котором находимся в данный момент в байтовом числе
     * @return Лист с нужными данными(цифра, место следующее, переход на следующее байтовое число)
     */
    private List<Number> digit(Byte number, Byte rang){

        ArrayList<Number> result = new ArrayList<>();
        if (number > 99) {
            if (rang == 0) {

                result.add(number % 10);
                result.add(1);
                result.add(0);

            } else if (rang == 1) {

                result.add(number / 10 % 10);
                result.add(2);
                result.add(0);

            } else {

                result.add(number / 100);
                result.add(0);
                result.add(1);

            }
        } else if (number > 9) {
            if (rang == 0) {

                result.add(number % 10);
                result.add(1);
                result.add(0);

            } else {

                result.add(number / 10);
                result.add(0);
                result.add(1);

            }
        } else {

            result.add(number);
            result.add(0);
            result.add(1);

        }
        return result;

    }

    /**
     * Количество байтовых чисел для следующего раза
     * @param minusNumber Переход к другому байтовому числу
     * @param size Количество байтовых чисел
     * @return Оставшееся количество байтовых чисел
     */
    private Integer minusSize(Byte minusNumber,Integer size){
        if (minusNumber == 1) {
            return size-1;
        } else {
            return size;
        }
    }

    /**
     * Сложение
     * @param number1 Число на вход
     * @return Результат операции
     */
    public String plus(Infinity number1) {

        List<Number> digit1;
        int remains = 0;
        int value;
        int thisSize = this.number.size();
        int number1Size = number1.number.size();
        byte rangThis = 0;
        byte rangNumber1 = 0;
        byte minusThis;
        byte minusNumber1;
        StringBuilder string = new StringBuilder();


        while (thisSize > 0 && number1Size > 0) {

            digit1 = digit(this.number.get(thisSize - 1), rangThis);

            minusThis = Byte.parseByte(digit1.get(2).toString());
            rangThis = Byte.parseByte(digit1.get(1).toString());
            value = Integer.parseInt(digit1.get(0).toString());

            digit1 = digit(number1.number.get(number1Size - 1), rangNumber1);

            minusNumber1 = Byte.parseByte(digit1.get(2).toString());
            rangNumber1 = Byte.parseByte(digit1.get(1).toString());
            value += Integer.parseInt(digit1.get(0).toString());

            value += remains;
            remains = getRemains(value, string);

            thisSize = minusSize(minusThis,thisSize);
            number1Size = minusSize(minusNumber1,number1Size);

        }


        while (thisSize > 0) {

            digit1 = digit(this.number.get(thisSize - 1), rangThis);

            minusThis = Byte.parseByte(digit1.get(2).toString());
            rangThis = Byte.parseByte(digit1.get(1).toString());
            value = Integer.parseInt(digit1.get(0).toString());

            value += remains;
            remains = getRemains(value, string);

            thisSize = minusSize(minusThis,thisSize);
        }

        while (number1Size > 0) {

            digit1 = digit(number1.number.get(number1Size - 1), rangNumber1);

            minusNumber1 = Byte.parseByte(digit1.get(2).toString());
            rangNumber1 = Byte.parseByte(digit1.get(1).toString());
            value = Integer.parseInt(digit1.get(0).toString());

            value += remains;
            remains = getRemains(value, string);

            number1Size = minusSize(minusNumber1,number1Size);
        }

        if (remains == 1) {
            getRemains(1,string);
        }

        return string.reverse().toString();
    }

    /**
     * Находит остаток для переноса в следующий разряд
     * @param value  Значение цифры
     * @param string Строка для записи чисел при сложении
     * @return возвращает остаток
     */
    private int getRemains(int value, StringBuilder string) {
        int remains;

        if (value < 10) {

            string.append(value);
            remains = 0;
        } else {

            string.append(value % 10);
            remains = 1;
        }
        return remains;
    }

    /**
     * Вычитание
     * @param number1 Число на вход
     * @return Результат операции
     */
    public String minus(Infinity number1) {

        List<Number> digi;
        int thisSize = this.number.size();
        int number1Size = number1.number.size();
        int thisSize1;
        byte rangThis1;
        byte rangThis = 0;
        byte rangNumber1 = 0;
        byte minusThis;
        byte minusNumber1;
        StringBuilder string = new StringBuilder();
        int value;
        int value1;
        int compareNumber = new Infinity(this.toString()).compareTo(new Infinity(number1.toString()));

        if (compareNumber == 0) return "0";
        if (compareNumber < 0) throw new IllegalArgumentException("Отрицательное число");

        while (thisSize > 0 && number1Size > 0) {

            digi = digit(this.number.get(thisSize - 1), rangThis);

            minusThis = Byte.parseByte(digi.get(2).toString());
            rangThis = Byte.parseByte(digi.get(1).toString());
            value = Integer.parseInt(digi.get(0).toString());

            digi = digit(number1.number.get(number1Size - 1), rangNumber1);

            minusNumber1 = Byte.parseByte(digi.get(2).toString());
            rangNumber1 = Byte.parseByte(digi.get(1).toString());
            value1 = Integer.parseInt(digi.get(0).toString());

            if (value >= value1) {

                string.append(value - value1);
            } else {

                string.append(10 + value - value1);

                rangThis1 = rangThis;
                value = 0;
                int minusThis1 = 0;
                thisSize1 = thisSize;

                while (value == 0) {
                    int k =rangThis1;
                    digi = digit(this.number.get(thisSize1 - 1), rangThis1);

                    minusThis1 = Byte.parseByte(digi.get(2).toString());
                    rangThis1 = Byte.parseByte(digi.get(1).toString());
                    value = Integer.parseInt(digi.get(0).toString());




                    byte digit1 = (byte) (this.number.get(thisSize1 - 1) / 100);
                    byte digit2 = (byte) (this.number.get(thisSize1 - 1) / 10 % 10);
                    byte digit3 = (byte) (this.number.get(thisSize1 - 1) % 10);

                    if (k == 0) {
                        if (value == 0) {
                            digit3 = 9;
                        } else digit3 -= 1;
                    } else if (k == 1) {
                        if (value == 0) {
                            digit2 = 9;
                        } else digit2 -= 1;
                    } else if (value == 0) {
                        digit1 = 9;
                    } else digit1 -= 1;

                    this.number.remove(thisSize1 - 1);
                    this.number.add(thisSize1 - 1, (byte) (digit1 * 100 + digit2 * 10 + digit3));

                    if (minusThis1 == 1) {
                        thisSize1 -= 1;
                        minusThis1 = 0;
                        rangThis1 = 0;
                    }
                }
            }
            thisSize = minusSize(minusThis,thisSize);

            number1Size = minusSize(minusNumber1,number1Size);

        }

        while (thisSize > 0) {

            digi = digit(this.number.get(thisSize - 1), rangThis);

            minusThis = Byte.parseByte(digi.get(2).toString());
            rangThis = Byte.parseByte(digi.get(1).toString());
            value = Integer.parseInt(digi.get(0).toString());

            string.append(value);

            thisSize = minusSize(minusThis,thisSize);
        }

        return string.reverse().toString();
    }


    /**
     * Умножение
     * @param number1 Входное число
     * @return Рзультат операции
     */
    public String multiplication(Infinity number1) {

        List<Byte> intermediate = new ArrayList<>();
        List<Number> digi;

        for (int i = 0; i < this.number.size() * number1.number.size() * 9; i++) {
            intermediate.add((byte) -1);
        }

        int value;
        int value1;
        int placeNumber = 0;
        int place = 0;

        int thisSize = this.number.size();
        int number1Size = number1.number.size();
        byte rangThis = 0;
        byte rangNumber1 = 0;
        byte minusThis;
        byte minusNumber1;

        while (number1Size > 0) {

            place = placeNumber;

            digi = digit(number1.number.get(number1Size - 1), rangNumber1);

            minusNumber1 = Byte.parseByte(digi.get(2).toString());
            rangNumber1 = Byte.parseByte(digi.get(1).toString());
            value = Integer.parseInt(digi.get(0).toString());

            while (thisSize > 0) {

                digi = digit(this.number.get(thisSize - 1), rangThis);

                minusThis = Byte.parseByte(digi.get(2).toString());
                rangThis = Byte.parseByte(digi.get(1).toString());
                value1 = Integer.parseInt(digi.get(0).toString());

                thisSize = minusSize(minusThis,thisSize);

                if (intermediate.get(place) == -1) value1 = value * value1;
                else value1 = intermediate.get(place) + (value * value1);

                intermediate.remove(place);
                intermediate.add(place, (byte) (value1 % 10));
                place++;

                if (value1 > 10) {
                    intermediate.remove(place);
                    intermediate.add(place, (byte) (value1 /10));
                }
            }

            number1Size = minusSize(minusNumber1,number1Size);

            thisSize = this.number.size();
            rangThis = 0;
            placeNumber++;
        }


        List<Byte> result = new ArrayList<>();
        while (place > 0) {
            result.add(intermediate.get(place - 1));
            place--;
        }
        return main(result);
    }

    /**
     * Деление (целая часть)
     * @param number1 Входное число
     * @return Результат операции
     */
    public long whole(Infinity number1) {

        long whole = 0;
        int compareNumber = new Infinity(this.toString()).compareTo(new Infinity(number1.toString()));

        while (compareNumber > 0 || compareNumber == 0) {

            this.number = new Infinity(new Infinity(main(this.number)).minus(new Infinity(main(number1.number)))).number;
            whole++;

            compareNumber = new Infinity(this.toString()).compareTo(new Infinity(number1.toString()));
        }

        return whole;
    }

    /**
     * Деление (остаток)
     * @param number1 Входное число
     * @return Результат операции
     */
    public String residue(Infinity number1) {

        long whole = new Infinity(this.number.toString()).whole(new Infinity(number1.number.toString()));

        for (int i = 0; i < whole; i++) {
            this.number = new Infinity(new Infinity(main(this.number)).minus(new Infinity(main(number1.number)))).number;
        }

        if (this.number.size() == 0) return "0";
        return main(this.number);
    }

    /**
     * Максимальное
     * @param number1 Число на вход
     * @return Результат сравнения
     */
    public String max(Infinity number1) {

        int compareNumber = new Infinity(this.toString()).compareTo(new Infinity(number1.toString()));

        if (compareNumber > 0) return main(this.number);
        if (compareNumber < 0) return main(number1.number);

        return "Одинаковые числа";
    }

    /**
     * Минимальное
     * @param number1 Число на вход
     * @return Результат сравнения
     */
    public String min(Infinity number1) {

        int compareNumber = new Infinity(this.toString()).compareTo(new Infinity(number1.toString()));

        if (compareNumber > 0) return main(number1.number);
        if (compareNumber < 0) return main(this.number);

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
        StringBuilder This = new StringBuilder();
        StringBuilder Number1 = new StringBuilder();

        for (Byte digit : this.number) {
            if (digit == 0) {
                lengthThis += 1;
            } else {
                lengthThis += Math.log10(digit) + 1;
            }
            This.append(digit);
        }
        for (Byte digit : number1.number) {
            if (digit == 0) {
                lengthNumber1 += 1;
            } else {
                lengthNumber1 += log10(digit) + 1;
            }
            Number1.append(digit);
        }
        if (lengthThis > lengthNumber1) return 1;
        if (lengthThis < lengthNumber1) return -1;


        for (int i = 0; i < lengthThis; i++) {

            if (This.charAt(i) > Number1.charAt(i)) return 1;
            if (This.charAt(i) < Number1.charAt(i)) return -1;
        }

        return 0;
    }
}