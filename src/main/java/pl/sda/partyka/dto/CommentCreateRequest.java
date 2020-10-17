package pl.sda.partyka.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Validated
public class CommentCreateRequest {

    @NotNull
    @NotBlank
    @Length(max = 500)
    private String content;
    @NotNull
    private Long eventId;
}
