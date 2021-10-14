package net.thumbtack.school.library.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetBooksByBookNameDtoRequest {

    private String bookName;

}
