package pl.sda.partyka.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;
import pl.sda.partyka.domain.Role;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Data
@Validated
public class UserCreateRequest {

    @Email
    private String login;
    @NotNull
    @Length(min = 8, max = 30)
    private String password;
    @NotNull
    @NotBlank
    @Length(max = 50)
    private String displauName;
    private List<Role> roles = new ArrayList<>();
}
