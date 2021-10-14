package net.thumbtack.school.library.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class GetBooksByCategoriesDtoRequest {

    private boolean all;
    private List<String> categories;

}
