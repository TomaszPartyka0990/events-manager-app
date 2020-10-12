package pl.sda.partyka.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor(staticName = "of")
public class EventTableView {

    private Long id;
    private String title;
    private LocalDate startingDate;
    private LocalDate endingDate;
    private String description;
}
