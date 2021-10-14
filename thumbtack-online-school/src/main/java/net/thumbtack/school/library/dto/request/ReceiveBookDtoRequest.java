package net.thumbtack.school.library.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class ReceiveBookDtoRequest {

    private int id;
    private LocalDate deadline;

}
