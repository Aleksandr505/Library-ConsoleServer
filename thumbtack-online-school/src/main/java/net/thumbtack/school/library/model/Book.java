package net.thumbtack.school.library.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
public class Book {

    private int id;
    private String bookName;
    private List<String> authors;
    private Set<String> categories;
    private transient User provider;
    private transient User recipient;
    private transient LocalDate deadline;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return id == book.id && bookName.equals(book.bookName) && authors.equals(book.authors) && categories.equals(book.categories) && provider.equals(book.provider);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, bookName, authors, categories, provider);
    }
}
