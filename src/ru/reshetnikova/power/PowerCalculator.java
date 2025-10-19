package ru.reshetnikova.power;

import static java.lang.Integer.parseInt;
import static java.lang.Math.pow;
import java.util.Scanner;
public class PowerCalculator {


    public static double calculatePower(String xStr, String yStr) {
        // Преобразуем строки в числа используя короткое имя метода
        int x = parseInt(xStr);
        int y = parseInt(yStr);

        // Возводим в степень используя короткое имя метода
        double result = Math.pow(x, y);

        return result;
    }


    public static void demonstrate() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите два числа:");

        System.out.print("Введите число X: ");
        String xStr = scanner.nextLine();

        System.out.print("Введите степень Y: ");
        String yStr = scanner.nextLine();

        try {
            double result = calculatePower(xStr, yStr);
            System.out.println(xStr + " ^ " + yStr + " = " + result);
        } catch (NumberFormatException e) {
            System.out.println("Ошибка: введите целые числа!");
        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
        }

    }
}