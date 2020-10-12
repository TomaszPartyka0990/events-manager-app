package pl.sda.partyka.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Validated
public class EventCreateRequest {

    @NotNull
    @NotBlank
    private String title;
    @NotNull
    private String startingDate;
    @NotNull
    private String endingDate;
    @NotNull
    @NotBlank
    @Length(min = 20)
    private String description;
}
