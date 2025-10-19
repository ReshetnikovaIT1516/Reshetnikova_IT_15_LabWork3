package ru.reshetnikova.name;

import ru.reshetnikova.validation.Validation;

import java.util.Scanner;

public class NotEmptyNames {
    private final String firstName;
    private final String lastName;
    private final String patronymic;

    public NotEmptyNames(String firstName) {
        String validatedFirstName;
        try {
            Validation.validateName(firstName);
            validatedFirstName = firstName.trim();
        } catch (RuntimeException e) {
            System.out.println("Ошибка: " + e.getMessage() + ". Установлено 'Иван'.");
            validatedFirstName = "Иван";
        }
        this.firstName = validatedFirstName;
        this.lastName = null;
        this.patronymic = null;
    }

    public NotEmptyNames(String lastName, String firstName) {
        String validatedFirstName;
        String validatedLastName;

        try {
            Validation.validateTwoFieldsEmpty(firstName, lastName);

            try {
                Validation.validateName(firstName);
                validatedFirstName = firstName.trim();
            } catch (RuntimeException e) {
                System.out.println("Ошибка имени: " + e.getMessage() + ". Установлено 'Иван'.");
                validatedFirstName = "Иван";
            }

            if (lastName == null || lastName.trim().isEmpty()) {
                validatedLastName = "Иванов";
            } else {
                validatedLastName = lastName.trim();
            }

        } catch (RuntimeException e) {
            System.out.println("Ошибка создания: " + e.getMessage());
            validatedFirstName = "Иван";
            validatedLastName = "Иванов";
        }

        this.firstName = validatedFirstName;
        this.lastName = validatedLastName;
        this.patronymic = null;
    }

    public NotEmptyNames(String lastName, String firstName, String patronymic) {
        String validatedFirstName;
        String validatedLastName;
        String validatedPatronymic;

        try {
            Validation.validateAllFieldsEmpty(firstName, lastName, patronymic);

            try {
                Validation.validateName(firstName);
                validatedFirstName = firstName.trim();
            } catch (RuntimeException e) {
                System.out.println("Ошибка имени: " + e.getMessage() + ". Установлено 'Иван'.");
                validatedFirstName = "Иван";
            }

            if (lastName == null || lastName.trim().isEmpty()) {
                validatedLastName = "Иванов";
            } else {
                validatedLastName = lastName.trim();
            }

            if (patronymic == null || patronymic.trim().isEmpty()) {
                validatedPatronymic = "Иванович";
            } else {
                validatedPatronymic = patronymic.trim();
            }

        } catch (RuntimeException e) {
            System.out.println("Ошибка создания: " + e.getMessage());
            validatedFirstName = "Иван";
            validatedLastName = "Иванов";
            validatedPatronymic = "Иванович";
        }

        this.firstName = validatedFirstName;
        this.lastName = validatedLastName;
        this.patronymic = validatedPatronymic;
    }

    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getPatronymic() { return patronymic; }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        if (lastName != null) result.append(lastName);
        if (firstName != null) {
            if (result.length() > 0) result.append(" ");
            result.append(firstName);
        }
        if (patronymic != null) {
            if (result.length() > 0) result.append(" ");
            result.append(patronymic);
        }
        return result.toString();
    }

    // Демонстрация с вводом с клавиатуры
    public static void demonstrate() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\n1. Создание человека только с именем:");
        System.out.print("Введите имя: ");
        String name1 = scanner.nextLine();
        NotEmptyNames person1 = new NotEmptyNames(name1);
        System.out.println("Результат: " + person1);

        System.out.println("\n2. Создание человека с фамилией и именем:");
        System.out.print("Введите фамилию: ");
        String lastName2 = scanner.nextLine();
        System.out.print("Введите имя: ");
        String name2 = scanner.nextLine();
        NotEmptyNames person2 = new NotEmptyNames(lastName2, name2);
        System.out.println("Результат: " + person2);

        System.out.println("\n3. Создание человека с полным ФИО:");
        System.out.print("Введите фамилию: ");
        String lastName3 = scanner.nextLine();
        System.out.print("Введите имя: ");
        String name3 = scanner.nextLine();
        System.out.print("Введите отчество: ");
        String patronymic3 = scanner.nextLine();
        NotEmptyNames person3 = new NotEmptyNames(lastName3, name3, patronymic3);
        System.out.println("Результат: " + person3);

        System.out.println("\n4. Обработки ошибок (введите пустые строки):");
        System.out.print("Введите фамилию: ");
        String lastName4 = scanner.nextLine();
        System.out.print("Введите имя: ");
        String name4 = scanner.nextLine();
        System.out.print("Введите отчество: ");
        String patronymic4 = scanner.nextLine();
        NotEmptyNames person4 = new NotEmptyNames(lastName4, name4, patronymic4);
        System.out.println("Результат: " + person4);
    }
}