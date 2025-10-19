package ru.reshetnikova.weapon;

import ru.reshetnikova.validation.Validation;

import java.util.Scanner;

public class ReloadingTheGun {
    private final int maxCapacity;
    private int currentBullets;

    public ReloadingTheGun(int maxCapacity) {
        Validation.validateCapacity(maxCapacity);
        this.maxCapacity = maxCapacity;
        this.currentBullets = 0;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public int getCurrentBullets() {
        return currentBullets;
    }

    public boolean isLoaded() {
        return currentBullets > 0;
    }

    public int reload(int bullets) {
        Validation.validateBulletCount(bullets);
        int spaceAvailable = maxCapacity - currentBullets;
        int bulletsToAdd = Math.min(bullets, spaceAvailable);
        currentBullets += bulletsToAdd;
        return bullets - bulletsToAdd;
    }

    public int unload() {
        int unloadedBullets = currentBullets;
        currentBullets = 0;
        return unloadedBullets;
    }

    public void shoot() {
        if (currentBullets > 0) {
            currentBullets--;
            System.out.println("Бах!");
        } else {
            System.out.println("Клац!");
        }
    }

    @Override
    public String toString() {
        return "Пистолет (вместимость=" + maxCapacity + ", патронов=" + currentBullets + ", заряжен=" + isLoaded() + ")";
    }

    public static void demonstrate() {
        Scanner scanner = new Scanner(System.in);

        // Создаем пистолет с ручным вводом вместимости
        System.out.print("Введите максимальную вместимость пистолета: ");
        try {
            String input = scanner.nextLine();
            int capacity = Validation.validateInt(input, "Вместимость пистолета");
            ReloadingTheGun gun = new ReloadingTheGun(capacity);
            System.out.println("Создан пистолет: " + gun);

            while (true) {
                System.out.println("\nУПРАВЛЕНИЕ ПИСТОЛЕТОМ");
                System.out.println("1. Зарядить патроны");
                System.out.println("2. Выстрелить");
                System.out.println("3. Разрядить");
                System.out.println("4. Показать состояние");
                System.out.println("0. Назад в меню выбора задач");
                System.out.print("Выберите действие: ");

                String choice = scanner.nextLine();

                switch (choice) {
                    case "1":
                        System.out.print("Введите количество патронов для зарядки: ");
                        try {
                            input = scanner.nextLine();
                            int bullets = Validation.validateInt(input, "Количество патронов");
                            int remaining = gun.reload(bullets);
                            System.out.println("Успешно заряжено: " + (bullets - remaining) + " патронов");
                            if (remaining > 0) {
                                System.out.println("Возвращено лишних: " + remaining + " патронов");
                            }
                            System.out.println("Текущее состояние: " + gun);
                        } catch (RuntimeException e) {
                            System.out.println("Ошибка: " + e.getMessage());
                        }
                        break;

                    case "2":
                        System.out.print("Сколько раз выстрелить? ");
                        try {
                            input = scanner.nextLine();
                            int shots = Validation.validateInt(input, "Количество выстрелов");
                            System.out.println("Стреляем " + shots + " раз:");
                            for (int i = 0; i < shots; i++) {
                                gun.shoot();
                            }
                            System.out.println("Текущее состояние: " + gun);
                        } catch (RuntimeException e) {
                            System.out.println("Ошибка: " + e.getMessage());
                        }
                        break;

                    case "3":
                        int unloaded = gun.unload();
                        System.out.println("Пистолет разряжен! Возвращено патронов: " + unloaded);
                        System.out.println("Текущее состояние: " + gun);
                        break;

                    case "4":
                        System.out.println(gun);
                        break;

                    case "0":
                        return;

                    default:
                        System.out.println("Неверный выбор!");
                }
            }
        } catch (RuntimeException e) {
            System.out.println("Ошибка при создании пистолета: " + e.getMessage());
        }
    }
}