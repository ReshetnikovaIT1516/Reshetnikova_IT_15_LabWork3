package ru.reshetnikova.weapon;

import ru.reshetnikova.validation.Validation;

import java.util.Scanner;

public class Shooter {
    private String name;
    private Weapon weapon;

    public Shooter(String name) {
        Validation.validateName(name);
        this.name = name.trim();
    }

    public Shooter(String name, Weapon weapon) {
        Validation.validateName(name);
        this.name = name.trim();
        this.weapon = weapon;
    }

    // Геттеры и сеттеры
    public String getName() {
        return name;
    }

    public void setName(String name) {
        Validation.validateName(name);
        this.name = name.trim();
    }

    public Weapon getWeapon() {
        return weapon;
    }

    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }

    // Основной метод - стрельба
    public void shoot() {
        if (weapon != null) {
            weapon.shoot();
        } else {
            System.out.println(name + ": не могу участвовать в перестрелке");
        }
    }

    @Override
    public String toString() {
        if (weapon == null) {
            return "Стрелок '" + name + "' (без оружия)";
        } else {
            return "Стрелок '" + name + "' (" + weapon + ")";
        }
    }

    // Вспомогательный метод для безопасного ввода чисел
    private static int getValidatedIntInput(Scanner scanner, String prompt, String fieldName) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine();
            try {
                return Validation.validateInt(input, fieldName);
            } catch (RuntimeException e) {
                System.out.println("Ошибка: " + e.getMessage() + ". Пожалуйста, введите целое число.");
            }
        }
    }

    // Демонстрационный метод с вводом с клавиатуры
    public static void demonstrate() {
        Scanner scanner = new Scanner(System.in);
        Shooter[] shooters = new Shooter[3];

        System.out.println("ЛУЧШИЙ СТРЕЛОК");
        System.out.println("Создадим 3 стрелков:");

        for (int i = 0; i < 3; i++) {
            System.out.println("\nСтрелок " + (i + 1));

            // Ввод имени
            System.out.print("Введите имя стрелка: ");
            String name = scanner.nextLine();
            // Проверка имени
            try {
                Validation.validateName(name);
            } catch (RuntimeException e) {
                System.out.println("Ошибка: " + e.getMessage() + ". Установлено имя по умолчанию 'Стрелок " + (i + 1) + "'");
                name = "Стрелок " + (i + 1);
            }

            // Выбор оружия
            System.out.println("Выберите оружие:");
            System.out.println("1. Без оружия");
            System.out.println("2. Пистолет");
            System.out.println("3. Автомат");
            System.out.print("Ваш выбор: ");

            String weaponChoice = scanner.nextLine();
            Weapon weapon = null;

            switch (weaponChoice) {
                case "1":
                    // Без оружия
                    break;

                case "2":
                    // Пистолет
                    int capacity = getValidatedIntInput(scanner, "Введите вместимость пистолета: ", "Вместимость пистолета");
                    try {
                        Validation.validateCapacity(capacity);
                        weapon = new Gun(capacity);
                    } catch (RuntimeException e) {
                        System.out.println("Ошибка: " + e.getMessage() + ". Установлена вместимость по умолчанию: 10");
                        weapon = new Gun(10);
                    }
                    break;

                case "3":
                    // Автомат
                    int autoCapacity = getValidatedIntInput(scanner, "Введите вместимость автомата: ", "Вместимость автомата");
                    int fireRate = getValidatedIntInput(scanner, "Введите скорострельность (выстрелов в секунду): ", "Скорострельность");

                    try {
                        Validation.validateCapacity(autoCapacity);
                        if (fireRate <= 0) {
                            throw new RuntimeException("Скорострельность должна быть положительной");
                        }
                        weapon = new AutomaticWeapon2(autoCapacity, fireRate);
                    } catch (RuntimeException e) {
                        System.out.println("Ошибка: " + e.getMessage() + ". Установлены значения по умолчанию: вместимость=30, скорострельность=10");
                        weapon = new AutomaticWeapon2(30, 10);
                    }
                    break;

                default:
                    System.out.println("Неверный выбор, создаем без оружия");
            }

            // Заряжаем оружие если оно есть
            if (weapon != null) {
                int bullets = getValidatedIntInput(scanner, "Сколько патронов зарядить? ", "Количество патронов");

                try {
                    Validation.validateBulletCount(bullets);

                    // Используем полиморфизм - все оружие имеет метод load()
                    int previousAmmo = weapon.ammo();
                    weapon.load(weapon.ammo() + bullets); // добавляем патроны к текущим
                    int actualLoaded = weapon.ammo() - previousAmmo;

                    System.out.println("Заряжено: " + actualLoaded + " патронов");
                    if (actualLoaded < bullets) {
                        System.out.println("Не влезло: " + (bullets - actualLoaded) + " патронов");
                    }
                } catch (RuntimeException e) {
                    System.out.println("Ошибка: " + e.getMessage() + ". Оружие не заряжено.");
                }
            }

            // Создаем стрелка
            shooters[i] = new Shooter(name, weapon);
            System.out.println("Создан: " + shooters[i]);
        }

        // Демонстрация стрельбы
        System.out.println("\nПЕРЕСТРЕЛКА");
        for (int i = 0; i < shooters.length; i++) {
            System.out.println("\nСтрелок " + (i + 1) + " (" + shooters[i].getName() + "):");
            shooters[i].shoot();
        }

        // Дополнительные действия
        while (true) {
            System.out.println("\nДОПОЛНИТЕЛЬНЫЕ ДЕЙСТВИЯ");
            System.out.println("1. Выстрелить конкретным стрелком");
            System.out.println("2. Показать информацию о стрелках");
            System.out.println("3. Перезарядить оружие");
            System.out.println("0. Завершить демонстрацию");
            System.out.print("Выберите действие: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    int shooterIndex = getValidatedIntInput(scanner, "Выберите стрелка (1-3): ", "Номер стрелка") - 1;
                    if (shooterIndex >= 0 && shooterIndex < 3) {
                        System.out.println(shooters[shooterIndex].getName() + " стреляет:");
                        shooters[shooterIndex].shoot();
                    } else {
                        System.out.println("Неверный номер стрелка");
                    }
                    break;

                case "2":
                    System.out.println("\nИнформация о стрелках:");
                    for (int i = 0; i < shooters.length; i++) {
                        System.out.println((i + 1) + ". " + shooters[i]);
                    }
                    break;

                case "3":
                    shooterIndex = getValidatedIntInput(scanner, "Выберите стрелка для перезарядки (1-3): ", "Номер стрелка") - 1;
                    if (shooterIndex >= 0 && shooterIndex < 3) {
                        Weapon weapon = shooters[shooterIndex].getWeapon();
                        if (weapon != null) {
                            int bullets = getValidatedIntInput(scanner, "Сколько патронов зарядить? ", "Количество патронов");

                            try {
                                Validation.validateBulletCount(bullets);

                                // Используем полиморфизм - все оружие имеет метод load()
                                int previousAmmo = weapon.ammo();
                                weapon.load(weapon.ammo() + bullets);
                                int actualLoaded = weapon.ammo() - previousAmmo;

                                System.out.println("Оружие перезаряжено! Заряжено: " + actualLoaded + " патронов");
                                if (actualLoaded < bullets) {
                                    System.out.println("Не влезло: " + (bullets - actualLoaded) + " патронов");
                                }
                            } catch (RuntimeException e) {
                                System.out.println("Ошибка: " + e.getMessage() + ". Оружие не перезаряжено.");
                            }
                        } else {
                            System.out.println("У этого стрелка нет оружия");
                        }
                    } else {
                        System.out.println("Неверный номер стрелка");
                    }
                    break;

                case "0":
                    return;

                default:
                    System.out.println("Неверный выбор");
            }
        }
    }
}