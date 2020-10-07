package pl.sda.partyka.dto;

import lombok.Data;
import pl.sda.partyka.domain.Role;

import java.util.ArrayList;
import java.util.List;

@Data
public class UserCreateRequest {

    private String login;
    private String password;
    private String displauName;
    private List<Role> roles = new ArrayList<>();
}
