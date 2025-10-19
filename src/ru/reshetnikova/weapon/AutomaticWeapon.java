package ru.reshetnikova.weapon;

import ru.reshetnikova.validation.InvalidNumberException;
import ru.reshetnikova.validation.Validation;

import java.util.Scanner;

public class AutomaticWeapon extends ReloadingTheGun {
    private final int fireRate; // скорострельность (выстрелов в секунду)

    // Конструктор без параметров - скорострельность 30, вместимость 30
    public AutomaticWeapon() {
        super(30);
        this.fireRate = 30;
    }

    // Конструктор с указанием максимального числа патронов
    public AutomaticWeapon(int maxCapacity) {
        super(maxCapacity);
        Validation.validateCapacity(maxCapacity);
        this.fireRate = maxCapacity / 2;
    }

    // Конструктор с указанием вместимости и скорострельности
    public AutomaticWeapon(int maxCapacity, int fireRate) {
        super(maxCapacity);
        Validation.validateCapacity(maxCapacity);
        validateFireRate(fireRate);
        this.fireRate = fireRate;
    }

    // Проверка скорострельности
    private void validateFireRate(int fireRate) {
        if (fireRate <= 0) {
            throw new InvalidNumberException("Скорострельность должна быть положительной");
        }
    }

    public int getFireRate() {
        return fireRate;
    }

    // Переопределение метода shoot() - выстреливает количество раз, равное скорострельности
    @Override
    public void shoot() {
        int shotsToFire = Math.min(getCurrentBullets(), fireRate);

        if (shotsToFire > 0) {
            System.out.println("Автоматическая очередь (" + shotsToFire + " выстрелов):");
            for (int i = 0; i < shotsToFire; i++) {
                super.shoot(); // вызываем родительский метод для каждого выстрела
            }
        } else {
            System.out.println("Клац! (автоматический режим)");
        }
    }

    // Стрельба N секунд
    public void shootForSeconds(int seconds) {
        Validation.validateBulletCount(seconds);
        if (seconds <= 0) {
            throw new InvalidNumberException("Количество секунд должно быть положительным");
        }

        int totalShots = seconds * fireRate;
        int actualShots = Math.min(getCurrentBullets(), totalShots);

        System.out.println("Стрельба в течение " + seconds + " секунд (скорострельность " + fireRate + " в/с):");

        if (actualShots > 0) {
            for (int i = 0; i < actualShots; i++) {
                if (getCurrentBullets() > 0) {
                    super.shoot(); // используем родительский shoot для одиночного выстрела
                } else {
                    break;
                }
            }
            System.out.println("Произведено выстрелов: " + actualShots);
        } else {
            System.out.println("Клац! Патроны закончились");
        }
    }

    @Override
    public String toString() {
        return "Автомат (вместимость=" + getMaxCapacity() +
                ", патронов=" + getCurrentBullets() +
                ", заряжен=" + isLoaded() +
                ", скорострельность=" + fireRate + " в/с)";
    }

    public static void demonstrate() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nСОЗДАНИЕ АВТОМАТА");
            System.out.println("1. Создать автомат по умолчанию (вместимость 30, скорострельность 30)");
            System.out.println("2. Создать автомат с указанием вместимости");
            System.out.println("3. Создать автомат с указанием вместимости и скорострельности");
            System.out.println("0. Назад в меню выбора задач");
            System.out.print("Выберите тип создания: ");

            String choice = scanner.nextLine();
            AutomaticWeapon weapon = null;

            try {
                switch (choice) {
                    case "1":
                        weapon = new AutomaticWeapon();
                        System.out.println("Создан автомат: " + weapon);
                        break;

                    case "2":
                        System.out.print("Введите вместимость автомата: ");
                        String capacityInput = scanner.nextLine();
                        int capacity = Validation.validateInt(capacityInput, "Вместимость автомата");
                        weapon = new AutomaticWeapon(capacity);
                        System.out.println("Создан автомат: " + weapon);
                        break;

                    case "3":
                        System.out.print("Введите вместимость автомата: ");
                        capacityInput = scanner.nextLine();
                        capacity = Validation.validateInt(capacityInput, "Вместимость автомата");

                        System.out.print("Введите скорострельность (выстрелов в секунду): ");
                        String fireRateInput = scanner.nextLine();
                        int fireRate = Validation.validateInt(fireRateInput, "Скорострельность");

                        weapon = new AutomaticWeapon(capacity, fireRate);
                        System.out.println("Создан автомат: " + weapon);
                        break;

                    case "0":
                        return;

                    default:
                        System.out.println("Неверный выбор!");
                        continue;
                }

                // Управление созданным автоматом
                if (weapon != null) {
                    manageWeapon(weapon, scanner);
                }

            } catch (RuntimeException e) {
                System.out.println("Ошибка при создании автомата: " + e.getMessage());
            }
        }
    }

    private static void manageWeapon(AutomaticWeapon weapon, Scanner scanner) {
        while (true) {
            System.out.println("\nУПРАВЛЕНИЕ АВТОМАТОМ");
            System.out.println("1. Зарядить патроны");
            System.out.println("2. Одиночный выстрел (автоматическая очередь)");
            System.out.println("3. Стрельба в течение N секунд");
            System.out.println("4. Разрядить");
            System.out.println("5. Показать состояние");
            System.out.println("0. Назад к выбору типа автомата");
            System.out.print("Выберите действие: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    System.out.print("Введите количество патронов для зарядки: ");
                    try {
                        String input = scanner.nextLine();
                        int bullets = Validation.validateInt(input, "Количество патронов");
                        int remaining = weapon.reload(bullets);
                        System.out.println("Успешно заряжено: " + (bullets - remaining) + " патронов");
                        if (remaining > 0) {
                            System.out.println("Возвращено лишних: " + remaining + " патронов");
                        }
                        System.out.println("Текущее состояние: " + weapon);
                    } catch (RuntimeException e) {
                        System.out.println("Ошибка: " + e.getMessage());
                    }
                    break;

                case "2":
                    System.out.println("Производим автоматическую очередь:");
                    weapon.shoot();
                    System.out.println("Текущее состояние: " + weapon);
                    break;

                case "3":
                    System.out.print("Введите количество секунд для стрельбы: ");
                    try {
                        String input = scanner.nextLine();
                        int seconds = Validation.validateInt(input, "Количество секунд");
                        weapon.shootForSeconds(seconds);
                        System.out.println("Текущее состояние: " + weapon);
                    } catch (RuntimeException e) {
                        System.out.println("Ошибка: " + e.getMessage());
                    }
                    break;

                case "4":
                    int unloaded = weapon.unload();
                    System.out.println("Автомат разряжен! Возвращено патронов: " + unloaded);
                    System.out.println("Текущее состояние: " + weapon);
                    break;

                case "5":
                    System.out.println(weapon);
                    break;

                case "0":
                    return;

                default:
                    System.out.println("Неверный выбор!");
            }
        }
    }
}
