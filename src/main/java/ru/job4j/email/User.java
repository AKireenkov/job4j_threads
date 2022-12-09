package ru.job4j.email;

/**
 * Модель данных User.
 * Содержит поля с информацией о пользователе: username, email
 *
 * @author Andrey Kireenkov
 * @version 1.0
 * @since 09.12.2022
 */
public class User {
    private final String username;
    private final String email;

    public User(String username, String email) {
        this.username = username;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }
}
