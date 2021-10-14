package net.thumbtack.school.library.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.thumbtack.school.library.model.Book;

import java.util.Collection;

@Getter
@Setter
@AllArgsConstructor
public class GetBooksDtoResponse {

    private Collection<Book> books;

    public GetBooksDtoResponse() {

    }
}
