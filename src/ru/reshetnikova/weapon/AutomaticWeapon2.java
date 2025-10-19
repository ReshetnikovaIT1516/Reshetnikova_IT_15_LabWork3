package ru.reshetnikova.weapon;

import ru.reshetnikova.validation.Validation;

import java.util.Scanner;
// для задачи про стрелков
public class AutomaticWeapon2 extends Weapon {
    private final int maxCapacity;
    private final int fireRate;

    // Конструктор с указанием вместимости и скорострельности
    public AutomaticWeapon2(int maxCapacity, int fireRate) {
        super(0); // начинаем с 0 патронов
        this.maxCapacity = maxCapacity;
        this.fireRate = fireRate;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public int getFireRate() {
        return fireRate;
    }

    @Override
    public void shoot() {
        int actualShots = Math.min(ammo(), fireRate);
        int emptyShots = fireRate - actualShots;

        System.out.println("Автоматическая очередь (" + fireRate + " выстрелов):");

        // Реальные выстрелы
        for (int i = 0; i < actualShots; i++) {
            if (getAmmo()) {
                System.out.println("Бах!");
            } else {
                break;
            }
        }

        // Холостые выстрелы (если скорострельность больше чем патронов)
        for (int i = 0; i < emptyShots; i++) {
            System.out.println("Клац!");
        }
    }

    public int reload(int bullets) {
        Validation.validateBulletCount(bullets);
        int spaceAvailable = maxCapacity - ammo();
        int bulletsToAdd = Math.min(bullets, spaceAvailable);

        // ВАЖНО: не используем load() для добавления, а вычисляем новое значение
        int newAmmoCount = ammo() + bulletsToAdd;
        load(newAmmoCount); // load() заменяет количество патронов

        return bullets - bulletsToAdd;
    }
    // Метод для разрядки
    public int unload() {
        int unloadedBullets = ammo();
        load(0); // устанавливаем количество патронов в 0
        return unloadedBullets;
    }

    @Override
    public String toString() {
        return "Автомат (вместимость=" + maxCapacity +
                ", патронов=" + ammo() +
                ", скорострельность=" + fireRate + " в/с)";
    }

    // Упрощенный демонстрационный метод
    public static void demonstrate() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Введите вместимость автомата: ");
        String capacityInput = scanner.nextLine();
        int capacity = Integer.parseInt(capacityInput);

        System.out.print("Введите скорострельность (выстрелов в секунду): ");
        String fireRateInput = scanner.nextLine();
        int fireRate = Integer.parseInt(fireRateInput);

        AutomaticWeapon2 weapon = new AutomaticWeapon2(capacity, fireRate);
        System.out.println("Создан автомат: " + weapon);

        // Простая демонстрация
        System.out.print("Сколько патронов зарядить? ");
        String bulletsInput = scanner.nextLine();
        int bullets = Integer.parseInt(bulletsInput);
        weapon.reload(bullets);

        System.out.println("После зарядки: " + weapon);
        System.out.println("Стреляем:");
        weapon.shoot();
        System.out.println("После стрельбы: " + weapon);
    }
}