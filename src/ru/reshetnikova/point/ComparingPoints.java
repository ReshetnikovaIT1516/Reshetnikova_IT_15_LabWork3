package ru.reshetnikova.point;

import ru.reshetnikova.validation.Validation;

import java.util.Scanner;

public class ComparingPoints {
    private int x;
    private int y;

    public ComparingPoints(int x, int y) {
        Validation.validateCoordinates(x, y);
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        ComparingPoints point = (ComparingPoints) obj;
        return x == point.x && y == point.y;
    }


    @Override
    public String toString() {
        return "Точка(" + x + ";" + y + ")";
    }

    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }

    public void setX(int x) {
        Validation.validateCoordinate(x, "X");
        this.x = x;
    }

    public void setY(int y) {
        Validation.validateCoordinate(y, "Y");
        this.y = y;
    }

    public static void demonstrate() {
        Scanner scanner = new Scanner(System.in);

        ComparingPoints[] points = new ComparingPoints[3];

        for (int i = 0; i < 3; i++) {
            System.out.println("\nТочка " + (i + 1) + ":");
            int x = getValidInt(scanner, "X");
            int y = getValidInt(scanner, "Y");
            points[i] = new ComparingPoints(x, y);
        }

        System.out.println("\nСозданные точки:");
        for (int i = 0; i < points.length; i++) {
            System.out.println((i + 1) + ". " + points[i]);
        }

        System.out.println("\nРезультаты сравнения:");
        System.out.println("Точка 1 равна Точке 2: " + points[0].equals(points[1]));
        System.out.println("Точка 1 равна Точке 3: " + points[0].equals(points[2]));
        System.out.println("Точка 2 равна Точке 3: " + points[1].equals(points[2]));

    }

    private static int getValidInt(Scanner scanner, String coordinateName) {
        while (true) {
            try {
                System.out.print(coordinateName + ": ");
                String input = scanner.next();
                return Validation.validateInt(input, coordinateName);
            } catch (RuntimeException e) {
                System.out.println("Ошибка валидации: " + e.getMessage());
                scanner.nextLine();
            }
        }
    }
}