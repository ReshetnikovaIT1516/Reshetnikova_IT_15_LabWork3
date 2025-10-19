package ru.reshetnikova.main;

import ru.reshetnikova.name.NotEmptyNames;
import ru.reshetnikova.phone.PhoneReferenceGuide;
import ru.reshetnikova.point.ComparingPoints;
import ru.reshetnikova.weapon.AutomaticWeapon;
import ru.reshetnikova.weapon.Gun;
import ru.reshetnikova.weapon.ReloadingTheGun;
import ru.reshetnikova.weapon.Shooter;
import ru.reshetnikova.power.PowerCalculator;
import ru.reshetnikova.weapon.Gun8;

import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("1) Задача 1.5: Перезарядка Пистолета.");
            System.out.println("2) Задача 1.7: Непустые Имена.");
            System.out.println("3) Задача 2.3: Желтые страницы.");
            System.out.println("4) Задача 3.4: Автомат.");
            System.out.println("5) Задача 4.1: Оружие.");
            System.out.println("6) Задача 5.8: Лучший стрелок.");
            System.out.println("7) Задача 6.2: Сравнение точек.");
            System.out.println("8) Задача 7.1: Навести порядок.");
            System.out.println("9) Задача 7.2: Главный метод.");
            System.out.println("10) Задача 7.3: Возведение в степень.");
            System.out.println("11) Задача 8.2: Патроны наследникам.");
            System.out.println("0 - Выход");
            System.out.print("Выберите задачу: ");

            String choice = scanner.next();

            switch (choice) {
                case "1":
                    scanner.nextLine();
                    System.out.println("Задача 1.5: Перезарядка Пистолета.  ");
                    ReloadingTheGun.demonstrate();
                    break;

                case "2":
                    scanner.nextLine();
                    System.out.println("Задача 1.7: Непустые Имена.");
                    NotEmptyNames.demonstrate();
                    break;

                case "3":
                    scanner.nextLine();
                    System.out.println("Задача 2.3: Желтые страницы. ");
                    PhoneReferenceGuide.demonstrate();
                    break;

                case "4":
                    scanner.nextLine();
                    System.out.println("Задача 3.4: Автомат. ");
                    AutomaticWeapon.demonstrate();
                    break;

                case "5":
                    scanner.nextLine();
                    System.out.println("Задача 4.1: Оружие. ");
                    Gun.demonstrate();
                    break;

                case "6":
                    scanner.nextLine();
                    System.out.println("Задача 5.8: Лучший стрелок.  ");
                    Shooter.demonstrate();
                    break;

                case "7":
                    scanner.nextLine();
                    System.out.println("Задача 6.2: Сравнение точек.  ");
                    ComparingPoints.demonstrate();
                    break;

                case "8":
                    scanner.nextLine();
                    System.out.println("Задача 7.1: Навести порядок. ");
                    System.out.println("Все классы размещены в пакетах ");
                    break;

                case "9":
                    scanner.nextLine();
                    System.out.println("Задача 7.2: Главный метод. ");
                    ShooterDemo.demonstrateShooterMethods();
                    break;

                case "10":
                    scanner.nextLine();
                    System.out.println("Задача 7.3: Возведение в степень. ");
                    PowerCalculator.demonstrate();
                    break;

                case "11":
                    scanner.nextLine();
                    System.out.println("Задача 8.2: Патроны наследникам.  ");
                    Gun8.demonstrate();
                    break;

                case "0":
                    System.out.println("Выход из программы");
                    scanner.close();
                    return;

                default:
                    System.out.println("Неверный выбор! Попробуйте снова.");
            }

            System.out.println("\nНажмите 'Enter' чтобы снова выбрать номер задачи");
            scanner.nextLine();

        }
    }
}