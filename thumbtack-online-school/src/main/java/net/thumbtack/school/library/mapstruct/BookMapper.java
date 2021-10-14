package net.thumbtack.school.library.mapstruct;

import net.thumbtack.school.library.dto.request.InsertBookDtoRequest;
import net.thumbtack.school.library.dto.request.ReceiveBookDtoRequest;
import net.thumbtack.school.library.model.Book;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BookMapper {

    BookMapper INSTANCE = Mappers.getMapper(BookMapper.class);

    Book fromInsertDtoToBook(InsertBookDtoRequest insertBookDtoRequest);
    Book fromReceiveDtoToBook(ReceiveBookDtoRequest receiveBookDtoRequest);
}
