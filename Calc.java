import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;

public class Calc {
    public static boolean rimsk = false; // римские числа или нет в строке
    public static int rimsk_kol = 0; // количество римских чисел
    private static int[] intervals={0, 1, 4, 5, 9, 10, 40, 50, 90, 100, 400, 500, 900, 1000};
    private static String[] numerals={"", "I", "IV", "V", "IX", "X", "XL", "L", "XC", "C", "CD", "D", "CM", "M"};
    private static String[] numerals_test={"I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X"};

    public static void main(String [] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Ваша строка : ");
        String name = sc.nextLine();
        telo(name);
/* тестовая часть
        for (int j=0;j<9;j++){
            for (int i=1;i<9;i++) {
                telo(numerals_test[i]+" + "+numerals_test[j]);
            }
        }

 */
    }
    public static void telo(String name) {
            int a,b;
            rimsk = false;
            rimsk_kol = 0;
        String[] words = name.split(" ");
        if (words.length!=3) {
            System.out.println("throws Exception //т.к. Строка не корректна");
            System.exit(0);
        }
        a=conv(words[0]);
        b=conv(words[2]);
        switch (words[1]){
            case "+" :
                a=a+b;
                break;
            case "-" :
                a=a-b;
                break;
            case "*" :
                a=a*b;
                break;
            case "/" :
                if (b==0) {
                    System.out.println("throws Exception //т.к. Разве на 0 делимся ????");
                    System.exit(0);
                }
                a=a/b;
                break;
            default:
                System.out.println("throws Exception //т.к. Опа, а такой операции нету : "+words[1]);
                System.exit(0);
        }
        if (rimsk) {
            if ((a<1) & (rimsk_kol==2)) {
                System.out.print("throws Exception //т.к. в римской системе нет таких чисел");
                System.exit(0);
            }
            if (rimsk_kol==2) {
                System.out.print("Ответ : ");
                System.out.println(toRoman(a));
            } else { System.out.println("throws Exception //т.к. используются одновременно разные системы счисления"); }

        } else {
            System.out.print("Ответ : ");
            System.out.println(a);
        }
    }

    public static int conv(String s) {
        char[] ws= s.toCharArray();
        int res,tt=0;
        try {
            res = Integer.parseInt(s);
        }
        catch (NumberFormatException e) {
            for (int kk=0;kk<14;kk++) {
                if(Objects.equals(numerals[kk],Character.toString(ws[0]))) { tt++; break;}
            }
            if (tt==0) {
                System.out.println("throws Exception //т.к. А тут непонятно какое число у нас : " + s);
                System.exit(0);
            }
            rimsk = true;
            rimsk_kol += 1;
            res = toArabic(s);
        }
        if ((res>10) || (res<1)) {
            System.out.println("throws Exception //т.к. А на входе от 1 до 10 у нас ");
            System.exit(0);
        }
        return res;
    }
    private final static int findFloor(final int number, final int firstIndex, final int lastIndex) {
        if(firstIndex==lastIndex)
            return firstIndex;
        if(intervals[firstIndex]==number)
            return firstIndex;
        if(intervals[lastIndex]==number)
            return lastIndex;
        final int median=(lastIndex+firstIndex)/2;
        if(median==firstIndex)
            return firstIndex;
        if(number == intervals[median])
            return median;
        if(number > intervals[median])
            return findFloor(number, median, lastIndex);
        else
            return findFloor(number, firstIndex, median);

    }

    public final static String toRoman(final int number) {
        int floorIndex=findFloor(number, 0, intervals.length-1);
        if(number==intervals[floorIndex])
            return numerals[floorIndex];
        return numerals[floorIndex]+toRoman(number-intervals[floorIndex]);
    }

    public static int toArabic(String roman) {
        int result = 0;
        for (int i = intervals.length-1; i >= 0; i-- ) {
            while (roman.indexOf(numerals[i]) == 0 && numerals[i].length() > 0) {
                result += intervals[i];
                roman = roman.substring(numerals[i].length());
            }
        }
        return result;
    }
}
