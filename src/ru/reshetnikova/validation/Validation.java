package ru.reshetnikova.validation;

public class Validation {
    public static void validateName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new EmptyFieldException("Имя не может быть null или пустой строкой");
        }
    }

    public static void validateNameParts(String firstName, String lastName, String patronymic) {
        if ((firstName == null || firstName.trim().isEmpty()) &&
                (lastName == null || lastName.trim().isEmpty()) &&
                (patronymic == null || patronymic.trim().isEmpty())) {
            throw new EmptyFieldException("Как минимум одно из полей (имя, фамилия, отчество) должно быть заполнено");
        }
    }

    public static void validateCapacity(int capacity) {
        if (capacity <= 0) {
            throw new InvalidNumberException("Вместимость должна быть положительной");
        }
    }

    public static void validateBulletCount(int bulletCount) {
        if (bulletCount < 0) {
            throw new InvalidNumberException("Количество патронов не может быть отрицательным");
        }
    }

    public static void validateAllFieldsEmpty(String firstName, String lastName, String patronymic) {
        boolean firstNameEmpty = firstName == null || firstName.trim().isEmpty();
        boolean lastNameEmpty = lastName == null || lastName.trim().isEmpty();
        boolean patronymicEmpty = patronymic == null || patronymic.trim().isEmpty();

        if (firstNameEmpty && lastNameEmpty && patronymicEmpty) {
            throw new EmptyFieldException("Все поля пустые. Установлены значения по умолчанию: Иванов Иван Иванович");
        }
    }

    public static void validateTwoFieldsEmpty(String field1, String field2) {
        boolean field1Empty = field1 == null || field1.trim().isEmpty();
        boolean field2Empty = field2 == null || field2.trim().isEmpty();

        if (field1Empty && field2Empty) {
            throw new EmptyFieldException("Оба поля пустые. Установлены значения по умолчанию");
        }
    }

    public static void validatePhone(String phone) {
        if (phone == null || phone.trim().isEmpty()) {
            throw new EmptyFieldException("Телефон не может быть пустым");
        }

        // Самая простая проверка
        if (!phone.matches("[0-9]+")) {
            throw new InvalidPhoneException("Номер телефона должен состоять только из цифр");
        }
    }

    public static void validateContactName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new EmptyFieldException("Имя контакта не может быть null или пустой строкой");
        }
    }

    public static void validateCoordinate(int coordinate, String coordinateName) {
        if (coordinateName == null || coordinateName.trim().isEmpty()) {
            throw new EmptyFieldException("Название координаты не может быть пустым");
        }
    }

    public static void validateCoordinates(int x, int y) {
        validateCoordinate(x, "X");
        validateCoordinate(y, "Y");
    }

    public static int validateInt(String input, String fieldName) {
        if (input == null || input.trim().isEmpty()) {
            throw new EmptyFieldException(fieldName + " не может быть пустым");
        }
        try {
            return Integer.parseInt(input.trim());
        } catch (NumberFormatException e) {
            throw new InvalidNumberException(fieldName + " должно быть целым числом");
        }
    }
}

