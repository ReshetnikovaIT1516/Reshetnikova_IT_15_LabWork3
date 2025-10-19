package ru.reshetnikova.phone;

import ru.reshetnikova.validation.InvalidPhoneException;
import ru.reshetnikova.validation.Validation;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.ArrayList;

public class PhoneReferenceGuide {
    private Map<String, String> contacts; // имя - телефон
    private Map<String, String> phoneToName; // телефон - имя (для быстрой проверки уникальности)

    public PhoneReferenceGuide() {
        this.contacts = new HashMap<>();
        this.phoneToName = new HashMap<>();
    }

    // Добавление новой пары "телефон – имя"
    public String addContact(String phone, String name) {
        Validation.validatePhone(phone);
        Validation.validateContactName(name);

        String cleanPhone = phone.trim();
        String cleanName = name.trim();

        // Проверяем, не используется ли телефон у другого человека
        if (phoneToName.containsKey(cleanPhone) && !phoneToName.get(cleanPhone).equals(cleanName)) {
            String existingName = phoneToName.get(cleanPhone);
            throw new InvalidPhoneException("Телефон " + cleanPhone + " уже используется у контакта: " + existingName);
        }

        String oldPhone = contacts.put(cleanName, cleanPhone);

        // Обновляем обратное отображение
        if (oldPhone != null) {
            phoneToName.remove(oldPhone); // удаляем старый телефон
        }
        phoneToName.put(cleanPhone, cleanName); // добавляем новый телефон

        return oldPhone; // возвращает старый телефон или null если не было
    }

    // Удаление значения по имени
    public String removeContact(String name) {
        Validation.validateContactName(name);
        String cleanName = name.trim();

        String phone = contacts.remove(cleanName);
        if (phone != null) {
            phoneToName.remove(phone); // удаляем из обратного отображения
        }
        return phone; // возвращает телефон удаленного контакта или null
    }

    // Получение телефона по имени
    public String getPhone(String name) {
        Validation.validateContactName(name);
        return contacts.get(name.trim());
    }

    // Получение имени по телефону
    public String getNameByPhone(String phone) {
        Validation.validatePhone(phone);
        return phoneToName.get(phone.trim());
    }

    // Проверка наличия телефона
    public boolean containsPhone(String phone) {
        Validation.validatePhone(phone);
        return phoneToName.containsKey(phone.trim());
    }

    // Проверка наличия имени
    public boolean containsName(String name) {
        Validation.validateContactName(name);
        return contacts.containsKey(name.trim());
    }

    // Получение количества контактов
    public int getContactCount() {
        return contacts.size();
    }

    // Получение всех пар
    public String[][] getAllPairs() {
        String[][] pairs = new String[contacts.size()][2];
        int i = 0;
        for (Map.Entry<String, String> entry : contacts.entrySet()) {
            pairs[i][0] = entry.getKey(); // имя
            pairs[i][1] = entry.getValue(); // телефон
            i++;
        }
        return pairs;
    }

    // Получение всех телефонов
    public String[] getAllPhones() {
        return contacts.values().toArray(new String[0]);
    }

    // Получение всех имен
    public String[] getAllNames() {
        return contacts.keySet().toArray(new String[0]);
    }

    // Получение имен по началу названия
    public String[] getNamesByPrefix(String prefix) {
        if (prefix == null) {
            prefix = "";
        }
        final String finalPrefix = prefix.toLowerCase().trim();

        ArrayList<String> result = new ArrayList<>();
        for (String name : contacts.keySet()) {
            if (name.toLowerCase().startsWith(finalPrefix)) {
                result.add(name);
            }
        }
        return result.toArray(new String[0]);
    }

    @Override
    public String toString() {
        if (contacts.isEmpty()) {
            return "Телефонный справочник пуст";
        }

        StringBuilder sb = new StringBuilder("Телефонный справочник:\n");
        for (Map.Entry<String, String> entry : contacts.entrySet()) {
            sb.append(entry.getValue()).append(" - ").append(entry.getKey()).append("\n");
        }
        return sb.toString();
    }

    public static void demonstrate() {
        Scanner scanner = new Scanner(System.in);
        PhoneReferenceGuide phoneBook = new PhoneReferenceGuide();

        while (true) {
            System.out.println("\nТЕЛЕФОННЫЙ СПРАВОЧНИК");
            System.out.println("1. Добавить контакт");
            System.out.println("2. Найти телефон по имени");
            System.out.println("3. Найти имя по телефону");
            System.out.println("4. Удалить контакт");
            System.out.println("5. Показать все контакты");
            System.out.println("6. Проверить наличие телефона");
            System.out.println("7. Проверить наличие имени");
            System.out.println("8. Получить статистику");
            System.out.println("9. Поиск по началу имени");
            System.out.println("0. Назад в меню выбора задач");
            System.out.print("Выберите действие: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    System.out.print("Введите имя: ");
                    String name = scanner.nextLine();
                    System.out.print("Введите телефон: ");
                    String phone = scanner.nextLine();
                    try {
                        String oldPhone = phoneBook.addContact(phone, name);
                        if (oldPhone != null) {
                            System.out.println("Контакт обновлен! Старый телефон: " + oldPhone);
                        } else {
                            System.out.println("Контакт добавлен!");
                        }
                    } catch (RuntimeException e) {
                        System.out.println("Ошибка: " + e.getMessage());
                    }
                    break;

                case "2":
                    System.out.print("Введите имя для поиска: ");
                    name = scanner.nextLine();
                    try {
                        phone = phoneBook.getPhone(name);
                        if (phone != null) {
                            System.out.println("Телефон: " + phone);
                        } else {
                            System.out.println("Контакт не найден");
                        }
                    } catch (RuntimeException e) {
                        System.out.println("Ошибка: " + e.getMessage());
                    }
                    break;

                case "3":
                    System.out.print("Введите телефон для поиска: ");
                    phone = scanner.nextLine();
                    try {
                        name = phoneBook.getNameByPhone(phone);
                        if (name != null) {
                            System.out.println("Имя: " + name);
                        } else {
                            System.out.println("Контакт не найден");
                        }
                    } catch (RuntimeException e) {
                        System.out.println("Ошибка: " + e.getMessage());
                    }
                    break;

                case "4":
                    System.out.print("Введите имя для удаления: ");
                    name = scanner.nextLine();
                    try {
                        phone = phoneBook.removeContact(name);
                        if (phone != null) {
                            System.out.println("Контакт удален! Телефон: " + phone);
                        } else {
                            System.out.println("Контакт не найден");
                        }
                    } catch (RuntimeException e) {
                        System.out.println("Ошибка: " + e.getMessage());
                    }
                    break;

                case "5":
                    System.out.println(phoneBook);
                    break;

                case "6":
                    System.out.print("Введите телефон для проверки: ");
                    phone = scanner.nextLine();
                    try {
                        boolean exists = phoneBook.containsPhone(phone);
                        System.out.println("Телефон " + (exists ? "найден" : "не найден") + " в справочнике");
                    } catch (RuntimeException e) {
                        System.out.println("Ошибка: " + e.getMessage());
                    }
                    break;

                case "7":
                    System.out.print("Введите имя для проверки: ");
                    name = scanner.nextLine();
                    try {
                        boolean exists = phoneBook.containsName(name);
                        System.out.println("Имя " + (exists ? "найдено" : "не найдено") + " в справочнике");
                    } catch (RuntimeException e) {
                        System.out.println("Ошибка: " + e.getMessage());
                    }
                    break;

                case "8":
                    System.out.println("Количество контактов: " + phoneBook.getContactCount());
                    System.out.println("Все имена: " + String.join(", ", phoneBook.getAllNames()));
                    System.out.println("Все телефоны: " + String.join(", ", phoneBook.getAllPhones()));
                    break;

                case "9":
                    System.out.print("Введите начало имени: ");
                    String prefix = scanner.nextLine();
                    String[] names = phoneBook.getNamesByPrefix(prefix);
                    if (names.length > 0) {
                        System.out.println("Найдены контакты:");
                        for (String foundName : names) {
                            System.out.println("- " + foundName + ": " + phoneBook.getPhone(foundName));
                        }
                    } else {
                        System.out.println("Контакты не найдены");
                    }
                    break;

                case "0":
                    return;

                default:
                    System.out.println("Неверный выбор!");
            }
        }
    }
}