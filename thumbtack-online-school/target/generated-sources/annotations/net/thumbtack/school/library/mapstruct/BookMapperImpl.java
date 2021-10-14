package net.thumbtack.school.library.mapstruct;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import net.thumbtack.school.library.dto.request.InsertBookDtoRequest;
import net.thumbtack.school.library.dto.request.ReceiveBookDtoRequest;
import net.thumbtack.school.library.model.Book;
import net.thumbtack.school.library.model.User;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-10-13T00:41:26+0600",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.12 (Oracle Corporation)"
)
public class BookMapperImpl implements BookMapper {

    @Override
    public Book fromInsertDtoToBook(InsertBookDtoRequest insertBookDtoRequest) {
        if ( insertBookDtoRequest == null ) {
            return null;
        }

        String bookName = null;
        List<String> authors = null;
        Set<String> categories = null;

        bookName = insertBookDtoRequest.getBookName();
        List<String> list = insertBookDtoRequest.getAuthors();
        if ( list != null ) {
            authors = new ArrayList<String>( list );
        }
        Set<String> set = insertBookDtoRequest.getCategories();
        if ( set != null ) {
            categories = new HashSet<String>( set );
        }

        int id = 0;
        User provider = null;
        User recipient = null;
        LocalDate deadline = null;

        Book book = new Book( id, bookName, authors, categories, provider, recipient, deadline );

        return book;
    }

    @Override
    public Book fromReceiveDtoToBook(ReceiveBookDtoRequest receiveBookDtoRequest) {
        if ( receiveBookDtoRequest == null ) {
            return null;
        }

        int id = 0;
        LocalDate deadline = null;

        id = receiveBookDtoRequest.getId();
        deadline = receiveBookDtoRequest.getDeadline();

        String bookName = null;
        List<String> authors = null;
        Set<String> categories = null;
        User provider = null;
        User recipient = null;

        Book book = new Book( id, bookName, authors, categories, provider, recipient, deadline );

        return book;
    }
}
