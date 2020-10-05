package pl.sda.partyka.dto;

import lombok.Data;

@Data
public class UserCreateRequest {

    private String login;
    private String password;
    private String displauName;
}
