package net.thumbtack.school.library.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
public class User {

    private int id;
    private String firstName;
    private String lastName;
    private String login;
    private String password;
    private transient Set<Book> providedBooks;
    private transient Set<Book> takenBooks;

    public User(int id, String firstName, String lastName, String login, String password) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.login = login;
        this.password = password;
        this.providedBooks = new HashSet<>();
        this.takenBooks = new HashSet<>();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return firstName.equals(user.firstName) && lastName.equals(user.lastName) && login.equals(user.login) && password.equals(user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, login, password);
    }
}
