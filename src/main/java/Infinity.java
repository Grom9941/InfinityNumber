import java.util.*;

import static java.lang.StrictMath.log10;

public class Infinity implements Comparable<Infinity> {

    private List<Byte> number = new ArrayList<>();

    /**
     * Преобразование в массив
     * @param string Бесконечно большое число
     */
    Infinity(String string) {
        boolean zero = true;
        int i = 0;

        while (i < string.length()) {
            if (string.charAt(i) != '[' && string.charAt(i) != ']' && string.charAt(i) != ',' && string.charAt(i) != ' ') {
                if (Character.getNumericValue(string.charAt(i)) != 0) {
                    zero = false;
                } else if (zero) {
                    i++;
                }
                // if (!Character.isDigit(string.charAt(i))) throw new IllegalArgumentException("Не правильная запись числа");

                if (!zero) {
                    if (Character.getNumericValue(string.charAt(i)) == 0) {

                        number.add((byte) Character.getNumericValue(string.charAt(i)));

                        i++;
                    } else if (i + 2 < string.length() && string.charAt(i + 1) != '[' && string.charAt(i + 1) != ',' &&
                            string.charAt(i + 1) != ' ' && string.charAt(i + 1) != ']' && string.charAt(i + 2) != ' ' &&
                             string.charAt(i + 2) != '[' && string.charAt(i + 2) != ',' && string.charAt(i + 2) != ']') {

                        if ((Character.getNumericValue(string.charAt(i)) == 1 && Character.getNumericValue(string.charAt(i + 1)) == 2
                             && Character.getNumericValue(string.charAt(i + 2)) < 7) || (Character.getNumericValue(string.charAt(i)) == 1
                              && Character.getNumericValue(string.charAt(i + 1)) == 1)) {

                            number.add((byte) (Character.getNumericValue(string.charAt(i)) * 100 +
                             Character.getNumericValue(string.charAt(i + 1)) * 10 +
                              Character.getNumericValue(string.charAt(i + 2))));

                            i += 3;
                        } else {

                            number.add((byte) (Character.getNumericValue(string.charAt(i)) * 10 +
                             Character.getNumericValue(string.charAt(i + 1))));

                            i += 2;
                        }

                    } else if (i + 1 < string.length() && string.charAt(i + 1) != '[' &&
                            string.charAt(i + 1) != ',' && string.charAt(i + 1) != ']' && string.charAt(i + 1) != ' ') {

                        number.add((byte) (Character.getNumericValue(string.charAt(i)) * 10 +
                         Character.getNumericValue(string.charAt(i + 1))));

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
     * Сложение
     * @param number1 Число на вход
     * @return Результат операции
     */
    public String plus(Infinity number1) {

        int remains = 0;
        int value = 0;
        int thisSize = this.number.size();
        int number1Size = number1.number.size();
        byte rangThis = 0;
        byte rangNumber1 = 0;
        byte minusThis = 0;
        byte minusNumber1 = 0;
        StringBuilder string = new StringBuilder();

        while (thisSize > 0 && number1Size > 0) {

            //Не смог вынести в отдельный метод так как зависит от двух переменных
            //Ниже алгоритм достает нужную цифру из массива первого числа
            if (this.number.get(thisSize - 1) > 99) {
                if (rangThis == 0) {

                    value = this.number.get(thisSize - 1) % 10;
                    rangThis = 1;

                } else if (rangThis == 1) {

                    value = this.number.get(thisSize - 1) / 10 % 10;
                    rangThis = 2;

                } else if (rangThis == 2) {

                    value = this.number.get(thisSize - 1) / 100;
                    rangThis = 0;
                    minusThis = 1;

                }
            } else if (this.number.get(thisSize - 1) > 9) {
                if (rangThis == 0) {

                    value = this.number.get(thisSize - 1) % 10;
                    rangThis = 1;

                } else if (rangThis == 1) {

                    value = this.number.get(thisSize - 1) / 10;
                    rangThis = 0;
                    minusThis = 1;

                }
            } else if (this.number.get(thisSize - 1) <= 9) {

                value = this.number.get(thisSize - 1);
                minusThis = 1;

            }

            //Ниже алгоритм достает нужную цифру из массива второго числа
            if (number1.number.get(number1Size - 1) > 99) {
                if (rangThis == 0) {

                    value += number1.number.get(number1Size - 1) % 10;
                    rangThis = 1;

                } else if (rangThis == 1) {

                    value += number1.number.get(number1Size - 1) / 10 % 10;
                    rangThis = 2;

                } else if (rangThis == 2) {

                    value += number1.number.get(number1Size - 1) / 100;
                    rangThis = 0;
                    minusNumber1 = 1;

                }
            } else if (number1.number.get(number1Size - 1) > 9) {
                if (rangThis == 0) {

                    value += number1.number.get(number1Size - 1) % 10;
                    rangThis = 1;

                } else if (rangThis == 1) {

                    value += number1.number.get(number1Size - 1) / 10;
                    rangThis = 0;
                    minusNumber1 = 1;

                }
            } else if (number1.number.get(number1Size - 1) <= 9) {

                value += number1.number.get(number1Size - 1);
                minusNumber1 = 1;

            }

            value += remains;
            remains = getRemains(value, string);

            if (minusThis == 1) {
                thisSize -= 1;
                minusThis = 0;
            }

            if (minusNumber1 == 1) {
                number1Size -= 1;
                minusNumber1 = 0;
            }
        }


        while (thisSize > 0) {

            //Ниже алгоритм достает нужную цифру из массива первого числа
            if (this.number.get(thisSize - 1) > 99) {
                if (rangThis == 0) {

                    value = this.number.get(thisSize - 1) % 10;
                    rangThis = 1;

                } else if (rangThis == 1) {

                    value = this.number.get(thisSize - 1) / 10 % 10;
                    rangThis = 2;

                } else if (rangThis == 2) {

                    value = this.number.get(thisSize - 1) / 100;
                    rangThis = 0;
                    minusThis = 1;

                }
            } else if (this.number.get(thisSize - 1) > 9) {
                if (rangThis == 0) {

                    value = this.number.get(thisSize - 1) % 10;
                    rangThis = 1;

                } else if (rangThis == 1) {

                    value = this.number.get(thisSize - 1) / 10;
                    rangThis = 0;
                    minusThis = 1;

                }
            } else if (this.number.get(thisSize - 1) <= 9) {

                value = this.number.get(thisSize - 1);
                minusThis = 1;

            }

            value += remains;
            remains = getRemains(value, string);

            if (minusThis == 1) {
                thisSize -= 1;
                minusThis = 0;
            }
        }

        while (number1Size > 0) {

            //Ниже алгоритм достает нужную цифру из массива второго числа
            if (number1.number.get(number1Size - 1) > 99) {
                if (rangNumber1 == 0) {

                    value += number1.number.get(number1Size - 1) % 10;
                    rangNumber1 = 1;

                } else if (rangNumber1 == 1) {

                    value += number1.number.get(number1Size - 1) / 10 % 10;
                    rangNumber1 = 2;

                } else if (rangNumber1 == 2) {

                    value += number1.number.get(number1Size - 1) / 100;
                    rangNumber1 = 0;
                    minusNumber1 = 1;

                }
            } else if (number1.number.get(number1Size - 1) > 9) {
                if (rangNumber1 == 0) {

                    value += number1.number.get(number1Size - 1) % 10;
                    rangNumber1 = 1;

                } else if (rangNumber1 == 1) {

                    value += number1.number.get(number1Size - 1) / 10;
                    rangNumber1 = 0;
                    minusNumber1 = 1;

                }
            } else if (number1.number.get(number1Size - 1) <= 9) {

                value += number1.number.get(number1Size - 1);
                minusNumber1 = 1;

            }

            value += remains;
            remains = getRemains(value, string);

            if (minusNumber1 == 1) {
                number1Size -= 1;
                minusNumber1 = 0;
            }
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


        int thisSize = this.number.size();
        int number1Size = number1.number.size();
        byte rangThis = 0;
        byte rangNumber1 = 0;
        byte minusThis = 0;
        byte minusNumber1 = 0;
        StringBuilder string = new StringBuilder();
        int value = 0;
        int value1 = 0;
        int compareNumber = new Infinity(this.number.toString()).compareTo(new Infinity(number1.number.toString()));

        if (compareNumber == 0) return "0";
        if (compareNumber < 0) throw new IllegalArgumentException("Отрицательное число");

        while (thisSize > 0 && number1Size > 0) {

            //Ниже алгоритм достает нужную цифру из массива первого числа
            if (this.number.get(thisSize - 1) > 99) {
                if (rangThis == 0) {

                    value = this.number.get(thisSize - 1) % 10;
                    rangThis = 1;

                } else if (rangThis == 1) {

                    value = this.number.get(thisSize - 1) / 10 % 10;
                    rangThis = 2;

                } else if (rangThis == 2) {

                    value = this.number.get(thisSize - 1) / 100;
                    rangThis = 0;
                    minusThis = 1;

                }
            } else if (this.number.get(thisSize - 1) > 9) {
                if (rangThis == 0) {

                    value = this.number.get(thisSize - 1) % 10;
                    rangThis = 1;

                } else if (rangThis == 1) {

                    value = this.number.get(thisSize - 1) / 10;
                    rangThis = 0;
                    minusThis = 1;

                }
            } else if (this.number.get(thisSize - 1) <= 9) {

                value = this.number.get(thisSize - 1);
                minusThis = 1;

            }

            //Ниже алгоритм достает нужную цифру из массива второго числа
            if (number1.number.get(number1Size - 1) > 99) {
                if (rangNumber1 == 0) {

                    value1 = number1.number.get(number1Size - 1) % 10;
                    rangNumber1 = 1;

                } else if (rangNumber1 == 1) {

                    value1 = number1.number.get(number1Size - 1) / 10 % 10;
                    rangNumber1 = 2;

                } else if (rangNumber1 == 2) {

                    value1 = number1.number.get(number1Size - 1) / 100;
                    rangNumber1 = 0;
                    minusNumber1 = 1;

                }
            } else if (number1.number.get(number1Size - 1) > 9) {
                if (rangNumber1 == 0) {

                    value1 = number1.number.get(number1Size - 1) % 10;
                    rangNumber1 = 1;

                } else if (rangNumber1 == 1) {

                    value1 = number1.number.get(number1Size - 1) / 10;
                    rangNumber1 = 0;
                    minusNumber1 = 1;

                }
            } else if (number1.number.get(number1Size - 1) <= 9) {

                value1 = number1.number.get(number1Size - 1);
                minusNumber1 = 1;

            }

            if (minusThis == 1) {
                thisSize -= 1;
                minusThis = 0;
            }

            if (minusNumber1 == 1) {
                number1Size -= 1;
                minusNumber1 = 0;
            }

            if (value >= value1) {

                string.append(value - value1);
            } else {

                string.append(10 + value - value1);

                value = 0;
                int j = thisSize;
                int rangThis1 = rangThis;

                while (value == 0) {

                    //Ниже алгоритм достает нужную цифру из массива первого числа
                    if (this.number.get(j) > 99) {
                        if (rangThis1 == 0) {

                            value = this.number.get(j) % 10;
                            rangThis1 = 1;

                        } else if (rangThis1 == 1) {

                            value = this.number.get(j) / 10 % 10;
                            rangThis1 = 2;

                        } else if (rangThis1 == 2) {

                            value = this.number.get(j) / 100;
                            rangThis1 = 0;
                            minusThis = 1;

                        }
                    } else if (this.number.get(j) > 9) {
                        if (rangThis1 == 0) {

                            value = this.number.get(j) % 10;
                            rangThis1 = 1;

                        } else if (rangThis1 == 1) {

                            value = this.number.get(j) / 10;
                            rangThis1 = 0;
                            minusThis = 1;

                        }
                    } else if (this.number.get(j) <= 9) {

                        value = this.number.get(j);
                        minusThis = 1;

                    }

                    byte digit1 = (byte) (this.number.get(j) / 100);
                    byte digit2 = (byte) (this.number.get(j) / 10 % 10);
                    byte digit3 = (byte) (this.number.get(j) % 10);

                    if (digit3 == value) {
                        if (value == 0) {

                            digit3 = 9;
                        } else {

                            digit3 -= 1;
                        }
                    } else if (digit2 == value) {
                        if (value == 0) {

                            digit2 = 9;
                        } else {

                            digit2 -= 1;
                        }
                    } else {
                        if (value == 0) {

                            digit1 = 9;
                        } else {

                            digit1 -= 1;
                        }
                    }

                    this.number.remove(j);
                    this.number.add(j, (byte) (digit1 * 100 + digit2 * 10 + digit3));

                    if (minusThis == 1) {
                        j -= 1;
                        minusThis = 0;
                        rangThis1 = 0;
                    }
                }
            }
        }
        minusThis = 0;
        while (thisSize > 0) {

            //Ниже алгоритм достает нужную цифру из массива первого числа
            if (this.number.get(thisSize - 1) > 99) {
                if (rangThis == 0) {

                    value = this.number.get(thisSize - 1) % 10;
                    rangThis = 1;

                } else if (rangThis == 1) {

                    value = this.number.get(thisSize - 1) / 10 % 10;
                    rangThis = 2;

                } else if (rangThis == 2) {

                    value = this.number.get(thisSize - 1) / 100;
                    rangThis = 0;
                    minusThis = 1;

                }
            } else if (this.number.get(thisSize - 1) > 9) {
                if (rangThis == 0) {

                    value = this.number.get(thisSize - 1) % 10;
                    rangThis = 1;

                } else if (rangThis == 1) {

                    value = this.number.get(thisSize - 1) / 10;
                    rangThis = 0;
                    minusThis = 1;

                }
            } else if (this.number.get(thisSize - 1) <= 9) {

                value = this.number.get(thisSize - 1);
                minusThis = 1;

            }

            string.append(value);

            if (minusThis == 1) {
                thisSize -= 1;
                minusThis = 0;
            }
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

        for (int i = 0; i < this.number.size() * number1.number.size() * 9; i++) {
            intermediate.add((byte) -1);
        }

        int value = 0;
        int value1 = 0;
        int placeNumber = 0;
        int place = 0;

        int thisSize = this.number.size();
        int number1Size = number1.number.size();
        byte rangThis = 0;
        byte rangNumber1 = 0;
        byte minusThis = 0;
        byte minusNumber1 = 0;

        while (number1Size > 0) {

            place = placeNumber;
            //Ниже алгоритм достает нужную цифру из массива второго числа
            if (number1.number.get(number1Size - 1) > 99) {
                if (rangNumber1 == 0) {

                    value = number1.number.get(number1Size - 1) % 10;
                    rangNumber1 = 1;

                } else if (rangNumber1 == 1) {

                    value = number1.number.get(number1Size - 1) / 10 % 10;
                    rangNumber1 = 2;

                } else if (rangNumber1 == 2) {

                    value = number1.number.get(number1Size - 1) / 100;
                    rangNumber1 = 0;
                    minusNumber1 = 1;

                }
            } else if (number1.number.get(number1Size - 1) > 9) {
                if (rangNumber1 == 0) {

                    value = number1.number.get(number1Size - 1) % 10;
                    rangNumber1 = 1;

                } else if (rangNumber1 == 1) {

                    value = number1.number.get(number1Size - 1) / 10;
                    rangNumber1 = 0;
                    minusNumber1 = 1;

                }
            } else if (number1.number.get(number1Size - 1) <= 9) {

                value = number1.number.get(number1Size - 1);
                minusNumber1 = 1;

            }

            while (thisSize > 0) {

                //Ниже алгоритм достает нужную цифру из массива первого числа
                if (this.number.get(thisSize - 1) > 99) {
                    if (rangThis == 0) {

                        value1 = this.number.get(thisSize - 1) % 10;
                        rangThis = 1;

                    } else if (rangThis == 1) {

                        value1 = this.number.get(thisSize - 1) / 10 % 10;
                        rangThis = 2;

                    } else if (rangThis == 2) {

                        value1 = this.number.get(thisSize - 1) / 100;
                        rangThis = 0;
                        minusThis = 1;

                    }
                } else if (this.number.get(thisSize - 1) > 9) {
                    if (rangThis == 0) {

                        value1 = this.number.get(thisSize - 1) % 10;
                        rangThis = 1;

                    } else if (rangThis == 1) {

                        value1 = this.number.get(thisSize - 1) / 10;
                        rangThis = 0;
                        minusThis = 1;

                    }
                } else if (this.number.get(thisSize - 1) <= 9) {

                    value1 = this.number.get(thisSize - 1);
                    minusThis = 1;

                }

                if (minusThis == 1) {
                    thisSize -= 1;
                    minusThis = 0;
                }

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

            if (minusNumber1 == 1) {
                number1Size -= 1;
                minusNumber1 = 0;
            }

            thisSize = this.number.size();
            rangThis = 0;
            minusThis = 0;
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
        int compareNumber = new Infinity(this.number.toString()).compareTo(new Infinity(number1.number.toString()));

        while (compareNumber > 0 || compareNumber == 0) {

            this.number = new Infinity(new Infinity(main(this.number)).minus(new Infinity(main(number1.number)))).number;
            whole++;

            compareNumber = new Infinity(this.number.toString()).compareTo(new Infinity(number1.number.toString()));
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

        int compareNumber = new Infinity(this.number.toString()).compareTo(new Infinity(number1.number.toString()));

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

        int compareNumber = new Infinity(this.number.toString()).compareTo(new Infinity(number1.number.toString()));

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