package net.thumbtack.school.library.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
public class InsertBookDtoRequest {

    private String bookName;
    private List<String> authors;
    private Set<String> categories;

}
