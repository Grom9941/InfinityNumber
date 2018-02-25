import java.util.*;

public class Main {
    // public static void main(String[] args) {
    //      System.out.println(new Main("9174").plus(new Main("9173")));
    //  }

    private ArrayList<Integer> number = new ArrayList<>();

    //Преобразование в массив
    public Main(String str) {
        boolean zero = true;

        for (int i = 0; i < str.length(); i++) {

            //if (!Character.isDigit(str.charAt(i))) throw new IllegalArgumentException();

            if (str.charAt(i) != '0' || !zero) {
                number.add(Character.getNumericValue(str.charAt(i)));
                zero = false;
            }
        }
    }

    //Преобразование в строку
    public Main(ArrayList<Integer> array) {
        boolean zero = true;
        StringBuilder str = new StringBuilder();

        for (Integer anArray : array) {

            if (anArray != 0 || !zero) {
                str.append(anArray);
                zero = false;
            }
        }

        // return str.toString();
    }

    //Добавление в начало массива 0
    private ArrayList<Integer> add0(int value, ArrayList<Integer> array) {

        for (int i = 0; i < value; i++) {
            array.add(0, 0);
        }

        return array;
    }

    //Сложение
    public Main plus(Main num) {

        if (this.number.size() > num.number.size()) {
            num.number = add0(this.number.size() - num.number.size(), num.number);
        } else this.number = add0(num.number.size() - this.number.size(), this.number);

        int ost = 0;
        int value;

        for (int i = this.number.size() - 1; i >= 0; i--) {

            value = (this.number.get(i) + num.number.get(i) + ost) % 10;
            ost = (this.number.get(i) + num.number.get(i)) / 10;

            this.number.remove(i);
            this.number.add(i, value);
        }

        if (ost != 0) this.number.add(0, ost);
        return new Main(this.number);
    }

    //Замена символа


    //Вычитание
    private Main minus(Main num) {

        int value;
        int ost;
        int compareNumber = new Main(this.number.toString()).compare(new Main(num.number.toString()));

        if (compareNumber == 0) return new Main("0");
        if (compareNumber == 2) {

            ArrayList<Integer> changeArray = this.number;

            this.number = num.number;
            num.number = changeArray;
        }

        for (int i = this.number.size() - 1; i >= 0; i--) {

            if (this.number.get(i) >= num.number.get(i)) {

                value = this.number.get(i) - num.number.get(i);

                this.number.remove(i);
                this.number.add(i, value);
            } else {

                value = 10 + this.number.get(i) - num.number.get(i);
                int j = i - 1;
                ost = 9;

                this.number.remove(i);
                this.number.add(i, value);

                while (this.number.get(j) == 0) {

                    value = ost + this.number.get(j);

                    this.number.remove(j);
                    this.number.add(j, value);
                    j--;
                }

                value = this.number.get(j) - 1;

                this.number.remove(j);
                this.number.add(j, value);
            }
        }
        if (compareNumber == 2) return //"-"
                new Main(this.number);

        return new Main(this.number);
    }


    //Максимальное
    private Main max(Main num) {

        int compareNumber = new Main(this.number.toString()).compare(new Main(num.number.toString()));

        if (compareNumber == 1) return new Main(this.number);
        if (compareNumber == 2) return new Main(num.number);

        return new Main("Нет максимального");
    }

    //Минимальное
    private Main min(Main num) {

        int compareNumber = new Main(this.number.toString()).compare(new Main(num.number.toString()));

        if (compareNumber == 1) return new Main(num.number);
        if (compareNumber == 2) return new Main(this.number);

        return new Main("Нет минимального");
    }

    //Сравнение
    private int compare(Main num) {

        if (this.number.size() > num.number.size()) return 1;
        if (this.number.size() < num.number.size()) return 2;

        for (int i = 0; i < this.number.size(); i++) {

            if (this.number.get(i) > num.number.get(i)) return 1;
            if (this.number.get(i) < num.number.get(i)) return 2;
        }

        return 0;
    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj) return true;

        if (obj instanceof Main) {

            Main other = (Main) obj;
            return this.toString().equals(other.toString());
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

        StringBuilder str = new StringBuilder();

        for (Integer aNumber : number) {
            str.append(aNumber);
        }

        return str.toString();
    }
}